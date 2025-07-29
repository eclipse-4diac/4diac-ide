/*******************************************************************************
 * Copyright (c) 2025 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.contracts.helpers;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

public class SWTPainter implements Painter {

	final GC gc;

	public SWTPainter(final GC gc) {
		this.gc = gc;
	}

	@Override
	public void setForeground(final Color color) {
		gc.setForeground(color);

	}

	@Override
	public void setBackground(final Color color) {
		gc.setBackground(color);

	}

	@Override
	public void setAlpha(final int alpha) {
		gc.setAlpha(alpha);
	}

	@Override
	public void drawLine(final int x1, final int y1, final int x2, final int y2) {
		gc.drawLine(x1, y1, x2, y2);
	}

	@Override
	public void drawRectangle(final int x, final int y, final int width, final int height) {
		gc.drawRectangle(x, y, width, height);
	}

	@Override
	public void fillRectangle(final int x, final int y, final int width, final int height) {
		gc.fillRectangle(x, y, width, height);
	}

	@Override
	public void fillPolygon(final int[] pointArray) {
		gc.fillPolygon(pointArray);
	}

	@Override
	public void drawTextCentered(final String string, final int x, final int y, final boolean isTransparent) {
		gc.drawText(string, x - gc.textExtent(string).x / 2, y, isTransparent);
	}
}
