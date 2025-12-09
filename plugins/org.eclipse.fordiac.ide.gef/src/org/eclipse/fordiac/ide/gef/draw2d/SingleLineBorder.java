/*******************************************************************************
 * Copyright (c) 2020 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.gef.draw2d;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

public class SingleLineBorder extends MarginBorder {

	private static final Insets DEFAULT_INSETS = new Insets(1, 1, 1, 1);

	private final Color borderColor;

	public SingleLineBorder() {
		this(ColorConstants.buttonDarker);
	}

	public SingleLineBorder(final Color borderColor) {
		super(DEFAULT_INSETS);
		this.borderColor = borderColor;
	}

	@Override
	public boolean isOpaque() {
		return true;
	}

	@Override
	public void paint(final IFigure figure, final Graphics g, final Insets insets) {
		g.setLineStyle(Graphics.LINE_SOLID);
		g.setLineWidth(1);
		final Rectangle r = getPaintRectangle(figure, insets);
		r.resize(-1, -1);
		g.setForegroundColor(borderColor);
		g.drawRectangle(r);
	}

}