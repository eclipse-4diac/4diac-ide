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

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;

/**
 * The Class ChangeCommentCommand.
 */
public class ChangeStandardCommand extends ChangeIdentificationCommand {

	/** The new standard value. */
	private final String newStandard;

	/** The old standard value. */
	private String oldStandard;

	public ChangeStandardCommand(LibraryElement type, final String newStandard) {
		super(type);
		this.newStandard = (null == newStandard) ? "" : newStandard; //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		oldStandard = getIdentification().getStandard();
		redo();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		getIdentification().setStandard(oldStandard);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		getIdentification().setStandard(newStandard);
	}

}
