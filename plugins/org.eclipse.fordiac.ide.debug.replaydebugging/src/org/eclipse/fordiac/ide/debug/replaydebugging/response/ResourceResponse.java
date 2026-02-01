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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.deployment.devResponse.Data;
import org.eclipse.fordiac.ide.deployment.devResponse.DevResponseFactory;
import org.eclipse.fordiac.ide.deployment.devResponse.FB;
import org.eclipse.fordiac.ide.deployment.devResponse.Port;
import org.eclipse.fordiac.ide.deployment.devResponse.Resource;

/**
 * @brief Resource response handler used for watches
 *
 *        It creates a resource response from a given map of datapoints strings
 *        and their values.
 *
 *        It offers an interface to update datapoints values.
 */
public class ResourceResponse {

	Resource response = DevResponseFactory.eINSTANCE.createResource();

	// map from the datapoints to the data values in the response. This allows fast
	// update of values
	private final Map<String, Data> dataValues = new HashMap<>();

	/**
	 * @brief Creates the resource response based on a list of datapoints and their
	 *        values.
	 *
	 *        This method iterates over all datapoints, creates FBs and ports in the
	 *        response, and stores the data values for each datapoint.
	 *
	 * @param resourceName        name of the resource
	 * @param allDatapointsStates datapoints and their values from which the
	 *                            resource response is created.
	 */
	public ResourceResponse(final String resourceName, final Map<String, String> allDatapointsStates) {
		response.setName(resourceName);

		// handle FBs in the response
		final EList<FB> responseFBs = response.getFbs();

		// list of already added FBs
		final HashMap<String, FB> existingResponseFBs = new HashMap<>();
		for (final Map.Entry<String, String> entry : allDatapointsStates.entrySet()) {
			final String datapoint = entry.getKey();
			final String value = entry.getValue();

			final int lastDot = datapoint.lastIndexOf('.');
			final String fbName = datapoint.substring(0, lastDot);
			final String portName = datapoint.substring(lastDot + 1);

			FB responseFB;
			if (!existingResponseFBs.containsKey(fbName)) {
				// create response FB if first time seeing it
				responseFB = DevResponseFactory.eINSTANCE.createFB();
				responseFB.setName(fbName);
				responseFBs.add(responseFB);

				// add it to the list of existing FBs
				existingResponseFBs.put(fbName, responseFB);
			} else {
				responseFB = existingResponseFBs.get(fbName);
			}
			final EList<Port> responseFBPorts = responseFB.getPorts();
			final Port responsePort = DevResponseFactory.eINSTANCE.createPort();
			responsePort.setName(portName);
			responseFBPorts.add(responsePort);

			final EList<Data> responsePortDataValues = responsePort.getDataValues();
			final Data dataValuesValue = DevResponseFactory.eINSTANCE.createData();
			dataValuesValue.setValue(value);
			responsePortDataValues.add(dataValuesValue);

			// store the quick access to the data values
			dataValues.put(datapoint, dataValuesValue);
		}
	}

	public Resource getResponse() {
		return response;
	}

	public void updateResponse(final Map<String, String> changedValues) {
		for (final Map.Entry<String, String> entry : changedValues.entrySet()) {
			final String datapoint = entry.getKey();
			final String value = entry.getValue();

			final Data dataValue = dataValues.get(datapoint);
			dataValue.setValue(value);
		}
	}

}
