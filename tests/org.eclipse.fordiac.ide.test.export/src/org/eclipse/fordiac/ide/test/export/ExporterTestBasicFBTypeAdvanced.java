package org.eclipse.fordiac.ide.test.export;

import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;

public class ExporterTestBasicFBTypeAdvanced extends ExporterTestBasicFBTypeBase {
	@Override
	void setupFunctionBlock() {
		super.setupFunctionBlock();
		final Event inputEvent = LibraryElementFactory.eINSTANCE.createEvent();
		inputEvent.setName(EVENT_INPUT_NAME);
		functionBlock.getInterfaceList().getEventInputs().add(inputEvent);
		final Event outputEvent = LibraryElementFactory.eINSTANCE.createEvent();
		outputEvent.setName(EVENT_OUTPUT_NAME);
		functionBlock.getInterfaceList().getEventOutputs().add(outputEvent);
		final VarDeclaration inputData = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		inputData.setType(new DataTypeLibrary().getType(FordiacKeywords.INT));
		inputData.setTypeName(FordiacKeywords.INT);
		inputData.setName(DATA_INPUT_NAME);
		functionBlock.getInterfaceList().getInputVars().add(inputData);
		final VarDeclaration outputData = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		outputData.setType(new DataTypeLibrary().getType(FordiacKeywords.INT));
		outputData.setTypeName(FordiacKeywords.INT);
		outputData.setName(DATA_OUTPUT_NAME);
		functionBlock.getInterfaceList().getOutputVars().add(outputData);

		final AdapterDeclaration adapterSocketDecl = LibraryElementFactory.eINSTANCE.createAdapterDeclaration();
		final AdapterType adapterType = LibraryElementFactory.eINSTANCE.createAdapterType();
		final ExporterTestAdapterFBType adapterFBType = new ExporterTestAdapterFBType();
		adapterFBType.setupFunctionBlock();
		adapterType.setAdapterFBType(adapterFBType.getFunctionBlock());
		adapterSocketDecl.setType(adapterType);
		adapterSocketDecl.setName(ADAPTER_SOCKET_NAME);
		adapterSocketDecl.setTypeName(ADAPTERFUNCTIONBLOCK_NAME);
		adapterSocketDecl.setIsInput(true);
		functionBlock.getInterfaceList().getSockets().add(adapterSocketDecl);
		final AdapterDeclaration adapterPlugDecl = LibraryElementFactory.eINSTANCE.createAdapterDeclaration();
		adapterPlugDecl.setType(adapterType);
		adapterPlugDecl.setName(ADAPTER_PLUG_NAME);
		adapterPlugDecl.setTypeName(ADAPTERFUNCTIONBLOCK_NAME);
		functionBlock.getInterfaceList().getPlugs().add(adapterPlugDecl);

		final ECState startState = LibraryElementFactory.eINSTANCE.createECState();
		startState.setName("START"); //$NON-NLS-1$
		functionBlock.getECC().getECState().add(startState);
	}
}
