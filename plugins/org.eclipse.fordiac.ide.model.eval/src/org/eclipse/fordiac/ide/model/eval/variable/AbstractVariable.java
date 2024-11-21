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
package org.eclipse.fordiac.ide.model.eval.variable;

import org.eclipse.fordiac.ide.model.data.AnyDateType;
import org.eclipse.fordiac.ide.model.data.AnyDurationType;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.value.TypedValue;
import org.eclipse.fordiac.ide.model.value.TypedValueConverter;

public abstract class AbstractVariable<T extends Value> implements Variable<T> {
	private final String name;
	private final INamedElement type;

	protected AbstractVariable(final String name, final INamedElement type) {
		this.name = name;
		this.type = type;
	}

	@Override
	public String toString() {
		return toString(true);
	}

	@Override
	public String toString(final boolean pretty) {
		if (type instanceof final DataType dataType && IecTypes.GenericTypes.isAnyType(dataType)
				&& !(getValue().getType() instanceof AnyDurationType || getValue().getType() instanceof AnyDateType)) {
			return getValue().getType().getName() + '#' + getValue().toString(pretty);
		}
		return getValue().toString(pretty);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public INamedElement getType() {
		return type;
	}

	@Override
	public final void setValue(final String value) throws IllegalArgumentException {
		setValue(value, null);
	}

	@Override
	public void setValue(final String value, final TypeLibrary typeLibrary) {
		if (value == null || value.isEmpty()) {
			setValue(ValueOperations.defaultValue(type));
		} else if (type instanceof final DataType dataType) {
			setValue(new TypedValueConverter(dataType, typeLibrary != null ? typeLibrary.getDataTypeLibrary() : null)
					.toTypedValue(value));
		} else {
			throw new UnsupportedOperationException("The type " + type.getName() + " is not supported"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	@Override
	public void setValue(final TypedValue value) {
		setValue(ValueOperations.wrapValue(value.value(), value.type()));
	}

	@Override
	public final boolean validateValue(final String value) {
		return validateValue(value, null);
	}

	@Override
	public boolean validateValue(final String value, final TypeLibrary typeLibrary) {
		try {
			ValueOperations.parseValue(value, getType(), typeLibrary != null ? typeLibrary.getDataTypeLibrary() : null);
		} catch (final Exception e) {
			return false;
		}
		return true;
	}

	protected RuntimeException createCastException(final Value value) {
		throw new ClassCastException("Cannot assign value with incompatible type " + value.getType().getName() //$NON-NLS-1$
				+ " as " + type.getName()); //$NON-NLS-1$
	}

	protected RuntimeException createCastException(final TypedValue value) {
		throw new ClassCastException("Cannot assign value with incompatible type " + value.type().getName() //$NON-NLS-1$
				+ " as " + type.getName()); //$NON-NLS-1$
	}
}
