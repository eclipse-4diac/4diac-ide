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
package org.eclipse.fordiac.ide.gef.listeners;

import org.eclipse.draw2d.IFigure;
import org.eclipse.jface.resource.JFaceResources;

public class FigureFontUpdateListener implements IFontUpdateListener {

	private final IFigure figure;
	private final String fontName;

	public FigureFontUpdateListener(IFigure figure, String fontname) {
		this.figure = figure;
		this.fontName = fontname;
	}

	@Override
	public void updateFonts() {
		figure.setFont(JFaceResources.getFont(fontName));
		figure.invalidateTree();
		figure.revalidate();
	}

}
