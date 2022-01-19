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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.ISearchResultListener;
import org.eclipse.search.ui.text.AbstractTextSearchResult;
import org.eclipse.search.ui.text.IEditorMatchAdapter;
import org.eclipse.search.ui.text.IFileMatchAdapter;

public class ModelSearchResult extends AbstractTextSearchResult {

	private final ISearchQuery modelSearchQuery;

	private final List<String> results; // Just a dummy example to print things out; TODO: change for the actual search

	public ModelSearchResult(final ISearchQuery job) {
		this.modelSearchQuery = job;
		this.results = new ArrayList<>();
	}

	@Override
	public void addListener(final ISearchResultListener l) {
	}

	@Override
	public void removeListener(final ISearchResultListener l) {
	}

	@Override
	public String getLabel() {
		return "Label for ModelSearchResult";
	}

	@Override
	public String getTooltip() {
		return "Found strings";
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public ISearchQuery getQuery() {
		return modelSearchQuery;
	}

	public void addResults(final String res) {
		results.add(res);
	}

	public List<String> getResults() {
		return results;
	}

	@Override
	public IEditorMatchAdapter getEditorMatchAdapter() {
		return null;
	}

	@Override
	public IFileMatchAdapter getFileMatchAdapter() {
		return null;
	}

}
