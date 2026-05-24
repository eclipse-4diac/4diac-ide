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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.swt.widgets.Display;

/**
 * Singleton service. Holds all added columns and notifies registered listeners
 * when the state changes. Thread-safe: all mutations go through synchronized
 * methods.
 */
public class ComparisonService {

	public interface Listener {
		void columnsChanged(List<ComparisonColumn> columns);
	}

	private static final ComparisonService INSTANCE = new ComparisonService();

	public static ComparisonService getInstance() {
		return INSTANCE;
	}

	// Insertion-ordered: preserves the sequence columns were added
	private final LinkedHashMap<String, ComparisonColumn> columns = new LinkedHashMap<>();
	private final List<Listener> listeners = new CopyOnWriteArrayList<>();

	private ComparisonService() {
	}

	public synchronized void addColumn(final ComparisonColumn column) {
		columns.put(column.getColumnId(), column);
		notifyListeners();
	}

	public synchronized void replaceColumn(final ComparisonColumn column) {
		addColumn(column); // same logic as add, but clearer intent
	}

	public synchronized void removeColumn(final String columnId) {
		columns.remove(columnId);
		notifyListeners();
	}

	public synchronized void clearAll() {
		columns.clear();
		notifyListeners();
	}

	public synchronized List<ComparisonColumn> getColumns() {
		return List.copyOf(columns.values());
	}

	public void addListener(final Listener l) {
		listeners.add(l);
	}

	public void removeListener(final Listener l) {
		listeners.remove(l);
	}

	private void notifyListeners() {
		final List<ComparisonColumn> snapshot = List.copyOf(columns.values());
		// Always fire on UI thread
		Display.getDefault().asyncExec(() -> listeners.forEach(l -> l.columnsChanged(snapshot)));
	}
}