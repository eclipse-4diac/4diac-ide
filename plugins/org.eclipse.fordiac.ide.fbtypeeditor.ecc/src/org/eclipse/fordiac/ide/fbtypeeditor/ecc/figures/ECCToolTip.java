/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Virendra Ashiwal
 *     - initial implementation
 *     - extracted common code from ECTransitionToolTipFigure and ECStateToolTipFigure
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.figures;

import org.eclipse.draw2d.Border;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.TextFlow;

public class ECCToolTip extends FlowPage {

	private static final Border TOOLTIP_BORDER = new MarginBorder(2, 2, 2, 2);

	private TextFlow tooltipContent = new TextFlow();

	public ECCToolTip() {
		setBorder(TOOLTIP_BORDER);
		add(tooltipContent);
	}

	protected void setLabel(String text) {
		if ((null != text) && !text.isEmpty()) {
			tooltipContent.setText(text);
		} else {
			tooltipContent.setText("[not set]");
		}
		revalidate();
		repaint();
	}

}
