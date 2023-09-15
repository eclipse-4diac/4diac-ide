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

import org.eclipse.fordiac.ide.contracts.model.AssumptionWithOffset;
import org.eclipse.fordiac.ide.contracts.model.Contract;
import org.junit.Test;

public class AssumptionWithOffsetTest {
	private static final int MAX_OFFSET = 20;
	private static final int MIN_OFFSET = 8;
	private static final int MIN_TIME = 7;

	@SuppressWarnings("static-method")
	@Test
	public void test() {	// Test runs if AssumptionWithOffest constructor throws Exeption gets //
		// Time and Event extraction and String reproduction test start
		Contract contract = Contract.getContractFromComment("ASSUMPTION CU occurs every 7ms with 8ms offset"); //$NON-NLS-1$
		AssumptionWithOffset toTest = (AssumptionWithOffset) contract.getAssumptions().get(0);
		assertTrue(toTest.getMin() == MIN_TIME);
		assertTrue(toTest.getMax() == MIN_TIME);
		assertTrue(toTest.getMinOffset() == MIN_OFFSET);
		assertTrue(toTest.getMaxOffset() == MIN_OFFSET);
		assertTrue("CU".equals(toTest.getInputEvent())); //$NON-NLS-1$
		assertTrue(
				("ASSUMPTION CU occurs every 7ms with 8ms offset" + System.lineSeparator()).equals(toTest.asString())); //$NON-NLS-1$

		contract = Contract.getContractFromComment("ASSUMPTION CU occurs every [1,2]ms with [8,20]ms offset"); //$NON-NLS-1$
		toTest = (AssumptionWithOffset) contract.getAssumptions().get(0);
		assertTrue(toTest.getMin() == 1);
		assertTrue(toTest.getMax() == 2);
		assertTrue(toTest.getMinOffset() == MIN_OFFSET);
		assertTrue(toTest.getMaxOffset() == MAX_OFFSET);
		assertTrue("CU".equals(toTest.getInputEvent())); //$NON-NLS-1$
		assertTrue(("ASSUMPTION CU occurs every [1,2]ms with [8,20]ms offset" + System.lineSeparator()) //$NON-NLS-1$
				.equals(toTest.asString()));

		// end Time and Event extraction and String reproduction test

	}

}
