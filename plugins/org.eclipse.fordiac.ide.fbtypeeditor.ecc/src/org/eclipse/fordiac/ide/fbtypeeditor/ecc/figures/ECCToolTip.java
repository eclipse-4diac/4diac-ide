/*******************************************************************************
 * Copyright (c) 2008, 2009, 2016 Profactor GmbH, fortiss GmbH
 * 				 2019 Johannes Kepler University
 * 				 2020 Johannes Kepler University Linz
 * 				 2020 Primetals Technologies Germany GmbH
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
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - extracted common FB shape for interface and fbn editors
 *   Alexander Lumplecker
 *     - class extended to change Figure
 *     - methods added: setLabelState, setLabelTransition, setLabelContent
 *     - code extracted from class FBNetworkElementTooltipFigure
 *     - changes: instanceNameLabel to nameLabel, deleted craeteTypeAndVersionLabel
 *     - changed setLabelState and setLabelTransition to setLabel
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.ParagraphTextLayout;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.fordiac.ide.gef.figures.VerticalLineCompartmentFigure;

public class ECCToolTip extends Figure {

	private final TextFlow content = new TextFlow();

	private final FlowPage fp = new FlowPage();

	private final Label nameLabel = new Label();

	private final Figure line = new VerticalLineCompartmentFigure();

	public ECCToolTip() {
		setLayoutManager(new GridLayout());
	}

	protected void setLabel(String name, String comment) {
		nameLabel.setText(name);
		add(nameLabel);
		setConstraint(nameLabel, new GridData(PositionConstants.CENTER, PositionConstants.MIDDLE, true, true));

		setLabelContent(comment);
	}

	private void setLabelContent(String text) {
		add(line);
		setConstraint(line, new GridData(PositionConstants.CENTER, PositionConstants.MIDDLE, true, true));

		if ((text != null) && (text.length() > 0)) {
			content.setText(text);
		} else {
			content.setText("[not set]");
		}
		content.setLayoutManager(new ParagraphTextLayout(content, ParagraphTextLayout.WORD_WRAP_HARD));
		fp.add(content);
		line.add(fp);
		line.setConstraint(fp, new GridData(PositionConstants.CENTER, PositionConstants.MIDDLE, false, true));

		revalidate();
		repaint();
	}
}
