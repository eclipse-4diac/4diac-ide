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
 *   Dunja Zivotin, Bianca Wiesmayr
 *    - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.search;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.SearchResultEvent;
import org.eclipse.search.ui.text.AbstractTextSearchResult;
import org.eclipse.search.ui.text.IEditorMatchAdapter;
import org.eclipse.search.ui.text.IFileMatchAdapter;

public class ModelSearchResult extends AbstractTextSearchResult {

	private final ISearchQuery modelSearchQuery;
	private final ModelSearchResultEvent searchResEvent;

	private final List<EObject> results;

	public ModelSearchResult(final ISearchQuery job) {
		this.modelSearchQuery = job;
		this.results = new ArrayList<>();
		searchResEvent = new ModelSearchResultEvent(this);
	}

	@Override
	public String getLabel() {
		return modelSearchQuery.getLabel() + ": " + results.size() + " results"; //$NON-NLS-1$
	}

	@Override
	public String getTooltip() {
		return "Found model elements";
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public ISearchQuery getQuery() {
		return modelSearchQuery;
	}

	public void addResult(final EObject res) {
		results.add(res);
		fireChange(getSearchResultEvent(res, ModelSearchResultEvent.ADDED));
	}

	public void addResults(List<EObject> res) {
		results.addAll(res);
		fireChange(getSearchResultEvent(res.get(0), ModelSearchResultEvent.ADDED));
	}

	private SearchResultEvent getSearchResultEvent(EObject el, int eventKind) {
		searchResEvent.setKind(eventKind);
		searchResEvent.setResult(el);
		return searchResEvent;
	}

	public List<EObject> getResults() {
		return results;
	}

	@Override
	public IEditorMatchAdapter getEditorMatchAdapter() {
		// for clicking onto the results
		return null;
	}

	@Override
	public IFileMatchAdapter getFileMatchAdapter() {
		// For clicking onto the results
		return null;
	}

}
