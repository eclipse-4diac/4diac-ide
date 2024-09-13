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

import java.util.Objects;

import org.eclipse.fordiac.ide.model.data.AnyElementaryType;
import org.eclipse.fordiac.ide.model.data.AnyType;
import org.eclipse.fordiac.ide.model.eval.value.AnyElementaryValue;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;

public final class ElementaryVariable<T extends AnyElementaryValue> extends AbstractVariable<T> {
	private T value;

	public ElementaryVariable(final String name, final AnyElementaryType type) {
		this(name, type, ValueOperations.defaultValue(type));
	}

	public ElementaryVariable(final String name, final AnyElementaryType type, final String value) {
		super(name, type);
		setValue(value);
	}

	public ElementaryVariable(final String name, final AnyElementaryType type, final Value value) {
		super(name, type);
		setValue(value);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void setValue(final Value value) {
		Objects.requireNonNull(value);
		this.value = (T) ValueOperations.castValue(value, getType());
	}

	@Override
	public AnyType getType() {
		return (AnyType) super.getType();
	}

	@Override
	public T getValue() {
		return value;
	}
}
