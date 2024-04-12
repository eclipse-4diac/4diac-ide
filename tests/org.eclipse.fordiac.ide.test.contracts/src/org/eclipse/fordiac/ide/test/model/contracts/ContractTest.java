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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.contracts.model.Contract;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.junit.jupiter.api.Test;

class ContractTest {

	@SuppressWarnings("static-method")
	@Test
	void testValid() {
		final Contract contract = Contract
				.getContractFromComment("ASSUMPTION CU occurs every [11,22]ms" + System.lineSeparator() //$NON-NLS-1$
						+ "GUARANTEE Whenever event CU occurs, then event CUO occurs within [7,10]ms"); //$NON-NLS-1$
		final UntypedSubApp subApp = LibraryElementFactory.eINSTANCE.createUntypedSubApp();
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
		assertTrue(contract.isValid());

	}

	@SuppressWarnings("static-method")
	@Test
	void testNotValid() {
		final Contract contract = Contract
				.getContractFromComment("ASSUMPTION CU occurs every [11,22]ms" + System.lineSeparator() //$NON-NLS-1$
						+ "GUARANTEE Whenever event CU occurs, then event CUO occurs within [7,10]ms"); //$NON-NLS-1$
		final UntypedSubApp subApp = LibraryElementFactory.eINSTANCE.createUntypedSubApp();
		subApp.setName("_CONTRACT_Test"); //$NON-NLS-1$
		contract.setOwner(subApp);
		final InterfaceList iList = LibraryElementFactory.eINSTANCE.createInterfaceList();
		subApp.setInterface(iList);
		final Event input = LibraryElementFactory.eINSTANCE.createEvent();
		input.setName("CD"); //$NON-NLS-1$
		subApp.getInterface().getEventInputs().add(input);
		final Event output = LibraryElementFactory.eINSTANCE.createEvent();
		output.setName("CUO"); //$NON-NLS-1$
		subApp.getInterface().getEventOutputs().add(output);
		subApp.setSubAppNetwork(LibraryElementFactory.eINSTANCE.createFBNetwork());
		subApp.getSubAppNetwork().getNetworkElements().add(EcoreUtil.copy(subApp));
		assertFalse(contract.isValid());
	}

}
