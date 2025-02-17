/*******************************************************************************
 * Copyright (c) 2008, 2012, 2013, 2016 Profactor GbmH, TU Wien ACIN, fortiss GmbH,
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
 *   Alois Zoitl - allowed resource drop on on whole interfaces
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.policies;

import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.gef.policies.ModifiedNonResizeableEditPolicy;
import org.eclipse.fordiac.ide.gef.preferences.GefPreferenceConstants;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.typelibrary.ResourceTypeEntry;
import org.eclipse.fordiac.ide.systemconfiguration.commands.ResourceCreateCommand;
import org.eclipse.fordiac.ide.systemconfiguration.commands.ResourceMoveCommand;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy;
import org.eclipse.gef.requests.AlignmentRequest;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

/**
 * The Class DeviceViewLayoutEditPolicy.
 */
public class DeviceViewLayoutEditPolicy extends ConstrainedLayoutEditPolicy {

	@Override
	protected EditPolicy createChildEditPolicy(final EditPart child) {
		return new ModifiedNonResizeableEditPolicy(GefPreferenceConstants.CORNER_DIM_HALF, new Insets(1));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#getConstraintFor
	 * (org.eclipse.draw2d.geometry.Point)
	 */
	@Override
	protected Object getConstraintFor(final Point point) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#getConstraintFor
	 * (org.eclipse.draw2d.geometry.Rectangle)
	 */
	@Override
	protected Object getConstraintFor(final Rectangle rect) {
		return null;
	}

	@Override
	protected Command getCreateCommand(final CreateRequest request) {
		final Object childClass = request.getNewObjectType();
		if (childClass instanceof ResourceTypeEntry) {
			final ResourceTypeEntry type = (ResourceTypeEntry) request.getNewObjectType();
			if (getHost().getModel() instanceof Device) {
				return new ResourceCreateCommand(type, (Device) getHost().getModel(), false);
			}
		}
		return null;
	}

	/**
	 * @see org.eclipse.gef.EditPolicy#getCommand(org.eclipse.gef.Request)
	 */
	@Override
	public Command getCommand(final Request request) {
		final Object type = request.getType();
		if (REQ_ALIGN.equals(type) && request instanceof AlignmentRequest) {
			return getAlignCommand((AlignmentRequest) request);
		}

		return super.getCommand(request);
	}

	@Override
	protected Command createAddCommand(final ChangeBoundsRequest request, final EditPart child,
			final Object constraint) {
		if (child.getModel() instanceof Resource) {
			final Device targetDevice = (Device) getHost().getModel();
			return new ResourceMoveCommand((Resource) child.getModel(), targetDevice,
					targetDevice.getResource().size());
		}
		return null;
	}

	/**
	 * Returns the command contribution to an alignment request
	 *
	 * @param request the alignment request
	 * @return the contribution to the alignment
	 */
	protected Command getAlignCommand(final AlignmentRequest request) {
		final AlignmentRequest req = new AlignmentRequest(REQ_ALIGN_CHILDREN);
		req.setEditParts(getHost());
		req.setAlignment(request.getAlignment());
		req.setAlignmentRectangle(request.getAlignmentRectangle());
		return getHost().getParent().getCommand(req);
	}

}
