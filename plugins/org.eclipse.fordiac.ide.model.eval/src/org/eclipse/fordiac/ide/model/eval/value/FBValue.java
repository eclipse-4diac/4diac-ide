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

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.model.eval.EvaluatorInitializerException;
import org.eclipse.fordiac.ide.model.eval.variable.ECStateVariable;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public final class FBValue implements Value, Iterable<Value> {

	private final FBType type;
	private final Map<String, Variable<?>> members = new HashMap<>();

	public FBValue(final FBType type) {
		this(type, Collections.emptyList());
	}

	public FBValue(final FBType type, final Iterable<Variable<?>> variables) {
		this(type, type.getInterfaceList(), variables);
	}

	public FBValue(final FB fb) {
		this(fb, Collections.emptyList());
	}

	public FBValue(final FB fb, final Iterable<Variable<?>> variables) {
		this(fb.getType(), fb.getInterface(), variables);
	}

	private FBValue(final FBType type, final InterfaceList interfaceList, final Iterable<Variable<?>> variables) {
		this.type = type;
		if (variables != null) {
			variables.forEach(variable -> members.put(variable.getName(), variable));
		}
		interfaceList.getInputVars().forEach(this::initializeMember);
		interfaceList.getInOutVars().forEach(this::initializeMember);
		interfaceList.getOutputVars().forEach(this::initializeMember);
		interfaceList.getSockets().stream().map(AdapterDeclaration::getAdapterFB).forEach(this::initializeMember);
		interfaceList.getPlugs().stream().map(AdapterDeclaration::getAdapterFB).forEach(this::initializeMember);
		if (type instanceof final BaseFBType baseFBType) {
			baseFBType.getInternalConstVars().forEach(this::initializeMember);
			baseFBType.getInternalVars().forEach(this::initializeMember);
			baseFBType.getInternalFbs().forEach(this::initializeMember);
		}
		if (type instanceof final BasicFBType basicFBType) {
			members.computeIfAbsent(ECStateVariable.NAME, unused -> new ECStateVariable(basicFBType));
		}
	}

	public FBValue(final FBType type, final Map<String, ?> values) {
		this(type);
		values.forEach((name, value) -> {
			final Variable<?> member = members.get(name);
			if (member != null) {
				member.setValue(ValueOperations.wrapValue(value, member.getType()));
			}
		});
	}

	protected void initializeMember(final VarDeclaration variable) {
		members.computeIfAbsent(variable.getName(), unused -> {
			try {
				return VariableOperations.newVariable(variable);
			} catch (final Exception e) {
				throw new EvaluatorInitializerException(variable, e);
			}
		});
	}

	protected void initializeMember(final FB fb) {
		members.computeIfAbsent(fb.getName(), unused -> {
			try {
				return VariableOperations.newVariable(fb);
			} catch (final Exception e) {
				throw new EvaluatorInitializerException(fb, e);
			}
		});
	}

	public Variable<?> get(final String key) {
		return this.members.get(key);
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
		final FBValue other = (FBValue) obj;
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
	public FBType getType() {
		return type;
	}

	public Map<String, Variable<?>> getMembers() {
		return members;
	}
}
