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

import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.gef.commands.Command;

public class ChangeActionOrderCommand extends Command {
	private final ECAction action;
	private ECState state;
	private int index;
	private boolean up;

	public ChangeActionOrderCommand(final ECState state, final ECAction action, boolean up) {
		this.action = action;
		this.up = up;
		this.state = state;
		this.index = state.getECAction().indexOf(action);
	}

	@Override
	public boolean canExecute() {
		return state.getECAction().size() > 1 && ( 
				(up && index > 0) || (!up && index < (state.getECAction().size()-1)));
	}

	@Override
	public void execute() {
		state.getECAction().remove(state.getECAction().indexOf(action));
		state.getECAction().add(up? (index - 1) : (index + 1), action);
	}

	@Override
	public void redo() {
		state.getECAction().remove(state.getECAction().indexOf(action));
		state.getECAction().add(up? (index - 1) : (index + 1), action);
	}

	@Override
	public void undo() {
		state.getECAction().remove(state.getECAction().indexOf(action));
		state.getECAction().add(index, action);
	}
}
