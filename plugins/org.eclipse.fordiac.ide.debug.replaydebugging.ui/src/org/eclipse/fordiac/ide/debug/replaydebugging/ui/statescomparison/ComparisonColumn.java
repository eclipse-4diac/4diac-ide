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
	private final String label; // shown in table header
	private final Map<String, String> cells; // rowKey → displayValue

	public ComparisonColumn(final String columnId, final String label, final Map<String, String> cells) {
		this.columnId = columnId;
		this.label = label;
		this.cells = Map.copyOf(cells); // defensive copy
	}

	public String getColumnId() {
		return columnId;
	}

	public String getLabel() {
		return label;
	}

	public String getCell(final String rowKey) {
		return cells.getOrDefault(rowKey, EMPTY_VALUE);
	}

	public Set<String> getRowKeys() {
		return cells.keySet();
	}

}
