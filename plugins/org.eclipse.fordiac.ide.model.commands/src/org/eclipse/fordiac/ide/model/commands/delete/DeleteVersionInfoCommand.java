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
package org.eclipse.fordiac.ide.model.commands.delete;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.gef.commands.Command;

/**
 * The Class AddNewVersionInfoCommand.
 */
public class DeleteVersionInfoCommand extends Command {
	
	/** The type. */
	private LibraryElement type;

	/** The new version info. */
	private VersionInfo info;
	
	/** The old index. */
	private int oldIndex;

	/**
	 * Instantiates a new change comment command.
	 * 
	 * @param interfaceElement the interface element
	 * @param comment the comment
	 */
	public DeleteVersionInfoCommand(final LibraryElement type, final VersionInfo info) {
		super();
		this.type = type;
		this.info = info;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		oldIndex = type.getVersionInfo().indexOf(info);		
		redo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		type.getVersionInfo().add(oldIndex, info);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		type.getVersionInfo().remove(info);
	}

}
