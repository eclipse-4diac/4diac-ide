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
package org.eclipse.fordiac.ide.structuredtextcore.resource;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.helpers.ImportHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.xtext.linking.impl.ImportedNamesAdapter;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.resource.impl.DefaultResourceDescription;
import org.eclipse.xtext.util.IResourceScopeCache;

public class STCoreResourceDescription extends DefaultResourceDescription {
	private final IQualifiedNameConverter nameConverter;

	private Iterable<QualifiedName> importedNames;

	public STCoreResourceDescription(final Resource resource, final IDefaultResourceDescriptionStrategy strategy,
			final IResourceScopeCache cache, final IQualifiedNameConverter nameConverter) {
		super(resource, strategy, cache);
		this.nameConverter = nameConverter;
	}

	@Override
	public Iterable<QualifiedName> getImportedNames() {
		if (importedNames == null) {
			importedNames = computeImportedNames();
		}
		return importedNames;
	}

	protected Set<QualifiedName> computeImportedNames() {
		final Set<QualifiedName> result = new HashSet<>();
		final TreeIterator<EObject> allContents = EcoreUtil.getAllContents(getResource(), true);
		while (allContents.hasNext()) {
			computeImportedNames(allContents.next(), result);
		}
		super.getImportedNames().forEach(result::add);
		return result;
	}

	protected void computeImportedNames(final EObject object, final Set<QualifiedName> result) {
		if (object instanceof final Import imp) {
			computeImportedNames(imp, result);
		}
	}

	protected void computeImportedNames(final Import imp, final Set<QualifiedName> result) {
		final QualifiedName imported = nameConverter.toQualifiedName(imp.getImportedNamespace());
		if (!ImportHelper.WILDCARD_IMPORT.equals(imported.getLastSegment())) {
			result.add(imported.toLowerCase());
		}
	}

	protected static Set<QualifiedName> getImportedNames(final Resource resource) {
		EcoreUtil.resolveAll(resource);
		final ImportedNamesAdapter adapter = ImportedNamesAdapter.find(resource);
		if (adapter != null) {
			return adapter.getImportedNames();
		}
		return Collections.emptySet();
	}

	protected IQualifiedNameConverter getNameConverter() {
		return nameConverter;
	}

}
