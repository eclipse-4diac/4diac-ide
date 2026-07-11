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

package org.eclipse.fordiac.ide.debug.replaydebugging.replayer.interpreter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.fordiac.debug.replaydebugging.trace.SendOutputEvent;
import org.eclipse.fordiac.debug.replaydebugging.trace.TracesReader;
import org.eclipse.fordiac.ide.debug.replaydebugging.replayer.IDeviceReplayer;
import org.eclipse.fordiac.ide.debug.replaydebugging.replayer.IResourceReplayer;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.tracecompass.ctf.core.CTFException;

/**
 * @brief Replays the execution of a device using the interpreter
 *
 *        It reads the traces from the given path and creates a resource
 *        replayer to replay them separately. If no path is provided, no event
 *        is replayed initially and the replayers starts from its initial state.
 */
public class DeviceReplayer implements IDeviceReplayer {

	private final String path;
	private final Device device;
	private final Set<String> resources;

	public DeviceReplayer(final Device device, final Set<String> resources, final String path) {
		this.path = path;
		this.device = device;
		this.resources = resources;
	}

	@Override
	public Map<Resource, IResourceReplayer> start() {
		final Map<Resource, IResourceReplayer> result = new HashMap<>();
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
			if (!resources.contains(resource.getName())) {
				continue;
			}
			// filter sifb events as we still get all events from forte
			final var sifbEvents = externalEvents.getOrDefault(resource.getName(), List.of()).stream()
					.map(externalEvent -> Map.entry(externalEvent,
							getInstanceFB(resource, externalEvent.instanceName())))
					.filter(entry -> isSIFB(entry.getValue().getType())).map(Map.Entry::getKey).toList();

			result.put(resource, new ResourceReplayer(resource, sifbEvents));
		}

		return result;
	}

	@Override
	public boolean stop() {
		// nothing to do
		return true;
	}

	// From here and below, it's only needed to filter non-sifb output events.
	// Remove when forte generates only SIFB output events
	private static FB getInstanceFB(final Resource resource, final String fbName) {

		// look in the resource first
		final FB fb = resource.getFBNetwork().getFBNamed(fbName);
		if (fb != null) {
			return fb;
		}
		// then look in the applications
		if (!fbName.contains(".")) { //$NON-NLS-1$
			return null; // no point in looking further if there is no dot
		}

		final var prefix = fbName.substring(0, fbName.indexOf('.'));

		// check if the prefix is an application
		final var application = resource.getAutomationSystem().getApplication().stream()
				.filter(app -> app.getName().equals(prefix)).findFirst();
		if (!application.isPresent()) {
			System.err.println("Application name " + prefix + " does not exist"); //$NON-NLS-1$
			return null;
		}

		final var suffix = fbName.substring(fbName.indexOf('.') + 1);
		return getInstanceFB(resource.getFBNetwork(), suffix);
	}

	private static FB getInstanceFB(final FBNetwork network, final String name) {
		if (network == null) {
			return null;
		}
		if (!name.contains(".")) {//$NON-NLS-1$
			return network.getFBNamed(name);
		}

		final var prefix = name.substring(0, name.indexOf('.'));
		final var suffix = name.substring(name.indexOf('.') + 1);

		for (final var networkElement : network.getNetworkElements()) {
			if (networkElement.getName().equals(prefix)) {
				if (networkElement instanceof final CFBInstance cfb) {
					return getInstanceFB(cfb.loadCFBNetwork(), suffix);
				}
				if (networkElement instanceof final SubApp subApp) {
					var internalNetwork = subApp.loadSubAppNetwork();
					if (internalNetwork == null) {
						internalNetwork = ((SubApp) subApp.getOpposite()).loadSubAppNetwork();
					}
					return getInstanceFB(internalNetwork, suffix);
				}
				System.err.println("Element with name " + prefix + " has not internal network"); //$NON-NLS-1$
			}
		}
		return null;
	}

	private static final Set<String> SIFB_IN_FORTE_NOT_IN_IDE = Set.of("E_CYCLE");

	private static boolean isSIFB(final FBType fbType) {
		if (SIFB_IN_FORTE_NOT_IN_IDE.contains(fbType.getName())) {
			return true;
		}
		return (fbType instanceof ServiceInterfaceFBType);
	}

}