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
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * @brief Figure representing a single line of a timeline of events in the
 *        replay debugging.
 *
 *        It is responsible for drawing the line. The line is drawn in black for
 *        the valid events, and gray for the invalid ones.
 */
public class LineFigure extends Figure {

	private static final int LINE_WITDH = 4;

	int maxNumberOfEvents;
	private int firstInvalid = 0;

	public LineFigure(final int maxNumberOfEvents) {
		this.maxNumberOfEvents = maxNumberOfEvents;
		setOpaque(true);
	}

	public void setFirstInvalid(final int firstInvalid) {
		this.firstInvalid = firstInvalid;
		repaint();
	}

	public void setMaxNumberOfEvents(final int maxNumberOfEvents) {
		this.maxNumberOfEvents = maxNumberOfEvents;
	}

	@Override
	public Dimension getPreferredSize(final int wHint, final int hHint) {
		final var width = maxNumberOfEvents * CommonConstants.TOTAL_MARKER_SPACE;
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

}