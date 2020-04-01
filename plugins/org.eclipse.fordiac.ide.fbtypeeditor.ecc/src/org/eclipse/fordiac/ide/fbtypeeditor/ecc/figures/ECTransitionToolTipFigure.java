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
 *   Bianca Wiesmayr, Ernst Blecha
 *     - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.figures;

import org.eclipse.draw2d.Border;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;

public class ECTransitionToolTipFigure extends FlowPage {

	private static final Border TOOLTIP_BORDER = new MarginBorder(2, 2, 2, 2);

	private ECTransition transition;

	private TextFlow tooltipContent = new TextFlow();

	public ECTransitionToolTipFigure() {
		setBorder(TOOLTIP_BORDER);

		add(tooltipContent);
	}

	public void setECTransition(ECTransition transition) {
		this.transition = transition;
		updateLabels();
	}

	private void updateLabels() {
		if ((null != transition.getComment()) && !transition.getComment().isEmpty()) {
			tooltipContent.setText(transition.getComment());
		} else {
			tooltipContent.setText("[not set]");
		}
		revalidate();
		repaint();
	}

}
