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

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * A speech-bubble icon with a stem leading down to the commented event. The
 * tooltip shows the comment.
 */
public class CommentIconFigure extends Figure {

	public static final int WIDTH = CommonFigureConstants.MARKER_SIZE;
	private static final int BUBBLE_HEIGHT = CommonFigureConstants.MARKER_SIZE * 3 / 4;
	private static final int STEM_HEIGHT = CommonFigureConstants.MARKER_SIZE / 2;
	public static final int HEIGHT = BUBBLE_HEIGHT + STEM_HEIGHT;
	private static final int CORNER_ARC = CommonFigureConstants.MARKER_SIZE / 3;

	public CommentIconFigure(final String comment) {
		setOpaque(false);
		setComment(comment);
	}

	public void setComment(final String comment) {
		setToolTip(new TooltipFigure(comment));
	}

	@Override
	protected void paintFigure(final Graphics g) {
		final Rectangle area = getBounds();
		final int centerX = area.x + area.width / 2;
		final Rectangle bubble = new Rectangle(area.x, area.y, area.width - 1, BUBBLE_HEIGHT - 1);

		g.setLineWidth(2);
		g.setForegroundColor(ColorConstants.black);

		// stem from the bubble down to the bottom of the comment row, where the event
		// row starts
		g.drawLine(centerX, bubble.y + bubble.height, centerX, area.y + area.height);

		g.setLineWidth(1);
		g.setBackgroundColor(ColorConstants.white);
		g.fillRoundRectangle(bubble, CORNER_ARC, CORNER_ARC);
		g.drawRoundRectangle(bubble, CORNER_ARC, CORNER_ARC);

		// two lines of "text" make it read as a comment
		final int inset = area.width / 5;
		final int firstLineY = bubble.y + bubble.height / 3;
		final int secondLineY = bubble.y + bubble.height * 2 / 3;
		g.drawLine(bubble.x + inset, firstLineY, bubble.x + bubble.width - inset, firstLineY);
		g.drawLine(bubble.x + inset, secondLineY, bubble.x + bubble.width - inset, secondLineY);
	}
}
