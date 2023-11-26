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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.eval.value.FBValue;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class FBVariable extends AbstractVariable<FBValue> {
	private static final Pattern MAP_PATTERN = Pattern.compile(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); //$NON-NLS-1$
	private static final Pattern MAP_KV_PATTERN = Pattern.compile(":=(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); //$NON-NLS-1$

	private final Map<String, Variable<?>> members = new HashMap<>();
	private final FBValue value;

	public FBVariable(final String name, final FBType type) {
		this(name, type, Collections.emptySet());
	}

	public FBVariable(final String name, final FBType type, final String value) {
		this(name, type);
		setValue(value);
	}

	public FBVariable(final String name, final FBType type, final Value value) {
		this(name, type);
		setValue(value);
	}

	public FBVariable(final String name, final FBType type, final Iterable<Variable<?>> variables) {
		super(name, type);
		if (variables != null) {
			variables.forEach(variable -> members.put(variable.getName(), variable));
		}
		type.getInterfaceList().getInputVars().forEach(this::initializeMember);
		type.getInterfaceList().getInOutVars().forEach(this::initializeMember);
		type.getInterfaceList().getOutputVars().forEach(this::initializeMember);
		type.getInterfaceList().getSockets().stream().map(AdapterDeclaration::getAdapterFB)
				.forEach(this::initializeMember);
		type.getInterfaceList().getPlugs().stream().map(AdapterDeclaration::getAdapterFB)
				.forEach(this::initializeMember);
		if (type instanceof final BaseFBType baseFBType) {
			baseFBType.getInternalConstVars().forEach(this::initializeMember);
			baseFBType.getInternalVars().forEach(this::initializeMember);
			baseFBType.getInternalFbs().forEach(this::initializeMember);
		}
		if (type instanceof final BasicFBType basicFBType) {
			members.computeIfAbsent(ECStateVariable.NAME, unused -> new ECStateVariable(basicFBType));
		}
		value = new FBValue(type, members);
	}

	protected void initializeMember(final VarDeclaration variable) {
		try {
			members.computeIfAbsent(variable.getName(), unused -> VariableOperations.newVariable(variable));
		} catch (final Exception e) {
			throw new RuntimeException("Error initializing member " + variable.getName() + ": " + e.getMessage(), e); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	protected void initializeMember(final FB fb) {
		try {
			members.computeIfAbsent(fb.getName(), unused -> VariableOperations.newVariable(fb));
		} catch (final Exception e) {
			throw new RuntimeException("Error initializing member " + fb.getName() + ": " + e.getMessage(), e); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	@Override
	public void setValue(final Value value) {
		if (!(value instanceof final FBValue fbValue) || fbValue.getType() != getType()) {
			throw createCastException(value);
		}
		fbValue.getMembers().forEach((name, variable) -> members.get(name).setValue(variable.getValue()));
	}

	@Override
	public void setValue(final String value) {
		final String trimmed = value.trim();
		if (!trimmed.startsWith("(") || !trimmed.endsWith(")")) { //$NON-NLS-1$ //$NON-NLS-2$
			throw createInvalidValueException();
		}
		final String inner = trimmed.substring(1, trimmed.length() - 1);
		for (final String elem : FBVariable.MAP_PATTERN.split(inner)) {
			final String[] split = FBVariable.MAP_KV_PATTERN.split(elem);
			if (split.length != 2) {
				throw createInvalidValueException();
			}
			final Variable<?> variable = members.get(split[0].trim());
			if (variable == null) {
				throw createInvalidValueException();
			}
			variable.setValue(split[1].trim());
		}
	}

	@Override
	public boolean validateValue(final String value) {
		final String trimmed = value.trim();
		if (!trimmed.startsWith("(") || !trimmed.endsWith(")")) { //$NON-NLS-1$ //$NON-NLS-2$
			return false;
		}
		final String inner = trimmed.substring(1, trimmed.length() - 1);
		return Stream.of(FBVariable.MAP_PATTERN.split(inner)).allMatch(elem -> {
			final String[] split = FBVariable.MAP_KV_PATTERN.split(elem);
			if (split.length != 2) {
				return false;
			}
			final Variable<?> variable = this.members.get(split[0].trim());
			return variable != null && variable.validateValue(split[1].trim());
		});
	}

	public Map<String, Variable<?>> getMembers() {
		return members;
	}

	@Override
	public FBValue getValue() {
		return value;
	}

	protected static IllegalArgumentException createInvalidValueException() {
		return new IllegalArgumentException("Not a valid FB value"); //$NON-NLS-1$
	}
}
