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
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.eval.value.FBValue;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;

public class FBVariable extends AbstractVariable<FBValue> {
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
		value = new FBValue(type, variables);
	}

	@Override
	public void setValue(final Value value) {
		if (!(value instanceof final FBValue fbValue) || fbValue.getType() != getType()) {
			throw createCastException(value);
		}
		fbValue.getMembers().forEach((name, variable) -> this.value.get(name).setValue(variable.getValue()));
	}

	public Map<String, Variable<?>> getMembers() {
		return value.getMembers();
	}

	@Override
	public Stream<Variable<?>> getChildren() {
		return getMembers().values().stream().sorted(Comparator.comparing(Variable::getName));
	}

	@Override
	public FBType getType() {
		return (FBType) super.getType();
	}

	@Override
	public FBValue getValue() {
		return value;
	}

	protected static IllegalArgumentException createInvalidValueException() {
		return new IllegalArgumentException("Not a valid FB value"); //$NON-NLS-1$
	}
}
