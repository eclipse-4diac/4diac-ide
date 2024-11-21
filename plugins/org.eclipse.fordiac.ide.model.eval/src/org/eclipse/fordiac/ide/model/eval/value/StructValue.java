/**
 * Copyright (c) 2022, 2024 Martin Erich Jobst
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

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.eval.EvaluatorInitializerException;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

@SuppressWarnings("java:S1452")
public final class StructValue implements AnyDerivedValue, Iterable<Value> {
	private final StructuredType type;
	private final Map<String, Variable<?>> members;

	public StructValue(final StructuredType type) {
		this.type = type;
		members = type.getMemberVariables().stream().map(StructValue::initializeMember)
				.collect(Collectors.toMap(Variable::getName, Function.identity(), (a, b) -> a, LinkedHashMap::new));
	}

	public StructValue(final StructuredType type, final Map<String, ?> values) {
		this.type = type;
		members = type.getMemberVariables().stream().map(member -> initializeMember(member, values))
				.collect(Collectors.toMap(Variable::getName, Function.identity(), (a, b) -> a, LinkedHashMap::new));
	}

	public StructValue(final StructValue value) {
		type = value.getType();
		members = value.getMembers().values().stream().map(VariableOperations::newVariable)
				.collect(Collectors.toMap(Variable::getName, Function.identity(), (a, b) -> a, LinkedHashMap::new));
	}

	protected static Variable<?> initializeMember(final VarDeclaration variable) {
		try {
			return VariableOperations.newVariable(variable);
		} catch (final Exception e) {
			throw new EvaluatorInitializerException(variable, e);
		}
	}

	protected static Variable<?> initializeMember(final VarDeclaration variable, final Object value) {
		try {
			final INamedElement type = VariableOperations.evaluateResultType(variable);
			return VariableOperations.newVariable(variable.getName(), type, ValueOperations.wrapValue(value, type));
		} catch (final Exception e) {
			throw new EvaluatorInitializerException(variable, e);
		}
	}

	protected static Variable<?> initializeMember(final VarDeclaration member, final Map<String, ?> values) {
		final Object value = values.get(member.getName());
		return value != null ? initializeMember(member, value) : initializeMember(member);
	}

	public Variable<?> get(final String key) {
		return members.get(key);
	}

	@Override
	public int hashCode() {
		return members.values().stream().map(Variable::getValue).mapToInt(Objects::hashCode).sum();
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
		final StructValue other = (StructValue) obj;
		return members.values().stream()
				.allMatch(member -> Objects.equals(member.getValue(), other.get(member.getName()).getValue()));
	}

	@Override
	public String toString() {
		return toString(true);
	}

	@Override
	public String toString(final boolean pretty) {
		return members.values().stream()
				.map(member -> member.getName() + (pretty ? " := " : ":=") + member.toString(pretty)) //$NON-NLS-1$ //$NON-NLS-2$
				.collect(Collectors.joining(pretty ? ", " : ",", "(", ")")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}

	@Override
	public Iterator<Value> iterator() {
		return members.values().stream().sorted(Comparator.comparing(Variable::getName)).<Value>map(Variable::getValue)
				.iterator();
	}

	@Override
	public StructuredType getType() {
		return type;
	}

	public Map<String, Variable<?>> getMembers() {
		return members;
	}
}
