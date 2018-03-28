/*******************************************************************************
 * Copyright (c) 2018 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.network.viewer;

import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.application.policies.FBNElementSelectionPolicy;
import org.eclipse.fordiac.ide.gef.policies.EmptyXYLayoutEditPolicy;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.ZoomManager;

public class FBEditPartRO extends FBEditPart {
	
	FBEditPartRO(ZoomManager zoomManager) {
		super(zoomManager);
	}

	@Override
	protected void createEditPolicies() {
		// Highlight In and Outconnections of the selected fb, allow alignment of FBs
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new FBNElementSelectionPolicy());
		//correctly layout the internals
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EmptyXYLayoutEditPolicy());
		//do not allow any further editing of the RO FB
	}
	
	@Override
	public void performDirectEdit() {
		//don't do anything here to avoid direct edit
	}

}