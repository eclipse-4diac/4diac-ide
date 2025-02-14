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
package org.eclipse.fordiac.ide.hierarchymanager.build;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Leaf;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.resource.impl.DefaultResourceDescription;
import org.eclipse.xtext.util.IResourceScopeCache;

public class HierarchyResourceDescription extends DefaultResourceDescription {

	private final IQualifiedNameConverter nameConverter;

	private Iterable<QualifiedName> importedNames;

	public HierarchyResourceDescription(final Resource resource, final IDefaultResourceDescriptionStrategy strategy,
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
			final EObject target = allContents.next();
			if (target instanceof final Leaf leaf) {
				computeImportedNames(leaf, result);
			}
		}
		super.getImportedNames().forEach(result::add);
		return result;
	}

	protected void computeImportedNames(final Leaf leaf, final Set<QualifiedName> result) {
		final QualifiedName containerTypeName = getContainerTypeName(leaf.getContainerFileName());
		final QualifiedName refName = nameConverter.toQualifiedName(leaf.getRef());
		result.add(containerTypeName.append(refName).toLowerCase());
	}

	protected QualifiedName getContainerTypeName(final String containerFileName) {
		final IPath containerPath = new Path(containerFileName);
		if (getURI().isPlatformResource()) {
			final URI containerURI = getURI().trimSegments(getURI().segmentCount() - 2)
					.appendSegments(containerPath.segments());
			final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForURI(containerURI);
			if (typeEntry != null) {
				return QualifiedName
						.create(typeEntry.getFullTypeName().split(PackageNameHelper.PACKAGE_NAME_DELIMITER));
			}
		}
		return QualifiedName.create(TypeEntry.getTypeNameFromFileName(containerPath.lastSegment()));
	}
}
