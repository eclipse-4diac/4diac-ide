/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.figures;

import org.eclipse.draw2d.AbstractBackground;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;

public class BorderedRoundedRectangle extends RoundedRectangle {

	@Override
	protected void fillShape(final Graphics graphics) {
		// for bordered rectangles we are only allowed to draw inside the client area
		final Dimension cornerDims = getCornerDimensions();
		graphics.fillRoundRectangle(getClientArea(), cornerDims.width, cornerDims.height);
	}

	/** Duplicated from {@link #org.eclipse.draw2d.RoundedRectangle;}
	 *
	 * The change is that for bordered shapes we need to take the client area instead of the figure bounds. **/
	@Override
	protected void outlineShape(final Graphics graphics) {
		final float lineInset = Math.max(1.0f, getLineWidthFloat()) / 2.0f;
		final int inset1 = (int) Math.floor(lineInset);
		final int inset2 = (int) Math.ceil(lineInset);

		final Rectangle r = Rectangle.SINGLETON.setBounds(getClientArea());
		r.x += inset1;
		r.y += inset1;
		r.width -= inset1 + inset2;
		r.height -= inset1 + inset2;

		final Dimension cornerDims = getCornerDimensions();
		graphics.drawRoundRectangle(r, Math.max(0, cornerDims.width - (int) lineInset),
				Math.max(0, cornerDims.height - (int) lineInset));
	}

	@Override
	public void paintFigure(final Graphics graphics) {
		// paint figure of shape does not check for background borders, needed for drop shadow
		if (getBorder() instanceof AbstractBackground) {
			((AbstractBackground) getBorder()).paintBackground(this, graphics, NO_INSETS);
		}
		super.paintFigure(graphics);
	}
}