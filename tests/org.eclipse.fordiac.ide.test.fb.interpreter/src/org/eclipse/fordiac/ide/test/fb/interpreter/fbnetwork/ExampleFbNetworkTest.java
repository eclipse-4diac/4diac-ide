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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.fb.interpreter.mm.FBNetworkTestRunner;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.test.fb.interpreter.infra.AbstractInterpreterTest;

/** small fb network consisting of e_sr, e_switch, and e_ctud */
public class ExampleFbNetworkTest extends AbstractInterpreterTest {

	private static final String EXECUTED_FB_1 = "E_SR"; //$NON-NLS-1$

	@Override
	public void test() {
		final FBNetwork network = loadFbNetwork("ExampleFbNetwork", "ExampleFbNetwork"); //$NON-NLS-1$ //$NON-NLS-2$
		assertNotNull(network);

		final EList<Transaction> returnedTransactions = FBNetworkTestRunner
				.runFBNetworkTestManager(network, EXECUTED_FB_1, "S").getTransactions(); //$NON-NLS-1$

		assertEquals(3, returnedTransactions.size());

		// transaction 0: initial trigger
		final FBTransaction t0 = (FBTransaction) returnedTransactions.get(0);
		assertEquals("S", t0.getInputEventOccurrence().getEvent().getName()); //$NON-NLS-1$
		assertEquals(EXECUTED_FB_1, t0.getInputEventOccurrence().getParentFB().getName());

		final FBNetwork fbNetwork = (FBNetwork) t0.getInputEventOccurrence().getResultFBRuntime().getModel();
		final VarDeclaration varQ = (VarDeclaration) fbNetwork.getFBNamed(EXECUTED_FB_1).getInterfaceElement("Q"); //$NON-NLS-1$
		assertEquals("TRUE", varQ.getValue().getValue()); //$NON-NLS-1$

		// transaction 1: connected block
		final FBTransaction t1 = (FBTransaction) returnedTransactions.get(1);
		assertEquals("EI", t1.getInputEventOccurrence().getEvent().getName()); //$NON-NLS-1$
		assertEquals("EO1", t1.getOutputEventOccurrences().get(0).getEvent().getName()); //$NON-NLS-1$
		assertEquals(1, t1.getOutputEventOccurrences().size());

		// transaction 2: final block
		final FBTransaction t2 = (FBTransaction) returnedTransactions.get(2);
		assertEquals("CD", t2.getInputEventOccurrence().getEvent().getName()); //$NON-NLS-1$
		assertTrue(t2.getOutputEventOccurrences().isEmpty());
	}
}
