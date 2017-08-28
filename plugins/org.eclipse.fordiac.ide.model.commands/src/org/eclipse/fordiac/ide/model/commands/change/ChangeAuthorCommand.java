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

import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.gef.commands.Command;

/**
 * The Class ChangeAuthorCommand.
 */
public class ChangeAuthorCommand extends Command {
	
	VersionInfo versionInfo;
	
	/** The new ApplicationDomain value. */
	private String newAuthor;

	/** The old ApplicationDomain value. */
	private String oldAuthor;

	public ChangeAuthorCommand(final VersionInfo VersionInfo, final String newAuthor) {
		super();
		this.versionInfo = VersionInfo;
		this.newAuthor = newAuthor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		oldAuthor = versionInfo.getAuthor();
		redo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		versionInfo.setAuthor(oldAuthor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		versionInfo.setAuthor(newAuthor);
	}

}
