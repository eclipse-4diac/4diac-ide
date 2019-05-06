/*******************************************************************************
 * Copyright (c) 2012 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;

/**
 * The Class ChangeDescriptionCommand.
 */
public class ChangeDescriptionCommand extends ChangeIdentificationCommand {
	
	/** The new Description value. */
	private String newDescription;

	/** The old Description value. */
	private String oldDescription;

	public ChangeDescriptionCommand(LibraryElement type,
			final String newDescription) {
		super(type);
		this.newDescription = newDescription;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		oldDescription = getIdentification().getDescription();
		redo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		getIdentification().setDescription(oldDescription);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		getIdentification().setDescription(newDescription);
	}

}
