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

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.fb.interpreter.mm.FBNetworkTestRunner;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.test.fb.interpreter.infra.AbstractInterpreterTest;

/* small fb network consisting of e_sr, e_switch, and e_ctud */

public class CounterNetworkTest extends AbstractInterpreterTest {

	@Override
	public void test() {
		final FBNetwork network = loadFbNetwork("ExampleFbNetwork", "CounterNetwork"); //$NON-NLS-1$ //$NON-NLS-2$
		assertNotNull(network);

		final EventManager em = FBNetworkTestRunner.runFBNetworkTestManager(network, "E_SPLIT", "EI"); //$NON-NLS-1$ //$NON-NLS-2$
		final var returnedTransactions = em.getTransactions();
		assert (9 == returnedTransactions.size());
		final Transaction finalResult = returnedTransactions.get(returnedTransactions.size() - 1);
		final EventOccurrence finalProcessedEO = finalResult.getInputEventOccurrence();
		final var rt = finalProcessedEO.getResultFBRuntime();
		final var counterFBresult = ((FBNetwork) rt.getModel()).getFBNamed("E_CTUD"); //$NON-NLS-1$
		assertEquals("CU", finalProcessedEO.getEvent().getName()); //$NON-NLS-1$
		final VarDeclaration quPin = (VarDeclaration) (counterFBresult.getInterfaceElement("QU")); //$NON-NLS-1$
		assertEquals("TRUE", quPin.getValue().getValue()); //$NON-NLS-1$
		final VarDeclaration cvPin = (VarDeclaration) counterFBresult.getInterfaceElement("CV"); //$NON-NLS-1$
		assertEquals("2", cvPin.getValue().getValue()); //$NON-NLS-1$
	}
}
