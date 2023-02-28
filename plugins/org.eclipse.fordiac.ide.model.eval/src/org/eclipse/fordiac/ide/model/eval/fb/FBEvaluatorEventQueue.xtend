/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.eval.fb

import org.eclipse.fordiac.ide.model.libraryElement.Event

interface FBEvaluatorEventQueue {

	/**
	 * Receive (and remove) an input event from the queue. This method may wait until an element becomes available, depending on the implementation.
	 * 
	 * @return The received input event or {@code null} if this queue is empty
	 * @throws InterruptedException if interrupted while waiting for an event
	 */
	def Event receiveInputEvent() throws InterruptedException

	/**
	 * Send an output event to this queue
	 * 
	 * @param event The event to send
	 * @return {@code true} if the event was successfully added to this queue, {@code false} otherwise
	 */
	def boolean sendOutputEvent(Event event)

	/**
	 * Send multiple output events to this queue
	 * 
	 * @param events The events to send
	 * @return {@code true} if the events were successfully added to this queue, {@code false} otherwise
	 */
	def boolean sendOutputEvents(Iterable<Event> events) {
		events.forall[sendOutputEvent]
	}
}
