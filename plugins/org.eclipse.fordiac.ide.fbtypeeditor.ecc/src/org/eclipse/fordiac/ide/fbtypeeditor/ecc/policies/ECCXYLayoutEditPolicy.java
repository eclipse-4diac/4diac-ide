/*******************************************************************************
 * Copyright (c) 2008 - 2016 Profactor GmbH, fortiss GmbH
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
 *   Vikash Kumar Sinha
 *     - adjust transition bendpoints when a state is moved
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.policies;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.CreateECStateCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionAlgorithmEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionOutputEventEditPart;
import org.eclipse.fordiac.ide.gef.policies.ModifiedNonResizeableEditPolicy;
import org.eclipse.fordiac.ide.gef.utilities.RequestUtil;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.commands.change.SetPositionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

public class ECCXYLayoutEditPolicy extends XYLayoutEditPolicy {

	@Override
	protected EditPolicy createChildEditPolicy(final EditPart child) {
		return new ModifiedNonResizeableEditPolicy(0, new Insets(4));
	}

	@Override
	protected Command createChangeConstraintCommand(final ChangeBoundsRequest request, final EditPart child,
			final Object constraint) {
		if ((child.getModel() instanceof final ECState state) && RequestUtil.isMoveRequest(request)) {
			final Rectangle rectConstraint = (Rectangle) constraint;
			final Position newPos = CoordinateConverter.INSTANCE.createPosFromScreenCoordinates(rectConstraint.x,
					rectConstraint.y);

			final Position oldPos = state.getPosition();
			final double dx = newPos.getX() - oldPos.getX();
			final double dy = newPos.getY() - oldPos.getY();

			final CompoundCommand compound = new CompoundCommand();
			compound.add(new SetPositionCommand(state, newPos));

			final Set<ECTransition> connectedTransitions = new LinkedHashSet<>(state.getOutTransitions());
			connectedTransitions.addAll(state.getInTransitions());

			for (final ECTransition transition : connectedTransitions) {
				compound.add(new SetPositionCommand(transition, dx, dy));
			}

			return compound;
		}
		return null;
	}

	@Override
	protected Command getAddCommand(final Request generic) {
		if (generic instanceof final ChangeBoundsRequest request) {
			for (final Object editPart : request.getEditParts()) {
				if ((editPart instanceof ECActionAlgorithmEditPart)
						|| (editPart instanceof ECActionOutputEventEditPart)) {
					// actions should not be moved or resized
					return null;
				}
			}
		}
		return super.getAddCommand(generic);
	}

	@Override
	protected Command getCreateCommand(final CreateRequest request) {
		if (request.getNewObjectType().equals(ECState.class) && getHost().getModel() instanceof final ECC ecc) {
			final Point point = request.getLocation().getCopy();
			getHostFigure().translateToRelative(point);

			final Position pos = CoordinateConverter.INSTANCE.createPosFromScreenCoordinates(point.x, point.y);

			return new CreateECStateCommand((ECState) request.getNewObject(), pos, ecc);
		}
		return null;
	}

	protected ZoomManager getZoomManager() {
		return ((ScalableFreeformRootEditPart) (getHost().getRoot())).getZoomManager();
	}
}
