/*******************************************************************************
 * Copyright (c) 2008 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 *                    Primetals Technologies Austria GmbH,
 *                    Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *   - initial API and implementation and/or initial documentation
 *   Fabio Gandolfi - added Comments for Applications
 *   Alois Zoit     - extracted from FBNEtworkEditPart and adjust to new look
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.OrderedLayout;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.ui.preferences.UIPreferenceConstants;

public class CommentContainer extends Figure {

	public CommentContainer() {
		setBorder(new MarginBorder(5));
		final ToolbarLayout layout = new ToolbarLayout();
		layout.setMinorAlignment(OrderedLayout.ALIGN_CENTER);
		layout.setStretchMinorAxis(false);
		setOpaque(true);
		setBackgroundColor(UIPreferenceConstants.getPageCommentColor());
		setLayoutManager(layout);
	}

	@Override
	protected void paintFigure(final Graphics graphics) {
		super.paintFigure(graphics);
		// draw a line separator at the bottom
		paintBottomLine(graphics);
	}

	protected void paintBottomLine(final Graphics graphics) {
		final int alpha = graphics.getAlpha();
		final Rectangle bounds = getBounds();
		final int bottom = bounds.y + bounds.height - 1;
		graphics.setAlpha(25);
		graphics.drawLine(bounds.x, bottom, bounds.x + bounds.width, bottom);
		graphics.setAlpha(alpha);
	}

}
