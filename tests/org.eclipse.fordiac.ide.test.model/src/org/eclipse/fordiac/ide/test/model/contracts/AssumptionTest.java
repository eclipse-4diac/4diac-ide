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

import org.eclipse.fordiac.ide.contracts.model.Assumption;
import org.eclipse.fordiac.ide.contracts.model.Contract;
import org.junit.jupiter.api.Test;

class AssumptionTest {

	@SuppressWarnings("static-method")
	@Test
	void test() {
		// Time and Event extraction and String reproduction test start
		Contract contract = Contract.getContractFromComment("ASSUMPTION VV occurs every [1,2]ms"); //$NON-NLS-1$
		Assumption toTest = contract.getAssumptions().get(0);
		assertEquals(1, toTest.getMin());
		assertEquals(2, toTest.getMax());
		assertEquals("VV", toTest.getInputEvent()); //$NON-NLS-1$
		assertEquals(("ASSUMPTION VV occurs every [1,2]ms" + System.lineSeparator()), toTest.asString()); //$NON-NLS-1$

		contract = Contract.getContractFromComment("ASSUMPTION CC occurs every 2ms"); //$NON-NLS-1$
		toTest = contract.getAssumptions().get(0);
		assertEquals(2, toTest.getMin());
		assertEquals(2, toTest.getMax());
		assertEquals("CC", toTest.getInputEvent()); //$NON-NLS-1$
		assertEquals(("ASSUMPTION CC occurs every 2ms" + System.lineSeparator()), toTest.asString()); //$NON-NLS-1$

		// end Time and Event extraction and String reproduction test
	}
}
