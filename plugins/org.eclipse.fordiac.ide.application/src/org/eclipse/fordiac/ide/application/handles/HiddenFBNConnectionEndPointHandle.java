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
package org.eclipse.fordiac.ide.application.handles;

import java.util.List;

import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.figures.FBNetworkConnection;
import org.eclipse.fordiac.ide.application.tools.FBNScrollingConnectionEndpointTracker;
import org.eclipse.fordiac.ide.application.tools.MultiFBNScrollingconnectionEndpointTracker;
import org.eclipse.fordiac.ide.gef.handles.ScrollingConnectionEndpointHandle;
import org.eclipse.fordiac.ide.gef.preferences.GefPreferenceConstants;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.tools.ConnectionEndpointTracker;

public class HiddenFBNConnectionEndPointHandle extends ScrollingConnectionEndpointHandle {

	public HiddenFBNConnectionEndPointHandle(final ConnectionEditPart owner, final int endPoint) {
		super(owner, endPoint);
		final Rectangle bounds = getLabelBounds().getExpanded(2, 2);
		setPreferredSize(bounds.width, bounds.height);
		setLocator(new ConnectionLocator(getConnection(), endPoint) {
			@Override
			protected Point getLocation(final PointList points) {
				final Point p = super.getLocation(points);
				// ensure that the returned point is such that the endpoint handle is on the
				// connection
				switch (getAlignment()) {
				case SOURCE:
					p.x += (getPreferredSize().width / 2) - 2;
					break;
				case TARGET:
					p.x += (-getPreferredSize().width / 2) + 2;
					break;
				default:
					break;
				}
				return p;
			}
		});
	}

	private Rectangle getLabelBounds() {
		final FBNetworkConnection con = (FBNetworkConnection) getOwner().getFigure();
		if (getEndPoint() == ConnectionLocator.SOURCE) {
			return con.getSourceDecoration().getBounds();
		}
		return con.getTargetDecoration().getBounds();
	}

	@Override
	protected void paintHandleCenter(final Graphics g, final Rectangle r) {
		// for hidden connections we don't want to have a handle center
	}

	@Override
	protected int getCornerRadius() {
		return GefPreferenceConstants.CORNER_DIM;
	}

	@Override
	protected ConnectionEndpointTracker createConnectionEndPointTracker(
			final List<ConnectionEditPart> coSelectedConnections) {
		if (coSelectedConnections.size() > 1) {
			return new MultiFBNScrollingconnectionEndpointTracker(coSelectedConnections);
		}
		return new FBNScrollingConnectionEndpointTracker(coSelectedConnections.get(0));
	}

}
