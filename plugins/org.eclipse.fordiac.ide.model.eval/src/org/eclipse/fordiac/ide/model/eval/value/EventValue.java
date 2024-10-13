/**
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.eval.value;

import java.util.Objects;

import org.eclipse.fordiac.ide.model.data.EventType;

public final class EventValue implements Value {
	private final EventType type;
	private final long count;

	public EventValue(final EventType type) {
		this(type, 0);
	}

	public EventValue(final EventType type, final long count) {
		this.type = type;
		this.count = count;
	}

	@Override
	public EventType getType() {
		return type;
	}

	public long getCount() {
		return count;
	}

	@Override
	public int hashCode() {
		return Long.hashCode(count);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final EventValue other = (EventValue) obj;
		return Objects.equals(type, other.type) && count == other.count;
	}

	@Override
	public String toString() {
		return Long.toString(count);
	}
}
