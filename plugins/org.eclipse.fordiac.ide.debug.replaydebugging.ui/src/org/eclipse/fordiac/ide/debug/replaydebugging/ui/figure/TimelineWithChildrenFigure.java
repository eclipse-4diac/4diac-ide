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
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.Timeline;

/**
 * @brief Figure that represents a timeline and its spawned timelines (children)
 */
public class TimelineWithChildrenFigure extends Figure {

	private final Figure timelineFigureWrapper;
	private final TimelineFigure timelineFigure;
	private int startPosition;
	private final Figure childrenFigure;
	private static final int SPACING_BETWEEN_TIMELINES = CommonConstants.MARKER_SIZE / 2;

	public TimelineWithChildrenFigure(final Timeline timeline) {
		final var parentTimeline = timeline.getParentTimeline();
		if (parentTimeline == null) {
			startPosition = 0;
		} else {
			startPosition = Timeline.getSpawnedTimelineGlobalEventNumber(timeline);
		}
		final var layout = new ToolbarLayout(false);
		layout.setSpacing(SPACING_BETWEEN_TIMELINES);
		layout.setStretchMinorAxis(true);
		setLayoutManager(layout);
		setOpaque(true);

		// we add a wrapper around the timeline figure to use an XYLayout and be able to
		// position it according to the start position of the timeline
		timelineFigureWrapper = new Figure();
		timelineFigureWrapper.setLayoutManager(new XYLayout());

		timelineFigure = new TimelineFigure(timeline);
		timelineFigureWrapper.add(timelineFigure,
				new Rectangle(startPosition * CommonConstants.TOTAL_MARKER_SPACE, 0, -1, -1));

		childrenFigure = new Figure();

		childrenFigure.setLayoutManager(new ToolbarLayout(false));
		childrenFigure.setOpaque(true);

		add(timelineFigureWrapper); // first row
		add(childrenFigure); // spawned timelines stacked below
	}

	public TimelineFigure getTimelineFigure() {
		return timelineFigure;
	}

	public Figure getChildrenFigure() {
		return childrenFigure;
	}

	@Override
	public Dimension getPreferredSize(final int wHint, final int hHint) {
		final Dimension timelineSize = timelineFigureWrapper.getPreferredSize();
		final Dimension contentSize = childrenFigure.getPreferredSize();

		final int width = Math.max(timelineSize.width, contentSize.width);
		final int height = timelineSize.height + contentSize.height + SPACING_BETWEEN_TIMELINES;

		return new Dimension(width, height);
	}

	@Override
	protected void paintFigure(final Graphics g) {
		timelineFigure.paint(g);
		childrenFigure.paint(g);
	}
}
