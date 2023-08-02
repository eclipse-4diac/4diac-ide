/*******************************************************************************
 * Copyright (c) 2022, 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextalgorithm.scoping;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.structuredtextcore.resource.LibraryElementXtextResource;
import org.eclipse.xtext.scoping.impl.ImportNormalizer;
import org.eclipse.xtext.scoping.impl.ImportedNamespaceAwareLocalScopeProvider;

public class STAlgorithmImportedNamespaceAwareLocalScopeProvider extends ImportedNamespaceAwareLocalScopeProvider {

	@Override
	public List<ImportNormalizer> internalGetImportedNamespaceResolvers(final EObject context,
			final boolean ignoreCase) {
		final var result = super.internalGetImportedNamespaceResolvers(context, ignoreCase);
		if (context.eContainer() == null) {
			final var resource = context.eResource();
			if (resource instanceof final LibraryElementXtextResource libraryElementXtextResource) {
				final LibraryElement libraryElement = libraryElementXtextResource.getLibraryElement();
				if ((libraryElement != null) && (libraryElement.getName() != null)) {
					final var fbTypeName = getQualifiedNameConverter().toQualifiedName(libraryElement.getName());
					result.add(doCreateImportNormalizer(fbTypeName, true, ignoreCase));
				}
			}
		}
		return result;
	}

	@Override
	public boolean isRelativeImport() {
		return false;
	}
}
