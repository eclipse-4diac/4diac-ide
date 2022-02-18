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

public class BorderedRoundedRectangle extends RoundedRectangle {
	@Override
	public void paintFigure(final Graphics graphics) {
		// paint figure of shape does not check for background borders, needed for drop shadow
		if (getBorder() instanceof AbstractBackground) {
			((AbstractBackground) getBorder()).paintBackground(this, graphics, NO_INSETS);
		}
		super.paintFigure(graphics);
	}
}