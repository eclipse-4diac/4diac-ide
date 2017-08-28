/*******************************************************************************
 * Copyright (c) 2008, 2009, 2015 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands;

import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.gef.commands.Command;

/**
 * The Class ChangeOutputCommand.
 */
public class ChangeOutputCommand extends Command {

	/** The ec action. */
	private final ECAction ecAction;

	/** The event. */
	private final Event event;

	/** The old event. */
	private Event oldEvent;

	/**
	 * Instantiates a new change output command.
	 * 
	 * @param action the action
	 * @param outputEvent the output event
	 */
	public ChangeOutputCommand(final ECAction action, final Event outputEvent) {
		super();
		this.ecAction = action;
		this.event = outputEvent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		oldEvent = ecAction.getOutput();
		redo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		ecAction.setOutput(oldEvent);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		ecAction.setOutput(event);
	}

}
