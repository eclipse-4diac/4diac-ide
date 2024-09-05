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
import org.eclipse.debug.core.model.IDebugElement;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IWatchExpressionDelegate;
import org.eclipse.debug.core.model.IWatchExpressionListener;
import org.eclipse.debug.core.model.IWatchExpressionResult;
import org.eclipse.fordiac.ide.debug.value.EvaluatorDebugValue;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class EvaluatorWatchExpressionDelegate implements IWatchExpressionDelegate {

	@Override
	public void evaluateExpression(final String expression, final IDebugElement context,
			final IWatchExpressionListener listener) {
		if (context instanceof final EvaluatorDebugStackFrame edsf) {
			final Evaluator evaluator = edsf.getEvaluator();
			final Evaluator expressionEvaluator = EvaluatorFactory.createEvaluator(expression, String.class, null, null,
					evaluator);
			WatchExpressionResult watchResult;
			try {
				final Value result = expressionEvaluator.evaluate();
				watchResult = new WatchExpressionResult(expression,
						EvaluatorDebugValue.forValue(result, expression, edsf.getDebugTarget()));
			} catch (final EvaluatorException e) {
				watchResult = new WatchExpressionResult(expression,
						new DebugException(Status.error(e.getMessage(), e)));
			} catch (final InterruptedException e) {
				watchResult = new WatchExpressionResult(expression,
						new DebugException(Status.error(e.getMessage(), e)));
				Thread.currentThread().interrupt();
			}
			try {
				listener.watchEvaluationFinished(watchResult);
			} catch (final Exception e) {
				FordiacLogHelper.logWarning("Exception in watch listener: " + e.getMessage(), e); //$NON-NLS-1$
			}
		}
	}

	public static class WatchExpressionResult implements IWatchExpressionResult {
		private final String expression;
		private final DebugException exception;
		private final IValue value;

		public WatchExpressionResult(final String expression, final IValue value) {
			this.expression = expression;
			this.exception = null;
			this.value = value;
		}

		public WatchExpressionResult(final String expression, final DebugException exception) {
			this.expression = expression;
			this.exception = exception;
			this.value = null;
		}

		@Override
		public IValue getValue() {
			return value;
		}

		@Override
		public boolean hasErrors() {
			return exception != null;
		}

		@Override
		public String[] getErrorMessages() {
			if (hasErrors()) {
				return new String[] { exception.getMessage() };
			}
			return new String[0];
		}

		@Override
		public String getExpressionText() {
			return expression;
		}

		@Override
		public DebugException getException() {
			return exception;
		}
	}
}
