/*******************************************************************************
 * Copyright (c) 2013, 2016 AIT, fortiss GmbH
 * 				 2018 Johannes Kepler University	
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Filip Andren, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Reworked this policy to confirm to latest model and to readd
 *                 the AddtoSubapp functionality.  
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.fordiac.ide.application.commands.AddElementsToSubAppCommand;
import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.gef.policies.EmptyXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.gef.policies.ModifiedMoveHandle;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * This policy creates an AddFBToSubAppCommand when user moves selected FBs over
 * a subapp. When this is possible the subapp is marked as selected.
 */
public class FBAddToSubAppLayoutEditPolicy extends EmptyXYLayoutEditPolicy {

	private Figure moveHandle;

	@SuppressWarnings("rawtypes")
	@Override
	protected Command getAddCommand(Request generic) {
		if (generic instanceof ChangeBoundsRequest
				&& getTargetEditPart(generic) instanceof SubAppForFBNetworkEditPart) {
			ChangeBoundsRequest request = (ChangeBoundsRequest) generic;
			List editParts = request.getEditParts();

			for (Object obj : editParts) {
				if (obj instanceof AbstractFBNElementEditPart) {
					// we have at least one draged element to be added to the subapp, create an add
					// command for it.
					return new AddElementsToSubAppCommand(
							((SubAppForFBNetworkEditPart) getTargetEditPart(generic)).getModel(), editParts);
				}
			}
		}
		return super.getAddCommand(generic);
	}

	@Override
	protected void showLayoutTargetFeedback(Request request) {
		if (REQ_ADD.equals(request.getType()) && null == moveHandle) {
			IPreferenceStore pf = Activator.getDefault().getPreferenceStore();
			int cornerDim = pf.getInt(DiagramPreferences.CORNER_DIM);
			if (cornerDim > 1) {
				cornerDim = cornerDim / 2;
			}
			moveHandle = new ModifiedMoveHandle((GraphicalEditPart) getTargetEditPart(request), new Insets(1),
					cornerDim);
			addFeedback(moveHandle);
		}
	}

	@Override
	protected void eraseLayoutTargetFeedback(Request request) {
		if (moveHandle != null) {
			removeFeedback(moveHandle);
			moveHandle = null;
		}
	}

}
