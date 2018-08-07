/*******************************************************************************
 * Copyright (c) 2013 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.MoveBendpointCommand;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.BendpointEditPolicy;
import org.eclipse.gef.editpolicies.SelectionHandlesEditPolicy;
import org.eclipse.gef.handles.BendpointMoveHandle;
import org.eclipse.gef.requests.BendpointRequest;

public class TransitionBendPointEditPolicy extends BendpointEditPolicy {

	private ECTransition transition;

	public TransitionBendPointEditPolicy(ECTransition transition) {
		super();
		this.transition = transition;
	}

	@Override
	protected Command getCreateBendpointCommand(BendpointRequest request) {
		// we don't allow to create additional bendpoints for transitions,
		// therefore we move them
		return getMoveBendpointCommand(request);
	}

	@Override
	protected Command getDeleteBendpointCommand(BendpointRequest request) {
		// we don't allow to delete a bendpoint for transitions, therefore we
		// move them
		return getMoveBendpointCommand(request);
	}

	@Override
	protected Command getMoveBendpointCommand(BendpointRequest request) {
		Point p = request.getLocation().getCopy();
		getConnection().translateToRelative(p);
		return new MoveBendpointCommand(transition, p);
	}

	/**
	 * @see SelectionHandlesEditPolicy#createSelectionHandles()
	 */
	@Override
	@SuppressWarnings("rawtypes")
	protected List createSelectionHandles() {
		return createHandlesForUserBendpoints();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List createHandlesForUserBendpoints() {
		List list = new ArrayList();
		ConnectionEditPart connEP = (ConnectionEditPart) getHost();
		PointList points = getConnection().getPoints();
		List bendPoints = (List) getConnection().getRoutingConstraint();
		int bendPointIndex = 0;
		Point currBendPoint = null;

		if (bendPoints == null) {
			bendPoints = new ArrayList();
		}else if (!bendPoints.isEmpty()) {
			currBendPoint = ((Bendpoint) bendPoints.get(0)).getLocation();
		}

		for (int i = 0; i < points.size() - 1; i++) {
			// If the current user bendpoint matches a bend location, show a move handle
			if (i < points.size() - 1 && bendPointIndex < bendPoints.size()
					&& currBendPoint.equals(points.getPoint(i + 1))) {
				list.add(new BendpointMoveHandle(connEP, bendPointIndex, i + 1));

				// Go to the next user bendpoint
				bendPointIndex++;
				if (bendPointIndex < bendPoints.size()) {
					currBendPoint = ((Bendpoint) bendPoints.get(bendPointIndex)).getLocation();
				}
			}
		}

		return list;
	}
}
