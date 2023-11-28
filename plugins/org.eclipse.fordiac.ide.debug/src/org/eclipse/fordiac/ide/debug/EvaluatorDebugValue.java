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

import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IIndexedValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.fordiac.ide.model.eval.value.ArrayValue;
import org.eclipse.fordiac.ide.model.eval.value.FBValue;
import org.eclipse.fordiac.ide.model.eval.value.StructValue;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;

public class EvaluatorDebugValue extends EvaluatorDebugElement implements IIndexedValue {
	private final Value value;

	public EvaluatorDebugValue(final Value value, final IDebugTarget target) {
		super(target);
		this.value = value;
	}

	public Value getInternalValue() {
		return value;
	}

	@Override
	public String getReferenceTypeName() throws DebugException {
		return value.getType().getName();
	}

	@Override
	public String getValueString() throws DebugException {
		return value.toString();
	}

	@Override
	public boolean isAllocated() throws DebugException {
		return true;
	}

	public EvaluatorDebugVariable getVariable(final String name) {
		if (value instanceof final StructValue structValue) {
			final var debugger = getDebugTarget().getDebugger();
			final Variable<?> variable = structValue.get(name);
			if (variable != null) {
				return debugger.getVariable(variable);
			}
		} else if (value instanceof final FBValue fbValue) {
			final var debugger = getDebugTarget().getDebugger();
			final Variable<?> variable = fbValue.get(name);
			if (variable != null) {
				return debugger.getVariable(variable);
			}
		}
		return null;
	}

	@Override
	public IVariable[] getVariables() throws DebugException {
		final var debugger = getDebugTarget().getDebugger();
		if (value instanceof final ArrayValue arrayValue) {
			return arrayValue.getElements().stream().map(debugger::getVariable).toArray(IVariable[]::new);
		}
		if (value instanceof final StructValue structValue) {
			return structValue.getMembers().values().stream().map(debugger::getVariable).toArray(IVariable[]::new);
		}
		if (value instanceof final FBValue fbValue) {
			return fbValue.getMembers().values().stream().map(debugger::getVariable).toArray(IVariable[]::new);
		}
		return new IVariable[0];
	}

	@Override
	public boolean hasVariables() throws DebugException {
		return value instanceof ArrayValue || value instanceof StructValue || value instanceof FBValue;
	}

	@Override
	public IVariable getVariable(final int offset) throws DebugException {
		if (value instanceof final ArrayValue arrayValue) {
			try {
				return getDebugTarget().getDebugger().getVariable(arrayValue.get(offset));
			} catch (final IndexOutOfBoundsException e) {
				throw new DebugException(Status.error("Cannot get variable at index " + offset, e)); //$NON-NLS-1$
			}
		}
		throw new DebugException(Status.error("Cannot get variable at index of non-array value")); //$NON-NLS-1$
	}

	@Override
	public IVariable[] getVariables(final int offset, final int length) throws DebugException {
		if (value instanceof final ArrayValue arrayValue) {
			try {
				final var debugger = getDebugTarget().getDebugger();
				return arrayValue.subList(offset, offset + length).stream().map(debugger::getVariable)
						.toArray(IVariable[]::new);
			} catch (final IndexOutOfBoundsException e) {
				throw new DebugException(
						Status.error("Cannot get variables from index " + offset + " to " + (offset + length), e)); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		throw new DebugException(Status.error("Cannot get variables in range of non-array value")); //$NON-NLS-1$
	}

	@Override
	public int getSize() throws DebugException {
		if (value instanceof final ArrayValue arrayValue) {
			return arrayValue.getElements().size();
		}
		throw new DebugException(Status.error("Cannot determine size of non-array value")); //$NON-NLS-1$
	}

	@Override
	public int getInitialOffset() {
		if (value instanceof final ArrayValue arrayValue) {
			return arrayValue.getType().getSubranges().get(0).getLowerLimit();
		}
		return 0;
	}

	@Override
	public EvaluatorDebugTarget getDebugTarget() {
		return (EvaluatorDebugTarget) super.getDebugTarget();
	}
}
