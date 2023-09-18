/*******************************************************************************
 * Copyright (c) 2012, 2014 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.commands.internal.ChangeCompilerInfoCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;

/** The Class ChangeCompilerInfoHeader. */
public class ChangeCompilerInfoHeaderCommand extends ChangeCompilerInfoCommand {
	/** The new ApplicationDomain value. */
	private final String newHeader;
	/** The old ApplicationDomain value. */
	private String oldHeader;

	public ChangeCompilerInfoHeaderCommand(final FBType type, final String newHeader) {
		super(type);
		this.newHeader = (null == newHeader) ? "" : newHeader; //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#execute() */
	@Override
	public void execute() {
		oldHeader = getCompilerInfo().getHeader();
		redo();
	}

	/* (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#undo() */
	@Override
	public void undo() {
		getCompilerInfo().setHeader(oldHeader);
	}

	/* (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#redo() */
	@Override
	public void redo() {
		getCompilerInfo().setHeader(newHeader);
	}
}