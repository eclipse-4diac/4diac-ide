/*******************************************************************************
 * Copyright (c) 2008, 2009, 2017 Profactor GbmH, fortiss GmbH 
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
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
