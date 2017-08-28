/*******************************************************************************
 * Copyright (c) 2011 Profactor GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;

public class WithAnchor extends ChopboxAnchor {
	protected final int pos;
	protected final EditPart editPart;

	public WithAnchor(final IFigure figure, final int pos, final EditPart editPart) {
		super(figure);
		this.pos = pos;
		this.editPart = editPart;
	}

	protected double getZoomFactor(){
		double zoom = 1.0;
		if(editPart.getRoot() instanceof ScalableFreeformRootEditPart){
			zoom = ((ScalableFreeformRootEditPart)editPart.getRoot()).getZoomManager().getZoom();
		}
		return zoom;
	}
}
