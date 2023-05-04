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
package org.eclipse.fordiac.ide.model.eval.variable

import org.eclipse.fordiac.ide.model.eval.Evaluator
import org.eclipse.fordiac.ide.model.eval.EvaluatorException
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement

interface VariableEvaluator extends Evaluator {
	/**
	 * Evaluate a variable
	 * 
	 * @return The resulting variable
	 * @throws EvaluatorException if an exception occurred during evaluation
	 * @throws InterruptedException if the evaluation was interrupted
	 */
	def Variable<?> evaluateVariable() throws EvaluatorException, InterruptedException

	/**
	 * Evaluate the result type
	 * 
	 * @return The resulting type
	 * @throws EvaluatorException if an exception occurred during evaluation
	 * @throws InterruptedException if the evaluation was interrupted
	 */
	def INamedElement evaluateResultType() throws EvaluatorException, InterruptedException
}
