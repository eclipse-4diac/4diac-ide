/*******************************************************************************
 * Copyright (c) 2008 - 2021 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 *                           Johannes Keppler University Linz,
 *                           Primetals Technologies Austria GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.dataimport.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.helpers.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.validation.ValueValidator;
import org.eclipse.fordiac.ide.ui.errormessages.ErrorMessenger;
import org.eclipse.gef.commands.Command;

public class ChangeValueCommand extends Command {

	private static final String CHANGE = Messages.ChangeValueCommand_LABEL_ChangeValue;
	private VarDeclaration variable;
	private VarDeclaration mirroredVar; // the variable of the mapped entity
	private String newValue;
	private String oldValue;
	private ErrorMarkerBuilder errorMarker;
	private String validationMsg = "";

	public ChangeValueCommand(final VarDeclaration variable, final String value) {
		this.variable = variable;
		newValue = (null == value) ? "" : value; //$NON-NLS-1$ //always ensure a valid value
	}

	@Override
	public boolean canExecute() {
		if ((variable == null) || (variable.getType() == null)) {
			return false;
		}
		isValid(newValue);
		return true;
	}

	private boolean isValid(final String value) {
		if (!value.isBlank()) {
			// if we have a non empty value check if it is a valid literal
			validationMsg = ValueValidator.validateValue(variable, value);
			if ((validationMsg != null) && (!validationMsg.trim().isEmpty())) {
				ErrorMessenger.popUpErrorMessage(validationMsg);
				return false;
			}
		}
		return true;
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
		} else {
			oldValue = variable.getValue().getValue();
		}
		variable.getValue().setValue(newValue);
		setMirroredVar(newValue);
		handleErrorMarker(newValue);
	}

	private void handleErrorMarker(final String value) {
		if (isValid(value)) {
			deleteErrorMarker();
		} else {
			createErrorMarker();
		}
	}

	@Override
	public void undo() {
		variable.getValue().setValue(oldValue);
		setMirroredVar(oldValue);
		handleErrorMarker(oldValue);
	}

	@Override
	public void redo() {
		variable.getValue().setValue(newValue);
		setMirroredVar(newValue);
		handleErrorMarker(newValue);
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

	private void deleteErrorMarker() {
		if (variable.getValue().hasError()) {
			errorMarker = ErrorMarkerBuilder.deleteErrorMarker(variable.getValue());
		}
	}

	private void createErrorMarker() {
		if (!variable.getValue().hasError()) {
			if (errorMarker == null) {
				errorMarker = FordiacMarkerHelper.createValueErrorMarkerBuilder(validationMsg, variable.getValue(), 0);
			}
			errorMarker.createMarkerInFile();
		}
	}
}