/*******************************************************************************
 * Copyright (c) 2008 - 2023 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 *                           Johannes Keppler University Linz,
 *                           Primetals Technologies Austria GmbH
 *                           Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - removed editor check from canUndo
 *               - added value validation and error marker handling
 *   Martin Jobst - refactor marker handling
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.commands.util.FordiacMarkerCommandHelper;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.errormessages.ErrorMessenger;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class ChangeValueCommand extends Command {

	private static final String CHANGE = Messages.ChangeValueCommand_LABEL_ChangeValue;
	private VarDeclaration variable;
	private VarDeclaration mirroredVar; // the variable of the mapped entity
	private String newValue;
	private String oldValue;
	private String newErrorMessage;
	private String oldErrorMessage;
	private final CompoundCommand errorMarkerUpdateCmds = new CompoundCommand();

	public ChangeValueCommand(final VarDeclaration variable, final String value) {
		this.variable = variable;
		newValue = (null == value) ? "" : value; //$NON-NLS-1$ //always ensure a valid value
	}

	@Override
	public boolean canExecute() {
		return variable != null && variable.getType() != null;
	}

	public ChangeValueCommand() {
		super(CHANGE);
	}

	@Override
	public void execute() {
		mirroredVar = getMirroredVariable();
		if (null == variable.getValue()) {
			variable.setValue(LibraryElementFactory.eINSTANCE.createValue());
			if (null != mirroredVar) {
				mirroredVar.setValue(LibraryElementFactory.eINSTANCE.createValue());
			}
			oldValue = ""; //$NON-NLS-1$
			oldErrorMessage = ""; //$NON-NLS-1$
		} else {
			oldValue = variable.getValue().getValue();
			oldErrorMessage = variable.getValue().getErrorMessage();
			if (oldErrorMessage == null) {
				oldErrorMessage = ""; //$NON-NLS-1$
			}
		}
		variable.getValue().setValue(newValue);
		newErrorMessage = VariableOperations.validateValue(variable);
		variable.getValue().setErrorMessage(newErrorMessage);
		setMirroredVar(newValue);
		handleErrorMarker();
		errorMarkerUpdateCmds.execute();
	}

	private void handleErrorMarker() {
		if (!oldErrorMessage.isBlank()) {
			errorMarkerUpdateCmds.add(FordiacMarkerCommandHelper
					.newDeleteMarkersCommand(FordiacMarkerHelper.findMarkers(variable.getValue())));
		}
		if (!newErrorMessage.isBlank()) {
			ErrorMessenger.popUpErrorMessage(newErrorMessage);
			errorMarkerUpdateCmds.add(FordiacMarkerCommandHelper.newCreateMarkersCommand(
					ErrorMarkerBuilder.createErrorMarkerBuilder(newErrorMessage).setTarget(variable.getValue())));
		}
	}

	@Override
	public void undo() {
		variable.getValue().setValue(oldValue);
		variable.getValue().setErrorMessage(oldErrorMessage);
		setMirroredVar(oldValue);
		errorMarkerUpdateCmds.undo();
	}

	@Override
	public void redo() {
		variable.getValue().setValue(newValue);
		variable.getValue().setErrorMessage(newErrorMessage);
		setMirroredVar(newValue);
		errorMarkerUpdateCmds.redo();
	}

	private VarDeclaration getMirroredVariable() {
		if ((null != variable.getFBNetworkElement()) && variable.getFBNetworkElement().isMapped()) {
			final FBNetworkElement opposite = variable.getFBNetworkElement().getOpposite();
			if (null != opposite) {
				final IInterfaceElement element = opposite.getInterfaceElement(variable.getName());
				if (element instanceof VarDeclaration) {
					return (VarDeclaration) element;
				}
			}
		}
		return null;
	}

	private void setMirroredVar(final String val) {
		if (null != mirroredVar) {
			mirroredVar.getValue().setValue(val);
		}
	}
}