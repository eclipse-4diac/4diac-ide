/*******************************************************************************
 * Copyright (c) 2014 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.policies;

import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.ChangeServiceInterfaceNameCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.SequenceRootEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.SequenceRootEditPart.ServiceFigure;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

public class ChangeInterfaceNameEditPolicy extends DirectEditPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editpolicies.DirectEditPolicy#getDirectEditCommand(org
	 * .eclipse.gef.requests.DirectEditRequest)
	 */
	@Override
	protected Command getDirectEditCommand(final DirectEditRequest request) {
		if (getHost() instanceof SequenceRootEditPart) {
			SequenceRootEditPart viewEditPart = (SequenceRootEditPart) getHost();
			return new ChangeServiceInterfaceNameCommand((String) request.getCellEditor().getValue(),
					viewEditPart.getFBType(), viewEditPart.isLeft(request));
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editpolicies.DirectEditPolicy#showCurrentEditValue(org
	 * .eclipse.gef.requests.DirectEditRequest)
	 */
	@Override
	protected void showCurrentEditValue(final DirectEditRequest request) {
		String value = (String) request.getCellEditor().getValue();
		if (getHost() instanceof SequenceRootEditPart) {
			SequenceRootEditPart viewEditPart = (SequenceRootEditPart) getHost();
			if (viewEditPart.isLeft(request)) {
				((ServiceFigure) viewEditPart.getFigure()).getLeftLabel().setText(value);
			} else {
				((ServiceFigure) viewEditPart.getFigure()).getRightLabel().setText(value);
			}
		}
	}
}
