/*******************************************************************************
 * Copyright (c) 2011, 2012  Profactor GbmH, TU Wien ACIN
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
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
	}

	public SetableAlphaLabel(final String text) {
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
	 * Sets the alpha to the given value. Values may range from 0 to 255. A value of
	 * 0 is completely transparent.
	 *
	 * @param alpha
	 */
	public void setAlpha(final int alpha) {
		if (this.alpha != alpha) {
			this.alpha = alpha;
			repaint();
		}

	}

	@Override
	public void paint(final Graphics graphics) {
		graphics.setAlpha(alpha);
		super.paint(graphics);
	}

	@Override
	public void setTransparency(final int value) {
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
	protected void paintFigure(final Graphics graphics) {
		final Rectangle bounds = getBounds();
		if (isOpaque()) {
			graphics.fillRectangle(bounds);
		}
		if (getIcon() != null) {
			final var iconLocation = getIconLocation();
			graphics.drawImage(getIcon(), iconLocation.x + bounds.x, iconLocation.y + bounds.y);
		}
		final var textLocation = getTextLocation();
		final int tx = bounds.x + textLocation.x;
		final int ty = bounds.y + textLocation.y;
		if (!isEnabled()) {
			graphics.setForegroundColor(ColorConstants.buttonLightest);
			graphics.drawText(getSubStringText(), tx + 1, ty + 1);
			graphics.setForegroundColor(ColorConstants.buttonDarker);
		}
		graphics.drawText(getSubStringText(), tx, ty);
	}

	@Override
	protected String getTruncationString() {
		return "\u2026"; //$NON-NLS-1$
	}

}
