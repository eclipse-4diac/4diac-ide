/*******************************************************************************
 * Copyright (c) 2012, 2015 TU Wien ACIN, fortiss GmbH
 *               2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr
 *     - extracted general code to DeleteVariableCommand
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.delete;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.commands.internal.DeleteVariableCommand;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/**
 * The Class CreateInternalVariableCommand.
 */
public class DeleteInternalVariableCommand extends DeleteVariableCommand {
	/**
	 * Instantiates a command to remove a variable from a list.
	 *
	 * @param type the BasicFBType from which the internal var is deleted
	 * @param var  the var that should be deleted
	 */
	public DeleteInternalVariableCommand(final BaseFBType type, final VarDeclaration var) {
		super(type, var);
	}

	@Override
	protected EList<VarDeclaration> getVariableList() {
		return ((BaseFBType) getType()).getInternalVars();
	}

}
