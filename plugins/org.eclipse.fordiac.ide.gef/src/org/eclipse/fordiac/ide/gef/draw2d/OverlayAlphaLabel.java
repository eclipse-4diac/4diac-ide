/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.draw2d;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Image;

public class OverlayAlphaLabel extends UnderlineAlphaLabel {

	private Image overlayIcon;

	public OverlayAlphaLabel() {
	}

	public OverlayAlphaLabel(final String text) {
		super(text);
	}

	@Override
	protected void paintFigure(final Graphics graphics) {
		super.paintFigure(graphics);
		if (overlayIcon != null) {
			final Rectangle bounds = getBounds();
			graphics.translate(bounds.x, bounds.y);
			graphics.drawImage(overlayIcon, getIconLocation());
			graphics.translate(-bounds.x, -bounds.y);
		}
	}

	public Image getOverlayIcon() {
		return overlayIcon;
	}

	public void setOverlayIcon(final Image overlayIcon) {
		if (overlayIcon == this.overlayIcon) {
			return;
		}
		this.overlayIcon = overlayIcon;
		repaint();
	}
}
