/**
 * Copyright (c) 2022, 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.model.eval;

import java.util.Collection;

import org.eclipse.fordiac.ide.model.eval.variable.Variable;

/**
 * A monitor for evaluator execution
 */
public interface EvaluatorMonitor {
	/**
	 * An info message occurred
	 *
	 * @param message The message
	 */
	void info(final String message);

	/**
	 * A warning message occurred
	 *
	 * @param message The message
	 */
	void warn(final String message);

	/**
	 * An error message occurred
	 *
	 * @param message The message
	 */
	void error(final String message);

	/**
	 * An error message occurred caused by an exception
	 *
	 * @param message The message
	 * @param t       The throwable
	 */
	void error(final String message, final Throwable t);

	/**
	 * Variables have been updated by an evaluator
	 *
	 * @param variables The variables
	 * @param evaluator The evaluator
	 */
	void update(final Collection<? extends Variable<?>> variables, final Evaluator evaluator);

	/**
	 * Execution in an evaluator thread pool executor has terminated
	 *
	 * @param executor The executor
	 */
	void terminated(EvaluatorThreadPoolExecutor executor);

	/**
	 * An empty monitor implementation
	 */
	class NullEvaluatorMonitor implements EvaluatorMonitor {

		@Override
		public void info(final String message) {
			// empty default
		}

		@Override
		public void warn(final String message) {
			// empty default
		}

		@Override
		public void error(final String message) {
			// empty default
		}

		@Override
		public void error(final String message, final Throwable t) {
			// empty default
		}

		@Override
		public void update(final Collection<? extends Variable<?>> variables, final Evaluator evaluator) {
			// empty default
		}

		@Override
		public void terminated(final EvaluatorThreadPoolExecutor executor) {
			// empty default
		}
	}
}
