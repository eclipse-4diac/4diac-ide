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

public class DefaultEvaluatorDebugger implements EvaluatorDebugger {
	public static final DefaultEvaluatorDebugger INSTANCE = new DefaultEvaluatorDebugger();

	@Override
	public void trap(final Object context, final Evaluator eval) throws InterruptedException {
		if (Thread.interrupted()) {
			throw new InterruptedException();
		}
	}
}
