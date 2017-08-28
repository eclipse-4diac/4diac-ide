/*******************************************************************************
 * Copyright (c) 2013, 2016 AIT, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Filip Andren, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.gef.policies.EmptyXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.gef.policies.ModifiedMoveHandle;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * This policy creates an AddFBToSubAppCommand when user moves selected FBs
 * over a subapp. When this is possible the subapp is marked as selected.
 */
public class FBAddToSubAppLayoutEditPolicy extends EmptyXYLayoutEditPolicy {

	Figure moveHandle;
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#getAddCommand(org.eclipse.gef.Request)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected Command getAddCommand(Request generic) {
		ChangeBoundsRequest request = (ChangeBoundsRequest) generic;
		List editParts = request.getEditParts();
		
		ArrayList<FB> fbs = new ArrayList<FB>();
		ArrayList<SubApp> subApps = new ArrayList<>(); 
		
		GraphicalEditPart child;

		for (int i = 0; i < editParts.size(); i++) {
			child = (GraphicalEditPart) editParts.get(i);
			if (child instanceof FBEditPart) {
				fbs.add(((FBEditPart) child).getModel());
			} else if (child instanceof SubAppForFBNetworkEditPart) {
				subApps.add(((SubAppForFBNetworkEditPart) child).getModel());
			}
		}
		if (fbs.isEmpty() && subApps.isEmpty()) {
			return super.getAddCommand(generic);
		}
		
//TODO model refactoring - implement when subapplications are reworked 		
//		AddFBToSubAppCommand command = new AddFBToSubAppCommand(((SubAppForFBNetworkEditPart)getTargetEditPart(request)).getCastedModel(), fbs, subApps);
//		return command;
		return null;
	}



	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#showLayoutTargetFeedback(org.eclipse.gef.Request)
	 */
	@Override
	protected void showLayoutTargetFeedback(Request request) {
		if (REQ_ADD.equals(request.getType())) {
			if (moveHandle == null) {
				IPreferenceStore pf = Activator.getDefault().getPreferenceStore();
				int cornerDim = pf.getInt(DiagramPreferences.CORNER_DIM);
				if (cornerDim > 1) {
					cornerDim = cornerDim / 2;
				}
				moveHandle = new ModifiedMoveHandle((GraphicalEditPart) getTargetEditPart(request), new Insets(1), cornerDim);							
				addFeedback(moveHandle);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#eraseLayoutTargetFeedback(org.eclipse.gef.Request)
	 */
	@Override
	protected void eraseLayoutTargetFeedback(Request request) {
		if (moveHandle != null) {
			removeFeedback(moveHandle);
			moveHandle = null;
		}
	}
	
	
}
