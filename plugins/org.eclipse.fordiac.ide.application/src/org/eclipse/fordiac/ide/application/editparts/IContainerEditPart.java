/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;

public interface IContainerEditPart extends GraphicalEditPart {

	GraphicalEditPart getContentEP();

	Rectangle getCommentBounds();

	int getCommentWidth();

	int getMinHeight();

	default Rectangle getMinContentBounds() {
		Rectangle selectionExtent = null;
		for (final GraphicalEditPart child : getContentEP().getChildren()) {
			final Rectangle fbBounds = getBoundsForEditPart(child);
			if (selectionExtent == null) {
				selectionExtent = fbBounds.getCopy();
			} else {
				selectionExtent.union(fbBounds);
			}
		}
		if (selectionExtent != null) {
			selectionExtent.setHeight(Math.max(selectionExtent.height, getMinHeight()));
			return selectionExtent;
		}
		return getDefaultContentBounds();
	}

	private static Rectangle getBoundsForEditPart(final GraphicalEditPart ep) {
		return ep.getFigure().getBounds().getCopy();
	}

	default Rectangle getDefaultContentBounds() {
		return new Rectangle(getContentEP().getFigure().getBounds().getTopLeft(), IFigure.MIN_DIMENSION);
	}

}
