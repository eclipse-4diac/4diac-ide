/*******************************************************************************
 * Copyright (c) 2023, 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.ui.view.policies;

import org.eclipse.fordiac.ide.debug.ui.view.editparts.InterfaceValueEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

public class InterfaceValueDirectEditPolicy extends DirectEditPolicy {

	@Override
	protected Command getDirectEditCommand(final DirectEditRequest request) {
		if (getHost() instanceof final InterfaceValueEditPart ivEP) {
			ivEP.getModel().updateValue((String) request.getCellEditor().getValue());
		}
		// as we directly apply the value we don't need to return a command
		return null;
	}

	@Override
	protected void showCurrentEditValue(final DirectEditRequest request) {
		final String value = (String) request.getCellEditor().getValue();
		if (getHost() instanceof final InterfaceValueEditPart ivEP) {
			ivEP.getLabelFigure().setText(value);
		}
	}

	@Override
	protected void revertOldEditValue(final DirectEditRequest request) {
		if (getHost() instanceof final InterfaceValueEditPart ivEP) {
			ivEP.getLabelFigure().setText(ivEP.getModel().getVariable().getValue().toString());
		}
	}

	@Override
	public boolean understandsRequest(final Request request) {
		// also start direct editor on double click
		if (RequestConstants.REQ_OPEN.equals(request.getType())) {
			return true;
		}
		return super.understandsRequest(request);
	}
}
