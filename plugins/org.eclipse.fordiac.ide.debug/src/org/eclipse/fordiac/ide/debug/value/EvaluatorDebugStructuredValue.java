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

import org.eclipse.fordiac.ide.debug.IEvaluatorDebugTarget;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugVariable;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.value.Value;

public abstract class EvaluatorDebugStructuredValue extends EvaluatorDebugValue {
	private final EvaluatorDebugVariable variable;

	protected EvaluatorDebugStructuredValue(final Value value, final IEvaluatorDebugTarget target) {
		super(value, target);
		this.variable = null;
	}

	protected EvaluatorDebugStructuredValue(final Value value, final EvaluatorDebugVariable variable) {
		super(value, variable.getDebugTarget());
		this.variable = variable;
	}

	public EvaluatorDebugVariable getVariable() {
		return variable;
	}

	protected static String createSubExpression(final String expression, final String memberName) {
		return switch (expression) {
		case null -> null;
		case Evaluator.CONTEXT_NAME -> memberName;
		default -> expression + "." + memberName; //$NON-NLS-1$
		};
	}

	protected static String createSubExpression(final String expression, final int index) {
		return switch (expression) {
		case null, Evaluator.CONTEXT_NAME -> null;
		default -> expression + "[" + index + "]"; //$NON-NLS-1$ //$NON-NLS-2$
		};
	}
}
