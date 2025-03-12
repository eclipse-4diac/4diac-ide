/*******************************************************************************
 * Copyright (c) 2025 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextalgorithm.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STMethod;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.structuredtextalgorithm.services.STAlgorithmGrammarAccess;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSource;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSourceElement;
import org.eclipse.fordiac.ide.structuredtextcore.resource.STCoreResource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STResource;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCoreParseUtil;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.ParseResult;
import org.eclipse.xtext.resource.IResourceFactory;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.validation.Issue;

public final class STAlgorithmParseUtil {
	private static final URI SYNTHETIC_URI_FBT = URI.createURI("__synthetic.stalg"); //$NON-NLS-1$
	private static final IResourceServiceProvider SERVICE_PROVIDER_FBT = IResourceServiceProvider.Registry.INSTANCE
			.getResourceServiceProvider(SYNTHETIC_URI_FBT);

	private static final URI SYNTHETIC_URI_FCT = URI.createURI("__synthetic.stfunc"); //$NON-NLS-1$
	private static final IResourceServiceProvider SERVICE_PROVIDER_FCT = IResourceServiceProvider.Registry.INSTANCE
			.getResourceServiceProvider(SYNTHETIC_URI_FCT);

	private static final URI SYNTHETIC_URI_GCF = URI.createURI("__synthetic.globalconsts"); //$NON-NLS-1$
	private static final IResourceServiceProvider SERVICE_PROVIDER_GCF = IResourceServiceProvider.Registry.INSTANCE
			.getResourceServiceProvider(SYNTHETIC_URI_GCF);

	// @formatter:off
	private static final Map<String, IResourceFactory> EXTENSION_TO_FACTORY_MAP = Map.of(
			TypeLibraryTags.FB_TYPE_FILE_ENDING,               SERVICE_PROVIDER_FBT.get(IResourceFactory.class),
			TypeLibraryTags.FB_TYPE_FILE_ENDING.toLowerCase(), SERVICE_PROVIDER_FBT.get(IResourceFactory.class),
			TypeLibraryTags.FC_TYPE_FILE_ENDING,               SERVICE_PROVIDER_FCT.get(IResourceFactory.class),
			TypeLibraryTags.FC_TYPE_FILE_ENDING.toLowerCase(), SERVICE_PROVIDER_FCT.get(IResourceFactory.class),
			TypeLibraryTags.GLOBAL_CONST_FILE_ENDING,               SERVICE_PROVIDER_GCF.get(IResourceFactory.class),
			TypeLibraryTags.GLOBAL_CONST_FILE_ENDING.toLowerCase(), SERVICE_PROVIDER_GCF.get(IResourceFactory.class));
	// @formatter:on

	private static final Map<String, Object> DEFAULT_LOAD_OPTIONS = Map.of(//
			ResourceDescriptionsProvider.PERSISTED_DESCRIPTIONS, Boolean.TRUE, //
			STCoreResource.OPTION_PLAIN_ST, Boolean.TRUE //
	);

	public static IParseResult parseAlgorithm(final STAlgorithm algorithm) {
		return switch (algorithm.eContainer()) {
		case final
				BaseFBType baseFBType ->
			createSourceElementParseResult(
					parse(getPartitioner().combine(baseFBType), getGrammarAccess().getSTAlgorithmSourceRule(),
							baseFBType.eResource() != null ? baseFBType.eResource().getURI() : null, null, baseFBType,
							null, null),
					element -> Objects.equals(algorithm.getName(), element.getName()));
		default -> parse(getPartitioner().toSTText(algorithm), getGrammarAccess().getSTAlgorithmSourceRule(),
				algorithm.eResource() != null ? algorithm.eResource().getURI() : null, null,
				EcoreUtil2.getContainerOfType(algorithm, LibraryElement.class), null, null);
		};
	}

	public static IParseResult parseMethod(final STMethod method) {
		return switch (method.eContainer()) {
		case final
				BaseFBType baseFBType ->
			createSourceElementParseResult(
					parse(getPartitioner().combine(baseFBType), getGrammarAccess().getSTAlgorithmSourceRule(),
							baseFBType.eResource() != null ? baseFBType.eResource().getURI() : null, null, baseFBType,
							null, null),
					element -> Objects.equals(method.getName(), element.getName()));
		default -> parse(getPartitioner().toSTText(method), getGrammarAccess().getSTAlgorithmSourceRule(),
				method.eResource() != null ? method.eResource().getURI() : null, null,
				EcoreUtil2.getContainerOfType(method, LibraryElement.class), null, null);
		};
	}

	public static IParseResult parseExpression(final String expression, final INamedElement expectedType,
			final EObject context) {
		return parse(expression, getGrammarAccess().getSTExpressionSourceRule(),
				context.eResource() != null ? context.eResource().getURI() : null, expectedType,
				EcoreUtil2.getContainerOfType(context, LibraryElement.class), null, null);
	}

	public static IParseResult parseInitializerExpression(final String expression, final INamedElement expectedType,
			final EObject context) {
		return parse(expression, getGrammarAccess().getSTInitializerExpressionSourceRule(),
				context.eResource() != null ? context.eResource().getURI() : null, expectedType,
				EcoreUtil2.getContainerOfType(context, LibraryElement.class), null, null);
	}

	public static IParseResult parseTypeDeclaration(final String type, final EObject context) {
		return parse(type, getGrammarAccess().getSTTypeDeclarationRule(),
				context.eResource() != null ? context.eResource().getURI() : null, null,
				EcoreUtil2.getContainerOfType(context, LibraryElement.class), null, null);
	}

	public static IParseResult parse(final String text, final ParserRule entryPoint, final URI uri,
			final INamedElement expectedType, final LibraryElement type,
			final Collection<? extends EObject> additionalContent, final List<Issue> issues) {
		final XtextResourceSet resourceSet = SERVICE_PROVIDER_FBT.get(XtextResourceSet.class);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().putAll(EXTENSION_TO_FACTORY_MAP);
		return STCoreParseUtil.parse(SERVICE_PROVIDER_FBT, resourceSet, text, entryPoint, type, additionalContent,
				issues, Objects.requireNonNullElse(uri, SYNTHETIC_URI_FBT), getLoadOptions(expectedType));
	}

	private static Map<String, Object> getLoadOptions(final INamedElement expectedType) {
		if (expectedType != null) {
			final Map<String, Object> loadOptions = new HashMap<>(DEFAULT_LOAD_OPTIONS);
			loadOptions.put(STResource.OPTION_EXPECTED_TYPE, expectedType);
			return loadOptions;
		}
		return DEFAULT_LOAD_OPTIONS;
	}

	private static IParseResult createSourceElementParseResult(final IParseResult parseResult,
			final Predicate<STAlgorithmSourceElement> filter) {
		return createSubParseResult(parseResult,
				root -> Optional.of(root).filter(STAlgorithmSource.class::isInstance).map(STAlgorithmSource.class::cast)
						.map(STAlgorithmSource::getElements).stream().flatMap(Collection::stream).filter(filter)
						.findFirst().orElse(null));
	}

	private static IParseResult createSubParseResult(final IParseResult parseResult,
			final UnaryOperator<EObject> mapping) {
		if (parseResult != null && parseResult.getRootASTElement() != null) {
			final EObject mapped = mapping.apply(parseResult.getRootASTElement());
			final ICompositeNode mappedNode = NodeModelUtils.getNode(mapped);
			if (mapped != null && mappedNode != null) {
				return new ParseResult(mapped, mappedNode, parseResult.hasSyntaxErrors());
			}
		}
		return parseResult;
	}

	public static STAlgorithmGrammarAccess getGrammarAccess() {
		return SERVICE_PROVIDER_FBT.get(STAlgorithmGrammarAccess.class);
	}

	public static STAlgorithmPartitioner getPartitioner() {
		return SERVICE_PROVIDER_FBT.get(STAlgorithmPartitioner.class);
	}

	private STAlgorithmParseUtil() {
		throw new UnsupportedOperationException();
	}
}
