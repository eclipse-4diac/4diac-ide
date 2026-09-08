/*******************************************************************************
 * Copyright (c) 2026 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.debug.replaydebugging.response;

import java.util.List;

import org.eclipse.fordiac.ide.deployment.devResponse.DevResponseFactory;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;

/**
 * @brief Handles the response of a device
 *
 *        The values of the network are stored in the this response which is
 *        used to update the watches and show the current state in the UI.
 *
 *        It builds its response from instances of ResourceResponses
 */
public class DeviceResponse {

	private final Response response = DevResponseFactory.eINSTANCE.createResponse();

	public DeviceResponse(final List<ResourceResponse> resourceResponses) {
		response.setWatches(DevResponseFactory.eINSTANCE.createWatches());

		resourceResponses
				.forEach(resourceResponse -> response.getWatches().getResources().add(resourceResponse.getResponse()));
	}

	public void addResourceResponse(final ResourceResponse resourceResponse) {
		response.getWatches().getResources().add(resourceResponse.getResponse());
	}

	public void removeResourceResponse(final ResourceResponse resourceResponse) {
		response.getWatches().getResources().remove(resourceResponse.getResponse());
	}

	public Response getResponse() {
		return response;
	}

}
