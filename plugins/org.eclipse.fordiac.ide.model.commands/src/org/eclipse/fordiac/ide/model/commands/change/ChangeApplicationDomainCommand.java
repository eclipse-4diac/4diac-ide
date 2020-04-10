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
public class ChangeApplicationDomainCommand extends ChangeIdentificationCommand {

	/** The new ApplicationDomain value. */
	private String newApplicationDomain;

	/** The old ApplicationDomain value. */
	private String oldApplicationDomain;

	public ChangeApplicationDomainCommand(LibraryElement type, final String newApplicationDomain) {
		super(type);
		this.newApplicationDomain = newApplicationDomain;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		oldApplicationDomain = getIdentification().getApplicationDomain();
		redo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		getIdentification().setApplicationDomain(oldApplicationDomain);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		getIdentification().setApplicationDomain(newApplicationDomain);
	}

}
