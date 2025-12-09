/*******************************************************************************
 * Copyright (c) 2008, 2025 Profactor GmbH, fortiss GmbH,
 *                          Johannes Kepler University Linz,
 *                          Primetals Technologies Germany GmbH
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
	private final Label nameLabel = new Label();

	public ECCToolTip() {
		setLayoutManager(new GridLayout());
		add(nameLabel);
		setConstraint(nameLabel, new GridData(PositionConstants.CENTER, PositionConstants.MIDDLE, true, true));

		final Figure line = new VerticalLineCompartmentFigure();
		add(line);
		setConstraint(line, new GridData(PositionConstants.CENTER, PositionConstants.MIDDLE, true, true));
		content.setLayoutManager(new ParagraphTextLayout(content, ParagraphTextLayout.WORD_WRAP_HARD));

		final FlowPage fp = new FlowPage();
		fp.add(content);
		line.add(fp);
		line.setConstraint(fp, new GridData(PositionConstants.CENTER, PositionConstants.MIDDLE, false, true));
	}

	protected void setLabel(final String name, final String comment) {
		nameLabel.setText(name);

		setLabelContent(comment);
	}

	private void setLabelContent(final String text) {
		if ((text != null) && (!text.isEmpty())) {
			content.setText(text);
		} else {
			content.setText("[not set]");
		}
	}

}
