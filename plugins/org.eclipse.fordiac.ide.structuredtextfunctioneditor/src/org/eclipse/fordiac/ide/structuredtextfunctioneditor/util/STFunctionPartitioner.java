/*******************************************************************************
 * Copyright (c) 2023, 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.dataexport.CommonElementExporter;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.STFunctionBody;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCorePartition;
import org.eclipse.fordiac.ide.structuredtextcore.util.STRecoveringPartitioner;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.services.STFunctionGrammarAccess;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;

import com.google.inject.Inject;

public class STFunctionPartitioner
		extends STRecoveringPartitioner<org.eclipse.fordiac.ide.model.libraryElement.STFunction> {

	@Inject
	private STFunctionGrammarAccess grammarAccess;

	@Override
	public String combine(final LibraryElement libraryElement) {
		if (libraryElement instanceof final FunctionFBType functionFbType) {
			return combine(functionFbType);
		}
		return ""; //$NON-NLS-1$
	}

	protected static String combine(final FunctionFBType fbType) {
		if (fbType.getBody() instanceof final STFunctionBody stBody && stBody.getText() != null) {
			return stBody.getText();
		}
		return generateFunctionText(fbType);
	}

	protected static String generateFunctionText(final FunctionFBType fbType) {
		final StringBuilder builder = new StringBuilder();
		generatePackage(fbType, builder);
		generateImports(fbType, builder);
		builder.append("FUNCTION "); //$NON-NLS-1$
		builder.append(fbType.getName());
		final DataType returnType = fbType.getReturnType();
		if (returnType != null) {
			builder.append(" : "); //$NON-NLS-1$
			builder.append(returnType.getName());
		}
		builder.append(CommonElementExporter.LINE_END);
		generateFunctionParameters("INPUT", fbType.getInputParameters(), builder); //$NON-NLS-1$
		generateFunctionParameters("IN_OUT", fbType.getInOutParameters(), builder); //$NON-NLS-1$
		generateFunctionParameters("OUTPUT", fbType.getOutputParameters(), builder); //$NON-NLS-1$
		builder.append("END_FUNCTION"); //$NON-NLS-1$
		builder.append(CommonElementExporter.LINE_END);
		builder.append(CommonElementExporter.LINE_END);
		return builder.toString();
	}

	protected static void generateFunctionParameters(final String type, final List<? extends INamedElement> parameters,
			final StringBuilder builder) {
		if (parameters.isEmpty()) {
			return;
		}
		builder.append("VAR_"); //$NON-NLS-1$
		builder.append(type);
		builder.append(CommonElementExporter.LINE_END);
		parameters.stream().filter(VarDeclaration.class::isInstance).map(VarDeclaration.class::cast).forEach(param -> {
			builder.append("    "); //$NON-NLS-1$
			generateVariable(param, builder);
			builder.append(CommonElementExporter.LINE_END);
		});
		builder.append("END_VAR"); //$NON-NLS-1$
		builder.append(CommonElementExporter.LINE_END);
	}

	@Override
	public Optional<STCorePartition> partition(final XtextResource resource) {
		if (resource.getEntryPoint() != null && resource.getEntryPoint() != grammarAccess.getSTFunctionSourceRule()) {
			return Optional.empty();
		}
		final var source = resource.getContents().get(0);
		if (source instanceof final STFunctionSource functionSource) {
			return partition(functionSource);
		}
		return emergencyPartition(resource); // try to salvage what we can
	}

	protected Optional<STCorePartition> partition(final STFunctionSource source) {
		try {
			final var node = NodeModelUtils.findActualNodeFor(source);
			final var imports = source.getImports().stream().map(STFunctionPartitioner::convertImport)
					.filter(Objects::nonNull).toList();
			final var callables = source.getFunctions().stream().map(this::convertSourceElement)
					.filter(Objects::nonNull).collect(Collectors.toCollection(ArrayList::new));
			handleLostAndFound(node.getRootNode(), source.getFunctions(), callables);
			return Optional.of(new STFunctionPartition(source.getName(), imports, node.getText(), callables));
		} catch (final Exception e) {
			return emergencyPartition(source); // try to salvage what we can
		}
	}

	protected org.eclipse.fordiac.ide.model.libraryElement.STFunction convertSourceElement(final STFunction function) {
		final var node = NodeModelUtils.findActualNodeFor(function);
		if (node == null || function.getName() == null) {
			return null;
		}
		final var result = LibraryElementFactory.eINSTANCE.createSTFunction();
		result.setName(function.getName());
		final String comment = getDocumentationProvider().getDocumentation(function);
		if (comment != null) {
			result.setComment(comment);
		}
		function.getInputParameters().stream().map(STVarDeclaration.class::cast)
				.filter(STFunctionPartitioner::isValidParameter).map(this::convertInputParameter)
				.forEachOrdered(result.getInputParameters()::add);
		function.getOutputParameters().stream().map(STVarDeclaration.class::cast)
				.filter(STFunctionPartitioner::isValidParameter).map(this::convertOutputParameter)
				.forEachOrdered(result.getOutputParameters()::add);
		function.getInOutParameters().stream().map(STVarDeclaration.class::cast)
				.filter(STFunctionPartitioner::isValidParameter).map(this::convertInOutParameter)
				.forEachOrdered(result.getInOutParameters()::add);
		result.setReturnType(resolveDataType(function.getReturnType(), function, null));
		result.setText(node.getText());
		return result;
	}

	@Override
	protected STCorePartition createEmergencyPartition(final String originalSource) {
		return new STFunctionPartition(null, Collections.emptyList(), originalSource,
				List.of(createLostAndFound(originalSource, 0)));
	}

	@Override
	protected org.eclipse.fordiac.ide.model.libraryElement.STFunction createLostAndFound(final String text,
			final int index) {
		final var function = LibraryElementFactory.eINSTANCE.createSTFunction();
		function.setName(generateLostAndFoundName(index));
		function.setComment(generateLostAndFoundComment(index));
		function.setText(text);
		return function;
	}

	@Override
	protected void appendText(final org.eclipse.fordiac.ide.model.libraryElement.STFunction function,
			final String text) {
		function.setText(function.getText() + text);
	}
}
