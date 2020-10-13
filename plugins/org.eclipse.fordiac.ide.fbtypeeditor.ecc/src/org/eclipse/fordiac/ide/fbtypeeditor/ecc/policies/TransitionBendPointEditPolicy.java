/*******************************************************************************
 * Copyright (c) 2013 fortiss GmbH
 * 				 2019-2020 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *               - increased size of middle bendpoint
 *               - changed middle bendpoint color to default selection color
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.MoveBendpointCommand;
import org.eclipse.fordiac.ide.gef.policies.ModifiedMoveHandle;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.ui.preferences.ConnectionPreferenceValues;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.BendpointEditPolicy;
import org.eclipse.gef.editpolicies.SelectionHandlesEditPolicy;
import org.eclipse.gef.handles.BendpointMoveHandle;
import org.eclipse.gef.requests.BendpointRequest;
import org.eclipse.swt.graphics.Color;

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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List createSelectionHandles() {
		List list = new ArrayList();
		ConnectionEditPart connEP = (ConnectionEditPart) getHost();
		PointList points = getConnection().getPoints();
		List bendPoints = (List) getConnection().getRoutingConstraint();
		int bendPointIndex = 0;
		Point currBendPoint = null;

		if (bendPoints == null) {
			bendPoints = new ArrayList();
		} else if (!bendPoints.isEmpty()) {
			currBendPoint = ((Bendpoint) bendPoints.get(0)).getLocation();
		}

		for (int i = 0; i < points.size() - 1; i++) {
			// If the current user bendpoint matches a bend location, show a move handle
			if (i < points.size() - 1 && bendPointIndex < bendPoints.size()
					&& currBendPoint.equals(points.getPoint(i + 1))) {
				list.add(createBendPointMoveHandle(connEP, bendPointIndex, i));

				// Go to the next user bendpoint
				bendPointIndex++;
				if (bendPointIndex < bendPoints.size()) {
					currBendPoint = ((Bendpoint) bendPoints.get(bendPointIndex)).getLocation();
				}
			}
		}

		return list;
	}

	private static BendpointMoveHandle createBendPointMoveHandle(ConnectionEditPart connEP, int bendPointIndex, int i) {
		BendpointMoveHandle handle = new BendpointMoveHandle(connEP, bendPointIndex, i + 1) {

			@Override
			protected Color getBorderColor() {
				return (isPrimary()) ? ColorConstants.white : ModifiedMoveHandle.getSelectionColor();
			}

			@Override
			protected Color getFillColor() {
				return (isPrimary()) ? ModifiedMoveHandle.getSelectionColor() : ColorConstants.white;
			}
		};
		handle.setPreferredSize(ConnectionPreferenceValues.HANDLE_SIZE, ConnectionPreferenceValues.HANDLE_SIZE);
		return handle;
	}
}
