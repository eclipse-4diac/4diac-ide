/*******************************************************************************
 * Copyright (c) 2013 fortiss GbmH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
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
	}

	public UnderlineAlphaLabel(final String text) {
		super(text);
	}

	public boolean isDrawUnderline() {
		return drawUnderline;
	}

	public void setDrawUnderline(final boolean drawUnderline) {
		this.drawUnderline = drawUnderline;
		repaint();
	}

	@Override
	protected void paintFigure(final Graphics graphics) {
		super.paintFigure(graphics);
		if (drawUnderline) {
			final Rectangle bounds = getBounds();
			final Point loc = getTextLocation();
			graphics.drawLine(bounds.x + loc.x(), bounds.x + loc.y() + bounds.height - 2, bounds.width() - loc.x(),
					loc.y());
		}
	}
}
