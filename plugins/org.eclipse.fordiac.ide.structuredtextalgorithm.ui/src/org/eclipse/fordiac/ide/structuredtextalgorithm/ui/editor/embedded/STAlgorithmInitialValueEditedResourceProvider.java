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

import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.structuredtextalgorithm.resource.STAlgorithmResource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.ParserRule;

public class STAlgorithmInitialValueEditedResourceProvider extends STAlgorithmEditedResourceProvider {
	private final INamedElement element;

	public STAlgorithmInitialValueEditedResourceProvider(final INamedElement element) {
		super(EcoreUtil2.getContainerOfType(element, LibraryElement.class));
		this.element = element;
	}

	@Override
	public STAlgorithmResource createResource() {
		final STAlgorithmResource resource = super.createResource();
		final LibraryElement featureType = STCoreUtil.getFeatureType(element);
		resource.getDefaultLoadOptions().put(STCoreUtil.OPTION_EXPECTED_TYPE, featureType);
		resource.getResourceSet().getLoadOptions().put(STCoreUtil.OPTION_EXPECTED_TYPE, featureType);
		return resource;
	}

	@Override
	protected ParserRule getEntryPoint() {
		return getParser().getGrammarAccess().getSTInitializerExpressionSourceRule();
	}
}
