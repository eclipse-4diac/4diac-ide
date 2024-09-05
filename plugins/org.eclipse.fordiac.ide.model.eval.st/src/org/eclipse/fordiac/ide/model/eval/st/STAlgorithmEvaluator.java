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
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm;
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.StructuredTextParseUtil;

public class STAlgorithmEvaluator extends STCallableEvaluator {
	private final org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm algorithm;
	private STAlgorithm parseResult;

	public STAlgorithmEvaluator(final org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm algorithm,
			final Variable<?> context, final Iterable<Variable<?>> variables, final Evaluator parent) {
		super(algorithm.getName(), context, variables, parent);
		this.algorithm = algorithm;
	}

	@Override
	public void prepare() {
		if (parseResult == null) {
			final List<String> errors = new ArrayList<>();
			final List<String> warnings = new ArrayList<>();
			final List<String> infos = new ArrayList<>();
			parseResult = StructuredTextParseUtil.parse(algorithm, errors, warnings, infos);
			errors.forEach(this::error);
			warnings.forEach(this::warn);
			infos.forEach(this::info);
			if (parseResult == null) {
				throw new EvaluatorPrepareException(errors.stream().collect(Collectors.joining(", ")), this); //$NON-NLS-1$
			}
		}
	}

	@Override
	public Value evaluate() throws EvaluatorException, InterruptedException {
		prepare();
		evaluateStructuredTextAlgorithm(parseResult);
		trap(parseResult);
		return null;
	}

	protected void evaluateStructuredTextAlgorithm(final STAlgorithm algorithm)
			throws EvaluatorException, InterruptedException {
		evaluateCallableVariables(algorithm.getBody().getVarTempDeclarations());
		try {
			evaluateStatementList(algorithm.getBody().getStatements());
		} catch (final StructuredTextException e) {
			// return
		}
	}

	@Override
	public STAlgorithm getSourceElement() {
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
