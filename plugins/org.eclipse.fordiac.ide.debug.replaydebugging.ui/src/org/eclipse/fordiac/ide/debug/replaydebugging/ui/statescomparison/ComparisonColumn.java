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

package org.eclipse.fordiac.ide.debug.replaydebugging.ui.statescomparison;

import java.util.Map;
import java.util.Set;

import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator.EventPosition;
import org.eclipse.swt.graphics.Color;

/**
 * Represents a column in the states comparison table.
 *
 * Each column has a unique ID, a label for the table header, and a mapping of
 * row keys to cell display values. The row keys are the datapoints and the cell
 * values are their corresponding values..
 */
public class ComparisonColumn {

	private static final String EMPTY_VALUE = ""; //$NON-NLS-1$

	private final String columnId; // unique, e.g. element UUID
	private final EventPosition eventPosition;
	private String label;
	private final Map<String, String> cells; // rowKey → displayValue
	private final Color color; // for coloring the column

	public ComparisonColumn(final String columnId, final EventPosition eventPosition, final String label,
			final Map<String, String> cells, final Color color) {
		this.columnId = columnId;
		this.eventPosition = eventPosition;
		this.label = label;
		this.color = color;
		this.cells = Map.copyOf(cells); // defensive copy
	}

	public String getColumnId() {
		return columnId;
	}

	public EventPosition getEventPosition() {
		return eventPosition;
	}

	public String getCell(final String rowKey) {
		return cells.getOrDefault(rowKey, EMPTY_VALUE);
	}

	public Set<String> getRowKeys() {
		return cells.keySet();
	}

	public Color getColor() {
		return color;
	}

	public String getLabel() {
		return label != null ? label : EMPTY_VALUE;
	}

	public void setLabel(final String label) {
		this.label = label;
	}

}
