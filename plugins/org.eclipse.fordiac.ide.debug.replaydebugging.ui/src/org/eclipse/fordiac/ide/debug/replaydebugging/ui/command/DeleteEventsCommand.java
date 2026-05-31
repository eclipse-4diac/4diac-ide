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

import java.util.List;

import org.eclipse.fordiac.ide.debug.replaydebugging.core.EventChange;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.Timeline;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.Messages;
import org.eclipse.gef.commands.Command;

/**
 * @brief Command to delete an event from a timeline
 */
public class DeleteEventsCommand extends Command {

	private final Timeline timeline;
	private final int eventIndex;
	private List<EventChange> eventChanges; // Store event data for undo

	public DeleteEventsCommand(final Timeline timeline, final int eventIndex) {
		super(Messages.DeleteEventsCommand_Label);
		this.timeline = timeline;
		this.eventIndex = eventIndex;
	}

	@Override
	public void execute() {
		// Store event data before deletion for undo support
		eventChanges = timeline.getEventsFrom(eventIndex);
		timeline.removeEventsFrom(eventIndex);
	}

	@Override
	public void undo() {
		// Restore the event with its previous data
		for (final var eventChange : eventChanges) {
			timeline.addEventChange(eventChange.newValues());
		}
	}

	@Override
	public void redo() {
		// Re-delete the event
		timeline.removeEventsFrom(eventIndex);
	}

	@Override
	public boolean canExecute() {
		return timeline != null && eventIndex >= 0 && eventIndex <= timeline.getMaxEventNumber()
				&& eventIndex >= timeline.getFirstDeletableEventIndex();
	}

	@Override
	public boolean canUndo() {
		return eventChanges != null;
	}
}