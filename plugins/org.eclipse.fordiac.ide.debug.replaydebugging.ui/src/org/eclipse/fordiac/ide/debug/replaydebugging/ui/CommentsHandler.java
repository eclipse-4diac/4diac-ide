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

package org.eclipse.fordiac.ide.debug.replaydebugging.ui;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator.EventPosition;
import org.eclipse.swt.widgets.Display;

/**
 * Singleton registry that maps EventPositions to comment strings.
 */
public class CommentsHandler {

	// ── Singleton ─────────────────────────────────────────────────────────

	private static final CommentsHandler INSTANCE = new CommentsHandler();

	public static CommentsHandler getInstance() {
		return INSTANCE;
	}

	private CommentsHandler() {
	}

	// ── Listener interface ────────────────────────────────────────────────

	public interface Listener {
		/**
		 * Fired on the UI thread after any add, update, or remove operation.
		 *
		 * @param position the position whose comment changed
		 * @param comment  the new comment, or null if the comment was removed
		 */
		void eventCommentChanged(EventPosition position, String comment);
	}

	private final Map<EventPosition, String> eventComments = new ConcurrentHashMap<>();

	private final List<Listener> listeners = new CopyOnWriteArrayList<>();

	// ── Comment mutations ─────────────────────────────────────────────────

	/**
	 * Adds or replaces the comment for the given position. Notifies listeners with
	 * the new comment text.
	 */
	public void setComment(final EventPosition position, final String comment) {
		eventComments.put(position, comment);
		notifyListeners(position, comment);
	}

	/**
	 * Removes the comment for the given position, if present. Notifies listeners
	 * with null to signal removal. No-op if no comment exists for the position.
	 */
	public void removeComment(final EventPosition position) {
		if (eventComments.remove(position) != null) {
			notifyListeners(position, null);
		}
	}

	// ── Queries ───────────────────────────────────────────────────────────

	/**
	 * Returns the comment for the given position, or null if no comment has been
	 * set.
	 */
	public String getComment(final EventPosition position) {
		return eventComments.get(position);
	}

	/**
	 * Returns a snapshot of all currently stored comments. The returned map is
	 * unmodifiable and decoupled from internal state.
	 */
	public Map<EventPosition, String> getAllComments() {
		return Map.copyOf(eventComments);
	}

	// ── Listener registration ─────────────────────────────────────────────

	public void addListener(final Listener listener) {
		listeners.add(listener);
		eventComments.forEach(listener::eventCommentChanged);
	}

	public void removeListener(final Listener listener) {
		listeners.remove(listener);
	}

	// ── Notification ──────────────────────────────────────────────────────

	private void notifyListeners(final EventPosition position, final String comment) {
		Display.getDefault().asyncExec(() -> listeners.forEach(l -> l.eventCommentChanged(position, comment)));
	}
}