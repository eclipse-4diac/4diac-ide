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
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * Hides rows where all columns share the same value. Toggled on/off via the
 * toolbar.
 */
public class UniquenessFilter extends ViewerFilter {

	private List<ComparisonColumn> columns = List.of();

	public void setActiveColumns(final List<ComparisonColumn> columns) {
		this.columns = columns;
	}

	@Override
	public boolean select(final Viewer viewer, final Object parentElement, final Object element) {
		if (!(element instanceof final RowEntry row)) {
			return true;
		}
		if (columns.size() < 2) {
			return true; // nothing to compare
		}

		final long distinctValues = columns.stream().map(cs -> cs.getCell(row.rowKey())).distinct().count();

		return distinctValues > 1; // keep only rows with differing values
	}
}
