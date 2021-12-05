/*******************************************************************************
 * Copyright (c) 2020, 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - reworked connection selection and hover feedback
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handles;

import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.tools.FBNScrollingConnectionEndpointTracker;
import org.eclipse.fordiac.ide.gef.handles.ScrollingConnectionEndpointHandle;
import org.eclipse.fordiac.ide.ui.preferences.ConnectionPreferenceValues;
import org.eclipse.gef.tools.ConnectionEndpointTracker;

public class FBNConnectionEndPointHandle extends ScrollingConnectionEndpointHandle {

	public FBNConnectionEndPointHandle(final org.eclipse.gef.ConnectionEditPart owner, final int endPoint) {
		super(owner, endPoint);
		setLocator(new ConnectionLocator(getConnection(), endPoint) {
			@Override
			protected Point getLocation(final PointList points) {
				final Point p = super.getLocation(points);
				// ensure that the returned point is such that the endpoint handle is on the
				// connection
				switch (getAlignment()) {
				case SOURCE:
					p.x += ((getPreferredSize().width / 2) - 4); // TODO replace with connection
					// border size
					break;
				case TARGET:
					p.x += ((-getPreferredSize().width / 2) + 4);
					break;
				default:
					break;
				}
				return p;
			}
		});
	}

	@Override
	protected void init() {
		super.init();
		setPreferredSize((ConnectionPreferenceValues.HANDLE_SIZE * 4) / 3, ConnectionPreferenceValues.HANDLE_SIZE);
	}

	@Override
	protected void paintHandleCenter(final Graphics g, final Rectangle r) {
		final int xbuf = r.x;
		final int wbuf = r.width;

		final int shrinkVal = getInnerShrinkVal();
		r.shrink(shrinkVal, shrinkVal + 1);
		switch (getEndPoint()) {
		case ConnectionLocator.SOURCE:
			r.x = xbuf;
			break;
		case ConnectionLocator.TARGET:
			r.x = (xbuf + wbuf) - r.width;
			break;
		default:
			break;
		}
		g.fillRoundRectangle(r, r.height / 2, r.height / 2);

	}

	@Override
	protected ConnectionEndpointTracker createConnectionEndPointTracker(
			final org.eclipse.gef.ConnectionEditPart connectionEditPart) {
		return new FBNScrollingConnectionEndpointTracker(connectionEditPart);
	}
}