/*******************************************************************************
 * Copyright (c) 2018, 2025 Johannes Kepler University
 *                          Primetals Technology Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - extracted this policy and added zoommanger for correct
 *   			   adjustment when zoomed
 *               - only show bendpoints when the connection is visible
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.fordiac.ide.gef.commands.AdjustConnectionCommand;
import org.eclipse.fordiac.ide.gef.figures.HideableConnection;
import org.eclipse.fordiac.ide.gef.router.LineSegmentHandle;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.BendpointEditPolicy;
import org.eclipse.gef.handles.BendpointHandle;
import org.eclipse.gef.requests.BendpointRequest;

public class AdjustConnectionEditPolicy extends BendpointEditPolicy {
	private final org.eclipse.fordiac.ide.model.libraryElement.Connection connection;

	public AdjustConnectionEditPolicy(final org.eclipse.fordiac.ide.model.libraryElement.Connection connection) {
		this.connection = connection;
	}

	@Override
	protected Command getMoveBendpointCommand(final BendpointRequest request) {
		return null;
	}

	@Override
	protected Command getDeleteBendpointCommand(final BendpointRequest request) {
		return null;
	}

	@Override
	protected Command getCreateBendpointCommand(final BendpointRequest request) {
		return new AdjustConnectionCommand(connection, getSourcePoint(), getDestinationPoint(),
				getTranslatedRequestPoint(request), request.getIndex());
	}

	private Point getTranslatedRequestPoint(final BendpointRequest request) {
		final Point p = request.getLocation().getCopy();
		getConnection().translateToRelative(p);
		return p;
	}

	@Override
	protected void showCreateBendpointFeedback(final BendpointRequest request) {
		final AdjustConnectionCommand cmd = new AdjustConnectionCommand(connection, getSourcePoint(),
				getDestinationPoint(), getTranslatedRequestPoint(request), request.getIndex());
		if (cmd.canExecute()) {
			cmd.execute();
		}
	}

	@Override
	protected List<BendpointHandle> createSelectionHandles() {
		final List<BendpointHandle> list = new ArrayList<>();
		if (connection.isVisible()) {
			final AbstractConnectionEditPart connEP = (AbstractConnectionEditPart) getHost();
			final PointList points = getConnection().getPoints();
			if (getConnection() instanceof HideableConnection) {
				// The HideableConnection has bevel points there fore we take every second pair
				for (int i = 2; i < points.size() - 2; i += 2) {
					list.add(new LineSegmentHandle(connEP, i));
				}
			} else {
				for (int i = 1; i < points.size() - 2; i++) {
					list.add(new LineSegmentHandle(connEP, i));
				}
			}

		}
		return list;
	}

	private Point getDestinationPoint() {
		final Point destPoint = getConnection().getTargetAnchor().getReferencePoint().getCopy();
		getConnection().translateToRelative(destPoint);
		return destPoint;
	}

	private Point getSourcePoint() {
		final Point sourcePoint = getConnection().getSourceAnchor().getReferencePoint().getCopy();
		getConnection().translateToRelative(sourcePoint);
		return sourcePoint;
	}

}