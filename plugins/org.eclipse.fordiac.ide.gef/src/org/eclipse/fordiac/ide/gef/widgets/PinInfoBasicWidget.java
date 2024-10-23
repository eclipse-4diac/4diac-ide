/*******************************************************************************
 * Copyright (c) 2022, 2023 Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *   Martin Jobst - lock editing for function FBs
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.widgets;

import java.util.function.Consumer;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.gef.editors.TypeDeclarationEditor;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class PinInfoBasicWidget implements CommandExecutor {

	private Text nameText;
	private Text commentText;
	private TypeSelectionWidget typeSelectionWidget;
	private TypeDeclarationEditor typeDeclarationEditor;
	private IInterfaceElement type;

	protected final TabbedPropertySheetWidgetFactory widgetFactory;

	protected Consumer<Command> commandExecutor;

	public PinInfoBasicWidget(final Composite parent, final TabbedPropertySheetWidgetFactory widgetFactory) {
		this.widgetFactory = widgetFactory;
		createWidget(parent);
	}

	protected void createWidget(final Composite parent) {
		GridLayoutFactory.swtDefaults().numColumns(2).generateLayout(parent);

		widgetFactory.createCLabel(parent, FordiacMessages.Name + ":"); //$NON-NLS-1$
		nameText = createText(parent);
		nameText.addModifyListener(e -> onNameChange(nameText));

		widgetFactory.createCLabel(parent, FordiacMessages.Comment + ":"); //$NON-NLS-1$
		commentText = createText(parent);
		commentText.addModifyListener(e -> executeCommand(new ChangeCommentCommand(type, commentText.getText())));

		widgetFactory.createCLabel(parent, FordiacMessages.Type + ":"); //$NON-NLS-1$
		typeSelectionWidget = new TypeSelectionWidget(widgetFactory, this::handleTypeSelectionChanged);
		typeSelectionWidget.createControls(parent);
		typeDeclarationEditor = new TypeDeclarationEditor(parent, SWT.SINGLE | SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(typeDeclarationEditor.getControl());
	}

	protected Text createText(final Composite parent) {
		final Text text = widgetFactory.createText(parent, "", SWT.BORDER); //$NON-NLS-1$
		text.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		text.setEditable(true);
		text.setEnabled(true);
		return text;
	}

	public void disableAllFields() {
		nameText.setEnabled(false);
		commentText.setEnabled(false);
	}

	public void refresh() {
		if (type != null) {
			final Consumer<Command> commandExecutorBuffer = commandExecutor;
			commandExecutor = null;
			if ((null != type.getName()) && (null != type.getComment())) {
				nameText.setText(type.getName());
				commentText.setText(type.getComment());
				typeSelectionWidget.refresh();
				typeDeclarationEditor.refresh();
				checkFieldEnablements();
			}
			commandExecutor = commandExecutorBuffer;
		}

	}

	public void initialize(final IInterfaceElement type, final Consumer<Command> commandExecutor) {
		this.type = type;
		this.commandExecutor = commandExecutor;
		typeDeclarationEditor.setInterfaceElement(type);
		typeDeclarationEditor.setCommandExecutor(commandExecutor);
	}

	@Override
	public void executeCommand(final Command cmd) {
		if (commandExecutor != null) {
			commandExecutor.accept(cmd);
		}
	}

	protected void checkFieldEnablements() {
		nameText.setEditable(isEditable());
		commentText.setEditable(isEditable());
		typeSelectionWidget.setEditable(isTypeChangeable());
		typeSelectionWidget.getControl().setVisible(true);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(typeSelectionWidget.getControl());
		typeDeclarationEditor.setEditable(false);
		typeDeclarationEditor.getControl().setVisible(false);
		GridDataFactory.swtDefaults().exclude(true).applyTo(typeDeclarationEditor.getControl());
	}

	protected boolean isEditable() {
		return !(EcoreUtil.getRootContainer(getType()) instanceof FunctionFBType);
	}

	protected boolean isTypeChangeable() {
		return isEditable();
	}

	public IInterfaceElement getType() {
		return type;
	}

	public TypeSelectionWidget getTypeSelectionWidget() {
		return typeSelectionWidget;
	}

	protected TabbedPropertySheetWidgetFactory getWidgetFactory() {
		return widgetFactory;
	}

	private void handleTypeSelectionChanged(final String newTypeName) {
		if (isTypeChangeable()) {
			executeCommand(ChangeDataTypeCommand.forTypeDeclaration(getType(), newTypeName));
			// ensure that the new value is shown
			final Consumer<Command> commandExecutorBuffer = commandExecutor;
			commandExecutor = null;
			refresh();
			commandExecutor = commandExecutorBuffer;
		}
	}

	protected void onNameChange(final Text name) {
		executeCommand(ChangeNameCommand.forName(type, name.getText()));
	}
}