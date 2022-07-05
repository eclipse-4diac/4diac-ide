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
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;

public interface IContainerEditPart extends GraphicalEditPart {

	GraphicalEditPart getContentEP();

	Rectangle getCommentBounds();

	int getCommentWidth();

	default Rectangle getMinContentBounds() {
		Rectangle selectionExtent = null;
		for (final Object child : getContentEP().getChildren()) {
			if (child instanceof GraphicalEditPart) {
				final Rectangle fbBounds = getBoundsForEditPart((GraphicalEditPart) child);
				if (selectionExtent == null) {
					selectionExtent = fbBounds.getCopy();
				} else {
					selectionExtent.union(fbBounds);
				}
			}
		}
		return (selectionExtent != null) ? selectionExtent : getDefaultContentBounds();
	}

	private static Rectangle getBoundsForEditPart(final GraphicalEditPart ep) {
		return ep.getFigure().getBounds().getCopy();
	}

	static Rectangle getDefaultContentBounds() {
		return new Rectangle(new Point(0, 0), IFigure.MIN_DIMENSION);
	}

}
