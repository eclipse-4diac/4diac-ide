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
 *   Bianca Wiesmayr - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.application.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.search.ui.ISearchResult;
import org.eclipse.search.ui.text.MatchEvent;

/** An event object describing addition and removal of matches. Events of this class are sent when <code>EObject</code>s
 * are added or removed from an <code>AbstractTextSearchResult</code>. */

public class ModelSearchResultEvent extends MatchEvent {

	private static final long serialVersionUID = -4864776728004102915L;

	static final int ADDED = 1;
	static final int REMOVED = 2;

	private int fKind;
	private final List<EObject> matches;

	/** create search result event for the given search result
	 *
	 * @param searchResult event source */
	protected ModelSearchResultEvent(ISearchResult searchResult) {
		super(searchResult);
		matches = new ArrayList<>();
	}

	@Override
	public void setKind(int eventKind) {
		this.fKind = eventKind;
	}

	public void setResult(EObject el) {
		matches.add(el);
	}

	public void setResults(Collection<EObject> el) {
		matches.addAll(el);
	}

	@Override
	public int getKind() {
		return fKind;
	}
}
