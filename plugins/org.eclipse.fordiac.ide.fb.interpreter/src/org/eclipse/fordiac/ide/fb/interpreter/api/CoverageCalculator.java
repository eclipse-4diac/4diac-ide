/*******************************************************************************
 * Copyright (c) 2023 Fabio Gandolfi
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.fb.interpreter.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;

public class CoverageCalculator {

	public static float calculateCoverageOfSequence(final List<Transaction> transactions, final FBType fbType) {

		if (fbType instanceof final BasicFBType bfb) {
			final EccTraceHelper eccTraceHelper = new EccTraceHelper(transactions, bfb.getECC());

			return eccTraceHelper.getAllStatesOfSequenceUnique().size()
					/ (float) eccTraceHelper.getAllPossibleStates().size();
		}
		return -1;
	}

	public static float calculateNodeCoverageOfSuiteBy(final HashMap<String, Integer> visitedStates) {

		final Integer allPossibleStates = visitedStates.size();

		final long visitedStatesNumber = visitedStates.entrySet().stream().filter(s -> s.getValue() > 0).count();

		return visitedStatesNumber / (float) allPossibleStates;
	}

	public static float calculatePathCoverageOfSuiteBy(final HashMap<ArrayList<String>, Integer> visitedPaths) {

		final Integer allPossiblePaths = visitedPaths.size();

		final long visitedPathNumber = visitedPaths.entrySet().stream().filter(s -> s.getValue() > 0).count();

		return visitedPathNumber / (float) allPossiblePaths;
	}
}
