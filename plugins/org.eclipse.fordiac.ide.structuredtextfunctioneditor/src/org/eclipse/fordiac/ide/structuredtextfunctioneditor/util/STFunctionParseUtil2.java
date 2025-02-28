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
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.STFunctionBody;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.structuredtextcore.resource.STCoreResource;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCoreParseUtil;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.services.STFunctionGrammarAccess;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.IResourceFactory;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.validation.Issue;

public final class STFunctionParseUtil2 {

	private static final URI SYNTHETIC_URI_FCT = URI.createURI("__synthetic.stfunc"); //$NON-NLS-1$
	private static final IResourceServiceProvider SERVICE_PROVIDER_FCT = IResourceServiceProvider.Registry.INSTANCE
			.getResourceServiceProvider(SYNTHETIC_URI_FCT);

	private static final URI SYNTHETIC_URI_GCF = URI.createURI("__synthetic.globalconsts"); //$NON-NLS-1$
	private static final IResourceServiceProvider SERVICE_PROVIDER_GCF = IResourceServiceProvider.Registry.INSTANCE
			.getResourceServiceProvider(SYNTHETIC_URI_GCF);

	// @formatter:off
	private static final Map<String, IResourceFactory> EXTENSION_TO_FACTORY_MAP = Map.of(
			TypeLibraryTags.FC_TYPE_FILE_ENDING,               SERVICE_PROVIDER_FCT.get(IResourceFactory.class),
			TypeLibraryTags.FC_TYPE_FILE_ENDING.toLowerCase(), SERVICE_PROVIDER_FCT.get(IResourceFactory.class),
			TypeLibraryTags.GLOBAL_CONST_FILE_ENDING,               SERVICE_PROVIDER_GCF.get(IResourceFactory.class),
			TypeLibraryTags.GLOBAL_CONST_FILE_ENDING.toLowerCase(), SERVICE_PROVIDER_GCF.get(IResourceFactory.class));
	// @formatter:on

	private static final Map<String, Object> DEFAULT_LOAD_OPTIONS = Map.of(//
			ResourceDescriptionsProvider.PERSISTED_DESCRIPTIONS, Boolean.TRUE, //
			STCoreResource.OPTION_PLAIN_ST, Boolean.TRUE //
	);

	public static IParseResult parseFunctionBody(final STFunctionBody body) {
		return parse(body.getText(), getGrammarAccess().getSTFunctionSourceRule(),
				body.eResource() != null ? body.eResource().getURI() : null,
				EcoreUtil2.getContainerOfType(body, LibraryElement.class), null, null);
	}

	public static IParseResult parse(final String text, final ParserRule entryPoint, final URI uri,
			final LibraryElement type, final Collection<? extends EObject> additionalContent,
			final List<Issue> issues) {
		final XtextResourceSet resourceSet = SERVICE_PROVIDER_FCT.get(XtextResourceSet.class);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().putAll(EXTENSION_TO_FACTORY_MAP);
		return STCoreParseUtil.parse(SERVICE_PROVIDER_FCT, resourceSet, text, entryPoint, type, additionalContent,
				issues, Objects.requireNonNullElse(uri, SYNTHETIC_URI_FCT), DEFAULT_LOAD_OPTIONS);
	}

	public static STFunctionGrammarAccess getGrammarAccess() {
		return SERVICE_PROVIDER_FCT.get(STFunctionGrammarAccess.class);
	}

	public static STFunctionPartitioner getPartitioner() {
		return SERVICE_PROVIDER_FCT.get(STFunctionPartitioner.class);
	}

	private STFunctionParseUtil2() {
		throw new UnsupportedOperationException();
	}
}
