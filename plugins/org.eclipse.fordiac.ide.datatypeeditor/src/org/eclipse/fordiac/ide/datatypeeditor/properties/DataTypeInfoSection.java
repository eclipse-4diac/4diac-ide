/*******************************************************************************
 * Copyright (c) 2020, 2024 Johannes Kepler University, Linz,
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Jaeger, Bianca Wiesmayr
 *     - initial API and implementation and/or initial documentation
 *   Daniel Lindhuber - comment field
 *******************************************************************************/
package org.eclipse.fordiac.ide.datatypeeditor.properties;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.gef.widgets.PackageInfoWidget;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class DataTypeInfoSection extends AbstractSection {

	private PackageInfoWidget typeInfoWidget;
	private Text commentText;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);

		parent.setLayout(new GridLayout(2, false));

		createCommentField(parent);
		createTypeInfoGroup(parent);
	}

	private void createCommentField(final Composite parent) {
		final Composite container = getWidgetFactory().createComposite(parent);
		container.setLayout(new GridLayout(2, false));
		final GridData data = new GridData(SWT.FILL, 0, true, false);
		data.horizontalSpan = 2;
		container.setLayoutData(data);

		getWidgetFactory().createLabel(container, FordiacMessages.Comment + ":"); //$NON-NLS-1$
		commentText = createGroupText(container, true);
		commentText.addModifyListener(e -> {
			/*
			 * Without this if statement the editor would be "dirty" from the get-go: -
			 * editor listens for changes on the type - first load also triggers refresh -
			 * refresh sets comment text (always, even if the comment is empty) - the
			 * ChangeCommentCommand makes a change to the type Therefore, restricting
			 * command execution does the trick.
			 */
			if (!commentText.getText().equals(getType().getComment())) {
				executeCommand(new ChangeCommentCommand(getType(), commentText.getText()));
			}
		});

	}

	private void createTypeInfoGroup(final Composite parent) {
		typeInfoWidget = new PackageInfoWidget(getWidgetFactory(), this::getAnnotationModel);
		typeInfoWidget.createControls(parent);
	}

	@Override
	protected LibraryElement getType() {
		return (LibraryElement) type;
	}

	@Override
	protected LibraryElement getInputType(final Object input) {
		if (input instanceof final EObject eObject
				&& EcoreUtil.getRootContainer(eObject) instanceof final LibraryElement libraryElement) {
			return libraryElement;
		}
		return null;
	}

	@Override
	protected void setInputCode() {
		// currently nothing to do here
	}

	@Override
	protected void performRefresh() {
		commentText.setText((null != getType().getComment()) ? getType().getComment() : ""); //$NON-NLS-1$
		typeInfoWidget.refresh();
	}

	@Override
	protected void performRefreshAnnotations() {
		typeInfoWidget.refreshAnnotations();
	}

	@Override
	protected void setInputInit() {
		typeInfoWidget.initialize(getType(), this::executeCommand);
	}
}
