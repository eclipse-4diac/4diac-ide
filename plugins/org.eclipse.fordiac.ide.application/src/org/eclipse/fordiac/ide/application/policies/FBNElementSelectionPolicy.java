/*******************************************************************************
 * Copyright (c) 2008, 2009, 2012, 2016 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
package org.eclipse.fordiac.ide.application.policies;

import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.SelectionEditPolicy;
import org.eclipse.gef.requests.AlignmentRequest;

/**
 * The Class FBSelectionPolicy.
 * Highlights the In/Outconnections of an FB. 
 * 
 * Returns AlignmentCommand for alignment of FBs.
 * 
 */
public class FBNElementSelectionPolicy extends SelectionEditPolicy {

	@Override
	protected void hideSelection() {
		for (Object object : ((AbstractFBNElementEditPart)getHost()).getChildren()) {
			if (object instanceof InterfaceEditPart) {
				((InterfaceEditPart) object).setInOutConnectionsWidth(0);

			}
		}
	}

	@Override
	protected void showSelection() {
		for (Object object : ((AbstractFBNElementEditPart)getHost()).getChildren()) {
			if (object instanceof InterfaceEditPart) {
				((InterfaceEditPart) object).setInOutConnectionsWidth(2);

			}
		}
	}

	
	/**
	 * @see org.eclipse.gef.EditPolicy#getCommand(org.eclipse.gef.Request)
	 */
	@Override
	public Command getCommand(Request request) {
		Object type = request.getType();
		if (REQ_ALIGN.equals(type) && request instanceof AlignmentRequest) {
			return getAlignCommand((AlignmentRequest) request);
		}
		return null;
	}
	
	/**
	 * Returns the command contribution to an alignment request
	 * 
	 * @param request
	 *            the alignment request
	 * @return the contribution to the alignment
	 */
	protected Command getAlignCommand(AlignmentRequest request) {
		AlignmentRequest req = new AlignmentRequest(REQ_ALIGN_CHILDREN);
		req.setEditParts(getHost());
		req.setAlignment(request.getAlignment());
		req.setAlignmentRectangle(request.getAlignmentRectangle());
		return getHost().getParent().getCommand(req);
	}
	
}
