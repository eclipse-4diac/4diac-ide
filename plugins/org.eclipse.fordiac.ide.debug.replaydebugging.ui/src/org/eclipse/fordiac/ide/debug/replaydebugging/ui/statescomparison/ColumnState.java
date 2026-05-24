/*******************************************************************************
 * Copyright (c) 2026 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: Jose Cabral - initial API and implementation and/or initial
 * documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.debug.replaydebugging.ui.statescomparison;

/**
 * Wraps a ComparisonColumn with view-local UI state. Disabled columns are
 * greyed out and excluded from uniqueness logic.
 */
public class ColumnState {

	private final ComparisonColumn column;
	private boolean disabled;

	public ColumnState(final ComparisonColumn column) {
		this.column = column;
		this.disabled = false;
	}

	public ComparisonColumn getColumn() {
		return column;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(final boolean d) {
		this.disabled = d;
	}

	public String getColumnId() {
		return column.getColumnId();
	}
}