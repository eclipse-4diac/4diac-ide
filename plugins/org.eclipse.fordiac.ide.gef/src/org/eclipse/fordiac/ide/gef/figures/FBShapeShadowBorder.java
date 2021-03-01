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

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.AbstractBackground;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;

class FBShapeShadowBorder extends AbstractBackground {

	private static final int SHADOW_ALPHA = 25;

	private static final int SHADOW_SIZE = 6;

	protected static final Insets SHADOW_INSETS = new Insets(2, 2, SHADOW_SIZE, SHADOW_SIZE * 2 / 3);

	@Override
	public Insets getInsets(final IFigure figure) {
		return SHADOW_INSETS;
	}

	@Override
	public boolean isOpaque() {
		return true;
	}

	@Override
	public void paintBackground(final IFigure figure, final Graphics graphics, final Insets insets) {
		Assert.isTrue(figure instanceof FBShape);
		final FBShape fbShape = (FBShape) figure;

		graphics.pushState();
		graphics.setBackgroundColor(ColorConstants.black);

		final Rectangle topShadowRect = fbShape.getTop().getBounds().getExpanded(2, 2);
		final Rectangle middleShadowRect = fbShape.getMiddle().getBounds().getExpanded(2, 0);
		final Rectangle bottomShadowRect = fbShape.getBottom().getBounds().getExpanded(2, 2);

		final Rectangle clipRect = topShadowRect.getCopy();
		clipRect.union(middleShadowRect);
		clipRect.union(bottomShadowRect);
		clipRect.width += SHADOW_SIZE;
		clipRect.height += SHADOW_SIZE;
		graphics.setClip(clipRect);

		drawShadowHalo(graphics, topShadowRect, middleShadowRect, bottomShadowRect);

		drawDropShadow(graphics, topShadowRect, middleShadowRect, bottomShadowRect);

		graphics.popState();
	}

	private static void drawShadowHalo(final Graphics graphics, final Rectangle topShadowRect,
			final Rectangle middleShadowRect, final Rectangle bottomShadowRect) {
		graphics.setAlpha(SHADOW_ALPHA);
		drawShadowFigure(graphics, topShadowRect, middleShadowRect, bottomShadowRect);
		topShadowRect.shrink(1, 1);
		middleShadowRect.shrink(1, 0);
		bottomShadowRect.shrink(1, 1);
		graphics.setAlpha(2 * SHADOW_ALPHA);
		drawShadowFigure(graphics, topShadowRect, middleShadowRect, bottomShadowRect);
	}

	private static void drawDropShadow(final Graphics graphics, final Rectangle topShadowRect,
			final Rectangle middleShadowRect, final Rectangle bottomShadowRect) {
		graphics.setAlpha(SHADOW_ALPHA);
		final double horInc = 0.8;  // emulate a roughly 30° shadow angle
		double horI = 0;
		for (int i = 0; i < SHADOW_SIZE; i++) {
			horI += horInc;
			topShadowRect.translate((int) horI, 1);
			middleShadowRect.translate((int) horI, 1);
			bottomShadowRect.translate((int) horI, 1);
			drawShadowFigure(graphics, topShadowRect, middleShadowRect, bottomShadowRect);
			if (horI > 1.0) {
				horI -= 1.0;
			}
		}
	}

	private static void drawShadowFigure(final Graphics graphics, final Rectangle topShadowRect,
			final Rectangle middleShadowRect, final Rectangle bottomShadowRect) {
		graphics.fillRoundRectangle(topShadowRect, DiagramPreferences.CORNER_DIM, DiagramPreferences.CORNER_DIM);
		graphics.fillRectangle(middleShadowRect);
		graphics.fillRoundRectangle(bottomShadowRect, DiagramPreferences.CORNER_DIM, DiagramPreferences.CORNER_DIM);
	}


}
