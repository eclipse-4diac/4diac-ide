/*******************************************************************************
 * Copyright (c) 2011 TU Wien ACIN
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands;

import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.gef.commands.Command;

/**
 * The Class ChangeConditionCommand.
 */
public class ChangeConditionExpressionCommand extends Command {

	/** The transition. */
	private final ECTransition transition;

	/** The condition. */
	private final String conditionExpression;

	/** The old condition. */
	private String oldConditionExpression;

	/**
	 * Instantiates a new change condition command.
	 * 
	 * @param transition the transition
	 * @param conditionExpression the condition
	 */
	public ChangeConditionExpressionCommand(final ECTransition transition,
			final String conditionExpression) {
		super();
		this.transition = transition;
		this.conditionExpression = conditionExpression;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		oldConditionExpression = transition.getConditionExpression();
		redo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		transition.setConditionExpression(oldConditionExpression);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		transition.setConditionExpression(conditionExpression);
	}

}
