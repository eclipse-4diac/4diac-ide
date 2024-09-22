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
import static org.eclipse.fordiac.ide.fb.interpreter.testappgen.GeneratedNameConstants.EVENT_NEXTCASE;
import static org.eclipse.fordiac.ide.fb.interpreter.testappgen.GeneratedNameConstants.EVENT_SUC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.AbstractBasicFBGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testcasemodel.TestSuite;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider.ECCContentAndLabelProvider;
import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class MatchFBGenerator extends AbstractBasicFBGenerator {

	private TestEccGenerator eccGen;
	private ECState sucState;
	private ECState errState;

	public MatchFBGenerator(final FBType type, final TestSuite testSuite) {
		super(type, testSuite);
	}

	public BasicFBType generateMatchFB() {
		createBasicFB();
		return destinationFB;
	}

	@Override
	protected void generateECC() {
		eccGen = new TestEccGenerator(destinationFB.getECC(), 0);
		createTimeOutPlug();
		final Algorithm timeout = createTimeOutAlgMatchFB(destinationFB, 300);

		// retrieve input events with "expected" in the name
		final List<Event> evExpected = destinationFB.getInterfaceList().getEventInputs().subList(0,
				destinationFB.getInterfaceList().getEventInputs().size()
						- sourceType.getInterfaceList().getEventOutputs().size());

		// loop over the expected events, create for each one a different testing route
		for (final Event event : evExpected) {
			// split the comment from the expected event, because information about the
			// expected events is stored there
			// it states how many events are expected and then the events are listed
			// f.e. "3.E5.E3.E4", "1.E8.1.E3"
			// it can also be empty, then there is no event expected
			final String comment = event.getComment();
			final String[] splitName = (comment).replace(";", "").split("\\."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			ECState comFrom = eccGen.getStartState();
			// error and success result states at the end, if the same testcase event comes
			// in the previous result event gets sent out

			// error state
			eccGen.createState("previous_error", splitName.length + 3); //$NON-NLS-1$
			errState = eccGen.getLastState();
			eccGen.createTransitionFromTo(errState, errState, event);
			errState.getECAction()
					.add(TestEccGenerator.createAction(destinationFB.getInterfaceList().getEventOutputs().get(0)));

			eccGen.increaseCaseCount();

			// success state
			eccGen.createState("previous_success", splitName.length + 3); //$NON-NLS-1$
			sucState = eccGen.getLastState();
			eccGen.createTransitionFromTo(sucState, sucState, event);
			sucState.getECAction()
					.add(TestEccGenerator.createAction(destinationFB.getInterfaceList().getEventOutputs().get(1)));

			eccGen.decreaseCaseCount();
			eccGen.resetStateCount();

			// 3 different scenarios, no event expected, one event expected, multiple events
			// expected

			// if no event is expected, the name of the input event is just "expected"
			if (event.getName().equals("expected")) { //$NON-NLS-1$
				// timeout path to check if there is actually no incoming event
				eccGen.createState("timeout"); //$NON-NLS-1$
				final ECAction act = TestEccGenerator.createAction();
				eccGen.createTransitionFromTo(comFrom, eccGen.getLastState(), getEventInput("expected")); //$NON-NLS-1$
				act.setAlgorithm(createTimeOutAlgMatchFB(destinationFB, 1000));
				act.setOutput(getStartTimeoutEvent());
				eccGen.getLastState().getECAction().add(act);
				comFrom = eccGen.getLastState();

				createErrorTransitions(comFrom, errState, false, ""); //$NON-NLS-1$
			} else {
				// start state from the test case
				eccGen.createStateWithIncomingConnection(eccGen.getEcc().getStart(), event, null, event.getName());
				final ECAction a = TestEccGenerator.createAction(getStartTimeoutEvent());
				a.setAlgorithm(timeout);
				eccGen.getLastState().getECAction().add(a);
				comFrom = eccGen.getLastState();

				// retrieve the information about how many events are coming
				final int[] numbers = Arrays.stream(splitName).filter(MatchFBGenerator::isInteger)
						.mapToInt(Integer::valueOf).toArray();
				final List<String> ev = Arrays.stream(splitName).filter(x -> !isInteger(x)).toList();

				// evCnt keeps track of which expected event was already handled
				int evCnt = 0;
				// create for each expected event a state
				for (final int number : numbers) {
					// scenario one expected event
					if (number == 1) {
						createPathOneEvent(comFrom, ev, comment, evCnt);
						eccGen.getLastState().getECAction().add(TestEccGenerator
								.createAction(destinationFB.getInterfaceList().getEventOutputs().getLast()));
						comFrom = eccGen.getLastState();
						evCnt++;

					}
					// if the number is 0 then there is no output event expected for that input
					// event but there are other test states in that testcase where output events
					// are expected
					else if (number == 0) {
						eccGen.createStateWithIncomingConnection(comFrom, null, null, "noEvent_sendNextCase"); //$NON-NLS-1$
//						eccGen.getLastState().getECAction().add(TestEccGenerator
//								.createAction(destinationFB.getInterfaceList().getEventOutputs().getLast()));

						comFrom = eccGen.getLastState();
					} else {
						// error transitions from the wait state
						createErrorTransitions(eccGen.getLastState(), errState, true,
								ev.subList(evCnt, evCnt + number));

						eccGen.createState("BindingState_1", eccGen.getStateCount() + number); //$NON-NLS-1$
						final ECState bsState = eccGen.getLastState();

						// add nextCase event output
						bsState.getECAction().add(TestEccGenerator
								.createAction(destinationFB.getInterfaceList().getEventOutputs().getLast()));

						// start of recursion
						createPathMultipleExpectedEvents(ev.subList(evCnt, evCnt + number), comFrom, event.getName(),
								bsState);

						comFrom = bsState;
						evCnt = evCnt + number;

					}
				}

//				if (testStateCount <= testSuite.getTestCases().get(i).getTestStates().size()) {
//					eccGen.createStateWithIncomingConnection(comFrom, null, null, "trigger_nextCases"); //$NON-NLS-1$
//					comFrom = eccGen.getLastState();
//					for (int j = testStateCount; j < testSuite.getTestCases().get(i).getTestStates().size(); j++) {
//						eccGen.getLastState().getECAction().add(TestEccGenerator
//								.createAction(destinationFB.getInterfaceList().getEventOutputs().getLast()));
//					}
//				}

				// overall waiting state to check for any additional events that are being sent
				createWaitState(comFrom, event.getName(), splitName.length + 1);
				comFrom = eccGen.getLastState();
			}

			// match state checks data if the block to test has data pins
			createMatchState(event, comFrom, splitName.length + 2);
			eccGen.increaseCaseCount();
			eccGen.increaseCaseCount();
			eccGen.increaseCaseCount();
		}
	}

	private void createPathOneEvent(final ECState comingFrom, final List<String> ev, final String comment,
			final int evCnt) {
		if (evCnt == 0) {
			// error transitions from the wait state
			createErrorTransitions(eccGen.getLastState(), errState, true, ev.get(0));
		}
		eccGen.createStateWithIncomingConnection(comingFrom, getEventInput(ev.get(evCnt)), "", //$NON-NLS-1$
				comment);
	}

	private void createWaitState(final ECState comingFrom, final String name, final int posCnt) {
		eccGen.createState(name, posCnt);
		eccGen.createTransitionFromTo(comingFrom, eccGen.getLastState(), null);
		eccGen.getLastState().setName(NameRepository.createUniqueName(eccGen.getLastState(), name + "_WAIT_1")); //$NON-NLS-1$
		final ECAction at = TestEccGenerator.createAction(getStartTimeoutEvent());

		at.setAlgorithm(createTimeOutAlgMatchFB(destinationFB, 1000));
		eccGen.getLastState().getECAction().add(at);
		createErrorTransitions(eccGen.getLastState(), errState, false, ""); //$NON-NLS-1$
	}

	protected static Algorithm createTimeOutAlgMatchFB(final BasicFBType fb, final int timeMS) {
		final String algText = TestGenBlockNames.MATCH_TIMEOUT_PINNAME + "." + TestGenBlockNames.TIMEOUT_PIN_NAME //$NON-NLS-1$
				+ " := TIME#" + timeMS + "ms + TIME#" + timeMS + "ms * " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ GeneratedNameConstants.TESTSIGNALGEN_CASECOUNT_PINNAME + "; \n"; //$NON-NLS-1$
		return TestEccGenerator.createAlgorithm(fb, "TimeOutAlgMatchState", algText); //$NON-NLS-1$
	}

	private void createPathMultipleExpectedEvents(final List<String> eventsToAdd, final ECState from, final String name,
			final ECState bindingState) {
		if (eventsToAdd.size() == 1) {
			eccGen.createTransitionFromTo(eccGen.getLastState(), bindingState, getEventInput(eventsToAdd.get(0)));
			return;
		}
		for (int i = 0; i < eventsToAdd.size(); i++) {
			final String event = eventsToAdd.get(i);
			eccGen.createStateWithIncomingConnection(from, getEventInput(eventsToAdd.get(i)), null, eventsToAdd.get(i));
			eccGen.getLastState().getECAction().add(TestEccGenerator.createAction(getStartTimeoutEvent()));
			createErrorTransitions(eccGen.getLastState(), errState, true, eventsToAdd);

			final List<String> eventsWithoutThis = new ArrayList<>(List.copyOf(eventsToAdd));
			eventsWithoutThis.remove(event);
			createPathMultipleExpectedEvents(eventsWithoutThis, eccGen.getLastState(), name, bindingState);

			eccGen.increaseCaseCount();
		}
	}

	private static boolean isInteger(final String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (final NumberFormatException e) {
			return false;
		}
	}

	private void createMatchState(final Event event, final ECState from, final int i) {
		eccGen.createState(event.getName() + "_match", i); //$NON-NLS-1$
		eccGen.getLastState()
				.setName(NameRepository.createUniqueName(eccGen.getLastState(), event.getName() + "_MATCH_1")); //$NON-NLS-1$
		final ECAction sucAct = TestEccGenerator.createAction();
		eccGen.getLastState().getECAction().add(sucAct);
		eccGen.createTransitionFromTo(from, eccGen.getLastState(), getTransitionTimeOutEvent());

		// check to see if there are outputs
		if (destinationFB.getInterfaceList().getInputVars().size() > 1) {
			final Algorithm alg = createMatchAlgorithm(destinationFB, destinationFB.getInterfaceList().getInputVars(),
					destinationFB.getInterfaceList().getOutputVars().get(0).getName());
			sucAct.setAlgorithm(alg);

			eccGen.createTransitionFromTo(eccGen.getLastState(), sucState, null);
			eccGen.getEcc().getECTransition().getLast().setConditionExpression("matchData"); //$NON-NLS-1$

			eccGen.createTransitionFromTo(eccGen.getLastState(), errState, null);
			eccGen.getEcc().getECTransition().getLast().setConditionExpression("NOT matchData"); //$NON-NLS-1$

		} else {
			eccGen.createTransitionFromTo(eccGen.getLastState(), sucState, null);
		}
	}

	private void createErrorTransitions(final ECState from, final ECState err, final boolean withTimeout,
			final String ev) {
		createErrorTransitions(from, err, withTimeout, Collections.singletonList(ev));
	}

	private void createErrorTransitions(final ECState from, final ECState err, final boolean withTimeout,
			final List<String> ev) {

		for (final Event errEv : destinationFB.getInterfaceList().getEventInputs()) {
			if (!ev.contains(errEv.getName())) {
				eccGen.createTransitionFromTo(from, err, errEv);
			}
		}
		final Event event = ECCContentAndLabelProvider.getInputEvents(destinationFB).stream()
				.filter(s -> ECCContentAndLabelProvider.getEventName(s)
						.equals(TestGenBlockNames.MATCH_TIMEOUT_PINNAME + ".TimeOut")) //$NON-NLS-1$
				.findFirst().orElse(null);
		if (event != null && withTimeout) {
			eccGen.createTransitionFromTo(from, err, event);
		}

	}

	private Event getEventInput(final String name) {
		return destinationFB.getInterfaceList().getEventInputs().stream().filter(n -> n.getName().equals(name))
				.findFirst().orElse(null);
	}

	public static Algorithm createMatchAlgorithm(final BasicFBType fb, final List<VarDeclaration> dataPins,
			final String outputPinName) {
		final StringBuilder algText = new StringBuilder();
		algText.append("IF "); //$NON-NLS-1$
		for (int i = 1; i < dataPins.size(); i += 2) {
			if (i > 1) {
				algText.append(" AND ");//$NON-NLS-1$
			}
			algText.append(dataPins.get(i).getName() + " = ");//$NON-NLS-1$
			algText.append(dataPins.get(i + 1).getName());
		}
		algText.append("\nTHEN\n");//$NON-NLS-1$
		algText.append(outputPinName + " := BOOL#TRUE;\n");//$NON-NLS-1$
		algText.append("ELSE\n"); //$NON-NLS-1$
		algText.append(outputPinName + " := BOOL#FALSE;\n");//$NON-NLS-1$
		algText.append("END_IF;\n"); //$NON-NLS-1$
		return TestEccGenerator.createAlgorithm(fb, "matchAlgo", algText.toString()); //$NON-NLS-1$
	}

	// an event is created for every unique testcase output, the given outputs from
	// the block to test must correspond with it to generate a success output
	// the match fb needs every event ouput from the block to test
	@Override
	protected List<Event> createInputEventList() {
		final List<Event> list = new ArrayList<>();
		list.addAll(getExpectedEvents(true));
		list.addAll(sourceType.getInterfaceList().getEventOutputs().stream()
				.map(event -> createInputEvent(event.getName())).toList());
		return list;
	}

	@Override
	protected List<Event> createOutputEventList() {
		return List.of(createOutputEvent(EVENT_ERR), createOutputEvent(EVENT_SUC), createOutputEvent(EVENT_NEXTCASE));
	}

	// match fb needs for each data output on the block to test an expected and
	// received data input
	@Override
	protected List<VarDeclaration> createInputDataList() {
		final List<VarDeclaration> list = new ArrayList<>();
		list.add(createInputVarDecl(sourceType.getTypeLibrary().getDataTypeLibrary().getType(FordiacKeywords.INT),
				GeneratedNameConstants.TESTSIGNALGEN_CASECOUNT_PINNAME));
		for (final VarDeclaration varDeclaration : sourceType.getInterfaceList().getOutputVars()) {
			list.add(createInputVarDecl(varDeclaration.getType(), varDeclaration.getName() + "_expected")); //$NON-NLS-1$
			list.add(createInputVarDecl(varDeclaration.getType(), varDeclaration.getName() + "_received")); //$NON-NLS-1$
		}
		return list;
	}

	// if there are data output pins, a bool output data pin is created for the
	// match fb indicating whether the data matches or not
	@Override
	protected List<VarDeclaration> createOutputDataList() {
		final List<VarDeclaration> list = new ArrayList<>();
		if (!sourceType.getInterfaceList().getOutputVars().isEmpty()) {
			list.add(createOutputVarDecl(sourceType.getTypeLibrary().getDataTypeLibrary().getType(FordiacKeywords.BOOL),
					"matchData"));//$NON-NLS-1$

		}
		return list;
	}

	@Override
	protected String getTypeName() {
		return sourceType.getName() + "_MATCH"; //$NON-NLS-1$
	}

	@Override
	protected FBType getSourceFB() {
		return sourceType;
	}
}
