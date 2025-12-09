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

package org.eclipse.fordiac.ide.fb.interpreter.monitorgen;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.TestEccGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.AbstractBasicFBGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testcasemodel.TestCase;
import org.eclipse.fordiac.ide.fb.interpreter.testcasemodel.TestSuite;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class MonitorFBGenerator extends AbstractBasicFBGenerator {
	public MonitorFBGenerator(final FBType type, final TestSuite testSuite, final TestCase testCase) {
		super(type, testSuite, testCase);
	}

	public FBType generateTestFb() {
		createBasicFB();

		return destinationFB;
	}

	// create for every in- and output event from the to monitor fb an input event
	// for the monitor fb
	@Override
	protected List<Event> createInputEventList() {
		final Stream<Event> stream = Stream.concat(sourceType.getInterfaceList().getEventInputs().stream(),
				sourceType.getInterfaceList().getEventOutputs().stream());
		return stream.map(n -> (createEvent(n.getName(), true))).toList();
	}

	// create a detected output event
	@Override
	protected List<Event> createOutputEventList() {
		final List<Event> list = new ArrayList<>();
		list.add(createEvent("DETECTED", false)); //$NON-NLS-1$
		return list;
	}

	// create for every in- and output data pin from the to monitor fb an input data
	// pin for the monitor fb
	@Override
	protected List<VarDeclaration> createInputDataList() {
		final Stream<VarDeclaration> stream = Stream.concat(sourceType.getInterfaceList().getInputVars().stream(),
				sourceType.getInterfaceList().getOutputVars().stream());
		return stream.map(n -> createInputVarDecl(n.getType(), n.getName())).toList();
	}

	// no data output
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
		for (int i = 0; i < testCase.getTestStates().size(); i++) {
			lastMultiple = false;
			// InputPrimitive
			eccGen.createState("S", cnt); //$NON-NLS-1$
			eccGen.getLastState().setName(NameRepository.createUniqueName(eccGen.getLastState(), "IP1")); //$NON-NLS-1$
			cnt++;
			eccGen.createTransitionFromTo(continueFrom, eccGen.getLastState(), destinationFB.getInterfaceList()
					.getEvent(testCase.getTestStates().get(i).getTestTrigger().getEvent()));
			eccGen.getEcc().getECTransition().get(eccGen.getEcc().getECTransition().size() - 1).setConditionExpression(
					createConditionExpression(testCase.getTestStates().get(i).getTestTrigger().getParameters()));

			continueFrom = eccGen.getLastState();

			// OutputPrimitives
			if (!testCase.getTestStates().get(i).getTestOutputs().isEmpty()) {
				if (testCase.getTestStates().get(i).getTestOutputs().size() == 1) {
					eccGen.createState("S", cnt); //$NON-NLS-1$
					eccGen.getLastState().setName(NameRepository.createUniqueName(eccGen.getLastState(), "S1")); //$NON-NLS-1$
					eccGen.createTransitionFromTo(eccGen.getNTimesLast(1), eccGen.getLastState(),
							destinationFB.getInterfaceList()
									.getEvent(testCase.getTestStates().get(i).getTestOutputs().get(0).getEvent()));
					eccGen.getEcc().getECTransition().get(eccGen.getEcc().getECTransition().size() - 1)
							.setConditionExpression(createConditionExpression(
									testCase.getTestStates().get(i).getTestOutputs().get(0).getParameters()));
					if (i < testCase.getTestStates().size() - 1) {
						createTransitionsBack(eccGen, eccGen.getLastState(),
								testCase.getTestStates().get(i + 1).getTestTrigger().getEvent());
					}
					cnt++;
					continueFrom = eccGen.getLastState();
				} else {
					final int prevCnt = cnt;
					// Binding State (BS) where every possible combination comes back together
					eccGen.createState("S", cnt + 2 * testCase.getTestStates().get(i).getTestOutputs().size()); //$NON-NLS-1$
					eccGen.getLastState().setName(NameRepository.createUniqueName(eccGen.getLastState(), "BS1")); //$NON-NLS-1$
					continueFrom = eccGen.getLastState();
					createStates(eccGen, eccGen.getNTimesLast(1), eccGen.getLastState(),
							testCase.getTestStates().get(i).getTestOutputs(), cnt);
					eccGen.decreaseCaseCountBy(eccGen.getCaseCount());
					cnt = prevCnt - 1 + 3 * testCase.getTestStates().get(i).getTestOutputs().size();
					lastMultiple = true;
				}

			}
		}
		createTransitionsBackForAllStates(eccGen);
		eccGen.createState("S", cnt); //$NON-NLS-1$
		eccGen.getLastState().setName(NameRepository.createUniqueName(eccGen.getLastState(), "S1")); //$NON-NLS-1$

		final ECAction action = TestEccGenerator.createAction();
		action.setOutput(destinationFB.getInterfaceList().getEventOutputs().get(0));
		eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size() - 1).getECAction().add(action);
		if (lastMultiple) {
			eccGen.createTransitionFromTo(continueFrom, eccGen.getLastState(), null);
		} else {
			eccGen.createTransitionFromTo(eccGen.getNTimesLast(1), eccGen.getLastState(), null);
		}

		eccGen.decreaseCaseCount();
		eccGen.createTransitionFromTo(eccGen.getLastState(), eccGen.getEcc().getStart(), null);
	}

	private void createTransitionsBackForAllStates(final TestEccGenerator eccGen) {
		for (final ECState state : eccGen.getEcc().getECState()) {
			// no transition needed when we are at the start state, at a binding state (BS),
			// at the last state before the detected output, at a sate with only one out
			// transition where the condition event is null and the condition expression is
			// 1
			if (!state.getName().equals("START") && !state.getName().contains("BS") //$NON-NLS-1$//$NON-NLS-2$
					&& !(state.getOutTransitions().size() == 1
							&& state.getOutTransitions().get(0).getConditionEvent() == null
							&& state.getOutTransitions().get(0).getConditionExpression().equals("1")) //$NON-NLS-1$
					&& !state.getName().equals(
							eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size() - 1).getName())) {
				createTransitionsBackTransitionList(eccGen, state, state.getOutTransitions());
			}
		}
	}

	private static String createConditionExpression(String text) {
		if (text != null) {
			text = text.replace(":", ""); //$NON-NLS-1$//$NON-NLS-2$
			if ((text.split(";")).length > 1) { //$NON-NLS-1$
				text = text.replace(";", " AND"); //$NON-NLS-1$//$NON-NLS-2$
				text = text.substring(0, text.length() - 4);
			} else {
				text = text.replace(";", ""); //$NON-NLS-1$//$NON-NLS-2$
			}
			return text;
		}
		return ""; //$NON-NLS-1$
	}

	private void createTransitionsBack(final TestEccGenerator eccGen, final ECState from, final String ev) {
		for (final Event errEv : destinationFB.getInterfaceList().getEventInputs()) {
			if (!errEv.getName().equals(ev)) {
				eccGen.createTransitionFromTo(from, eccGen.getEcc().getStart(), errEv);
				eccGen.getEcc().getECTransition().get(eccGen.getEcc().getECTransition().size() - 1)
						.setConditionExpression(""); //$NON-NLS-1$
			}
		}
	}

	private void createTransitionsBackTransitionList(final TestEccGenerator eccGen, final ECState from,
			final List<ECTransition> outgoingTransitions) {
		for (final Event backEv : destinationFB.getInterfaceList().getEventInputs()) {
			boolean inList = false;
			for (final ECTransition outT : outgoingTransitions) {
				if (outT.getConditionEvent() != null && backEv.getName().equals(outT.getConditionEvent().getName())
						&& (outT.getConditionExpression() == null || outT.getConditionExpression().isEmpty())) {
					inList = true;
				}
			}
			if (!inList) {
				eccGen.createTransitionFromTo(from, eccGen.getEcc().getStart(), backEv);
				eccGen.getEcc().getECTransition().get(eccGen.getEcc().getECTransition().size() - 1)
						.setConditionExpression(""); //$NON-NLS-1$
			}
		}
	}

	private void createStates(final TestEccGenerator eccGen, final ECState state, final ECState to,
			final List<OutputPrimitive> listOutP, int cnt) {

		for (final OutputPrimitive firstColumn : listOutP) {
			eccGen.createState("S", cnt); //$NON-NLS-1$
			eccGen.getLastState().setName(NameRepository.createUniqueName(eccGen.getLastState(), "S1")); //$NON-NLS-1$
			cnt++;
			eccGen.createTransitionFromTo(state, eccGen.getLastState(),
					destinationFB.getInterfaceList().getEvent(firstColumn.getEvent()));
			eccGen.getEcc().getECTransition().get(eccGen.getEcc().getECTransition().size() - 1)
					.setConditionExpression(createConditionExpression(firstColumn.getParameters()));

			final List<OutputPrimitive> outPList = (List<OutputPrimitive>) EcoreUtil.copyAll(listOutP);
			outPList.remove(indexOutputPrim(firstColumn.getEvent(), outPList));
			if (outPList.isEmpty()) {
				eccGen.createTransitionFromTo(eccGen.getLastState(), to, null);
				return;
			}
			createStates(eccGen, eccGen.getLastState(), to, outPList, cnt);
			eccGen.increaseCaseCount();
		}
	}

	// get the index of the event in the output primitive list
	private static int indexOutputPrim(final String ev, final List<OutputPrimitive> list) {
		return IntStream.range(0, list.size()).filter(i -> list.get(i).getEvent().equals(ev)).findFirst()
				.orElse(list.size());
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
