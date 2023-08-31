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

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.structuredtextalgorithm.resource.STAlgorithmResource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil;
import org.eclipse.xtext.ParserRule;

public class STAlgorithmConditionEditedResourceProvider extends STAlgorithmEditedResourceProvider {

	private final INamedElement expectedType;
	private final Collection<? extends EObject> additionalContent;

	public STAlgorithmConditionEditedResourceProvider(final LibraryElement libraryElement,
			final Collection<? extends EObject> additionalContent, final INamedElement expectedType) {
		super(libraryElement);
		this.expectedType = expectedType;
		this.additionalContent = additionalContent;
	}

	@Override
	public STAlgorithmResource createResource() {
		final STAlgorithmResource resource = super.createResource();
		resource.getDefaultLoadOptions().put(STCoreUtil.OPTION_EXPECTED_TYPE, expectedType);
		resource.getResourceSet().getLoadOptions().put(STCoreUtil.OPTION_EXPECTED_TYPE, expectedType);
		resource.getAdditionalContent().addAll(additionalContent);
		return resource;
	}

	@Override
	protected ParserRule getEntryPoint() {
		return getParser().getGrammarAccess().getSTExpressionSourceRule();
	}
}
