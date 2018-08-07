/*******************************************************************************
 * Copyright (c) 2011, 2012  Profactor GbmH, TU Wien ACIN
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.draw2d;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;

public class SetableAlphaLabel extends Label implements ITransparencyFigure {

	private int alpha = 255;

	public SetableAlphaLabel() {
		super();
	}

	public SetableAlphaLabel(String text) {
		super(text);
	}

	/**
	 * Returns the current alpha value.
	 * 
	 * @return the alpha value
	 */
	public int getAlpha() {
		return alpha;
	}

	/**
	 * Sets the alpha to the given value. Values may range from 0 to 255. A
	 * value of 0 is completely transparent.
	 * 
	 * @param alpha
	 */
	public void setAlpha(int alpha) {
		if (this.alpha != alpha) {
			this.alpha = alpha;
			repaint();
		}

	}

	@Override
	public void paint(Graphics graphics) {
		graphics.setAlpha(alpha);
		super.paint(graphics);
	}

	@Override
	public void setTransparency(int value) {
		setAlpha(value);
	}

	@Override
	public int getTransparency() {
		return getAlpha();
	}
	
	/**
	 * @see Figure#paintFigure(Graphics)
	 */
	@Override
	protected void paintFigure(Graphics graphics) {
		graphics.setAlpha(getAlpha());
		if(isOpaque()){
			graphics.fillRectangle(getBounds());
		}

		Rectangle bounds = getBounds();
		graphics.translate(bounds.x, bounds.y);
		if (getIcon() != null)
			graphics.drawImage(getIcon(), getIconLocation());
		if (!isEnabled()) {
			graphics.translate(1, 1);
			graphics.setForegroundColor(ColorConstants.buttonLightest);
			graphics.drawText(getSubStringText(), getTextLocation());
			graphics.translate(-1, -1);
			graphics.setForegroundColor(ColorConstants.buttonDarker);
		}
		graphics.drawText(getSubStringText(), getTextLocation());
		graphics.translate(-bounds.x, -bounds.y);
	}


}
