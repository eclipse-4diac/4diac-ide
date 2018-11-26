/*******************************************************************************
 * Copyright (c) 2011 - 2016 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.draw2d;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.util.ColorHelper;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.widgets.Display;

public class AdvancedRoundedRectangle extends RoundedRectangle {

	protected int side = PositionConstants.NONE;
	private ZoomManager zoomManager;
	private Figure parent;
	private boolean useGradient = false;
	private Color color;
	
	public AdvancedRoundedRectangle(int side) {
		super();
		this.side = side;
	}

	public AdvancedRoundedRectangle(int side, ZoomManager zoomManager,
			Figure parent, boolean useGradient, Color color) {
		super();
		this.side = side;
		this.zoomManager = zoomManager;
		this.parent = parent;
		this.useGradient = useGradient;
		this.color = color;
	}

	public void setSide(int side){
		this.side = side;
	}

	@Override
	protected void fillShape(Graphics graphics) {
		if (useGradient) {
			setPattern(graphics, true);
		} else {
			super.fillShape(graphics);
		}
	}

	private void setPattern(Graphics graphics, boolean background) {
		Display display = Display.getCurrent();

		Rectangle boundingRect;
		if (parent != null) {
			boundingRect = parent.getBounds().getCopy();
		} else {
			boundingRect = getBounds().getCopy();
		}

		if (zoomManager != null) {
			boundingRect.scale(zoomManager.getZoom());
		}
		Point topLeft = boundingRect.getTopLeft();
		Point bottomRight = boundingRect.getBottomRight();

		Color first = ColorHelper.lighter(getBackgroundColor());

		Pattern pattern = new Pattern(display, topLeft.x, topLeft.y,
				bottomRight.x, bottomRight.y, first, getBackgroundColor());
		if (background) {
			graphics.setBackgroundPattern(pattern);
		} else {
			graphics.setForegroundPattern(pattern);
		}

		// graphics.fillRectangle(boundingRect);
		graphics.fillRoundRectangle(getBounds(), getCornerDimensions().width,
				getCornerDimensions().height);
		graphics.setBackgroundPattern(null);
		pattern.dispose();
		first.dispose();
	}

	@Override
	protected void outlineShape(Graphics graphics) {
		if(null != color){		
			graphics.setForegroundColor(color);
		}
		float lineInset = Math.max(1.0f, getLineWidthFloat()) / 2.0f;
		int inset1 = (int) Math.floor(lineInset);
		int inset2 = (int) Math.ceil(lineInset);

		Rectangle r = Rectangle.SINGLETON.setBounds(getBounds());
		r.x += inset1;
		r.y += inset1;
		r.width -= inset1 + inset2;
		r.height -= inset1 + inset2;

		int x = r.x;
		int y = r.y;

		int width = r.width;
		int height = r.height;
		int arcWidth = Math.max(0, getCornerDimensions().width
				- (int) lineInset);
		int arcHeight = Math.max(0, getCornerDimensions().height
				- (int) lineInset);

		if (width == 0 || height == 0)
			return;
		if (arcWidth == 0 || arcHeight == 0) {
			if ((side & PositionConstants.NORTH) != 0)
				graphics.drawLine(r.x, r.y, r.x + r.width, r.y);
			if ((side & PositionConstants.EAST) != 0)
				graphics.drawLine(r.x + r.width, r.y, r.x + r.width, r.y
						+ r.height);
			if ((side & PositionConstants.SOUTH) != 0)
				graphics.drawLine(r.x, r.y + r.height, r.x + r.width, r.y
						+ r.height);
			if ((side & PositionConstants.WEST) != 0)
				graphics.drawLine(r.x, r.y, r.x, r.y + r.height);
			if ((side & PositionConstants.NONE) != 0) {
				// nothing to do!
			}

			return;
		}
		if (width < 0) {
			x += width;
			width = -width;
		}
		if (height < 0) {
			y += height;
			height = -height;
		}
		if (arcWidth < 0)
			arcWidth = -arcWidth;
		if (arcHeight < 0)
			arcHeight = -arcHeight;
		if (arcWidth > width)
			arcWidth = width;
		if (arcHeight > height)
			arcHeight = height;

		if (arcWidth < width) {
			if ((side & PositionConstants.NORTH) != 0)
				graphics.drawLine(x + arcWidth / 2, y,
						x + width - arcWidth / 2, y);
			if ((side & PositionConstants.SOUTH) != 0)
				graphics.drawLine(x + arcWidth / 2, y + height, x + width
						- arcWidth / 2, y + height);
		}
		if (arcHeight < height) {
			if ((side & PositionConstants.WEST) != 0)
				graphics.drawLine(x, y + arcHeight / 2, x, y + height
						- arcHeight / 2);
			if ((side & PositionConstants.EAST) != 0)
				graphics.drawLine(x + width, y + arcHeight / 2, x + width, y
						+ height - arcHeight / 2);
		}
		if (arcWidth != 0 && arcHeight != 0) {

			if (side != PositionConstants.NONE) {

				graphics.drawArc(x, y, arcWidth, arcHeight, 90, 90);
				graphics.drawArc(x + width - arcWidth, y, arcWidth, arcHeight,
						0, 90);
				graphics.drawArc(x + width - arcWidth, y + height - arcHeight,
						arcWidth, arcHeight, 0, -90);
				graphics.drawArc(x, y + height - arcHeight, arcWidth,
						arcHeight, 180, 90);
			}
		}

	}
	
}
