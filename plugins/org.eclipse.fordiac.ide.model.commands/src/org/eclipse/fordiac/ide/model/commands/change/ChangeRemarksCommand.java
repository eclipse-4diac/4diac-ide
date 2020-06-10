/*******************************************************************************
 * Copyright (c) 2012 fortiss GmbH
 *               2020 Johannes Kepler University
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
 *   Ernst Blecha
 *     - fix behaviour on passing null
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.gef.commands.Command;

/**
 * The Class ChangeRemarksCommand.
 */
public class ChangeRemarksCommand extends Command {

	private VersionInfo versionInfo;

	/** The new ApplicationDomain value. */
	private String newRemarks;

	/** The old ApplicationDomain value. */
	private String oldRemarks;

	public ChangeRemarksCommand(final VersionInfo versionInfo, final String newRemarks) {
		super();
		this.versionInfo = versionInfo;
		this.newRemarks = (newRemarks == null) ? "" : newRemarks; //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		oldRemarks = versionInfo.getRemarks();
		redo();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		versionInfo.setRemarks(oldRemarks);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		versionInfo.setRemarks(newRemarks);
	}

}
