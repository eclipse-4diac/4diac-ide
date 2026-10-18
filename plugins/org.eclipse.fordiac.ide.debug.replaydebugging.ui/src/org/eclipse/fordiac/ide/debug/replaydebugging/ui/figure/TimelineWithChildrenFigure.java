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

	/**
	 * First row: the comment row above the timeline row, without spacing between
	 * them.
	 */
	private final Figure timelineBlock;
	/**
	 * Holds one comment icon per commented event; has height 0 while no event is
	 * commented.
	 */
	private final Figure commentRow;
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

		// An XYLayout without children has preferred size 0x0, so the row takes no
		// space until an icon is added.
		commentRow = new Figure();
		commentRow.setLayoutManager(new XYLayout());

		timelineBlock = new Figure();
		final var blockLayout = new ToolbarLayout(false); // no spacing: an empty comment row must vanish completely
		blockLayout.setStretchMinorAxis(true);
		timelineBlock.setLayoutManager(blockLayout);
		timelineBlock.add(commentRow);
		timelineBlock.add(timelineFigure);

		childrenFigure = new Figure();

		childrenFigure.setLayoutManager(new ToolbarLayout(false));
		childrenFigure.setOpaque(true);

		add(timelineBlock); // first row: comment icons above the timeline
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

	public void setTooltipText(final String tooltipText) {
		if (tooltipText != null && !tooltipText.isEmpty()) {
			timelineFigure.setToolTip(new TooltipFigure(tooltipText));
		} else {
			timelineFigure.setToolTip(null);
		}
	}

	/**
	 * Adds the icon of the commented event with the given index to the comment row.
	 */
	public void addCommentIcon(final IFigure icon, final int eventIndex) {
		commentRow.add(icon,
				new Rectangle(getCommentIconX(eventIndex), 0, CommentIconFigure.WIDTH, CommentIconFigure.HEIGHT));
	}

	public void removeCommentIcon(final IFigure icon) {
		if (icon.getParent() == commentRow) {
			commentRow.remove(icon);
		}
	}

	/** Left edge of the icon of an event: centered over the event's marker. */
	private int getCommentIconX(final int eventIndex) {
		return (startPosition + eventIndex) * CommonFigureConstants.TOTAL_MARKER_SPACE
				+ (CommonFigureConstants.MARKER_SIZE - CommentIconFigure.WIDTH) / 2;
	}

	public void updateMaxNumberOfEvents(final int maxNumberOfEvents) {
		lineFigure.setMaxNumberOfEvents(maxNumberOfEvents);

		final int newWidth = maxNumberOfEvents * CommonFigureConstants.TOTAL_MARKER_SPACE;
		timelineFigure.setConstraint(lineFigure, new Rectangle(startPosition * CommonFigureConstants.TOTAL_MARKER_SPACE,
				0, newWidth, CommonFigureConstants.MARKER_SIZE));

		revalidate();
		repaint();
	}

	@Override
	public Dimension getPreferredSize(final int wHint, final int hHint) {
		final Dimension timelineSize = lineFigure.getPreferredSize();
		final Dimension contentSize = childrenFigure.getPreferredSize();

		final int width = Math.max(startPosition * CommonFigureConstants.TOTAL_MARKER_SPACE + timelineSize.width,
				contentSize.width);
		final int height = commentRow.getPreferredSize().height + timelineSize.height + contentSize.height
				+ SPACING_BETWEEN_TIMELINES;

		return new Dimension(width, height);
	}

	@Override
	protected void paintFigure(final Graphics g) {
		timelineFigure.paint(g);
		childrenFigure.paint(g);
	}
}
