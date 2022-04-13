/*******************************************************************************
 * Copyright (c) 2020 fortiss GmbH.
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

import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public class ExporterTestAdapterFBType extends ExporterTestBase<AdapterFBType> {

	@Override
	void setupFunctionBlock() {
		// prepare a function block object including an interface list
		functionBlock = LibraryElementFactory.eINSTANCE.createAdapterFBType();
		functionBlock.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());
		functionBlock.setName(ADAPTERFUNCTIONBLOCK_NAME);
		functionBlock.setTypeEntry(preparePaletteWithTypeLib());

		functionBlock.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());

		final Event adpInputEvent = LibraryElementFactory.eINSTANCE.createEvent();
		adpInputEvent.setName(ADAPTER_EVENT_INPUT_NAME);
		adpInputEvent.setIsInput(true);
		functionBlock.getInterfaceList().getEventInputs().add(adpInputEvent);

		final Event adpOutputEvent = LibraryElementFactory.eINSTANCE.createEvent();
		adpOutputEvent.setName(ADAPTER_EVENT_OUTPUT_NAME);
		functionBlock.getInterfaceList().getEventOutputs().add(adpOutputEvent);

		final VarDeclaration adapterInputData = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		adapterInputData.setType(new DataTypeLibrary().getType(FordiacKeywords.INT));
		adapterInputData.setTypeName(FordiacKeywords.INT);
		adapterInputData.setName(ADAPTER_DATA_INPUT_NAME);
		adapterInputData.setIsInput(true);
		final With withInput = LibraryElementFactory.eINSTANCE.createWith();
		adpInputEvent.getWith().add(withInput);
		withInput.setVariables(adapterInputData);
		adapterInputData.getWiths().add(withInput);
		functionBlock.getInterfaceList().getInputVars().add(adapterInputData);

		final VarDeclaration adapterOutputData = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		final With withOutput = LibraryElementFactory.eINSTANCE.createWith();
		adpOutputEvent.getWith().add(withOutput);
		withOutput.setVariables(adapterOutputData);
		adapterOutputData.getWiths().add(withOutput);
		adapterOutputData.setType(new DataTypeLibrary().getType(FordiacKeywords.INT));
		adapterOutputData.setTypeName(FordiacKeywords.INT);
		adapterOutputData.setName(ADAPTER_DATA_OUTPUT_NAME);
		functionBlock.getInterfaceList().getOutputVars().add(adapterOutputData);
	}

}
