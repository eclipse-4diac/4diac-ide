/*******************************************************************************
 * Copyright (c) 2021 fortiss GmbH.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Kirill Dorofeev
 *     - tests for lua exporter
 *******************************************************************************/

package org.eclipse.fordiac.ide.test.export;

import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public class ExporterTestCompositeFBTypeBase extends ExporterTestBase<CompositeFBType> {

	@Override
	void setupFunctionBlock() {
		// prepare a function block object including an interface list
		functionBlock = LibraryElementFactory.eINSTANCE.createCompositeFBType();
		functionBlock.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());
		functionBlock.setName(COMPOSITEFUNCTIONBLOCK_NAME);

		setupAdvancedInterface();

		final FBNetwork fbNetwork = LibraryElementFactory.eINSTANCE.createFBNetwork();
		final EventConnection eventconn = LibraryElementFactory.eINSTANCE.createEventConnection();
		eventconn.setSource(inputEvent);
		eventconn.setDestination(outputEvent);
		fbNetwork.addConnection(eventconn);
		final DataConnection dataconn = LibraryElementFactory.eINSTANCE.createDataConnection();
		dataconn.setSource(inputData);
		dataconn.setDestination(outputData);
		fbNetwork.addConnection(dataconn);
		functionBlock.setFBNetwork(fbNetwork);

		functionBlock.setPaletteEntry(preparePaletteWithTypeLib());
	}

}
