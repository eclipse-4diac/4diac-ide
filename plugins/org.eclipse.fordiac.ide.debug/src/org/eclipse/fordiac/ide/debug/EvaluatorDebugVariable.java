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
import org.eclipse.fordiac.ide.model.eval.variable.Variable;

public class EvaluatorDebugVariable extends EvaluatorDebugElement implements IVariable, Comparable<EvaluatorDebugVariable> {
	private final Variable variable;
	private EvaluatorDebugValue cachedValue;

	public EvaluatorDebugVariable(Variable variable, EvaluatorDebugTarget debugTarget) {
		super(debugTarget);
		this.variable = variable;
		this.cachedValue = new EvaluatorDebugValue(this.variable.getValue(), getDebugTarget());
	}

	@Override
	public void setValue(String expression) throws DebugException {
		this.variable.setValue(expression);
		this.fireChangeEvent(DebugEvent.CONTENT);
	}

	@Override
	public void setValue(IValue value) throws DebugException {
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
	public boolean verifyValue(String expression) throws DebugException {
		return this.variable.validateValue(expression);
	}

	@Override
	public boolean verifyValue(IValue value) throws DebugException {
		if (value instanceof EvaluatorDebugValue) {
			return ((EvaluatorDebugValue) value).getInternalValue().getType()
					.isCompatibleWith(this.variable.getDeclaration().getType());
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
		return this.variable.getDeclaration().getTypeName();
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
	public int compareTo(EvaluatorDebugVariable o) {
		return this.variable.getName().compareTo(o.variable.getName());
	}
}
