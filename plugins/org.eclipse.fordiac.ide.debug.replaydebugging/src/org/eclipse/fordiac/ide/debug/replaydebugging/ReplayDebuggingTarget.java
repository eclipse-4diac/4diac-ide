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
package org.eclipse.fordiac.ide.debug.replaydebugging;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentDebugTarget;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;

/**
 * @brief Represents a debug target for replay debugging.
 *
 *        This class extends DeploymentDebugTarget to handle replay debugging
 *        functionality. It manages a collection of devices selected for
 *        debugging and their associated traces.
 */
public class ReplayDebuggingTarget extends DeploymentDebugTarget {

	/**
	 * All the information needed to run replay debugging on a single device: the
	 * device itself, the resources selected within it, whether it should run
	 * remotely (via Forte), and the trace path to use for it.
	 */
	public static final class DeviceReplaySettings {

		private final Device device;
		private final Set<Resource> resources;
		private final boolean remote;
		private final String tracePath;

		public DeviceReplaySettings(final Device device, final Set<Resource> resources, final boolean remote,
				final String tracePath) {
			this.device = device;
			this.resources = resources;
			this.remote = remote;
			this.tracePath = tracePath;
		}

		public Device getDevice() {
			return device;
		}

		public Set<Resource> getResources() {
			return resources;
		}

		public boolean isRemote() {
			return remote;
		}

		public String getTracePath() {
			return tracePath;
		}
	}

	private final Map<String, String> deviceNameToPath = new HashMap<>();
	private final Map<String, Boolean> deviceNameToRemote = new HashMap<>();
	private final Map<String, Set<String>> deviceNameToResources = new HashMap<>();

	public ReplayDebuggingTarget(final AutomationSystem system, final List<DeviceReplaySettings> deviceSettings,
			final ILaunch launch, final boolean allowTerminate) throws DeploymentException {
		super(system, computeDeploySelection(deviceSettings), launch, allowTerminate, Duration.ofSeconds(30),
				List.of());

		for (final DeviceReplaySettings settings : deviceSettings) {
			final String deviceName = settings.getDevice().getName();
			deviceNameToPath.put(deviceName, settings.getTracePath());
			deviceNameToRemote.put(deviceName, settings.isRemote());
			deviceNameToResources.put(deviceName,
					settings.getResources().stream().map(Resource::getName).collect(Collectors.toSet()));
		}
	}

	private static Set<INamedElement> computeDeploySelection(final List<DeviceReplaySettings> deviceSettings) {
		final Set<INamedElement> result = new HashSet<>();
		for (final DeviceReplaySettings settings : deviceSettings) {
			if (settings.isRemote()) {
				result.add(settings.getDevice());
				result.addAll(settings.getResources());
			}
		}
		return result;
	}

	@Override
	protected void doConnect(final Device device) throws DebugException {
		final var path = deviceNameToPath.get(device.getName());
		if (path == null) {
			// trying to connect to a device that has not been selected
			return;
		}
		final boolean deviceRemote = deviceNameToRemote.getOrDefault(device.getName(), false);
		final Set<String> deviceResources = deviceNameToResources.getOrDefault(device.getName(), Set.of());

		final ReplayDebuggingDevice replayDebuggingDevice = new ReplayDebuggingDevice(device, deviceResources, this,
				path, deviceRemote);

		replayDebuggingDevice.connect();
	}
}
