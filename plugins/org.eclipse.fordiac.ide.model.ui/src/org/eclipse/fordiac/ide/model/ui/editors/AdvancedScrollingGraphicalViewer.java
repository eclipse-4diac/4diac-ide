/*******************************************************************************
 * Copyright (c) 2009, 2017 Profactor GbmH, fortiss GmbH
 * 				 2019 - 2020 Johannes Kepler University Linz
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.editors;

import org.eclipse.draw2d.RangeModel;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
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
	public void reveal(EditPart part) {
		// in order that the viewport is not moved on simple mouse selections do nothing
		// here
	}

	/**
	 * Explicitly move view port such that the editpart is moved fully visible
	 *
	 * @param part the editpart to be selected and revealed
	 */
	public void revealEditPart(EditPart part) {
		// do not correct viewport for connections
		if (!(part instanceof ConnectionEditPart)) {
			super.reveal(part);
		}
	}

	/**
	 * Select and explicitly move view port such that the editpart is moved fully
	 * visible
	 *
	 * @param part the editpart to be revealed
	 */
	public void selectAndRevealEditPart(EditPart part) {
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
	public void checkScrollPositionDuringDrag(MouseEvent me) {
		if (!getControl().getBounds().contains(me.x, me.y)) {
			Point newLocation = getNewScrollPosition(me);
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
	public void checkScrollPositionDuringDragBounded(MouseEvent me, Point border) {
		checkScrollPositionDuringDrag(me);
		me.x = boundCoordinate(me.x, border.x, getFigureCanvas().getViewport().getHorizontalRangeModel());
		me.y = boundCoordinate(me.y, border.y, getFigureCanvas().getViewport().getVerticalRangeModel());
	}

	private static int boundCoordinate(int mousePos, int border, RangeModel rangeModel) {
		int retVal = mousePos;
		int min = rangeModel.getMinimum() - rangeModel.getValue();
		int max = rangeModel.getMaximum() - rangeModel.getMinimum() - rangeModel.getValue();
		if (retVal < min) {
			retVal = min + border;
		} else if (retVal > max) {
			retVal = max - border;
		}
		return retVal;
	}

	private Point getNewScrollPosition(MouseEvent me) {
		Point newLocation = getViewLocation();
		newLocation.x += getScrollDelta(me.x, getControl().getBounds().x, getControl().getBounds().width);
		newLocation.y += getScrollDelta(me.y, getControl().getBounds().y, getControl().getBounds().height);
		return newLocation;
	}

	public Point getViewLocation() {
		return getFigureCanvas().getViewport().getViewLocation();
	}

	private static int getScrollDelta(int mousePos, int controlPos, int length) {
		if (mousePos < controlPos) {
			return mousePos - controlPos;
		}

		if ((controlPos + length) < mousePos) {
			return mousePos - (controlPos + length);
		}

		return 0;
	}

	public void scrollByOffset(int dx, int dy) {
		Point location = getViewLocation();
		getFigureCanvas().scrollSmoothTo(location.x + dx, location.y + dy);
	}

	public org.eclipse.swt.graphics.Point getFigureCanvasSize() {
		return getFigureCanvas().getSize();
	}
}
