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

import java.util.List;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;

/**
 * Pluggable ViewerComparator. Sorting is stateless given the mode — all context
 * comes from the active ColumnState list injected before each sort pass.
 */
public class ComparisonSorter extends ViewerComparator {

	public enum SortMode {
		NONE, ALPHA_ASC, ALPHA_DESC, UNIQUENESS
	}

	private SortMode mode = SortMode.NONE;
	private List<ComparisonColumn> columns = List.of();

	public void setMode(final SortMode mode) {
		this.mode = mode;
	}

	public SortMode getMode() {
		return mode;
	}

	public void setActiveColumns(final List<ComparisonColumn> columns) {
		this.columns = columns;
	}

	@Override
	public int compare(final Viewer viewer, final Object a, final Object b) {
		if (!(a instanceof final RowEntry ra) || !(b instanceof final RowEntry rb)) {
			return 0;
		}

		return switch (mode) {
		case ALPHA_ASC -> ra.rowKey().compareToIgnoreCase(rb.rowKey());
		case ALPHA_DESC -> -ra.rowKey().compareToIgnoreCase(rb.rowKey());
		case UNIQUENESS -> compareByUniqueness(ra, rb);
		case NONE -> 0;
		};
	}

	/**
	 * Rows where values differ across columns are sort to the top. Uniqueness score
	 * equals to the number of distinct values across enabled columns. Higher score
	 * (more unique) → earlier in list.
	 */
	private int compareByUniqueness(final RowEntry a, final RowEntry b) {
		return Integer.compare(uniquenessScore(b), uniquenessScore(a)); // desc
	}

	private int uniquenessScore(final RowEntry row) {
		return (int) columns.stream().map(column -> column.getCell(row.rowKey())).distinct().count();
	}
}
