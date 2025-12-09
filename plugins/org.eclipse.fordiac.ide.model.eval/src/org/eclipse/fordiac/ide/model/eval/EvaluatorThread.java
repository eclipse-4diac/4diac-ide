/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.eval;

/**
 * A thread for executing an evaluator
 */
public class EvaluatorThread extends Thread {

	private final EvaluatorThreadPoolExecutor executor;

	/**
	 * Create a new evaluator thread
	 *
	 * @param group    The thread group
	 * @param runnable The runnable encapsulating an evaluator
	 * @param name     The name
	 * @param executor The executor responsible for this thread
	 */
	@SuppressWarnings("java:S3014") // use of ThreadGroup only as parameter
	public EvaluatorThread(final ThreadGroup group, final Runnable runnable, final String name,
			final EvaluatorThreadPoolExecutor executor) {
		super(group, runnable, name);
		this.executor = executor;
	}

	/**
	 * Get the executor for this thread
	 *
	 * @return The executor
	 */
	public EvaluatorThreadPoolExecutor getExecutor() {
		return executor;
	}
}
