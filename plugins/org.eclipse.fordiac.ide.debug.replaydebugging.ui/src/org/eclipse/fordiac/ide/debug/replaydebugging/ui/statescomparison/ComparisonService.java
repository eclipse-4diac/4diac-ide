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

import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator.EventPosition;
import org.eclipse.swt.widgets.Display;

/**
 * Singleton service. Holds all added columns and notifies registered listeners
 * when the state changes. Thread-safe: all mutations go through synchronized
 * methods.
 */
public class ComparisonService {

	public interface Listener {
		enum Event {
			COLUMN_ADDED, COLUMN_REMOVED, INFORMATION_CHANGED,

		}

		void columnsChanged(List<ComparisonColumn> columns, Event event);
	}

	private long id = 0; // for generating unique column IDs

	private static final ComparisonService INSTANCE = new ComparisonService();

	public static ComparisonService getInstance() {
		return INSTANCE;
	}

	// Insertion-ordered: preserves the sequence columns were added
	private final LinkedHashMap<String, ComparisonColumn> columns = new LinkedHashMap<>();
	private final List<Listener> listeners = new CopyOnWriteArrayList<>();

	private ComparisonService() {
	}

	public String generateUniqueColumnId() {
		return Long.toString(id++);
	}

	public synchronized void addColumn(final ComparisonColumn column) {
		columns.put(column.getColumnId(), column);
		notifyListeners(Listener.Event.COLUMN_ADDED);
	}

	public synchronized void replaceColumn(final ComparisonColumn column) {
		columns.put(column.getColumnId(), column);
		notifyListeners(Listener.Event.INFORMATION_CHANGED);
	}

	public synchronized void removeColumn(final String columnId) {
		columns.remove(columnId);
		notifyListeners(Listener.Event.COLUMN_REMOVED);
	}

	public synchronized void clearAll() {
		columns.clear();
		notifyListeners(Listener.Event.COLUMN_REMOVED);
	}

	public synchronized void updateColumnLabel(final EventPosition eventPosition, final String newLabel) {
		for (final var column : columns.values()) {
			if (column.getEventPosition().equals(eventPosition)) {
				column.setLabel(newLabel);
				notifyListeners(Listener.Event.INFORMATION_CHANGED);
				break;
			}
		}
	}

	public synchronized List<ComparisonColumn> getColumns() {
		return List.copyOf(columns.values());
	}

	public void addListener(final Listener l) {
		final List<ComparisonColumn> snapshot = List.copyOf(columns.values());
		listeners.add(l);
		l.columnsChanged(snapshot, Listener.Event.INFORMATION_CHANGED); // initial notification with current state
	}

	public void removeListener(final Listener l) {
		listeners.remove(l);
	}

	private void notifyListeners(final Listener.Event event) {
		final List<ComparisonColumn> snapshot = List.copyOf(columns.values());
		// Always fire on UI thread
		Display.getDefault().asyncExec(() -> listeners.forEach(l -> l.columnsChanged(snapshot, event)));
	}
}
