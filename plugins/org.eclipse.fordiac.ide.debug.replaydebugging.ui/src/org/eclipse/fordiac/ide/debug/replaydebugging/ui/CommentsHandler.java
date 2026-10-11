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
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator.EventPosition;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.Timeline;
import org.eclipse.swt.widgets.Display;

/**
 * Singleton registry that maps EventPositions and Timelines to comment strings.
 * Together with each comment, the {@link ReplayNavigator} the comment was made
 * on is stored, so that consumers of a comment (e.g. the comments view) can
 * navigate to the commented position.
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

		enum Event {
			ADDED_EDITED, REMOVED

		}

		/**
		 * Fired on the UI thread after any add, update, or remove operation.
		 *
		 * @param position the position whose comment changed
		 * @param comment  the new comment, or null if the comment was removed
		 * @param event    whether the comment was added/edited or removed
		 */
		void eventCommentChanged(EventPosition position, String comment, Event event);

		void timelineCommentChanged(Timeline timeline, String comment, Event event);
	}

	/** A stored comment and the navigator it was made on. */
	private record Entry(String comment, ReplayNavigator navigator) {
	}

	private final Map<EventPosition, Entry> eventComments = new ConcurrentHashMap<>();

	private final Map<Timeline, Entry> timelineComments = new ConcurrentHashMap<>();

	private final List<Listener> listeners = new CopyOnWriteArrayList<>();

	// ── Comment mutations ─────────────────────────────────────────────────

	/**
	 * Adds or replaces the comment for the given position and remembers the
	 * navigator it belongs to. Notifies listeners with the new comment text.
	 */
	public void setComment(final EventPosition position, final String comment, final ReplayNavigator navigator) {
		eventComments.put(position, new Entry(comment, Objects.requireNonNull(navigator)));
		notifyListeners(position, comment, Listener.Event.ADDED_EDITED);
	}

	public void setComment(final Timeline timeline, final String comment, final ReplayNavigator navigator) {
		timelineComments.put(timeline, new Entry(comment, Objects.requireNonNull(navigator)));
		notifyListeners(timeline, comment, Listener.Event.ADDED_EDITED);
	}

	/**
	 * Removes the comment (and the stored navigator) for the given position, if
	 * present. Notifies listeners with null to signal removal. No-op if no comment
	 * exists for the position.
	 */
	public void removeComment(final EventPosition position) {
		if (eventComments.remove(position) != null) {
			notifyListeners(position, null, Listener.Event.REMOVED);
		}
	}

	public void removeComment(final Timeline timeline) {
		if (timelineComments.remove(timeline) != null) {
			notifyListeners(timeline, null, Listener.Event.REMOVED);
		}
	}

	// ── Queries ───────────────────────────────────────────────────────────

	/**
	 * Returns the comment for the given position, or null if no comment has been
	 * set.
	 */
	public String getComment(final EventPosition position) {
		final Entry entry = eventComments.get(position);
		return entry == null ? null : entry.comment();
	}

	public String getComment(final Timeline timeline) {
		final Entry entry = timelineComments.get(timeline);
		return entry == null ? null : entry.comment();
	}

	/**
	 * Returns the navigator the comment for the given position was made on, or null
	 * if no comment has been set (or it has been removed in the meantime).
	 */
	public ReplayNavigator getReplayNavigator(final EventPosition position) {
		final Entry entry = eventComments.get(position);
		return entry == null ? null : entry.navigator();
	}

	public ReplayNavigator getReplayNavigator(final Timeline timeline) {
		final Entry entry = timelineComments.get(timeline);
		return entry == null ? null : entry.navigator();
	}

	/**
	 * Returns a snapshot of all currently stored comments. The returned map is
	 * unmodifiable and decoupled from internal state.
	 */
	public Map<EventPosition, String> getAllEventComments() {
		return snapshot(eventComments);
	}

	public Map<Timeline, String> getAllTimelineComments() {
		return snapshot(timelineComments);
	}

	private static <K> Map<K, String> snapshot(final Map<K, Entry> comments) {
		return comments.entrySet().stream()
				.collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, e -> e.getValue().comment()));
	}

	// ── Listener registration ─────────────────────────────────────────────

	public void addListener(final Listener listener) {
		listeners.add(listener);
		eventComments.forEach((position, entry) -> listener.eventCommentChanged(position, entry.comment(),
				Listener.Event.ADDED_EDITED));
		timelineComments.forEach((timeline, entry) -> listener.timelineCommentChanged(timeline, entry.comment(),
				Listener.Event.ADDED_EDITED));
	}

	public void removeListener(final Listener listener) {
		listeners.remove(listener);
	}

	// ── Notification ──────────────────────────────────────────────────────

	private void notifyListeners(final EventPosition position, final String comment, final Listener.Event event) {
		Display.getDefault().asyncExec(() -> listeners.forEach(l -> l.eventCommentChanged(position, comment, event)));
	}

	private void notifyListeners(final Timeline timeline, final String comment, final Listener.Event event) {
		Display.getDefault()
				.asyncExec(() -> listeners.forEach(l -> l.timelineCommentChanged(timeline, comment, event)));
	}
}
