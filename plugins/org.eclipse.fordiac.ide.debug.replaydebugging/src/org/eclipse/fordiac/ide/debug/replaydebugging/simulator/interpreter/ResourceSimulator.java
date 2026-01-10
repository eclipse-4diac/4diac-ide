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
import java.util.Optional;
import java.util.OptionalLong;

import org.eclipse.fordiac.debug.replaydebugging.trace.SendOutputEvent;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.Utils;
import org.eclipse.fordiac.ide.fb.interpreter.api.EventManagerFactory;
import org.eclipse.fordiac.ide.fb.interpreter.mm.EventManagerProcessor;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;

public class ResourceSimulator {
	private final Resource resource;
	private final List<SendOutputEvent> externalEvents;
	private int externalEventCounter = 0;

	private final EventManagerProcessor eventManagerProcessor;

	public ResourceSimulator(final Resource resource, final List<SendOutputEvent> externalEvents) {
		this.resource = resource;
		this.externalEvents = externalEvents;
		eventManagerProcessor = new EventManagerProcessor(EventManagerFactory.createFrom(List.of()),
				resource.getFBNetwork());
	}

	public List<Event> getLastOutputEvents() {
		return eventManagerProcessor.getLastOutputEvents();
	}

	public Optional<Event> reproduceNextEvent() {

		// check if we reached the end of the list of events
		if (externalEvents.size() <= externalEventCounter) {
			// keep processing internal events
			return eventManagerProcessor.processOne(OptionalLong.empty());
		}

		final var externalEvent = externalEvents.get(externalEventCounter);
		final int eventCounter = externalEvent.eventCounter();

		if (eventManagerProcessor.getEventCounter() < eventCounter) {
			return eventManagerProcessor.processOne(OptionalLong.empty());
		}

		externalEventCounter++;
		final var instanceFB = Utils.getInstanceFB(resource, externalEvent.instanceName());
		if (instanceFB == null) {
			// could not find FB
			return Optional.empty();
		}
		final var event = instanceFB.getInterface().getEventOutputs().get(externalEvent.eventId());

		// set outputs
		final List<String> outputValues = externalEvent.outputs();
		final var fbDataOutput = instanceFB.getInterface().getOutputVars();
		final var dataOutputValues = new HashMap<String, String>();
		for (int i = 0; i < fbDataOutput.size(); i++) {
			dataOutputValues.put(fbDataOutput.get(i).getName(), outputValues.get(i));
		}

		eventManagerProcessor.injectOutputEvent(instanceFB, event, dataOutputValues);
		return eventManagerProcessor.processOne(OptionalLong.empty());
	}

}
