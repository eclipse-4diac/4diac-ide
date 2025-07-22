/*******************************************************************************
 * Copyright (c) 2025 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.contracts;

import java.util.Objects;

public class EventOccurrence implements Comparable<EventOccurrence> {

	public enum Type {
		RECORDED, // an actual recored event occurrence
		MISSED_MARKER, // a special occurrence marker used to check for missing events
	}

	public enum State {
		NOT_SET, FULFILLING, ISSUE
	}

	private final String eventName;
	private final double timestampNs;
	private final Type type;
	private State state;
	private final int ruleIndex;

	public EventOccurrence(final String eventName, final double timestampNs) {
		this(eventName, timestampNs, 0);
	}

	public EventOccurrence(final String eventName, final double timestampNs, final int ruleIndex) {
		this(eventName, timestampNs, Type.RECORDED, State.NOT_SET, ruleIndex);
	}

	public EventOccurrence(final String eventName, final double timestampNs, final Type type, final State state,
			final int ruleIndex) {
		this.eventName = eventName;
		this.timestampNs = timestampNs;
		this.type = type;
		this.state = state;
		this.ruleIndex = ruleIndex;
	}

	public String getShortName() {
		final int idx = eventName.lastIndexOf('.');
		if (idx < 0 || idx + 1 > eventName.length()) {
			return ""; //$NON-NLS-1$
		}
		return eventName.substring(idx + 1);
	}

	@Override
	public boolean equals(final Object obj) {
		return obj instanceof final EventOccurrence other && eventName.equals(other.eventName)
				&& timestampNs == other.timestampNs && type == other.type;
	}

	@SuppressWarnings("boxing")
	@Override
	public int hashCode() {
		return Objects.hash(eventName, timestampNs, type);
	}

	@Override
	public int compareTo(final EventOccurrence other) {
		final int comp = Double.compare(timestampNs, other.timestampNs);
		if (comp == 0) {
			// missed markers sort behind recorded ones if time is the same
			// to ensure the checking order is correct for closed intervals
			return type.compareTo(other.type);
		}
		return comp;
	}

	// === getters/setters
	public String eventName() {
		return eventName;
	}

	public double timestampNs() {
		return timestampNs;
	}

	public Type type() {
		return type;
	}

	public State state() {
		return state;
	}

	public void setState(final State state) {
		this.state = state;
	}

	public int ruleIndex() {
		return ruleIndex;
	}
}
