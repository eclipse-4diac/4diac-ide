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
package org.eclipse.fordiac.ide.gef.widgets;

import java.util.function.Consumer;

import org.eclipse.fordiac.ide.model.commands.change.ChangeArraySizeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeValueCommand;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class PinInfoDataWidget extends PinInfoBasicWidget {

	private Text arraySizeText;
	private Text initValueText;

	public PinInfoDataWidget(final Composite parent, final TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, widgetFactory);
	}

	@Override
	public void refresh() {
		super.refresh();
		if (getType() != null) {
			final Consumer<Command> commandExecutorBuffer = commandExecutor;
			commandExecutor = null;
			arraySizeText.setText((getType().getArraySize() > 0) ?
					String.valueOf(getType().getArraySize()) : ""); //$NON-NLS-1$

			initValueText.setText((getType().getValue() != null) ?
					getType().getValue().getValue() : ""); //$NON-NLS-1$
			commandExecutor = commandExecutorBuffer;
		}
	}

	@Override
	public VarDeclaration getType() {
		return (VarDeclaration) super.getType();
	}

	@Override
	protected void createWidget(final Composite parent) {
		super.createWidget(parent);
		widgetFactory.createCLabel(parent, FordiacMessages.ArraySize + ":"); //$NON-NLS-1$
		arraySizeText = createText(parent);
		arraySizeText
		.addModifyListener(e -> executeCommand(new ChangeArraySizeCommand(getType(), arraySizeText.getText())));

		widgetFactory.createCLabel(parent, FordiacMessages.InitialValue + ":"); //$NON-NLS-1$
		initValueText = createText(parent);
		initValueText
		.addModifyListener(e -> executeCommand(new ChangeValueCommand(getType(), initValueText.getText())));
	}

	@Override
	public void disableAllFields() {
		super.disableAllFields();
		arraySizeText.setEnabled(false);
		initValueText.setEnabled(false);
	}

	@Override
	protected void checkFieldEnablements() {
		super.checkFieldEnablements();
		arraySizeText.setEnabled(isTypeChangeable());
	}


}