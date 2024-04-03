/*******************************************************************************
 * Copyright (c) 2008, 2012, 2014, 2016, 2017 Profactor GbmH, TU Wien ACIN, fortiss GmbH,
 * 				 2018 Johannes Kepler University
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.gef.policies.ModifiedMoveHandle;
import org.eclipse.fordiac.ide.gef.policies.ModifiedNonResizeableEditPolicy;
import org.eclipse.fordiac.ide.gef.utilities.RequestUtil;
import org.eclipse.fordiac.ide.model.commands.change.SetPositionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.model.typelibrary.DeviceTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.SegmentTypeEntry;
import org.eclipse.fordiac.ide.systemconfiguration.commands.DeviceCreateCommand;
import org.eclipse.fordiac.ide.systemconfiguration.commands.SegmentCreateCommand;
import org.eclipse.fordiac.ide.systemconfiguration.commands.SegmentSetConstraintCommand;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.editpolicies.ResizableEditPolicy;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.handles.ResizeHandle;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

public class SystemConfXYLayoutEditPolicy extends XYLayoutEditPolicy {
	@Override
	protected EditPolicy createChildEditPolicy(final EditPart child) {
		if (child.getModel() instanceof Segment) {
			return new ResizableEditPolicy() {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				@Override
				protected List createSelectionHandles() {
					final List list = new ArrayList();
					list.add(new ResizeHandle(getHost(), PositionConstants.EAST));
					list.add(new ResizeHandle(getHost(), PositionConstants.WEST));
					list.add(new ModifiedMoveHandle(getHost(), new Insets(0, 2, 0, 2), 20));
					return list;
				}
			};
		}
		return new ModifiedNonResizeableEditPolicy();
	}

	@Override
	protected Command getDeleteDependantCommand(final Request request) {
		return null;
	}

	@Override
	protected Command createChangeConstraintCommand(final ChangeBoundsRequest request, final EditPart child,
			final Object constraint) {
		if (constraint instanceof final Rectangle rectangle) {
			final Rectangle rec = new Rectangle(rectangle);
			if (child.getModel() instanceof final Segment segment) {
				return new SegmentSetConstraintCommand(segment, rec, request);
			}
			// return a command that can move a "PositionableElement"
			if (child.getModel() instanceof final PositionableElement posel && RequestUtil.isMoveRequest(request)) {
				final Point moveDelta = request.getMoveDelta().getScaled(1.0 / getZoomManager().getZoom());
				return new SetPositionCommand(posel, moveDelta.x, moveDelta.y);
			}
		}
		return null;
	}

	@Override
	protected Command getCreateCommand(final CreateRequest request) {
		if (request == null) {
			return null;
		}
		final Object childClass = request.getNewObjectType();
		final Rectangle constraint = (Rectangle) getConstraintFor(request);
		if (childClass instanceof DeviceTypeEntry) {
			final DeviceTypeEntry type = (DeviceTypeEntry) request.getNewObjectType();
			if (getHost().getModel() instanceof final SystemConfiguration sysConf) {
				return new DeviceCreateCommand(type, sysConf,
						new Rectangle(constraint.getLocation().x, constraint.getLocation().y, -1, -1));
			}
		}
		if (childClass instanceof SegmentTypeEntry) {
			final SegmentTypeEntry type = (SegmentTypeEntry) request.getNewObjectType();
			if (getHost().getModel() instanceof final SystemConfiguration sysConf) {
				return new SegmentCreateCommand(type, sysConf, constraint);
			}
		}
		return null;
	}

	@Override
	protected Command getAddCommand(final Request generic) {
		return null;
	}

	protected ZoomManager getZoomManager() {
		return ((ScalableFreeformRootEditPart) (getHost().getRoot())).getZoomManager();
	}
}
