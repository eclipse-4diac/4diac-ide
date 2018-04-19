/*******************************************************************************
 * Copyright (c) 2014, 2016, 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.subapptypeeditor.policies;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.fbtypeeditor.network.editparts.CompositeFBNetworkLayoutEditPolicy;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.commands.create.CreateSubAppInstanceCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;

public class SubAppTypeFBNetworkLayoutEditPolicy extends CompositeFBNetworkLayoutEditPolicy {

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		if (request == null) {
			return null;
		}
		Object childClass = request.getNewObjectType();
		Rectangle constraint = (Rectangle) getConstraintFor(request);
		if (childClass instanceof SubApplicationTypePaletteEntry) {
			SubApplicationTypePaletteEntry type = (SubApplicationTypePaletteEntry) request.getNewObjectType();

			if (getHost().getModel() instanceof FBNetwork) {
				FBNetwork fbNetwork = (FBNetwork) getHost().getModel();
				CreateSubAppInstanceCommand cmd = new CreateSubAppInstanceCommand(type, fbNetwork, constraint.getLocation().x,
						constraint.getLocation().y);
				return cmd;
			}
		}
		return super.getCreateCommand(request);
	}

}
