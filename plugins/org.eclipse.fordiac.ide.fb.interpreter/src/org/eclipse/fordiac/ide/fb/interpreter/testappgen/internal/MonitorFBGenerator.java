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
import java.util.stream.Stream;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class MonitorFBGenerator extends AbstractFBGenerator {
	public MonitorFBGenerator(final FBType type, final TestSuite testSuite, final TestCase testCase) {
		super(type, testSuite, testCase);
	}

	public FBType generateTestFb() {
		createBasicFB();

		return destinationFB;
	}

	@Override
	protected List<Event> createInputEventList() {
		final Stream<Event> stream = Stream.concat(sourceType.getInterfaceList().getEventInputs().stream(),
				sourceType.getInterfaceList().getEventOutputs().stream());
		return stream.map(n -> (createEvent(n.getName(), true))).toList();
	}

	@Override
	protected List<Event> createOutputEventList() {
		final List<Event> list = new ArrayList<>();
		list.add(createEvent("DETECTED", false)); //$NON-NLS-1$
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
		int cnt = 0;
		ECState continueFrom = eccGen.getEcc().getStart();
		boolean lastMultiple = false;
		for (final TestState testState : testCase.getTestStates()) {
			lastMultiple = false;
			// InputPrimitive

			eccGen.createState("S", cnt); //$NON-NLS-1$
			eccGen.getLast().setName(NameRepository.createUniqueName(eccGen.getLast(), "S1")); //$NON-NLS-1$
			cnt++;
			eccGen.createTransitionFromTo(continueFrom, eccGen.getLast(),
					destinationFB.getInterfaceList().getEvent(testState.getTestTrigger().getEvent()));
			eccGen.getEcc().getECTransition().get(eccGen.getEcc().getECTransition().size() - 1)
					.setConditionExpression(createConditionExpression(testState.getTestTrigger().getParameters()));

			// OutputPrimitives
			if (!testState.getTestOutputs().isEmpty()) {
				if (testState.getTestOutputs().size() == 1) {
					eccGen.createState("S", cnt); //$NON-NLS-1$
					eccGen.getLast().setName(NameRepository.createUniqueName(eccGen.getLast(), "S1")); //$NON-NLS-1$
					eccGen.createTransitionFromTo(eccGen.getNTimesLast(1), eccGen.getLast(),
							destinationFB.getInterfaceList().getEvent(testState.getTestOutputs().get(0).getEvent()));
					eccGen.getEcc().getECTransition().get(eccGen.getEcc().getECTransition().size() - 1)
							.setConditionExpression(
									createConditionExpression(testState.getTestOutputs().get(0).getParameters()));
					cnt++;
					continueFrom = eccGen.getLast();
				} else {
					final int prevCnt = cnt;
					eccGen.createState("S", cnt + 2 * testState.getTestOutputs().size()); //$NON-NLS-1$
					eccGen.getLast().setName(NameRepository.createUniqueName(eccGen.getLast(), "S1")); //$NON-NLS-1$
					continueFrom = eccGen.getLast();
					createStates(eccGen, eccGen.getNTimesLast(1), eccGen.getLast(), testState.getTestOutputs(), cnt);
					eccGen.decreaseCaseCountBy(eccGen.getCaseCount());

					cnt = prevCnt - 1 + 3 * testState.getTestOutputs().size();
					lastMultiple = true;
				}

			}
		}
		eccGen.createState("S", cnt); //$NON-NLS-1$
		eccGen.getLast().setName(NameRepository.createUniqueName(eccGen.getLast(), "S1")); //$NON-NLS-1$

		final ECAction action = TestEccGenerator.createAction();
		action.setOutput(destinationFB.getInterfaceList().getEventOutputs().get(0));
		eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size() - 1).getECAction().add(action);
		if (lastMultiple) {
			eccGen.createTransitionFromTo(continueFrom, eccGen.getLast(), null);
		} else {
			eccGen.createTransitionFromTo(eccGen.getNTimesLast(1), eccGen.getLast(), null);
		}

		eccGen.decreaseCaseCount();
		for (int i = 1; i < eccGen.getEcc().getECState().size(); i++) {
			eccGen.createTransitionFromTo(eccGen.getEcc().getECState().get(i), eccGen.getEcc().getStart(), null);
		}

	}

	private static String createConditionExpression(String text) {
		if (text != null) {
			text = text.replace(":", ""); //$NON-NLS-1$//$NON-NLS-2$
			return text.replaceAll(" ", " AND "); //$NON-NLS-1$//$NON-NLS-2$
		}
		return ""; //$NON-NLS-1$
	}

	private void createStates(final TestEccGenerator eccGen, final ECState state, final ECState to,
			final List<OutputPrimitive> listOutP, int cnt) {

		for (final OutputPrimitive firstColumn : listOutP) {
			eccGen.createState("S", cnt); //$NON-NLS-1$
			eccGen.getLast().setName(NameRepository.createUniqueName(eccGen.getLast(), "S1")); //$NON-NLS-1$
			cnt++;
			eccGen.createTransitionFromTo(state, eccGen.getLast(),
					destinationFB.getInterfaceList().getEvent(firstColumn.getEvent()));
			eccGen.getEcc().getECTransition().get(eccGen.getEcc().getECTransition().size() - 1)
					.setConditionExpression(createConditionExpression(firstColumn.getParameters()));
			final List<OutputPrimitive> outPList = (List<OutputPrimitive>) EcoreUtil.copyAll(listOutP);
			outPList.remove(indexOutputPrim(firstColumn.getEvent(), outPList));
			if (outPList.isEmpty()) {
				eccGen.createTransitionFromTo(eccGen.getLast(), to, null);
				eccGen.getEcc().getECTransition().get(eccGen.getEcc().getECTransition().size() - 1)
						.setConditionExpression(createConditionExpression(firstColumn.getParameters()));
				return;
			}
			createStates(eccGen, eccGen.getLast(), to, outPList, cnt);
			eccGen.increaseCaseCount();
		}
	}

	private static int indexOutputPrim(final String ev, final List<OutputPrimitive> list) {
		int i = 0;
		for (final OutputPrimitive outputPrimitive : list) {
			if (outputPrimitive.getEvent().equals(ev)) {
				return i;
			}
			i++;
		}
		return i;
	}

	@Override
	protected String getTypeName() {
		return sourceType.getName() + "_" + testCase.getName() + "_MONITOR"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	protected FBType getSourceFB() {
		return sourceType;
	}

}
