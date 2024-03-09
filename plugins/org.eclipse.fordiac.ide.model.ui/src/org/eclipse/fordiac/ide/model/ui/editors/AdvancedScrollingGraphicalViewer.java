/*******************************************************************************
 * Copyright (c) 2009, 2021 Profactor GbmH, fortiss GmbH, Johannes Kepler University Linz
 *                          Primetals Technologies Austria GmbH
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
 *   Alois Zoitl - added method to handle mouse drags outside of the viewport
 *                 Bug #553136.
 *   Bianca Wiesmayr - support keyboard navigation
 *   Alois Zoitl - keep connection draging within canvas bounds
 *   Bianc Wiesmayr, Alois Zoitl - center revealed edit parts for better visual
 *                 appearence
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.editors;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RangeModel;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.swt.events.MouseEvent;

/**
 * The Class AdvancedScrollingGraphicalViewer.
 */
public class AdvancedScrollingGraphicalViewer extends ScrollingGraphicalViewer {

	/**
	 * Extends the superclass implementation to scroll the native Canvas control
	 * after the super's implementation has completed.
	 *
	 * @param part the part
	 *
	 * @see org.eclipse.gef.EditPartViewer#reveal(org.eclipse.gef.EditPart)
	 */
	@Override
	public void reveal(final EditPart part) {
		// in order that the viewport is not moved on simple mouse selections do nothing
		// here
	}

	/**
	 * Explicitly move view port such that the editpart is moved fully visible
	 *
	 * @param part the editpart to be selected and revealed
	 */
	public void revealEditPart(final EditPart part) {
		// do not correct viewport for connections
		if (!(part instanceof ConnectionEditPart)) {
			super.reveal(part);
			if (part instanceof final GraphicalEditPart graphicalEP) {
				centerPartPositionInViewport(graphicalEP);
			}
		}
	}

	/** move viewport such that the editpart is in the center of the visible area */
	private void centerPartPositionInViewport(final GraphicalEditPart part) {
		// calculate current center of the visible part of figurecanvas
		final Rectangle visibleArea = getFigureCanvas().getViewport().getBounds();
		final Point currentCenter = new Point(getViewLocation().x + (visibleArea.width / 2),
				getViewLocation().y + (visibleArea.height / 2));

		// calculate desired center of the visible part of figurecanvas
		final Point partCenter = getTranslatedPartCenter(part.getFigure());

		// calculate offset and move there
		final int dx = currentCenter.x - partCenter.x;
		final int dy = currentCenter.y - partCenter.y;

		getFigureCanvas().scrollTo(getViewLocation().x - dx, getViewLocation().y - dy);
	}

	private Point getTranslatedPartCenter(final IFigure partFigure) {
		final Rectangle partExtent = translateBoundsToRoute(partFigure);
		return new Point(partExtent.x + (partExtent.width / 2), partExtent.y + (partExtent.height / 2));
	}

	public Rectangle translateBoundsToRoute(final IFigure partFigure) {
		final Viewport viewPort = getFigureCanvas().getViewport();
		final Rectangle partExtent = partFigure.getBounds().getCopy();
		IFigure runner = partFigure.getParent();
		while ((null != runner) && (runner != viewPort)) {
			runner.translateToParent(partExtent);
			runner = runner.getParent();
		}
		return partExtent;
	}

	/**
	 * Select and explicitly move view port such that the editpart is moved fully
	 * visible
	 *
	 * @param part the editpart to be revealed
	 */
	public void selectAndRevealEditPart(final EditPart part) {
		select(part);
		revealEditPart(part);
	}

	/**
	 * Checks if during dragging of an element (e.g., connection creation, moving of
	 * an element) the mouse is outside of the viewer and tries to move the
	 * scrollbar in that direction
	 *
	 * @param me mouse event of the drag movement
	 */
	public void checkScrollPositionDuringDrag(final MouseEvent me) {
		final org.eclipse.swt.graphics.Rectangle controlBounds = getControl().getBounds();
		// mouse coordinates are given relative to control origin, translate for
		// correct handling
		final Point relativePos = new Point(me.x + controlBounds.x, me.y + controlBounds.y);
		if (!controlBounds.contains(relativePos.x, relativePos.y)) {
			final Point newLocation = getNewScrollPosition(relativePos);
			getFigureCanvas().scrollSmoothTo(newLocation.x, newLocation.y);
		}
	}

	/**
	 * Checks if during dragging of an element (e.g., connection creation, moving of
	 * an element) the mouse is outside of the viewer and tries to move the
	 * scrollbar in that direction
	 *
	 * This bounded version will ensure that the mouse position is within the
	 * canvas.
	 *
	 * @param me     mouse event of the drag movement
	 * @param border the border to be considered around the etch of the canvas, is,
	 *               for example, needed for connection dragging to consider the
	 *               size of the drag handle
	 */
	public void checkScrollPositionDuringDragBounded(final MouseEvent me, final Point border) {
		checkScrollPositionDuringDrag(me);
		me.x = boundCoordinate(me.x, border.x, getFigureCanvas().getViewport().getHorizontalRangeModel());
		me.y = boundCoordinate(me.y, border.y, getFigureCanvas().getViewport().getVerticalRangeModel());
	}

	private static int boundCoordinate(final int mousePos, final int border, final RangeModel rangeModel) {
		int retVal = mousePos;
		final int min = rangeModel.getMinimum() - rangeModel.getValue();
		final int max = rangeModel.getMaximum() - rangeModel.getMinimum() - rangeModel.getValue();
		if (retVal < min) {
			retVal = min + border;
		} else if (retVal > max) {
			retVal = max - border;
		}
		return retVal;
	}

	private Point getNewScrollPosition(final Point pos) {
		final Point newLocation = getViewLocation();
		newLocation.x += getScrollDelta(pos.x, getControl().getBounds().x, getControl().getBounds().width);
		newLocation.y += getScrollDelta(pos.y, getControl().getBounds().y, getControl().getBounds().height);
		return newLocation;
	}

	public Point getViewLocation() {
		return getFigureCanvas().getViewport().getViewLocation();
	}

	private static int getScrollDelta(final int mousePos, final int controlPos, final int length) {
		if (mousePos < controlPos) {
			return mousePos - controlPos;
		}

		if ((controlPos + length) < mousePos) {
			return mousePos - (controlPos + length);
		}

		return 0;
	}

	public void scrollByOffset(final int dx, final int dy) {
		final Point location = getViewLocation();
		getFigureCanvas().scrollSmoothTo(location.x + dx, location.y + dy);
	}

	public org.eclipse.swt.graphics.Point getFigureCanvasSize() {
		return getFigureCanvas().getSize();
	}
}
