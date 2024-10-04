/**
 * Copyright (c) 2022, 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextalgorithm.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.dataexport.CommonElementExporter;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STMethod;
import org.eclipse.fordiac.ide.structuredtextalgorithm.services.STAlgorithmGrammarAccess;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSource;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSourceElement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCorePartition;
import org.eclipse.fordiac.ide.structuredtextcore.util.STRecoveringPartitioner;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;

import com.google.inject.Inject;

public class STAlgorithmPartitioner extends STRecoveringPartitioner<ICallable> {

	@Inject
	private STAlgorithmGrammarAccess grammarAccess;

	@Override
	public String combine(final LibraryElement libraryElement) {
		if (libraryElement instanceof final BaseFBType baseFBType) {
			return combine(baseFBType);
		}
		return ""; //$NON-NLS-1$
	}

	protected String combine(final BaseFBType baseFBType) {
		return combine(baseFBType.getCallables());
	}

	protected String combine(final List<? extends ICallable> callables) {
		return callables.stream().map(this::toSTText).collect(Collectors.joining());
	}

	protected String toSTText(final ICallable callable) {
		return switch (callable) {
		case final STAlgorithm algorithm -> toSTText(algorithm);
		case final STMethod method -> toSTText(method);
		default -> ""; //$NON-NLS-1$
		};
	}

	protected String toSTText(final STAlgorithm algorithm) {
		final String text = algorithm.getText();
		if (text.contains(grammarAccess.getSTAlgorithmAccess().getALGORITHMKeyword_0().getValue())
				|| text.contains(grammarAccess.getSTAlgorithmAccess().getEND_ALGORITHMKeyword_3().getValue())) {
			return text;
		}
		return generateAlgorithmDefinition(algorithm);
	}

	protected static String generateAlgorithmDefinition(final STAlgorithm algorithm) {
		final StringBuilder builder = new StringBuilder();
		builder.append("ALGORITHM "); //$NON-NLS-1$
		builder.append(algorithm.getName());
		builder.append(CommonElementExporter.LINE_END);
		builder.append(algorithm.getText());
		builder.append(CommonElementExporter.LINE_END);
		builder.append("END_ALGORITHM"); //$NON-NLS-1$
		builder.append(CommonElementExporter.LINE_END);
		builder.append(CommonElementExporter.LINE_END);
		return builder.toString();
	}

	protected static String toSTText(final STMethod method) {
		return method.getText();
	}

	@Override
	public Optional<STCorePartition> partition(final XtextResource resource) {
		if (resource.getEntryPoint() != null && resource.getEntryPoint() != grammarAccess.getSTAlgorithmSourceRule()) {
			return Optional.empty();
		}
		final EObject source = resource.getContents().get(0);
		if (source instanceof final STAlgorithmSource algorithmSource) {
			return partition(algorithmSource);
		}
		return emergencyPartition(resource);
	}

	public Optional<STCorePartition> partition(final STAlgorithmSource source) {
		try {
			final ICompositeNode node = NodeModelUtils.getNode(source);
			final List<ICallable> result = source.getElements().stream().map(this::convertSourceElement)
					.filter(Objects::nonNull).collect(Collectors.toCollection(ArrayList::new));
			handleLostAndFound(node.getRootNode(), source.getElements(), result);
			handleDuplicates(result);
			return Optional.of(new STAlgorithmPartition(null, Collections.emptyList(), node.getText(), result));
		} catch (final Exception e) {
			return emergencyPartition(source);
		}
	}

	protected ICallable convertSourceElement(final STAlgorithmSourceElement element) {
		return switch (element) {
		case final org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm algorithm ->
			convertSourceElement(algorithm);
		case final org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod method ->
			convertSourceElement(method);
		case null, default -> null;
		};
	}

	protected ICallable convertSourceElement(
			final org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm algorithm) {
		final var node = NodeModelUtils.findActualNodeFor(algorithm);
		if (node == null || algorithm.getName() == null) {
			return null;
		}
		final var result = LibraryElementFactory.eINSTANCE.createSTAlgorithm();
		result.setName(algorithm.getName());
		final String comment = getDocumentationProvider().getDocumentation(algorithm);
		if (comment != null) {
			result.setComment(comment);
		}
		result.setText(node.getText());
		return result;
	}

	protected org.eclipse.fordiac.ide.model.libraryElement.STMethod convertSourceElement(
			final org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod method) {
		final var node = NodeModelUtils.findActualNodeFor(method);
		if (node == null || method.getName() == null) {
			return null;
		}
		final var result = LibraryElementFactory.eINSTANCE.createSTMethod();
		result.setName(method.getName());
		final String comment = getDocumentationProvider().getDocumentation(method);
		if (comment != null) {
			result.setComment(comment);
		}
		method.getInputParameters().stream().map(STVarDeclaration.class::cast)
				.filter(STAlgorithmPartitioner::isValidParameter).map(this::convertInputParameter)
				.forEachOrdered(result.getInputParameters()::add);
		method.getOutputParameters().stream().map(STVarDeclaration.class::cast)
				.filter(STAlgorithmPartitioner::isValidParameter).map(this::convertOutputParameter)
				.forEachOrdered(result.getOutputParameters()::add);
		method.getInOutParameters().stream().map(STVarDeclaration.class::cast)
				.filter(STAlgorithmPartitioner::isValidParameter).map(this::convertInOutParameter)
				.forEachOrdered(result.getInOutParameters()::add);
		result.setReturnType(resolveDataType(method.getReturnType(), method, null));
		result.setText(node.getText());
		return result;
	}

	@Override
	protected STCorePartition createEmergencyPartition(final String originalSource) {
		return new STAlgorithmPartition(null, Collections.emptyList(), originalSource,
				List.of(createLostAndFound(originalSource, 0)));
	}

	@Override
	protected STMethod createLostAndFound(final String content, final int index) {
		final STMethod method = LibraryElementFactory.eINSTANCE.createSTMethod();
		method.setName(generateLostAndFoundName(index));
		method.setComment(generateLostAndFoundComment(index));
		method.setText(content);
		return method;
	}

	@Override
	protected void appendText(final ICallable callable, final String text) {
		switch (callable) {
		case final STAlgorithm algorithm:
			algorithm.setText(algorithm.getText() + text);
			break;
		case final STMethod method:
			method.setText(method.getText() + text);
			break;
		default:
			break;
		}
	}
}
