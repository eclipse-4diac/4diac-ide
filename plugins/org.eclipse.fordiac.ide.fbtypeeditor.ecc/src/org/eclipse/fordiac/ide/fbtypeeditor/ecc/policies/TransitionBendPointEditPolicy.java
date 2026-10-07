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
 *   Vikash Kumar Sinha - bind the bendpoint handle to the routed bendpoint
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.MoveBendpointCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.figures.ECCTransitionRouter;
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

	private final ECTransition transition;

	public TransitionBendPointEditPolicy(final ECTransition transition) {
		this.transition = transition;
	}

	@Override
	protected Command getCreateBendpointCommand(final BendpointRequest request) {
		// we don't allow to create additional bendpoints for transitions,
		// therefore we move them
		return getMoveBendpointCommand(request);
	}

	@Override
	protected Command getDeleteBendpointCommand(final BendpointRequest request) {
		// we don't allow to delete a bendpoint for transitions, therefore we
		// move them
		return getMoveBendpointCommand(request);
	}

	@Override
	protected Command getMoveBendpointCommand(final BendpointRequest request) {
		final Point p = request.getLocation().getCopy();
		getConnection().translateToRelative(p);
		return new MoveBendpointCommand(transition, p);
	}

	/**
	 * @see SelectionHandlesEditPolicy#createSelectionHandles()
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List createSelectionHandles() {
		final List list = new ArrayList();
		final List bendPoints = (List) getConnection().getRoutingConstraint();

		if (bendPoints != null && !bendPoints.isEmpty()
				&& getConnection().getPoints().size() > ECCTransitionRouter.BENDPOINT_INDEX) {
			list.add(createBendPointMoveHandle(getHost(), 0, ECCTransitionRouter.BENDPOINT_INDEX));
		}

		return list;
	}

	private static BendpointMoveHandle createBendPointMoveHandle(final ConnectionEditPart connEP,
			final int bendPointIndex, final int pointIndex) {
		final BendpointMoveHandle handle = new BendpointMoveHandle(connEP, bendPointIndex, pointIndex) {

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
