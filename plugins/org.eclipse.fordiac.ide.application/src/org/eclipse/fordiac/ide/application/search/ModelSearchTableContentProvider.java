/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.search;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.search.ui.text.AbstractTextSearchResult;

// Content provider supplies the data which should be displayed in the TableViewer
public class ModelSearchTableContentProvider implements IStructuredContentProvider, IModelSearchContentProvider {

	private final ModelSearchResultPage resultPage;
	private ModelSearchResult result;
	private static final Object[] EMPTY_ARR = new Object[0];

	public ModelSearchTableContentProvider(final ModelSearchResultPage page) {
		this.resultPage = page;
	}

	// Used to translate the input of the viewer into an array of elements.
	// Once the setInput() method on the viewer is called, it uses the content provider to convert it.
	@Override
	public Object[] getElements(final Object inputElement) {
		if (inputElement instanceof ModelSearchResult) {
			// Parse the data
			result = (ModelSearchResult) inputElement;
			return result.getResults().toArray();

		}
		return EMPTY_ARR;
	}

	private TableViewer getViewer() {
		return (TableViewer) resultPage.getViewer();
	}

	@Override
	public void clear() {
		getViewer().setInput(null);
		getViewer().refresh();
	}

	@Override
	public void elementsChanged(final Object[] updatedElements) {
		// TODO implement updating
	}

	@Override
	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
		result = (ModelSearchResult) newInput;
	}

	AbstractTextSearchResult getSearchResult() {
		return result;
	}
}
