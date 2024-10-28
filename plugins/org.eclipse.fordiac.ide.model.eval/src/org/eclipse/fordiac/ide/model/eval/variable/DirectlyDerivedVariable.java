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

import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.eval.EvaluatorInitializerException;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public class DirectlyDerivedVariable extends AbstractVariable<Value> {

	private final Variable<?> delegate;

	protected DirectlyDerivedVariable(final String name, final DirectlyDerivedType type) {
		super(name, type);
		try {
			delegate = VariableOperations.newVariable(type);
		} catch (final Exception e) {
			throw new EvaluatorInitializerException(type, e);
		}
	}

	protected DirectlyDerivedVariable(final String name, final DirectlyDerivedType type, final String value) {
		this(name, type);
		setValue(value);
	}

	protected DirectlyDerivedVariable(final String name, final DirectlyDerivedType type, final Value value) {
		this(name, type);
		setValue(value);
	}

	@Override
	public Value getValue() {
		return delegate.getValue();
	}

	@Override
	public void setValue(final Value value) throws ClassCastException {
		delegate.setValue(value);
	}

	@Override
	public void setValue(final String value, final TypeLibrary typeLibrary) throws IllegalArgumentException {
		delegate.setValue(value, typeLibrary);
	}

	@Override
	public boolean validateValue(final String value, final TypeLibrary typeLibrary) {
		return delegate.validateValue(value, typeLibrary);
	}
}
