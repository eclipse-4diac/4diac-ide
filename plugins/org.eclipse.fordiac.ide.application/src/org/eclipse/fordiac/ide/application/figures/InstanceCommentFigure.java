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

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.fordiac.ide.gef.listeners.IFontUpdateListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

public class InstanceCommentFigure extends Label implements IFontUpdateListener {

	private static final int BORDER_WIDTH = 3;
	private static final Insets BORDER_INSET = new Insets(BORDER_WIDTH);

	public InstanceCommentFigure() {
		super();
		setTextAlignment(PositionConstants.CENTER);
		setLabelAlignment(PositionConstants.CENTER);
		setText("[empty comment]");
		setBorder(new LineBorder(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY)) {
			@Override
			public Insets getInsets(final IFigure figure) {
				return BORDER_INSET;
			}
		});

	}

	@Override
	public void setText(String s) {
		if ((s != null) && !"".equals(s)) { //$NON-NLS-1$
			super.setText(s);
		} else {
			super.setText("[empty comment]");
		}
	}

	@Override
	public void updateFonts() {
		// no need to update fonts
	}

}
