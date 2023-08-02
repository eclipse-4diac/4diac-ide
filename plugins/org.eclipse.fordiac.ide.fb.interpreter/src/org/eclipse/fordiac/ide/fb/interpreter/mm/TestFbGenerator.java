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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.TestCase;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.TestEccGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.TestState;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.TestSuite;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.Identification;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.xtext.common.services.Ecore2XtextTerminalConverters;

public class TestFbGenerator {
	private final FBType sourceType;
	private BasicFBType destinationType;
	private TypeEntry destinationEntry;
	private TestSuite testSuite;

	public TestFbGenerator(final FBType type) {
		this.sourceType = type;
		testSuite = TestSuite.createTestSuite(type);
	}

	public BasicFBType generateTestFb() {
		createDestinationType();
		createFBInOutputs();
		return destinationType;
	}

	private BasicFBType createDestinationType() {
		destinationType = LibraryElementFactory.eINSTANCE.createBasicFBType();
		destinationType.setECC(LibraryElementFactory.eINSTANCE.createECC());
		final ECState start = LibraryElementFactory.eINSTANCE.createECState();
		destinationType.getECC().getECState().add(start);
		start.setName("START"); //$NON-NLS-1$
		destinationType.getECC().setStart(start);
		final Position p0 = LibraryElementFactory.eINSTANCE.createPosition();
		p0.setX(0);
		p0.setY(0);
		start.setPosition(p0);
		final Identification id = LibraryElementFactory.eINSTANCE.createIdentification();
		destinationType.setIdentification(id);
		id.setStandard("IEC 61499"); //$NON-NLS-1$

		destinationType.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());
		destinationType.setName(sourceType.getName() + "_TEST"); //$NON-NLS-1$
		
		
		destinationType.setService(LibraryElementFactory.eINSTANCE.createService());

		final IProject project = sourceType.getTypeLibrary().getProject();
		final IFolder folder = project.getFolder("Type Library"); //$NON-NLS-1$
		final IFile destfile = folder.getFile(sourceType.getName() + "_TEST.fbt");  //$NON-NLS-1$

		destinationEntry = sourceType.getTypeLibrary().createTypeEntry(destfile);
		destinationEntry.setType(destinationType);

		return destinationType;
	}
	
	private void createFBInOutputs() {

		int caseCount = 0;
		for (TestCase testCase : testSuite.getTestCases()) {
			createInOutputEvents(testCase, caseCount);
			caseCount++;
		}
		createDataOutputs();
	}

	private void createInOutputEvents(final TestCase testCase, int caseCount) {
		final Event input = createFbInputEvent(testCase);
		final List<Event> outputs = createFbOutputEvents(input.getName());
		
		final Event resultEvent = LibraryElementFactory.eINSTANCE.createEvent();
		resultEvent.setIsInput(false);
		destinationType.getInterfaceList().getEventOutputs().add(resultEvent);
		resultEvent.setName("RESULT"); //$NON-NLS-1$
		resultEvent.setType(EventTypeLibrary.getInstance().getType(EventTypeLibrary.EVENT));
		resultEvent.setComment("Test sequence complete"); //$NON-NLS-1$
		outputs.add(resultEvent);

		addEccPath(testCase, caseCount, input, outputs );
	}
	
	
	private Event createFbInputEvent(final TestCase testCase) {
		final Event startEvent = LibraryElementFactory.eINSTANCE.createEvent();
		startEvent.setIsInput(true);
		startEvent.setType(EventTypeLibrary.getInstance().getType(EventTypeLibrary.EVENT));
		destinationType.getInterfaceList().getEventInputs().add(startEvent);
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
		destinationType.getInterfaceList().getEventOutputs().add(triggerEvent);
		triggerEvent.setName(NameRepository.createUniqueName(triggerEvent, testCaseName + "_TRIG")); //$NON-NLS-1$
		triggerEvent.setComment("Starts the test sequence"); //$NON-NLS-1$
		triggerEvent.setType(EventTypeLibrary.getInstance().getType(EventTypeLibrary.EVENT));

		final Event completionEvent = LibraryElementFactory.eINSTANCE.createEvent();
		completionEvent.setIsInput(false);
		destinationType.getInterfaceList().getEventOutputs().add(completionEvent);
		completionEvent.setName(NameRepository.createUniqueName(triggerEvent, testCaseName + "_COMPLETE")); //$NON-NLS-1$
		completionEvent.setType(EventTypeLibrary.getInstance().getType(EventTypeLibrary.EVENT));
		completionEvent.setComment("Test sequence complete"); //$NON-NLS-1$

		final ArrayList<Event> created = new ArrayList<>();
		created.add(completionEvent);
		created.add(triggerEvent);
		return created;
	}

	private void createDataOutputs() {
		destinationType.getInterfaceList().getOutputVars().addAll(EcoreUtil.copyAll(sourceType.getInterfaceList().getInputVars()));
		destinationType.getInterfaceList().getOutputVars().addAll(EcoreUtil.copyAll(sourceType.getInterfaceList().getOutputVars()));
		for (VarDeclaration varDecl : destinationType.getInterfaceList().getOutputVars()) {
			varDecl.setIsInput(false);
		}
	}
	

//	private Algorithm createAlgorithm(final ServiceSequence testCase, final String testCaseName) {
//		final TextAlgorithm alg = LibraryElementFactory.eINSTANCE.createSTAlgorithm();
//		destinationType.getCallables().add(alg);
//		alg.setName(NameRepository.createUniqueName(alg, testCaseName));
//
//		final StringBuilder text = new StringBuilder();
//		text.append("ALGORITHM " + testCaseName + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
//		for (final ServiceTransaction t : testCase.getServiceTransaction()) {
//			// add input primitive parameters as statements
//			if (t.getInputPrimitive().getParameters() != null) {
//				text.append(t.getInputPrimitive().getParameters());
//				text.append(";"); //$NON-NLS-1$
//			}
//
//			// add output primitive parameters as statements
//			for (final OutputPrimitive o : t.getOutputPrimitive()) {
//				if (o.getParameters() != null) {
//					text.append(o.getParameters());
//					text.append(";"); //$NON-NLS-1$
//				}
//			}
//		}
//		text.append("\nEND_ALGORITHM"); //$NON-NLS-1$
//		text.append('\0');
//
//		alg.setText(text.toString());
//		return alg;
//	}
//
	
	
	private void addEccPath(final TestCase testCase,int caseCount, final Event input, final List<Event> outputs) {
		
		final ECC ecc = destinationType.getECC();
		TestEccGenerator eccGenerator = new TestEccGenerator(ecc, caseCount);
		
		int stateCnt = 1;
		for (TestState testState : testCase.getTestStates()) {
			eccGenerator.createState(testCase.getName(), stateCnt);
			eccGenerator.createTransition(input, stateCnt);
			stateCnt++;
		}
		
		
		
		
		
		
		//final ECAction action = LibraryElementFactory.eINSTANCE.createECAction();
		//action.setAlgorithm(createAlgorithm(testCase, testCaseName));
		//action.setOutput(outputs.get(0));

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
}
