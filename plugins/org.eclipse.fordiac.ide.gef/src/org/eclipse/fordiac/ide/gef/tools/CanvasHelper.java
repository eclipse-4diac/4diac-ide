/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.gef.tools;

import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.model.ui.editors.AdvancedScrollingGraphicalViewer;
import org.eclipse.gef.editparts.FreeformGraphicalRootEditPart;
import org.eclipse.swt.events.MouseEvent;

public final class CanvasHelper {

	public static void bindToContentPane(final MouseEvent me, final AdvancedScrollingGraphicalViewer viewer,
			final Insets border) {
		final Rectangle contentPaneBounds = viewer
				.translateBoundsToRoute(((FreeformGraphicalRootEditPart) viewer.getRootEditPart()).getContentPane());

		contentPaneBounds.shrink(getZooomedBorder(border, viewer));

		final org.eclipse.draw2d.geometry.Point viewLocation = viewer.getViewLocation();
		me.x += viewLocation.x;
		me.y += viewLocation.y;

		me.x = Math.max(me.x, contentPaneBounds.getTopLeft().x);
		me.y = Math.max(me.y, contentPaneBounds.getTopLeft().y);

		me.x = Math.min(me.x, contentPaneBounds.getBottomRight().x);
		me.y = Math.min(me.y, contentPaneBounds.getBottomRight().y);
		me.x -= viewLocation.x;
		me.y -= viewLocation.y;
	}

	private static Insets getZooomedBorder(final Insets border, final AdvancedScrollingGraphicalViewer viewer) {
		final double zoom = ((ZoomScalableFreeformRootEditPart) viewer.getRootEditPart()).getZoomManager().getZoom();

		final Insets zoomedBorder = new Insets((int) (border.top * zoom), (int) (border.left * zoom),
				(int) (border.bottom * zoom), (int) (border.right * zoom));
		// compensate rounding errors
		if (border.top != 0) {
			zoomedBorder.top = Math.max(zoomedBorder.top, 1);
		}
		if (border.top != 0) {
			zoomedBorder.left = Math.max(zoomedBorder.left, 1);
		}
		if (border.top != 0) {
			zoomedBorder.bottom = Math.max(zoomedBorder.bottom, 1);
		}
		if (border.top != 0) {
			zoomedBorder.right = Math.max(zoomedBorder.right, 1);
		}

		return zoomedBorder;
	}

	private CanvasHelper() {
		throw new UnsupportedOperationException("Utility class should not be instantiated!"); //$NON-NLS-1$
	}

}
