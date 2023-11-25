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

public interface EvaluatorDebugger {
	/**
	 * Trap hook for debugging evaluators
	 *
	 * @param context The current context
	 * @param eval    The current evaluator
	 * @throws InterruptedException if the current thread was interrupted
	 */
	void trap(final Object context, final Evaluator eval) throws InterruptedException;
}
