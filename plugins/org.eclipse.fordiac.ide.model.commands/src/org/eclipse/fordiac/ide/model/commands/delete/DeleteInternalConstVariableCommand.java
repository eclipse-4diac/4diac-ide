/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Prankur Agarwal - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.delete;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.commands.internal.DeleteVariableCommand;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/** The Class CreateInternalConstVariableCommand. */
public class DeleteInternalConstVariableCommand extends DeleteVariableCommand {
	/** Instantiates a command to remove a variable from a list.
	 *
	 * @param type        the BasicFBType from which the internal const var is deleted
	 * @param varToDelete the var that should be deleted */
	public DeleteInternalConstVariableCommand(final BaseFBType type, final VarDeclaration varToDelete) {
		super(type, varToDelete);
	}

	@Override
	protected EList<VarDeclaration> getVariableList() {
		return ((BaseFBType) getType()).getInternalConstVars();
	}

}
