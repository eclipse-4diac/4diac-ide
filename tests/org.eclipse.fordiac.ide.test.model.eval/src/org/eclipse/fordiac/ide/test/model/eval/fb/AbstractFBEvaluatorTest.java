/**
 * Copyright (c) 2022, 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.test.model.eval.fb;

import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluatorExternalEventQueue;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.test.model.eval.AbstractEvaluatorTest;

public abstract class AbstractFBEvaluatorTest extends AbstractEvaluatorTest {

	public static class TracingFBEvaluatorEventQueue implements FBEvaluatorExternalEventQueue {
		private final Queue<Event> inputEvents;
		private final Queue<Event> outputEvents = new ArrayBlockingQueue<>(1000);

		public TracingFBEvaluatorEventQueue(final Collection<Event> inputEvents) {
			this.inputEvents = new ArrayBlockingQueue<>(inputEvents.size(), false, inputEvents);
		}

		@Override
		public Event receiveInputEvent() throws InterruptedException {
			return inputEvents.poll();
		}

		@Override
		public boolean triggerInputEvent(final Event event) {
			return inputEvents.offer(event);
		}

		@Override
		public boolean sendOutputEvent(final Event event) {
			return outputEvents.offer(event);
		}

		public Queue<Event> getInputEvents() {
			return inputEvents;
		}

		public Queue<Event> getOutputEvents() {
			return outputEvents;
		}
	}
}
