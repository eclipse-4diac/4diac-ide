/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011 Profactor GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.draw2d;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.swt.SWT;

/**
 * The Class AdvancedLineBorder.
 * 
 * @author Gerhard Ebenhofer, gerhard.ebenhofer@profactor.at
 */
public class AdvancedLineBorder extends LineBorder {

	protected int side = PositionConstants.NONE;
	protected int style = SWT.LINE_SOLID;
	
	private int alpha = 255;

	/**
	 * Sets the placement of line Valid values are (or any combinations)
	 * <UL>
	 * <LI>{@link PositionConstants#EAST}
	 * <LI>{@link PositionConstants#NORTH}
	 * <LI>{@link PositionConstants#SOUTH}
	 * <LI>{@link PositionConstants#WEST}
	 * <LI><EM>{@link PositionConstants#NONE}</EM>
	 * </UL>.
	 * 
	 * @param side the side
	 */
	public AdvancedLineBorder(int side) {
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
	 * </UL>.
	 * 
	 * @param side the side
	 * @param style the style
	 */
	public AdvancedLineBorder(int side, int style) {
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

	/* (non-Javadoc)
	 * @see org.eclipse.draw2d.LineBorder#getInsets(org.eclipse.draw2d.IFigure)
	 */
	@Override
	public Insets getInsets(IFigure figure) {
		int north = 0;
		int east = 0;
		int south = 0;
		int west = 0;
		if ((side & PositionConstants.NORTH) != 0)
			north = getWidth();
		if ((side & PositionConstants.EAST) != 0)
			east = getWidth();
		if ((side & PositionConstants.SOUTH) != 0)
			south = getWidth();
		if ((side & PositionConstants.WEST) != 0)
			west = getWidth();

		return new Insets(north, west, south, east);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.draw2d.LineBorder#paint(org.eclipse.draw2d.IFigure, org.eclipse.draw2d.Graphics, org.eclipse.draw2d.geometry.Insets)
	 */
	@Override
	public void paint(IFigure figure, Graphics graphics, Insets insets) {
		graphics.setAlpha(alpha);
		tempRect.setBounds(getPaintRectangle(figure, insets));
		// if (getWidth() % 2 == 1) {
		// tempRect.width--;
		// tempRect.height--;
		// }
		// tempRect.shrink(getWidth() / 2, getWidth() / 2);
		graphics.setLineWidth(getWidth());
		if (getColor() != null)
			graphics.setForegroundColor(getColor());

		graphics.setLineStyle(style);
		
		if ((side & PositionConstants.NORTH) != 0)
			graphics.drawLine(tempRect.x, tempRect.y, tempRect.x
					+ tempRect.width - getWidth(), tempRect.y);
		if ((side & PositionConstants.EAST) != 0)
			graphics.drawLine(tempRect.x + tempRect.width - getWidth(),
					tempRect.y, tempRect.x + tempRect.width - getWidth(),
					tempRect.y + tempRect.height - getWidth());
		if ((side & PositionConstants.SOUTH) != 0)
			graphics.drawLine(tempRect.x, tempRect.y + tempRect.height
					- getWidth(), tempRect.x + tempRect.width, tempRect.y
					+ tempRect.height - getWidth());
		if ((side & PositionConstants.WEST) != 0)
			graphics.drawLine(tempRect.x, tempRect.y, tempRect.x, tempRect.y
					+ tempRect.height - getWidth());
		if ((side & PositionConstants.NONE) != 0) {
			// nothing to do!
		}

	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.draw2d.LineBorder#setStyle(int)
	 */
	@Override
	public void setStyle(int style) {
		this.style = style;
	}
	
	public void setAlpha(int alpha) {
		if (this.alpha != alpha) {
			this.alpha = alpha;

		}
	}
	
	public int getAlpha() {
		return alpha;
	}

	@Override
	public boolean isOpaque() {
		return alpha > 254;
	}
	

}
