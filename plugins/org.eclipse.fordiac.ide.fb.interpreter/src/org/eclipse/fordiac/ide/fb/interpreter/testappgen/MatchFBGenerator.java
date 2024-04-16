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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.AbstractBasicFBGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testcasemodel.TestSuite;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider.ECCContentAndLabelProvider;
import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class MatchFBGenerator extends AbstractBasicFBGenerator {

	private static final String START_STATE = "START"; //$NON-NLS-1$
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
	protected String getTypeName() {
		return sourceType.getName() + "_MATCH"; //$NON-NLS-1$
	}

	@Override
	protected FBType getSourceFB() {
		return sourceType;
	}

	@Override
	protected void generateECC() {
		final TestEccGenerator eccGen = new TestEccGenerator(destinationFB.getECC(), 0);
		createTimeOutPlug();
		createTimeOutAlg(destinationFB);

		// retrieve input events with "expected" in the name
		final List<Event> evExpected = destinationFB.getInterfaceList().getEventInputs().subList(0,
				destinationFB.getInterfaceList().getEventInputs().size()
						- sourceType.getInterfaceList().getEventOutputs().size());

		// loop over the expected events, create for each one a different testing route
		for (final Event event : evExpected) {
			// split the comment from the expected event, because additional info is saved
			// there
			final String comment = event.getComment();
			final String[] splitName = (comment).replace(";", "").split("\\."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			ECState lastState = null;
			// error and success result states at the end, if the same testcase event comes
			// in the previous result event gets sent out

			// error state
			eccGen.createState("previous_error", splitName.length + 2); //$NON-NLS-1$
			errState = eccGen.getLastState();
			eccGen.createTransitionFromTo(errState, errState, event);
			errState.getECAction()
					.add(TestEccGenerator.createAction(destinationFB.getInterfaceList().getEventOutputs().get(0)));
			eccGen.increaseCaseCount();

			// success state
			eccGen.createState("previous_success", splitName.length + 2); //$NON-NLS-1$
			sucState = eccGen.getLastState();
			eccGen.createTransitionFromTo(sucState, sucState, event);
			sucState.getECAction()
					.add(TestEccGenerator.createAction(destinationFB.getInterfaceList().getEventOutputs().get(1)));
			eccGen.decreaseCaseCount();

			// 3 different scenarios, no event expected, one event expected, multiple events
			// expected

			// if no event is expected, the name of the input event is just "expected"
			if (event.getName().equals("expected")) { //$NON-NLS-1$
				// timeout path to check if there is actually no incoming event
				createPathTimeOut(eccGen, errState, ""); //$NON-NLS-1$
				lastState = eccGen.getLastState();
			} else {
				// start state from the test case
				eccGen.createStateWithIncomingConnection(eccGen.getEcc().getStart(), event, null, event.getName(), 0);
				eccGen.getLastState().getECAction()
						.add(TestEccGenerator.createAction(ECCContentAndLabelProvider.getOutputEvents(destinationFB)
								.stream().filter(s -> s.getName().equals(START_STATE)).findFirst().orElse(null)));

				// retrieve the information about how many events are coming
				final int[] numbers = Arrays.stream(splitName).filter(MatchFBGenerator::isInteger)
						.mapToInt(Integer::valueOf).toArray();
				final List<String> ev = Arrays.stream(splitName).filter(x -> !isInteger(x)).toList();

				// evCnt keeps track of which expected event was already handled
				int evCnt = 0;
				// helps with positioning the elements
				int posCnt = 1;

				ECState comingFrom = eccGen.getLastState();

				// create for each expected event a state
				for (final int number : numbers) {
					// scenario one expected event
					if (number == 1) {
						// error transitions from the wait state
						createErrorTransitions(eccGen, eccGen.getLastState(), errState, true, ev.get(evCnt));

						eccGen.createStateWithIncomingConnection(comingFrom, getEventInput(ev.get(evCnt)), "", //$NON-NLS-1$
								comment, posCnt);
						eccGen.getLastState().getECAction()
								.add(TestEccGenerator.createAction(ECCContentAndLabelProvider
										.getOutputEvents(destinationFB).stream()
										.filter(s -> s.getName().equals(START_STATE)).findFirst().orElse(null)));
						createErrorTransitions(eccGen, comingFrom, errState, true, ev.get(evCnt));
						comingFrom = eccGen.getLastState();
						lastState = eccGen.getLastState();
						posCnt++;
						evCnt++;
					} else {
						createErrorTransitions(eccGen, eccGen.getLastState(), errState, true,
								ev.subList(evCnt, evCnt + number));

						eccGen.createState("BindingState_1", splitName.length); //$NON-NLS-1$
						lastState = eccGen.getLastState();
						createPathMultipleExpectedEvents(eccGen, ev.subList(evCnt, evCnt + number), comingFrom,
								event.getName(), posCnt, lastState);
						comingFrom = eccGen.getLastState();

					}
				}

			}
			// connection from last state to the time out and then also match state
			createMatchState(eccGen, event, lastState, splitName.length + 1);
			eccGen.createTransitionFromTo(eccGen.getLastState(), sucState, null);
			eccGen.increaseCaseCount();
			eccGen.increaseCaseCount();
		}
	}

	private void createPathMultipleExpectedEvents(final TestEccGenerator eccGen, final List<String> eventsToAdd,
			final ECState from, final String name, int position, final ECState bindingState) {
		if (eventsToAdd.size() == 1) {
			eccGen.createTransitionFromTo(eccGen.getLastState(), bindingState, getEventInput(eventsToAdd.get(0)));
			return;
		}
		for (int i = 0; i < eventsToAdd.size(); i++) {
			final String event = eventsToAdd.get(i);
			eccGen.createStateWithIncomingConnection(from, getEventInput(eventsToAdd.get(i)), null, eventsToAdd.get(i),
					position);
			position++;
			eccGen.getLastState().getECAction()
					.add(TestEccGenerator.createAction(ECCContentAndLabelProvider.getOutputEvents(destinationFB)
							.stream().filter(s -> s.getName().equals(START_STATE)).findFirst().orElse(null)));
			createErrorTransitions(eccGen, eccGen.getLastState(), errState, true, eventsToAdd);

			final List<String> eventsWithoutThis = eventsToAdd.stream().filter(x -> !x.equals(event)).toList();
			createPathMultipleExpectedEvents(eccGen, eventsWithoutThis, eccGen.getLastState(), name, i, bindingState);

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

	private void createMatchState(final TestEccGenerator eccGen, final Event event, final ECState from, final int i) {
		eccGen.createState(event.getName() + "_match", i); //$NON-NLS-1$
		eccGen.getLastState()
				.setName(NameRepository.createUniqueName(eccGen.getLastState(), event.getName() + "_MATCH_1")); //$NON-NLS-1$
		final ECAction sucAct = TestEccGenerator.createAction();
		eccGen.getLastState().getECAction().add(sucAct);
		eccGen.createTransitionFromTo(from, eccGen.getLastState(),
				ECCContentAndLabelProvider.getInputEvents(destinationFB).stream()
						.filter(s -> ECCContentAndLabelProvider.getEventName(s).equals("_ETimeOut.TimeOut")).findFirst() //$NON-NLS-1$
						.orElse(null));

		// check to see if there are outputs
		if (!destinationFB.getInterfaceList().getInputVars().isEmpty()) {
			final Algorithm alg = createMatchAlgorithm(destinationFB, destinationFB.getInterfaceList().getInputVars(),
					destinationFB.getInterfaceList().getOutputVars().get(0).getName());
			sucAct.setAlgorithm(alg);

			eccGen.createTransitionFromTo(eccGen.getLastState(), sucState, null);
			eccGen.getEcc().getECTransition().get(eccGen.getEcc().getECTransition().size() - 1)
					.setConditionExpression("matchData"); //$NON-NLS-1$

			eccGen.createTransitionFromTo(eccGen.getLastState(), errState, null);
			eccGen.getEcc().getECTransition().get(eccGen.getEcc().getECTransition().size() - 1)
					.setConditionExpression("NOT matchData"); //$NON-NLS-1$

		} else {
			eccGen.createTransitionFromTo(eccGen.getLastState(), sucState, null);
		}
	}

	private void createPathTimeOut(final TestEccGenerator eccGen, final ECState err, final String name) {
		eccGen.createState("timeout", 0); //$NON-NLS-1$
		eccGen.getLastState().setName(NameRepository.createUniqueName(eccGen.getLastState(), "TIMEOUT_1")); //$NON-NLS-1$
		final ECAction act = TestEccGenerator.createAction();

		eccGen.createTransitionFromTo(eccGen.getEcc().getStart(), eccGen.getLastState(), getEventInput("expected")); //$NON-NLS-1$
		act.setAlgorithm(createTimeOutAlg(destinationFB));

		act.setOutput(ECCContentAndLabelProvider.getOutputEvents(destinationFB).stream()
				.filter(s -> s.getName().equals(START_STATE)).findFirst().orElse(null));
		eccGen.getLastState().getECAction().add(act);

		createErrorTransitions(eccGen, eccGen.getLastState(), err, false, name); // $NON-NLS-1$
	}

	private AdapterDeclaration createTimeOutPlug() {
		final TypeLibrary typelib = entry.getTypeLibrary();
		final AdapterTypeEntry timeOutEntry = typelib.getAdapterTypeEntry("ATimeOut"); //$NON-NLS-1$
		final CreateInterfaceElementCommand cmd = new CreateInterfaceElementCommand(timeOutEntry.getType(), "_ETimeOut", //$NON-NLS-1$
				sourceType.getInterfaceList(), false, -1);

		try {
			cmd.execute();
		} catch (final Exception e) {
			FordiacLogHelper.logError(e.getMessage());
		}

		final AdapterDeclaration timeOutPlug = (AdapterDeclaration) cmd.getCreatedElement();
		destinationFB.getInterfaceList().getPlugs().add(timeOutPlug);
		return timeOutPlug;
	}

	public static Algorithm createTimeOutAlg(final BasicFBType fb) {
		final String algText = "_ETimeOut.DT := TIME#1000ms; \n";//$NON-NLS-1$
		return TestEccGenerator.createAlgorithm(fb, "TimeOutAlg", algText); //$NON-NLS-1$
	}

	private void createErrorTransitions(final TestEccGenerator eccGen, final ECState from, final ECState err,
			final boolean withTimeout, final String ev) {
		createErrorTransitions(eccGen, from, err, withTimeout, Collections.singletonList(ev));
	}

	private void createErrorTransitions(final TestEccGenerator eccGen, final ECState from, final ECState err,
			final boolean withTimeout, final List<String> ev) {

		for (final Event errEv : destinationFB.getInterfaceList().getEventInputs()) {
			if (!ev.contains(errEv.getName())) {
				eccGen.createTransitionFromTo(from, err, errEv);
			}
		}
		final Event event = ECCContentAndLabelProvider.getInputEvents(destinationFB).stream()
				.filter(s -> ECCContentAndLabelProvider.getEventName(s).equals("_ETimeOut.TimeOut")).findFirst() //$NON-NLS-1$
				.orElse(null);
		if (event != null && withTimeout) {
			eccGen.createTransitionFromTo(from, err, event);
		}

	}

	private Event getEventInput(final String name) {
		return destinationFB.getInterfaceList().getEventInputs().stream().filter(n -> n.getName().equals(name))
				.findFirst().orElse(null);
	}

	public static Algorithm createMatchAlgorithm(final BasicFBType fb, final List<VarDeclaration> inputData,
			final String outputPinName) {
		final StringBuilder algText = new StringBuilder();
		algText.append("IF "); //$NON-NLS-1$
		for (int i = 0; i < inputData.size(); i += 2) {
			if (i > 0) {
				algText.append(" AND ");//$NON-NLS-1$
			}
			algText.append(inputData.get(i).getName() + " = ");//$NON-NLS-1$
			algText.append(inputData.get(i + 1).getName());
		}
		algText.append("\nTHEN\n");//$NON-NLS-1$
		algText.append(outputPinName + ":= BOOL#TRUE;\n");//$NON-NLS-1$
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
				.map(event -> createEvent(event.getName(), true)).toList());
		return list;
	}

	@Override
	protected List<Event> createOutputEventList() {
		return List.of(createEvent("ERR", false), createEvent("SUC", false)); //$NON-NLS-1$//$NON-NLS-2$
	}

	// match fb needs for each data output on the block to test an expected and
	// received data input
	@Override
	protected List<VarDeclaration> createInputDataList() {
		final List<VarDeclaration> list = new ArrayList<>();
		for (final VarDeclaration varDeclaration : sourceType.getInterfaceList().getOutputVars()) {
			list.add(createVarDeclaration(varDeclaration.getType(), varDeclaration.getName() + "_expected", true)); //$NON-NLS-1$
			list.add(createVarDeclaration(varDeclaration.getType(), varDeclaration.getName() + "_received", true)); //$NON-NLS-1$
		}
		return list;
	}

	// if there are data output pins, a bool output data pin is created for the
	// match fb indicating wheter the data matches or not
	@Override
	protected List<VarDeclaration> createOutputDataList() {
		final List<VarDeclaration> list = new ArrayList<>();
		if (!sourceType.getInterfaceList().getOutputVars().isEmpty()) {
			list.add(createVarDeclaration(
					sourceType.getTypeLibrary().getDataTypeLibrary().getType(FordiacKeywords.BOOL), "matchData", //$NON-NLS-1$
					false));
		}
		return list;
	}
}
