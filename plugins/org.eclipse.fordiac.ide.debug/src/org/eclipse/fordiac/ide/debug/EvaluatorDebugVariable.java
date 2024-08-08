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
package org.eclipse.fordiac.ide.debug;

import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.fordiac.ide.debug.value.EvaluatorDebugValue;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

public class EvaluatorDebugVariable extends EvaluatorDebugElement
		implements IVariable, Comparable<EvaluatorDebugVariable> {
	private final Variable<?> variable;
	private final EvaluatorDebugVariable parent;
	private final String expression;
	private EvaluatorDebugValue cachedValue;
	private long updateCount;

	public EvaluatorDebugVariable(final Variable<?> variable, final String expression,
			final EvaluatorDebugTarget debugTarget) {
		this(variable, expression, null, debugTarget);
	}

	public EvaluatorDebugVariable(final Variable<?> variable, final String expression,
			final EvaluatorDebugVariable parent) {
		this(variable, expression, parent, parent.getDebugTarget());
	}

	private EvaluatorDebugVariable(final Variable<?> variable, final String expression,
			final EvaluatorDebugVariable parent, final EvaluatorDebugTarget debugTarget) {
		super(debugTarget);
		this.variable = variable;
		this.parent = parent;
		this.expression = expression;
		cachedValue = EvaluatorDebugValue.forValue(variable.getValue(), this);
		updateCount = debugTarget.getVariableUpdateCount();
	}

	@Override
	public void setValue(final String expression) throws DebugException {
		try {
			variable.setValue(expression);
		} catch (final Exception e) {
			throw new DebugException(Status.error(e.getMessage(), e));
		}
		fireChangeEvent(DebugEvent.CONTENT);
	}

	@Override
	public void setValue(final IValue value) throws DebugException {
		if (value instanceof final EvaluatorDebugValue evaluatorValue) {
			variable.setValue(evaluatorValue.getInternalValue());
			cachedValue = evaluatorValue;
			fireChangeEvent(DebugEvent.CONTENT);
		} else {
			this.setValue(value.getValueString());
		}
	}

	@Override
	public boolean supportsValueModification() {
		return true;
	}

	@Override
	public boolean verifyValue(final String expression) {
		return variable.validateValue(expression);
	}

	@Override
	public boolean verifyValue(final IValue value) throws DebugException {
		if (value instanceof final EvaluatorDebugValue evaluatorValue) {
			final INamedElement variableType = variable.getType();
			final INamedElement valueType = evaluatorValue.getInternalValue().getType();
			if (variableType instanceof final DataType variableDataType
					&& valueType instanceof final DataType valueDataType) {
				return variableDataType.isAssignableFrom(valueDataType);
			}
			return variableType == valueType;
		}
		return this.verifyValue(value.getValueString());
	}

	@Override
	public EvaluatorDebugValue getValue() {
		final Value value = variable.getValue();
		if (value != cachedValue.getInternalValue()) {
			final EvaluatorDebugTarget debugTarget = getDebugTarget();
			if (!value.equals(cachedValue.getInternalValue())) {
				updateCount = debugTarget.getVariableUpdateCount();
			}
			cachedValue = EvaluatorDebugValue.forValue(value, this);
		}
		return cachedValue;
	}

	@Override
	public String getName() {
		return variable.getName();
	}

	public String getExpression() {
		return expression;
	}

	@Override
	public String getReferenceTypeName() {
		return variable.getType().getName();
	}

	@Override
	public boolean hasValueChanged() {
		return updateCount == getDebugTarget().getVariableUpdateCount();
	}

	@Override
	public EvaluatorDebugTarget getDebugTarget() {
		return (EvaluatorDebugTarget) super.getDebugTarget();
	}

	public final EvaluatorDebugVariable getParent() {
		return parent;
	}

	@Override
	public int compareTo(final EvaluatorDebugVariable o) {
		return variable.getName().compareTo(o.variable.getName());
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() == obj.getClass()) {
			return variable.equals(((EvaluatorDebugVariable) obj).variable);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return variable.hashCode();
	}
}
