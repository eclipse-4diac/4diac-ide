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

package org.eclipse.fordiac.ide.debug.replaydebugging.replayer.forte;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.fordiac.ide.debug.replaydebugging.replayer.IDeviceReplayer;
import org.eclipse.fordiac.ide.debug.replaydebugging.replayer.IResourceReplayer;
import org.eclipse.fordiac.ide.deployment.debug.Messages;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementExecutorService;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class DeviceReplayer implements IDeviceReplayer {

	private final Device device;
	private final String path;
	private final IDeviceManagementExecutorService executorService;

	public DeviceReplayer(final IDeviceManagementExecutorService executorService, final Device device,
			final String path) {
		this.device = device;
		this.path = path;
		this.executorService = executorService;
	}

	@Override
	public Map<Resource, IResourceReplayer> start() {
		final HashMap<Resource, IResourceReplayer> result = new HashMap<>();
		try {
			executorService.connect();
			executorService.readTraces(device, path);

			for (final Resource resource : device.getResource()) {
				result.put(resource, new ResourceReplayer(executorService, resource));
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
