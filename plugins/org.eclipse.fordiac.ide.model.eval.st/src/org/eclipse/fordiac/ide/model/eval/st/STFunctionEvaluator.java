/**
 * Copyright (c) 2022 Martin Erich Jobst
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

import java.util.Set;

import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.util.STFunctionParseUtil;

public class STFunctionEvaluator extends STCallableEvaluator {
	private final STFunction function;

	public STFunctionEvaluator(final STFunction function, final Variable<?> context,
			final Iterable<Variable<?>> variables, final Evaluator parent) {
		super(function.getName(), null, variables, parent);
		this.function = function;
	}

	@Override
	public void prepare() throws EvaluatorException {
		prepareCallableVariables(function.getVarDeclarations(), function.getName(), function.getReturnType());
	}

	@Override
	public Value evaluate() throws EvaluatorException, InterruptedException {
		prepare();
		evaluateStructuredTextFunction(function);
		trap(function);
		if (getReturnVariable() != null) {
			return getReturnVariable().getValue();
		}
		return null;
	}

	protected void evaluateStructuredTextFunction(final STFunction function)
			throws EvaluatorException, InterruptedException {
		evaluateCallableVariables(function.getVarDeclarations());
		try {
			evaluateStatementList(function.getCode());
		} catch (final StructuredTextException e) {
			// return
		}
	}

	@Override
	public STFunction getSourceElement() {
		return function;
	}

	@Override
	public Set<String> getDependencies() {
		return STFunctionParseUtil.collectUsedTypes(function);
	}
}
