/*******************************************************************************
 * Copyright (c) 2008, 2012 Profactor GmbH, TU Wien ACIN
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
 *   Alois Zoitl - added scrolling support to connection dragging
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.fordiac.ide.gef.tools.ScrollingConnectionEndpointTracker;
import org.eclipse.fordiac.ide.ui.preferences.ConnectionPreferenceValues;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.gef.handles.ConnectionEndpointHandle;
import org.eclipse.gef.tools.ConnectionEndpointTracker;

/**
 * An EditPolicy for showing feedback when selected.
 *
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class FeedbackConnectionEndpointEditPolicy extends ConnectionEndpointEditPolicy {

	private static final class ScrollingConnectionEndpointHandle extends ConnectionEndpointHandle {
		private ScrollingConnectionEndpointHandle(ConnectionEditPart owner, int endPoint) {
			super(owner, endPoint);
			setPreferredSize(ConnectionPreferenceValues.HANDLE_SIZE, ConnectionPreferenceValues.HANDLE_SIZE);
		}

		@Override
		protected DragTracker createDragTracker() {
			if (isFixed()) {
				return null;
			}
			ConnectionEndpointTracker tracker;
			tracker = new ScrollingConnectionEndpointTracker((ConnectionEditPart) getOwner());
			if (getEndPoint() == ConnectionLocator.SOURCE) {
				tracker.setCommandName(RequestConstants.REQ_RECONNECT_SOURCE);
			} else {
				tracker.setCommandName(RequestConstants.REQ_RECONNECT_TARGET);
			}
			tracker.setDefaultCursor(getCursor());
			return tracker;
		}
	}

	private int selectedLineWidth = ConnectionPreferenceValues.SELECTED_LINE_WIDTH;
	private int unselectedLineWidth = ConnectionPreferenceValues.NORMAL_LINE_WIDTH;

	public FeedbackConnectionEndpointEditPolicy(int unselectedLineWidth, int selectedLineWidth) {
		super();
		this.selectedLineWidth = selectedLineWidth;
		this.unselectedLineWidth = unselectedLineWidth;
	}

	public FeedbackConnectionEndpointEditPolicy() {
		super();
	}

	@Override
	protected void showSelection() {
		super.showSelection();
		getConnectionFigure().setLineWidth(selectedLineWidth);
	}

	/**
	 * Gets the connection figure.
	 *
	 * @return the connection figure
	 */
	protected PolylineConnection getConnectionFigure() {
		return (PolylineConnection) ((GraphicalEditPart) getHost()).getFigure();
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected List createSelectionHandles() {
		List<ConnectionEndpointHandle> list = new ArrayList<>();
		list.add(new ScrollingConnectionEndpointHandle((ConnectionEditPart) getHost(), ConnectionLocator.SOURCE));
		list.add(new ScrollingConnectionEndpointHandle((ConnectionEditPart) getHost(), ConnectionLocator.TARGET));
		return list;
	}

	@Override
	protected void hideSelection() {
		super.hideSelection();
		getConnectionFigure().setLineWidth(unselectedLineWidth);
	}

}
