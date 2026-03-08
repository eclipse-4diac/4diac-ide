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

package org.eclipse.fordiac.ide.debug.replaydebugging.ui.figure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.Timeline;
import org.eclipse.swt.graphics.Color;

/**
 * @brief Figure representing a single timeline of events in the replay
 *        debugging.
 *
 *        It is responsible for drawing the line and the event markers, as well
 *        as handling the selection of events and notifying the listeners about
 *        it.
 *
 *        The line is drawn in black for the valid events, and gray for the
 *        invalid ones. The current event is highlighted in yellow, while the
 *        past events are highlighted in blue.
 */
public class TimelineFigure extends Figure implements EventMarkerFigure.SelectedEventListener {

	private static final int LINE_WITDH = 4;
	private static final Color CURRENT_EVENT_COLOR = ColorConstants.yellow;
	private static final Color NOT_CURRENT_EVENT_COLOR = ColorConstants.blue;
	private static final Color INVALID_COLOR = ColorConstants.gray;

	final Timeline timeline;
	private int currentEvent = -1;
	private int firstInvalid = 0;
	private final List<EventMarkerFigure> markers = new ArrayList<>();

	public TimelineFigure(final Timeline timeline) {
		this.timeline = timeline;
		setOpaque(true);
		setLayoutManager(null);
		rebuildMarkers();
		for (final var marker : markers) {
			marker.setBackgroundColor(INVALID_COLOR);
		}
		repaint();
	}

	public void rebuildMarkers() {
		removeAll();
		markers.clear();
		final int count = timeline.getMaxEventNumber();

		for (int i = 0; i <= count; i++) {
			final EventMarkerFigure marker = new EventMarkerFigure(i);
			marker.addEventSelectionListener(this);
			markers.add(marker);
			add(marker);
		}
		updateEventStates(firstInvalid, currentEvent);
		revalidate();
		repaint();
	}

	public void updateEventStates(final int firstInvalid, final int currentEvent) {
		if (currentEvent >= markers.size() || firstInvalid > markers.size()) {
			return;
		}
		for (int runner = 0; runner < firstInvalid; runner++) {
			markers.get(runner).setBackgroundColor(NOT_CURRENT_EVENT_COLOR);
		}
		for (int runner = firstInvalid; runner < markers.size(); runner++) {
			markers.get(runner).setBackgroundColor(INVALID_COLOR);
		}
		this.firstInvalid = firstInvalid;

		if (this.currentEvent >= 0 && this.currentEvent < markers.size()) {
			final var color = this.currentEvent >= firstInvalid ? INVALID_COLOR : NOT_CURRENT_EVENT_COLOR;
			markers.get(this.currentEvent).setBackgroundColor(color);
		}
		if (currentEvent >= 0 && currentEvent < markers.size()) {
			markers.get(currentEvent).setBackgroundColor(CURRENT_EVENT_COLOR);
		}
		this.currentEvent = currentEvent;
	}

	public Figure getCurrentSelectedEventFigure() {
		if (currentEvent != -1) {
			return markers.get(currentEvent);
		}
		return null;
	}

	@Override
	protected void layout() {
		final Rectangle area = getBounds();

		final int centerY = area.y + area.height / 2;

		for (final EventMarkerFigure marker : markers) {
			final int x = area.x + (marker.getEventIndex()) * (CommonConstants.TOTAL_MARKER_SPACE);
			marker.setBounds(new Rectangle(x, centerY - CommonConstants.MARKER_SIZE / 2, CommonConstants.MARKER_SIZE,
					CommonConstants.MARKER_SIZE));
		}
	}

	@Override
	public Dimension getPreferredSize(final int wHint, final int hHint) {
		final var width = markers.size() * CommonConstants.TOTAL_MARKER_SPACE;
		return new Dimension(width, CommonConstants.MARKER_SIZE);
	}

	@Override
	protected void paintFigure(final Graphics g) {
		final Rectangle area = getBounds();
		final int centerY = area.y + area.height / 2;
		g.setLineWidth(LINE_WITDH);

		final var endBlack = area.x + Math.min(area.width,
				CommonConstants.TOTAL_MARKER_SPACE * (firstInvalid) - CommonConstants.EVENT_SPACING);

		g.setForegroundColor(ColorConstants.black);
		g.drawLine(area.x, centerY, endBlack, centerY);

		g.setForegroundColor(ColorConstants.gray);
		g.drawLine(endBlack, centerY, area.x + area.width - CommonConstants.EVENT_SPACING, centerY);

	}

	// Callback from selected event marker

	@Override
	public void eventSelected(final int eventIndex) {
		notifyListeners(eventIndex);
	}

	// Listener

	public interface SelectedEventListener {
		void eventSelected(Timeline timeline, int eventIndex);
	}

	private final Set<SelectedEventListener> listeners = new HashSet<>();

	public void addEventSelectionListener(final SelectedEventListener listener) {
		listeners.add(listener);
	}

	public void removeEventSelectionListener(final SelectedEventListener listener) {
		listeners.remove(listener);
	}

	private void notifyListeners(final int eventIndex) {
		for (final var listener : listeners) {
			listener.eventSelected(timeline, eventIndex);
		}
	}
}