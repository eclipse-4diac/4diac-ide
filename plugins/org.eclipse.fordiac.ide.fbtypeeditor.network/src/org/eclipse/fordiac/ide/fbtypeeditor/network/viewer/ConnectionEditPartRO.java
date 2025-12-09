/*******************************************************************************
 * Copyright (c) 2018 fortiss GmbH
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
package org.eclipse.fordiac.ide.fbtypeeditor.network.viewer;

import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.policies.FBNConnectionEndpointPolicy;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.requests.ReconnectRequest;

public class ConnectionEditPartRO extends ConnectionEditPart {
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new FBNConnectionEndpointPolicy() {
			@Override
			protected void showConnectionMoveFeedback(final ReconnectRequest request) {
				// we do not want to allow moving viewer connections
			}
		});

	}
}