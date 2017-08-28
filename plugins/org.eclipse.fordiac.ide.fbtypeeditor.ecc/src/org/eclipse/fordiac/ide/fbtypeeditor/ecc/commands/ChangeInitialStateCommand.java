/*******************************************************************************
 * Copyright (c) 2008, 2009, 2015 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands;

import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.gef.commands.Command;

/**
 * The Class ChangeInitialStateCommand.
 */
public class ChangeInitialStateCommand extends Command {

	/** The ecc. */
	private final ECC ecc;

	/** The state. */
	private final ECState state;

	/** The old state. */
	private ECState oldState;

	/**
	 * Instantiates a new change initial state command.
	 * 
	 * @param ecc the ecc
	 * @param state the state
	 */
	public ChangeInitialStateCommand(final ECState state) {
		super();
		this.ecc = (ECC)state.eContainer();
		this.state = state;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		oldState = ecc.getStart();
		ecc.setStart(state);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		ecc.setStart(oldState);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		ecc.setStart(state);
	}

}
