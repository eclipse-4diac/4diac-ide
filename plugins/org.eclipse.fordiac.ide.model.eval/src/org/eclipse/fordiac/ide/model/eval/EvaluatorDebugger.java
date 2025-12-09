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

/**
 * A debugger for evaluator execution
 */
public interface EvaluatorDebugger {
	/**
	 * Trap hook for debugging evaluators
	 *
	 * @param context The current context
	 * @param eval    The current evaluator
	 * @throws InterruptedException if the current thread was interrupted
	 */
	void trap(final Object context, final Evaluator eval) throws InterruptedException;

	/**
	 * Hook when a new evaluator thread is about to start executing
	 *
	 * @param thread   The thread
	 * @param runnable The executing runnable
	 * @param executor The executor
	 */
	void beforeExecute(Thread thread, Runnable runnable, final EvaluatorThreadPoolExecutor executor);

	/**
	 * Hook when an evaluator thread has finished execution and is about to
	 * terminate
	 *
	 * @param thread    The thread
	 * @param runnable  The executed runnable
	 * @param throwable An uncaught exception or null if finished normally
	 * @param executor  The executor
	 */
	void afterExecute(Thread thread, Runnable runnable, Throwable throwable,
			final EvaluatorThreadPoolExecutor executor);

	/**
	 * Hook when the target has terminated
	 *
	 * @param executor The executor
	 */
	void terminated(final EvaluatorThreadPoolExecutor executor);
}
