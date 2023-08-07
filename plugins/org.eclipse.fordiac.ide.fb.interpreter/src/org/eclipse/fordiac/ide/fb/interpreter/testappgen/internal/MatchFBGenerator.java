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

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Primitive;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;

public class MatchFBGenerator extends AbstractFBGenerator{

	
	public MatchFBGenerator(FBType type,  TestSuite testSuite) {
		super(type, testSuite);
	}
	
	public BasicFBType generateMatchFB() {
		createBasicFB();
		 return destinationFB;
	}
	
	@Override
	protected String getTypeName() {
		return sourceType.getName() + "_MATCH";
	}
	
	@Override
	protected FBType getSourceFB() {
		return sourceType;
	}

	@Override
	protected void generateECC() {
		TestEccGenerator eccGen = new TestEccGenerator(destinationFB.getECC(), 0);
		
		for (Event event : sourceType.getInterfaceList().getEventInputs()) {
			// create State + Transition
			eccGen.createState(event.getName() + "_wait", 1, event);
			eccGen.createTransitionFromTo(
					eccGen.getEcc().getECState().get(0),
					eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size()-1),
					event, 0);

			// add Action + Algorithm
			ECAction action = eccGen.createAction(null);
			action.setAlgorithm(eccGen.createAlgorithm(event.getName() + "_wait"));
			eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size()-1).getECAction().add(action);
		

			Event event4 =  LibraryElementFactory.eINSTANCE.createEvent();
			for(TestCase testCase : testSuite.getTestCases()) {
				if(testCase.getTestStates().get(0).getTestTrigger().getEvent().equals(event.getName())) {
					event4 = destinationFB.getInterfaceList().getEvent(testCase.getTestStates().get(0).getTestOutputs().get(0).getEvent());
				}
			}
			
			// match state
			eccGen.createState(event.getName() + "_match", 2, event);
	
			eccGen.createTransitionFromTo(eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size()-2), 
					eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size()-1), 
					event4, 0);

			eccGen.increaseCaseCount();
			ECAction action2 = eccGen.createAction(null);
			action2.setAlgorithm(eccGen.createAlgorithm(event.getName() + "_match"));
			action2.setOutput(destinationFB.getInterfaceList().getEventOutputs().get(1));
			eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size()-1).getECAction().add(action2);
			
			// ErrorState + Transitions to ErrorState
			eccGen.createState("ERROR_" + event.getName(), 2, event);
			for (Event errorEvent : inputEventList) {
				if(!errorEvent.getName().equals(event4.getName()))
				eccGen.createTransitionFromTo(
						eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size()-3), 
						eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size()-1), 
						errorEvent, 0);
			}
			eccGen.createTransitionFromTo(
					eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size()-1), 
					eccGen.getEcc().getECState().get(0),
					null, 0);
			ECAction action3 = eccGen.createAction(null);
			action3.setAlgorithm(eccGen.createAlgorithm(event.getName() + "_error"));
			action3.setOutput(destinationFB.getInterfaceList().getEventOutputs().get(0));
			eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size()-1).getECAction().add(action3);
				
			eccGen.increaseCaseCount();
			
		}
	}
	
	@Override
	protected List<Event> createInputEventList() {
		List<Event> list = new ArrayList<Event>();
		for (Event event : sourceType.getInterfaceList().getEventInputs()) {
			Event outEvent = LibraryElementFactory.eINSTANCE.createEvent();
			outEvent.setIsInput(true);
			outEvent.setName(event.getName());
			outEvent.setType(event.getType());
			list.add(outEvent);
		}
		for (Event event : sourceType.getInterfaceList().getEventOutputs()) {
			Event inEvent = LibraryElementFactory.eINSTANCE.createEvent();
			inEvent.setIsInput(true);
			inEvent.setName(event.getName());
			inEvent.setType(event.getType());
			list.add(inEvent);
		}
		inputEventList = list;
		return list;
	}

	@Override
	protected List<Event> createOutputEventList() {
		List<Event> list = new ArrayList<Event>();
		Event errorEvent = LibraryElementFactory.eINSTANCE.createEvent();
		errorEvent.setIsInput(false);
		errorEvent.setType(EventTypeLibrary.getInstance().getType(EventTypeLibrary.EVENT));
		errorEvent.setName("ERROR");
		list.add(errorEvent);
		
		Event successEvent = LibraryElementFactory.eINSTANCE.createEvent();
		successEvent.setIsInput(false);
		successEvent.setType(EventTypeLibrary.getInstance().getType(EventTypeLibrary.EVENT));
		successEvent.setName("SUCCESS");
		list.add(successEvent);
		outputEventList = list;
		return list;
	}

	@Override
	protected List<VarDeclaration> createInputDataList() {
		List<VarDeclaration> list = new ArrayList<VarDeclaration>();
		for (VarDeclaration varDeclaration : sourceType.getInterfaceList().getOutputVars()) {
			
			VarDeclaration varDecl = EcoreUtil.copy(varDeclaration);
			varDecl.setName(varDeclaration.getName() + "_expected");
			varDecl.setIsInput(true);
			list.add(varDecl);
			
			varDecl = EcoreUtil.copy(varDeclaration);
			varDecl.setName(varDeclaration.getName() + "_received");
			varDecl.setIsInput(true);
			list.add(varDecl);
		}
		return list;
	}

	@Override
	protected List<VarDeclaration> createOutputDataList() {
		List<VarDeclaration> list = new ArrayList<VarDeclaration>();
		for (VarDeclaration varDeclaration : sourceType.getInterfaceList().getOutputVars()) {
			
			varDeclaration.setName(varDeclaration.getName() + "_matchData");
			varDeclaration.setIsInput(false);
			varDeclaration.setType(sourceType.getTypeLibrary().getDataTypeLibrary().getType(FordiacKeywords.BOOL));
			varDeclaration.setComment("");
			list.add(varDeclaration);
		}
		return list;
	}
}
