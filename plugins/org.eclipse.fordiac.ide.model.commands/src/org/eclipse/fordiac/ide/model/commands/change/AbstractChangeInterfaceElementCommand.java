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
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.errormessages.ErrorMessenger;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public abstract class AbstractChangeInterfaceElementCommand extends Command {
	private final IInterfaceElement interfaceElement;
	private String newValueErrorMessage = ""; //$NON-NLS-1$
	private String oldValueErrorMessage = ""; //$NON-NLS-1$
	private String newArraySizeErrorMessage = ""; //$NON-NLS-1$
	private String oldArraySizeErrorMessage = ""; //$NON-NLS-1$
	private final CompoundCommand errorMarkerUpdateCmds = new CompoundCommand();

	protected AbstractChangeInterfaceElementCommand(final IInterfaceElement interfaceElement) {
		this.interfaceElement = interfaceElement;
	}

	@Override
	public boolean canExecute() {
		return interfaceElement != null;
	}

	@Override
	public final void execute() {
		doExecute();
		if (interfaceElement instanceof final VarDeclaration varDeclaration) {
			validateArraySize(varDeclaration);
			validateValue(varDeclaration);
		}
		errorMarkerUpdateCmds.execute();
	}

	private void validateValue(final VarDeclaration variable) {
		oldValueErrorMessage = getValueErrorMessage();
		if (!oldValueErrorMessage.isBlank()) {
			errorMarkerUpdateCmds.add(FordiacMarkerCommandHelper.newDeleteMarkersCommand(
					FordiacMarkerHelper.findMarkers(variable.getValue(), FordiacErrorMarker.INITIAL_VALUE_MARKER)));
		}

		if (newArraySizeErrorMessage.isBlank()) {
			newValueErrorMessage = VariableOperations.validateValue(variable);
			setValueErrorMessage(newValueErrorMessage);
			if (!newValueErrorMessage.isBlank()) {
				ErrorMessenger.popUpErrorMessage(newValueErrorMessage);
				errorMarkerUpdateCmds.add(FordiacMarkerCommandHelper
						.newCreateMarkersCommand(ErrorMarkerBuilder.createErrorMarkerBuilder(newValueErrorMessage)
								.setType(FordiacErrorMarker.INITIAL_VALUE_MARKER).setTarget(variable.getValue())));
			}
		}
	}

	private void validateArraySize(final VarDeclaration variable) {
		oldArraySizeErrorMessage = getArraySizeErrorMessage();
		if (!oldArraySizeErrorMessage.isBlank()) {
			errorMarkerUpdateCmds.add(FordiacMarkerCommandHelper.newDeleteMarkersCommand(FordiacMarkerHelper
					.findMarkers(variable.getArraySize(), FordiacErrorMarker.TYPE_DECLARATION_MARKER)));
		}

		newArraySizeErrorMessage = VariableOperations.validateType(variable);
		setArraySizeErrorMessage(newArraySizeErrorMessage);
		if (!newArraySizeErrorMessage.isBlank()) {
			ErrorMessenger.popUpErrorMessage(newArraySizeErrorMessage);
			errorMarkerUpdateCmds.add(FordiacMarkerCommandHelper
					.newCreateMarkersCommand(ErrorMarkerBuilder.createErrorMarkerBuilder(newArraySizeErrorMessage)
							.setType(FordiacErrorMarker.TYPE_DECLARATION_MARKER).setTarget(variable.getArraySize())));
		}
	}

	@Override
	public final void undo() {
		errorMarkerUpdateCmds.undo();
		setValueErrorMessage(oldValueErrorMessage);
		setArraySizeErrorMessage(oldArraySizeErrorMessage);
		doUndo();
	}

	@Override
	public final void redo() {
		doRedo();
		setValueErrorMessage(newValueErrorMessage);
		setArraySizeErrorMessage(newArraySizeErrorMessage);
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

	protected String getValueErrorMessage() {
		if (interfaceElement instanceof final VarDeclaration varDeclaration && varDeclaration.getValue() != null) {
			final String message = varDeclaration.getValue().getErrorMessage();
			if (message != null) {
				return message;
			}
		}
		return ""; //$NON-NLS-1$
	}

	protected void setValueErrorMessage(final String message) {
		if (interfaceElement instanceof final VarDeclaration varDeclaration && varDeclaration.getValue() != null) {
			varDeclaration.getValue().setErrorMessage(message);
		}
	}

	protected String getArraySizeErrorMessage() {
		if (interfaceElement instanceof final VarDeclaration varDeclaration && varDeclaration.getArraySize() != null) {
			final String message = varDeclaration.getArraySize().getErrorMessage();
			if (message != null) {
				return message;
			}
		}
		return ""; //$NON-NLS-1$
	}

	protected void setArraySizeErrorMessage(final String message) {
		if (interfaceElement instanceof final VarDeclaration varDeclaration && varDeclaration.getArraySize() != null) {
			varDeclaration.getArraySize().setErrorMessage(message);
		}
	}

	protected boolean isSubAppPinAndConnected() {
		return interfaceElement.getFBNetworkElement() instanceof SubApp
				&& (!interfaceElement.getInputConnections().isEmpty()
						|| !interfaceElement.getOutputConnections().isEmpty());
	}
}
