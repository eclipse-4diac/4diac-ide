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
package org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.SequencedSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.ide.serializer.hooks.IEObjectSnapshot;
import org.eclipse.xtext.ide.serializer.hooks.IResourceSnapshot;
import org.eclipse.xtext.ide.serializer.impl.RelatedResourcesProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.IResourceDescriptionsProvider;

import com.google.inject.Inject;

@SuppressWarnings("restriction")
public class STCoreRelatedResourcesProvider extends RelatedResourcesProvider {

	@Inject
	private IResourceDescriptionsProvider resourceDescriptionsProvider;

	@Override
	public List<RelatedResource> getRelatedResources(final Collection<IResourceSnapshot> snapshots) {
		final Map<URI, RelatedResource> result = super.getRelatedResources(snapshots).stream().collect(
				Collectors.toMap(RelatedResource::getUri, Function.identity(), (a, _) -> a, LinkedHashMap::new));
		for (final ResourceSet resourceSet : getDistinctResourceSets(snapshots)) {
			final IResourceDescriptions resourceDescriptions = resourceDescriptionsProvider
					.getResourceDescriptions(resourceSet);
			for (final IResourceDescription description : resourceDescriptions.getAllResourceDescriptions()) {
				if (!result.containsKey(description.getURI()) && containsImportedName(description, snapshots)) {
					result.put(description.getURI(),
							new STCoreRelatedResource(description.getURI(), description.getImportedNames()));
				}
			}
		}
		return result.values().stream().toList();
	}

	protected static SequencedSet<ResourceSet> getDistinctResourceSets(final Collection<IResourceSnapshot> snapshots) {
		return snapshots.stream().map(IResourceSnapshot::getResource).map(Resource::getResourceSet)
				.collect(Collectors.toCollection(LinkedHashSet::new));
	}

	protected static boolean containsImportedName(final IResourceDescription resourceDescription,
			final Collection<IResourceSnapshot> snapshots) {
		final Iterable<QualifiedName> importedNames = resourceDescription.getImportedNames();
		for (final IResourceSnapshot res : snapshots) {
			for (final IEObjectSnapshot obj : res.getObjects().values()) {
				for (final IEObjectDescription desc : obj.getDescriptions()) {
					for (final QualifiedName name : importedNames) {
						if (name.equalsIgnoreCase(desc.getQualifiedName())) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public class STCoreRelatedResource extends RelatedResource {

		private final Iterable<QualifiedName> importedNames;

		public STCoreRelatedResource(final URI uri, final Iterable<QualifiedName> importedNames) {
			super(uri);
			this.importedNames = importedNames;
		}

		public Iterable<QualifiedName> getImportedNames() {
			return importedNames;
		}
	}
}
