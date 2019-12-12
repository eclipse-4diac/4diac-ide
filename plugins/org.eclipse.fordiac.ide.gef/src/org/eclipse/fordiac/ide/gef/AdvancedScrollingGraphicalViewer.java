/*******************************************************************************
 * Copyright (c) 2009, 2017 Profactor GbmH, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef;

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
		// do not correct viewport for connections
		// FIXME -> make it setable in e.g. preferences
		if (!(part instanceof ConnectionEditPart)) {
			super.reveal(part);
		}
	}

	/**
	 * checks if during dragging of an element (e.g., connection creation, moving of
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
