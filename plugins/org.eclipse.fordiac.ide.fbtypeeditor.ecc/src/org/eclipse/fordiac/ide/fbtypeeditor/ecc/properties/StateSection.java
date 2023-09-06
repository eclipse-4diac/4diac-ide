/*******************************************************************************
 * Copyright (c) 2015 - 2017 fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Harmonized look and feel, added multi line selection
 *   Virendra Ashiwal, Bianca Wiesmayr
 *     - added "[none]" as showing text in ECC->State->Property when no
 *       algorithm is selected
 *     - change TransitionViewer to table and make it editable
 *   Alois Zoitl - extracted helper for ComboCellEditors that unfold on activation
 *   Bianca Wiesmayr - externalized code from StateSection and cleanup
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.properties;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.widgets.ActionEditingComposite;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.widgets.TransitionEditingComposite;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class StateSection extends AbstractSection {
	private Text stateNameText;
	private Text stateCommentText;
	private ActionEditingComposite actionGroup;
	private TransitionEditingComposite transitionGroup;

	@Override
	protected ECState getType() {
		return (ECState) type;
	}

	@Override
	protected Object getInputType(final Object input) {
		final Object obToCheck = (input instanceof final EditPart ep) ? ep.getModel() : input;

		if (obToCheck instanceof ECState) {
			return obToCheck;
		}
		return null;
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		parent.setLayout(new GridLayout(2, true));
		createStateNameControls(parent);
		createStateCommentControls(parent);
		actionGroup = new ActionEditingComposite(parent, getWidgetFactory(), this);
		transitionGroup = new TransitionEditingComposite(parent, getWidgetFactory(), this);
	}

	public void createStateNameControls(final Composite parent) {
		final Composite nameComposite = getWidgetFactory().createComposite(parent);
		nameComposite.setLayout(new GridLayout(2, false));
		nameComposite.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		getWidgetFactory().createCLabel(nameComposite, Messages.StateSection_Name);
		stateNameText = createGroupText(nameComposite, true);
		stateNameText.addListener(SWT.Modify, e -> {
			removeContentAdapter();
			executeCommand(ChangeNameCommand.forName(getType(), stateNameText.getText()));
			addContentAdapter();
		});
	}

	public void createStateCommentControls(final Composite parent) {
		final Composite commentComposite = getWidgetFactory().createComposite(parent);
		commentComposite.setLayout(new GridLayout(2, false));
		commentComposite.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		getWidgetFactory().createCLabel(commentComposite, Messages.StateSection_Comment);
		stateCommentText = createGroupText(commentComposite, true);
		stateCommentText.addListener(SWT.Modify, e -> {
			removeContentAdapter();
			executeCommand(new ChangeCommentCommand(getType(), stateCommentText.getText()));
			addContentAdapter();
		});
	}

	@Override
	protected void setInputCode() {
		stateCommentText.setEnabled(false);
		stateNameText.setEnabled(false);
	}

	@Override
	public void refresh() {
		actionGroup.refresh();
		transitionGroup.refresh();
		final CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if (null != type) {
			stateCommentText.setText(getType().getComment() != null ? getType().getComment() : ""); //$NON-NLS-1$
			stateNameText.setText(getType().getName() != null ? getType().getName() : ""); //$NON-NLS-1$
		}
		commandStack = commandStackBuffer;
	}

	@Override
	protected void setInputInit() {
		// we have to do this here because at this point in time we have a valid type
		actionGroup.setTypeAndCommandStack(getType(), commandStack);
		transitionGroup.setTypeAndCommandStack(getType(), commandStack);
	}
}
