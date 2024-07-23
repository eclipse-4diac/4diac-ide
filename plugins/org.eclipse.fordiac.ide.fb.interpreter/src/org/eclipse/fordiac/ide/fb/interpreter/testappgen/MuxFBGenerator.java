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

import static org.eclipse.fordiac.ide.fb.interpreter.testappgen.GeneratedNameConstants.EVENT_ERR;
import static org.eclipse.fordiac.ide.fb.interpreter.testappgen.GeneratedNameConstants.EVENT_ERROR;
import static org.eclipse.fordiac.ide.fb.interpreter.testappgen.GeneratedNameConstants.EVENT_SUC;
import static org.eclipse.fordiac.ide.fb.interpreter.testappgen.GeneratedNameConstants.EVENT_SUCCESS;
import static org.eclipse.fordiac.ide.fb.interpreter.testappgen.GeneratedNameConstants.VARDECL_TESTCASENAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.AbstractBasicFBGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testcasemodel.TestSuite;
import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class MuxFBGenerator extends AbstractBasicFBGenerator {

	public MuxFBGenerator(final FBType type, final TestSuite testSuite) {
		super(type, testSuite);

	}

	public BasicFBType generateMuxFB() {
		createBasicFB();
		return destinationFB;
	}

	// mux fb has for each testCase an input, like the compositeFb
	// also an error and success event input that are connected with the matchFbs
	@Override
	protected List<Event> createInputEventList() {
		final List<Event> list = new ArrayList<>();
		list.addAll(testSuite.getTestCases().stream().map(n -> createInputEvent(n.getName() + "_TEST")).toList()); //$NON-NLS-1$
		list.add(createInputEvent(EVENT_ERR));
		list.add(createInputEvent(EVENT_SUC));
		return list;

	}

	// error and success output events
	@Override
	protected List<Event> createOutputEventList() {
		final Event errorOutput = createOutputEvent(EVENT_ERROR);
		final Event successOutput = createOutputEvent(EVENT_SUCCESS);
		return Arrays.asList(errorOutput, successOutput);
	}

	// takes no input data
	@Override
	protected List<VarDeclaration> createInputDataList() {
		return Collections.emptyList();
	}

	// string data output for the test name
	@Override
	protected List<VarDeclaration> createOutputDataList() {
		return Arrays.asList(
				createOutputVarDecl(sourceType.getTypeLibrary().getDataTypeLibrary().getType(FordiacKeywords.STRING),
						VARDECL_TESTCASENAME));
	}

	@Override
	protected void generateECC() {

		final TestEccGenerator eccGen = new TestEccGenerator(destinationFB.getECC(), 0);
		for (int i = 0; i < destinationFB.getInterfaceList().getEventInputs().size(); i++) {
			final Event ev = destinationFB.getInterfaceList().getEventInputs().get(i);
			int stateCnt = 0;
			if (ev.getName().contains("TEST")) { //$NON-NLS-1$
				// create state from input event
				eccGen.createState("S", 0); //$NON-NLS-1$
				eccGen.getLastState().setName(NameRepository.createUniqueName(eccGen.getLastState(), "S1")); //$NON-NLS-1$
				eccGen.createTransitionFromTo(eccGen.getEcc().getStart(), eccGen.getLastState(), ev);
				stateCnt++;

				// create state for error output
				eccGen.createState("S", stateCnt); //$NON-NLS-1$
				eccGen.getLastState().setName(NameRepository.createUniqueName(eccGen.getLastState(), "S1")); //$NON-NLS-1$
				eccGen.createTransitionFromTo(eccGen.getNTimesLast(1), eccGen.getLastState(),
						destinationFB.getInterfaceList().getEventInputs()
								.get(destinationFB.getInterfaceList().getEventInputs().size() - 2));
				eccGen.createTransitionFromTo(eccGen.getLastState(), eccGen.getEcc().getStart(), null);

				final ECAction errAction = TestEccGenerator.createAction();
				final ECAction sucAction = TestEccGenerator.createAction();
				errAction.setOutput(destinationFB.getInterfaceList().getEventOutputs()
						.get(destinationFB.getInterfaceList().getEventOutputs().size() - 2));
				final Algorithm alg = createMuxFbAlgorithm(destinationFB, ev.getName());
				errAction.setAlgorithm(alg);
				sucAction.setAlgorithm(alg);

				eccGen.getLastState().getECAction().add(errAction);
				eccGen.increaseCaseCount();

				// create state for success output
				eccGen.createState("S", stateCnt); //$NON-NLS-1$
				eccGen.getLastState().setName(NameRepository.createUniqueName(eccGen.getLastState(), "S1")); //$NON-NLS-1$

				eccGen.createTransitionFromTo(eccGen.getNTimesLast(2), eccGen.getLastState(),
						destinationFB.getInterfaceList().getEventInputs().getLast());
				eccGen.createTransitionFromTo(eccGen.getLastState(), eccGen.getEcc().getStart(), null);

				sucAction.setOutput(destinationFB.getInterfaceList().getEventOutputs().getLast());
				eccGen.getLastState().getECAction().add(sucAction);
			}
			eccGen.increaseCaseCount();
			eccGen.increaseCaseCount();
		}
	}

	// the algorithm sets the string data pin to the name of the current testcase
	public static Algorithm createMuxFbAlgorithm(final BasicFBType fb, final String name) {
		final StringBuilder algText = new StringBuilder();
		algText.append(VARDECL_TESTCASENAME);
		algText.append(":="); //$NON-NLS-1$
		algText.append("'" + name + "'"); //$NON-NLS-1$ //$NON-NLS-2$
		algText.append(";"); //$NON-NLS-1$
		return TestEccGenerator.createAlgorithm(fb, name, algText.toString());
	}

	@Override
	protected String getTypeName() {
		return sourceType.getName() + "_MUX"; //$NON-NLS-1$
	}

	@Override
	protected FBType getSourceFB() {
		return sourceType;
	}

}
