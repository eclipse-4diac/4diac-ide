/**
 * Copyright (c) 2022 - 2023 Martin Erich Jobst
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

import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.EvaluatorPrepareException;
import org.eclipse.fordiac.ide.model.eval.value.BoolValue;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.eval.variable.VariableEvaluator;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.StructuredTextParseUtil;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpressionSource;
import org.eclipse.xtext.EcoreUtil2;

public class ECTransitionEvaluator extends StructuredTextEvaluator implements VariableEvaluator {

	private final ECTransition transition;
	private STExpressionSource parseResult;

	public ECTransitionEvaluator(final ECTransition transition, final Variable<?> context,
			final Iterable<Variable<?>> variables, final Evaluator parent) {
		super("anonymous", context, variables, parent); //$NON-NLS-1$
		this.transition = transition;
	}

	@Override
	public void prepare() {
		if (parseResult == null && VariableOperations.hasConditionExpression(transition)) {
			final List<String> errors = new ArrayList<>();
			final List<String> warnings = new ArrayList<>();
			final List<String> infos = new ArrayList<>();
			parseResult = parseConditionExpression(errors, warnings, infos);
			errors.forEach(this::error);
			warnings.forEach(this::warn);
			infos.forEach(this::info);
			if (parseResult == null) {
				throw new EvaluatorPrepareException(errors.stream().collect(Collectors.joining(", ")), this); //$NON-NLS-1$
			}
		}
	}

	@Override
	public void cleanup() {
		parseResult = null;
	}

	private STExpressionSource parseConditionExpression(final List<String> errors, final List<String> warnings,
			final List<String> infos) {
		return StructuredTextParseUtil.parse(transition.getConditionExpression(), IecTypes.ElementaryTypes.BOOL,
				EcoreUtil2.getContainerOfType(transition, FBType.class), errors, warnings, infos);
	}

	@Override
	public Value evaluate() throws InterruptedException {
		prepare();
		if (parseResult != null && parseResult.getExpression() != null) {
			return evaluateExpression(trap(parseResult.getExpression()));
		}
		return BoolValue.TRUE;
	}

	@Override
	public Variable<?> evaluateVariable() throws EvaluatorException, InterruptedException {
		prepare();
		final Variable<?> result = VariableOperations.newVariable("", ElementaryTypes.BOOL, BoolValue.TRUE); //$NON-NLS-1$
		if (parseResult != null && parseResult.getExpression() != null) {
			result.setValue(evaluateExpression(trap(parseResult).getExpression()));
		}
		return result;
	}

	@Override
	public Variable<?> evaluateVariable(final Set<Variable<?>> explicitlyInitialized)
			throws EvaluatorException, InterruptedException {
		return evaluateVariable();
	}

	@Override
	public boolean validateVariable(final List<String> errors, final List<String> warnings, final List<String> infos)
			throws EvaluatorException, InterruptedException {
		if (VariableOperations.hasConditionExpression(transition)) {
			return parseConditionExpression(errors, warnings, infos) != null;
		}
		return true;
	}

	@Override
	public LibraryElement evaluateResultType() {
		return ElementaryTypes.BOOL;
	}

	@Override
	public boolean validateResultType(final List<String> errors, final List<String> warnings,
			final List<String> infos) {
		return true;
	}

	@Override
	public ECTransition getSourceElement() {
		return transition;
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
