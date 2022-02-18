/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.figures;

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.fordiac.ide.gef.figures.AbstractShadowBorder;
import org.eclipse.fordiac.ide.gef.listeners.IFontUpdateListener;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceConstants;
import org.eclipse.jface.resource.JFaceResources;

public class InstanceNameFigure extends Label implements IFontUpdateListener {

	public InstanceNameFigure() {
		super();
		setFont();
		setTextAlignment(PositionConstants.CENTER);
		setLabelAlignment(PositionConstants.CENTER);
		setBorder(new MarginBorder(0, 0, AbstractShadowBorder.SHADOW_INSETS.top + 2, 0));
	}

	private void setFont() {
		setFont(JFaceResources.getFontRegistry().getBold(PreferenceConstants.DIAGRAM_FONT));
	}

	@Override
	public void updateFonts() {
		setFont();
		invalidateTree();
		revalidate();
	}
}
