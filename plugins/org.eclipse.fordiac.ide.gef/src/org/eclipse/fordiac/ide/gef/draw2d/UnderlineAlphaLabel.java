/*******************************************************************************
 * Copyright (c) 2013 fortiss GbmH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.draw2d;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

public class UnderlineAlphaLabel extends SetableAlphaLabel {

	private boolean drawUnderline = false;
	
	public UnderlineAlphaLabel() {
		super();
	}

	public UnderlineAlphaLabel(String text) {
		super(text);
	}

	public boolean isDrawUnderline() {
		return drawUnderline;
	}

	public void setDrawUnderline(boolean drawUnderline) {
		this.drawUnderline = drawUnderline;
		repaint();
	}
	
	@Override
	protected void paintFigure(Graphics graphics) {
		super.paintFigure(graphics);
		Rectangle bounds = getBounds();
		if(drawUnderline){
			graphics.translate(bounds.x, bounds.y);
			Point loc = new Point(getTextLocation());
			loc.y += bounds.height - 2;
			graphics.drawLine(loc.x(), loc.y(), bounds.width() - loc.x(), loc.y());
			graphics.translate(-bounds.x, -bounds.y);
		}
	}
	

}
