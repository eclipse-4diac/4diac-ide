/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.eval.variable;

import java.util.Objects;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.data.AnyType;
import org.eclipse.fordiac.ide.model.eval.value.AnyValue;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public class GenericVariable extends AbstractVariable<AnyValue> {
	private Variable<AnyValue> variable;

	public GenericVariable(final String name, final AnyType type) {
		this(name, type, ValueOperations.defaultValue(type));
	}

	public GenericVariable(final String name, final AnyType type, final String value) {
		super(name, type);
		setValue(value);
	}

	public GenericVariable(final String name, final AnyType type, final Value value) {
		super(name, type);
		setValue(value);
	}

	@Override
	public void setValue(final String value, final TypeLibrary typeLibrary) {
		try {
			variable.setValue(value, typeLibrary);
		} catch (final Exception e) {
			super.setValue(value, typeLibrary);
		}
	}

	@Override
	public boolean validateValue(final String value, final TypeLibrary typeLibrary) {
		return variable.validateValue(value, typeLibrary) || super.validateValue(value, typeLibrary);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void setValue(final Value value) {
		Objects.requireNonNull(value);
		if (variable != null && variable.getType().equals(value.getType())) {
			variable.setValue(value);
		} else {
			variable = (Variable<AnyValue>) VariableOperations.newVariable(getName(),
					ValueOperations.castValue(value, getType()));
		}
	}

	@Override
	public AnyType getType() {
		return (AnyType) super.getType();
	}

	@Override
	public AnyValue getValue() {
		return variable.getValue();
	}

	@Override
	public Stream<Variable<?>> getChildren() {
		return variable.getChildren();
	}

	public Variable<?> getVariable() {
		return variable;
	}
}
