/**
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
 */
package org.eclipse.fordiac.ide.model.eval.variable;

import java.util.List;

import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

public interface VariableEvaluator extends Evaluator {
	/**
	 * Evaluate a variable
	 *
	 * @return The resulting variable
	 * @throws EvaluatorException   if an exception occurred during evaluation
	 * @throws InterruptedException if the evaluation was interrupted
	 */
	Variable<?> evaluateVariable() throws EvaluatorException, InterruptedException;

	/**
	 * Validate a variable
	 *
	 * @param errors   The errors
	 * @param warnings The warnings
	 * @param infos    The infos
	 * @return true on success, false otherwise
	 * @throws EvaluatorException   if an exception occurred during evaluation
	 * @throws InterruptedException if the evaluation was interrupted
	 */
	boolean validateVariable(List<String> errors, List<String> warnings, List<String> infos)
			throws EvaluatorException, InterruptedException;

	/**
	 * Evaluate the result type
	 *
	 * @return The resulting type
	 * @throws EvaluatorException   if an exception occurred during evaluation
	 * @throws InterruptedException if the evaluation was interrupted
	 */
	INamedElement evaluateResultType() throws EvaluatorException, InterruptedException;

	/**
	 * Validate the result type
	 *
	 * @param errors   The errors
	 * @param warnings The warnings
	 * @param infos    The infos
	 * @return true on success, false otherwise
	 * @throws EvaluatorException   if an exception occurred during evaluation
	 * @throws InterruptedException if the evaluation was interrupted
	 */
	boolean validateResultType(List<String> errors, List<String> warnings, List<String> infos)
			throws EvaluatorException, InterruptedException;
}
