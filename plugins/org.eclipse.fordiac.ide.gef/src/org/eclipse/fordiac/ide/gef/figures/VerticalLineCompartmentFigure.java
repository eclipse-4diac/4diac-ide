/*******************************************************************************
 * Copyright (c) 2008, 2009, 2017 Profactor GbmH, fortiss GmbH 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.OrderedLayout;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.fordiac.ide.gef.draw2d.AdvancedLineBorder;

/**
 * The Class VerticalLineFigure.
 */
public class VerticalLineCompartmentFigure extends Figure {

	public VerticalLineCompartmentFigure() {
		ToolbarLayout layout = new ToolbarLayout();
		layout.setMinorAlignment(OrderedLayout.ALIGN_TOPLEFT);
		layout.setStretchMinorAxis(false);
		setLayoutManager(layout);
		setBorder(new AdvancedLineBorder(PositionConstants.NORTH));
	}

}
