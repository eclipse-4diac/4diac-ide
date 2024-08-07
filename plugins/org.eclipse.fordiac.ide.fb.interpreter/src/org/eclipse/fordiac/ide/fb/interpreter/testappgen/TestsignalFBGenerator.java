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
 * Bianca Wiesmayr - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter.testappgen;

import static org.eclipse.fordiac.ide.fb.interpreter.testappgen.GeneratedNameConstants.EVENT_NEXTCASE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.AbstractBasicFBGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testcasemodel.TestCase;
import org.eclipse.fordiac.ide.fb.interpreter.testcasemodel.TestState;
import org.eclipse.fordiac.ide.fb.interpreter.testcasemodel.TestSuite;
import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class TestsignalFBGenerator extends AbstractBasicFBGenerator {

	public TestsignalFBGenerator(final FBType type, final TestSuite testSuite) {
		super(type, testSuite);
	}

	public BasicFBType generateTestFb() {
		createBasicFB();
		return destinationFB;
	}

	// each testcase needs an event that starts it
	@Override
	protected List<Event> createInputEventList() {
		final List<Event> list = new ArrayList<>();
		list.addAll(testSuite.getTestCases().stream().map(n -> createInputEvent(n.getName() + "_TEST")) //$NON-NLS-1$
				.toList());
		list.add(createInputEvent(EVENT_NEXTCASE));
		return list;

	}

	// needs all event inputs from the block to test as outputs
	// creates for each unique testcase output an event and sends it to the matchc
	// fb, so it knows which ones to expect
	@Override
	protected List<Event> createOutputEventList() {
		final List<Event> list = new ArrayList<>();
		list.addAll(sourceType.getInterfaceList().getEventInputs().stream().map(n -> createOutputEvent(n.getName()))
				.toList());
		list.addAll(getExpectedEvents(false));
		return list;
	}

	@Override
	protected void generateECC() {
		createTimeOutPlug();
		final Algorithm timeoutAlg = createTimeOutAlg(destinationFB, 300);

		final TestEccGenerator eccGen = new TestEccGenerator(destinationFB.getECC(), 0);
		for (final TestCase testCase : testSuite.getTestCases()) {
			// int stateCnt = 1;

			eccGen.createState(testCase.getName() + "_S" + 0, 0); //$NON-NLS-1$
			eccGen.createTransitionFromTo(eccGen.getEcc().getECState().get(0), eccGen.getLastState(),
					destinationFB.getInterfaceList().getEventInputs().get(eccGen.getCaseCount()));
			final ECAction actToMatch = TestEccGenerator.createAction();
			actToMatch.setOutput(getOutputEventForTestCase(testCase));
			actToMatch.setAlgorithm(timeoutAlg);
			eccGen.getLastState().getECAction().add(actToMatch);

			for (int i = 0; i < testCase.getTestStates().size(); i++) {
				final TestState testState = testCase.getTestStates().get(i);
				eccGen.createState(testCase.getName() + "_S" + i + 1, i + 1); //$NON-NLS-1$

				final Event ev = destinationFB.getInterfaceList().getEvent(testState.getTestTrigger().getEvent());
				final ECAction actToTest = TestEccGenerator.createAction();
				actToTest.setOutput(ev);
				final Algorithm alg = createValueSettingAlgorithm(destinationFB, testState);
				actToTest.setAlgorithm(alg);
				eccGen.getLastState().getECAction().add(actToTest);
				if (i == 0) {
					eccGen.createTransitionFromTo(eccGen.getNTimesLast(1), eccGen.getLastState(), null);
				} else if (i >= 1 && testCase.getTestStates().get(i - 1).getTestOutputs().isEmpty()) {
					// timeoutaction
					final ECAction a = TestEccGenerator.createAction(getStartTimeoutEvent());
					eccGen.getNTimesLast(1).getECAction().add(a);
					eccGen.createTransitionFromTo(eccGen.getNTimesLast(1), eccGen.getLastState(),
							getTransitionTimeOutEvent());
				} else {
					eccGen.createTransitionFromTo(eccGen.getNTimesLast(1), eccGen.getLastState(),
							destinationFB.getInterfaceList().getEventInputs().getLast());
				}

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
		final StringBuilder algText = new StringBuilder();
		algText.append(GeneratedNameConstants.TESTSIGNALGEN_CASECOUNT_PINNAME + " := " //$NON-NLS-1$
				+ testState.getTestCase().getServiceSequence().getServiceTransaction().size() + "; \n"); //$NON-NLS-1$

		if (testState.getTestTrigger().getParameters() != null
				&& !testState.getTestTrigger().getParameters().equals("")) { //$NON-NLS-1$
			algText.append(testState.getTestTrigger().getParameters().replace(";", ";\n")); //$NON-NLS-1$ //$NON-NLS-2$
		}

		for (final OutputPrimitive outP : testState.getTestOutputs()) {
			if (outP.getParameters() != null && !outP.getParameters().equals("")) { //$NON-NLS-1$
				String s = outP.getParameters().replace(";", ";\n");//$NON-NLS-1$ //$NON-NLS-2$
				s = createDataPinName(s);
				algText.append(s);
			}
		}
		final int nameCnt = fb.getAlgorithm().size();
		return TestEccGenerator.createAlgorithm(fb, "A" + nameCnt, algText.toString()); //$NON-NLS-1$
	}

	private static String createDataPinName(final String s) {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == ':') {
				sb.append("_expected:"); //$NON-NLS-1$
			} else if (s.charAt(i) != ' ') {
				sb.append(s.charAt(i));
			}
		}
		return sb.toString();
	}

	@Override
	protected String getTypeName() {
		return sourceType.getName() + "_TESTSIGNALGEN"; //$NON-NLS-1$
	}

	@Override
	protected FBType getSourceFB() {
		return sourceType;
	}

	// no data input needed
	@Override
	protected List<VarDeclaration> createInputDataList() {
		return Collections.emptyList();
	}

	// data pin needed for each input data pin on the block to test to feed it the
	// data needed for the test, also a data pin for every output data pin on the
	// block to test is needed, to send the expected output data to the match fb
	@Override
	protected List<VarDeclaration> createOutputDataList() {
		final List<VarDeclaration> list = new ArrayList<>();
		list.add(createOutputVarDecl(sourceType.getTypeLibrary().getDataTypeLibrary().getType(FordiacKeywords.INT),
				GeneratedNameConstants.TESTSIGNALGEN_CASECOUNT_PINNAME));
		list.addAll(sourceType.getInterfaceList().getInputVars().stream()
				.map(n -> createOutputVarDecl(n.getType(), n.getName())).toList());
		list.addAll(sourceType.getInterfaceList().getOutputVars().stream()
				.map(n -> createOutputVarDecl(n.getType(), n.getName() + "_expected")).toList()); //$NON-NLS-1$
		return list;
	}
}
