/*******************************************************************************
 * Copyright (c) 2008, 2009 Profactor GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands;

import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.gef.commands.Command;

/**
 * The Class ChangeAlgorithmCommand.
 */
public class ChangeAlgorithmCommand extends Command {

	/** The ec action. */
	private final ECAction ecAction;

	/** The algorithm. */
	private final Algorithm algorithm;

	/** The old algorithm. */
	private Algorithm oldAlgorithm;

	/**
	 * Instantiates a new change algorithm command.
	 * 
	 * @param action    the action
	 * @param algorithm the algorithm
	 */
	public ChangeAlgorithmCommand(final ECAction action, final Algorithm algorithm) {
		super();
		this.ecAction = action;
		this.algorithm = algorithm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		oldAlgorithm = ecAction.getAlgorithm();
		ecAction.setAlgorithm(algorithm);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		ecAction.setAlgorithm(oldAlgorithm);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		ecAction.setAlgorithm(algorithm);
	}

}
