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
import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator.EventPosition;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.Messages;
import org.eclipse.gef.commands.Command;

/**
 * @brief Command to select an event marker by moving to its position
 *
 *        This command integrates event marker selection into the command stack.
 *        When executed, it moves the current event position to the selected
 *        event.
 */
public class SelectEventCommand extends Command {

	private final ReplayNavigator replayNavigator;
	private final EventPosition targetPosition;
	private EventPosition previousPosition;

	public SelectEventCommand(final ReplayNavigator replayNavigator, final EventPosition targetPosition) {
		super(Messages.SelectEventCommand_Text);
		this.replayNavigator = replayNavigator;
		this.targetPosition = targetPosition;
	}

	@Override
	public void execute() {
		previousPosition = replayNavigator.getCurrentEventPosition();
		replayNavigator.moveToEvent(targetPosition);
	}

	@Override
	public void undo() {
		if (previousPosition != null) {
			replayNavigator.moveToEvent(previousPosition);
		}
	}

	@Override
	public void redo() {
		replayNavigator.moveToEvent(targetPosition);
	}

	@Override
	public boolean canExecute() {
		return targetPosition != null && replayNavigator != null;
	}

	@Override
	public boolean canUndo() {
		return previousPosition != null;
	}

}