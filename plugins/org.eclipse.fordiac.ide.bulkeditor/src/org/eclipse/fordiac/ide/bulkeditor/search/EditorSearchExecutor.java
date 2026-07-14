/*******************************************************************************
 * Copyright (c) 2025, 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor.search;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.bulkeditor.query.QueryModelHelper;
import org.eclipse.fordiac.ide.model.search.ISearchContext;
import org.eclipse.fordiac.ide.model.search.types.IEC61499ElementSearch;
import org.eclipse.fordiac.ide.model.search.types.IEC61499SearchFilter;
import org.eclipse.fordiac.ide.model.typelibrary.AttributeTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;

public class EditorSearchExecutor {

	public record Result(List<? extends EObject> searchResult, Set<URI> searchScope,
			AttributeTypeEntry attributeTypeEntry) {
	}

	private EditorSearchExecutor() {
	}

	public static Result search(final EObject queryRoot, final IProject project) {
		final IEC61499SearchFilter filter = QuerySearchAdapter.buildTargetSearchFilter(queryRoot);

		final PlaceConfig placeConfig = QuerySearchAdapter.buildPlaceConfig(queryRoot);
		final List<ISearchContext> contexts = List.of(SearchHelper.createSearchContext(project, placeConfig));

		final Stream<? extends EObject> result = contexts.stream().flatMap(
				ctx -> new IEC61499ElementSearch(ctx, filter, SearchHelper.createChildrenSearchProvider(placeConfig))
						.performSearch().stream());

		final var res = result.toList();

		final Set<URI> searchScope = contexts.stream().flatMap(ISearchContext::getTypes)
				.collect(Collectors.toUnmodifiableSet());

		final AttributeTypeEntry attrDeclType = resolveAttributeDeclarationType(queryRoot, project);

		return new Result(res, searchScope, attrDeclType);
	}

	private static AttributeTypeEntry resolveAttributeDeclarationType(final EObject queryRoot, final IProject project) {
		final EObject target = QueryModelHelper.getContainedChild(queryRoot, QueryModelHelper.REF_TARGET);
		final EObject targetOption = (target != null)
				? QueryModelHelper.getContainedChild(target, QueryModelHelper.REF_TARGET)
				: null;

		if (targetOption == null || !targetOption.eClass().getName().equals(QueryModelHelper.ATTRIBUTE_DECLARATION)) {
			return null;
		}

		final String rawName = (String) QueryModelHelper.getFeatureValue(targetOption, QueryModelHelper.FEATURE_NAME);
		final String name = QuerySearchAdapter.substitutePlaceholders(rawName,
				QuerySearchAdapter.resolvePlaceholders(queryRoot));
		if (name == null || name.isBlank()) {
			return null;
		}
		return TypeLibraryManager.INSTANCE.getTypeLibrary(project).getAttributeTypeEntry(name);
	}
}