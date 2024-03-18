/*******************************************************************************
 * Copyright (c) 2015 - 2024 fortiss GmbH, Johannes Kepler University Linz,
 * 							 Primetals Technologies Germany GmbH,
 * 							 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - added mulitline selection and code cleanup.
 *   Bianca Wiesmayr - extract Table creation, improve insertion
 *   Alois Zoitl - extracted helper for ComboCellEditors that unfold on activation
 *   Daniel Lindhuber - added copy and paste
 *   Bianca Wiesmayr - extracted super class for simple and basic FB, added context menu
 *   Daniel Lindhuber - changed type selection to search field
 *   Alexander Lumplecker
 *     - changed AddDeleteWidget to AddDeleteReorderListWidget
 *     - added ChangeVariableOrderCommand
 *   Sebastian Hollersbacher - change to nebula NatTable
 *   Martin Jobst - add initial value cell editor support
 *   Prankur Agarwal - create a super class and change it's implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.commands.create.CreateInternalVariableCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInternalVariableCommand;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class InternalVarsSection extends AbstractInternalVarsSection {

	@Override
	protected CreationCommand newCreateCommand(final Object refElement) {
		return new CreateInternalVariableCommand(getType(), getInsertionIndex(), getName(), getDataType());
	}

	@Override
	protected Command newDeleteCommand(final Object refElement) {
		return new DeleteInternalVariableCommand(getType(), (VarDeclaration) refElement);
	}

	@Override
	protected EList<VarDeclaration> getVarList() {
		return getType().getInternalVars();
	}

	@Override
	public Object getEntry(final int index) {
		return getType().getInternalVars().get(index);
	}

	@Override
	public void removeEntry(final Object entry, final CompoundCommand cmd) {
		if (entry instanceof final VarDeclaration varEntry) {
			cmd.add(new DeleteInternalVariableCommand(getType(), varEntry));
		}
	}
}
