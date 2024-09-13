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
package org.eclipse.fordiac.ide.model.eval.st;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.EvaluatorPrepareException;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.STFunctionBody;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInOutDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInputDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.util.STFunctionParseUtil;

public class STFunctionBodyEvaluator extends STCallableEvaluator {
	private final STFunctionBody body;
	private STFunctionSource parseResult;

	public STFunctionBodyEvaluator(final STFunctionBody body, final Variable<?> context,
			final Iterable<Variable<?>> variables, final Evaluator parent) {
		super(parent != null ? parent.getName() + "." + parent.getName() : "body", context, variables, parent); //$NON-NLS-1$ //$NON-NLS-2$
		this.body = body;
	}

	@Override
	public void prepare() throws EvaluatorException {
		if (parseResult == null) {
			final List<String> errors = new ArrayList<>();
			final List<String> warnings = new ArrayList<>();
			final List<String> infos = new ArrayList<>();
			parseResult = STFunctionParseUtil.parse(body, errors, warnings, infos);
			errors.forEach(this::error);
			warnings.forEach(this::warn);
			infos.forEach(this::info);
			if (getSourceElement() == null) {
				throw new EvaluatorPrepareException(errors.stream().collect(Collectors.joining(", ")), this); //$NON-NLS-1$
			}
			prepareCallableVariables(getSourceElement().getVarDeclarations(), RETURN_VARIABLE_NAME,
					getSourceElement().getReturnType());
		}
	}

	@Override
	public Value evaluate() throws EvaluatorException, InterruptedException {
		prepare();
		evaluateStructuredTextFunction(getSourceElement());
		trap(getSourceElement());
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

	protected static boolean isInput(final STVarDeclarationBlock block) {
		return block instanceof STVarInputDeclarationBlock || block instanceof STVarInOutDeclarationBlock;
	}

	@Override
	public STFunction getSourceElement() {
		if (parseResult != null && !parseResult.getFunctions().isEmpty()) {
			return parseResult.getFunctions().getFirst();
		}
		return null;
	}

	@Override
	public Set<String> getDependencies() {
		prepare();
		if (parseResult != null) {
			return STFunctionParseUtil.collectUsedTypes(parseResult);
		}
		return Collections.emptySet();
	}
}
