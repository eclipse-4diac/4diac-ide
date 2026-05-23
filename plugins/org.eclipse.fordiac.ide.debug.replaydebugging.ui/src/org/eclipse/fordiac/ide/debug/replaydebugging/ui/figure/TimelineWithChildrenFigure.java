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

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * @brief Figure that represents a timeline and its spawned timelines (children)
 */
public class TimelineWithChildrenFigure extends Figure {

	private final Figure timelineFigure;
	private final LineFigure lineFigure;
	private final Figure childrenFigure;
	private final int startPosition;
	private static final int SPACING_BETWEEN_TIMELINES = CommonFigureConstants.MARKER_SIZE / 2;

	public TimelineWithChildrenFigure(final int startPosition, final int maxNumberOfEvents) {
		this.startPosition = startPosition;
		final var layout = new ToolbarLayout(false);
		layout.setSpacing(SPACING_BETWEEN_TIMELINES);
		layout.setStretchMinorAxis(true);
		setLayoutManager(layout);
		setOpaque(true);

		// the timeline figure contains a line, and the events are later added by the
		// caller
		timelineFigure = new Figure();

		timelineFigure.setLayoutManager(new XYLayout());

		lineFigure = new LineFigure(maxNumberOfEvents);

		final int figureWidth = maxNumberOfEvents * CommonFigureConstants.TOTAL_MARKER_SPACE;
		final int figureHeight = CommonFigureConstants.MARKER_SIZE;

		timelineFigure.add(lineFigure,
				new Rectangle(startPosition * CommonFigureConstants.TOTAL_MARKER_SPACE, 0, figureWidth, figureHeight));

		childrenFigure = new Figure();

		childrenFigure.setLayoutManager(new ToolbarLayout(false));
		childrenFigure.setOpaque(true);

		add(timelineFigure); // first row
		add(childrenFigure); // spawned timelines stacked below
	}

	public IFigure getTimelineFigure() {
		return timelineFigure;
	}

	public LineFigure getLineFigure() {
		return lineFigure;
	}

	public Figure getSpawnedTimelinesPane() {
		return childrenFigure;
	}

	public void updateMaxNumberOfEvents(final int maxNumberOfEvents) {
		lineFigure.setMaxNumberOfEvents(maxNumberOfEvents);

		final int newWidth = maxNumberOfEvents * CommonFigureConstants.TOTAL_MARKER_SPACE;
		timelineFigure.setConstraint(lineFigure, new Rectangle(startPosition * CommonFigureConstants.TOTAL_MARKER_SPACE, 0,
				newWidth, CommonFigureConstants.MARKER_SIZE));

		revalidate();
		repaint();
	}

	@Override
	public Dimension getPreferredSize(final int wHint, final int hHint) {
		final Dimension timelineSize = lineFigure.getPreferredSize();
		final Dimension contentSize = childrenFigure.getPreferredSize();

		final int width = Math.max(startPosition * CommonFigureConstants.MARKER_SIZE + timelineSize.width, contentSize.width);
		final int height = timelineSize.height + contentSize.height + SPACING_BETWEEN_TIMELINES;

		return new Dimension(width, height);
	}

	@Override
	protected void paintFigure(final Graphics g) {
		timelineFigure.paint(g);
		childrenFigure.paint(g);
	}
}
