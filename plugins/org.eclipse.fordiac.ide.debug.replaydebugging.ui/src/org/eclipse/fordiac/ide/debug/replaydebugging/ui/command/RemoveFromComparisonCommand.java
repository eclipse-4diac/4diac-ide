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

import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator.EventPosition;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.Messages;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.statescomparison.ComparisonColumn;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.statescomparison.ComparisonService;
import org.eclipse.gef.commands.Command;

public class RemoveFromComparisonCommand extends Command {

	private ComparisonColumn column = null;

	public RemoveFromComparisonCommand(final EventPosition eventPosition) {
		super(Messages.AddToComparisonCommand_Text);
		for (final var existingColumn : ComparisonService.getInstance().getColumns()) {
			if (existingColumn.getEventPosition().equals(eventPosition)) {
				column = existingColumn;
				break;
			}
		}
	}

	@Override
	public void execute() {
		ComparisonService.getInstance().removeColumn(column.getColumnId());
	}

	@Override
	public void undo() {
		ComparisonService.getInstance().addColumn(column);
	}

	@Override
	public void redo() {
		ComparisonService.getInstance().removeColumn(column.getColumnId());
	}

	@Override
	public boolean canExecute() {
		return column != null;
	}

}