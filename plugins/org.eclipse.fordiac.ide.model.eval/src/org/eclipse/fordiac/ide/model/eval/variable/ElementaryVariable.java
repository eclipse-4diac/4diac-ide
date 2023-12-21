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

import org.eclipse.fordiac.ide.model.data.AnyType;
import org.eclipse.fordiac.ide.model.eval.value.AnyElementaryValue;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;
import org.eclipse.xtext.xbase.lib.Pure;

public final class ElementaryVariable<T extends AnyElementaryValue> extends AbstractVariable<T> {
	private T value;

	public ElementaryVariable(final String name, final AnyType type) {
		this(name, type, ValueOperations.defaultValue(type));
	}

	public ElementaryVariable(final String name, final AnyType type, final String value) {
		super(name, type);
		setValue(value);
	}

	public ElementaryVariable(final String name, final AnyType type, final Value value) {
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
	public void setValue(final String value) {
		setValue(VariableOperations.evaluateValue(getType(), value));
	}

	@Override
	public boolean validateValue(final String value) {
		return VariableOperations.validateValue(getType(), value).isEmpty();
	}

	@Override
	public AnyType getType() {
		return (AnyType) super.getType();
	}

	@Pure
	@Override
	public T getValue() {
		return value;
	}
}
