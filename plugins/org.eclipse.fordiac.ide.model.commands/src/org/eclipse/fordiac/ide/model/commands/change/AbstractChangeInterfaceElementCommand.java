/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.commands.util.FordiacMarkerCommandHelper;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.errormessages.ErrorMessenger;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public abstract class AbstractChangeInterfaceElementCommand extends Command {
	private final IInterfaceElement interfaceElement;
	private String newValueErrorMessage = ""; //$NON-NLS-1$
	private String oldValueErrorMessage = ""; //$NON-NLS-1$
	private final CompoundCommand errorMarkerUpdateCmds = new CompoundCommand();

	protected AbstractChangeInterfaceElementCommand(final IInterfaceElement interfaceElement) {
		this.interfaceElement = interfaceElement;
	}

	@Override
	public final void execute() {
		doExecute();
		if (interfaceElement instanceof final VarDeclaration varDeclaration && varDeclaration.getValue() != null) {
			validateValue(varDeclaration);
		}
		errorMarkerUpdateCmds.execute();
	}

	private void validateValue(final VarDeclaration variable) {
		oldValueErrorMessage = variable.getValue().getErrorMessage();
		if (oldValueErrorMessage == null) {
			oldValueErrorMessage = ""; //$NON-NLS-1$
		}
		if (!oldValueErrorMessage.isBlank()) {
			errorMarkerUpdateCmds.add(FordiacMarkerCommandHelper.newDeleteMarkersCommand(
					FordiacMarkerHelper.findMarkers(variable.getValue(), FordiacErrorMarker.INITIAL_VALUE_MARKER)));
		}

		newValueErrorMessage = VariableOperations.validateValue(variable);
		variable.getValue().setErrorMessage(newValueErrorMessage);
		if (!newValueErrorMessage.isBlank()) {
			ErrorMessenger.popUpErrorMessage(newValueErrorMessage);
			errorMarkerUpdateCmds.add(FordiacMarkerCommandHelper
					.newCreateMarkersCommand(ErrorMarkerBuilder.createErrorMarkerBuilder(newValueErrorMessage)
							.setType(FordiacErrorMarker.INITIAL_VALUE_MARKER).setTarget(variable.getValue())));
		}
	}

	@Override
	public final void undo() {
		errorMarkerUpdateCmds.undo();
		if (interfaceElement instanceof final VarDeclaration varDeclaration && varDeclaration.getValue() != null) {
			varDeclaration.getValue().setErrorMessage(oldValueErrorMessage);
		}
		doUndo();
	}

	@Override
	public final void redo() {
		doRedo();
		if (interfaceElement instanceof final VarDeclaration varDeclaration && varDeclaration.getValue() != null) {
			varDeclaration.getValue().setErrorMessage(newValueErrorMessage);
		}
		errorMarkerUpdateCmds.redo();
	}

	protected abstract void doExecute();

	protected abstract void doRedo();

	protected abstract void doUndo();

	public IInterfaceElement getInterfaceElement() {
		return interfaceElement;
	}

	protected CompoundCommand getErrorMarkerUpdateCmds() {
		return errorMarkerUpdateCmds;
	}
}
