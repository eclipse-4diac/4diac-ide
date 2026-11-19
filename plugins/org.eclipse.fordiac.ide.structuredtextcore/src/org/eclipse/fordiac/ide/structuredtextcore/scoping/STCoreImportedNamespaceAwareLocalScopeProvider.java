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
package org.eclipse.fordiac.ide.structuredtextcore.scoping;

import static java.util.Collections.singletonList;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fordiac.ide.model.helpers.ImportHelper;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.structuredtextcore.resource.LibraryElementXtextResource;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.ImportNormalizer;
import org.eclipse.xtext.scoping.impl.ImportedNamespaceAwareLocalScopeProvider;
import org.eclipse.xtext.scoping.impl.ScopeBasedSelectable;

public class STCoreImportedNamespaceAwareLocalScopeProvider extends ImportedNamespaceAwareLocalScopeProvider {

	@Override
	protected IScope getLocalElementsScope(final IScope parent, final EObject context, final EReference reference) {
		return getTypeImportScope(super.getLocalElementsScope(parent, context, reference), context, reference);
	}

	protected IScope getTypeImportScope(final IScope parent, final EObject context, final EReference reference) {
		if (context.eContainer() != null) {
			return parent;
		}

		final Resource resource = context.eResource();
		if (!hasQuery(resource)) {
			return parent;
		}

		final LibraryElement libraryElement = getLibraryElement(context);
		if (libraryElement == null) {
			return parent;
		}

		final QualifiedName name = getQualifiedNameProvider().getFullyQualifiedName(libraryElement);
		if (name == null || name.isEmpty()) {
			return parent;
		}

		return createTypeImportScope(parent, name, resource, reference);
	}

	protected IScope createTypeImportScope(final IScope parent, final QualifiedName name, final Resource resource,
			final EReference reference) {
		final boolean ignoreCase = isIgnoreCase(reference);
		final ImportNormalizer normalizer = doCreateImportNormalizer(name, true, ignoreCase);
		final ScopeBasedSelectable importFrom = new ScopeBasedSelectable(createTypeScope(resource, reference));
		return createImportScope(parent, singletonList(normalizer), importFrom, reference.getEReferenceType(),
				ignoreCase);
	}

	protected IScope createTypeScope(final Resource resource, final EReference reference) {
		final URI typeUri = resource.getURI().trimQuery();
		return getGlobalScope(resource, reference,
				candidate -> typeUri.equals(candidate.getEObjectURI().trimFragment()));
	}

	@Override
	protected ImportNormalizer doCreateImportNormalizer(final QualifiedName importedNamespace, final boolean wildcard,
			final boolean ignoreCase) {
		return new STCoreImportNormalizer(importedNamespace, wildcard, ignoreCase);
	}

	@Override
	protected List<ImportNormalizer> getImplicitImports(final boolean ignoreCase) {
		return ImportHelper.IMPLICIT_IMPORTS.stream().map(getQualifiedNameConverter()::toQualifiedName)
				.map(imp -> doCreateImportNormalizer(imp, true, ignoreCase)).toList();
	}

	protected static boolean hasQuery(final Resource resource) {
		final URI uri = resource.getURI();
		return uri != null && uri.hasQuery();
	}

	protected static LibraryElement getLibraryElement(final EObject context) {
		if (context.eResource() instanceof final LibraryElementXtextResource libraryElementXtextResource) {
			return libraryElementXtextResource.getInternalLibraryElement();
		}
		return null;
	}
}
