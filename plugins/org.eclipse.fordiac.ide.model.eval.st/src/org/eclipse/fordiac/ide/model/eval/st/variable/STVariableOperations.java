/**
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
 */
package org.eclipse.fordiac.ide.model.eval.st.variable;

import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.st.ConstantExpressionEvaluator;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;

@SuppressWarnings("java:S1452")
public final class STVariableOperations {

	public static Variable<?> newVariable(final STVarDeclaration varDeclaration) throws EvaluatorException {
		try {
			return ConstantExpressionEvaluator.evaluate(
					VariableOperations.newVariable(varDeclaration.getName(), evaluateResultType(varDeclaration)),
					varDeclaration.getDefaultValue());
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			return null;
		}
	}

	public static Variable<?> newVariable(final STVarDeclaration varDeclaration, final Value value)
			throws EvaluatorException {
		return VariableOperations.newVariable(varDeclaration.getName(), evaluateResultType(varDeclaration), value);
	}

	public static INamedElement evaluateResultType(final STVarDeclaration varDeclaration) throws EvaluatorException {
		try {
			return ConstantExpressionEvaluator.evaluateResultType(varDeclaration);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			return null;
		}
	}

	private STVariableOperations() {
		throw new UnsupportedOperationException();
	}
}
