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

import org.eclipse.fordiac.ide.contracts.model.Assumption;
import org.eclipse.fordiac.ide.contracts.model.Contract;
import org.junit.Test;

public class AssumptionTest {

	@SuppressWarnings("static-method")
	@Test
	public void test() {
		final Contract contract = Contract.getContractFromComment("ASSUMPTION VV occurs within [1,2]ms"); //$NON-NLS-1$
		final Assumption toTest = contract.getAssumptions().get(0);
		assertTrue(toTest.getMin() == 1);
		assertTrue(toTest.getMax() == 2);
		assertTrue("VV".equals(toTest.getInputEvent())); //$NON-NLS-1$
		assertTrue("ASSUMPTION VV occurs within [1,2]ms".equals(toTest.asString())); //$NON-NLS-1$
	}
}
