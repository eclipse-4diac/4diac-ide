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
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
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
		list.add(createEvent("tr_CMPLT", true));//$NON-NLS-1$
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
		list.add(createEvent("RESULT", false));//$NON-NLS-1$

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

				eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size() - 1).getECAction().add(actToTest);

				if (stateCnt <= 1) {
					eccGen.createTransitionFromTo(eccGen.getEcc().getECState().get(0),
							eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size() - 1),
							inputEventList.get(eccGen.getCaseCount()));
					final ECAction actToMatch = TestEccGenerator.createAction();
					actToMatch.setOutput(getOutputEvent(testCase));
					eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size() - 1).getECAction()
							.add(actToMatch);

				} else {
					eccGen.createTransitionFromTo(
							eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size() - 2),
							eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size() - 1),
							inputEventList.get(eccGen.getCaseCount()));
				}

				stateCnt++;
			}
			eccGen.createTransitionFromTo(eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size() - 1),
					eccGen.getEcc().getECState().get(0), null);

			eccGen.increaseCaseCount();
		}
	}

	private Event getOutputEvent(final TestCase testCase) {
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
			list.add(createVarDeclaration(varDecl, varDecl.getName(), false));
		}

		return list;
	}

}
