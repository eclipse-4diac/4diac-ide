/*******************************************************************************
 * Copyright (c) 2014, 2016, 2017 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.subapptypeeditor.policies;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.fbtypeeditor.network.editparts.CompositeFBNetworkLayoutEditPolicy;
import org.eclipse.fordiac.ide.model.commands.create.CreateSubAppInstanceCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.typelibrary.SubAppTypeEntry;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;

public class SubAppTypeFBNetworkLayoutEditPolicy extends CompositeFBNetworkLayoutEditPolicy {

	@Override
	protected Command getCreateCommand(final CreateRequest request) {
		if (request == null) {
			return null;
		}
		final Object childClass = request.getNewObjectType();
		final Rectangle constraint = (Rectangle) getConstraintFor(request);
		if (childClass instanceof SubAppTypeEntry) {
			final SubAppTypeEntry type = (SubAppTypeEntry) childClass;

			if (getHost().getModel() instanceof FBNetwork) {
				final FBNetwork fbNetwork = (FBNetwork) getHost().getModel();
				return new CreateSubAppInstanceCommand(type, fbNetwork, constraint.getLocation().x,
						constraint.getLocation().y);
			}
		}
		return super.getCreateCommand(request);
	}

}
