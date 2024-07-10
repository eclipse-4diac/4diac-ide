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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.dataexport.CommonElementExporter;
import org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.STFunctionBody;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STImport;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCorePartition;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCorePartitioner;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.services.STFunctionGrammarAccess;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource;
import org.eclipse.xtext.documentation.IEObjectDocumentationProvider;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;

import com.google.inject.Inject;

public class STFunctionPartitioner implements STCorePartitioner {

	protected static final String LOST_AND_FOUND_PATTERN = STFunctionPartition.LOST_AND_FOUND_NAME + "_%s"; //$NON-NLS-1$

	@Inject
	private STFunctionGrammarAccess grammarAccess;

	@Inject
	private IEObjectDocumentationProvider documentationProvider;

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
		final String packageName = PackageNameHelper.getPackageName(fbType);
		if (!packageName.isBlank()) {
			builder.append("PACKAGE "); //$NON-NLS-1$
			builder.append(packageName);
			builder.append(";"); //$NON-NLS-1$
			builder.append(CommonElementExporter.LINE_END);
			builder.append(CommonElementExporter.LINE_END);
		}
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
		return builder.toString();
	}

	protected static void generateFunctionParameters(final String type, final List<INamedElement> parameters,
			final StringBuilder builder) {
		if (parameters.isEmpty()) {
			return;
		}
		builder.append("VAR_"); //$NON-NLS-1$
		builder.append(type);
		builder.append(CommonElementExporter.LINE_END);
		parameters.stream().forEach(param -> {
			builder.append("    "); //$NON-NLS-1$
			builder.append(param.getName());
			builder.append(" : "); //$NON-NLS-1$
			builder.append(STCoreUtil.getFeatureType(param).getName());
			builder.append(";"); //$NON-NLS-1$
			builder.append(CommonElementExporter.LINE_END);
		});
		builder.append("END_VAR"); //$NON-NLS-1$
		builder.append(CommonElementExporter.LINE_END);
	}

	protected static String generateFunctionVariable(final VarDeclaration varDeclaration) {
		final StringBuilder builder = new StringBuilder(varDeclaration.getName());
		builder.append(" : "); //$NON-NLS-1$
		builder.append(varDeclaration.getFullTypeName());
		if (varDeclaration.getValue() != null && varDeclaration.getValue().getValue() != null
				&& !varDeclaration.getValue().getValue().isBlank()) {
			builder.append(" := "); //$NON-NLS-1$
			builder.append(varDeclaration.getValue().getValue());
		}
		builder.append(";"); //$NON-NLS-1$
		return builder.toString();
	}

	@Override
	public Optional<? extends STCorePartition> partition(final XtextResource resource) {
		if (resource.getEntryPoint() != null && resource.getEntryPoint() != grammarAccess.getSTFunctionSourceRule()) {
			return Optional.empty();
		}
		final var source = resource.getContents().get(0);
		if (source instanceof final STFunctionSource functionSource) {
			return partition(functionSource);
		}
		return emergencyPartition(resource); // try to salvage what we can
	}

	@SuppressWarnings("static-method") // overridable
	protected Optional<? extends STCorePartition> emergencyPartition(final XtextResource resource) {
		final String text = getResourceText(resource);
		return Optional.of(new STFunctionPartition(null, ECollections.emptyEList(), text,
				ECollections.newBasicEList(newLostAndFound(text, 0))));
	}

	protected Optional<? extends STCorePartition> partition(final STFunctionSource source) {
		try {
			final var node = NodeModelUtils.findActualNodeFor(source);
			final var text = node != null ? node.getText() : null;
			final var imports = source.getImports().stream().map(this::convertSourceElement).filter(Objects::nonNull)
					.collect(Collectors.<Import, EList<Import>>toCollection(ECollections::newBasicEList));
			final var callables = source.getFunctions().stream().map(this::convertSourceElement)
					.filter(Objects::nonNull).collect(Collectors.toCollection(ECollections::newBasicEList));
			handleLostAndFound(source, callables);
			return Optional.of(new STFunctionPartition(source.getName(), imports, text, callables));
		} catch (final Exception e) {
			return emergencyPartition(source); // try to salvage what we can
		}
	}

	@SuppressWarnings("static-method") // overridable
	protected Optional<? extends STCorePartition> emergencyPartition(final STFunctionSource source) {
		final String text = NodeModelUtils.getNode(source).getRootNode().getText();
		if (text == null) {
			throw new IllegalStateException("Cannot get text from root node"); //$NON-NLS-1$
		}
		return Optional.of(new STFunctionPartition(null, ECollections.emptyEList(), text,
				ECollections.newBasicEList(newLostAndFound(text, 0))));
	}

	protected Import convertSourceElement(final STImport decl) {
		final var result = LibraryElementFactory.eINSTANCE.createImport();
		result.setImportedNamespace(decl.getImportedNamespace());
		return result;
	}

	protected org.eclipse.fordiac.ide.model.libraryElement.STFunction convertSourceElement(final STFunction function) {
		final var node = NodeModelUtils.findActualNodeFor(function);
		if (node != null && function.getName() != null) {
			final var result = LibraryElementFactory.eINSTANCE.createSTFunction();
			result.setName(function.getName());
			final String comment = documentationProvider.getDocumentation(function);
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
			result.setReturnType(function.getReturnType());
			result.setText(node.getText());
			return result;
		}
		return null;
	}

	protected VarDeclaration convertInputParameter(final STVarDeclaration declaration) {
		final var result = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		convertParameter(result, declaration, true);
		return result;
	}

	protected VarDeclaration convertOutputParameter(final STVarDeclaration declaration) {
		final var result = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		convertParameter(result, declaration, false);
		return result;
	}

	protected VarDeclaration convertInOutParameter(final STVarDeclaration declaration) {
		return convertInputParameter(declaration);
	}

	protected void convertParameter(final VarDeclaration result, final STVarDeclaration declaration,
			final boolean input) {
		result.setName(declaration.getName());
		final String comment = documentationProvider.getDocumentation(declaration);
		if (comment != null) {
			result.setComment(comment);
		}
		result.setType((DataType) declaration.getType());
		if (declaration.isArray()) {
			ArraySizeHelper.setArraySize(result, extractArraySize(declaration));
		}
		if (declaration.getDefaultValue() != null) {
			final Value value = LibraryElementFactory.eINSTANCE.createValue();
			value.setValue(extractDefaultValue(declaration));
			result.setValue(value);
		}
		result.setIsInput(input);
	}

	protected static boolean isValidParameter(final STVarDeclaration declaration) {
		return declaration.getName() != null && !declaration.getName().isEmpty() && declaration.getType() != null;
	}

	protected static String extractArraySize(final STVarDeclaration declaration) {
		return NodeModelUtils.findNodesForFeature(declaration, STCorePackage.eINSTANCE.getSTVarDeclaration_Ranges())
				.stream().map(INode::getText).collect(Collectors.joining(",")); //$NON-NLS-1$
	}

	protected static String extractDefaultValue(final STVarDeclaration declaration) {
		return NodeModelUtils
				.findNodesForFeature(declaration, STCorePackage.eINSTANCE.getSTVarDeclaration_DefaultValue()).stream()
				.map(INode::getText).collect(Collectors.joining()).trim();
	}

	protected static void handleDuplicates(final EList<ICallable> result) {
		result.stream().collect(Collectors.groupingBy(ICallable::getName)).forEach((name, duplicates) -> IntStream
				.range(1, duplicates.size()).forEach(index -> duplicates.get(index).setName(name + "_" + index))); //$NON-NLS-1$
	}

	protected static void handleLostAndFound(final STFunctionSource source,
			final EList<org.eclipse.fordiac.ide.model.libraryElement.STFunction> result) {
		final ICompositeNode rootNode = NodeModelUtils.getNode(source).getRootNode();
		var lastOffset = 0;
		for (int index = 0; index < source.getFunctions().size(); index++) {
			final var element = source.getFunctions().get(index);
			final var node = NodeModelUtils.findActualNodeFor(element);
			final int totalOffset = node.getTotalOffset();
			if (totalOffset > lastOffset) {
				handleLostAndFound(rootNode, index, lastOffset, totalOffset, result);
			}
			lastOffset = node.getTotalEndOffset();
		}
		final int totalEndOffset = rootNode.getTotalEndOffset();
		if (totalEndOffset > lastOffset) {
			if (result.isEmpty()) {
				handleLostAndFound(rootNode, result.size(), lastOffset, totalEndOffset, result);
			} else {
				appendText(result.get(result.size() - 1), rootNode.getText().substring(lastOffset, totalEndOffset));
			}
		}
	}

	protected static void handleLostAndFound(final ICompositeNode rootNode, final int index, final int start,
			final int end, final EList<org.eclipse.fordiac.ide.model.libraryElement.STFunction> result) {
		final String text = rootNode.getText().substring(start, end);
		if (!text.trim().isEmpty()) {
			result.add(index, newLostAndFound(text, index));
		}
	}

	protected static void appendText(final org.eclipse.fordiac.ide.model.libraryElement.STFunction function,
			final String text) {
		function.setText(function.getText() + text);
	}

	protected static org.eclipse.fordiac.ide.model.libraryElement.STFunction newLostAndFound(final String text,
			final int index) {
		final var function = LibraryElementFactory.eINSTANCE.createSTFunction();
		function.setName(String.format(LOST_AND_FOUND_PATTERN, Integer.valueOf(index)));
		function.setText(text);
		return function;
	}

	protected static String getResourceText(final XtextResource resource) {
		if (resource.getParseResult() != null && resource.getParseResult().getRootNode() != null) {
			return resource.getParseResult().getRootNode().getText();
		}
		return ""; //$NON-NLS-1$
	}
}
