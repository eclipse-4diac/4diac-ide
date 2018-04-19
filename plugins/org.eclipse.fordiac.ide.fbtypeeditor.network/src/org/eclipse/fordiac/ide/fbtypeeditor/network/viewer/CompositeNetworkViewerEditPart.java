/*******************************************************************************
 * Copyright (c) 2013 - 2017 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.network.viewer;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.fordiac.ide.fbtypeeditor.network.editparts.CompositeNetworkEditPart;
import org.eclipse.fordiac.ide.gef.policies.ModifiedMoveHandle;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.gef.editpolicies.SelectionEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

/**
 * Edit Part for the visualization of Composite Networks.
 * 
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class CompositeNetworkViewerEditPart extends CompositeNetworkEditPart {

	private FB fbInstance;

	// the CompositeNetworkEditPart which contained the instance of the
	// composite FB this editor visualizes
	private CompositeNetworkViewerEditPart parentInstanceViewerEditPart;

	public CompositeNetworkViewerEditPart getparentInstanceViewerEditPart() {
		return parentInstanceViewerEditPart;
	}

	public void setparentInstanceViewerEditPart(CompositeNetworkViewerEditPart parentEditPart) {
		this.parentInstanceViewerEditPart = parentEditPart;
	}

	public FB getFbInstance() {
		return fbInstance;
	}

	public void setFbInstance(FB fbInstance) {
		this.fbInstance = fbInstance;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new LayoutEditPolicy() {
			
			@Override
			protected Command getCreateCommand(CreateRequest request) {
				return null;
			}
			
			@Override
			protected EditPolicy createChildEditPolicy(EditPart child) {
				/* a simple selection edit policy which will show a rounded rectangle around the host */
				return new SelectionEditPolicy() {
					private ModifiedMoveHandle handle = null; 
					
					private ModifiedMoveHandle getHandle() {
						if(null == handle) {
							handle = new ModifiedMoveHandle((GraphicalEditPart) getHost(), new Insets(2), 14);
						}
						return handle;
					}

					@Override
					protected void hideSelection() {
						if (handle != null){
							IFigure layer = getLayer(LayerConstants.HANDLE_LAYER);
							layer.remove(handle);
						}
					}

					@Override
					protected void showSelection() {
						IFigure layer = getLayer(LayerConstants.HANDLE_LAYER);
						layer.add(getHandle());
					}
					
				};
			}

			@Override
			protected Command getMoveChildrenCommand(Request request) {
				return null;
			}
		});
	}
	
}
