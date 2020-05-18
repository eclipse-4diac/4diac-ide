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
 * change the author in a version info
 */
public class ChangeAuthorCommand extends Command {

	private VersionInfo versionInfo;

	/** The new ApplicationDomain value. */
	private String newAuthor;

	/** The old ApplicationDomain value. */
	private String oldAuthor;

	public ChangeAuthorCommand(final VersionInfo versionInfo, final String newAuthor) {
		super();
		this.versionInfo = versionInfo;
		this.newAuthor = (newAuthor == null) ? "" : newAuthor; //$NON-NLS-1$
	}

	@Override
	public void execute() {
		oldAuthor = versionInfo.getAuthor();
		setNewAuthor();
	}

	@Override
	public void undo() {
		versionInfo.setAuthor(oldAuthor);
	}

	@Override
	public void redo() {
		setNewAuthor();
	}

	private void setNewAuthor() {
		versionInfo.setAuthor(newAuthor);
	}

}
