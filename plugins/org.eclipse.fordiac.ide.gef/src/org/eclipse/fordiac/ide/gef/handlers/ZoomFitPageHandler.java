/*******************************************************************************
 * Copyright (c) 2021, 2024 Johannes Kepler University Linz,
 *                          Primetals Technology Austria GmbH
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
package org.eclipse.fordiac.ide.gef.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class ZoomFitPageHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IWorkbenchPart part = HandlerUtil.getActiveEditor(event);
		final ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
		if (part != null) {
			final ZoomManager zoomManager = part.getAdapter(ZoomManager.class);
			if (zoomManager != null) {
				if (isZoomAll(currentSelection)) {
					Display.getDefault().syncExec(() -> zoomManager.setZoomAsText(ZoomManager.FIT_ALL));
				} else {
					zoomSelection(currentSelection, zoomManager);
				}
			}
		}
		return Status.OK_STATUS;
	}

	private static boolean isZoomAll(final ISelection currentSelection) {
		if (currentSelection == null || currentSelection.isEmpty()) {
			return false;
		}
		if (currentSelection instanceof final IStructuredSelection structSel && structSel.size() == 1) {
			final Object firstEl = structSel.getFirstElement();
			return firstEl instanceof final EditPart ep && ep.getParent() == ep.getRoot();
		}
		return false;
	}

	private static void zoomSelection(final ISelection currentSelection, final ZoomManager zoomManager) {
		final Rectangle zoomBounds = getZoomBoundsFromSelection(currentSelection);
		if (zoomBounds != null) {
			final double newZoom = getZoomFactor(zoomBounds, zoomManager);
			Display.getDefault().syncExec(() -> {
				zoomManager.setZoom(newZoom);
				// for correctly setting the position we need to get bounds after zooming again
				final Point newPos = getScrollLocation(currentSelection, zoomManager);
				zoomManager.setViewLocation(newPos);
			});
		}
	}

	private static Rectangle getZoomBoundsFromSelection(final ISelection currentSelection) {
		if (currentSelection instanceof final IStructuredSelection structSel) {
			Rectangle zoomBounds = null;
			for (final Object ob : structSel.toList()) {
				if (ob instanceof final GraphicalEditPart gep) {
					final Rectangle bounds = new Rectangle(gep.getFigure().getBounds());
					gep.getFigure().translateToAbsolute(bounds);
					if (zoomBounds == null) {
						zoomBounds = bounds;
					} else {
						zoomBounds.union(bounds);
					}
				}
			}
			return zoomBounds;
		}
		return null;
	}

	private static double getZoomFactor(final Rectangle zoomBounds, final ZoomManager zoomManager) {
		final Dimension available = zoomManager.getViewport().getClientArea().getSize();
		final double scaleX = Math.min(available.width * zoomManager.getZoom() / zoomBounds.width,
				zoomManager.getMaxZoom());
		final double scaleY = Math.min(available.height * zoomManager.getZoom() / zoomBounds.height,
				zoomManager.getMaxZoom());
		return Math.min(scaleX, scaleY);

	}

	private static Point getScrollLocation(final ISelection currentSelection, final ZoomManager zoomManager) {
		final Rectangle zoomBounds = getZoomBoundsFromSelection(currentSelection);
		if (zoomBounds != null) {
			final Dimension size = zoomManager.getViewport().getClientArea().getSize();
			return zoomBounds.getCenter().translate(-size.width / 2, -size.height / 2)
					.translate(zoomManager.getViewport().getViewLocation());
		}
		return zoomManager.getViewport().getViewLocation();
	}

}
