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

import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class RunAllFBGenerator extends AbstractFBGenerator {

	public RunAllFBGenerator(final FBType type, final TestSuite testSuite) {
		super(type, testSuite);
	}

	public FBType generateDemuxFB() {
		createBasicFB();
		return destinationFB;
	}

	@Override
	protected List<Event> createInputEventList() {
		final List<Event> list = new ArrayList<>();
		list.add(createEvent("run_all", true)); //$NON-NLS-1$
		list.add(createEvent("last_complete", true)); //$NON-NLS-1$
		return list;
	}

	@Override
	protected List<Event> createOutputEventList() {
		final List<Event> list = new ArrayList<>();
		for (final TestCase testCase : testSuite.getTestCases()) {
			list.add(createEvent(testCase.getName() + "_TEST", false)); //$NON-NLS-1$
		}
		return list;
	}

	@Override
	protected List<VarDeclaration> createInputDataList() {
		return new ArrayList<>();
	}

	@Override
	protected List<VarDeclaration> createOutputDataList() {
		return new ArrayList<>();
	}

	@Override
	protected void generateECC() {
		final TestEccGenerator eccGen = new TestEccGenerator(destinationFB.getECC(), 0);
		for (int i = 0; i < testSuite.getTestCases().size(); i++) {
			eccGen.createState("S", i); //$NON-NLS-1$
			eccGen.getLastState().setName(NameRepository.createUniqueName(eccGen.getLastState(), "S1")); //$NON-NLS-1$
			if (i == 0) {
				eccGen.createTransitionFromTo(eccGen.getNTimesLast(1), eccGen.getLastState(),
						destinationFB.getInterfaceList().getEventInputs().get(0));
			} else {
				eccGen.createTransitionFromTo(eccGen.getNTimesLast(1), eccGen.getLastState(),
						destinationFB.getInterfaceList().getEventInputs().get(1));
			}
			final ECAction act = TestEccGenerator.createAction();
			act.setOutput(destinationFB.getInterfaceList().getEventOutputs().get(i));
			act.setECState(eccGen.getLastState());
		}
		eccGen.createTransitionFromTo(eccGen.getLastState(), eccGen.getEcc().getStart(),
				destinationFB.getInterfaceList().getEventInputs().get(1));
	}

	@Override
	protected String getTypeName() {
		return sourceType.getName() + "_DEMUX"; //$NON-NLS-1$
	}

	@Override
	protected FBType getSourceFB() {
		return sourceType;
	}

}
