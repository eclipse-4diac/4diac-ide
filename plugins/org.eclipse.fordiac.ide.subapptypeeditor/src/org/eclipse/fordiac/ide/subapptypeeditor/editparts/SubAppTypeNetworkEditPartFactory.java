/*******************************************************************************
 * Copyright (c) 2014, 2016, 2017 fortiss GmbH
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
package org.eclipse.fordiac.ide.subapptypeeditor.editparts;

import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.network.editparts.CompositeNetworkEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.network.editparts.CompositeNetworkEditPartFactory;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.subapptypeeditor.policies.SubAppTypeFBNetworkLayoutEditPolicy;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.gef.ui.parts.GraphicalEditor;

public class SubAppTypeNetworkEditPartFactory extends
		CompositeNetworkEditPartFactory {

	public SubAppTypeNetworkEditPartFactory(GraphicalEditor editor, ZoomManager zoomManager) {
		super(editor, zoomManager);
	}
	
	@Override
	protected EditPart getPartForElement(EditPart context,
			Object modelElement) {
		if (modelElement instanceof FBNetwork) {
			CompositeNetworkEditPart compositeNetEP = new CompositeNetworkEditPart(){

				@Override
				protected void createEditPolicies() {
					installEditPolicy(EditPolicy.COMPONENT_ROLE,
							new RootComponentEditPolicy());
					// handles constraint changes of model elements and creation of new
					// model elements
					installEditPolicy(EditPolicy.LAYOUT_ROLE,
							new SubAppTypeFBNetworkLayoutEditPolicy());
				}
				
			};
			return compositeNetEP;
		}
		
		if (modelElement instanceof SubApp) {
			return new SubAppForFBNetworkEditPart(zoomManager);
		}		
		return super.getPartForElement(context, modelElement);
	}
	
	

}
