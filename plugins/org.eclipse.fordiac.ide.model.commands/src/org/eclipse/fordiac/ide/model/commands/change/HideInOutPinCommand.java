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
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;

public class HideInOutPinCommand extends Command implements ScopedCommand {

	private final VarDeclaration inputVarInOut;
	private final boolean visible;

	public HideInOutPinCommand(final VarDeclaration inputVarInOut, final boolean visible) {
		this.inputVarInOut = Objects.requireNonNull(inputVarInOut);
		this.visible = visible;
	}

	@Override
	public void execute() {
		inputVarInOut.setVisible(visible);
	}

	@Override
	public void redo() {
		execute();
	}

	@Override
	public void undo() {
		inputVarInOut.setVisible(!visible);
	}

	@Override
	public boolean canExecute() {
		return (inputVarInOut.isInOutVar() && inputVarInOut.getInputConnections().isEmpty()
				&& inputVarInOut.getOutputConnections().isEmpty());
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(inputVarInOut);
	}
}