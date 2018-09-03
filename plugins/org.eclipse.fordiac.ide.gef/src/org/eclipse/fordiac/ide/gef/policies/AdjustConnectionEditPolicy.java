/*******************************************************************************
 * Copyright (c) 2018 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - extracted this policy and added zoommanger for correct 
 *   				adjustment when zoomed  
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.fordiac.ide.gef.commands.AdjustConnectionCommand;
import org.eclipse.fordiac.ide.gef.router.LineSegmentHandle;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editpolicies.BendpointEditPolicy;
import org.eclipse.gef.handles.ConnectionHandle;
import org.eclipse.gef.requests.BendpointRequest;

public class AdjustConnectionEditPolicy extends BendpointEditPolicy {
	private final Connection connection;

	public AdjustConnectionEditPolicy(Connection connection) {
		this.connection = connection;
	}

	@Override
	protected Command getMoveBendpointCommand(final BendpointRequest request) {
		return null;
	}

	@Override
	protected Command getDeleteBendpointCommand(
			final BendpointRequest request) {
		return null;
	}

	@Override
	protected Command getCreateBendpointCommand(
			final BendpointRequest request) {
		return new AdjustConnectionCommand(getConnection(), request.getLocation(), request.getIndex(), connection, getZoom());
	}

	@Override
	protected void showCreateBendpointFeedback(BendpointRequest request) {
		AdjustConnectionCommand cmd = new AdjustConnectionCommand(getConnection(), request.getLocation(), request.getIndex(), connection, getZoom());
		if (cmd.canExecute()) {
			cmd.execute();
		}
	}

	@Override
	protected List<ConnectionHandle> createSelectionHandles() {
		List<ConnectionHandle> list = new ArrayList<>();
		AbstractConnectionEditPart connEP = (AbstractConnectionEditPart) getHost();
		PointList points = getConnection().getPoints();
		for (int i = 1; i < points.size() - 2; i++) {
			list.add(new LineSegmentHandle(connEP, i));
		}
		return list;
	}
	
	private double getZoom() {
		double zoom = 1.0;
		if(getHost().getRoot() instanceof ScalableFreeformRootEditPart) {
			zoom = ((ScalableFreeformRootEditPart)(getHost().getRoot())).getZoomManager().getZoom(); 
		}
		return zoom;
	}
}