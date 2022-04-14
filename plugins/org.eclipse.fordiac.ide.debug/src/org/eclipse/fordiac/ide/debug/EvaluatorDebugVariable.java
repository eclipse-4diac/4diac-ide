/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

public class EvaluatorDebugVariable extends EvaluatorDebugElement
implements IVariable, Comparable<EvaluatorDebugVariable> {
	private final Variable variable;
	private EvaluatorDebugValue cachedValue;

	public EvaluatorDebugVariable(final Variable variable, final EvaluatorDebugTarget debugTarget) {
		super(debugTarget);
		this.variable = variable;
		this.cachedValue = new EvaluatorDebugValue(this.variable.getValue(), getDebugTarget());
	}

	@Override
	public void setValue(final String expression) throws DebugException {
		this.variable.setValue(expression);
		this.fireChangeEvent(DebugEvent.CONTENT);
	}

	@Override
	public void setValue(final IValue value) throws DebugException {
		if (value instanceof EvaluatorDebugValue) {
			final EvaluatorDebugValue evaluatorValue = (EvaluatorDebugValue) value;
			this.variable.setValue(evaluatorValue.getInternalValue());
			this.cachedValue = evaluatorValue;
			this.fireChangeEvent(DebugEvent.CONTENT);
		} else {
			this.setValue(value.getValueString());
		}
	}

	@Override
	public boolean supportsValueModification() {
		return true;
	}

	@Override
	public boolean verifyValue(final String expression) throws DebugException {
		return this.variable.validateValue(expression);
	}

	@Override
	public boolean verifyValue(final IValue value) throws DebugException {
		if (value instanceof EvaluatorDebugValue) {
			final INamedElement variableType = ((EvaluatorDebugValue) value).getInternalValue().getType();
			final INamedElement valueType = this.variable.getType();
			if (variableType instanceof DataType && valueType instanceof DataType) {
				return ((DataType) variableType).isCompatibleWith((DataType) valueType);
			}
			return variableType == valueType;
		}
		return this.verifyValue(value.getValueString());
	}

	@Override
	public IValue getValue() throws DebugException {
		if (hasValueChanged()) {
			this.cachedValue = new EvaluatorDebugValue(this.variable.getValue(), getDebugTarget());
		}
		return this.cachedValue;
	}

	@Override
	public String getName() throws DebugException {
		return this.variable.getName();
	}

	@Override
	public String getReferenceTypeName() throws DebugException {
		return this.variable.getType().getName();
	}

	@Override
	public boolean hasValueChanged() throws DebugException {
		return !this.variable.getValue().equals(this.cachedValue.getInternalValue());
	}

	@Override
	public EvaluatorDebugTarget getDebugTarget() {
		return (EvaluatorDebugTarget) super.getDebugTarget();
	}

	@Override
	public int compareTo(final EvaluatorDebugVariable o) {
		if (Evaluator.CONTEXT_NAME.equals(this.variable.getName())) { // sort THIS always at top
			return Evaluator.CONTEXT_NAME.equals(o.variable.getName()) ? 0 : -1;
		}
		return this.variable.getName().compareTo(o.variable.getName());
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
			return this.variable.getName().equals(((EvaluatorDebugVariable) obj).variable.getName());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.variable.getName().hashCode();
	}
}
