/*******************************************************************************
 * Copyright (c) 2023 Paul Pavlicek
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Pavlicek
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.contracts.tests;

import static org.junit.Assert.assertTrue;

import org.eclipse.fordiac.ide.contracts.model.Contract;
import org.eclipse.fordiac.ide.contracts.model.Guarantee;
import org.junit.Test;

public class GuaranteeTest {

	private static final int MAX = 100;
	private static final int MIN = 7;

	@SuppressWarnings("static-method")
	@Test
	public void test() {
		// Time and Event extraction and String reproduction test start
		Contract contract = Contract
				.getContractFromComment("GUARANTEE Whenever event CU occurs, then event CUO occurs within [7,100]ms"); //$NON-NLS-1$
		Guarantee toTest = contract.getGuarantees().get(0);
		assertTrue(toTest.getMin() == MIN);
		assertTrue(toTest.getMax() == MAX);
		assertTrue("CU".equals(toTest.getInputEvent())); //$NON-NLS-1$
		assertTrue("CUO".equals(toTest.getOutputEvent())); //$NON-NLS-1$
		assertTrue(
				("GUARANTEE Whenever event CU occurs, then event CUO occurs within [7,100]ms" + System.lineSeparator()) //$NON-NLS-1$
						.equals(toTest.asString()));

		contract = Contract
				.getContractFromComment("GUARANTEE Whenever event CU occurs, then event CUO occurs within 100ms"); //$NON-NLS-1$
		toTest = contract.getGuarantees().get(0);
		assertTrue(toTest.getMin() == 0);
		assertTrue(toTest.getMax() == MAX);
		assertTrue("CU".equals(toTest.getInputEvent())); //$NON-NLS-1$
		assertTrue("CUO".equals(toTest.getOutputEvent())); //$NON-NLS-1$
		assertTrue(("GUARANTEE Whenever event CU occurs, then event CUO occurs within 100ms" + System.lineSeparator()) //$NON-NLS-1$
				.equals(toTest.asString()));
		// end Time and Event extraction and String reproduction test
	}

}
