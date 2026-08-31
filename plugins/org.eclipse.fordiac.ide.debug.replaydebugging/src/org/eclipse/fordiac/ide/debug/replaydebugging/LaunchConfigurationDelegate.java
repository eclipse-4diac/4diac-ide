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
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.replaydebugging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentLaunchConfigurationAttributes;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentLaunchConfigurationDelegate;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.util.FordiacLogHelper;

/**
 * Launch delegate for the Replay Debugging configuration.
 *
 * Reads the configuration from the user and create the objects needed for
 * replay debugging
 */
public class LaunchConfigurationDelegate extends DeploymentLaunchConfigurationDelegate {

	private static final String PLUGIN_ID = "org.eclipse.fordiac.ide.debug.replaydebugging"; //$NON-NLS-1$

	public static final String ATTR_DEVICE_REMOTE_MAP = PLUGIN_ID + ".DEVICE_REMOTE_MAP"; //$NON-NLS-1$
	public static final String ATTR_DEVICE_TRACE_PATH_MAP = PLUGIN_ID + ".DEVICE_TRACE_PATH_MAP"; //$NON-NLS-1$
	public static final Map<String, String> ATTR_DEVICE_MAP_DEFAULT = Collections.emptyMap();

	@Override
	public void launch(final ILaunchConfiguration configuration, final String mode, final ILaunch launch,
			final org.eclipse.core.runtime.IProgressMonitor monitor) {
		if (!ILaunchManager.DEBUG_MODE.equals(mode)) {
			return;
		}
		try {
			final AutomationSystem system = DeploymentLaunchConfigurationAttributes.getSystem(configuration);
			if (system == null) {
				throw new CoreException(Status.error("Cannot find system")); //$NON-NLS-1$
			}
			final Set<INamedElement> selection = DeploymentLaunchConfigurationAttributes.getSelection(configuration,
					system);

			final Map<String, String> tracePathByDevice = configuration.getAttribute(ATTR_DEVICE_TRACE_PATH_MAP,
					ATTR_DEVICE_MAP_DEFAULT);
			final Map<String, String> remoteByDevice = configuration.getAttribute(ATTR_DEVICE_REMOTE_MAP,
					ATTR_DEVICE_MAP_DEFAULT);

			final List<ReplayDebuggingTarget.DeviceReplaySettings> deviceSettings = buildDeviceSettings(selection,
					tracePathByDevice, remoteByDevice);

			final IResource resource = DeploymentLaunchConfigurationAttributes.getSystemResource(configuration);
			launch.setAttribute(SYSTEM_FILE_ATTRIBUTE, resource.getFullPath().toString());

			final ReplayDebuggingTarget debugTarget = new ReplayDebuggingTarget(system, deviceSettings, launch, true);
			debugTarget.start();

		} catch (final Exception e) {
			FordiacLogHelper.logError("Couldn't launch replay debugging target!", e); //$NON-NLS-1$
		}
	}

	/**
	 * Regroups the flat selection (devices + individual resources, as stored by the
	 * tab) back under their owning device, and attaches each device's own trace
	 * path / remote flag from the saved per-device maps.
	 */
	private static List<ReplayDebuggingTarget.DeviceReplaySettings> buildDeviceSettings(
			final Set<INamedElement> selection, final Map<String, String> tracePathByDevice,
			final Map<String, String> remoteByDevice) {
		final Map<Device, Set<Resource>> resourcesByDevice = new LinkedHashMap<>();
		for (final INamedElement element : selection) {
			if (element instanceof final Device device) {
				resourcesByDevice.computeIfAbsent(device, _ -> new LinkedHashSet<>());
			} else if (element instanceof final Resource resource
					&& resource.eContainer() instanceof final Device device) {
				resourcesByDevice.computeIfAbsent(device, _ -> new LinkedHashSet<>()).add(resource);
			}
		}

		final List<ReplayDebuggingTarget.DeviceReplaySettings> result = new ArrayList<>();
		for (final Map.Entry<Device, Set<Resource>> entry : resourcesByDevice.entrySet()) {
			final Device device = entry.getKey();
			final String qualifiedName = device.getQualifiedName();
			final String tracePath = tracePathByDevice.get(qualifiedName);
			final boolean remote = Boolean.parseBoolean(remoteByDevice.get(qualifiedName));
			result.add(new ReplayDebuggingTarget.DeviceReplaySettings(device, entry.getValue(), remote, tracePath));
		}
		return result;
	}
}