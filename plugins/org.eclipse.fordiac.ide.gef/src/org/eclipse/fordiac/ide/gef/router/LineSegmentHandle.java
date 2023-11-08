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
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.gef.figures.HideableConnection;
import org.eclipse.fordiac.ide.gef.policies.ModifiedMoveHandle;
import org.eclipse.fordiac.ide.ui.preferences.ConnectionPreferenceValues;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.handles.BendpointCreationHandle;
import org.eclipse.swt.graphics.Color;

public class LineSegmentHandle extends BendpointCreationHandle {

	/**
	 * Creates a new BendpointCreationHandle, sets its owner to <code>owner</code>
	 * and its index to <code>index</code>, and sets its locator to a new
	 * {@link org.eclipse.draw2d.MidpointLocator}.
	 */
	public LineSegmentHandle(final ConnectionEditPart owner, final int index) {
		super(owner, index);

		final PointList points = ((Connection) owner.getFigure()).getPoints();
		final Point pt1 = points.getPoint(index);
		final Point pt2 = points.getPoint(index + 1);
		final int handleSize = ((PolylineConnection) owner.getFigure()).getLineWidth() + 2;

		if (Math.abs(pt1.x - pt2.x) < Math.abs(pt1.y - pt2.y)) {
			setCursor(Cursors.SIZEWE);
			setPreferredSize(handleSize, getHandleLenght(Math.abs(pt1.y - pt2.y)));
		} else {
			setCursor(Cursors.SIZENS);
			setPreferredSize(getHandleLenght(Math.abs(pt1.x - pt2.x)), handleSize);
		}
	}

	@Override
	protected Color getFillColor() {
		if (getOwner() != null && getOwner().getFigure() != null) {
			return getOwner().getFigure().getForegroundColor();
		}
		return super.getFillColor();
	}

	@Override
	public void paintFigure(final Graphics g) {
		g.setLineStyle(Graphics.LINE_SOLID);
		g.setLineWidth((ModifiedMoveHandle.SELECTION_BORDER_WIDTH));
		g.setXORMode(false);
		g.setForegroundColor(ModifiedMoveHandle.getSelectionColor());
		final int radius = (int) (ConnectionPreferenceValues.HANDLE_SIZE * 0.45);
		final Rectangle r = Rectangle.SINGLETON.setBounds(getBounds()).shrink(1, 1);
		g.drawRoundRectangle(r, radius, radius);
	}

	private static int getHandleLenght(final int len) {
		return len - 2 * HideableConnection.BEND_POINT_BEVEL_SIZE;
	}

}
