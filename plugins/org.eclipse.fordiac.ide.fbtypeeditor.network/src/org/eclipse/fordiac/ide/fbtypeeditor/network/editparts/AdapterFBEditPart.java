/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.network.editparts;

import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

public class AdapterFBEditPart extends FBEditPart {

	public AdapterFBEditPart(ZoomManager zoomManager) {
		super(zoomManager);
	}
	
	@Override
	public AdapterFB getModel(){
		return (AdapterFB)super.getModel();
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		
		removeEditPolicy(EditPolicy.COMPONENT_ROLE);
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ComponentEditPolicy(){
			@Override
			protected Command createDeleteCommand(GroupRequest deleteRequest) {
				if (getHost() instanceof AdapterFBEditPart) {
					AdapterFB adapter = ((AdapterFBEditPart)getHost()).getModel();					
					return new DeleteInterfaceCommand(adapter.getAdapterDecl());
				}
				
				return super.createDeleteCommand(deleteRequest);
			}
		});
		
	}
	
	

}
