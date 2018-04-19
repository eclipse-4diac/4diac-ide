/*******************************************************************************
 * Copyright (c) 2015, 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands;

import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.gef.commands.Command;

public class ChangeTransitionPriorityCommand extends Command {
	private final ECTransition transition;
	private final ECState state;
	private final boolean up;
	private final int oldStateIndex;
	private final int newStateIndex;
	private final int oldECCTransitionIndex;
	private int newECCTransitionIndex;

	public ChangeTransitionPriorityCommand(final ECState state, final ECTransition transition, boolean up) {
		this.transition = transition;
		this.state = state;
		this.up = up;
		this.oldStateIndex = state.getOutTransitions().indexOf(transition);
		newStateIndex = oldStateIndex + (up ? -1 : 1); 
		oldECCTransitionIndex = transition.getECC().getECTransition().indexOf(transition);
	}

	@Override
	public boolean canExecute() {
		return state.getOutTransitions().size() > 1 && ((up && oldStateIndex > 0) || 
				(!up && oldStateIndex < (state.getOutTransitions().size() - 1)));
	}

	@Override
	public void execute() {
		//it is better to calculate these values here as then we have the checks in canExecute performed and know new index is valid
		ECTransition referenceTransition = state.getOutTransitions().get(newStateIndex);
		newECCTransitionIndex = transition.getECC().getECTransition().indexOf(referenceTransition); 
		redo();
	}

	@Override
	public void redo() {
		//change order in state model
		state.getOutTransitions().move(newStateIndex, transition);
		//change order in overall transition list
		transition.getECC().getECTransition().move(newECCTransitionIndex, transition);
	}

	@Override
	public void undo() {
		state.getOutTransitions().move(oldStateIndex, transition);
		transition.getECC().getECTransition().move(oldECCTransitionIndex, transition);
	}
}
