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

import org.eclipse.fordiac.ide.contracts.model.Contract;
import org.eclipse.fordiac.ide.contracts.model.GuaranteeTwoEvents;

class GuaranteeTwoEventsTest {

	private static final int MAX = 9;
	private static final int MIN = 8;

	@SuppressWarnings("static-method")

	void test() {	// Test runs if GuaranteeTwoEventss constructor throws Exception gets //
		// Time and Event extraction and String reproduction test start
		Contract contract = Contract.getContractFromComment(
				"GUARANTEE Whenever event CD occurs, then events (CDO,LDO) occur within [8,9]ms"); //$NON-NLS-1$
		GuaranteeTwoEvents toTest = (GuaranteeTwoEvents) contract.getGuarantees().get(0);
		assertEquals(MIN, toTest.getMin());
		assertEquals(MAX, toTest.getMax());
		assertEquals("CD", toTest.getInputEvent()); //$NON-NLS-1$
		assertEquals("CDO", toTest.getOutputEvent()); //$NON-NLS-1$
		assertEquals("LDO", toTest.getSecondOutputEvent()); //$NON-NLS-1$
		assertEquals(("GUARANTEE Whenever event CD occurs, then events (CDO,LDO) occur within [8,9]ms" //$NON-NLS-1$
				+ System.lineSeparator()), toTest.asString());

		contract = Contract
				.getContractFromComment("GUARANTEE Whenever event CD occurs, then events (CDO,LDO) occur within 9ms"); //$NON-NLS-1$
		toTest = (GuaranteeTwoEvents) contract.getGuarantees().get(0);
		assertEquals(0, toTest.getMin());
		assertEquals(MAX, toTest.getMax());
		assertEquals("CD", toTest.getInputEvent()); //$NON-NLS-1$
		assertEquals("CDO", toTest.getOutputEvent()); //$NON-NLS-1$
		assertEquals("LDO", toTest.getSecondOutputEvent()); //$NON-NLS-1$
		assertEquals(
				("GUARANTEE Whenever event CD occurs, then events (CDO,LDO) occur within 9ms" + System.lineSeparator()) //$NON-NLS-1$
				, toTest.asString());
		// end Time and Event extraction and String reproduction test
	}
}
