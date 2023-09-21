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

package org.eclipse.fordiac.ide.fb.interpreter.testappgen;

import java.util.ArrayList;
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
		list.addAll(testSuite.getTestCases().stream().map(n -> createEvent(n.getName() + "_TEST", true)).toList()); //$NON-NLS-1$
		list.add(createEvent("ERR", true));//$NON-NLS-1$
		list.add(createEvent("SUC", true)); //$NON-NLS-1$
		return list;

	}

	// error and success output events
	@Override
	protected List<Event> createOutputEventList() {
		final List<Event> list = new ArrayList<>();
		list.add(createEvent("ERROR", false));//$NON-NLS-1$
		list.add(createEvent("SUCCESS", false)); //$NON-NLS-1$
		return list;
	}

	// takes no input data
	@Override
	protected List<VarDeclaration> createInputDataList() {
		return new ArrayList<>();
	}

	// string data output for the test name
	@Override
	protected List<VarDeclaration> createOutputDataList() {
		final List<VarDeclaration> list = new ArrayList<>();
		list.add(createVarDeclaration(sourceType.getTypeLibrary().getDataTypeLibrary().getType(FordiacKeywords.STRING),
				"TestCaseName", //$NON-NLS-1$
				false));
		return list;
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
						destinationFB.getInterfaceList().getEventInputs()
								.get(destinationFB.getInterfaceList().getEventInputs().size() - 1));
				eccGen.createTransitionFromTo(eccGen.getLastState(), eccGen.getEcc().getStart(), null);

				sucAction.setOutput(destinationFB.getInterfaceList().getEventOutputs()
						.get(destinationFB.getInterfaceList().getEventOutputs().size() - 1));
				eccGen.getLastState().getECAction().add(sucAction);
			}
			eccGen.increaseCaseCount();
			eccGen.increaseCaseCount();
		}
	}

	// sets the name of the string data output
	public static Algorithm createMuxFbAlgorithm(final BasicFBType fb, final String name) {
		final StringBuilder algText = new StringBuilder();
		algText.append(fb.getInterfaceList().getOutputVars().get(0).getName());
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
