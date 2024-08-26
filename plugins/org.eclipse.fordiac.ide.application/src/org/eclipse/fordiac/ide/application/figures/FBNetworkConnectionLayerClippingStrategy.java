/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
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

import org.eclipse.draw2d.IClippingStrategy;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;

public class FBNetworkConnectionLayerClippingStrategy implements IClippingStrategy {

	private final GraphicalViewer viewer;

	public FBNetworkConnectionLayerClippingStrategy(final GraphicalViewer viewer) {
		this.viewer = viewer;
	}

	@Override
	public Rectangle[] getClip(final IFigure childFigure) {
		final Rectangle[] clipping = new Rectangle[] { childFigure.getBounds() };
		if (childFigure instanceof final FBNetworkConnection fbnConn) {
			clipping[0] = getgetFBNConnectionClippingRect(fbnConn);
		}
		return clipping;
	}

	private Rectangle getgetFBNConnectionClippingRect(final FBNetworkConnection fbnConn) {
		final EditPart ep = viewer.getEditPartForModel(fbnConn.getModel().getFBNetwork());
		if (ep instanceof final GraphicalEditPart fbnEP) {
			final IFigure fbnFigure = fbnEP.getFigure();
			final Rectangle fbnBounds = fbnFigure.getBounds().getCopy();
			fbnBounds.expand(fbnFigure.getInsets());
			fbnFigure.translateToAbsolute(fbnBounds);
			fbnConn.translateToRelative(fbnBounds);
			return fbnBounds;
		}
		return fbnConn.getBounds();
	}

}
