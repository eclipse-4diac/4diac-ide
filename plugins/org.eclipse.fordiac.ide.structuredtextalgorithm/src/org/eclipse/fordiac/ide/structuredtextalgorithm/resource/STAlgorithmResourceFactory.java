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
package org.eclipse.fordiac.ide.structuredtextalgorithm.resource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fordiac.ide.model.resource.FordiacTypeResourceFactory;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class STAlgorithmResourceFactory extends XtextResourceFactory {

	@Inject
	public STAlgorithmResourceFactory(final Provider<XtextResource> resourceProvider) {
		super(resourceProvider);
	}

	@Override
	public Resource createResource(final URI uri) {
		if (STAlgorithmResource.isValidAdditionalUri(uri) && !uri.hasQuery()) {
			return FordiacTypeResourceFactory.INSTANCE.createResource(uri);
		}
		return super.createResource(uri);
	}
}
