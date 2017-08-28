/*******************************************************************************
 * Copyright (c) 2012, 2013 TU Wien ACIN, Profactor GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.draw2d;

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.util.ColorHelper;
import org.eclipse.swt.graphics.Color;

public class FordiacFigureUtilities {
	
	public static void paintEtchedBorder(Graphics g, Rectangle r) {
		Color rgb = g.getBackgroundColor();
		Color shadow = ColorHelper.darker(rgb);
		Color highlight = ColorHelper.lighter(rgb);
		FigureUtilities.paintEtchedBorder(g, r, shadow, highlight);
		shadow.dispose();
		highlight.dispose();
	}

}
