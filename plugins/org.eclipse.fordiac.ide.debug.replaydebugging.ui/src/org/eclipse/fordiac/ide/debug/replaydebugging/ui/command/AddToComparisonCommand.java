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
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.statescomparison.ComparisonColumn;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.statescomparison.ComparisonService;
import org.eclipse.gef.commands.Command;

public class AddToComparisonCommand extends Command {

	private static long idCounter = 0; // for generating unique column IDs if needed

	private final ComparisonService service;
	private final ReplayNavigator replayNavigator;
	private final EventPosition eventPosition;
	private ComparisonColumn column = null;
	private ComparisonColumn previousColumn = null; // for undo

	public AddToComparisonCommand(final ComparisonService service, final ReplayNavigator replayNavigator,
			final ReplayNavigator.EventPosition eventPosition) {
		super(Messages.AddToComparisonCommand_Text);
		this.service = service;
		this.replayNavigator = replayNavigator;
		this.eventPosition = eventPosition;
	}

	@Override
	public void execute() {
		final var snapshot = replayNavigator.getStateAtEventPosition(eventPosition);

		column = new ComparisonColumn(Long.toString(idCounter++), // unique column ID
				"", //$NON-NLS-1$ header, empty for now
				snapshot); // cell data

		// Capture previous state for undo
		previousColumn = service.getColumns().stream().filter(c -> c.getColumnId().equals(column.getColumnId()))
				.findFirst().orElse(null);
		service.addColumn(column);
	}

	@Override
	public void undo() {
		if (previousColumn != null) {
			service.replaceColumn(previousColumn); // restore prior snapshot
		} else {
			service.removeColumn(column.getColumnId()); // it was new, remove it
		}
	}

	@Override
	public void redo() {
		service.addColumn(column);
	}

	@Override
	public boolean canExecute() {
		return eventPosition.eventNumber() != -1;
	}

}