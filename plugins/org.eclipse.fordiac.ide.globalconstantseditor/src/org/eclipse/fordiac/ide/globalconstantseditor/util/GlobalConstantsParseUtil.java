/*******************************************************************************
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.globalconstantseditor.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STGlobalConstsSource;
import org.eclipse.fordiac.ide.globalconstantseditor.resource.GlobalConstantsResource;
import org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCoreParseUtil;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.IResourceFactory;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.LazyStringInputStream;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.Issue;

public final class GlobalConstantsParseUtil {
	private static final URI SYNTHETIC_URI_GCF = URI.createURI("__synthetic.globalconsts"); //$NON-NLS-1$
	private static final IResourceServiceProvider SERVICE_PROVIDER_GCF = IResourceServiceProvider.Registry.INSTANCE
			.getResourceServiceProvider(SYNTHETIC_URI_GCF);

	private static final URI SYNTHETIC_URI_FCT = URI.createURI("__synthetic.stfunc"); //$NON-NLS-1$
	private static final IResourceServiceProvider SERVICE_PROVIDER_FCT = IResourceServiceProvider.Registry.INSTANCE
			.getResourceServiceProvider(SYNTHETIC_URI_FCT);

	public static STGlobalConstsSource parse(final GlobalConstants globalConstants, final List<String> errors,
			final List<String> warnings, final List<String> infos) {
		final IParseResult parseResult = parseInternal(globalConstants, errors, warnings, infos);
		return parseResult != null ? (STGlobalConstsSource) parseResult.getRootASTElement() : null;
	}

	private static IParseResult parseInternal(final GlobalConstants type, final List<String> errors,
			final List<String> warnings, final List<String> infos) {
		final List<Issue> issues = new ArrayList<>();
		final IParseResult parseResult = parseInternal(type, issues);
		return STCoreParseUtil.postProcess(type.getName(), errors, warnings, infos, issues, parseResult);
	}

	private static IParseResult parseInternal(final GlobalConstants type, final List<Issue> issues) {
		final XtextResourceSet resourceSet = (XtextResourceSet) SERVICE_PROVIDER_GCF.get(ResourceSet.class);
		resourceSet.getLoadOptions().put(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		resourceSet.getLoadOptions().put(ResourceDescriptionsProvider.PERSISTED_DESCRIPTIONS, Boolean.TRUE);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("gcf", //$NON-NLS-1$
				SERVICE_PROVIDER_GCF.get(IResourceFactory.class));
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("GCF", //$NON-NLS-1$
				SERVICE_PROVIDER_GCF.get(IResourceFactory.class));
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("fct", //$NON-NLS-1$
				SERVICE_PROVIDER_FCT.get(IResourceFactory.class));
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("FCT", //$NON-NLS-1$
				SERVICE_PROVIDER_FCT.get(IResourceFactory.class));
		final GlobalConstantsResource resource = (GlobalConstantsResource) SERVICE_PROVIDER_GCF
				.get(XtextResource.class);
		resource.setURI(type.eResource().getURI());
		resource.setLibraryElement(type);
		resource.setIncludeInternalLibraryElement(false);
		resourceSet.getResources().add(resource);
		try {
			resource.load(new LazyStringInputStream(GlobalConstantsPartitioner.combine(type)),
					Map.of(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE,
							ResourceDescriptionsProvider.PERSISTED_DESCRIPTIONS, Boolean.TRUE,
							GlobalConstantsResource.OPTION_PLAIN_ST, Boolean.TRUE));
		} catch (final IOException e) {
			return null;
		}
		final var validator = resource.getResourceServiceProvider().getResourceValidator();
		issues.addAll(validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl));
		return resource.getParseResult();
	}

	private GlobalConstantsParseUtil() {
		throw new UnsupportedOperationException();
	}
}
