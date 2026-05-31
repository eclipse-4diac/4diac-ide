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

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * @brief A figure representing an event marker in the timeline.
 *
 *        It is represented as a filled oval, and when clicked, it notifies its
 *        listeners with the index of the event it represents.
 */
public class EventMarkerFigure extends Figure {

	private final int eventIndex;
	private boolean readOnly = false;

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

	public void setIsReadOnly(final boolean isReadOnly) {
		this.readOnly = isReadOnly;
	}

	@Override
	protected void paintFigure(final Graphics g) {
		final Rectangle b = getBounds();

		g.setBackgroundColor(getBackgroundColor());
		g.fillOval(b);

		if (readOnly) {
			drawLockBadge(g, b);
		}
	}

	private static void drawLockBadge(final Graphics g, final Rectangle b) {
		// Position the badge in the top-right quadrant
		final int size = Math.max(6, b.width / 4);
		final int bx = b.x + b.width - size - 1;
		final int by = b.y + 1;

		// Badge background — small white filled circle
		g.setBackgroundColor(ColorConstants.white);
		g.fillOval(bx, by, size, size);

		// Shackle (the arc on top of the padlock body)
		g.setForegroundColor(ColorConstants.darkGray);
		g.setLineWidth(1);
		final int shackleInset = size / 4;
		g.drawArc(bx + shackleInset, by, size - shackleInset * 2, size - shackleInset * 2, 0, 180);

		// Body (the rectangular base of the padlock)
		final int bodyH = size / 2;
		final int bodyY = by + size - bodyH;
		g.setBackgroundColor(ColorConstants.darkGray);
		g.fillRoundRectangle(new Rectangle(bx, bodyY, size, bodyH), 2, 2);
	}

	// Listener

	public interface SelectedEventListener {
		void eventSelected();
	}

	private final Set<SelectedEventListener> listeners = new HashSet<>();

	public void addEventSelectionListener(final SelectedEventListener listener) {
		listeners.add(listener);
	}

	public void removeEventSelectionListener(final SelectedEventListener listener) {
		listeners.remove(listener);
	}

	@Override
	public Dimension getPreferredSize(final int wHint, final int hHint) {
		return new Dimension(CommonFigureConstants.MARKER_SIZE, CommonFigureConstants.MARKER_SIZE);
	}

	private void notifyListeners() {
		for (final var listener : listeners) {
			listener.eventSelected();
		}
	}
}
