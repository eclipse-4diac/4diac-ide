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

import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.eval.value.StructValue;
import org.eclipse.fordiac.ide.model.eval.value.Value;

public class StructVariable extends AbstractVariable<StructValue> implements Iterable<Variable<?>> {
	private final Map<String, Variable<?>> members;
	private final StructValue value;

	public StructVariable(final String name, final StructuredType type) {
		super(name, type);
		members = type.getMemberVariables().stream().map(VariableOperations::newVariable)
				.collect(Collectors.toUnmodifiableMap(Variable::getName, Function.identity()));
		value = new StructValue(type, members);
	}

	public StructVariable(final String name, final StructuredType type, final String value) {
		this(name, type);
		setValue(value);
	}

	public StructVariable(final String name, final StructuredType type, final Value value) {
		this(name, type);
		setValue(value);
	}

	@Override
	public void setValue(final Value value) {
		if (!(value instanceof final StructValue structValue) || !getType().isAssignableFrom(structValue.getType())) {
			throw createCastException(value);
		}
		structValue.getMembers().forEach((name, variable) -> members.get(name).setValue(variable.getValue()));
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
	public StructuredType getType() {
		return (StructuredType) super.getType();
	}

	@Override
	public Iterator<Variable<?>> iterator() {
		return members.values().iterator();
	}

	public Map<String, Variable<?>> getMembers() {
		return members;
	}

	@Override
	public StructValue getValue() {
		return value;
	}
}
