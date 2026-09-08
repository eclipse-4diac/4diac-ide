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
package org.eclipse.fordiac.ide.model.commands.create;

import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleECState;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.gef.commands.Command;

public class CreateSimpleECStateCommand extends Command {

	private final Event inputEvent;
	private final SimpleFBType simpleFBType;
	private SimpleECState state;

	/**
	 * Command to create a new simple EC state
	 *
	 * @param eventInput   the event input for the state
	 * @param simpleFBType the type for which to create the state
	 */
	public CreateSimpleECStateCommand(final Event inputEvent, final SimpleFBType simpleFBType) {
		this.inputEvent = inputEvent;
		this.simpleFBType = simpleFBType;
	}

	@Override
	public void execute() {
		state = LibraryElementFactory.eINSTANCE.createSimpleECState();
		state.setInputEvent(inputEvent);
		state.setName(inputEvent.getName());
		addState();
	}

	@Override
	public void undo() {
		simpleFBType.getSimpleECStates().remove(state);
	}

	@Override
	public void redo() {
		addState();
	}

	private void addState() {
		simpleFBType.getSimpleECStates().add(state);
	}
}
