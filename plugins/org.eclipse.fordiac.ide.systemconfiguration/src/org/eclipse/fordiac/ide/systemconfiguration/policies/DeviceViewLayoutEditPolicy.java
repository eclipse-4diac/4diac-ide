/*******************************************************************************
 * Copyright (c) 2008, 2012, 2013, 2016 Profactor GbmH, TU Wien ACIN, fortiss GmbH, 
 * 				 2018 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.gef.policies.ModifiedNonResizeableEditPolicy;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.fordiac.ide.model.Palette.ResourceTypeEntry;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.systemconfiguration.commands.ResourceCreateCommand;
import org.eclipse.fordiac.ide.systemconfiguration.commands.ResourceMoveCommand;
import org.eclipse.fordiac.ide.systemconfiguration.editparts.DeviceEditPart;
import org.eclipse.fordiac.ide.systemconfiguration.editparts.ResourceEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy;
import org.eclipse.gef.requests.AlignmentRequest;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * The Class DeviceViewLayoutEditPolicy.
 */
public class DeviceViewLayoutEditPolicy extends ConstrainedLayoutEditPolicy {
	@Override
	protected EditPolicy createChildEditPolicy(EditPart child) {
		IPreferenceStore pf = Activator.getDefault().getPreferenceStore();
		int cornerDim = pf.getInt(DiagramPreferences.CORNER_DIM);
		if (cornerDim > 1) {
			cornerDim = cornerDim / 2;
		}
		return new ModifiedNonResizeableEditPolicy(cornerDim, new Insets(1));
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
		Object childClass = request.getNewObjectType();
		if (childClass instanceof ResourceTypeEntry) {
			ResourceTypeEntry type = (ResourceTypeEntry) request.getNewObjectType();
			if (getHost() instanceof DeviceEditPart) {
				return new ResourceCreateCommand(type, ((DeviceEditPart)getHost()).getModel(), false);
			}
		}
		return null;
	}

	/**
	 * @see org.eclipse.gef.EditPolicy#getCommand(org.eclipse.gef.Request)
	 */
	@Override
	public Command getCommand(Request request) {
		Object type = request.getType();
		if (REQ_ALIGN.equals(type) && request instanceof AlignmentRequest) {
			return getAlignCommand((AlignmentRequest) request);
		}

		return super.getCommand(request);
	}
	
	@Override
	protected Command createAddCommand(ChangeBoundsRequest request,
			EditPart child, Object constraint){
		if (child instanceof ResourceEditPart) {
			Device targetDevice = ((DeviceEditPart)getHost()).getModel();
			return new ResourceMoveCommand((Resource) child.getModel(), targetDevice, targetDevice.getResource().size());
		}
		return null;
	}

	/**
	 * Returns the command contribution to an alignment request
	 * 
	 * @param request
	 *            the alignment request
	 * @return the contribution to the alignment
	 */
	protected Command getAlignCommand(AlignmentRequest request) {
		AlignmentRequest req = new AlignmentRequest(REQ_ALIGN_CHILDREN);
		req.setEditParts(getHost());
		req.setAlignment(request.getAlignment());
		req.setAlignmentRectangle(request.getAlignmentRectangle());
		return getHost().getParent().getCommand(req);
	}

}
