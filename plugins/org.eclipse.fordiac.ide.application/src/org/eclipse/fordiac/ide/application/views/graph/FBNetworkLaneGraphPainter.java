/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.views.graph;

import java.util.Set;

import org.eclipse.fordiac.ide.model.graph.FBNetworkLaneGraph;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Tree;

/**
 * A painter for lane graphs in an FB network.
 */
public class FBNetworkLaneGraphPainter {

	private static final int LINE_WIDTH = 2;
	private static final int BORDER_INSET = 2;
	private static final int BORDER_WIDTH = 1;
	private static final int BORDER_CORNER_RADIUS = 8;
	private static final int CHEVRON_LENGTH = LINE_WIDTH * 3;
	private static final int CHEVRON_WIDTH = LINE_WIDTH * 2;

	private static final String LANE_COLOR_KEY_PREFIX = "org.eclipse.fordiac.ide.application.chart.series."; //$NON-NLS-1$
	private static final int LANE_COLOR_COUNT = 24;

	private final Tree tree;
	private final Color borderColor;
	private final Color dotFillColor;
	private final Color dotOutlineColor;

	/**
	 * Create a new painter for lane graphs in an FB network
	 *
	 * @param tree the tree
	 */
	public FBNetworkLaneGraphPainter(final Tree tree) {
		this.tree = tree;

		borderColor = tree.getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY);
		dotFillColor = tree.getDisplay().getSystemColor(SWT.COLOR_GRAY);
		dotOutlineColor = tree.getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY);
	}

	/**
	 * Paint a lane graph node
	 *
	 * @param event the event
	 */
	public void paint(final Event event) {
		if (!(event.item.getData() instanceof final FBNetworkLaneGraph<?, ?>.LaneNode node)) {
			return;
		}

		final GC gc = event.gc;
		final Color originalForeground = gc.getForeground();
		final Color originalBackground = gc.getBackground();
		final int originalLineWidth = gc.getLineWidth();
		final int originalLineCap = gc.getLineCap();
		final int originalLineJoin = gc.getLineJoin();

		try {
			enableAntialias(gc);
			gc.setLineCap(SWT.CAP_ROUND);
			gc.setLineJoin(SWT.JOIN_ROUND);
			paintNode(event, node);
		} finally {
			gc.setForeground(originalForeground);
			gc.setBackground(originalBackground);
			gc.setLineWidth(originalLineWidth);
			gc.setLineCap(originalLineCap);
			gc.setLineJoin(originalLineJoin);
		}
	}

	/**
	 * Measure the bounds of a lane graph node
	 *
	 * @param event the event
	 */
	@SuppressWarnings("static-method")
	public void measure(final Event event) {
		if (!(event.item.getData() instanceof final FBNetworkLaneGraph<?, ?>.LaneNode node)) {
			return;
		}

		final int depth = node.getGraph().getDepth();
		final int laneCount = node.getGraph().getLaneCount();
		final int laneWidth = laneWidth(event.height);
		event.width = (laneCount + depth + 1) * laneWidth;
	}

	private void paintNode(final Event event, final FBNetworkLaneGraph<?, ?>.LaneNode node) {
		final GC gc = event.gc;
		final int rowTop = event.y;
		final int rowBottom = event.y + event.height;
		final int rowCenterY = rowTop + (event.height / 2);
		final int laneWidth = laneWidth(event.height);

		final FBNetworkLaneGraph<?, ?>.Lane lane = node.getLane();
		final int laneCenterX = laneCenterX(event.x, laneWidth, lane);
		final Color laneColor = laneColor(lane);

		// draw subgraph borders
		if (hasParent(node)) {
			drawBorder(gc, event.x, laneWidth, rowTop, rowBottom, node);
		}

		// draw current lane
		if (node.hasLaneEntry()) {
			drawLine(gc, laneCenterX, rowCenterY, laneCenterX, rowTop, laneColor);
		}
		if (node.hasLaneExit()) {
			drawLine(gc, laneCenterX, rowCenterY, laneCenterX, rowBottom, laneColor);
		}

		// draw joining, forking, and passing lanes
		drawLines(gc, event.x, laneWidth, laneCenterX, rowCenterY, rowTop, node.getJoiningLanes());
		drawLines(gc, event.x, laneWidth, laneCenterX, rowCenterY, rowBottom, node.getForkingLanes());
		drawLines(gc, event.x, laneWidth, rowTop, rowBottom, node.getPassingLanes());
		if (node.hasSelfLoop()) {
			drawSelfLoop(gc, laneCenterX, rowCenterY, rowTop, laneColor);
		}

		// draw dot for node
		drawDot(gc, laneCenterX, rowCenterY, laneWidth);
	}

	private void drawBorder(final GC gc, final int rowLeft, final int laneWidth, final int rowTop, final int rowBottom,
			final FBNetworkLaneGraph<?, ?>.LaneNode node) {
		final FBNetworkLaneGraph<?, ?> graph = node.getGraph();
		final FBNetworkLaneGraph<?, ?>.LaneNode parent = graph.getParent();
		final FBNetworkLaneGraph<?, ?> parentGraph = parent.getGraph();
		final int left = rowLeft + (parentGraph.getLaneCount() + parentGraph.getDepth() + 1) * laneWidth + BORDER_INSET;
		final int right = rowLeft + (graph.getLaneCount() + graph.getDepth() + 1) * laneWidth - BORDER_INSET;
		final int top = rowTop + BORDER_INSET;
		final int bottom = rowBottom - BORDER_INSET;
		final int radius = Math.clamp((right - left) / 2, LINE_WIDTH, BORDER_CORNER_RADIUS);
		final boolean isFirstNode = graph.getSortedNodes().getFirst() == node;
		final boolean isLastNode = graph.getSortedNodes().getLast() == node;

		gc.setForeground(borderColor);
		gc.setLineWidth(BORDER_WIDTH);

		if (isFirstNode && isLastNode) {
			drawTopBorder(gc, left, right, top, radius);
			drawBottomBorder(gc, left, right, bottom, radius);
			drawSideBorders(gc, top + radius, bottom - radius, left, right);
		} else if (isFirstNode) {
			drawTopBorder(gc, left, right, top, radius);
			drawSideBorders(gc, top + radius, rowBottom, left, right);
		} else if (isLastNode) {
			drawBottomBorder(gc, left, right, bottom, radius);
			drawSideBorders(gc, rowTop, bottom - radius, left, right);
		} else {
			drawSideBorders(gc, rowTop, rowBottom, left, right);
		}

		if (hasParent(parent)) {
			drawParentBorder(gc, rowLeft, laneWidth, rowTop, rowBottom, parent);
		}
	}

	private void drawParentBorder(final GC gc, final int rowLeft, final int laneWidth, final int rowTop,
			final int rowBottom, final FBNetworkLaneGraph<?, ?>.LaneNode node) {
		final FBNetworkLaneGraph<?, ?> graph = node.getGraph();
		final FBNetworkLaneGraph<?, ?>.LaneNode parent = graph.getParent();
		final FBNetworkLaneGraph<?, ?> parentGraph = parent.getGraph();
		final int left = rowLeft + (parentGraph.getLaneCount() + parentGraph.getDepth() + 1) * laneWidth + BORDER_INSET;
		final int right = rowLeft + (graph.getLaneCount() + graph.getDepth() + 1) * laneWidth - BORDER_INSET;
		final boolean isLastNode = graph.getSortedNodes().getLast() == node;

		if (!isLastNode) {
			gc.setForeground(borderColor);
			gc.setLineWidth(BORDER_WIDTH);
			drawSideBorders(gc, rowTop, rowBottom, left, right);
		}

		if (hasParent(parent)) {
			drawParentBorder(gc, rowLeft, laneWidth, rowTop, rowBottom, parent);
		}
	}

	private static void drawTopBorder(final GC gc, final int left, final int right, final int top, final int radius) {
		gc.drawLine(left + radius, top, right - radius, top);
		gc.drawArc(left, top, 2 * radius, 2 * radius, 90, 90);
		gc.drawArc(right - 2 * radius, top, 2 * radius, 2 * radius, 0, 90);
	}

	private static void drawBottomBorder(final GC gc, final int left, final int right, final int bottom,
			final int radius) {
		gc.drawLine(left + radius, bottom, right - radius, bottom);
		gc.drawArc(left, bottom - 2 * radius, 2 * radius, 2 * radius, 180, 90);
		gc.drawArc(right - 2 * radius, bottom - 2 * radius, 2 * radius, 2 * radius, 270, 90);
	}

	private static void drawSideBorders(final GC gc, final int top, final int bottom, final int left, final int right) {
		gc.drawLine(left, top, left, bottom);
		gc.drawLine(right, top, right, bottom);
	}

	private void drawLines(final GC gc, final int rowLeft, final int laneWidth, final int nodeCenterX,
			final int rowCenterY, final int connectorEnd, final Set<? extends FBNetworkLaneGraph<?, ?>.Lane> lanes) {
		for (final var lane : lanes) {
			final Color laneColor = laneColor(lane);
			final int laneCenterX = laneCenterX(rowLeft, laneWidth, lane);
			gc.setForeground(laneColor);
			gc.setLineWidth(LINE_WIDTH);

			final int cornerX;
			// draw horizontal line to corner if distance is greater than lane width
			if (Math.abs(laneCenterX - nodeCenterX) > laneWidth) {
				if (nodeCenterX < laneCenterX) {
					cornerX = laneCenterX - laneWidth / 2;
				} else {
					cornerX = laneCenterX + laneWidth / 2;
				}

				gc.drawLine(nodeCenterX, rowCenterY, cornerX, rowCenterY);
			} else {
				cornerX = nodeCenterX;
			}

			// draw corner
			if (lane.isBackward()) {
				drawArc(gc, cornerX, rowCenterY, laneCenterX, connectorEnd);
			} else {
				gc.drawLine(cornerX, rowCenterY, laneCenterX, connectorEnd);
			}
		}
	}

	private void drawLines(final GC gc, final int rowLeft, final int laneWidth, final int top, final int bottom,
			final Set<? extends FBNetworkLaneGraph<?, ?>.Lane> lanes) {
		for (final var lane : lanes) {
			final Color laneColor = laneColor(lane);
			final int laneCenterX = laneCenterX(rowLeft, laneWidth, lane);

			if (lane.isBackward()) {
				drawVerticalLineWithChevron(gc, laneCenterX, top, bottom, laneColor);
			} else {
				drawLine(gc, laneCenterX, top, laneCenterX, bottom, laneColor);
			}
		}
	}

	private static void drawLine(final GC gc, final int x1, final int y1, final int x2, final int y2,
			final Color color) {
		gc.setForeground(color);
		gc.setLineWidth(LINE_WIDTH);
		gc.drawLine(x1, y1, x2, y2);
	}

	private static void drawVerticalLineWithChevron(final GC gc, final int x, final int top, final int bottom,
			final Color color) {
		final int chevronTipY = top + (bottom - top) / 2 + LINE_WIDTH;
		final int chevronBottomY = chevronTipY + CHEVRON_LENGTH;

		gc.setForeground(color);
		gc.setLineWidth(LINE_WIDTH);
		gc.drawLine(x, top, x, chevronTipY);
		gc.drawLine(x, chevronTipY, x - CHEVRON_WIDTH, chevronBottomY);
		gc.drawLine(x, chevronTipY, x + CHEVRON_WIDTH, chevronBottomY);
		gc.drawLine(x, chevronBottomY, x, bottom);
	}

	private static void drawArc(final GC gc, final int x1, final int y1, final int x2, final int y2) {
		final int rx = Math.abs(x2 - x1);
		final int ry = Math.abs(y2 - y1);

		final int startAngle;
		if (x2 > x1) {
			if (y1 < y2) {
				startAngle = 0;
			} else {
				startAngle = 270;
			}
		} else if (y1 < y2) {
			startAngle = 90;
		} else {
			startAngle = 180;
		}

		gc.drawArc(x1 - rx, y2 - ry, 2 * rx, 2 * ry, startAngle, 90);
	}

	private static void drawSelfLoop(final GC gc, final int centerX, final int centerY, final int rowTop,
			final Color color) {
		final int diameter = centerY - rowTop - LINE_WIDTH;
		gc.setForeground(color);
		gc.setLineWidth(LINE_WIDTH);
		gc.drawArc(centerX, rowTop + LINE_WIDTH, diameter, diameter, -90, 270);
	}

	private void drawDot(final GC gc, final int centerX, final int centerY, final int laneWidth) {
		final int dotSize = dotSize(laneWidth);
		final int x = centerX - (dotSize / 2);
		final int y = centerY - (dotSize / 2);
		gc.setBackground(dotFillColor);
		gc.fillOval(x, y, dotSize, dotSize);
		gc.setForeground(dotOutlineColor);
		gc.setLineWidth(LINE_WIDTH);
		gc.drawOval(x, y, dotSize, dotSize);
	}

	private static int dotSize(final int laneWidth) {
		// half of rowHeight, rounded up to next even number, minus line width
		return ((laneWidth / 2 + 1) & ~1) - LINE_WIDTH;
	}

	private Color laneColor(final FBNetworkLaneGraph<?, ?>.Lane lane) {
		if (lane != null) {
			final String key = LANE_COLOR_KEY_PREFIX + (lane.getPosition() % LANE_COLOR_COUNT + 1);
			final Color color = JFaceResources.getColorRegistry().get(key);
			if (color != null) {
				return color;
			}
		}
		return tree.getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY);
	}

	private static int laneWidth(final int rowHeight) {
		// half of rowHeight, rounded up to next even number, plus 2
		return ((rowHeight / 2 + 1) & ~1) + 2;
	}

	private static int laneCenterX(final int rowLeft, final int laneWidth, final FBNetworkLaneGraph<?, ?>.Lane lane) {
		if (lane != null) {
			return rowLeft + (lane.getPosition() + lane.getGraph().getDepth() + 1) * laneWidth;
		}
		return rowLeft + laneWidth;
	}

	private static void enableAntialias(final GC gc) {
		try {
			gc.setAntialias(SWT.ON);
			gc.setTextAntialias(SWT.ON);
		} catch (final SWTException e) {
			// some SWT backends do not support anti-aliasing
		}
	}

	private static boolean hasParent(final FBNetworkLaneGraph<?, ?>.LaneNode node) {
		return node.getGraph().getParent() != null;
	}
}
