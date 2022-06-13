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

import java.util.stream.Collectors;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.fordiac.ide.model.eval.value.ArrayValue;
import org.eclipse.fordiac.ide.model.eval.value.FBValue;
import org.eclipse.fordiac.ide.model.eval.value.StructValue;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;

public class EvaluatorDebugValue extends EvaluatorDebugElement implements IValue {
	private final Value value;

	public EvaluatorDebugValue(final Value value, final IDebugTarget target) {
		super(target);
		this.value = value;
	}

	public Value getInternalValue() {
		return this.value;
	}

	@Override
	public String getReferenceTypeName() throws DebugException {
		return this.value.getType().getName();
	}

	@Override
	public String getValueString() throws DebugException {
		return this.value.toString();
	}

	@Override
	public boolean isAllocated() throws DebugException {
		return true;
	}

	public EvaluatorDebugVariable getVariable(final String name) {
		if (value instanceof StructValue) {
			final var debugger = getDebugTarget().getDebugger();
			final Variable<?> variable = ((StructValue) value).get(name);
			if (variable != null) {
				return debugger.getVariable(variable);
			}
		} else if (value instanceof FBValue) {
			final var debugger = getDebugTarget().getDebugger();
			final Variable<?> variable = ((FBValue) value).get(name);
			if (variable != null) {
				return debugger.getVariable(variable);
			}
		}
		return null;
	}

	@Override
	public IVariable[] getVariables() throws DebugException {
		if (value instanceof ArrayValue) {
			final var debugger = getDebugTarget().getDebugger();
			final var elements = ((ArrayValue) value).getElements().stream().map(debugger::getVariable)
					.collect(Collectors.toList());
			return elements.toArray(new IVariable[elements.size()]);
		} else if (value instanceof StructValue) {
			final var debugger = getDebugTarget().getDebugger();
			final var members = ((StructValue) value).getMembers().values().stream().map(debugger::getVariable)
					.collect(Collectors.toList());
			return members.toArray(new IVariable[members.size()]);
		} else if (value instanceof FBValue) {
			final var debugger = getDebugTarget().getDebugger();
			final var members = ((FBValue) value).getMembers().values().stream().map(debugger::getVariable)
					.collect(Collectors.toList());
			return members.toArray(new IVariable[members.size()]);
		}
		return new IVariable[0];
	}

	@Override
	public boolean hasVariables() throws DebugException {
		return value instanceof ArrayValue || value instanceof StructValue || value instanceof FBValue;
	}

	@Override
	public EvaluatorDebugTarget getDebugTarget() {
		return (EvaluatorDebugTarget) super.getDebugTarget();
	}
}
