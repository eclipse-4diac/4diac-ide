/*******************************************************************************
 * Copyright (c) 2025 Jose Cabral
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

package org.eclipse.fordiac.ide.debug.replaydebugging.simulator.forte;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.fordiac.ide.debug.replaydebugging.simulator.IDeviceSimulator;
import org.eclipse.fordiac.ide.debug.replaydebugging.simulator.IResourceSimulator;
import org.eclipse.fordiac.ide.deployment.debug.Messages;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementExecutorService;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class DeviceSimulator implements IDeviceSimulator {

	private final Device device;
	private final String path;
	private final IDeviceManagementExecutorService executorService;

	public DeviceSimulator(final IDeviceManagementExecutorService executorService, final Device device,
			final String path) {
		this.device = device;
		this.path = path;
		this.executorService = executorService;
	}

	@Override
	public Map<Resource, IResourceSimulator> start() {
		final HashMap<Resource, IResourceSimulator> result = new HashMap<>();
		try {
			executorService.connect();
			executorService.readTraces(device, path);

			for (final Resource resource : device.getResource()) {
				result.put(resource, new ResourceSimulator(executorService, resource));
			}
		} catch (final DeploymentException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean stop() {
		try {
			executorService.disconnect();
		} catch (final DeploymentException e) {
			FordiacLogHelper
					.logError(MessageFormat.format(Messages.DeploymentDebugDevice_ConnectError, device.getName()));
			return false;
		}
		return true;
	}

}
