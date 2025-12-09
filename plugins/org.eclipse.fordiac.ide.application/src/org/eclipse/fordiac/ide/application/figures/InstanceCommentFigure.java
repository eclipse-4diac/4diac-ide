/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
 * 				 2020 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Daniel Lindhuber - altered for instance comment
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.ParagraphTextLayout;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.gef.listeners.IFontUpdateListener;

public class InstanceCommentFigure extends Figure implements IFontUpdateListener {

	public static final String EMPTY_COMMENT = "[" + Messages.InstanceCommentEditPart_EMPTY_COMMENT + "]"; //$NON-NLS-1$ //$NON-NLS-2$
	private final TextFlow textFlow;

	public InstanceCommentFigure() {
		final FlowPage flowPage = new FlowPage();
		textFlow = new TextFlow();
		textFlow.setLayoutManager(new ParagraphTextLayout(textFlow, ParagraphTextLayout.WORD_WRAP_SOFT));

		flowPage.add(textFlow);

		setLayoutManager(new StackLayout());
		add(flowPage);
		setText(null);
	}

	public void setText(final String s) {
		if ((s != null) && !"".equals(s)) { //$NON-NLS-1$
			textFlow.setText(s);
		} else {
			textFlow.setText(EMPTY_COMMENT);
		}
	}

	@Override
	public void updateFonts() {
		// no need to update fonts
	}

	public int getTextWidth() {
		return textFlow.getSize().width();
	}

}
