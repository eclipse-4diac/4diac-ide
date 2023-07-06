/*******************************************************************************
 * Copyright (c) 2022 - 2023 Primetals Technologies Austria GmbH
 *               2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *   Martin Jobst - adopt ST editor for initial values
 *                - fix refresh for array size changes
 *   Martin Melik Merkumians - add VarConfig checkbox for data pins
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.widgets;

import java.util.function.Consumer;

import org.eclipse.fordiac.ide.gef.editors.InitialValueEditor;
import org.eclipse.fordiac.ide.model.commands.change.ChangeVarConfigurationCommand;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class PinInfoDataWidget extends PinInfoBasicWidget {

	private InitialValueEditor initialValueEditor;
	private CLabel varConfigLabel;
	private Button varConfigCheckBox;

	public PinInfoDataWidget(final Composite parent, final TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, widgetFactory);
	}

	@Override
	public void initialize(final IInterfaceElement type, final Consumer<Command> commandExecutor) {
		super.initialize(type, commandExecutor);
		initialValueEditor.setInterfaceElement(type);
		initialValueEditor.setCommandExecutor(commandExecutor);
		setVarConfigOption(getType().isIsInput());
	}

	private void setVarConfigOption(final boolean enabled) {
		varConfigLabel.setVisible(enabled);
		varConfigCheckBox.setVisible(enabled);
		varConfigCheckBox.setEnabled(enabled);
	}

	@Override
	public void refresh() {
		super.refresh();
		final var type = getType();
		if (type != null) {
			final Consumer<Command> commandExecutorBuffer = commandExecutor;
			commandExecutor = null;
			varConfigCheckBox.setSelection(type.isVarConfig());
			commandExecutor = commandExecutorBuffer;
		}
		initialValueEditor.refresh();
	}

	@Override
	public VarDeclaration getType() {
		return (VarDeclaration) super.getType();
	}

	@Override
	protected void createWidget(final Composite parent) {
		super.createWidget(parent);

		widgetFactory.createCLabel(parent, FordiacMessages.InitialValue + ":"); //$NON-NLS-1$
		initialValueEditor = new InitialValueEditor(parent, SWT.SINGLE | SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(initialValueEditor.getControl());
		varConfigLabel = widgetFactory.createCLabel(parent, FordiacMessages.VarConfig + ":"); //$NON-NLS-1$
		varConfigCheckBox = widgetFactory.createButton(parent, null, SWT.CHECK);
		varConfigCheckBox.addListener(SWT.Selection, event -> executeCommand(
				new ChangeVarConfigurationCommand(getType(), varConfigCheckBox.getSelection())));
	}

	@Override
	public void disableAllFields() {
		super.disableAllFields();
		initialValueEditor.setEditable(false);
	}

	@Override
	protected void checkFieldEnablements() {
		super.checkFieldEnablements();
		initialValueEditor.setEditable(isTypeChangeable());
		varConfigCheckBox.setEnabled(isEditable());
	}
}