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
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.debug.replaydebugging.simulator.interpreter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.fordiac.debug.replaydebugging.trace.SendOutputEvent;
import org.eclipse.fordiac.debug.replaydebugging.trace.TracesReader;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.Utils;
import org.eclipse.fordiac.ide.debug.replaydebugging.simulator.IDeviceSimulator;
import org.eclipse.fordiac.ide.debug.replaydebugging.simulator.IResourceSimulator;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.tracecompass.ctf.core.CTFException;

/**
 * @brief Simulates the execution of a device using the interpreter
 *
 *        It reads the traces from the given paths and creates a resource
 *        simulator to simulate them separately. A ResourceState instance gives
 *        access to the current state of the simulator to retrieve the current
 *        state, but also to update the event counters (information not inside
 *        the resource)
 */
public class DeviceSimulator implements IDeviceSimulator {

	private final String path;
	private final Device device;

	public DeviceSimulator(final Device device, final String path) {
		this.path = path;
		this.device = device;
	}

	@Override
	public Map<Resource, IResourceSimulator> start() {
		final Map<Resource, IResourceSimulator> result = new HashMap<>();
		Map<String, List<SendOutputEvent>> externalEvents = new HashMap<>();

		if (!path.isEmpty()) {
			final TracesReader tracerReader = new TracesReader(path);
			try {
				externalEvents = tracerReader.read();
			} catch (final CTFException e) {
				FordiacLogHelper.logError("Error reading traces: " + e.getMessage()); //$NON-NLS-1$
				return result;
			}
		}

		for (final var resource : device.getResource()) {
			// filter sifb events as we still get all events from forte
			final var sifbEvents = externalEvents.getOrDefault(resource.getName(), List.of()).stream()
					.map(externalEvent -> Map.entry(externalEvent,
							Utils.getInstanceFB(resource, externalEvent.instanceName())))
					.filter(entry -> Utils.isSIFB(entry.getValue().getType())).map(Map.Entry::getKey).toList();

			result.put(resource, new ResourceSimulator(resource, sifbEvents));
		}

		return result;
	}

	@Override
	public boolean stop() {
		// these aren't the droids you are looking for
		return true;
	}

}