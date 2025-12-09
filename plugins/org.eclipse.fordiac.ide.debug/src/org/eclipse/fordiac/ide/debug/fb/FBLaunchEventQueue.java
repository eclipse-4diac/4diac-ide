/*******************************************************************************
 * Copyright (c) 2022, 2025 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Alois Zoitl  - extracted from FBLaunchConfigurationDelegate and added event
 *                  counts
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.fb;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluatorCountingEventQueue;
import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluatorExternalEventQueue;
import org.eclipse.fordiac.ide.model.libraryElement.Event;

public class FBLaunchEventQueue implements FBEvaluatorCountingEventQueue, FBEvaluatorExternalEventQueue {
	private final AtomicBoolean repeat;
	private final AtomicBoolean blocking;

	private final BlockingQueue<Event> queue = new LinkedBlockingQueue<>();

	private final AtomicLong totalInputEventCount = new AtomicLong();
	private final ConcurrentMap<Event, AtomicInteger> eventCounts = new ConcurrentHashMap<>();

	/**
	 * Create a new FB launch event queue
	 *
	 * @param event      The initial event or {@code null} to create queue in empty
	 *                   state
	 * @param repeat     Whether to repeat the last event (indefinitely or until
	 *                   switched off)
	 * @param blocking   Whether to block when empty
	 * @param evaluator2
	 */
	public FBLaunchEventQueue(final Event event, final boolean repeat, final boolean blocking) {
		this.repeat = new AtomicBoolean(repeat);
		this.blocking = new AtomicBoolean(blocking);
		if (event != null) {
			queue.add(event);
		}
	}

	@Override
	public Event receiveInputEvent() throws InterruptedException {
		final Event result = blocking.get() ? queue.take() : queue.poll();
		if (result != null) {
			incrementEventCount(result);
			if (repeat.get()) {
				queue.add(result);
			}
		}
		return result;
	}

	@Override
	public boolean sendOutputEvent(final Event event) {
		incrementEventCount(event);
		return true; // infinite sink
	}

	@Override
	public boolean triggerInputEvent(final Event event) {
		return queue.offer(event);
	}

	protected void incrementEventCount(final Event ev) {
		final AtomicInteger count = getCount(ev);
		count.incrementAndGet();
		if (ev.isIsInput()) {
			totalInputEventCount.incrementAndGet();
		}
	}

	@Override
	public AtomicInteger getCount(final Event ev) {
		return eventCounts.computeIfAbsent(ev, e -> new AtomicInteger());
	}

	@Override
	public AtomicLong getTotalInputCount() {
		return totalInputEventCount;
	}

	/**
	 * Get whether to repeat the last event
	 *
	 * @return The repeat state
	 */
	public boolean isRepeat() {
		return repeat.get();
	}

	/**
	 * Set whether to repeat the last event
	 *
	 * @param repeat The repeat state
	 */
	public void setRepeat(final boolean repeat) {
		this.repeat.set(repeat);
	}

	/**
	 * Get whether to block if empty
	 *
	 * @return The blocking state
	 */
	public boolean isBlocking() {
		return blocking.get();
	}

	/**
	 * Set whether to block if empty
	 *
	 * @param blocking The blocking state
	 */
	public void setBlocking(final boolean blocking) {
		this.blocking.set(blocking);
	}
}
