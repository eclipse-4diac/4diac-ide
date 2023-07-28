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

import static org.eclipse.fordiac.ide.fb.interpreter.mm.FBNetworkTestRunner.runFBNetworkTest;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.test.fb.interpreter.infra.AbstractInterpreterTest;

/** small fb network consisting of e_sr, e_switch, and e_ctud */
/*
 * @deprecated This test case will be added in the ReferenceExamplesTest
 */
@Deprecated
public class ExampleFbNetworkTest extends AbstractInterpreterTest {

	@Override
	public void test() {
		final FBNetwork network = loadFbNetwork("ExampleFbNetwork", "ExampleFbNetwork"); //$NON-NLS-1$ //$NON-NLS-2$
		assertNotNull(network);

		final EList<Transaction> returnedTransactions = runFBNetworkTest(network, "E_SR", "S"); //$NON-NLS-1$ //$NON-NLS-2$

		assertTrue(returnedTransactions.size() == 3);

		// transaction 0: initial trigger
		final FBTransaction t0 = (FBTransaction) returnedTransactions.get(0);
		assertTrue("S".equals(t0.getInputEventOccurrence().getEvent().getName())); //$NON-NLS-1$
		assertTrue("E_SR".equals(t0.getInputEventOccurrence().getParentFB().getName())); //$NON-NLS-1$

		final VarDeclaration varQ = (VarDeclaration) t0.getInputEventOccurrence().getParentFB()
				.getInterfaceElement("Q"); //$NON-NLS-1$
		assertTrue("TRUE".equals(varQ.getValue().getValue())); //$NON-NLS-1$

		// transaction 1: connected block
		final FBTransaction t1 = (FBTransaction) returnedTransactions.get(1);
		assertTrue("EI".equals(t1.getInputEventOccurrence().getEvent().getName())); //$NON-NLS-1$
		assertTrue("EO1".equals(t1.getOutputEventOccurrences().get(0).getEvent().getName())); //$NON-NLS-1$
		assertTrue(t1.getOutputEventOccurrences().size() == 1);

		// transaction 2: final block
		final FBTransaction t2 = (FBTransaction) returnedTransactions.get(2);
		assertTrue("CD".equals(t2.getInputEventOccurrence().getEvent().getName())); //$NON-NLS-1$
		assertTrue(t2.getOutputEventOccurrences().isEmpty());

	}
}
