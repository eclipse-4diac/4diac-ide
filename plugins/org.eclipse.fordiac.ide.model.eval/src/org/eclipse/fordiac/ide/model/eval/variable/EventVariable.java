/**
 * Copyright (c) 2022, 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.eval.variable;

import org.eclipse.fordiac.ide.model.data.EventType;
import org.eclipse.fordiac.ide.model.eval.value.EventValue;
import org.eclipse.fordiac.ide.model.eval.value.Value;

public class EventVariable extends AbstractVariable<EventValue> {
	private EventValue value;

	public EventVariable(final String name, final EventType type) {
		super(name, type);
		value = new EventValue(type);
	}

	public EventVariable(final String name, final EventType type, final long value) {
		super(name, type);
		setValue(value);
	}

	public EventVariable(final String name, final EventType type, final String value) {
		super(name, type);
		setValue(value);
	}

	public EventVariable(final String name, final EventType type, final Value value) {
		super(name, type);
		setValue(value);
	}

	@Override
	public void setValue(final Value value) {
		if (!(value instanceof final EventValue eventValue) || eventValue.getType() != getType()) {
			throw createCastException(value);
		}
		this.value = eventValue;
	}

	@Override
	public void setValue(final String value) {
		setValue(Long.parseLong(value));
	}

	public void setValue(final long value) {
		this.value = new EventValue(getType(), value);
	}

	@Override
	public boolean validateValue(final String value) {
		try {
			Long.parseLong(value);
			return true;
		} catch (final NumberFormatException e) {
			return false;
		}
	}

	@Override
	public EventType getType() {
		return (EventType) super.getType();
	}

	@Override
	public EventValue getValue() {
		return value;
	}
}
