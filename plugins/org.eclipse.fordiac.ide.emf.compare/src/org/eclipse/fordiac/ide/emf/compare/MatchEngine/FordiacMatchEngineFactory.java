/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.emf.compare.MatchEngine;

import org.eclipse.emf.compare.match.IMatchEngine;
import org.eclipse.emf.compare.scope.IComparisonScope;

public class FordiacMatchEngineFactory implements IMatchEngine.Factory {

	private int ranking = 20;

	@Override
	public IMatchEngine getMatchEngine() {
		return new FordiacMatchEngine();
	}

	@Override
	public int getRanking() {
		return ranking;
	}

	@Override
	public void setRanking(final int parseInt) {
		this.ranking = parseInt;
	}

	@Override
	public boolean isMatchEngineFactoryFor(final IComparisonScope scope) {
		return true;
	}

}
