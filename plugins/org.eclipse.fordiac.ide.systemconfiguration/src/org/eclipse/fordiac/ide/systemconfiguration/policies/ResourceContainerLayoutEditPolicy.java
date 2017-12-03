/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
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
package org.eclipse.fordiac.ide.systemconfiguration.policies;

import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.gef.policies.ModifiedNonResizeableEditPolicy;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.fordiac.ide.model.Palette.ResourceTypeEntry;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.systemconfiguration.commands.ResourceCreateCommand;
import org.eclipse.fordiac.ide.systemconfiguration.commands.ResourceMoveCommand;
import org.eclipse.fordiac.ide.systemconfiguration.editparts.ResourceContainerEditPart;
import org.eclipse.fordiac.ide.systemconfiguration.editparts.ResourceEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.FlowLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.jface.preference.IPreferenceStore;


public class ResourceContainerLayoutEditPolicy extends FlowLayoutEditPolicy {

	@Override
	protected EditPolicy createChildEditPolicy(EditPart child) {
		IPreferenceStore pf = Activator.getDefault().getPreferenceStore();
		int cornerDim = pf.getInt(DiagramPreferences.CORNER_DIM);
		if (cornerDim > 1) {
			cornerDim = cornerDim / 2;
		}
		return new ModifiedNonResizeableEditPolicy(cornerDim, new Insets(1));
	}


	@Override
	protected Command createMoveChildCommand(EditPart child, EditPart after) {
		return getMoveCommand(child, after);
	}

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		if (request == null) {
			return null;
		}
		Object childClass = request.getNewObjectType();
		if (childClass instanceof ResourceTypeEntry) {
			ResourceTypeEntry type = (ResourceTypeEntry) request.getNewObjectType();
			if (getHost() instanceof ResourceContainerEditPart) {
				ResourceContainerEditPart resContainerEditPart = (ResourceContainerEditPart) getHost();
				EditPart ref = getInsertionReference(request);
				int index = -1;
				if (ref != null) {
					index = resContainerEditPart.getModel().getDevice().getResource().indexOf(((ResourceEditPart) ref).getModel());
				}
				return new ResourceCreateCommand(type, resContainerEditPart.getModel().getDevice(), index, false);
			}
		}
		return null;
	}


	@Override
	protected Command createAddCommand(EditPart child, EditPart after) {
		return getMoveCommand(child, after);
	}

	private ResourceMoveCommand getMoveCommand(EditPart child, EditPart after) {
		ResourceMoveCommand cmd = null;
		if (child instanceof ResourceEditPart && getHost() instanceof ResourceContainerEditPart) {
			int index = -1; 
			Device targetDevice = ((ResourceContainerEditPart)getHost()).getModel().getDevice();
			if (after == null) {
				index = targetDevice.getResource().size();
			} else {
				index = targetDevice.getResource().indexOf((Resource) after.getModel()); 
			}
			cmd = new ResourceMoveCommand((Resource) child.getModel(), targetDevice, index);
		}
		return cmd;
	}
}
