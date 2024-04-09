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
package org.eclipse.fordiac.ide.fb.interpreter.testappgen;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.AbstractBasicFBGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testcasemodel.TestCase;
import org.eclipse.fordiac.ide.fb.interpreter.testcasemodel.TestState;
import org.eclipse.fordiac.ide.fb.interpreter.testcasemodel.TestSuite;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class TestFbGenerator extends AbstractBasicFBGenerator {

	public TestFbGenerator(final FBType type, final TestSuite testSuite) {
		super(type, testSuite);
	}

	public BasicFBType generateTestFb() {
		createBasicFB();
		return destinationFB;
	}

	// each testcase needs an event that starts it
	@Override
	protected List<Event> createInputEventList() {
		return testSuite.getTestCases().stream().map(n -> createEvent(n.getName() + "_TEST", true)).toList(); //$NON-NLS-1$
	}

	// needs all event inputs from the block to test as outputs
	// creates for each unique testcase output an event and sends it to the matchc
	// fb, so it knows which ones to expect
	@Override
	protected List<Event> createOutputEventList() {
		final List<Event> list = new ArrayList<>();
		list.addAll(sourceType.getInterfaceList().getEventInputs().stream().map(n -> createEvent(n.getName(), false))
				.toList());
		list.addAll(getExpectedEvents(false));
		return list;
	}

	@Override
	protected void generateECC() {
		final TestEccGenerator eccGen = new TestEccGenerator(destinationFB.getECC(), 0);
		for (final TestCase testCase : testSuite.getTestCases()) {
			int stateCnt = 1;
			for (final TestState testState : testCase.getTestStates()) {
				eccGen.createState(testCase.getName() + "_S" + stateCnt, stateCnt); //$NON-NLS-1$

				final Event ev = destinationFB.getInterfaceList().getEvent(testState.getTestTrigger().getEvent());
				final ECAction actToTest = TestEccGenerator.createAction();
				actToTest.setOutput(ev);
				final Algorithm alg = createValueSettingAlgorithm(destinationFB, testState);
				actToTest.setAlgorithm(alg);
				eccGen.getLastState().getECAction().add(actToTest);

				if (stateCnt <= 1) {
					eccGen.createTransitionFromTo(eccGen.getEcc().getECState().get(0), eccGen.getLastState(),
							destinationFB.getInterfaceList().getEventInputs().get(eccGen.getCaseCount()));
					final ECAction actToMatch = TestEccGenerator.createAction();
					actToMatch.setOutput(getOutputEventForTestCase(testCase));
					eccGen.getLastState().getECAction().add(actToMatch);

				} else {
					eccGen.createTransitionFromTo(eccGen.getNTimesLast(1), eccGen.getLastState(), null);
				}

				stateCnt++;
			}
			eccGen.createTransitionFromTo(eccGen.getLastState(), eccGen.getEcc().getECState().get(0), null);

			eccGen.increaseCaseCount();
		}
	}

	// gets the output event from the test fb corresponding with the testcase
	private Event getOutputEventForTestCase(final TestCase testCase) {
		final StringBuilder sb = new StringBuilder();

		for (final TestState testState : testCase.getTestStates()) {
			for (final OutputPrimitive outP : testState.getTestOutputs()) {
				sb.append(outP.getEvent() + "_"); //$NON-NLS-1$
			}
		}
		sb.append("expected"); //$NON-NLS-1$
		final String name = sb.toString();

		return destinationFB.getInterfaceList().getEventOutputs().stream().filter(e -> e.getName().equals(name))
				.findAny().orElse(null);
	}

	public static Algorithm createValueSettingAlgorithm(final BasicFBType fb, final TestState testState) {
		boolean containsParameters = false;
		int nameCnt = 0;
		if (!fb.getAlgorithm().isEmpty()) {
			nameCnt = Integer.parseInt(fb.getAlgorithm().get(fb.getAlgorithm().size() - 1).getName().substring(1));
			nameCnt++;
		}

		final StringBuilder algText = new StringBuilder();

		if (testState.getTestTrigger().getParameters() != null
				&& !testState.getTestTrigger().getParameters().equals("")) { //$NON-NLS-1$
			containsParameters = true;
			algText.append(testState.getTestTrigger().getParameters().replace(";", ";\n")); //$NON-NLS-1$ //$NON-NLS-2$
		}

		for (final OutputPrimitive outP : testState.getTestOutputs()) {
			if (outP.getParameters() != null && !outP.getParameters().equals("")) { //$NON-NLS-1$
				containsParameters = true;
				algText.append(createDataPinName(outP.getParameters().replace(";", ";\n"))); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}

		if (!containsParameters) {
			return null;
		}
		return TestEccGenerator.createAlgorithm(fb, "A" + nameCnt, algText.toString()); //$NON-NLS-1$
	}

	private static String createDataPinName(final String s) {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == ':') {
				sb.append("_expected:"); //$NON-NLS-1$
			} else {
				sb.append(s.charAt(i));
			}
		}
		return sb.toString();
	}

	@Override
	protected String getTypeName() {
		return sourceType.getName() + "_TEST"; //$NON-NLS-1$
	}

	@Override
	protected FBType getSourceFB() {
		return sourceType;
	}

	// no data input needed
	@Override
	protected List<VarDeclaration> createInputDataList() {

		return new ArrayList<>();
	}

	// data pin needed for each input data pin on the block to test to feed it the
	// data needed for the test, also a data pin for every output data pin on the
	// block to test is needed, to send the expected output data to the match fb
	@Override
	protected List<VarDeclaration> createOutputDataList() {
		final List<VarDeclaration> list = new ArrayList<>();
		list.addAll(sourceType.getInterfaceList().getInputVars().stream()
				.map(n -> createVarDeclaration(n.getType(), n.getName(), false)).toList());
		list.addAll(sourceType.getInterfaceList().getOutputVars().stream()
				.map(n -> createVarDeclaration(n.getType(), n.getName() + "_expected", false)).toList()); //$NON-NLS-1$
		return list;
	}
}
