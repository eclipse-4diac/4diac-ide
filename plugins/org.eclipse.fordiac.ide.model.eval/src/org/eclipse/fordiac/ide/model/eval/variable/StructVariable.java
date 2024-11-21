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

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.eval.value.StructValue;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;
import org.eclipse.fordiac.ide.model.value.TypedValue;

public class StructVariable extends AbstractVariable<StructValue> implements Iterable<Variable<?>> {
	private final StructValue value;

	public StructVariable(final String name, final StructuredType type) {
		super(name, type);
		value = new StructValue(type);
	}

	public StructVariable(final String name, final StructuredType type, final String value) {
		super(name, type);
		this.value = new StructValue((StructValue) ValueOperations.parseValue(value, type, null));
	}

	public StructVariable(final String name, final StructuredType type, final Value value) {
		super(name, type);
		this.value = new StructValue(checkValue(value));
	}

	@Override
	public void setValue(final Value value) {
		checkValue(value).getMembers().forEach((name, variable) -> this.value.get(name).setValue(variable.getValue()));
	}

	protected StructValue checkValue(final Value value) {
		if (!(value instanceof final StructValue structValue) || !getType().isAssignableFrom(structValue.getType())) {
			throw createCastException(value);
		}
		return structValue;
	}

	@Override
	public void setValue(final TypedValue value) {
		if (!getType().isAssignableFrom(value.type())) {
			createCastException(value);
		}
		final Map<?, ?> memberValues = (Map<?, ?>) value.value();
		this.value.getMembers().forEach((name, member) -> {
			final Object memberValue = memberValues.get(name);
			if (memberValue != null) {
				member.setValue(new TypedValue((DataType) member.getType(), memberValue));
			} else {
				member.setValue(ValueOperations.defaultValue(member.getType()));
			}
		});
	}

	@Override
	public StructuredType getType() {
		return (StructuredType) super.getType();
	}

	@Override
	public Iterator<Variable<?>> iterator() {
		return value.getMembers().values().iterator();
	}

	public Map<String, Variable<?>> getMembers() {
		return value.getMembers();
	}

	@Override
	public Stream<Variable<?>> getChildren() {
		return getMembers().values().stream().sorted(Comparator.comparing(Variable::getName));
	}

	@Override
	public StructValue getValue() {
		return value;
	}
}
