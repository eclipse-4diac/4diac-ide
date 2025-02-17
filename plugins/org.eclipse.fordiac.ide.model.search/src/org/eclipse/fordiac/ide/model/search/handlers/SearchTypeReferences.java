/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.search.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.search.ModelQuerySpec;
import org.eclipse.fordiac.ide.model.search.ModelQuerySpec.SearchScope;
import org.eclipse.fordiac.ide.model.search.ModelSearchQuery;
import org.eclipse.fordiac.ide.model.search.ModelSearchResult;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.SubAppTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class SearchTypeReferences extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		final TypeEntry typeEntry = getTypeEntryFromSelection(selection);
		if (typeEntry != null) {
			// @formatter:off
			final ModelQuerySpec searchSpec = new ModelQuerySpec(
					typeEntry.getFullTypeName(),
					false,
					false,
					true,
					false,
					false,
					true,
					SearchScope.PROJECT,
					typeEntry.getFile().getProject());
			// @formatter:on
			final ModelSearchQuery query = createModelSearchQuery(typeEntry, searchSpec);
			NewSearchUI.runQueryInBackground(query, NewSearchUI.getSearchResultView());
		}
		return Status.OK_STATUS;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final Object selection = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_CURRENT_SELECTION_NAME);
		setBaseEnabled(getTypeEntryFromSelection(selection) != null);
	}

	private static TypeEntry getTypeEntryFromSelection(final Object selection) {
		if (selection instanceof final StructuredSelection structSel && !structSel.isEmpty()
				&& structSel.getFirstElement() instanceof final IFile file) {
			final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);
			if (typeEntry instanceof DataTypeEntry || typeEntry instanceof FBTypeEntry
					|| typeEntry instanceof SubAppTypeEntry) {
				return typeEntry;
			}
		}
		return null;
	}

	private static ModelSearchQuery createModelSearchQuery(final TypeEntry typeEntry, final ModelQuerySpec searchSpec) {
		return new ModelSearchQuery(searchSpec) {
			@Override
			protected ModelSearchResult createModelSearchResult() {
				return new ModelSearchResult(this) {
					@Override
					public void addResult(final EObject res) {
						if (res instanceof final LibraryElement libEl && typeEntry.equals(libEl.getTypeEntry())) {
							// as we are searching for our references do not add the root type into the
							// search result
							return;
						}
						super.addResult(res);
					}
				};
			}
		};
	}

}
