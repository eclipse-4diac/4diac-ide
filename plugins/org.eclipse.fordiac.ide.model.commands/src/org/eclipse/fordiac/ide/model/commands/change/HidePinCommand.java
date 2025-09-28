/*******************************************************************************
 * Copyright (c) 2022, 2025 Primetals Technologies Austria GmbH
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

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.errormessages.ErrorMessenger;
import org.eclipse.gef.commands.Command;

public class HidePinCommand extends Command implements ScopedCommand {

	private final IInterfaceElement interfaceElement; // The pin
	private final boolean visible;

	public HidePinCommand(final IInterfaceElement interfaceElement, final boolean visible) {
		this.interfaceElement = Objects.requireNonNull(interfaceElement);
		this.visible = visible;
	}

	@Override
	public void execute() {
		interfaceElement.setVisible(visible);
	}

	@Override
	public void redo() {
		execute();
	}

	@Override
	public void undo() {
		interfaceElement.setVisible(!visible);
	}

	@Override
	public boolean canExecute() {
		if (!visible && isConnectedInsideSubApp(interfaceElement)) {
			ErrorMessenger.popUpErrorMessage(Messages.HidePinCommand_PinCannotBeHidden_ConnectedInside);
			return false;
		}
		return (interfaceElement instanceof VarDeclaration || interfaceElement instanceof AdapterDeclaration)
				&& (interfaceElement.isIsInput() && interfaceElement.getInputConnections().isEmpty()
						|| !interfaceElement.isIsInput() && interfaceElement.getOutputConnections().isEmpty())
				&& !isExpandedSubAppPinAndConnected(interfaceElement);
	}

	protected static boolean isExpandedSubAppPinAndConnected(final IInterfaceElement interfaceElement) {
		return interfaceElement.getBlockFBNetworkElement() instanceof final SubApp subApp && subApp.isUnfolded()
				&& !interfaceElement.getInputConnections().isEmpty()
				&& !interfaceElement.getOutputConnections().isEmpty();
	}

	private static boolean isConnectedInsideSubApp(final IInterfaceElement element) {
		if (element instanceof final VarDeclaration varDecl && element.isIsInput()
				&& element.eContainer().eContainer() instanceof final SubAppType sat) {
			final String name = varDecl.getName();
			return sat.getFBNetwork().getBlockFBNetworkElements().anyMatch(fb -> {
				final IInterfaceElement internal = fb.getInterfaceElement(name);
				return (internal != null && !internal.getInputConnections().isEmpty());
			});
		}
		return false;
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(interfaceElement);
	}
}
