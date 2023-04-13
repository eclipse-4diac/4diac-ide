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

package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.helpers;

import java.util.HashMap;
import java.util.List;

import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;

public class CoverageCalculator {

	public static float calculateCoverageOfSequence(final List<Transaction> transactions) {

		final EccTraceHelper eccTraceHelper = new EccTraceHelper(transactions);

		return eccTraceHelper.getAllStates().size() / (float) eccTraceHelper.getAllPossibleStates().size();
	}

	public static float calculateCoverageOfSuiteBy(final HashMap<String, Integer> visitedStates) {

		final Integer allPossibleStates = visitedStates.size();

		final long visitedStatesNumber = visitedStates.entrySet().stream().filter(s -> s.getValue() > 0).count();

		return visitedStatesNumber / (float) allPossibleStates;
	}
}
