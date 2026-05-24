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

import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.Messages;
import org.eclipse.gef.commands.Command;

/**
 * @brief Command to move the current position one event up
 */
public class MoveUpCommand extends Command {

	private final ReplayNavigator replayNavigator;
	private ReplayNavigator.EventPosition originalEventPosition;
	private ReplayNavigator.EventPosition destinationEventPosition;

	public MoveUpCommand(final ReplayNavigator replayNavigator) {
		super(Messages.MoveUpCommand_Label);
		this.replayNavigator = replayNavigator;
	}

	@Override
	public void execute() {
		final var currentEventPosition = replayNavigator.getCurrentEventPosition();
		final var currentTimeline = currentEventPosition.timeline();
		final var currentEvent = currentEventPosition.eventNumber();

		destinationEventPosition = NavigationHelper.findNextTimelineWithValidEventNumber(replayNavigator,
				currentTimeline, currentEvent, true);
		if (destinationEventPosition == null) {
			return;
		}
		originalEventPosition = currentEventPosition;
		replayNavigator.moveToEvent(destinationEventPosition);
	}

	@Override
	public void undo() {
		replayNavigator.moveToEvent(originalEventPosition);
	}

	@Override
	public void redo() {
		replayNavigator.moveToEvent(destinationEventPosition);
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return destinationEventPosition != null;
	}
}