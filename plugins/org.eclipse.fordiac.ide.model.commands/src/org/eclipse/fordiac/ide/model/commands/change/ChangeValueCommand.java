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

import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class ChangeValueCommand extends AbstractChangeInterfaceElementCommand {
	private VarDeclaration mirroredVar; // the variable of the mapped entity
	private final String newValue;
	private String oldValue;

	public ChangeValueCommand(final VarDeclaration variable, final String value) {
		super(variable);
		newValue = (null == value) ? "" : value; //$NON-NLS-1$ //always ensure a valid value
	}

	@Override
	public boolean canExecute() {
		return getInterfaceElement() != null && getInterfaceElement().getType() != null;
	}

	@Override
	protected void doExecute() {
		final VarDeclaration variable = getInterfaceElement();
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
	}

	@Override
	protected void doUndo() {
		final VarDeclaration variable = getInterfaceElement();
		variable.getValue().setValue(oldValue);
		setMirroredVar(oldValue);
	}

	@Override
	protected void doRedo() {
		final VarDeclaration variable = getInterfaceElement();
		variable.getValue().setValue(newValue);
		setMirroredVar(newValue);
	}

	private VarDeclaration getMirroredVariable() {
		final VarDeclaration variable = getInterfaceElement();
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

	@Override
	public VarDeclaration getInterfaceElement() {
		return (VarDeclaration) super.getInterfaceElement();
	}
}