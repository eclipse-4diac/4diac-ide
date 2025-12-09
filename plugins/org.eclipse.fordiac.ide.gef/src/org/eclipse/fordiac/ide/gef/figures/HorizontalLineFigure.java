/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011 Profactor GbmH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * The Class HorizontalLineFigure.
 */
public class HorizontalLineFigure extends Figure {

	private static int lineThickness = 2;
	private static Rectangle rectBuffer = new Rectangle();

	/**
	 * Instantiates a new horizontal line figure.
	 *
	 * @param width the width
	 */
	public HorizontalLineFigure(final int width) {
		setMaximumSize(new Dimension(width, -1));
		setMinimumSize(new Dimension(width, -1));
		setPreferredSize(width, lineThickness);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.draw2d.Figure#paintFigure(org.eclipse.draw2d.Graphics)
	 */
	@Override
	public void paintFigure(final Graphics g) {
		g.setLineWidth(lineThickness);
		final Rectangle r = rectBuffer.setBounds(getBounds());
		r.y = r.y + r.height / 2;
		g.drawLine(r.getTopLeft(), r.getTopRight());
	}
}
