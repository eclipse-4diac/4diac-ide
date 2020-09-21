/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2019 Johannes Keppler University Linz
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.ErrorMessenger;
import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;

public class ChangeValueCommand extends Command {

	private static final String CHANGE = Messages.ChangeValueCommand_LABEL_ChangeValue;
	private VarDeclaration var;
	private VarDeclaration mirroredVar; // the variable of the mapped entity
	private String newValue;
	private String oldValue;

	public ChangeValueCommand(VarDeclaration var, String value) {
		this.var = var;
		newValue = (null == value) ? "" : value; //$NON-NLS-1$ //always ensure a valid value
	}

	@Override
	public boolean canExecute() {
		if ((null != var) && (null != var.getType()) && ("ANY".equals(var.getTypeName())) //$NON-NLS-1$
				&& (null != newValue)) {
			if ((!newValue.equals("")) && (!newValue.contains("#"))) { //$NON-NLS-1$ //$NON-NLS-2$
				ErrorMessenger.popUpErrorMessage("Constant Values are not allowed on ANY Input!"); //$NON-NLS-1$
				return false;
			}
		}
		return super.canExecute();
	}

	public ChangeValueCommand() {
		super(CHANGE);
	}

	@Override
	public void execute() {
		mirroredVar = getMirroredVariable();
		if (null == var.getValue()) {
			var.setValue(LibraryElementFactory.eINSTANCE.createValue());
			if (null != mirroredVar) {
				mirroredVar.setValue(LibraryElementFactory.eINSTANCE.createValue());
			}
			oldValue = ""; //$NON-NLS-1$
		} else {
			oldValue = var.getValue().getValue();
		}
		var.getValue().setValue(newValue);
		setMirroredVar(newValue);
	}

	@Override
	public void undo() {
		var.getValue().setValue(oldValue);
		setMirroredVar(oldValue);
	}

	@Override
	public void redo() {
		var.getValue().setValue(newValue);
		setMirroredVar(newValue);
	}

	private VarDeclaration getMirroredVariable() {
		if (null != var.getFBNetworkElement() && var.getFBNetworkElement().isMapped()) {
			FBNetworkElement opposite = var.getFBNetworkElement().getOpposite();
			if (null != opposite) {
				IInterfaceElement element = opposite.getInterfaceElement(var.getName());
				if (element instanceof VarDeclaration) {
					return (VarDeclaration) element;
				}
			}
		}
		return null;
	}

	private void setMirroredVar(String val) {
		if (null != mirroredVar) {
			mirroredVar.getValue().setValue(val);
		}
	}
}