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
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorPrepareException;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.StructuredTextParseUtil;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpressionSource;
import org.eclipse.xtext.EcoreUtil2;

public class ECTransitionEvaluator extends StructuredTextEvaluator {

	private final ECTransition transition;
	private STExpressionSource parseResult;

	public ECTransitionEvaluator(final ECTransition transition, final Variable<?> context,
			final Iterable<Variable<?>> variables, final Evaluator parent) {
		super("anonymous", context, variables, parent); //$NON-NLS-1$
		this.transition = transition;
	}

	@Override
	public void prepare() {
		if (parseResult == null) {
			final List<String> errors = new ArrayList<>();
			final List<String> warnings = new ArrayList<>();
			final List<String> infos = new ArrayList<>();
			parseResult = StructuredTextParseUtil.parse(transition.getConditionExpression(),
					IecTypes.ElementaryTypes.BOOL, EcoreUtil2.getContainerOfType(transition, FBType.class), errors,
					warnings, infos);
			errors.forEach(this::error);
			warnings.forEach(this::warn);
			infos.forEach(this::info);
			if (parseResult == null) {
				throw new EvaluatorPrepareException(errors.stream().collect(Collectors.joining(", ")), this); //$NON-NLS-1$
			}
		}
	}

	@Override
	public Value evaluate() throws InterruptedException {
		prepare();
		return evaluateExpression(trap(parseResult.getExpression()));
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
