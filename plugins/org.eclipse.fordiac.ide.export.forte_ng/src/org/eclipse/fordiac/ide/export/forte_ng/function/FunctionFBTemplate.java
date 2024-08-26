/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng.function;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate;
import org.eclipse.fordiac.ide.export.forte_ng.util.ForteNgExportUtil;
import org.eclipse.fordiac.ide.export.language.ILanguageSupport;
import org.eclipse.fordiac.ide.export.language.ILanguageSupportFactory;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public abstract class FunctionFBTemplate extends ForteFBTemplate<FunctionFBType> {

	private final ILanguageSupport bodyLanguageSupport;

	protected FunctionFBTemplate(final FunctionFBType type, final String name, final Path prefix,
			final String baseClass) {
		super(type, name, prefix, baseClass);
		bodyLanguageSupport = type.getBody() != null
				? ILanguageSupportFactory.createLanguageSupport("forte_ng", type.getBody()) //$NON-NLS-1$
				: null;
	}

	protected CharSequence generateFunctionSignature() {
		final StringBuilder builder = new StringBuilder();
		if (getType().getReturnType() != null) {
			builder.append(ForteNgExportUtil.generateTypeName(getType().getReturnType()));
		} else {
			builder.append("void"); //$NON-NLS-1$
		}
		builder.append(" func_"); //$NON-NLS-1$
		builder.append(getType().getName());
		builder.append('(');
		builder.append(generateFunctionParameters());
		builder.append(')');
		return builder;
	}

	protected CharSequence generateFunctionParameters() {
		return getFunctionParameters().map(FunctionFBTemplate::generateFunctionParameter)
				.collect(Collectors.joining(", ")); //$NON-NLS-1$
	}

	protected static CharSequence generateFunctionParameter(final VarDeclaration param) {
		final boolean reference = param.isInOutVar() || !param.isIsInput();
		final StringBuilder builder = new StringBuilder();
		builder.append(generateParameterTypeName(param, reference));
		builder.append(' ');
		if (reference) {
			builder.append('&');
		}
		builder.append(ForteNgExportUtil.generateName(param));
		return builder;
	}

	protected Stream<VarDeclaration> getFunctionParameters() {
		return Stream
				.of(getType().getInputParameters(), getType().getInOutParameters(), getType().getOutputParameters())
				.flatMap(List::stream).filter(VarDeclaration.class::isInstance).map(VarDeclaration.class::cast);
	}

	protected static CharSequence generateParameterTypeName(final VarDeclaration variable, final boolean output) {
		final INamedElement type = VariableOperations.evaluateResultType(variable);
		return output ? ForteNgExportUtil.generateTypeNameAsParameter(type) : ForteNgExportUtil.generateTypeName(type);
	}

	public ILanguageSupport getBodyLanguageSupport() {
		return bodyLanguageSupport;
	}

	@Override
	public List<String> getErrors() {
		if (bodyLanguageSupport != null) {
			return Stream.concat(super.getErrors().stream(), bodyLanguageSupport.getErrors().stream()).toList();
		}
		return super.getErrors();
	}

	@Override
	public List<String> getWarnings() {
		if (bodyLanguageSupport != null) {
			return Stream.concat(super.getWarnings().stream(), bodyLanguageSupport.getWarnings().stream()).toList();
		}
		return super.getWarnings();
	}

	@Override
	public List<String> getInfos() {
		if (bodyLanguageSupport != null) {
			return Stream.concat(super.getInfos().stream(), bodyLanguageSupport.getInfos().stream()).toList();
		}
		return super.getInfos();
	}

	@Override
	public Set<INamedElement> getDependencies(final Map<?, ?> options) {
		if (bodyLanguageSupport != null) {
			return Stream.concat(super.getDependencies(options).stream(),
					bodyLanguageSupport.getDependencies(options).stream()).collect(Collectors.toSet());
		}
		return super.getDependencies(options);
	}
}
