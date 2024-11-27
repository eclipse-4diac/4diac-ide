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

import org.eclipse.debug.core.model.IVariable;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugVariable;
import org.eclipse.fordiac.ide.debug.IEvaluatorDebugTarget;
import org.eclipse.fordiac.ide.model.eval.value.Value;

public final class EvaluatorDebugElementaryValue extends EvaluatorDebugValue {

	public EvaluatorDebugElementaryValue(final Value value, final String expression,
			final IEvaluatorDebugTarget target) {
		super(value, target);
	}

	public EvaluatorDebugElementaryValue(final Value value, final EvaluatorDebugVariable variable) {
		super(value, variable.getDebugTarget());
	}

	@Override
	public EvaluatorDebugVariable getVariable(final String name) {
		return null;
	}

	@Override
	public IVariable[] getVariables() {
		return new IVariable[0];
	}

	@Override
	public boolean hasVariables() {
		return false;
	}
}
