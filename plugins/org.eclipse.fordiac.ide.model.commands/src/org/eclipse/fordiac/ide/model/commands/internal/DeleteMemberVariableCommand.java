/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr
 *       - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.internal;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteVariableCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class DeleteMemberVariableCommand extends DeleteVariableCommand {
	/**
	 * Instantiates a command to remove a variable from a struct.
	 *
	 * @param type the StructuredType from which the member variable is deleted
	 * @param var  the var that should be deleted
	 */
	public DeleteMemberVariableCommand(final StructuredType type, final VarDeclaration var) {
		super(type, var);
	}

	@Override
	protected EList<VarDeclaration> getVariableList() {
		return ((StructuredType) getType()).getMemberVariables();
	}

}
