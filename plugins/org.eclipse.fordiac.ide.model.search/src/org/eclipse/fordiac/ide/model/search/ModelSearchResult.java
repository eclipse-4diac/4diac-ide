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
package org.eclipse.fordiac.ide.model.search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.SearchResultEvent;
import org.eclipse.search.ui.text.AbstractTextSearchResult;
import org.eclipse.search.ui.text.IEditorMatchAdapter;
import org.eclipse.search.ui.text.IFileMatchAdapter;

public class ModelSearchResult extends AbstractTextSearchResult {

	private final ISearchQuery modelSearchQuery;

	private final List<EObject> results;

	private final SearchNameDictionary dictionary;

	private final Map<URI, Set<Match>> matches = new HashMap<>();

	public ModelSearchResult(final ISearchQuery job) {
		this.modelSearchQuery = job;
		this.results = new ArrayList<>();
		this.dictionary = new SearchNameDictionary();
	}

	@Override
	public String getLabel() {
		if (results.size() == 1) {
			return "\'" + modelSearchQuery.getLabel() + "\' - " + results.size() + " result"; //$NON-NLS-1$
		}
		return "\'" + modelSearchQuery.getLabel() + "\' - " + results.size() + " results"; //$NON-NLS-1$
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

	public void addResults(final List<? extends EObject> res) {
		results.addAll(res);
		if (!res.isEmpty()) {
			fireChange(getSearchResultEvent(res.get(0), ModelSearchResultEvent.ADDED));
		}
	}

	public Match[] getFordiacMatches(final URI uri) {
		final Set<Match> targetMatches = matches.get(uri);
		if (targetMatches == null) {
			return new Match[0];
		}
		return targetMatches.toArray(new Match[targetMatches.size()]);
	}

	public void addFordiacMatch(final Match match) {
		matches.computeIfAbsent(match.getUri(),
				k -> new ConcurrentSkipListSet<Match>(Comparator.comparing(Match::getLocation))).add(match);
	}

	public boolean hasFordiacMatch(final URI uri) {
		final var uriMatches = matches.get(uri);
		return uriMatches != null && !uriMatches.isEmpty();
	}

	private SearchResultEvent getSearchResultEvent(final EObject el, final int eventKind) {
		final ModelSearchResultEvent searchResEvent = new ModelSearchResultEvent(this);
		searchResEvent.setKind(eventKind);
		searchResEvent.setResult(el);
		return searchResEvent;
	}

	public List<EObject> getResults() {
		return results;
	}

	public SearchNameDictionary getDictionary() {
		return dictionary;
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

	public void clear() {
		final EObject first = (results.isEmpty()) ? null : results.get(0);
		results.clear();
		matches.clear();
		fireChange(getSearchResultEvent(first, ModelSearchResultEvent.REMOVED));
	}

}
