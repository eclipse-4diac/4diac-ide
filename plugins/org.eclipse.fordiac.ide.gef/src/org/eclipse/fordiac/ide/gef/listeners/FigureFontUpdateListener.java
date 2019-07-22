/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.listeners;

import org.eclipse.draw2d.IFigure;
import org.eclipse.jface.resource.JFaceResources;

public class FigureFontUpdateListener implements IFontUpdateListener {

	final IFigure figure;
	final String fontName;

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
