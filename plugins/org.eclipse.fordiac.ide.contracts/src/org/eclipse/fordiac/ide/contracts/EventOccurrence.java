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

public record EventOccurrence(String eventName, double timestampNs, Type type) implements Comparable<EventOccurrence> {
	public enum Type {
		RECORDED, // an actual recored event occurrence
		MISSED_MARKER // a special occurrence marker used to check for missing events
	}

	public EventOccurrence(final String eventName, final double timestampNs) {
		this(eventName, timestampNs, Type.RECORDED);
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
}
