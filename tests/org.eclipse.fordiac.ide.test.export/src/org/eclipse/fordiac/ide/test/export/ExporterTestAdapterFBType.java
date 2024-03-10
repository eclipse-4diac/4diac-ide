/*******************************************************************************
 * Copyright (c) 2020, 2024 fortiss GmbH.
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
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.impl.AdapterTypeEntryImpl;
import org.eclipse.fordiac.ide.test.model.typelibrary.FBTypeEntryMock;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public class ExporterTestAdapterFBType extends ExporterTestBase<AdapterType> {

	@Override
	void setupFunctionBlock() {
		// prepare a function block object including an interface list
		functionBlock = LibraryElementFactory.eINSTANCE.createAdapterType();
		functionBlock.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());
		functionBlock.setName(ADAPTERFUNCTIONBLOCK_NAME);

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
		adapterOutputData.setName(ADAPTER_DATA_OUTPUT_NAME);
		functionBlock.getInterfaceList().getOutputVars().add(adapterOutputData);

		prepareAdapterTypeEntryWithTypeLib().setType(functionBlock);
	}

	private static FBTypeEntry prepareAdapterTypeEntryWithTypeLib() {
		return new FBTypeEntryMock(null, TypeLibraryManager.INSTANCE.getTypeLibrary(null), null) {
			@Override
			public synchronized void setType(final LibraryElement type) {
				if (type instanceof final AdapterType adpType) {
					// wenn we get a new type ensure that the plug is the mirror of the socket
					adpType.setPlugType(AdapterTypeEntryImpl.createPlugType(adpType));
				}
				super.setType(type);
			}
		};
	}

}
