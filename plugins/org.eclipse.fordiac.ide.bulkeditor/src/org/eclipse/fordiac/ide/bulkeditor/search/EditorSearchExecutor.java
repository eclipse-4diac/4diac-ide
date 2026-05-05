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

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.bulkeditor.editors.BulkEditorMode;
import org.eclipse.fordiac.ide.bulkeditor.ui.BulkEditorControls;
import org.eclipse.fordiac.ide.bulkeditor.ui.FilterComposite;
import org.eclipse.fordiac.ide.model.search.ISearchContext;
import org.eclipse.fordiac.ide.model.search.types.IEC61499ElementSearch;
import org.eclipse.fordiac.ide.model.search.types.IEC61499SearchFilter;
import org.eclipse.fordiac.ide.model.typelibrary.AttributeTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;

public class EditorSearchExecutor {

	public record Result(List<? extends EObject> searchResult, Set<URI> searchScope, SearchHelper helper,
			AttributeTypeEntry simpleAttributeTypeEntry) {
	}

	private EditorSearchExecutor() {
		// static entry point
	}

	public static Result search(final SearchParameters params, final IProject project) {
		final SearchHelper helper = buildHelper(params);

		final List<ISearchContext> contexts;
		if (params.subappHierarchyScope()) {
			contexts = SearchHelper.createSearchContextList(project, params.selectedSubApps());
		} else {
			contexts = helper.createSearchContextList(params.workspaceScope(), params.projectScope(), project);
		}

		AttributeTypeEntry simpleAttributeTypeEntry = null;
		final IEC61499SearchFilter modelSearchFilter;
		if (BulkEditorMode.resolve(params.modeSelection(), params.advancedMode()) == BulkEditorMode.SIMPLE_ATTRIBUTE) {
			simpleAttributeTypeEntry = TypeLibraryManager.INSTANCE.getTypeLibrary(project)
					.getAttributeTypeEntry(params.searchText().getText());
			if (simpleAttributeTypeEntry == null) {
				return null;
			}
			modelSearchFilter = SearchHelper.createAttributeDeclarationSearchFilter(simpleAttributeTypeEntry.getType());
		} else {
			modelSearchFilter = SearchHelper.createSearchFilter(params.modeSelection(),
					BulkEditorControls.DEFAULT_LIST.stream().map(params.searchFilter()::getFilter).toList());
		}

		final List<? extends EObject> result = contexts.stream().flatMap(
				context -> new IEC61499ElementSearch(context, modelSearchFilter, helper.createChildrenSearchProvider())
						.performSearch().stream())
				.toList();

		final Set<URI> searchScope = contexts.stream().flatMap(ISearchContext::getTypes)
				.collect(Collectors.toUnmodifiableSet());

		return new Result(result, searchScope, helper, simpleAttributeTypeEntry);
	}

	private static SearchHelper buildHelper(final SearchParameters params) {
		return new SearchHelper(recordFor(params.fbSubappTypesSelected(), params.fbSubappTypesFilter()),
				recordFor(params.fbTypedSubappInstanceSelected(), params.fbTypedSubappInstanceFilter()),
				recordFor(params.untypedSubappSelected(), params.untypedSubappFilter()),
				recordFor(params.dataTypesSelected(), params.dataTypesFilter()),
				recordFor(params.attributeTypesSelected(), params.attributeTypesFilter()),
				params.ignoreLinkedLibraries());
	}

	private static FilterRecord recordFor(final boolean selected, final FilterComposite filter) {
		return new FilterRecord(selected, filter.getFilter(BulkEditorControls.LIST_WITHOUT_VALUE.get(0)),
				filter.getFilter(BulkEditorControls.LIST_WITHOUT_VALUE.get(1)),
				filter.getFilter(BulkEditorControls.LIST_WITHOUT_VALUE.get(2)));
	}
}
