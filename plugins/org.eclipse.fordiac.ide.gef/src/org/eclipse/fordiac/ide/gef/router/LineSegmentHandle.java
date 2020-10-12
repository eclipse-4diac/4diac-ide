/*******************************************************************************
 * Copyright (c) 2008- 2010 Profactor GmbH
 * 				 2018 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer - initial API and implementation and/or initial documentation
 *   Alois Zoitl       - fixed connection adjusting when zoomed
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.router;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.fordiac.ide.gef.figures.HideableConnection;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.handles.BendpointCreationHandle;
import org.eclipse.swt.graphics.Color;

public class LineSegmentHandle extends BendpointCreationHandle {

	/**
	 * Creates a new BendpointCreationHandle, sets its owner to <code>owner</code>
	 * and its index to <code>index</code>, and sets its locator to a new
	 * {@link org.eclipse.draw2d.MidpointLocator}.
	 */
	public LineSegmentHandle(ConnectionEditPart owner, int index) {
		super(owner, index);

		PointList points = ((Connection) owner.getFigure()).getPoints();
		Point pt1 = points.getPoint(index);
		Point pt2 = points.getPoint(index + 1);
		if (Math.abs(pt1.x - pt2.x) < Math.abs(pt1.y - pt2.y)) {
			setCursor(Cursors.SIZEWE);
			setPreferredSize(DEFAULT_HANDLE_SIZE - 2, getHandleLenght(Math.abs(pt1.y - pt2.y)));
		} else {
			setCursor(Cursors.SIZENS);
			setPreferredSize(getHandleLenght(Math.abs(pt1.x - pt2.x)), DEFAULT_HANDLE_SIZE - 2);
		}
	}

	@Override
	protected Color getFillColor() {
		if (getOwner() != null && getOwner().getFigure() != null) {
			return getOwner().getFigure().getForegroundColor();
		}
		return super.getFillColor();
	}

	private static int getHandleLenght(int len) {
		return len - 2 * HideableConnection.BEND_POINT_BEVEL_SIZE;
	}

}
