/*******************************************************************************
 * Copyright (c) 2026 Johannes Kepler Universiy Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.delete;

import java.util.Optional;

import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleECState;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.gef.commands.Command;

public class DeleteSimpleECStateCommand extends Command {

	private final SimpleFBType simpleFBType;
	private final Optional<SimpleECState> state;

	/**
	 * Command to delete a simple EC state
	 *
	 * @param eventInput   the event input for the state
	 * @param simpleFBType the type for which to delete the state
	 */
	public DeleteSimpleECStateCommand(final Event inputEvent, final SimpleFBType simpleFBType) {
		this.simpleFBType = simpleFBType;
		state = simpleFBType.getSimpleECStates().stream().filter(s -> s.getName().equals(inputEvent.getName()))
				.findFirst();
	}

	@Override
	public void execute() {
		redo();
	}

	@Override
	public boolean canExecute() {
		return state.isPresent();
	}

	@Override
	public void undo() {
		simpleFBType.getSimpleECStates().add(state.get());
	}

	@Override
	public void redo() {
		simpleFBType.getSimpleECStates().remove(state.get());
	}
}
