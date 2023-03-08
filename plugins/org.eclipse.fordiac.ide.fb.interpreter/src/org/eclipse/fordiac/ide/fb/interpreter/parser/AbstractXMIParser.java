/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmend�a, Bianca Wiesmayr
 *       - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter.parser;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResourceSet;

public abstract class AbstractXMIParser {

	public static final String SYNTHETIC_URI_NAME = "__synthetic"; //$NON-NLS-1$
	public static final String URI_SEPERATOR = "."; //$NON-NLS-1$
	public static final String FB_URI_EXTENSION = "xtextfbt"; //$NON-NLS-1$
	public static final String ST_URI_EXTENSION = "st"; //$NON-NLS-1$
	public static final String EXPR_URI_EXTENSION = "expr"; //$NON-NLS-1$
	public static final IResourceServiceProvider SERVICE_PROVIDER = IResourceServiceProvider.Registry.INSTANCE
			.getResourceServiceProvider(URI.createURI(SYNTHETIC_URI_NAME + URI_SEPERATOR + ST_URI_EXTENSION));

	protected final XtextResourceSet resourceSet;

	protected AbstractXMIParser() {
		this.resourceSet = (XtextResourceSet) SERVICE_PROVIDER.get(ResourceSet.class);
	}

	protected AbstractXMIParser(final XtextResourceSet resourceSet) {
		this.resourceSet = resourceSet;
	}

	protected abstract Resource createFBResource(FBType fbType);

	protected abstract Resource createFBResource(BasicFBType fbType);

	protected URI computeUnusedUri(final ResourceSet resourceSet, final String fileExtension) {
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			final var syntheticUri = URI.createURI(SYNTHETIC_URI_NAME + i + URI_SEPERATOR + fileExtension); // $NON-NLS-1$
			if (resourceSet.getResource(syntheticUri, false) == null) {
				return syntheticUri;
			}
		}
		throw new IllegalStateException("Cannot compute the URI"); //$NON-NLS-1$
	}

	protected void createAdapterResource(final XtextResourceSet resourceSet, final AdapterDeclaration adapter) {
		final var adapterResource = resourceSet.createResource(computeUnusedUri(resourceSet, FB_URI_EXTENSION));
		adapterResource.getContents().add(adapter.getType().getAdapterFBType());
	}

	protected void createStructResource(final XtextResourceSet resourceSet, final VarDeclaration variable) {
		if (variable.getType() instanceof StructuredType) {
			final var structResource = resourceSet.createResource(computeUnusedUri(resourceSet, FB_URI_EXTENSION));
			final var type = (StructuredType) variable.getType();
			structResource.getContents().add(type);
			type.getMemberVariables().forEach(v -> createStructResource(resourceSet, v));
		}
	}
}
