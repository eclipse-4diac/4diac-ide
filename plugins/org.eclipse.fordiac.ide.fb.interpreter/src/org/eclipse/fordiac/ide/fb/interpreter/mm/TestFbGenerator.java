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
import org.eclipse.fordiac.ide.model.NameRepository;
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
			addEccPath(eccGen, testCase);
			eccGen.increaseStateCount();
		}
	}
	
	private void addEccPath(TestEccGenerator eccGen, TestCase testCase) {
		int stateCnt = 1;
		for (TestState testState : testCase.getTestStates()) {
			eccGen.createState(testCase, stateCnt, inputEventList.get(0));
			eccGen.createTransition(inputEventList.get(0), stateCnt);
			stateCnt++;
		}
	}

//	private void createFBInOutputs() {
//		final Event resultEvent = LibraryElementFactory.eINSTANCE.createEvent();
//		resultEvent.setIsInput(false);
//		destinationFB.getInterfaceList().getEventOutputs().add(resultEvent);
//		resultEvent.setName("RESULT"); //$NON-NLS-1$
//		resultEvent.setType(EventTypeLibrary.getInstance().getType(EventTypeLibrary.EVENT));
//		resultEvent.setComment("Test sequence complete"); //$NON-NLS-1$
//		outputs.add(resultEvent);
//		int caseCount = 0;
//		for (TestCase testCase : testSuite.getTestCases()) {
//			createInOutputEvents(testCase, caseCount);
//			caseCount++;
//		}
//		createDataOutputs();
//	}
//
//	private void createInOutputEvents(final TestCase testCase, int caseCount) {
//		input = createFbInputEvent(testCase);
//		outputs = createFbOutputEvents(input.getName());
//		addEccPath(testCase, caseCount, input, outputs );
//	}
		
	private Event createFbInputEvent(final TestCase testCase) {
		final Event startEvent = LibraryElementFactory.eINSTANCE.createEvent();
		startEvent.setIsInput(true);
		startEvent.setType(EventTypeLibrary.getInstance().getType(EventTypeLibrary.EVENT));
		destinationFB.getInterfaceList().getEventInputs().add(startEvent);
		if (NameRepository.isValidName(startEvent, testCase.getName())) {
			startEvent.setName(NameRepository.createUniqueName(startEvent, testCase.getName() + "_TEST")); //$NON-NLS-1$
		} else {
			startEvent.setName(NameRepository.createUniqueName(startEvent, "Test1"));//$NON-NLS-1$
		}
		return startEvent;
	}

	private List<Event> createFbOutputEvents(final String testCaseName) {
		final Event triggerEvent = LibraryElementFactory.eINSTANCE.createEvent();
		triggerEvent.setIsInput(false);
		destinationFB.getInterfaceList().getEventOutputs().add(triggerEvent);
		triggerEvent.setName(NameRepository.createUniqueName(triggerEvent, testCaseName + "_TRIG")); //$NON-NLS-1$
		triggerEvent.setComment("Starts the test sequence"); //$NON-NLS-1$
		triggerEvent.setType(EventTypeLibrary.getInstance().getType(EventTypeLibrary.EVENT));


		final ArrayList<Event> created = new ArrayList<>();
		created.add(triggerEvent);
//		created.add(completionEvent);
		return created;
	}

	private void createDataOutputs() {
		destinationFB.getInterfaceList().getOutputVars().addAll(EcoreUtil.copyAll(sourceType.getInterfaceList().getInputVars()));
		destinationFB.getInterfaceList().getOutputVars().addAll(EcoreUtil.copyAll(sourceType.getInterfaceList().getOutputVars()));
		for (VarDeclaration varDecl : destinationFB.getInterfaceList().getOutputVars()) {
			varDecl.setIsInput(false);
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

		
//	final Event completionEvent = LibraryElementFactory.eINSTANCE.createEvent();
//	completionEvent.setIsInput(false);
//	destinationType.getInterfaceList().getEventOutputs().add(completionEvent);
//	completionEvent.setName(NameRepository.createUniqueName(triggerEvent, testCaseName + "_COMPLETE")); //$NON-NLS-1$
//	completionEvent.setType(EventTypeLibrary.getInstance().getType(EventTypeLibrary.EVENT));
//	completionEvent.setComment("Test sequence complete"); //$NON-NLS-1$
//
		
		//private BasicFBType createDestinationType() {
//		destinationType = LibraryElementFactory.eINSTANCE.createBasicFBType();
//		destinationType.setECC(LibraryElementFactory.eINSTANCE.createECC());
//		final ECState start = LibraryElementFactory.eINSTANCE.createECState();
//		destinationType.getECC().getECState().add(start);
//		start.setName("START"); //$NON-NLS-1$
//		destinationType.getECC().setStart(start);
//		final Position p0 = LibraryElementFactory.eINSTANCE.createPosition();
//		p0.setX(0);
//		p0.setY(0);
//		start.setPosition(p0);
//		final Identification id = LibraryElementFactory.eINSTANCE.createIdentification();
//		destinationType.setIdentification(id);
//		id.setStandard("IEC 61499"); //$NON-NLS-1$
//
//		destinationType.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());
//		destinationType.setName(sourceType.getName() + "_TEST"); //$NON-NLS-1$
//		
//		
//		destinationType.setService(LibraryElementFactory.eINSTANCE.createService());
//
//		final IProject project = sourceType.getTypeLibrary().getProject();
//		final IFolder folder = project.getFolder("Type Library"); //$NON-NLS-1$
//		final IFile destfile = folder.getFile(sourceType.getName() + "_TEST.fbt");  //$NON-NLS-1$
//
//		destinationEntry = sourceType.getTypeLibrary().createTypeEntry(destfile);
//		destinationEntry.setType(destinationType);
//
//		return destinationType;
	//}

//		final ECTransition transition = LibraryElementFactory.eINSTANCE.createECTransition();
//		transition.setConditionEvent(input);
//		transition.setSource(ecc.getStart());
//		transition.setDestination(state);
//		ecc.getECTransition().add(transition);
//		final Position pT1 = LibraryElementFactory.eINSTANCE.createPosition();
//		pT1.setX(100 * ecc.getECState().size());
//		pT1.setY(100 * ecc.getECState().size());
//		transition.setPosition(pT1);
//
//		final ECTransition transitionBack = LibraryElementFactory.eINSTANCE.createECTransition();
//		transitionBack.setConditionEvent(null);
//		transitionBack.setConditionExpression("1"); //$NON-NLS-1$
//		transitionBack.setSource(state);
//		transitionBack.setDestination(ecc.getStart());
//		ecc.getECTransition().add(transitionBack);
//		final Position pT2 = LibraryElementFactory.eINSTANCE.createPosition();
//		pT2.setX(100 * ecc.getECState().size());
//		pT2.setY(100 * ecc.getECState().size());
//		transitionBack.setPosition(pT2);

		// TODO support test sequences with multiple events
	
}
