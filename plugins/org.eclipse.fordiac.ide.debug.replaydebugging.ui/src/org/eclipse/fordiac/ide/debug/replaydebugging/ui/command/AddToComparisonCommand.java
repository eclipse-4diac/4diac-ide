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
import org.eclipse.fordiac.ide.model.helpers.ColorHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.graphics.Color;

public class AddToComparisonCommand extends Command {

	private final ReplayNavigator replayNavigator;
	private final EventPosition eventPosition;
	private ComparisonColumn column = null;
	private final String label;
	private ComparisonColumn previousColumn = null; // for undo

	private static final float COLOR_SATURATION = 0.75f;
	private static final float COLOR_BRIGHTNESS = 0.75f;
	private static final String DEFAULT_LABEL = ""; //$NON-NLS-1$

	public AddToComparisonCommand(final ReplayNavigator replayNavigator,
			final ReplayNavigator.EventPosition eventPosition, final String label) {
		super(Messages.AddToComparisonCommand_Text);
		this.replayNavigator = replayNavigator;
		this.eventPosition = eventPosition;
		this.label = label != null ? label : DEFAULT_LABEL;
	}

	@Override
	public void execute() {
		final var snapshot = replayNavigator.getStateAtEventPosition(eventPosition);

		final var rgb = ColorHelper.createRandomColor(COLOR_SATURATION, COLOR_BRIGHTNESS);

		// label empty for now
		column = new ComparisonColumn(ComparisonService.getInstance().generateUniqueColumnId(), eventPosition, label,
				snapshot, new Color(rgb.red, rgb.green, rgb.blue));

		// Capture previous state for undo
		previousColumn = ComparisonService.getInstance().getColumns().stream()
				.filter(c -> c.getColumnId().equals(column.getColumnId())).findFirst().orElse(null);
		ComparisonService.getInstance().addColumn(column);
	}

	@Override
	public void undo() {
		if (previousColumn != null) {
			ComparisonService.getInstance().replaceColumn(previousColumn); // restore prior snapshot
		} else {
			ComparisonService.getInstance().removeColumn(column.getColumnId()); // it was new, remove it
		}
	}

	@Override
	public void redo() {
		ComparisonService.getInstance().addColumn(column);
	}

	@Override
	public boolean canExecute() {
		return eventPosition.eventNumber() != -1;
	}

}