/*******************************************************************************
 * Copyright (c) 2012, 2013, 2016 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *    - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtester.policies;

import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.editparts.TestEditPart;
import org.eclipse.fordiac.ide.gef.editparts.AbstractViewEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;


/**
 * The Class SetTestValueEditPolicy.
 */
public class SetTestValueEditPolicy extends DirectEditPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.DirectEditPolicy#getDirectEditCommand(org.
	 * eclipse.gef.requests.DirectEditRequest)
	 */
	@Override
	protected Command getDirectEditCommand(DirectEditRequest request) {
		if (getHost() instanceof TestEditPart) {
			TestEditPart testEditPart = (TestEditPart) getHost();
			testEditPart.getModel().setValue((String) request.getCellEditor().getValue());
			testEditPart.refresh();
			
			// return a dummy command needed to fulfill requirements of direct edit of interface value for testing.
			// canExecute is set to false so that it is not put onto the command stack and makes the editor dirty.
			return new Command(){
				@Override
				public boolean canExecute() {
					return false;
				}
			};
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.DirectEditPolicy#showCurrentEditValue(org.
	 * eclipse.gef.requests.DirectEditRequest)
	 */
	@Override
	protected void showCurrentEditValue(DirectEditRequest request) {
		String value = (String) request.getCellEditor().getValue();
		if (getHost() instanceof AbstractViewEditPart) {
			AbstractViewEditPart viewEditPart = (AbstractViewEditPart) getHost();
			viewEditPart.getNameLabel().setText(value);
		}
	}
}
