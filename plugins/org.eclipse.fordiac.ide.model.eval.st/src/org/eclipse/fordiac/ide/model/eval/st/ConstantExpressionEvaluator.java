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
package org.eclipse.fordiac.ide.model.eval.st;

import java.util.Collections;
import java.util.Set;

import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;

@SuppressWarnings("java:S1452")
public final class ConstantExpressionEvaluator extends StructuredTextEvaluator {
	private static final ConstantExpressionEvaluator INSTANCE = new ConstantExpressionEvaluator();

	private ConstantExpressionEvaluator() {
		super("anonymous", null, Collections.emptyList(), null); //$NON-NLS-1$
	}

	public static Value evaluate(final STExpression expression) throws EvaluatorException, InterruptedException {
		return INSTANCE.evaluateExpression(expression);
	}

	public static Variable<?> evaluate(final Variable<?> variable, final STInitializerExpression expression)
			throws EvaluatorException, InterruptedException {
		return INSTANCE.evaluateInitializerExpression(variable, expression);
	}

	public static INamedElement evaluateResultType(final STVarDeclaration varDeclaration)
			throws EvaluatorException, InterruptedException {
		return INSTANCE.evaluateType(varDeclaration);
	}

	@Override
	public void prepare() {
		// nothing to do
	}

	@Override
	public Value evaluate() throws EvaluatorException, InterruptedException {
		return null;
	}

	@Override
	public Object getSourceElement() {
		return null;
	}

	@Override
	public Set<String> getDependencies() {
		return Collections.emptySet();
	}
}
