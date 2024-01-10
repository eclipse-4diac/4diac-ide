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
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.contracts.model.Contract;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.junit.jupiter.api.Test;

class SimplifyGuaranteeTest {
	@SuppressWarnings("static-method")
	@Test
	void test() {
		final Contract contract = Contract.getContractFromComment(
				"GUARANTEE Whenever event CU occurs, then event CUO occurs within [7,100]ms" + System.lineSeparator() //$NON-NLS-1$
						+ "GUARANTEE Whenever event CU occurs, then event CUO occurs within [50,90]ms"); //$NON-NLS-1$
		final SubApp subApp = LibraryElementFactory.eINSTANCE.createSubApp();
		subApp.setName("_CONTRACT_Test"); //$NON-NLS-1$
		contract.setOwner(subApp);
		final InterfaceList iList = LibraryElementFactory.eINSTANCE.createInterfaceList();
		subApp.setInterface(iList);
		final Event input = LibraryElementFactory.eINSTANCE.createEvent();
		input.setName("CU"); //$NON-NLS-1$
		subApp.getInterface().getEventInputs().add(input);
		final Event output = LibraryElementFactory.eINSTANCE.createEvent();
		output.setName("CUO"); //$NON-NLS-1$
		subApp.getInterface().getEventOutputs().add(output);
		subApp.setSubAppNetwork(LibraryElementFactory.eINSTANCE.createFBNetwork());
		subApp.getSubAppNetwork().getNetworkElements().add(EcoreUtil.copy(subApp));
		assertFalse(contract.isValid());
		assertEquals(1, contract.getGuarantees().size());
		assertEquals(
				("GUARANTEE Whenever event CU occurs, then event CUO occurs within [50,90]ms" + System.lineSeparator()) //$NON-NLS-1$
				, (contract.getGuarantees().get(0).asString()));

	}

}
