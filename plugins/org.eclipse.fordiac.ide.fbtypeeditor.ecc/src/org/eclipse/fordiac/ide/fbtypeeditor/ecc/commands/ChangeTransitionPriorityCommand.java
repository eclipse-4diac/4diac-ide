/*******************************************************************************
 * Copyright (c) 2015 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands;

import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.gef.commands.Command;

public class ChangeTransitionPriorityCommand extends Command {
	protected final ECTransition transition;
	protected ECState state;
	protected int index;
	protected boolean up;

	public ChangeTransitionPriorityCommand(final ECState state, final ECTransition transition, boolean up) {
		this.transition = transition;
		this.up = up;
		this.state = state;
		this.index = state.getOutTransitions().indexOf(transition);
	}

	@Override
	public boolean canExecute() {
		return state.getOutTransitions().size() > 1 && ( (up && index > 0) || (!up && index < state.getOutTransitions().size()));
	}

	@Override
	public void execute() {
		state.getOutTransitions().remove(state.getOutTransitions().indexOf(transition));
		state.getOutTransitions().add(up? (index - 1) : (index + 1), transition);
	}

	@Override
	public void redo() {
		state.getOutTransitions().remove(state.getOutTransitions().indexOf(transition));
		state.getOutTransitions().add(up? (index - 1) : (index + 1), transition);
	}

	@Override
	public void undo() {
		state.getOutTransitions().remove(state.getOutTransitions().indexOf(transition));
		state.getOutTransitions().add(index, transition);
	}
}
