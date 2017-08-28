/*******************************************************************************
 * Copyright (c) 2008 - 2016 Profactor GmbH, fortiss GmbH
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
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.CreateECStateCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionAlgorithmEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionOutputEventEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECStateEditPart;
import org.eclipse.fordiac.ide.gef.policies.ModifiedNonResizeableEditPolicy;
import org.eclipse.fordiac.ide.model.commands.change.SetPositionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

public class ECCXYLayoutEditPolicy extends XYLayoutEditPolicy {

	@Override
	protected EditPolicy createChildEditPolicy(EditPart child) {
		return new ModifiedNonResizeableEditPolicy(0, new Insets(4)) {
		};
	}

	@Override
	protected Command createChangeConstraintCommand(
			final ChangeBoundsRequest request, final EditPart child,
			final Object constraint) {
		if (child instanceof ECStateEditPart) {
			ECStateEditPart temp = (ECStateEditPart) child;
			translateToModelConstraint(constraint);
			return new SetPositionCommand((ECState) temp.getModel(), request, (Rectangle) constraint);
		}
		return null;
	}

	@Override
	protected Command getAddCommand(Request generic) {		
		if(generic instanceof ChangeBoundsRequest){
			ChangeBoundsRequest request = (ChangeBoundsRequest)generic;			
			for (Object editPart : request.getEditParts()) {
				if((editPart instanceof ECActionAlgorithmEditPart) ||
						(editPart instanceof ECActionOutputEventEditPart)){
					//actions should not be moved or resized
					return null;
				}
			}
		}	
		return super.getAddCommand(generic);
	}

	@Override
	protected Command createChangeConstraintCommand(final EditPart child,
			final Object constraint) {
		return null;
	}

	@Override
	protected Command getCreateCommand(final CreateRequest request) {	
		if (request.getNewObjectType().equals(ECState.class)
				&& getHost().getModel() instanceof ECC) {
			Point point = request.getLocation().getCopy();
			getHostFigure().translateToRelative(point);
			return new CreateECStateCommand((ECState) request.getNewObject(), point, (ECC) getHost().getModel());
		}
		return null;
	}
}