/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.search;

import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.LayoutConstants;
import org.eclipse.jface.widgets.LabelFactory;
import org.eclipse.jface.widgets.WidgetFactory;
import org.eclipse.search.ui.ISearchPage;
import org.eclipse.search.ui.ISearchPageContainer;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

public class ModelSearchPage extends DialogPage implements ISearchPage {

	public static final String EXTENSION_POINT_ID = "org.eclipse.fordiac.ide.application.search.ModelSearchPage"; //$NON-NLS-1$
	public static final String ID = "ModelSearchPage"; //$NON-NLS-1$
	public static final int NUMBER_OF_SEARCH_OPTIONS = 4;

	private ISearchPageContainer container;
	private Button instanceName;
	private Button pinName;
	private Button type;
	private Button comment;
	private Text query;
	private Button caseSensitive;
	private Button exactNameMatching;

	public Button getInstanceName() {
		return instanceName;
	}

	public Button getPinName() {
		return pinName;
	}

	public Button getType() {
		return type;
	}

	public Button getComment() {
		return comment;
	}

	public ISearchPageContainer getContainer() {
		return container;
	}

	public Button getCaseSensitive() {
		return caseSensitive;
	}

	@Override
	public void createControl(final Composite parent) {
		GridLayoutFactory.fillDefaults().numColumns(1).margins(LayoutConstants.getMargins()).generateLayout(parent);

		// Factory for the big composite
		final Composite composite = WidgetFactory.composite(NONE).create(parent);
		GridLayoutFactory.fillDefaults().numColumns(1).generateLayout(composite);

		final Group fbInfoGroup = new Group(composite, SWT.NONE);
		fbInfoGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		fbInfoGroup.setLayout(new GridLayout(NUMBER_OF_SEARCH_OPTIONS, false));
		fbInfoGroup.setText(Messages.SearchFor);

		instanceName = WidgetFactory.button(SWT.CHECK).text(Messages.InstanceName).create(fbInfoGroup);
		pinName = WidgetFactory.button(SWT.CHECK).text(Messages.PinName).create(fbInfoGroup);
		type = WidgetFactory.button(SWT.CHECK).text(Messages.Type).create(fbInfoGroup);
		comment = WidgetFactory.button(SWT.CHECK).text(Messages.Comment).create(fbInfoGroup);

		instanceName.setSelection(true);
		pinName.setSelection(false);
		type.setSelection(true);
		comment.setSelection(true);

		final Composite searchBoxAndOptions = WidgetFactory.composite(NONE).create(composite);
		GridLayoutFactory.fillDefaults().numColumns(1).generateLayout(searchBoxAndOptions);

		// Text box for the actual search
		final LabelFactory labelFactory = LabelFactory.newLabel(SWT.NONE);
		labelFactory.text(Messages.ContainingText).create(composite);
		query = WidgetFactory.text(SWT.BORDER).message(Messages.TypeQuery).create(composite);
		query.setFocus();
		GridDataFactory.fillDefaults().grab(true, false).applyTo(query);

		caseSensitive = WidgetFactory.button(SWT.CHECK).text(Messages.CaseSensitive).create(composite);
		exactNameMatching = WidgetFactory.button(SWT.CHECK).text(Messages.ExactNameMatching)
				.create(composite);

		setControl(composite);
	}

	@Override
	public boolean performAction() {
		// What type is the user searching for
		final boolean isCheckedInstanceName = instanceName.getSelection();
		final boolean isCheckedPinName = pinName.getSelection();
		final boolean isCheckedType = type.getSelection();
		final boolean isCheckedComment = comment.getSelection();
		final boolean isCaseSensitive = caseSensitive.getSelection();
		final boolean isExactNameMatching = exactNameMatching.getSelection();

		// Search string aka the name of it
		final String searchString = query.getText();

		// Check to see if at least one of the check boxes is ticked and if the search string exists
		final boolean optionSelected = isCheckedInstanceName || isCheckedPinName || isCheckedType || isCheckedComment;
		if (!"".equals(searchString) && optionSelected) { //$NON-NLS-1$

			final ModelQuerySpec modelQuerySpec = new ModelQuerySpec(searchString, isCheckedInstanceName,
					isCheckedPinName, isCheckedType, isCheckedComment, isCaseSensitive, isExactNameMatching);

			final ModelSearchQuery searchJob = new ModelSearchQuery(modelQuerySpec);
			NewSearchUI.runQueryInBackground(searchJob, NewSearchUI.getSearchResultView());

			// close the page
			return true;

		}
		errorDialogDisplay();
		// dialog remains open so the user can fix the search parameters
		return false;

	}

	private void errorDialogDisplay() {
		MessageDialog.openWarning(getShell(), Messages.Warning, Messages.ErrorMessageSearch);
	}

	@Override
	public void setContainer(final ISearchPageContainer container) {
		this.container = container;
	}

}
