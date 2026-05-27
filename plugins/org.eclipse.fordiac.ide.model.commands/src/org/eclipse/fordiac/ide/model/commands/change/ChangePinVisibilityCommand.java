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
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.helpers.InterfaceHelper;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.ui.errormessages.ErrorMessenger;
import org.eclipse.gef.commands.Command;

public class ChangePinVisibilityCommand extends Command implements ScopedCommand {

	private final IInterfaceElement interfaceElement; // The pin
	private final boolean visible;

	public ChangePinVisibilityCommand(final IInterfaceElement interfaceElement, final boolean visible) {
		this.interfaceElement = Objects.requireNonNull(interfaceElement);
		this.visible = visible;
	}

	@Override
	public void execute() {
		interfaceElement.setVisible(visible);
	}

	@Override
	public void redo() {
		interfaceElement.setVisible(visible);
	}

	@Override
	public void undo() {
		interfaceElement.setVisible(!visible);
	}

	@Override
	public boolean canExecute() {
		if (interfaceElement instanceof Event || interfaceElement instanceof ErrorMarkerInterface) {
			return false;
		}

		if (!visible && !InterfaceHelper.canHidePin(interfaceElement)) {
			ErrorMessenger
					.popUpErrorMessage(org.eclipse.fordiac.ide.model.commands.Messages.HidePinCommand_ConnectedPin);
			return false;
		}

		return true;
	}

	protected IInterfaceElement getInterfaceElement() {
		return interfaceElement;
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(interfaceElement);
	}
}
