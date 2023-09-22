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
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.HiddenElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;

public class HidePinCommand extends Command {

	private final HiddenElement hiddenElement; // The pin
	private final boolean visible;

	public HidePinCommand(final HiddenElement hiddenElement, final boolean visible) {
		this.hiddenElement = hiddenElement;
		this.visible = visible;
	}

	@Override
	public void execute() {
		hiddenElement.setVisible(visible);
	}

	@Override
	public void redo() {
		execute();
	}

	@Override
	public void undo() {
		hiddenElement.setVisible(!visible);
	}

	@Override
	public boolean canExecute() {
		return hiddenElement instanceof final IInterfaceElement interfaceElement
				&& (interfaceElement instanceof VarDeclaration || interfaceElement instanceof AdapterDeclaration)
				&& interfaceElement.isIsInput() && interfaceElement.getInputConnections().isEmpty()
				&& !isExpandedSubAppPinAndConnected(interfaceElement);
	}

	protected static boolean isExpandedSubAppPinAndConnected(final IInterfaceElement interfaceElement) {
		return interfaceElement.getFBNetworkElement() instanceof final SubApp subApp && subApp.isUnfolded()
				&& !interfaceElement.getInputConnections().isEmpty()
				&& !interfaceElement.getOutputConnections().isEmpty();
	}
}
