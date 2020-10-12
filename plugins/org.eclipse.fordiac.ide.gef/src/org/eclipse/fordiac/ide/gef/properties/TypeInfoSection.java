/*******************************************************************************
 * Copyright (c) 2014 - 2016 fortiss GmbH
 * 				 2020 Johannes Kepler Universiy Linz
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
 *   Alois Zoitl - extracted the ui into own widget to make it usable in other
 *                 places (e.g., system editor)
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import org.eclipse.fordiac.ide.gef.widgets.TypeInfoWidget;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * Properties tab which shows the FB type information of the selected FB
 *
 */
public abstract class TypeInfoSection extends AbstractSection {

	private TypeInfoWidget typeInfo;

	private Text fbTypeNameText;
	private Text commentText;

	@Override
	protected LibraryElement getType() {
		return (LibraryElement) type;
	}

	@Override
	protected void setInputInit() {
		typeInfo.initialize(getType(), this::executeCommand);
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		createTypeAndCommentSection(getLeftComposite());
		typeInfo = new TypeInfoWidget(getWidgetFactory());
		typeInfo.createControls(getLeftComposite(), getRightComposite());
	}

	private void createTypeAndCommentSection(Composite parent) {
		Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		getWidgetFactory().createCLabel(composite, FordiacMessages.TypeName + ":"); //$NON-NLS-1$
		fbTypeNameText = createGroupText(composite, false);
		getWidgetFactory().createCLabel(composite, FordiacMessages.Comment + ":"); //$NON-NLS-1$
		commentText = createGroupText(composite, true);
		commentText.addModifyListener(e -> executeCommand(new ChangeCommentCommand(getType(), commentText.getText())));
	}

	@Override
	public void setInputCode() {
		commentText.setEnabled(false);
		typeInfo.setEnabled(false);
	}

	@Override
	public void refresh() {
		CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if (null != type) {
			fbTypeNameText.setText(getType().getName() != null ? getType().getName() : ""); //$NON-NLS-1$
			commentText.setText(getType().getComment() != null ? getType().getComment() : ""); //$NON-NLS-1$
			typeInfo.refresh();
		}
		commandStack = commandStackBuffer;
	}

}
