/*******************************************************************************
 * Copyright (c) 2014 fortiss GmbH
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
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.figures;

import org.eclipse.draw2d.Border;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm;

public class ECAlgorithmToolTipFigure extends FlowPage {

	private final Border TOOLTIP_BORDER = new MarginBorder(2, 2, 2, 2);

	private Algorithm alg;

	private TextFlow algContent;

	public ECAlgorithmToolTipFigure() {
		setBorder(TOOLTIP_BORDER);

		algContent = new TextFlow();
		add(algContent);

	}

	@Override
	public Dimension getPreferredSize(int w, int h) {
		Dimension d = super.getPreferredSize(-1, -1);
		boolean update = false;
		if (d.width > 350) {
			d.width = 350;
			update = true;
		}
		if (d.height > 250) {
			d.height = 250;
			update = true;
		}
		if (update) {
			d = super.getPreferredSize(d.width, d.height);
		}
		return d;
	}

	public void setAlgorithm(Algorithm alg) {
		this.alg = alg;
		updateLabels();
	}

	private void updateLabels() {
		if (null != alg) {
			if (alg instanceof TextAlgorithm) {
				algContent.setText(((TextAlgorithm) alg).getText());
			} else {
				algContent.setText(""); //$NON-NLS-1$
			}
			revalidate();
			repaint();
		}
	}

}
