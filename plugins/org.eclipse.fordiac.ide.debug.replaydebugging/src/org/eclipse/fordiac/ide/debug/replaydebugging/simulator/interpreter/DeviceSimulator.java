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
import java.util.Optional;
import java.util.Set;

import org.eclipse.fordiac.debug.replaydebugging.trace.SendOutputEvent;
import org.eclipse.fordiac.debug.replaydebugging.trace.TracesReader;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.Utils;
import org.eclipse.fordiac.ide.debug.replaydebugging.simulator.IDeviceSimulator;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.tracecompass.ctf.core.CTFException;

public class DeviceSimulator implements IDeviceSimulator {

	private final String path;
	private final Device device;
	private Map<String, Set<IInterfaceElement>> allValueHolderElements = new HashMap<>();
	private final Map<IInterfaceElement, Integer> eventMonitoringValues = new HashMap<>();
	private final Map<Resource, ReplayNavigator.DatapointsState> currentStates = new HashMap<>();
	private final Map<String, ResourceSimulator> resourceReplayers = new HashMap<>();

	public DeviceSimulator(final Device device, final String path) {
		this.path = path;
		this.device = device;
	}

	@Override
	public boolean start() {

		// read traces
		final TracesReader tracerReader = new TracesReader(path);

		try {
			final var externalEvents = tracerReader.read();

			for (final var resource : device.getResource()) {
				final var sifbEvents = externalEvents.getOrDefault(resource.getName(), List.of()).stream()
						.map(externalEvent -> Map.entry(externalEvent,
								Utils.getInstanceFB(resource, externalEvent.instanceName())))
						.filter(entry -> Utils.isSIFB(entry.getValue().getType())).map(Map.Entry::getKey).toList();

				resourceReplayers.put(resource.getName(), new ResourceSimulator(resource, sifbEvents));
			}

			for (final var entry : externalEvents.entrySet()) {
				FordiacLogHelper.logInfo("Resource: " + entry.getKey()); //$NON-NLS-1$
				for (final SendOutputEvent event : entry.getValue()) {
					FordiacLogHelper.logInfo("  " + event); //$NON-NLS-1$
				}
			}
		} catch (final CTFException e) {
			FordiacLogHelper.logError("Error reading traces: " + e.getMessage()); //$NON-NLS-1$
			return false;
		}

		// get reference to all value holders
		allValueHolderElements = Utils.collectAllValueHolderElements(device);

		// add monitoring counter for events
		for (final var entry : allValueHolderElements.entrySet()) {
			for (final IInterfaceElement element : entry.getValue()) {
				System.out.println(
						" Element: " + element.getQualifiedName() + " id: " + System.identityHashCode(element));
				if (element instanceof Event) {
					eventMonitoringValues.put(element, 0);
				}
			}
		}

		// set initial state
		for (final var resource : device.getResource()) {
			updateState(resource);
		}

		return true;
	}

	@Override
	public boolean stop() {
		// these aren't the droids you are looking for
		return true;
	}

	@Override
	public Optional<String> replayNextEvent(final Resource resource) {
		final var resourceName = resource.getName();

		final var resourceReplayer = resourceReplayers.get(resourceName);
		if (null == resourceReplayer) {
			return Optional.empty();
		}
		final var event = resourceReplayer.reproduceNextEvent();

		if (event.isPresent()) {
			final var currentCounter = eventMonitoringValues.get(event.get());
			if (null == currentCounter) {
				// should not happen
			} else {
				eventMonitoringValues.put(event.get(),
						Integer.valueOf(eventMonitoringValues.get(event.get()).intValue() + 1));
			}
			for (final var outputEvent : resourceReplayer.getLastOutputEvents()) {
				var val = eventMonitoringValues.get(outputEvent);
				if (null == val) {
					val = Integer.valueOf(0);
					eventMonitoringValues.put(outputEvent, val);
				}
				eventMonitoringValues.put(outputEvent,
						Integer.valueOf(eventMonitoringValues.get(outputEvent).intValue() + 1));
			}
			updateState(resource);
			return Optional.of(event.get().getQualifiedName());
		}
		updateState(resource);
		return Optional.empty();
	}

	@Override
	public ReplayNavigator.DatapointsState getCurrentState(final Resource resource) {
		return currentStates.get(resource);
	}

	private void updateState(final Resource resource) {
		final var interfaceElements = allValueHolderElements.get(resource.getName());
		final var result = new ReplayNavigator.DatapointsState();
		for (final var interfaceElement : interfaceElements) {
			if (interfaceElement instanceof Event) {
				result.put(Utils.getWatchName(interfaceElement),
						Integer.toString(eventMonitoringValues.get(interfaceElement)));
			} else if (interfaceElement instanceof final VarDeclaration varDecl) {
				final var value = varDecl.getValue();
				if (value != null) {
					result.put(Utils.getWatchName(varDecl), varDecl.getValue().getValue());
				}
			}
		}
		currentStates.put(resource, result);
	}

}