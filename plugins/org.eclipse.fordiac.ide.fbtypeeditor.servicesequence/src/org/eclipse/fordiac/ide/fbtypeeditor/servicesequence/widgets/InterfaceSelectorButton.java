/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr, Melanie Winter
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.widgets;

import java.util.function.Consumer;

import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.ChangePrimitiveInterfaceCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Primitive;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterface;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class InterfaceSelectorButton {
	private final Button interfaceSelector;
	private Primitive type;

	public InterfaceSelectorButton(final Composite parent, final Consumer<Command> executeCommand) {
		interfaceSelector = new Button(parent, SWT.PUSH);
		interfaceSelector.setText("Interface");
		interfaceSelector.setImage(FordiacImage.ICON_LEFT_INPUT_PRIMITIVE.getImage());
		interfaceSelector.pack();

		interfaceSelector.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final ServiceInterface current = type.getInterface();
				ServiceInterface other;
				if (current.isLeftInterface()) {
					other = current.getService().getRightInterface();
				} else {
					other = current.getService().getLeftInterface();
				}
				final Command cmd = new ChangePrimitiveInterfaceCommand(type, other);
				executeCommand.accept(cmd);
			}


			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				// nothing to do here
			}
		});

	}

	public void setType(final Primitive primitive) {
		this.type = primitive;
		setPrimitiveIcon();
	}

	private void setPrimitiveIcon() {
		if (type.getInterface().isLeftInterface()) {
			interfaceSelector.setImage(FordiacImage.ICON_LEFT_INPUT_PRIMITIVE.getImage());
		} else {
			interfaceSelector.setImage(FordiacImage.ICON_RIGHT_INPUT_PRIMITIVE.getImage());
		}
	}
}
