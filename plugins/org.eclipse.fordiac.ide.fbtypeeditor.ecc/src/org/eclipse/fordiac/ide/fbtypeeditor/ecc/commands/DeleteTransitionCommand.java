/*******************************************************************************
 * Copyright (c) 2008, 2009, 2013 Profactor GmbH, fortiss GmbH
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
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.gef.commands.Command;

/**
 * The Class DeleteTransitionCommand.
 */
public class DeleteTransitionCommand extends Command {

	/** The transition. */
	private final ECTransition transition;

	/** The parent. */
	private ECC parent;

	/** The source. */
	private ECState source;
	
	private int oldSourcePos;
	private int oldTransitionPos;

	/** The dest. */
	private ECState dest;

	/**
	 * Instantiates a new delete transition command.
	 * 
	 * @param transition the transition
	 */
	public DeleteTransitionCommand(final ECTransition transition) {
		super();
		this.transition = transition;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		parent = (ECC) transition.eContainer();
		oldTransitionPos = parent.getECTransition().indexOf(transition);

		source = transition.getSource();
		oldSourcePos = source.getOutTransitions().indexOf(transition);
		dest = transition.getDestination();

		transition.setSource(null);
		transition.setDestination(null);
		parent.getECTransition().remove(transition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		parent.getECTransition().add(oldTransitionPos, transition);
		transition.setSource(source);
		source.getOutTransitions().move(oldSourcePos, transition);
		transition.setDestination(dest);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		transition.setSource(null);
		transition.setDestination(null);
		parent.getECTransition().remove(transition);
	}

}
