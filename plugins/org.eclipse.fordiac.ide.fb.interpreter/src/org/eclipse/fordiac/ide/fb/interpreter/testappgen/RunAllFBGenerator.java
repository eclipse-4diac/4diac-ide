/*******************************************************************************
 * Copyright (c) 2023, 2024 Johannes Kepler University Linz
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

package org.eclipse.fordiac.ide.fb.interpreter.testappgen;

import static org.eclipse.fordiac.ide.fb.interpreter.testappgen.GeneratedNameConstants.EVENT_LASTCOMPLETE;
import static org.eclipse.fordiac.ide.fb.interpreter.testappgen.GeneratedNameConstants.EVENT_RUNALL;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.AbstractBasicFBGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testcasemodel.TestSuite;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class RunAllFBGenerator extends AbstractBasicFBGenerator {

	public RunAllFBGenerator(final FBType type, final TestSuite testSuite) {
		super(type, testSuite);
	}

	public FBType generateRunAllFB() {
		createBasicFB();
		return destinationFB;
	}

	// run all starts the generation of events that are then sent to the testsignal
	// and muxFb block, last complete indicates the last test finished
	@Override
	protected List<Event> createInputEventList() {
		final List<Event> list = new ArrayList<>();
		list.add(createInputEvent(EVENT_RUNALL));
		list.add(createInputEvent(EVENT_LASTCOMPLETE));
		return list;
	}

	// event outputs are the same as the event inputs from the testsignal fb
	@Override
	protected List<Event> createOutputEventList() {
		return testSuite.getTestCases().stream().map(n -> createOutputEvent(n.getName() + "_TEST")).toList(); //$NON-NLS-1$
	}

	// no data pins needed
	@Override
	protected List<VarDeclaration> createInputDataList() {
		return new ArrayList<>();
	}

	// no data pins needed
	@Override
	protected List<VarDeclaration> createOutputDataList() {
		return new ArrayList<>();
	}

	@Override
	protected void generateECC() {
		// the ecc should trigger should trigger each testcase subsequently
		final TestEccGenerator eccGen = new TestEccGenerator(destinationFB.getECC(), 0);
		for (int i = 0; i < testSuite.getTestCases().size(); i++) {
			eccGen.createState("S", i); //$NON-NLS-1$
			eccGen.getLastState().setName(NameRepository.createUniqueName(eccGen.getLastState(), "S1")); //$NON-NLS-1$
			if (i == 0) {
				eccGen.createTransitionFromTo(eccGen.getNTimesLast(1), eccGen.getLastState(),
						(Event) destinationFB.getInterfaceList().getInterfaceElement(EVENT_RUNALL));
			} else {
				eccGen.createTransitionFromTo(eccGen.getNTimesLast(1), eccGen.getLastState(),
						(Event) destinationFB.getInterfaceList().getInterfaceElement(EVENT_LASTCOMPLETE));
			}
			final ECAction act = TestEccGenerator.createAction();
			act.setOutput(destinationFB.getInterfaceList().getEventOutputs().get(i));
			act.setECState(eccGen.getLastState());
		}
		eccGen.createTransitionFromTo(eccGen.getLastState(), eccGen.getEcc().getStart(),
				(Event) destinationFB.getInterfaceList().getInterfaceElement(EVENT_LASTCOMPLETE));
	}

	@Override
	protected String getTypeName() {
		return sourceType.getName() + "_RUN_ALL"; //$NON-NLS-1$
	}

	@Override
	protected FBType getSourceFB() {
		return sourceType;
	}

}
