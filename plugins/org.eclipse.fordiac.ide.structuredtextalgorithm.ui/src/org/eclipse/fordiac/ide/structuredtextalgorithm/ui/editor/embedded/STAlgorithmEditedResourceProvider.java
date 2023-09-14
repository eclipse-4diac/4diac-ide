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
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.embedded;

import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.structuredtextalgorithm.parser.antlr.STAlgorithmParser;
import org.eclipse.fordiac.ide.structuredtextalgorithm.resource.STAlgorithmResource;
import org.eclipse.fordiac.ide.structuredtextcore.resource.STCoreResource;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.parser.IParser;
import org.eclipse.xtext.resource.IResourceFactory;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.ui.editor.embedded.IEditedResourceProvider;

@SuppressWarnings("restriction")
public abstract class STAlgorithmEditedResourceProvider implements IEditedResourceProvider {
	private static final URI SYNTHETIC_URI_FBT = URI.createURI("__synthetic.stalg"); //$NON-NLS-1$
	private static final IResourceServiceProvider SERVICE_PROVIDER_FBT = IResourceServiceProvider.Registry.INSTANCE
			.getResourceServiceProvider(SYNTHETIC_URI_FBT);

	private static final URI SYNTHETIC_URI_FCT = URI.createURI("__synthetic.stfunc"); //$NON-NLS-1$
	private static final IResourceServiceProvider SERVICE_PROVIDER_FCT = IResourceServiceProvider.Registry.INSTANCE
			.getResourceServiceProvider(SYNTHETIC_URI_FCT);

	private static final URI SYNTHETIC_URI_GCF = URI.createURI("__synthetic.globalconsts"); //$NON-NLS-1$
	private static final IResourceServiceProvider SERVICE_PROVIDER_GCF = IResourceServiceProvider.Registry.INSTANCE
			.getResourceServiceProvider(SYNTHETIC_URI_GCF);

	private final LibraryElement libraryElement;

	protected STAlgorithmEditedResourceProvider(final LibraryElement libraryElement) {
		this.libraryElement = libraryElement;
	}

	@Override
	public STAlgorithmResource createResource() {
		final XtextResourceSet resourceSet = (XtextResourceSet) SERVICE_PROVIDER_FBT.get(ResourceSet.class);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("fbt", //$NON-NLS-1$
				SERVICE_PROVIDER_FBT.get(IResourceFactory.class));
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("fct", //$NON-NLS-1$
				SERVICE_PROVIDER_FCT.get(IResourceFactory.class));
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("gcf", //$NON-NLS-1$
				SERVICE_PROVIDER_GCF.get(IResourceFactory.class));
		resourceSet.getLoadOptions().putAll(Map.of(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE, // resolve all
				ResourceDescriptionsProvider.LIVE_SCOPE, Boolean.TRUE // use live scope
		));
		final STAlgorithmResource resource = (STAlgorithmResource) SERVICE_PROVIDER_FBT.get(XtextResource.class);
		resource.setURI(getURI());
		resource.setLibraryElement(libraryElement);
		resource.setIncludeInternalLibraryElement(libraryElement instanceof BaseFBType);
		resource.setEntryPoint(getEntryPoint());
		resourceSet.getResources().add(resource);
		resource.getDefaultLoadOptions().putAll(Map.of(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE, // resolve all
				STCoreResource.OPTION_PLAIN_ST, Boolean.TRUE // use plain ST
		));
		return resource;
	}

	protected abstract ParserRule getEntryPoint();

	protected URI getURI() {
		if (libraryElement != null) {
			final Resource resource = libraryElement.eResource();
			if (resource != null) {
				final URI uri = resource.getURI();
				if (uri != null) {
					return uri;
				}
			}
		}
		return SYNTHETIC_URI_FBT;
	}

	protected static STAlgorithmParser getParser() {
		return (STAlgorithmParser) SERVICE_PROVIDER_FBT.get(IParser.class);
	}
}
