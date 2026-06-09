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
 * @brief Command to move the current position one event down
 */
public class MoveDownCommand extends Command {

	private final ReplayNavigator replayNavigator;
	private ReplayNavigator.EventPosition originalEventPosition;
	private ReplayNavigator.EventPosition destinationEventPosition;

	public MoveDownCommand(final ReplayNavigator replayNavigator) {
		super(Messages.MoveDownCommand_Label);
		this.replayNavigator = replayNavigator;
	}

	@Override
	public void execute() {
		final var currentPosition = replayNavigator.getCurrentEventPosition();
		final var currentTimeline = currentPosition.timeline();
		final var currentEvent = currentPosition.eventNumber();

		var hasSpawnedAtCurrentPosition = false;
		for (final var spawnedTimeline : currentTimeline.getSpawnedTimelines()) {
			if (currentTimeline.getSpawnedTimelineEventNumber(spawnedTimeline) == currentEvent) {
				hasSpawnedAtCurrentPosition = true;
				break;
			}
		}
		// if there's at least one spawned timeline, move down but one event later to
		// follow the first timeline that appears
		destinationEventPosition = NavigationHelper.findNextTimelineWithValidEventNumber(replayNavigator,
				currentTimeline, hasSpawnedAtCurrentPosition ? currentEvent + 1 : currentEvent, false);
		if (destinationEventPosition == null) {
			return;
		}
		originalEventPosition = currentPosition;
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
		return originalEventPosition != null;
	}
}