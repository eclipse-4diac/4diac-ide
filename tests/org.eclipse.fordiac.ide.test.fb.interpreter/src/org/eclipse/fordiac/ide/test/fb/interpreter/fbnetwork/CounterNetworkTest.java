/*******************************************************************************
 * Copyright (c) 2022 Paul Pavlicek and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Pavlicek, Bianca Wiesmayr
 *       - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.fb.interpreter.fbnetwork;

import static org.eclipse.fordiac.ide.fb.interpreter.mm.FBNetworkTestRunner.runFBNetworkTest;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.test.fb.interpreter.infra.AbstractInterpreterTest;

/* small fb network consisting of e_sr, e_switch, and e_ctud */

/*
 * @deprecated This test case will be added in the ReferenceExamplesTest
 */
 
@Deprecated
public class CounterNetworkTest extends AbstractInterpreterTest {

	@Override
	public void test() {
		final FBNetwork network = loadFbNetwork("ExampleFbNetwork", "CounterNetwork"); //$NON-NLS-1$ //$NON-NLS-2$
		assertNotNull(network);

		final EList<Transaction> returnedTransactions = runFBNetworkTest(network, "E_SPLIT", "EI"); //$NON-NLS-1$ //$NON-NLS-2$

		final Transaction finalResult = returnedTransactions.get(returnedTransactions.size() - 1);
		assertTrue("CU".equals(finalResult.getInputEventOccurrence().getEvent().getName())); //$NON-NLS-1$
		final VarDeclaration quPin = (VarDeclaration) finalResult.getInputEventOccurrence().getParentFB()
				.getInterfaceElement("QU"); //$NON-NLS-1$
		assertTrue("TRUE".equals(quPin.getValue().getValue())); //$NON-NLS-1$
		final VarDeclaration cvPin = (VarDeclaration) finalResult.getInputEventOccurrence().getParentFB()
				.getInterfaceElement("CV"); //$NON-NLS-1$
		assertTrue("1".equals(cvPin.getValue().getValue())); //$NON-NLS-1$

	}
}
