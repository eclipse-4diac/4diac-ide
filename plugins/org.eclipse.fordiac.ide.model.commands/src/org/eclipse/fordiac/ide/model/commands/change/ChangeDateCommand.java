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
 * The Class ChangeDateCommand.
 */
public class ChangeDateCommand extends Command {
	
	VersionInfo versionInfo;
	
	/** The new ApplicationDomain value. */
	private String newDate;

	/** The old ApplicationDomain value. */
	private String oldDate;

	public ChangeDateCommand(final VersionInfo VersionInfo, final String newDate) {
		super();
		this.versionInfo = VersionInfo;
		this.newDate = newDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		oldDate = versionInfo.getDate();
		redo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		versionInfo.setDate(oldDate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		versionInfo.setDate(newDate);
	}

}
