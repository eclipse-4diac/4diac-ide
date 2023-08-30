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
package org.eclipse.fordiac.ide.contracts.model;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public class ModelTest {

	public void test() {
		final SubApp subApp = LibraryElementFactory.eINSTANCE.createSubApp();
		subApp.setComment("ASSUMPTION VV occurs within [1,2]ms   \nGUARANTEE Reaction(VV,BB) within [6,39]ms \n"); //$NON-NLS-1$
		subApp.setComment(
				"ASSUMPTION CD occurs every 7ms with [8,9]ms offset\n GUARANTEE Reaction(EI1,EO1) within [1,1]ms  ConstractState TRUE \n"); //$NON-NLS-1$
		subApp.setName("_CONTRACT_E"); //$NON-NLS-1$
		final Contract contract = Contract.getContractFromComment(subApp.getComment());
		contract.setOwner(subApp);

		assert (contract.getAssumptions().get(0).getMin() == 1);
		assert (contract.getAssumptions().get(0).getMax() == 2);
		assert (((AssumptionWithOffset) contract.getAssumptions().get(1)).getMinOffset() == 8);
		assert (((AssumptionWithOffset) contract.getAssumptions().get(1)).getMaxOffset() == 9);
	}

}
