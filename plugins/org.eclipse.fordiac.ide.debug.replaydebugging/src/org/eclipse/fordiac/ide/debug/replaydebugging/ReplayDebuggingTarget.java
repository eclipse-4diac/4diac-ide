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
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentDebugTarget;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

/**
 * @brief Represents a debug target for replay debugging.
 *
 *        This class extends DeploymentDebugTarget to handle replay debugging
 *        functionality. It manages a collection of devices selected for
 *        debugging and their associated traces.
 */
public class ReplayDebuggingTarget extends DeploymentDebugTarget {

	private final Map<String, String> deviceNameToPath = new HashMap<>();
	private final boolean remote;

	public ReplayDebuggingTarget(final AutomationSystem system, final Set<INamedElement> selection,
			final ILaunch launch, final boolean allowTerminate, final String tracesPath, final boolean remote)
			throws DeploymentException {
		// pass empty list to deploy if not remote, as we do not want to deploy
		super(system, remote ? selection : Set.of(), launch, allowTerminate, Duration.ofSeconds(30), List.of());
		this.remote = remote;
		for (final INamedElement element : selection) {
			if (element instanceof final Device device) {
				this.deviceNameToPath.put(device.getName(), tracesPath);
			}
		}
	}

	@Override
	protected void doConnect(final Device device) throws DebugException {
		final ReplayDebuggingDevice replayDebuggingDevice = new ReplayDebuggingDevice(device, this,
				deviceNameToPath.get(device.getName()), remote);

		replayDebuggingDevice.connect();
	}
}
