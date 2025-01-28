/**
 * Copyright (c) 2025 Martin Erich Jobst
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

import org.eclipse.fordiac.ide.model.data.EnumeratedType;
import org.eclipse.fordiac.ide.model.data.EnumeratedValue;

public final class EnumValue implements AnyDerivedValue {
	private final EnumeratedValue value;

	public EnumValue(final EnumeratedValue value) {
		this.value = Objects.requireNonNull(value);
	}

	public EnumValue(final EnumeratedType type) {
		this.value = type.getEnumeratedValues().getFirst();
	}

	@Override
	public EnumeratedType getType() {
		return value.getType();
	}

	@Override
	public int hashCode() {
		return value.hashCode();
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
		final EnumValue other = (EnumValue) obj;
		return value == other.value
				|| (getType().isAssignableFrom(other.getType()) && value.getName().equals(other.value.getName()));
	}

	@Override
	public String toString() {
		return getType().getName() + '#' + value.getName();
	}

	public EnumeratedValue getValue() {
		return value;
	}
}
