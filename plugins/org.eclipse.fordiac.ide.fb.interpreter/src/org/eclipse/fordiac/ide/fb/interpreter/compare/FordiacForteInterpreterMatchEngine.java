/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University Linz and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Antonio Garmendia, Bianca Wiesmayr
 *       - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter.compare;

import org.eclipse.emf.compare.match.DefaultComparisonFactory;
import org.eclipse.emf.compare.match.DefaultEqualityHelperFactory;
import org.eclipse.emf.compare.match.IComparisonFactory;
import org.eclipse.emf.compare.match.IMatchEngine;
import org.eclipse.emf.compare.match.eobject.IEObjectMatcher;
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryImpl;
import org.eclipse.emf.compare.scope.IComparisonScope;

public class FordiacForteInterpreterMatchEngine implements IMatchEngine.Factory {

	// Default Ranking
	private int ranking = 150;

	public FordiacForteInterpreterMatchEngine() {
		// Do nothing
	}

	@Override
	public IMatchEngine getMatchEngine() {
		// Custom Matcher
		final IEObjectMatcher fordiacInterpreterMatcher = new FordiacForteInterpreterMatcher();
		final IComparisonFactory comparisonFactory = new DefaultComparisonFactory(new DefaultEqualityHelperFactory());
		@SuppressWarnings("deprecation")
		final MatchEngineFactoryImpl matchEngineFactory = new MatchEngineFactoryImpl(fordiacInterpreterMatcher,
				comparisonFactory);
		matchEngineFactory.setRanking(getRanking());
		// Return the match engine
		return matchEngineFactory.getMatchEngine();
	}

	@Override
	public int getRanking() {
		return this.ranking;
	}

	@Override
	public boolean isMatchEngineFactoryFor(final IComparisonScope scope) {
		return true;
	}

	@Override
	public void setRanking(final int parseInt) {
		this.ranking = parseInt;
	}

}
