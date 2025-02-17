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
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.EvaluatorPrepareException;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.eval.variable.VariableEvaluator;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.StructuredTextParseUtil;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpressionSource;
import org.eclipse.xtext.EcoreUtil2;

public class DirectlyDerivedTypeEvaluator extends StructuredTextEvaluator implements VariableEvaluator {
	private final DirectlyDerivedType directlyDerivedType;
	private STInitializerExpressionSource parseResult;

	public DirectlyDerivedTypeEvaluator(final DirectlyDerivedType directlyDerivedType, final Variable<?> context,
			final Iterable<Variable<?>> variables, final Evaluator parent) {
		super(directlyDerivedType.getName(), context, variables, parent);
		this.directlyDerivedType = directlyDerivedType;
	}

	@Override
	public void prepare() {
		if (parseResult == null && directlyDerivedType.getInitialValue() != null
				&& !directlyDerivedType.getInitialValue().isBlank()) {
			final ArrayList<String> errors = new ArrayList<>();
			final ArrayList<String> warnings = new ArrayList<>();
			final ArrayList<String> infos = new ArrayList<>();
			parseResult = parseInitialValue(errors, warnings, infos);
			errors.forEach(error -> error("Parse error: " + error)); //$NON-NLS-1$
			warnings.forEach(warning -> error("Parse warning: " + warning)); //$NON-NLS-1$
			infos.forEach(info -> error("Parse info: " + info)); //$NON-NLS-1$
			if (parseResult == null) {
				throw new EvaluatorPrepareException(errors.stream().collect(Collectors.joining(", ")), this); //$NON-NLS-1$
			}
		}
	}

	protected STInitializerExpressionSource parseInitialValue(final List<String> errors, final List<String> warnings,
			final List<String> infos) {
		return StructuredTextParseUtil.parse(directlyDerivedType.getInitialValue(),
				directlyDerivedType.eResource() != null ? directlyDerivedType.eResource().getURI() : null,
				directlyDerivedType.getBaseType(),
				EcoreUtil2.getContainerOfType(directlyDerivedType, LibraryElement.class), null, errors, warnings,
				infos);
	}

	@Override
	public Value evaluate() throws EvaluatorException, InterruptedException {
		return evaluateVariable().getValue();
	}

	@Override
	public Variable<?> evaluateVariable() throws EvaluatorException, InterruptedException {
		prepare();
		final Variable<?> result = VariableOperations.newVariable(directlyDerivedType.getName(), evaluateResultType());
		if (parseResult != null && parseResult.getInitializerExpression() != null) {
			evaluateInitializerExpression(result, trap(parseResult).getInitializerExpression());
		}
		return result;
	}

	@Override
	public boolean validateVariable(final List<String> errors, final List<String> warnings, final List<String> infos)
			throws EvaluatorException, InterruptedException {
		if (directlyDerivedType.getInitialValue() != null && !directlyDerivedType.getInitialValue().isBlank()) {
			return parseInitialValue(errors, warnings, infos) != null;
		}
		return true;
	}

	@Override
	public INamedElement evaluateResultType() throws EvaluatorException, InterruptedException {
		return directlyDerivedType.getBaseType();
	}

	@Override
	public boolean validateResultType(final List<String> errors, final List<String> warnings, final List<String> infos)
			throws EvaluatorException, InterruptedException {
		return true;
	}

	@Override
	public Set<String> getDependencies() {
		return Stream.concat(getTypeDependencies().stream(), getInitialValueDependencies().stream())
				.collect(Collectors.toSet());
	}

	protected Set<String> getTypeDependencies() {
		return Set.of(PackageNameHelper.getFullTypeName(directlyDerivedType.getBaseType()));
	}

	protected Set<String> getInitialValueDependencies() {
		prepare();
		if (parseResult != null) {
			return StructuredTextParseUtil.collectUsedTypes(parseResult);
		}
		return Collections.emptySet();
	}

	@Override
	public Object getSourceElement() {
		return directlyDerivedType;
	}
}
