/*******************************************************************************
 * Copyright (c) 2026 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.debug.replaydebugging.ui.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.ToolbarLayout;

public class TooltipFigure extends Figure {

	private static final int PADDING = 4;
	private final Label label = new Label();

	public TooltipFigure(final String text) {
		setLayoutManager(new ToolbarLayout());
		setBorder(new LineBorder(ColorConstants.black, 1));
		setBackgroundColor(ColorConstants.tooltipBackground);
		setForegroundColor(ColorConstants.tooltipForeground);
		setOpaque(true);

		label.setText(text);
		label.setBorder(new MarginBorder(PADDING));
		add(label);
	}

	public void setText(final String text) {
		label.setText(text);
		repaint();
	}
}
