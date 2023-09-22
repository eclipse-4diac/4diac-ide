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

import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.AbstractFBGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.TestCase;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.TestEccGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.TestState;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.TestSuite;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class TestFbGenerator extends AbstractFBGenerator {

	public TestFbGenerator(final FBType type, final TestSuite testSuite) {
		super(type, testSuite);
	}

	public BasicFBType generateTestFb() {
		createBasicFB();
		return destinationFB;
	}

	@Override
	protected List<Event> createInputEventList() {
		final List<Event> list = new ArrayList<>();
		for (final TestCase testCase : testSuite.getTestCases()) {
			list.add(createEvent(testCase.getName() + "_TEST", true)); //$NON-NLS-1$
		}
		inputEventList = list;
		return list;
	}

	@Override
	protected List<Event> createOutputEventList() {
		final List<Event> list = new ArrayList<>();
		for (final Event event : sourceType.getInterfaceList().getEventInputs()) {
			list.add(createEvent(event.getName(), event.getType(), false));
		}
		list.addAll(getExpectedEvents(false));
		outputEventList = list;
		return list;
	}

	@Override
	protected void generateECC() {
		final TestEccGenerator eccGen = new TestEccGenerator(destinationFB.getECC(), 0);
		for (final TestCase testCase : testSuite.getTestCases()) {
			int stateCnt = 1;
			for (final TestState testState : testCase.getTestStates()) {
				eccGen.createState(testCase, stateCnt);

				final Event ev = destinationFB.getInterfaceList().getEvent(testState.getTestTrigger().getEvent());
				final ECAction actToTest = TestEccGenerator.createAction();
				actToTest.setOutput(ev);
				actToTest.setAlgorithm(createValueSettingAlgorithm(testState));
				eccGen.getLastState().getECAction().add(actToTest);

				if (stateCnt <= 1) {
					eccGen.createTransitionFromTo(eccGen.getEcc().getECState().get(0), eccGen.getLastState(),
							inputEventList.get(eccGen.getCaseCount()));
					final ECAction actToMatch = TestEccGenerator.createAction();
					actToMatch.setOutput(createCombinedOutputEvent(testCase));
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

	private Algorithm createValueSettingAlgorithm(final TestState testState) {
		boolean containsParameters = false;
		final TextAlgorithm alg = LibraryElementFactory.eINSTANCE.createSTAlgorithm();
		int nameCnt = 0;
		if (!destinationFB.getAlgorithm().isEmpty()) {
			nameCnt = Integer.parseInt(Character.toString(
					destinationFB.getAlgorithm().get(destinationFB.getAlgorithm().size() - 1).getName().charAt(1)));
			nameCnt++;
		}
		alg.setName("A" + nameCnt); //$NON-NLS-1$
		final StringBuilder text = new StringBuilder();
		text.append("ALGORITHM " + alg.getName() + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
		if (testState.getTestTrigger().getParameters() != null
				&& !testState.getTestTrigger().getParameters().equals("")) { //$NON-NLS-1$
			containsParameters = true;
			text.append(testState.getTestTrigger().getParameters().replace(";", ";\n")); //$NON-NLS-1$ //$NON-NLS-2$
		}

		for (final OutputPrimitive outP : testState.getTestOutputs()) {
			if (outP.getParameters() != null && !outP.getParameters().equals("")) { //$NON-NLS-1$
				containsParameters = true;
				text.append(createDataPinName(outP.getParameters().replace(";", ";\n"))); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}

		text.append("\nEND_ALGORITHM"); //$NON-NLS-1$
		text.append("\n\n\n"); //$NON-NLS-1$

		if (!containsParameters) {
			return null;
		}

		alg.setText(text.toString());
		destinationFB.getCallables().add(alg);

		return alg;
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

	private Event createCombinedOutputEvent(final TestCase testCase) {
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

	@Override
	protected String getTypeName() {
		return sourceType.getName() + "_TEST"; //$NON-NLS-1$
	}

	@Override
	protected FBType getSourceFB() {
		return sourceType;
	}

	@Override
	protected List<VarDeclaration> createInputDataList() {

		return new ArrayList<>();
	}

	@Override
	protected List<VarDeclaration> createOutputDataList() {
		final List<VarDeclaration> list = new ArrayList<>();

		for (final VarDeclaration varDecl : sourceType.getInterfaceList().getInputVars()) {
			list.add(createVarDeclaration(varDecl, varDecl.getName(), false));
		}
		for (final VarDeclaration varDecl : sourceType.getInterfaceList().getOutputVars()) {
			list.add(createVarDeclaration(varDecl, varDecl.getName() + "_expected", false)); //$NON-NLS-1$
		}
		return list;
	}

}
