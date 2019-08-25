/*******************************************************************************
 * Copyright (c) 2012, 2013 TU Wien ACIN, Profactor GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
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

public final class FordiacFigureUtilities {
	
	public static void paintEtchedBorder(Graphics g, Rectangle r) {
		Color rgb = g.getBackgroundColor();
		Color shadow = ColorHelper.darker(rgb);
		Color highlight = ColorHelper.lighter(rgb);
		FigureUtilities.paintEtchedBorder(g, r, shadow, highlight);
		shadow.dispose();
		highlight.dispose();
	}

	private FordiacFigureUtilities() {
		throw new UnsupportedOperationException("FordiacFigureUtilities utility class should not be instantiated!"); //$NON-NLS-1$
	}
}
