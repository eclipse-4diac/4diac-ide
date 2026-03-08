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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;

/**
 * @brief A figure representing an event marker in the timeline.
 *
 *        It is represented as a filled oval, and when clicked, it notifies its
 *        listeners with the index of the event it represents.
 */
public class EventMarkerFigure extends Figure {

	private final int eventIndex;

	public EventMarkerFigure(final int eventIndex) {
		this.eventIndex = eventIndex;
		setOpaque(true);

		addMouseListener(new MouseListener() {

			@Override
			public void mousePressed(final MouseEvent me) {
				notifyListeners();
			}

			@Override
			public void mouseReleased(final MouseEvent me) {
				// nothing to do
			}

			@Override
			public void mouseDoubleClicked(final MouseEvent me) {
				// nothing to do
			}
		});

	}

	public int getEventIndex() {
		return eventIndex;
	}

	@Override
	protected void paintFigure(final Graphics g) {
		g.fillOval(getBounds());
	}

	// Listener

	public interface SelectedEventListener {
		void eventSelected(int eventIndex);
	}

	private final Set<SelectedEventListener> listeners = new HashSet<>();

	public void addEventSelectionListener(final SelectedEventListener listener) {
		listeners.add(listener);
	}

	public void removeEventSelectionListener(final SelectedEventListener listener) {
		listeners.remove(listener);
	}

	private void notifyListeners() {
		for (final var listener : listeners) {
			listener.eventSelected(eventIndex);
		}
	}
}
