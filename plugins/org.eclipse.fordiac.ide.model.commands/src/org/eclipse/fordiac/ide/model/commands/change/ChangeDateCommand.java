/*******************************************************************************
 * Copyright (c) 2012 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
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
	
	private VersionInfo versionInfo;
	
	/** The new ApplicationDomain value. */
	private String newDate;

	/** The old ApplicationDomain value. */
	private String oldDate;

	public ChangeDateCommand(final VersionInfo versionInfo, final String newDate) {
		super();
		this.versionInfo = versionInfo;
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
