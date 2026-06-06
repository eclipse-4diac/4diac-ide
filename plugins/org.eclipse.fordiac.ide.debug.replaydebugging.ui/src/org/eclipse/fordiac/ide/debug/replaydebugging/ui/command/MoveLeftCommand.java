/*******************************************************************************
 * Copyright (c) 2026 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.debug.replaydebugging.ui.command;

import java.util.Set;

import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator.EventPosition;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.Messages;
import org.eclipse.gef.commands.Command;

/**
 * @brief Command to move the current position one event to the left
 */
public class MoveLeftCommand extends Command {

	private final ReplayNavigator replayNavigator;
	private ReplayNavigator.EventPosition eventPosition;
	private final Set<Integer> highlighted;
	private final boolean jump;

	public MoveLeftCommand(final ReplayNavigator replayNavigator, final boolean jump, final Set<Integer> highlighted) {
		super(Messages.MoveLeftCommand_Label);
		this.replayNavigator = replayNavigator;
		this.highlighted = highlighted;
		this.jump = jump;
	}

	@Override
	public void execute() {
		eventPosition = replayNavigator.getCurrentEventPosition();
		if (!jump) {
			replayNavigator.moveOneEventBackwards();
			return;
		}

		final var destinationIndex = NavigationHelper.getJumpDestination(eventPosition.eventNumber(),
				eventPosition.timeline().getMaxEventNumber(), false, highlighted);
		replayNavigator.moveToEvent(new EventPosition(eventPosition.timeline(), destinationIndex));
	}

	@Override
	public void undo() {
		replayNavigator.moveToEvent(eventPosition);
	}

	@Override
	public void redo() {
		if (!jump) {
			replayNavigator.moveOneEventBackwards();
			return;
		}

		final var destinationIndex = NavigationHelper.getJumpDestination(eventPosition.eventNumber(),
				eventPosition.timeline().getMaxEventNumber(), false, highlighted);
		replayNavigator.moveToEvent(new EventPosition(eventPosition.timeline(), destinationIndex));
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return eventPosition != null;
	}

}