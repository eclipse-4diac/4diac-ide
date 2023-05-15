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
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;

public class TestFbGenerator {
	private final FBType sourceType;
	private final Service service;
	private BasicFBType destinationType;
	private TypeEntry destinationEntry;

	public TestFbGenerator(final FBType type) {
		this.sourceType = type;
		this.service = type.getService();
	}

	public BasicFBType generateTestFb() {
		destinationType = createDestinationType();
		createOutputFile();
		createTestCases();

		return destinationType;
	}

	private void createOutputFile() {
		destinationEntry = null;
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

		final IProject project = sourceType.getTypeLibrary().getProject();
		final IFolder folder = project.getFolder("Type Library"); //$NON-NLS-1$
		final IFile destfile = folder.getFile(sourceType.getName() + "_TEST.fbt");  //$NON-NLS-1$

		destinationEntry = sourceType.getTypeLibrary().createTypeEntry(destfile);
		destinationEntry.setType(destinationType);

		return destinationType;
	}

	private void createTestCases() {
		for (final ServiceSequence seq : service.getServiceSequence()) {
			createTestCase(seq);
		}
	}

	private void createTestCase(final ServiceSequence testCase) {
		final Event input = createFbInputEvent(testCase);
		final List<Event> outputs = createFbOutputEvents(input.getName());
		createDataInputs();
		createDataOutputs();
		addEccPath(testCase, input.getName(), input, outputs);
	}

	private Event createFbInputEvent(final ServiceSequence testCase) {
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

	private void createDataInputs() {
		// TODO Auto-generated method stub

	}

	private void createDataOutputs() {
		// TODO Auto-generated method stub

	}

	private Algorithm createAlgorithm(final ServiceSequence testCase, final String testCaseName) {
		final TextAlgorithm alg = LibraryElementFactory.eINSTANCE.createSTAlgorithm();
		destinationType.getCallables().add(alg);
		alg.setName(NameRepository.createUniqueName(alg, testCaseName));

		final StringBuilder text = new StringBuilder();
		text.append("ALGORITHM " + testCaseName + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
		for (final ServiceTransaction t : testCase.getServiceTransaction()) {
			// add input primitive parameters as statements
			if (t.getInputPrimitive().getParameters() != null) {
				text.append(t.getInputPrimitive().getParameters());
				text.append(";"); //$NON-NLS-1$
			}

			// add output primitive parameters as statements
			for (final OutputPrimitive o : t.getOutputPrimitive()) {
				if (o.getParameters() != null) {
					text.append(o.getParameters());
					text.append(";"); //$NON-NLS-1$
				}
			}
		}
		text.append("\nEND_ALGORITHM"); //$NON-NLS-1$
		text.append('\0');

		alg.setText(text.toString());
		return alg;
	}

	private void addEccPath(final ServiceSequence testCase, final String testCaseName, final Event input,
			final List<Event> outputs) {
		final ECC ecc = destinationType.getECC();

		final ECState state = LibraryElementFactory.eINSTANCE.createECState();
		ecc.getECState().add(state);
		state.setName(NameRepository.createUniqueName(state, testCaseName + "_S1")); //$NON-NLS-1$
		final Position pS = LibraryElementFactory.eINSTANCE.createPosition();
		pS.setX(200 * ecc.getECState().size());
		pS.setY(200 * ecc.getECState().size());
		state.setPosition(pS);

		final ECAction action = LibraryElementFactory.eINSTANCE.createECAction();
		action.setAlgorithm(createAlgorithm(testCase, testCaseName));
		action.setOutput(outputs.get(0));

		final ECTransition transition = LibraryElementFactory.eINSTANCE.createECTransition();
		transition.setConditionEvent(input);
		transition.setSource(ecc.getStart());
		transition.setDestination(state);
		ecc.getECTransition().add(transition);
		final Position pT1 = LibraryElementFactory.eINSTANCE.createPosition();
		pT1.setX(100 * ecc.getECState().size());
		pT1.setY(100 * ecc.getECState().size());
		transition.setPosition(pT1);

		final ECTransition transitionBack = LibraryElementFactory.eINSTANCE.createECTransition();
		transitionBack.setConditionEvent(null);
		transitionBack.setConditionExpression("1"); //$NON-NLS-1$
		transitionBack.setSource(state);
		transitionBack.setDestination(ecc.getStart());
		ecc.getECTransition().add(transitionBack);
		final Position pT2 = LibraryElementFactory.eINSTANCE.createPosition();
		pT2.setX(100 * ecc.getECState().size());
		pT2.setY(100 * ecc.getECState().size());
		transitionBack.setPosition(pT2);

		// TODO support test sequences with multiple events
	}
}
