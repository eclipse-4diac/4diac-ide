/*******************************************************************************
 * Copyright (c) Philipps University of Marburg. All rights reserved.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Philipps University of Marburg - initial API and implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.commands.CompoundCommand;

/**
 * Class used for providing a command that provides a preview of the rfactored
 * EMF model.
 *
 * @generated NOT
 * @author Florian Mantz
 */
public class PreviewCommand extends AbstractCommand {

	/**
	 * Command that was the origin of the PreviewCommand.
	 */
	final CompoundCommand command;

	/**
	 * Root element of the EMF model.
	 */
	private final EObject root;

	/**
	 * Copy of the root element of the model (temporary model).
	 */
	private EObject rootCopy;

	/**
	 * Default constructor using the origin RefactoringCommand and the root of the
	 * EMF model.
	 *
	 * @param command Origin RefactoringCommand.
	 * @param root    Root element of the EMF model.
	 */
	public PreviewCommand(final CompoundCommand command, final EObject root) {
		this.command = command;
		this.root = root;
	}

	/**
	 * @see org.eclipse.emf.common.command.Command#execute()
	 */
	@Override
	public void execute() {
		this.command.execute();
		rootCopy = this.generateRootCopy();
		this.command.undo();
	}

	/**
	 * Generates a copy of the EMF model presented by the root element.
	 *
	 * @return Copy of the EMF model presented by the root element.
	 */
	private EObject generateRootCopy() {
		// final Copier copier = new Copier();
		return EcoreUtil.copy(this.root);
		// final EObject rootCopy = copier.copy(this.root);
		// copier.copyReferences();
		// return rootCopy;
	}

	/**
	 * @see org.eclipse.emf.common.command.Command#redo()
	 */
	@Override
	public void redo() {
		// do nothing
	}

	/**
	 * @see org.eclipse.emf.common.command.AbstractCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return true;
	}

	/**
	 * @see org.eclipse.emf.common.command.AbstractCommand#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return false; // Important
	}

	/**
	 * Gets a copy of the root element of the model (temporary model).
	 *
	 * @return Copy of the root element of the model (temporary model).
	 */
	public EObject getRootCopy() {
		return rootCopy;
	}

}
