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

package org.eclipse.fordiac.ide.debug.replaydebugging.replayer.interpreter;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

import org.eclipse.fordiac.ide.debug.replaydebugging.core.DatapointsState;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.api.EventManagerFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.RuntimeFactory;
import org.eclipse.fordiac.ide.fb.interpreter.mm.EventManagerProcessor;
import org.eclipse.fordiac.ide.fb.interpreter.mm.NetworkRuntimeInspector;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;

/**
 * @brief Offers an interface to the execution of an interpreted resource.
 *
 *        It handles an event manger processor to inject and process events, as
 *        well as a ResourceState to set/get the state of the interpreter.
 */
public class ResourceInterpreterExecutor {

	private final NetworkRuntimeInspector networkRuntimeInspector;
	private final EventManagerProcessor eventManagerProcessor;
	private final ResourceState state;
	private Event lastInjectedEvent = null;
	private static final String NAME_SEPARATOR = "."; //$NON-NLS-1$

	public ResourceInterpreterExecutor(final Resource resource) {

		final var deviceResourcePrefix = resource.getDevice().getName() + NAME_SEPARATOR + resource.getName()
				+ NAME_SEPARATOR;

		final FBNetworkRuntime networkRuntime = RuntimeFactory.createRecursiveFrom(resource.getFBNetwork());
		networkRuntimeInspector = new NetworkRuntimeInspector(networkRuntime, deviceResourcePrefix, NAME_SEPARATOR);

		eventManagerProcessor = new EventManagerProcessor(EventManagerFactory.createFrom(List.of()), networkRuntime);

		state = new ResourceState(networkRuntimeInspector.getNetworkRuntimeState());
	}

	/**
	 * @brief Executes the next event in the resource.
	 *
	 *        This method applies the forced values, processes the next event in the
	 *        event manager processor, updates the state of the resource and returns
	 *        the event that was executed.
	 *
	 * @return the event that was executed, or an empty optional if no event was
	 *         executed
	 */
	public Optional<Event> executeNextEvent() {
		state.applyForceValues();
		final var event = networkRuntimeInspector.getRealEvent(eventManagerProcessor.processOne(OptionalLong.empty()));

		if (event.isPresent()) {
			state.eventTriggered(event.get());
			final var lastOutputEvents = eventManagerProcessor.getLastOutputEvents().stream()
					.map(e -> (Event) networkRuntimeInspector.getRuntimeInterfaceElement(e)).toList();
			for (final var outputEvent : lastOutputEvents) {
				state.eventTriggered(outputEvent);
			}
			if (lastInjectedEvent != null) {
				state.eventTriggered((Event) networkRuntimeInspector.getRuntimeInterfaceElement(lastInjectedEvent));
				lastInjectedEvent = null;
			}
		}
		state.applyForceValues();
		state.updateState();
		return event;
	}

	public DatapointsState getCurrentState() {
		return state.getCurrentState();
	}

	public void setCurrentState(final DatapointsState targetState) {
		state.setCurrentState(targetState);
	}

	public void forceValue(final String watchPoint, final String value) {
		state.forceValue(watchPoint, value);
	}

	public void clearForce(final String watchPoint) {
		state.clearForce(watchPoint);
	}

	public int getCurrentEventCounter() {
		return eventManagerProcessor.getEventCounter();
	}

	public void injectEventOutput(final String instanceName, final int eventId, final List<String> outputValues) {

		final var realEvent = networkRuntimeInspector.getRealFB(instanceName).getInterface().getEventOutputs()
				.get(eventId);

		networkRuntimeInspector.applyOutputData(instanceName, outputValues);

		eventManagerProcessor.injectOutputEvent(realEvent);
		lastInjectedEvent = realEvent;
	}

	public void injectEvent(final String name) {
		final var lastPointPosition = name.lastIndexOf(NAME_SEPARATOR);
		final var instanceName = name.substring(0, lastPointPosition);
		final var eventName = name.substring(lastPointPosition + 1);

		final var fb = networkRuntimeInspector.getRealFB(instanceName);

		for (var eventId = 0; eventId < fb.getInterface().getEventOutputs().size(); eventId++) {
			if (fb.getInterface().getEventOutputs().get(eventId).getName().equals(eventName)) {
				injectEventOutput(instanceName, eventId, List.of());
				return;
			}
		}

		for (final var inputEvent : fb.getInterface().getEventInputs()) {
			if (inputEvent.getName().equals(eventName)) {
				eventManagerProcessor.injectInputEvent(inputEvent);
				return;
			}
		}
	}

}
