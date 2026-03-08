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

import org.eclipse.fordiac.debug.replaydebugging.trace.SendOutputEvent;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.DatapointsState;
import org.eclipse.fordiac.ide.debug.replaydebugging.replayer.IResourceReplayer;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;

/**
 * @brief Implementation of the replay algorithm for a resource
 *
 *        It leverages the ResourceInterpreterExecutor for injecting events,
 *        processing them and forcing and clearing values.
 */
public class ResourceReplayer implements IResourceReplayer {

	private final List<SendOutputEvent> externalEvents;
	private int externalEventCounter = 0;
	private final ResourceInterpreterExecutor resourceInterpreterExecutor;

	public ResourceReplayer(final Resource resource, final List<SendOutputEvent> externalEvents) {
		this.externalEvents = externalEvents;
		this.resourceInterpreterExecutor = new ResourceInterpreterExecutor(resource);
	}

	public ResourceInterpreterExecutor getresourceInterpreterExecutor() {
		return resourceInterpreterExecutor;
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
	public DatapointsState getCurrentState() {
		return resourceInterpreterExecutor.getCurrentState();
	}

	private Optional<Event> replayNextEventInternal() {

		// check if we reached the end of the list of events
		if (externalEvents.size() <= externalEventCounter) {
			// keep processing internal events
			return resourceInterpreterExecutor.executeNextEvent();
		}

		final var externalEvent = externalEvents.get(externalEventCounter);
		final int eventCounter = externalEvent.eventCounter();

		if (resourceInterpreterExecutor.getCurrentEventCounter() < eventCounter) {
			return resourceInterpreterExecutor.executeNextEvent();
		}

		externalEventCounter++;
		resourceInterpreterExecutor.injectEventOutput(externalEvent.instanceName(), externalEvent.eventId(),
				externalEvent.outputs());
		return resourceInterpreterExecutor.executeNextEvent();

	}

	@Override
	public void injectEvent(final String name) {
		resourceInterpreterExecutor.injectEvent(name);
	}

	@Override
	public void setCurrentState(final DatapointsState targetState) {
		resourceInterpreterExecutor.setCurrentState(targetState);
	}

	@Override
	public void forceValue(final String name, final String value) {
		resourceInterpreterExecutor.forceValue(name, value);
	}

	@Override
	public void clearForce(final String name) {
		resourceInterpreterExecutor.clearForce(name);
	}

}
