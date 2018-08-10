/*******************************************************************************
 * Copyright (c) 2008, 2012 - 2017 Profactor GmbH, fortiss GmbH
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
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.policies;

import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.CreateECActionCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionAlgorithmEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionOutputEventEditPart;
import org.eclipse.fordiac.ide.gef.policies.ModifiedNonResizeableEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.AlignmentRequest;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

/**
 * The Class ECStateLayoutEditPolicy.
 */
public class ECStateLayoutEditPolicy extends XYLayoutEditPolicy {
	
	@Override
	protected Command getResizeChildrenCommand(ChangeBoundsRequest request) {
		for (Object editPart : request.getEditParts()) {
			if((editPart instanceof ECActionAlgorithmEditPart) || 
					(editPart instanceof ECActionOutputEventEditPart)){
				//actions should not be moved or resized
				return null;
			}
		}	
		
		return super.getResizeChildrenCommand(request);
	}


	@Override
	public Command getCommand(Request request) {
		Object type = request.getType();

		if (REQ_ALIGN.equals(type) && request instanceof AlignmentRequest) {
			return getAlignCommand((AlignmentRequest) request);
		}
		return super.getCommand(request);
	}

	protected Command getAlignCommand(AlignmentRequest request) {
		AlignmentRequest req = new AlignmentRequest(REQ_ALIGN_CHILDREN);
		req.setEditParts(getHost());
		req.setAlignment(request.getAlignment());
		req.setAlignmentRectangle(request.getAlignmentRectangle());
		return getHost().getParent().getCommand(req);
	}

	@Override
	protected EditPolicy createChildEditPolicy(EditPart child) {
		ModifiedNonResizeableEditPolicy editPolicy = new ModifiedNonResizeableEditPolicy(0, new Insets(0)){
			@Override
			protected Command getMoveCommand(ChangeBoundsRequest request) {	
				//algorithms and states should not be movable relative to the state
				return null;
			}			
		};
		
		editPolicy.setDragAllowed(false);
		return editPolicy;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.LayoutEditPolicy#getCreateCommand(org.eclipse
	 * .gef.requests.CreateRequest)
	 */
	@Override
	protected Command getCreateCommand(final CreateRequest request) {
		if (request.getNewObjectType().equals(ECAction.class)
				&& getHost().getModel() instanceof ECState) {
			ECState state = (ECState) getHost().getModel();
			if((null != state) && (!state.isStartState())){
				//only create an action when the target is not the initial state
				return new CreateECActionCommand((ECAction) request.getNewObject(), state);
			}
		}

		return null;
	}

	@Override
	protected Command getAddCommand(Request generic) {
		// currently we don't allow to add any children
		return null;
	}
	
	

}
