/*******************************************************************************
 * Copyright (c) 2021, 2022 Johannes Kepler University Linz and others
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
 *  Paul Pavlicek - cleanup
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.fb.interpreter.fbnetwork;

import static org.eclipse.fordiac.ide.fb.interpreter.mm.utils.FBNetworkTestRunner.runFBNetworkTest;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.fb.interpreter.api.FBTransactionBuilder;
import org.eclipse.fordiac.ide.fb.interpreter.mm.utils.FBNetworkTestRunner;
import org.eclipse.fordiac.ide.fb.interpreter.mm.utils.FBNetworkTestRunner.IllegalTraceException;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.test.fb.interpreter.infra.AbstractInterpreterTest;

/** small fb network consisting of e_sr, e_switch, and e_ctud */
public class SimpleEventConnectionTest extends AbstractInterpreterTest {

	@Override
	public void test() throws IllegalTraceException {

		final FBNetwork network = loadFbNetwork("ReferenceExamples", "ReferenceExamples", "EventConnections"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		assertNotNull(network);

		final SubApp subApp = network.getSubAppNamed("Ex1a"); //$NON-NLS-1$

		final EList<Transaction> returnedTransactions = runFBNetworkTest(subApp.getSubAppNetwork(), "E_SPLIT", "EI"); //$NON-NLS-1$ //$NON-NLS-2$

		assertTrue(returnedTransactions.size() == 3);

		// transaction 0: initial trigger
		FBTransactionBuilder expectedT = new FBTransactionBuilder("E_SPLIT.EI").addOutputEvent("E_SPLIT.EO1")
				.addOutputEvent("E_SPLIT.EO2");
		FBNetworkTestRunner.checkTransaction(returnedTransactions.get(0), expectedT);

		// transaction 1: first event connection
		expectedT = new FBTransactionBuilder("E_REND.EI1");
		FBNetworkTestRunner.checkTransaction(returnedTransactions.get(1), expectedT);

		// transaction 2: second event connection
		expectedT = new FBTransactionBuilder("E_REND.EI2").addOutputEvent("E_REND.EO");
		FBNetworkTestRunner.checkTransaction(returnedTransactions.get(2), expectedT);

	}

}
