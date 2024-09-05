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
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod;
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.StructuredTextParseUtil;

public class STMethodEvaluator extends STCallableEvaluator {
	private final org.eclipse.fordiac.ide.model.libraryElement.STMethod method;
	private STMethod parseResult;

	public STMethodEvaluator(final org.eclipse.fordiac.ide.model.libraryElement.STMethod method,
			final Variable<?> context, final Iterable<Variable<?>> variables, final Evaluator parent) {
		super(method.getName(), context, variables, parent);
		this.method = method;
	}

	public STMethodEvaluator(final STMethod method, final Variable<?> context, final Iterable<Variable<?>> variables,
			final Evaluator parent) {
		super(method.getName(), context, variables, parent);
		this.method = null;
		parseResult = method;
	}

	@Override
	public void prepare() throws EvaluatorException {
		if (parseResult == null) {
			final List<String> errors = new ArrayList<>();
			final List<String> warnings = new ArrayList<>();
			final List<String> infos = new ArrayList<>();
			if (method != null) {
				parseResult = StructuredTextParseUtil.parse(method, errors, warnings, infos);
			}
			errors.forEach(this::error);
			warnings.forEach(this::warn);
			infos.forEach(this::info);
			if (parseResult == null) {
				throw new EvaluatorPrepareException(errors.stream().collect(Collectors.joining(", ")), this); //$NON-NLS-1$
			}
		}
		prepareCallableVariables(parseResult.getBody().getVarDeclarations(), parseResult.getName(),
				parseResult.getReturnType());
	}

	@Override
	public Value evaluate() throws EvaluatorException, InterruptedException {
		prepare();
		evaluateStructuredTextMethod(parseResult);
		trap(parseResult);
		if (getReturnVariable() != null) {
			return getReturnVariable().getValue();
		}
		return null;
	}

	protected void evaluateStructuredTextMethod(final STMethod method) throws EvaluatorException, InterruptedException {
		evaluateCallableVariables(method.getBody().getVarDeclarations());
		try {
			evaluateStatementList(method.getBody().getStatements());
		} catch (final StructuredTextException e) {
			// return
		}
	}

	@Override
	public STMethod getSourceElement() {
		return parseResult;
	}

	@Override
	public Set<String> getDependencies() {
		prepare();
		if (parseResult != null) {
			return StructuredTextParseUtil.collectUsedTypes(parseResult);
		}
		return Collections.emptySet();
	}
}
