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

package org.eclipse.fordiac.ide.debug.replaydebugging.simulator.interpreter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalLong;

import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.Utils;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.api.EventManagerFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.RuntimeFactory;
import org.eclipse.fordiac.ide.fb.interpreter.mm.EventManagerProcessor;
import org.eclipse.fordiac.ide.fb.interpreter.mm.NetworkRuntimeInspector;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

/**
 * @brief Offers an interface to the execution of a event manager, allowing
 *        execution of a single event and injection of events.
 *
 *        The state of the network (data values and events) is internally
 *        handled and offered to the outside.
 */
public class ResourceExecutor {

	private final NetworkRuntimeInspector networkRuntimeInspector;
	private final EventManagerProcessor eventManagerProcessor;
	private final ResourceState state;

	public ResourceExecutor(final Resource resource) {

		final FBNetworkRuntime networkRuntime = RuntimeFactory.createFrom(resource.getFBNetwork());
		networkRuntimeInspector = new NetworkRuntimeInspector(networkRuntime, Utils.getDeviceResourcePrefix(resource));

		eventManagerProcessor = new EventManagerProcessor(EventManagerFactory.createFrom(List.of()), networkRuntime);

		state = new ResourceState(networkRuntimeInspector.getAllValueHolderElements(),
				Utils.getDeviceResourcePrefix(resource));
	}

	public Optional<Event> executeNextEvent() {
		final var event = networkRuntimeInspector.getRealEvent(eventManagerProcessor.processOne(OptionalLong.empty()));

		if (event.isPresent()) {
			state.eventTriggered(event.get());
			final var lastOutputEvents = eventManagerProcessor.getLastOutputEvents().stream()
					.map(e -> (Event) networkRuntimeInspector.getRuntimeInterfaceElement(e)).toList();
			for (final var outputEvent : lastOutputEvents) {
				state.eventTriggered(outputEvent);
			}
		}
		state.updateState();
		return event;
	}

	public ReplayNavigator.DatapointsState getCurrentState() {
		return state.getCurrentState();
	}

	public int getCurrentEventCounter() {
		return eventManagerProcessor.getEventCounter();
	}

	public void injectEvent(final String instanceName, final int eventId, final List<String> outputValues) {
		final var fb = networkRuntimeInspector.getRealFB(instanceName);
		final var event = fb.getInterface().getEventOutputs().get(eventId);

		// set outputs
		final var fbDataOutput = fb.getInterface().getOutputVars();
		final var dataOutputValues = new HashMap<String, String>();
		for (int i = 0; i < fbDataOutput.size(); i++) {
			dataOutputValues.put(fbDataOutput.get(i).getName(), outputValues.get(i));
		}

		final String toLog = "\nEvent injected " + instanceName + "." + event.getName();
		FordiacLogHelper.logInfo(toLog);

		eventManagerProcessor.injectOutputEvent(fb, event, dataOutputValues);
	}

	public void injectEvent(final String name) {
		final var lastPointPosition = name.lastIndexOf('.');
		final var instanceName = name.substring(0, lastPointPosition);
		final var eventName = name.substring(lastPointPosition + 1);

		final var fb = networkRuntimeInspector.getRealFB(instanceName);
		final var eventOutputs = fb.getInterface().getEventOutputs();

		Event event = null;
		for (int i = 0; i < eventOutputs.size(); i++) {
			if (eventOutputs.get(i).getName().equals(eventName)) {
				event = eventOutputs.get(i);
			}
		}
		if (event == null) {
			return;
		}

		eventManagerProcessor.injectOutputEvent(fb, event, Map.of());
	}

}
