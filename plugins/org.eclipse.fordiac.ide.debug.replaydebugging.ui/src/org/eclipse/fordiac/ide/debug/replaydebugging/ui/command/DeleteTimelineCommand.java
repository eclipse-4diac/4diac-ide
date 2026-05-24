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

import org.eclipse.fordiac.ide.debug.replaydebugging.core.Timeline;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.Messages;
import org.eclipse.gef.commands.Command;

/**
 * @brief Command to delete a timeline
 */
public class DeleteTimelineCommand extends Command {

	private final Timeline timeline;
	private Timeline parentTimeline;
	private int spawnedTimelineEventNumber;

	public DeleteTimelineCommand(final Timeline timeline) {
		super(Messages.DeleteTimelineCommand_Label);
		this.timeline = timeline;
	}

	@Override
	public void execute() {
		parentTimeline = timeline.getParentTimeline();
		if (parentTimeline != null) {
			spawnedTimelineEventNumber = parentTimeline.getSpawnedTimelineEventNumber(timeline);
			parentTimeline.removeSpawnedTimeline(timeline);
		}
	}

	@Override
	public void undo() {
		if (parentTimeline != null) {
			parentTimeline.addSpawnedTimeline(timeline, spawnedTimelineEventNumber);
		}
	}

	@Override
	public void redo() {
		if (parentTimeline != null) {
			parentTimeline.removeSpawnedTimeline(timeline);
		}
	}

	@Override
	public boolean canExecute() {
		return timeline != null && timeline.getParentTimeline() != null;
	}

	@Override
	public boolean canUndo() {
		return parentTimeline != null;
	}
}