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
 *   Bianca Wiesmayr
 *     - extracted code from DeleteInternalVariableCommand
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.delete;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;

/**
 * abstract command to delete a variable from a list of a LibraryElement
 */
public abstract class DeleteVariableCommand extends Command {

	/** The type to whose list the new variable is added. */
	private final LibraryElement type;

	/** The variable that is deleted */
	private VarDeclaration varToDelete;

	/** The old index. */
	private int oldIndex;

	/**
	 * Instantiates a command to remove a variable from a list.
	 *
	 * @param type the type from which the var is deleted
	 * @param var  the var that should be deleted
	 */
	protected DeleteVariableCommand(final LibraryElement type, final VarDeclaration var) {
		this.type = type;
		this.varToDelete = var;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		oldIndex = getVariableList().indexOf(varToDelete);
		redo();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		getVariableList().add(oldIndex, varToDelete);
	}

	/**
	 * subclasses must provide the list, from which the variable shall be removed
	 *
	 * @return EList<VarDeclaration> the list containing variable declarations
	 */
	protected abstract EList<VarDeclaration> getVariableList();

	protected LibraryElement getType() {
		return type;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		getVariableList().remove(varToDelete);
	}
}
