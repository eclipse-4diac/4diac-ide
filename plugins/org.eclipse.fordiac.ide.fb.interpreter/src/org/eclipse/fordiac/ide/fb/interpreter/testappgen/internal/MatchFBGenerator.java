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
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
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
	protected List<Event> createInputEventList() {
		List<Event> list = new ArrayList<Event>();
		for (Event event : sourceType.getInterfaceList().getEventInputs()) {
			Event outEvent = LibraryElementFactory.eINSTANCE.createEvent();
			outEvent.setIsInput(true);
			outEvent.setName(event.getName());
			outEvent.setType(event.getType());
			list.add(outEvent);
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
	protected void generateECC() {
		TestEccGenerator eccGen = new TestEccGenerator(destinationFB.getECC(), 0);
		for (Event event : inputEventList) {
			// create State + Transition
			eccGen.createState(event.getName(), 1, event);
			eccGen.createTransition(event, 1);
			eccGen.createTransitionBack(event, 0);
			// add Action + Algorithm
			ECAction action = eccGen.createAction(event);
			action.setAlgorithm(eccGen.createAlgorithm(event.getName() + "_match"));
			eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size()-1).getECAction().add(action);

			// ErrorState + Transitions to ErrorState
			eccGen.createState("ERROR_" + event.getName(), 2, event);
			for (Event errorEvent : inputEventList) {
				if(!errorEvent.getName().equals(event.getName()))
				eccGen.createTransitionFromTo(
						eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size()-2), 
						eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size()-1), 
						errorEvent, 0);
			}
						
			eccGen.increaseStateCount();
			eccGen.increaseStateCount();
		}
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
//		VarDeclaration varDecl = EcoreUtil.copy(sourceType.getInterfaceList().getOutputVars().get(1));
//		varDecl.setIsInput(true);
//		list.add(varDecl);
		return list;
	}

	@Override
	protected List<VarDeclaration> createOutputDataList() {
		List<VarDeclaration> list = new ArrayList<VarDeclaration>();
//		VarDeclaration varDecl = EcoreUtil.copy(sourceType.getInterfaceList().getOutputVars().get(0));
//		varDecl.setIsInput(false);
//		list.add(varDecl);
		return list;
	}
}
