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

import java.util.List;
import java.util.stream.IntStream;

import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IIndexedValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugVariable;
import org.eclipse.fordiac.ide.debug.IEvaluatorDebugTarget;
import org.eclipse.fordiac.ide.model.eval.value.ArrayValue;

public final class EvaluatorDebugArrayValue extends EvaluatorDebugStructuredValue implements IIndexedValue {

	private final List<EvaluatorDebugVariable> elements;

	public EvaluatorDebugArrayValue(final ArrayValue value, final String expression,
			final IEvaluatorDebugTarget target) {
		super(value, target);
		elements = IntStream.rangeClosed(value.getStart(), value.getEnd())
				.mapToObj(index -> target.createVariable(value.get(index), createSubExpression(expression, index)))
				.toList();
	}

	public EvaluatorDebugArrayValue(final ArrayValue value, final EvaluatorDebugVariable variable) {
		super(value, variable);
		elements = IntStream
				.rangeClosed(value.getStart(), value.getEnd()).mapToObj(index -> variable
						.createSubVariable(value.get(index), createSubExpression(variable.getExpression(), index)))
				.toList();
	}

	@Override
	public ArrayValue getInternalValue() {
		return (ArrayValue) super.getInternalValue();
	}

	@Override
	public EvaluatorDebugVariable getVariable(final String name) {
		return null;
	}

	@Override
	public IVariable[] getVariables() {
		return elements.toArray(IVariable[]::new);
	}

	@Override
	public boolean hasVariables() {
		return true;
	}

	@Override
	public IVariable getVariable(final int offset) throws DebugException {
		try {
			return elements.get(offset - getInitialOffset());
		} catch (final IndexOutOfBoundsException e) {
			throw new DebugException(Status.error("Cannot get variable at index " + offset, e)); //$NON-NLS-1$
		}
	}

	@Override
	public IVariable[] getVariables(final int offset, final int length) throws DebugException {
		try {
			return elements.subList(offset - getInitialOffset(), offset + length - getInitialOffset())
					.toArray(IVariable[]::new);
		} catch (final IndexOutOfBoundsException e) {
			throw new DebugException(
					Status.error("Cannot get variables from index " + offset + " to " + (offset + length), e)); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	@Override
	public int getSize() throws DebugException {
		return getInternalValue().getElements().size();
	}

	@Override
	public int getInitialOffset() {
		return getInternalValue().getStart();
	}
}
