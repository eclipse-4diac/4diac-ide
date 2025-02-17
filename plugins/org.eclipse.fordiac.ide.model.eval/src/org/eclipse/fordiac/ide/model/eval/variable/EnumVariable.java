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
package org.eclipse.fordiac.ide.model.eval.variable;

import org.eclipse.fordiac.ide.model.data.EnumeratedType;
import org.eclipse.fordiac.ide.model.data.EnumeratedValue;
import org.eclipse.fordiac.ide.model.eval.value.EnumValue;
import org.eclipse.fordiac.ide.model.eval.value.Value;

public class EnumVariable extends AbstractVariable<EnumValue> {
	private EnumValue value;

	public EnumVariable(final String name, final EnumeratedType type) {
		super(name, type);
		this.value = new EnumValue(type);
	}

	public EnumVariable(final String name, final EnumeratedValue value) {
		super(name, value.getType());
		this.value = new EnumValue(value);
	}

	public EnumVariable(final String name, final EnumeratedType type, final String value) {
		super(name, type);
		setValue(value);
	}

	public EnumVariable(final String name, final EnumeratedType type, final Value value) {
		super(name, type);
		setValue(value);
	}

	@Override
	public void setValue(final Value value) {
		if (!(value instanceof final EnumValue enumValue) || !getType().isAssignableFrom(enumValue.getType())) {
			throw createCastException(value);
		}
		this.value = enumValue;
	}

	@Override
	public EnumeratedType getType() {
		return (EnumeratedType) super.getType();
	}

	@Override
	public EnumValue getValue() {
		return value;
	}
}
