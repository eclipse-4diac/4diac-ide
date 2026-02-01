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

import java.util.List;
import java.util.Optional;

import org.eclipse.fordiac.debug.replaydebugging.trace.SendOutputEvent;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator;
import org.eclipse.fordiac.ide.debug.replaydebugging.simulator.IResourceSimulator;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;

/**
 * @brief Implementation of the replay algorithm for a resource
 *
 *        It leverages the EventManagementProccessor for injecting events and
 *        processing them. A NetworkRuntimeInspector is used to get the the
 *        fb/interface instances of the network of the resource itself or the
 *        ones used by the runtimes.
 */
public class ResourceSimulator implements IResourceSimulator {

	private final List<SendOutputEvent> externalEvents;
	private int externalEventCounter = 0;
	private final ResourceExecutor resourceExecutor;

	public ResourceSimulator(final Resource resource, final List<SendOutputEvent> externalEvents) {
		this.externalEvents = externalEvents;
		this.resourceExecutor = new ResourceExecutor(resource);
	}

	@Override
	public Optional<String> replayNextEvent() {
		final var event = replayNextEventInternal();
		if (!event.isPresent()) {
			return Optional.empty();
		}
		return Optional.of(event.get().getQualifiedName());

	}

	@Override
	public ReplayNavigator.DatapointsState getCurrentState() {
		return resourceExecutor.getCurrentState();
	}

	private Optional<Event> replayNextEventInternal() {

		// check if we reached the end of the list of events
		if (externalEvents.size() <= externalEventCounter) {
			// keep processing internal events
			return resourceExecutor.executeNextEvent();
		}

		final var externalEvent = externalEvents.get(externalEventCounter);
		final int eventCounter = externalEvent.eventCounter();

		if (resourceExecutor.getEventCounter() < eventCounter) {
			return resourceExecutor.executeNextEvent();
		}

		externalEventCounter++;
		resourceExecutor.injectEvent(externalEvent.instanceName(), externalEvent.eventId(), externalEvent.outputs());
		return resourceExecutor.executeNextEvent();

	}

}
