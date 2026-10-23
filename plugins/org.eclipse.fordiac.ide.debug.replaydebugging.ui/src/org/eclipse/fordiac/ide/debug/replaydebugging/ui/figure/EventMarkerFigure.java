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
import org.eclipse.swt.graphics.Color;

/**
 * @brief A figure representing an event marker in the timeline.
 *
 *        It is represented as a filled oval, and when clicked, it notifies its
 *        listeners with the index of the event it represents.
 */
public class EventMarkerFigure extends Figure {

	private final int eventIndex;
	private boolean readOnly = false;
	private boolean isHighlighted = false;
	private Color secondColor = null;
	private static final int MIN_CORNER_ARC = 2;

	public EventMarkerFigure(final int eventIndex) {
		this.eventIndex = eventIndex;
		setOpaque(true);

		addMouseListener(new MouseListener() {

			@Override
			public void mousePressed(final MouseEvent me) {
				if (me.button == 1) { // left-click only
					notifyListeners();
				}
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

	public void setTooltipText(final String tooltipText) {
		if (tooltipText != null && !tooltipText.isEmpty()) {
			setToolTip(new TooltipFigure(tooltipText));
		} else {
			setToolTip(null);
		}
	}

	public void setSecondColor(final Color secondColor) {
		this.secondColor = secondColor;
	}

	public int getEventIndex() {
		return eventIndex;
	}

	public void setIsReadOnly(final boolean isReadOnly) {
		this.readOnly = isReadOnly;
		repaint(); // the shape depends on it
	}

	public void setIsHighlighted(final boolean isHighlighted) {
		this.isHighlighted = isHighlighted;
		repaint(); // the shape depends on it
	}

	@Override
	protected void paintFigure(final Graphics g) {
		final Rectangle completeBound = getBounds();
		final int highlightedThickness = completeBound.height * 15 / 100;
		var colorBounds = completeBound;

		if (isHighlighted) {
			// Fill the full shape with the highlight color — this becomes
			// the visible ring since the inner shape paints over the centre
			g.setBackgroundColor(ColorConstants.red);
			fillShape(g, completeBound);

			// Paint the actual shape inset by the ring thickness,
			// so the highlight color is only visible as a border ring
			colorBounds = completeBound.getCopy().shrink(highlightedThickness, highlightedThickness);

		}
		if (secondColor != null) {
			paintSplitShape(g, colorBounds);
		} else {
			g.setBackgroundColor(getBackgroundColor());
			fillShape(g, colorBounds);
		}
	}

	/** Read-only events are circles, editable events are rounded squares. */
	private void fillShape(final Graphics g, final Rectangle b) {
		if (readOnly) {
			g.fillOval(b);
		} else {
			final int arc = Math.max(MIN_CORNER_ARC, b.width / 6);
			g.fillRoundRectangle(b, arc, arc);
		}
	}

	private void paintSplitShape(final Graphics g, final Rectangle b) {
		final int midX = b.x + b.width / 2;

		g.pushState();
		g.clipRect(new Rectangle(b.x, b.y, b.width / 2, b.height));
		g.setBackgroundColor(getBackgroundColor());
		fillShape(g, b);
		g.popState();

		g.pushState();
		g.clipRect(new Rectangle(midX, b.y, b.width - b.width / 2, b.height));
		g.setBackgroundColor(secondColor);
		fillShape(g, b);
		g.popState();
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
