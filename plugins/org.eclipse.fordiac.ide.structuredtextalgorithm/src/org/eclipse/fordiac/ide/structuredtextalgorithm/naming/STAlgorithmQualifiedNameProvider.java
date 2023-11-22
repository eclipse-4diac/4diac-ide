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
package org.eclipse.fordiac.ide.structuredtextalgorithm.naming;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.structuredtextalgorithm.resource.STAlgorithmResource;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSource;
import org.eclipse.fordiac.ide.structuredtextcore.naming.STCoreQualifiedNameProvider;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpressionSource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpressionSource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTypeDeclaration;
import org.eclipse.xtext.naming.QualifiedName;

public class STAlgorithmQualifiedNameProvider extends STCoreQualifiedNameProvider {

	protected QualifiedName qualifiedName(final STAlgorithmSource source) {
		return getLibraryElementName(source);
	}

	protected QualifiedName qualifiedName(final STExpressionSource source) {
		return getLibraryElementName(source);
	}

	protected QualifiedName qualifiedName(final STInitializerExpressionSource source) {
		return getLibraryElementName(source);
	}

	protected QualifiedName qualifiedName(final STTypeDeclaration source) {
		return getLibraryElementName(source);
	}

	protected QualifiedName getLibraryElementName(final EObject source) {
		if (source.eResource() instanceof final STAlgorithmResource resource) {
			final LibraryElement libraryElement = resource.getInternalLibraryElement();
			if (libraryElement != null) {
				return getFullyQualifiedName(libraryElement);
			}
		}
		return null;
	}
}
