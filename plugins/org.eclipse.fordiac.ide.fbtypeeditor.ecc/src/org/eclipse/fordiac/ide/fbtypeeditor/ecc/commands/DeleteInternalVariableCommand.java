/*******************************************************************************
 * Copyright (c) 2012, 2015 TU Wien ACIN, fortiss GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands;

import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;

/**
 * The Class CreateInternalVariableCommand.
 */
public class DeleteInternalVariableCommand extends Command {

	/** The fb type. */
	private final BasicFBType fbType;

	private VarDeclaration varToDelete;

	/** The old index. */
	private int oldIndex;

	/**
	 * Instantiates a new creates the input variable command.
	 * 
	 * @param dataType the data type
	 * @param fbType   the fb type
	 */
	public DeleteInternalVariableCommand(final BasicFBType fbType, final VarDeclaration var) {
		this.fbType = fbType;
		this.varToDelete = var;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		oldIndex = fbType.getInternalVars().indexOf(varToDelete);

		redo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		fbType.getInternalVars().add(oldIndex, varToDelete);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		fbType.getInternalVars().remove(varToDelete);
	}
}
