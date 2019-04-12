/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz (JKU) 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.figures;

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.widgets.Display;

public class GradientLabel extends Label {
	
	final ZoomManager zoomManger;
	
	public GradientLabel(ZoomManager zoomManger) {
		super();
		this.zoomManger = zoomManger;
	}

	@Override
	protected void paintFigure(Graphics graphics) {
		Display display = Display.getCurrent();
		Rectangle boundingRect = getBounds().getCopy();
		boundingRect.scale(zoomManger.getZoom());
		
		Point topLeft = boundingRect.getTopLeft();
		Point bottomRight = boundingRect.getBottomRight();
		Color first = FigureUtilities.lighter(getBackgroundColor());
		Pattern pattern = new Pattern(display, topLeft.x, topLeft.y, bottomRight.x, bottomRight.y, first,
				getBackgroundColor());
		graphics.setBackgroundPattern(pattern);
		graphics.fillRectangle(getBounds());
		graphics.setBackgroundPattern(null);
		pattern.dispose();
		first.dispose();
		graphics.translate(bounds.x, bounds.y);
		graphics.drawText(getSubStringText(), getTextLocation());
		graphics.translate(-bounds.x, -bounds.y);
	}
}
