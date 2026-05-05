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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.fordiac.ide.model.libraryElement.Event;

/**
 * @brief Handles the counter for events.
 *
 *        The counter of how many times an event has been triggered is not
 *        stored in the model, so watching this information in the application
 *        requires storing the values outside of it. This class is a simple one,
 *        but it encapsulates the needed logic.
 *
 *        This logic is not exclusive to replay debugging but also to regular
 *        monitoring, therefore is separated from the rest in order to make it
 *        easier to migrate when needed.
 */
public class EventCounter {
	private final Map<Event, Integer> eventMonitoringValues = new HashMap<>();

	public EventCounter(final Set<Event> events) {
		events.forEach(event -> eventMonitoringValues.put(event, Integer.valueOf(0)));
	}

	public void incrementEventCount(final Event event) {
		var val = eventMonitoringValues.get(event);
		// in some edge cases (part of subApps) the triggered event is the one from the
		// application and not resource and therefore at this point it does not exist
		// this is something to be fixed in the interpreter and not here.
		// remove this when the interpreter is fixed
		if (null == val) {
			val = Integer.valueOf(0);
			eventMonitoringValues.put(event, val);
		}
		eventMonitoringValues.put(event, Integer.valueOf(eventMonitoringValues.get(event).intValue() + 1));
	}

	public int getEventCount(final Event event) {
		return eventMonitoringValues.get(event).intValue();
	}

	public void setEventCount(final Event event, final int value) {
		eventMonitoringValues.put(event, Integer.valueOf(value));
	}
}
