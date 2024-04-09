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

import org.eclipse.fordiac.ide.fb.interpreter.testcasemodel.TestCase;
import org.eclipse.fordiac.ide.fb.interpreter.testcasemodel.TestState;
import org.eclipse.fordiac.ide.fb.interpreter.testcasemodel.TestSuite;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public abstract class AbstractBasicFBGenerator extends AbstractBlockGenerator {

	protected BasicFBType destinationFB;
	protected TestSuite testSuite;
	protected TestCase testCase;

	protected AbstractBasicFBGenerator(final FBType type, final TestSuite testSuite, final TestCase testCase) {
		super(type);
		this.testCase = testCase;
		this.testSuite = testSuite;
	}

	protected AbstractBasicFBGenerator(final FBType type, final TestSuite testSuite) {
		super(type);
		this.testSuite = testSuite;
	}

	protected void createBasicFB() {
		destinationFB = LibraryElementFactory.eINSTANCE.createBasicFBType();

		createFile(destinationFB);
		configureBlock(destinationFB);

		destinationFB.setECC(LibraryElementFactory.eINSTANCE.createECC());
		final ECState start = LibraryElementFactory.eINSTANCE.createECState();
		destinationFB.getECC().getECState().add(start);
		start.setName("START"); //$NON-NLS-1$
		destinationFB.getECC().setStart(start);
		addPosition(start, 0, 0);

		addEvents();
		addDataPins();
		createWiths();
		generateECC();
	}

	private void createWiths() {
		for (final Event input : destinationFB.getInterfaceList().getEventInputs()) {
			for (final VarDeclaration varD : destinationFB.getInterfaceList().getInputVars()) {
				input.getWith().add(createWith(varD));
			}
		}

		for (final Event output : destinationFB.getInterfaceList().getEventOutputs()) {
			for (final VarDeclaration varD : destinationFB.getInterfaceList().getOutputVars()) {
				output.getWith().add(createWith(varD));
			}
		}
	}

	protected void addEvents() {
		destinationFB.getInterfaceList().getEventInputs().addAll(createInputEventList());
		destinationFB.getInterfaceList().getEventOutputs().addAll(createOutputEventList());

	}

	protected void addDataPins() {
		final List<VarDeclaration> varIn = createInputDataList();
		for (final VarDeclaration varD : varIn) {
			varD.setValue(LibraryElementFactory.eINSTANCE.createValue());
			varD.getValue().setValue(""); //$NON-NLS-1$
			destinationFB.getInterfaceList().getInputVars().add(varD);
		}
		final List<VarDeclaration> varOut = createOutputDataList();
		for (final VarDeclaration varD : varOut) {
			varD.setValue(LibraryElementFactory.eINSTANCE.createValue());
			varD.getValue().setValue(""); //$NON-NLS-1$
			destinationFB.getInterfaceList().getOutputVars().add(varD);
		}
	}

	protected static String createEventName(final List<OutputPrimitive> testOutputs) {
		final StringBuilder sb = new StringBuilder();
		testOutputs.stream().forEach(n -> sb.append(n.getEvent() + "_")); //$NON-NLS-1$
		sb.append("expected"); //$NON-NLS-1$
		return sb.toString();
	}

	protected static boolean containsEvent(final List<Event> list, final String name) {
		return list.stream().anyMatch(n -> n.getName().equals(name));
	}

	protected List<Event> getExpectedEvents(final boolean isInput) {
		final List<Event> list = new ArrayList<>();
		for (final TestCase testC : testSuite.getTestCases()) {
			final StringBuilder sbName = new StringBuilder();
			final StringBuilder sbComment = new StringBuilder();
			for (final TestState testState : testC.getTestStates()) {
				for (final OutputPrimitive prim : testState.getTestOutputs()) {
					sbName.append(prim.getEvent() + "_"); //$NON-NLS-1$
					sbComment.append(prim.getEvent() + "."); //$NON-NLS-1$
				}
			}
			sbName.append("expected"); //$NON-NLS-1$
			final String comment = sbComment.toString();
			final String name = sbName.toString();
			if (!containsEvent(list, name)) {
				final Event newEvent = createEvent(name, isInput);
				newEvent.setComment(comment.substring(0, comment.lastIndexOf('.')));
				list.add(newEvent);
			}
		}
		return list;
	}

	protected abstract List<Event> createInputEventList();

	protected abstract List<Event> createOutputEventList();

	protected abstract List<VarDeclaration> createInputDataList();

	protected abstract List<VarDeclaration> createOutputDataList();

	protected abstract void generateECC();

	protected abstract FBType getSourceFB();

}
