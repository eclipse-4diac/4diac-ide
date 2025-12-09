/*******************************************************************************
 * Copyright (c) 2025 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.fordiac.ide.application.editparts.InstanceContract;
import org.eclipse.fordiac.ide.gef.figures.BorderedRoundedRectangle;
import org.eclipse.fordiac.ide.gef.figures.RoundedRectangleShadowBorder;
import org.eclipse.fordiac.ide.gef.preferences.GefPreferenceConstants;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.ui.preferences.UIPreferenceConstants;
import org.eclipse.fordiac.ide.ui.utils.ContractScanner;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Font;

public class InstanceContractFigure extends Figure {

	private static final int PADDING = 4;

	private final FlowPage flowPage;
	private String currentText;
	private final InstanceContract instanceContract;

	public InstanceContractFigure(final InstanceContract instanceContract) {
		final var main = new BorderedRoundedRectangle();
		main.setOutline(false);
		main.setCornerDimensions(new Dimension(GefPreferenceConstants.CORNER_DIM, GefPreferenceConstants.CORNER_DIM));
		main.setBorder(new RoundedRectangleShadowBorder());
		main.setLayoutManager(new StackLayout());

		flowPage = new FlowPage();

		final int lineHeight = (int) CoordinateConverter.INSTANCE.getLineHeight();
		int top = lineHeight / 2;
		final int bottom = top;
		if (top + bottom != lineHeight) {
			// we have a rounding error
			top += lineHeight - (top + bottom);
		}
		flowPage.setBorder(new MarginBorder(top, PADDING, bottom, PADDING));
		main.add(flowPage);

		setLayoutManager(new StackLayout());
		add(main);
		setText(instanceContract.getContract());
		this.instanceContract = instanceContract;
	}

	public void setText(final String text) {
		if (text != null && text.equals(currentText)) {
			return; // no need for expensive refresh
		}
		currentText = text;

		final Font boldFont = JFaceResources.getFontRegistry().getBold(UIPreferenceConstants.DIAGRAM_FONT);
		flowPage.removeAll();
		if (text != null) {
			final ContractScanner scan = new ContractScanner(text);

			for (final ContractScanner.Token t : scan) {
				final TextFlow tf = new TextFlow(t.value());

				switch (t.type()) {
				case COMMENT:
					tf.setForegroundColor(ContractScanner.COMMENT);
					break;
				case KEYWORD:
					tf.setForegroundColor(ContractScanner.KEYWORD);
					tf.setFont(boldFont);
					break;
				default:
					tf.setForegroundColor(ContractScanner.NORMAL);
					break;
				}
				flowPage.add(tf);
			}
		} else {
			flowPage.add(new TextFlow("")); //$NON-NLS-1$
		}
	}

	@Override
	public Dimension getPreferredSize(final int wHint, final int hHint) {
		final int w = CoordinateConverter.INSTANCE.iec61499ToScreen(instanceContract.getSubApp().getWidth());
		final int h = flowPage.getPreferredSize(w, -1).height;
		return new Dimension(w, h + PADDING * 2);
	}
}
