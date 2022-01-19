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
import org.eclipse.search.ui.text.AbstractTextSearchResult;

// Content provider supplies the data which should be displayed in the TableViewer
public class ModelSearchTableContentProvider implements IStructuredContentProvider, IModelSearchContentProvider {

	private final ModelSearchResultPage resultPage;
	private AbstractTextSearchResult result;
	private final Object[] EMPTY_ARR = new Object[0]; // Like in FileTableConProv

	public ModelSearchTableContentProvider(final ModelSearchResultPage page) {
		this.resultPage = page;
	}

	// Used to translate the input of the viewer into an array of elements.
	// Once the setInput() method on the viewer is called, it uses the content provider to convert it.
	@Override
	public Object[] getElements(final Object inputElement) {
		if (inputElement != null) {
			// Parse the data
			final ModelSearchResult msr = (ModelSearchResult) inputElement;
			int index = 0;
			// Stupid solution for now
			final Object[] elements = new Object[msr.getResults().size()]; // Initialize it to the number of hits
			for (final String s : msr.getResults()) {
				elements[index] = s;
				index++;
			}
			return elements;
		}
		return EMPTY_ARR;
	}

	private TableViewer getViewer() {
		return (TableViewer) resultPage.getViewer();
	}

	@Override
	public void clear() {
		getViewer().refresh();
	}

}
