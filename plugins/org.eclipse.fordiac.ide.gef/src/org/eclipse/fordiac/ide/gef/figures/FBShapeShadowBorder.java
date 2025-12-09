/*******************************************************************************
 * Copyright (c) 2021, 2023 Johannes Kepler University Linz
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
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;

public class FBShapeShadowBorder extends AbstractShadowBorder {

	private static Rectangle topShadowRect = new Rectangle();
	private static Rectangle middleShadowRect = new Rectangle();
	private static Rectangle bottomShadowRect = new Rectangle();

	@Override
	public void paintBackground(final IFigure figure, final Graphics graphics, final Insets insets) {
		Assert.isTrue(figure instanceof FBShape);
		final FBShape fbShape = (FBShape) figure;

		final var backgroundColor = graphics.getBackgroundColor();
		final var alpha = graphics.getAlpha();
		graphics.setBackgroundColor(figure.getForegroundColor());

		topShadowRect.setBounds(fbShape.getTop().getBounds()).expand(2, 2);
		middleShadowRect.setBounds(fbShape.getMiddle().getBounds()).expand(2, 0);
		bottomShadowRect.setBounds(fbShape.getBottom().getBounds()).expand(2, 2);

		drawShadowHalo(graphics, topShadowRect, middleShadowRect, bottomShadowRect);
		drawDropShadow(graphics, topShadowRect, middleShadowRect, bottomShadowRect);

		graphics.setBackgroundColor(backgroundColor);
		graphics.setAlpha(alpha);
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
		final double horInc = 0.7; // emulate a roughly 30° shadow angle
		double horI = 0;
		for (int i = 0; i < SHADOW_SIZE; i++) {
			horI += horInc;
			topShadowRect.translate((int) horI, 1);
			middleShadowRect.translate((int) horI, 1);
			bottomShadowRect.translate((int) horI, 1);
			drawShadowFigure(graphics, topShadowRect, middleShadowRect, bottomShadowRect);
			if (horI >= 1.0) {
				horI -= 1.0;
			}
		}
	}

	private static void drawShadowFigure(final Graphics graphics, final Rectangle topShadowRect,
			final Rectangle middleShadowRect, final Rectangle bottomShadowRect) {
		graphics.fillRoundRectangle(topShadowRect, SHADOW_CORNER_RADIUS, SHADOW_CORNER_RADIUS);
		graphics.fillRectangle(middleShadowRect);
		graphics.fillRoundRectangle(bottomShadowRect, SHADOW_CORNER_RADIUS, SHADOW_CORNER_RADIUS);
	}

}
