/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.debug.breakpoint;

import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugStackFrame;
import org.eclipse.fordiac.ide.model.eval.Evaluator;

public interface IEvaluatorBreakpoint extends IBreakpoint {

	/**
	 * Determine if the breakpoint matches the frame and context
	 *
	 * @param frame   The current stack frame
	 * @param context The current context
	 * @return true on match, false otherwise
	 */
	boolean matches(final EvaluatorDebugStackFrame frame, final Object context);

	/**
	 * Check if the breakpoint is applicable for the given evaluator
	 *
	 * @param evaluator The evaluator
	 * @return true if applicable, false otherwise
	 */
	boolean isApplicable(Evaluator evaluator);
}
