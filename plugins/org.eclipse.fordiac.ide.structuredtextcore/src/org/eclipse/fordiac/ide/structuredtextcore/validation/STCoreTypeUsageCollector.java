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
package org.eclipse.fordiac.ide.structuredtextcore.validation;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

import com.google.inject.Inject;

public class STCoreTypeUsageCollector {

	@Inject
	private IQualifiedNameProvider nameProvider;

	private final Set<QualifiedName> usedTypes = new HashSet<>();

	public Set<QualifiedName> collectUsedTypes(final EObject object) {
		final TreeIterator<EObject> iter = EcoreUtil.getAllProperContents(object, true);
		while (iter.hasNext()) {
			collectUsedTypesFrom(iter.next());
		}
		return usedTypes;
	}

	protected void collectUsedTypesFrom(final EObject object) {
		object.eCrossReferences().stream().filter(target -> isExternalReference(object, target))
				.map(this::resolveImportedType).filter(Objects::nonNull).map(nameProvider).forEach(usedTypes::add);
	}

	protected static boolean isExternalReference(final EObject source, final EObject target) {
		return source.eResource() != target.eResource();
	}

	protected INamedElement resolveImportedType(final EObject object) {
		if (object.eIsProxy()) {
			return null;
		}
		final LibraryElement libraryElement = EcoreUtil2.getContainerOfType(object, LibraryElement.class);
		if (libraryElement != null) {
			return libraryElement;
		}
		if (object instanceof final INamedElement element) {
			return element;
		}
		return null;
	}

	public Set<QualifiedName> getUsedTypes() {
		return usedTypes;
	}
}
