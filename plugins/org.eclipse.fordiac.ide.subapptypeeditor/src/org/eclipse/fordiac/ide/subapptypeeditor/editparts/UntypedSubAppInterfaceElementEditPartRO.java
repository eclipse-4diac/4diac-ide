/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.subapptypeeditor.editparts;

import org.eclipse.fordiac.ide.application.editparts.UntypedSubAppInterfaceElementEditPart;
import org.eclipse.fordiac.ide.gef.policies.InterfaceElementSelectionPolicy;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;

public class UntypedSubAppInterfaceElementEditPartRO extends UntypedSubAppInterfaceElementEditPart {

	@Override
	protected GraphicalNodeEditPolicy getNodeEditPolicy() {
		return null;
	}

	@Override
	public boolean isConnectable() {
		return false;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new InterfaceElementSelectionPolicy(this));
	}

	@Override
	public void performRequest(final Request request) {
		if (request.getType() != RequestConstants.REQ_DIRECT_EDIT) {
			// only handle the request when it is not a direct edit request
			super.performRequest(request);
		}
	}

}
