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

import static org.eclipse.fordiac.ide.fb.interpreter.mm.utils.FBTestRunner.runFBNetworkTest;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.test.fb.interpreter.infra.AbstractInterpreterTest;

/** small fb network consisting of e_sr, e_switch, and e_ctud */
public class SimpleEventConnectionTest extends AbstractInterpreterTest {

	@Override
	public void test() {

		final FBNetwork network = loadFbNetwork("ReferenceExamples", "ReferenceExamples", "EventConnections"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		assertNotNull(network);

		final SubApp subApp = network.getSubAppNamed("Ex1a"); //$NON-NLS-1$

		final EList<Transaction> returnedTransactions = runFBNetworkTest(subApp.getSubAppNetwork(), "E_SPLIT", "EI"); //$NON-NLS-1$ //$NON-NLS-2$

		assertTrue(returnedTransactions.size() == 3);

		// transaction 0: initial trigger
		final FBTransaction t0 = (FBTransaction) returnedTransactions.get(0);
		assertTrue("EI".equals(t0.getInputEventOccurrence().getEvent().getName())); //$NON-NLS-1$
		assertTrue("E_SPLIT".equals(t0.getInputEventOccurrence().getParentFB().getName())); //$NON-NLS-1$
		assertTrue(t0.getOutputEventOccurrences().size() == 2);

		final EventOccurrence eo1 = t0.getOutputEventOccurrences().get(0);
		assertTrue("EO1".equals(eo1.getEvent().getName())); //$NON-NLS-1$
		assertTrue("E_SPLIT".equals(eo1.getParentFB().getName())); //$NON-NLS-1$

		final EventOccurrence eo2 = t0.getOutputEventOccurrences().get(1);
		assertTrue("EO2".equals(eo2.getEvent().getName())); //$NON-NLS-1$
		assertTrue("E_SPLIT".equals(eo2.getParentFB().getName())); //$NON-NLS-1$

		// transaction 1: first event connection
		final FBTransaction t1 = (FBTransaction) returnedTransactions.get(1);
		assertTrue("EI1".equals(t1.getInputEventOccurrence().getEvent().getName())); //$NON-NLS-1$
		assertTrue("E_REND".equals(t1.getInputEventOccurrence().getParentFB().getName())); //$NON-NLS-1$
		assertTrue(t1.getOutputEventOccurrences().isEmpty());

		// transaction 2: second event connection
		final FBTransaction t2 = (FBTransaction) returnedTransactions.get(2);
		assertTrue("EI2".equals(t2.getInputEventOccurrence().getEvent().getName())); //$NON-NLS-1$
		assertTrue("E_REND".equals(t2.getInputEventOccurrence().getParentFB().getName())); //$NON-NLS-1$
		assertTrue(t2.getOutputEventOccurrences().size() == 1);

		final EventOccurrence eo21 = t2.getOutputEventOccurrences().get(0);
		assertTrue("EO".equals(eo21.getEvent().getName())); //$NON-NLS-1$
		assertTrue("E_REND".equals(eo21.getParentFB().getName())); //$NON-NLS-1$

	}

}
