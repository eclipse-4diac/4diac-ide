/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011 Profactor GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr - make border equal width on all sides
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.draw2d;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.swt.SWT;

/** The Class AdvancedLineBorder: Allows selecting the directions, where a border is required */
public class AdvancedLineBorder extends LineBorder {

	private static final int ALPHA_MAX_VALUE = 255;

	private int side = PositionConstants.NONE;
	private int style = SWT.LINE_SOLID;
	private int alpha = ALPHA_MAX_VALUE;

	/**
	 * Sets the placement of line Valid values are (or any combinations)
	 * <UL>
	 * <LI>{@link PositionConstants#EAST}
	 * <LI>{@link PositionConstants#NORTH}
	 * <LI>{@link PositionConstants#SOUTH}
	 * <LI>{@link PositionConstants#WEST}
	 * <LI><EM>{@link PositionConstants#NONE}</EM>
	 * </UL>
	 * .
	 *
	 * @param side the side
	 */
	public AdvancedLineBorder(final int side) {
		super();
		this.side = side;
	}

	/**
	 * Sets the placement of line Valid values are (or any combinations)
	 * <UL>
	 * <LI>{@link PositionConstants#EAST}
	 * <LI>{@link PositionConstants#NORTH}
	 * <LI>{@link PositionConstants#SOUTH}
	 * <LI>{@link PositionConstants#WEST}
	 * <LI><EM>{@link PositionConstants#NONE}</EM>
	 * </UL>
	 * and the style
	 * <UL>
	 * <LI>{@link SWT#LINE_DASH}
	 * <LI>{@link SWT#LINE_DASHDOT}
	 * <LI>{@link SWT#LINE_DASHDOTDOT}
	 * <LI>{@link SWT#LINE_DOT}
	 * <LI>{@link SWT#LINE_SOLID}
	 * <LI><EM>{@link SWT#LINE_SOLID}</EM>
	 * </UL>
	 * .
	 *
	 * @param side  the side
	 * @param style the style
	 */
	public AdvancedLineBorder(final int side, final int style) {
		super();
		this.side = side;
		this.style = style;
	}

	/**
	 * A line border with no line ! see {@see AdvancedLineBorder(int side)}.
	 */
	public AdvancedLineBorder() {
		super();
	}

	@Override
	public Insets getInsets(final IFigure figure) {
		int north = 0;
		int east = 0;
		int south = 0;
		int west = 0;
		if ((side & PositionConstants.NORTH) != 0) {
			north = getWidth();
		}
		if ((side & PositionConstants.EAST) != 0) {
			east = getWidth();
		}
		if ((side & PositionConstants.SOUTH) != 0) {
			south = getWidth();
		}
		if ((side & PositionConstants.WEST) != 0) {
			west = getWidth();
		}

		return new Insets(north, west, south, east);
	}

	@Override
	public void paint(final IFigure figure, final Graphics graphics, final Insets insets) {
		graphics.setAlpha(alpha);
		tempRect.setBounds(getPaintRectangle(figure, insets));

		graphics.setLineWidth(getWidth());
		if (getColor() != null) {
			graphics.setForegroundColor(getColor());
		}

		graphics.setLineStyle(style);

		final int yLowerBound = tempRect.y + tempRect.height - getWidth() / 4;
		if ((side & PositionConstants.NORTH) != 0) {
			graphics.drawLine(tempRect.x, tempRect.y, tempRect.x + tempRect.width, tempRect.y);
		}
		if ((side & PositionConstants.EAST) != 0) {
			final int xRightBound = tempRect.x + tempRect.width - getWidth() / 4;
			graphics.drawLine(xRightBound, tempRect.y, xRightBound, yLowerBound);
		}
		if ((side & PositionConstants.SOUTH) != 0) {
			graphics.drawLine(tempRect.x, yLowerBound, tempRect.x + tempRect.width, yLowerBound);
		}
		if ((side & PositionConstants.WEST) != 0) {
			graphics.drawLine(tempRect.x, tempRect.y, tempRect.x, yLowerBound);
		}
	}

	@Override
	public void setStyle(final int style) {
		this.style = style;
	}

	public void setAlpha(final int alpha) {
		if (this.alpha != alpha) {
			this.alpha = alpha;
		}
	}

	public int getAlpha() {
		return alpha;
	}

	@Override
	public boolean isOpaque() {
		return alpha >= ALPHA_MAX_VALUE;
	}

}
