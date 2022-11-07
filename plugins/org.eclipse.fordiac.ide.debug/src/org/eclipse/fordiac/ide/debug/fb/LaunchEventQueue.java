/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.fordiac.ide.model.libraryElement.Event;

public class LaunchEventQueue extends AbstractQueue<Event> {
	private final Event event;
	private final boolean repeat;
	private boolean full;

	private final ConcurrentMap<Event, AtomicInteger> eventCounts = new ConcurrentHashMap<>();

	public LaunchEventQueue(final Event event, final boolean repeat) {
		this.event = event;
		this.repeat = repeat;
		this.full = true;
	}

	@Override
	public boolean offer(final Event e) {
		incEventCount(e);
		return true; // infinite sink
	}

	@Override
	public Event poll() {
		if (full) {
			full = repeat;
			incEventCount(event);
			return event;
		}
		return null;
	}

	private void incEventCount(final Event ev) {
		final AtomicInteger count = getCount(ev);
		count.incrementAndGet();
	}

	public AtomicInteger getCount(final Event ev) {
		return eventCounts.computeIfAbsent(ev, e -> new AtomicInteger());
	}

	@Override
	public Event peek() {
		return full ? event : null;
	}

	@Override
	public Iterator<Event> iterator() {
		return new LaunchEventIterator();
	}

	@Override
	public int size() {
		return full ? 1 : 0;
	}

	private class LaunchEventIterator implements Iterator<Event> {
		private boolean start;

		@Override
		public boolean hasNext() {
			return start || repeat;
		}

		@Override
		public Event next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			start = false;
			incEventCount(event);
			return event;
		}
	}
}