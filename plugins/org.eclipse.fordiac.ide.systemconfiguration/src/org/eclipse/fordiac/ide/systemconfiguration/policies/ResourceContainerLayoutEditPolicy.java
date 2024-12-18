/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.policies;

import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.fordiac.ide.gef.policies.ModifiedNonResizeableEditPolicy;
import org.eclipse.fordiac.ide.gef.preferences.GefPreferenceConstants;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.typelibrary.ResourceTypeEntry;
import org.eclipse.fordiac.ide.systemconfiguration.commands.ResourceCreateCommand;
import org.eclipse.fordiac.ide.systemconfiguration.commands.ResourceMoveCommand;
import org.eclipse.fordiac.ide.systemconfiguration.editparts.ResourceContainer;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.FlowLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

public class ResourceContainerLayoutEditPolicy extends FlowLayoutEditPolicy {

	@Override
	protected EditPolicy createChildEditPolicy(final EditPart child) {

		return new ModifiedNonResizeableEditPolicy(GefPreferenceConstants.CORNER_DIM_HALF, new Insets(1));
	}

	@Override
	protected Command createMoveChildCommand(final EditPart child, final EditPart after) {
		return getMoveCommand(child, after);
	}

	@Override
	protected Command getCreateCommand(final CreateRequest request) {
		if (request == null) {
			return null;
		}
		final Object childClass = request.getNewObjectType();
		if (childClass instanceof ResourceTypeEntry) {
			final ResourceTypeEntry type = (ResourceTypeEntry) request.getNewObjectType();
			if (getHost().getModel() instanceof ResourceContainer) {
				final ResourceContainer resContainer = (ResourceContainer) getHost().getModel();
				final EditPart ref = getInsertionReference(request);
				int index = -1;
				if (ref != null) {
					index = resContainer.getDevice().getResource().indexOf(ref.getModel());
				}
				return new ResourceCreateCommand(type, resContainer.getDevice(), index, false);
			}
		}
		return null;
	}

	@Override
	protected Command createAddCommand(final EditPart child, final EditPart after) {
		return getMoveCommand(child, after);
	}

	private ResourceMoveCommand getMoveCommand(final EditPart child, final EditPart after) {
		ResourceMoveCommand cmd = null;
		if (child.getModel() instanceof Resource && getHost().getModel() instanceof ResourceContainer) {
			int index = -1;
			final Device targetDevice = ((ResourceContainer) getHost().getModel()).getDevice();
			if (after == null) {
				index = targetDevice.getResource().size();
			} else {
				index = targetDevice.getResource().indexOf(after.getModel());
			}
			cmd = new ResourceMoveCommand((Resource) child.getModel(), targetDevice, index);
		}
		return cmd;
	}
}
