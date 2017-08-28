/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2016, 2017 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.network.editparts;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.policies.FBNetworkXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;

public class CompositeFBNetworkLayoutEditPolicy extends FBNetworkXYLayoutEditPolicy {

	@Override
	protected Command getCreateCommand(final CreateRequest request) {
		if (request == null) {
			return null;
		}
		Object childClass = request.getNewObjectType();
		Rectangle constraint = (Rectangle) getConstraintFor(request);
		if (childClass instanceof org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry) {
			org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry type = (org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry) request
					.getNewObjectType();

			if (getHost().getModel() instanceof FBNetwork) {
				FBNetwork fbNetwork = (FBNetwork) getHost().getModel();
				Command cmd = new FBCreateCommand(type, fbNetwork, constraint.getLocation().x,
						constraint.getLocation().y);
				return cmd;
			}
		}
		return null;
	}
}
