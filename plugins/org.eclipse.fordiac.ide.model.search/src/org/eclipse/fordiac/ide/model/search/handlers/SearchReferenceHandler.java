/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.model.search.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.search.ModelQuerySpec;
import org.eclipse.fordiac.ide.model.search.ModelQuerySpec.SearchScope;
import org.eclipse.fordiac.ide.model.search.ModelSearchQuery;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class SearchReferenceHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = HandlerUtil.getCurrentStructuredSelection(event);
		final INamedElement ie = getElementFromSelection(selection);

		if (ie != null && EcoreUtil.getRootContainer(ie) instanceof final LibraryElement libElement) {
			final TypeEntry typeEntry = libElement.getTypeEntry();
			// @formatter:off
			final ModelQuerySpec searchSpec = new ModelQuerySpec(
					ie.getQualifiedName(),
					false,
					true,
					true,
					false,
					false,
					true,
					false,
					SearchScope.FILE,
					typeEntry.getFile(),
					null
				);
			// @formatter:on

			final ModelSearchQuery query = new ModelSearchQuery(searchSpec);
			NewSearchUI.runQueryInBackground(query, NewSearchUI.getSearchResultView());
		}

		return null;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		setBaseEnabled(HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME) instanceof final IStructuredSelection selection
				&& getElementFromSelection(selection) != null);
	}

	private static INamedElement getElementFromSelection(final IStructuredSelection selection) {
		if (selection.getFirstElement() instanceof final IInterfaceElement ie) {
			return ie;
		}
		if (selection.getFirstElement() instanceof final EditPart ep
				&& ep.getModel() instanceof final IInterfaceElement ie) {
			return ie;
		}
		if (selection.getFirstElement() instanceof final FB fb) {
			return fb;
		}
		return null;
	}
}
