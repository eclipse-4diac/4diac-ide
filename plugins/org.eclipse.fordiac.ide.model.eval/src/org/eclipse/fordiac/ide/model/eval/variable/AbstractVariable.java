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

import org.eclipse.fordiac.ide.model.data.AnyDateType;
import org.eclipse.fordiac.ide.model.data.AnyDurationType;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

public abstract class AbstractVariable<T extends Value> implements Variable<T> {
	private final String name;
	private final INamedElement type;

	protected AbstractVariable(final String name, final INamedElement type) {
		this.name = name;
		this.type = type;
	}

	@Override
	public String toString() {
		if (type instanceof final DataType dataType && IecTypes.GenericTypes.isAnyType(dataType)
				&& !(getValue().getType() instanceof AnyDurationType || getValue().getType() instanceof AnyDateType)) {
			return getValue().getType().getName() + '#' + getValue();
		}
		return getValue().toString();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public INamedElement getType() {
		return type;
	}

	protected RuntimeException createCastException(final Value value) {
		throw new ClassCastException("Cannot assign value with incompatible type " + value.getType().getName() //$NON-NLS-1$
				+ " as " + type.getName()); //$NON-NLS-1$
	}
}
