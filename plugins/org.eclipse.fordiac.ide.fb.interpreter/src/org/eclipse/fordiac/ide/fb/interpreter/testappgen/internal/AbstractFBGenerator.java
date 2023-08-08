/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Melanie Winter - initial API and implementation and/or initial documentation
 *******************************************************************************/


package org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.Identification;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;

public abstract class AbstractFBGenerator {
	
	protected BasicFBType destinationFB; 
	protected TestSuite testSuite;
	protected TypeEntry entry;
	protected FBType sourceType;
	
	protected List<Event> inputEventList;
	protected List<Event> outputEventList;
	
	protected AbstractFBGenerator(FBType type, TestSuite testSuite) {
		this.sourceType = type;
		this.testSuite = testSuite;
	}
	
	protected void createBasicFB() {
		destinationFB = LibraryElementFactory.eINSTANCE.createBasicFBType();
		destinationFB.setECC(LibraryElementFactory.eINSTANCE.createECC());
		final ECState start = LibraryElementFactory.eINSTANCE.createECState();
		destinationFB.getECC().getECState().add(start);
		start.setName("START"); //$NON-NLS-1$
		destinationFB.getECC().setStart(start);
		final Position p0 = LibraryElementFactory.eINSTANCE.createPosition();
		p0.setX(0);
		p0.setY(0);
		start.setPosition(p0);
		final Identification id = LibraryElementFactory.eINSTANCE.createIdentification();
		destinationFB.setIdentification(id);
		id.setStandard("IEC 61499"); //$NON-NLS-1$

		destinationFB.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());
		destinationFB.setName(getTypeName()); //$NON-NLS-1$
		
		
		destinationFB.setService(LibraryElementFactory.eINSTANCE.createService());

		final IProject project = getSourceFB().getTypeLibrary().getProject();
		final IFolder folder = project.getFolder("Type Library"); //$NON-NLS-1$
		final IFile destfile = folder.getFile(getTypeName() + ".fbt");  //$NON-NLS-1$
		
		entry  = getSourceFB().getTypeLibrary().createTypeEntry(destfile);
		entry.setType(destinationFB);
		
		addEvents();
		addDataPins();
		createMiths();
		generateECC();
	}
	
	private void createMiths() {		
		for (Event input : destinationFB.getInterfaceList().getEventInputs()) {
			for (VarDeclaration varD : destinationFB.getInterfaceList().getInputVars()) {
				With w = LibraryElementFactory.eINSTANCE.createWith();
				w.setVariables(varD);
				input.getWith().add(w);
			}
		}
		
		for (Event output : destinationFB.getInterfaceList().getEventOutputs()) {
			for (VarDeclaration varD : destinationFB.getInterfaceList().getOutputVars()) {
				With w = LibraryElementFactory.eINSTANCE.createWith();
				w.setVariables(varD);
				output.getWith().add(w);
			}
		}
	}

	protected abstract List<Event> createInputEventList();
	protected abstract List<Event> createOutputEventList();
	protected abstract List<VarDeclaration> createInputDataList();
	protected abstract List<VarDeclaration> createOutputDataList();

	protected void addEvents() {
		
		destinationFB.getInterfaceList().getEventInputs().addAll(createInputEventList());
		destinationFB.getInterfaceList().getEventOutputs().addAll(createOutputEventList());
		
	}
	
	protected void addDataPins() {
		destinationFB.getInterfaceList().getInputVars().addAll(createInputDataList());
		destinationFB.getInterfaceList().getOutputVars().addAll(createOutputDataList());
	}
	
	protected abstract void generateECC();
	protected abstract String getTypeName();
	protected abstract FBType getSourceFB();
	
	protected List<Event> getExpectedEvents(boolean isInput){
		List<Event> list = new ArrayList<>();
		for (TestCase testCase : testSuite.getTestCases()) {
			for(TestState testState : testCase.getTestStates()) {
				if(testState.getTestOutputs().size() == 1) {
					if(!containsEvent(list, testState.getTestOutputs().get(0).getEvent() + "_expected")){						
						list.add(createEvent(testState.getTestOutputs().get(0).getEvent() + "_expected", isInput));
					}
				}
				else if (testState.getTestOutputs().size() > 1) {
					String name = createEventName(testState.getTestOutputs());
					if(!containsEvent(list, name)) {
						list.add(createEvent(name, isInput));
					}
				}
			}
		}
		return list;
	}
	
	private String createEventName(List<OutputPrimitive> testOutputs) {
		StringBuilder sb = new StringBuilder();
		for (OutputPrimitive outP : testOutputs) {
			sb.append(outP.getEvent() + "_");
		}
		sb.append("expected");
		return sb.toString();
	}

	protected Event createEvent(String name, boolean isInput) {
		Event newEv = LibraryElementFactory.eINSTANCE.createEvent();
		newEv.setIsInput(isInput);
		newEv.setType(EventTypeLibrary.getInstance().getType(EventTypeLibrary.EVENT));
		newEv.setName(name);
		return newEv;
	}
	
	private boolean containsEvent(List<Event> list, String name) {
		boolean retBool = false;
		for(Event ev : list) {	
			if(ev.getName().equals(name)) {
				retBool = true;
			}
		}
		return retBool;
	}
}
