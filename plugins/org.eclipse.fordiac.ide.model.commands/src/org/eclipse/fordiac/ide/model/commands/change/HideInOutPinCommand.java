/*******************************************************************************
 * Copyright (c) 2024 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Stemmer - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;

public class HideInOutPinCommand extends Command implements ScopedCommand {

	private final IInterfaceElement interfaceElement;
	private final boolean visible;
	private final boolean isInput;

	public HideInOutPinCommand(final IInterfaceElement interfaceElement, final boolean visible, final boolean isInput) {
		this.interfaceElement = Objects.requireNonNull(interfaceElement);
		this.visible = visible;
		this.isInput = isInput;
	}

	@Override
	public void execute() {
		getPin(isInput).setVisible(visible);
	}

	@Override
	public void redo() {
		execute();
	}

	@Override
	public void undo() {
		getPin(isInput).setVisible(!visible);
	}

	@Override
	public boolean canExecute() {
		return (interfaceElement instanceof VarDeclaration && ((VarDeclaration) interfaceElement).isInOutVar()
				&& interfaceElement.getInputConnections().isEmpty()
				&& interfaceElement.getOutputConnections().isEmpty());
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(getPin(isInput));
	}

	private IInterfaceElement getPin(final boolean isInput) {
		if (isInput) {
			return interfaceElement.getFBNetworkElement().getInterface().getInOutVars().stream()
					.filter(x -> x.getName().equals(Objects.toString(interfaceElement.getName()))).toList().getFirst();
		}
		return interfaceElement.getFBNetworkElement().getInterface().getOutMappedInOutVars().stream()
				.filter(x -> x.getName().equals(Objects.toString(interfaceElement.getName()))).toList().getFirst();
	}
}
