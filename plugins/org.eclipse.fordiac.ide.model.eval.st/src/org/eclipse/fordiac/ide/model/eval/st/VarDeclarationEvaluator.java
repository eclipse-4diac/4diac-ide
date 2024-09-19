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

import org.eclipse.fordiac.ide.model.data.AnyStringType;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.Subrange;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.EvaluatorPrepareException;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.eval.variable.VariableEvaluator;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.StructuredTextParseUtil;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpressionSource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTypeDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil;
import org.eclipse.xtext.EcoreUtil2;

public class VarDeclarationEvaluator extends StructuredTextEvaluator implements VariableEvaluator {
	private final VarDeclaration varDeclaration;
	private INamedElement resultType;
	private STTypeDeclaration parseResultType;
	private STInitializerExpressionSource parseResult;

	public VarDeclarationEvaluator(final VarDeclaration varDeclaration, final Variable<?> context,
			final Iterable<Variable<?>> variables, final Evaluator parent) {
		super(varDeclaration.getName(), context, variables, parent);
		this.varDeclaration = varDeclaration;
	}

	@Override
	public void prepare() {
		prepareResultType();
		prepareInitialValue();
	}

	protected void prepareResultType() {
		if (parseResultType == null && varDeclaration.isArray()) {
			final List<String> errors = new ArrayList<>();
			final List<String> warnings = new ArrayList<>();
			final List<String> infos = new ArrayList<>();
			parseResultType = StructuredTextParseUtil.parseType(varDeclaration, errors, warnings, infos);
			errors.forEach(this::error);
			warnings.forEach(this::warn);
			infos.forEach(this::info);
			if (parseResultType == null) {
				throw new EvaluatorPrepareException(errors.stream().collect(Collectors.joining(", ")), this); //$NON-NLS-1$
			}
		}
	}

	protected void prepareInitialValue() {
		if (parseResult == null && VariableOperations.hasDeclaredInitialValue(varDeclaration)) {
			final List<String> errors = new ArrayList<>();
			final List<String> warnings = new ArrayList<>();
			final List<String> infos = new ArrayList<>();
			parseResult = parseInitialValue(errors, warnings, infos);
			errors.forEach(this::error);
			warnings.forEach(this::warn);
			infos.forEach(this::info);
			if (parseResult == null) {
				throw new EvaluatorPrepareException(errors.stream().collect(Collectors.joining(", ")), this); //$NON-NLS-1$
			}
		}
	}

	protected STInitializerExpressionSource parseInitialValue(final List<String> errors, final List<String> warnings,
			final List<String> infos) {
		try {
			evaluateResultType();
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			return null;
		}
		return StructuredTextParseUtil.parse(varDeclaration.getValue().getValue(),
				varDeclaration.eResource() != null ? varDeclaration.eResource().getURI() : null, resultType,
				EcoreUtil2.getContainerOfType(varDeclaration, LibraryElement.class), null, errors, warnings, infos);
	}

	@Override
	public Value evaluate() throws EvaluatorException, InterruptedException {
		return evaluateVariable().getValue();
	}

	@Override
	public Variable<?> evaluateVariable() throws EvaluatorException, InterruptedException {
		prepareInitialValue();
		final Variable<?> result = VariableOperations.newVariable(varDeclaration.getName(), evaluateResultType());
		if (parseResult != null && parseResult.getInitializerExpression() != null) {
			evaluateInitializerExpression(result, trap(parseResult).getInitializerExpression());
		}
		return result;
	}

	@Override
	public boolean validateVariable(final List<String> errors, final List<String> warnings, final List<String> infos)
			throws EvaluatorException, InterruptedException {
		if (VariableOperations.hasDeclaredInitialValue(varDeclaration)) {
			return parseInitialValue(errors, warnings, infos) != null;
		}
		return true;
	}

	@Override
	public INamedElement evaluateResultType() throws EvaluatorException, InterruptedException {
		if (resultType == null) {
			prepareResultType();
			if (parseResultType != null) {
				resultType = evaluateTypeDeclaration(parseResultType);
			} else {
				resultType = varDeclaration.getType();
			}
		}
		return resultType;
	}

	@Override
	public boolean validateResultType(final List<String> errors, final List<String> warnings, final List<String> infos)
			throws EvaluatorException, InterruptedException {
		if (varDeclaration.isArray()) {
			return StructuredTextParseUtil.parseType(varDeclaration, errors, warnings, infos) != null;
		}
		return true;
	}

	protected INamedElement evaluateTypeDeclaration(final STTypeDeclaration declaration)
			throws EvaluatorException, InterruptedException {
		final DataType type = switch (declaration.getType()) {
		case final AnyStringType anyStringType when declaration.getMaxLength() != null -> STCoreUtil.newStringType(
				anyStringType, ValueOperations.asInteger(evaluateExpression(declaration.getMaxLength())));
		case final DataType dataType -> dataType;
		case null, default -> null;
		};
		if (declaration.isArray()) {
			if (declaration.getRanges().isEmpty()) {
				return STCoreUtil.newArrayType(type,
						declaration.getCount().stream().map(unused -> DataFactory.eINSTANCE.createSubrange()).toList());
			}
			final List<Subrange> subranges = new ArrayList<>(declaration.getRanges().size());
			for (final STExpression range : declaration.getRanges()) {
				subranges.add(evaluateSubrange(range));
			}
			return STCoreUtil.newArrayType(type, subranges);
		}
		return type;
	}

	@Override
	public Set<String> getDependencies() {
		return Stream.of(getTypeDependencies(), getInitialValueDependencies()).flatMap(Set::stream)
				.collect(Collectors.toSet());
	}

	protected Set<String> getTypeDependencies() {
		if (varDeclaration.getFBNetworkElement() == null) {
			prepareResultType();
			if (parseResultType != null) {
				return StructuredTextParseUtil.collectUsedTypes(parseResultType);
			}
			return Set.of(PackageNameHelper.getFullTypeName(varDeclaration.getType()));
		}
		return Collections.emptySet();
	}

	protected Set<String> getInitialValueDependencies() {
		prepareInitialValue();
		if (parseResult != null) {
			return StructuredTextParseUtil.collectUsedTypes(parseResult);
		}
		return Collections.emptySet();
	}

	@Override
	public Object getSourceElement() {
		return varDeclaration;
	}
}
