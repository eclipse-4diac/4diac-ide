/*******************************************************************************
 * Copyright (c) 2022-2023 Martin Erich Jobst
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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluatorCountingEventQueue;
import org.eclipse.fordiac.ide.model.libraryElement.Event;

public class FBLaunchEventQueue implements FBEvaluatorCountingEventQueue {
	private final Event event;
	private final boolean repeat;
	private final AtomicBoolean full;

	private final ConcurrentMap<Event, AtomicInteger> eventCounts = new ConcurrentHashMap<>();

	public FBLaunchEventQueue(final Event event, final boolean repeat) {
		this.event = event;
		this.repeat = repeat;
		full = new AtomicBoolean(event != null);
	}

	@Override
	public Event receiveInputEvent() throws InterruptedException {
		if (full.getAndSet(repeat)) {
			incrementEventCount(event);
			return event;
		}
		return null;
	}

	@Override
	public boolean sendOutputEvent(final Event event) {
		incrementEventCount(event);
		return true; // infinite sink
	}

	protected void incrementEventCount(final Event ev) {
		final AtomicInteger count = getCount(ev);
		count.incrementAndGet();
	}

	@Override
	public AtomicInteger getCount(final Event ev) {
		return eventCounts.computeIfAbsent(ev, e -> new AtomicInteger());
	}
}