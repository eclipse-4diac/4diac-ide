/**
 * Copyright (c) 2022-2023 Martin Erich Jobst
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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.EvaluatorPrepareException;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod;
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.StructuredTextParseUtil;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpressionSource;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;

@SuppressWarnings("java:S1452")
public class ScopedExpressionEvaluator extends StructuredTextEvaluator {
	private final String expression;
	private STExpressionSource parseResult;

	public ScopedExpressionEvaluator(final String expression, final Variable<?> context,
			final Iterable<Variable<?>> variables, final Evaluator parent) {
		super("anonymous", getContext(context, parent), getVariables(variables, parent), parent); //$NON-NLS-1$
		this.expression = expression;
	}

	private static Variable<?> getContext(final Variable<?> context, final Evaluator parent) {
		if (context != null) {
			return context;
		}
		if (parent != null) {
			return parent.getContext();
		}
		return null;
	}

	private static Iterable<Variable<?>> getVariables(final Iterable<Variable<?>> variables, final Evaluator parent) {
		if (variables != null) {
			return variables;
		}
		if (parent != null) {
			return parent.getVariables().values();
		}
		return null;
	}

	@Override
	public void prepare() {
		if (parseResult == null) {
			final List<String> errors = new ArrayList<>();
			final List<String> warnings = new ArrayList<>();
			final List<String> infos = new ArrayList<>();
			parseResult = StructuredTextParseUtil.parse(expression, null, getType(), getAdditionalScope(), errors,
					warnings, infos);
			errors.forEach(this::error);
			warnings.forEach(this::warn);
			infos.forEach(this::info);
			if (parseResult == null) {
				throw new EvaluatorPrepareException(errors.stream().collect(Collectors.joining(", ")), this); //$NON-NLS-1$
			}
		}
	}

	private LibraryElement getType() {
		if (getContext() != null && getContext().getType() instanceof final LibraryElement libraryElement) {
			return libraryElement;
		}
		return null;
	}

	protected List<? extends EObject> getAdditionalScope() {
		final Evaluator parent = getParent();
		if (parent != null) {
			return switch (parent.getSourceElement()) {
			case final STAlgorithm algorithm -> algorithm.getBody().getVarTempDeclarations();
			case final STMethod method -> method.getBody().getVarDeclarations();
			case final STFunction function -> function.getVarDeclarations();
			case null, default -> Collections.emptyList();
			};
		}
		return Collections.emptyList();
	}

	@Override
	public Value evaluate() throws EvaluatorException, InterruptedException {
		prepare();
		return evaluateExpression(parseResult.getExpression());
	}

	@Override
	public String getSourceElement() {
		return expression;
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
