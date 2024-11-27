/*******************************************************************************
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.value;

import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugElement;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugVariable;
import org.eclipse.fordiac.ide.debug.IEvaluatorDebugTarget;
import org.eclipse.fordiac.ide.model.eval.value.ArrayValue;
import org.eclipse.fordiac.ide.model.eval.value.FBValue;
import org.eclipse.fordiac.ide.model.eval.value.StructValue;
import org.eclipse.fordiac.ide.model.eval.value.Value;

public abstract class EvaluatorDebugValue extends EvaluatorDebugElement implements IValue {
	private final Value value;

	protected EvaluatorDebugValue(final Value value, final IEvaluatorDebugTarget target) {
		super(target);
		this.value = value;
	}

	public static EvaluatorDebugValue forValue(final Value value, final String expression,
			final IEvaluatorDebugTarget target) {
		return switch (value) {
		case final ArrayValue arrayValue -> new EvaluatorDebugArrayValue(arrayValue, expression, target);
		case final FBValue arrayValue -> new EvaluatorDebugFBValue(arrayValue, expression, target);
		case final StructValue arrayValue -> new EvaluatorDebugStructValue(arrayValue, expression, target);
		default -> new EvaluatorDebugElementaryValue(value, expression, target);
		};
	}

	public static EvaluatorDebugValue forValue(final Value value, final EvaluatorDebugVariable variable) {
		return switch (value) {
		case final ArrayValue arrayValue -> new EvaluatorDebugArrayValue(arrayValue, variable);
		case final FBValue arrayValue -> new EvaluatorDebugFBValue(arrayValue, variable);
		case final StructValue arrayValue -> new EvaluatorDebugStructValue(arrayValue, variable);
		default -> new EvaluatorDebugElementaryValue(value, variable);
		};
	}

	public Value getInternalValue() {
		return value;
	}

	@Override
	public final String getReferenceTypeName() {
		return value.getType().getName();
	}

	@Override
	public final String getValueString() {
		return value.toString();
	}

	@Override
	public final boolean isAllocated() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract IVariable[] getVariables();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract boolean hasVariables();

	public abstract EvaluatorDebugVariable getVariable(final String name);

	@Override
	public final IEvaluatorDebugTarget getDebugTarget() {
		return (IEvaluatorDebugTarget) super.getDebugTarget();
	}
}
