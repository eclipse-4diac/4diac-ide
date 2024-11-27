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

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.debug.core.model.IVariable;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugVariable;
import org.eclipse.fordiac.ide.debug.IEvaluatorDebugTarget;
import org.eclipse.fordiac.ide.model.eval.value.FBValue;

public final class EvaluatorDebugFBValue extends EvaluatorDebugStructuredValue {

	private final Map<String, EvaluatorDebugVariable> members;

	public EvaluatorDebugFBValue(final FBValue value, final String expression, final IEvaluatorDebugTarget target) {
		super(value, target);
		members = value.getMembers().values().stream()
				.map(member -> target.createVariable(member, createSubExpression(expression, member.getName())))
				.collect(Collectors.toUnmodifiableMap(EvaluatorDebugVariable::getName, Function.identity()));
	}

	public EvaluatorDebugFBValue(final FBValue value, final EvaluatorDebugVariable variable) {
		super(value, variable);
		members = value.getMembers().values().stream()
				.map(member -> variable.createSubVariable(member,
						createSubExpression(variable.getExpression(), member.getName())))
				.collect(Collectors.toUnmodifiableMap(EvaluatorDebugVariable::getName, Function.identity()));
	}

	@Override
	public FBValue getInternalValue() {
		return (FBValue) super.getInternalValue();
	}

	@Override
	public EvaluatorDebugVariable getVariable(final String name) {
		return members.get(name);
	}

	@Override
	public IVariable[] getVariables() {
		return members.values().stream().sorted().toArray(IVariable[]::new);
	}

	@Override
	public boolean hasVariables() {
		return true;
	}
}
