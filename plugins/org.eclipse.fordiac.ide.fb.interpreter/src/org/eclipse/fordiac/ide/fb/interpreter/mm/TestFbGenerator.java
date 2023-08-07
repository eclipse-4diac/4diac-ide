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
 * Bianca Wiesmayr - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter.mm;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.AbstractFBGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.TestCase;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.TestEccGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.TestState;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.TestSuite;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;

public class TestFbGenerator extends AbstractFBGenerator {

	public TestFbGenerator(final FBType type, TestSuite testSuite) {
		super(type, testSuite);
	}

	public BasicFBType generateTestFb() {
		createBasicFB();
		return destinationFB;
	}

	@Override
	protected List<Event> createInputEventList() {
		List<Event> list = new ArrayList<Event>();
		for (TestCase testCase : testSuite.getTestCases()) {
			Event event = LibraryElementFactory.eINSTANCE.createEvent();
			event.setIsInput(true);
			event.setType(EventTypeLibrary.getInstance().getType(EventTypeLibrary.EVENT));
			event.setName(testCase.getName() + "_TEST");
			list.add(event);
		}

		inputEventList = list;
		return list;
	}

	@Override
	protected List<Event> createOutputEventList() {
		List<Event> list = new ArrayList<Event>();
		for (Event event : sourceType.getInterfaceList().getEventInputs()) {
			Event outEvent = LibraryElementFactory.eINSTANCE.createEvent();
			outEvent.setIsInput(false);
			outEvent.setName(event.getName());
			outEvent.setType(event.getType());
			list.add(outEvent);
		}
		
		Event resultEvent = LibraryElementFactory.eINSTANCE.createEvent();
		resultEvent.setIsInput(false);
		resultEvent.setType(EventTypeLibrary.getInstance().getType(EventTypeLibrary.EVENT));
		resultEvent.setName("RESULT");
		list.add(resultEvent);
		
		outputEventList = list;
		return list;
	}
	
	@Override
	protected void generateECC() {
		TestEccGenerator eccGen = new TestEccGenerator(destinationFB.getECC(), 0);
		for (TestCase testCase : testSuite.getTestCases()) {
			int stateCnt = 1;
			for (TestState testState : testCase.getTestStates()) {
				eccGen.createState(testCase, stateCnt, inputEventList.get(0));
				if(stateCnt <= 1 ) {
					eccGen.createTransitionFromTo(
							eccGen.getEcc().getECState().get(0),
							eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size()-1),
							inputEventList.get(eccGen.getCaseCount()), stateCnt);

				} else {
					eccGen.createTransitionFromTo(
							eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size()-2),
							eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size()-1),
							inputEventList.get(eccGen.getCaseCount()), stateCnt);
				}
				
				stateCnt++;
			}
			eccGen.createTransitionFromTo(
					eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size()-1),
					eccGen.getEcc().getECState().get(0),
					null, stateCnt);
			
			eccGen.increaseCaseCount();
		}
	}
	
	@Override
	protected String getTypeName() {
		return sourceType.getName() + "_TEST";
	}

	@Override
	protected FBType getSourceFB() {
		return sourceType;
	}

	@Override
	protected List<VarDeclaration> createInputDataList() {
		List<VarDeclaration> list = new ArrayList<VarDeclaration>();
		
		return list;
	}

	@Override
	protected List<VarDeclaration> createOutputDataList() {
		List<VarDeclaration> list = new ArrayList<VarDeclaration>();
		list.addAll(EcoreUtil.copyAll(sourceType.getInterfaceList().getInputVars()));
		list.addAll(EcoreUtil.copyAll(sourceType.getInterfaceList().getOutputVars()));
		for (VarDeclaration varDecl : list) {
			varDecl.setIsInput(false);
		}
		return list;
	}
}
