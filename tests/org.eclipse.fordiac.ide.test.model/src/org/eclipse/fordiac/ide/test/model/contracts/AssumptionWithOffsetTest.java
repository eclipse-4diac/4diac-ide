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
package org.eclipse.fordiac.ide.test.model.contracts;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.fordiac.ide.contracts.model.AssumptionWithOffset;
import org.eclipse.fordiac.ide.contracts.model.Contract;

class AssumptionWithOffsetTest {
	private static final int MAX_OFFSET = 20;
	private static final int MIN_OFFSET = 8;
	private static final int MIN_TIME = 7;

	@SuppressWarnings("static-method")
	void test() {	// Test runs if AssumptionWithOffest constructor throws Exception gets //
		// Time and Event extraction and String reproduction test start
		Contract contract = Contract.getContractFromComment("ASSUMPTION CU occurs every 7ms with 8ms offset"); //$NON-NLS-1$
		AssumptionWithOffset toTest = (AssumptionWithOffset) contract.getAssumptions().get(0);
		assertEquals(MIN_TIME, toTest.getMin());
		assertEquals(MIN_TIME, toTest.getMax());
		assertEquals(MIN_OFFSET, toTest.getMinOffset());
		assertEquals(MIN_OFFSET, toTest.getMaxOffset());
		assertEquals("CU", toTest.getInputEvent()); //$NON-NLS-1$
		assertEquals(("ASSUMPTION CU occurs every 7ms with 8ms offset" + System.lineSeparator()), toTest.asString()); //$NON-NLS-1$

		contract = Contract.getContractFromComment("ASSUMPTION CU occurs every [1,2]ms with [8,20]ms offset"); //$NON-NLS-1$
		toTest = (AssumptionWithOffset) contract.getAssumptions().get(0);
		assertEquals(1, toTest.getMin());
		assertEquals(2, toTest.getMax());
		assertEquals(MIN_OFFSET, toTest.getMinOffset());
		assertEquals(MAX_OFFSET, toTest.getMaxOffset());
		assertEquals("CU", toTest.getInputEvent()); //$NON-NLS-1$
		assertEquals(("ASSUMPTION CU occurs every [1,2]ms with [8,20]ms offset" + System.lineSeparator()) //$NON-NLS-1$
				, toTest.asString());

		// end Time and Event extraction and String reproduction test

	}

}
