/*******************************************************************************
 * Copyright (c) 2011 Profactor GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
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

	public static final double WITH_DISTANCE = 10.0;

	private final int pos;
	private final EditPart editPart;

	public WithAnchor(final IFigure figure, final int pos, final EditPart editPart) {
		super(figure);
		this.pos = pos;
		this.editPart = editPart;
	}

	protected double getZoomFactor() {
		double zoom = 1.0;
		if (editPart.getRoot() instanceof final ScalableFreeformRootEditPart scaleableREP) {
			zoom = scaleableREP.getZoomManager().getZoom();
		}
		return zoom;
	}

	public int getPos() {
		return pos;
	}
}
