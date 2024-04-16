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

	private final static String nameEnding = "_WAIT_1"; //$NON-NLS-1$

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

		final List<Event> evExpected = destinationFB.getInterfaceList().getEventInputs().subList(0,
				destinationFB.getInterfaceList().getEventInputs().size()
						- sourceType.getInterfaceList().getEventOutputs().size());
		for (final Event event : evExpected) {

			// each splitName segment is an event name that is expected
			final String comment = event.getComment();
			final String[] splitName = comment.split("\\."); //$NON-NLS-1$

			// error and success result states at the end, if the same testcase event comes
			// in the previous result event gets sent out
			// error
			eccGen.createState("previous_error", splitName.length + 1); //$NON-NLS-1$
			eccGen.getLastState().setName(NameRepository.createUniqueName(eccGen.getLastState(), "previous_error_1")); //$NON-NLS-1$
			final ECState errState = eccGen.getLastState();
			eccGen.createTransitionFromTo(errState, errState, event);
			final ECAction erAct = TestEccGenerator.createAction();
			erAct.setOutput(destinationFB.getInterfaceList().getEventOutputs().get(0));
			errState.getECAction().add(erAct);
			eccGen.increaseCaseCount();
			// success
			eccGen.createState("previous_success", splitName.length + 1); //$NON-NLS-1$
			eccGen.getLastState().setName(NameRepository.createUniqueName(eccGen.getLastState(), "previous_success")); //$NON-NLS-1$
			final ECState sucState = eccGen.getLastState();
			eccGen.createTransitionFromTo(sucState, sucState, event);
			final ECAction suAct = TestEccGenerator.createAction();
			suAct.setOutput(destinationFB.getInterfaceList().getEventOutputs().get(1));
			eccGen.getLastState().getECAction().add(suAct);
			eccGen.decreaseCaseCount();

			// if there are no output events expected, just a timeout state is created
			if (event.getName().equals("expected")) { //$NON-NLS-1$
				// createPathTimeOut(eccGen, sucState, errState, ""); //$NON-NLS-1$
				createMatchState(eccGen, event, sucState, errState, 1);
			} else {
				for (int i = 0; i < splitName.length; i++) {
					// create wait state for input
					eccGen.createState(splitName[i] + "_wait", i); //$NON-NLS-1$
					eccGen.getLastState()
							.setName(NameRepository.createUniqueName(eccGen.getLastState(), splitName[i] + nameEnding));

					// timeout action for wait state
					final ECAction waitAct = createWaitAct();
					if (i == 0) { // first event in a row needs a transition from the start start
						eccGen.createTransitionFromTo(eccGen.getEcc().getStart(), eccGen.getLastState(), event);
						waitAct.setAlgorithm(createTimeOutAlg(destinationFB));
					} else { // create transition from the last
						// wait state to the new one
						eccGen.createTransitionFromTo(eccGen.getNTimesLast(2), eccGen.getLastState(),
								getEventInput(splitName[i - 1]));
					}
					eccGen.getLastState().getECAction().add(waitAct);
					eccGen.increaseCaseCount();

					// check how many output events are expected
					final List<Event> outputs = new ArrayList<>();
					int outputCount = 0;
					for (int j = i + 1; j < splitName.length; j++) {
						if (isInputEvent(splitName[j])) {
							break;
						}
						outputs.add(getEventInput(splitName[j]));
						outputCount++;
					}
					if (outputCount == 1) {
						eccGen.createState(splitName[i + 1] + "_wait", i); //$NON-NLS-1$
						eccGen.getLastState().setName(
								NameRepository.createUniqueName(eccGen.getLastState(), splitName[i] + nameEnding));
						eccGen.createTransitionFromTo(eccGen.getNTimesLast(1), eccGen.getLastState(),
								getEventInput(splitName[i + 1]));
						eccGen.getLastState().getECAction().add(createWaitAct());
					} else {
						// create every possible combinations for output event order
						for (int j = i + 1; j < i + outputCount; j++) {
							eccGen.createState("BS", j); //$NON-NLS-1$
							eccGen.getLastState()
									.setName(NameRepository.createUniqueName(eccGen.getLastState(), "BS_1")); //$NON-NLS-1$

							createOutputCombinationsRecursive(eccGen, outputs, eccGen.getLastState(),
									eccGen.getNTimesLast(1), sucState, errState);
						}
					}

					if (i == splitName.length - 1) {
						// before the success/match state a state with timeout is inserted to check for
						// any additional incoming events
						// createPathTimeOut(eccGen, sucState, errState, splitName[i]);
						// success/match state, sends success event when reached and checks data
						createMatchState(eccGen, event, sucState, errState, i);
					}
				}

				/*
				 * eccGen.createState(splitName[i] + "_wait", i); //$NON-NLS-1$
				 * eccGen.getLastState()
				 * .setName(NameRepository.createUniqueName(eccGen.getLastState(), splitName[i]
				 * + "_WAIT_1")); //$NON-NLS-1$
				 *
				 * // timeout action for wait state final ECAction waitAct =
				 * TestEccGenerator.createAction();
				 *
				 * waitAct.setOutput(ECCContentAndLabelProvider.getOutputEvents(destinationFB).
				 * stream() .filter(s -> s.getName().equals("START")).findFirst().orElse(null));
				 * waitAct.setAlgorithm(createTimeOutAlg(destinationFB));
				 * eccGen.getLastState().getECAction().add(waitAct);
				 *
				 * if (i == 0) { // first event in a row needs a transition from the start start
				 * eccGen.createTransitionFromTo(eccGen.getEcc().getStart(),
				 * eccGen.getLastState(), event); } else { // create transition from the last
				 * wait state to the new one
				 * eccGen.createTransitionFromTo(eccGen.getNTimesLast(2), eccGen.getLastState(),
				 * getEventInput(splitName[i - 1])); } eccGen.increaseCaseCount();
				 *
				 * // error state eccGen.createState("ERROR", i); //$NON-NLS-1$
				 * eccGen.getLastState().setName(NameRepository.createUniqueName(eccGen.
				 * getLastState(), "ERROR_1")); //$NON-NLS-1$ createErrorTransitions(eccGen,
				 * splitName[i]);
				 *
				 * eccGen.createTransitionFromTo(eccGen.getNTimesLast(1), eccGen.getLastState(),
				 * ECCContentAndLabelProvider.getInputEvents(destinationFB).stream() .filter(s
				 * -> ECCContentAndLabelProvider.getEventName(s).equals("_ETimeOut.TimeOut"))
				 * //$NON-NLS-1$ .findFirst().orElse(null));
				 * eccGen.createTransitionFromTo(eccGen.getLastState(), errState, null);
				 * eccGen.decreaseCaseCount();
				 */
			}

			eccGen.increaseCaseCount();
			eccGen.increaseCaseCount();
		}

	}

	/*
	 * private void createAllOutputCombinations(final TestEccGenerator eccGen, final
	 * List<Event> outputs, final ECState bindingState, final EC final ECState
	 * sucState, final ECState errState) { for (final Event ev : outputs) {
	 * eccGen.createState( 0); createOutputCombinationsRecursive(eccGen, outputs,
	 * eccGen.getLastState(), bindingState, sucState, errState); }
	 *
	 * }
	 */

	private void createOutputCombinationsRecursive(final TestEccGenerator eccGen, final List<Event> outputs,
			final ECState bindingState, final ECState from, final ECState sucState, final ECState errState) {
		if (outputs.isEmpty()) {
			eccGen.createTransitionFromTo(from, bindingState, null);
		} else {
			for (final Event output : outputs) {
				eccGen.createState(output.getName() + "_wait", 100); //$NON-NLS-1$
				eccGen.getLastState()
						.setName(NameRepository.createUniqueName(eccGen.getLastState(), output.getName() + nameEnding));

				eccGen.createTransitionFromTo(from, eccGen.getLastState(), output);
				createErrorTransitions(eccGen, from, errState, output.getName());

				final List<Event> listWithRemovedOutput = new ArrayList<>();
				Collections.copy(listWithRemovedOutput, outputs);
				listWithRemovedOutput.remove(output);
				createOutputCombinationsRecursive(eccGen, listWithRemovedOutput, bindingState, eccGen.getLastState(),
						sucState, errState);
			}
		}

	}

	private ECAction createWaitAct() {
		final ECAction waitAct = TestEccGenerator.createAction();
		waitAct.setOutput(ECCContentAndLabelProvider.getOutputEvents(destinationFB).stream()
				.filter(s -> s.getName().equals("START")).findFirst().orElse(null)); //$NON-NLS-1$
		return waitAct;
	}

	private void createStateWithTransitions(final TestEccGenerator eccGen, final String name, final int i) {
		eccGen.createState(name + "_wait", i); //$NON-NLS-1$
		eccGen.getLastState().setName(NameRepository.createUniqueName(eccGen.getLastState(), name + nameEnding));

	}

	private boolean isInputEvent(final String eventName) {
		if (ECCContentAndLabelProvider.getInputEvents(destinationFB).stream()
				.filter(s -> ECCContentAndLabelProvider.getEventName(s).equals(eventName)).findFirst()
				.orElse(null) != null) {
			return true;
		}
		return false;

	}

	/*
	 * private void createTimeOutState(final TestEccGenerator eccGen, final String
	 * name, final ECState errState, final int i) { // state
	 * eccGen.createState("WAIT", i + 1); //$NON-NLS-1$
	 * eccGen.getLastState().setName(NameRepository.createUniqueName(eccGen.
	 * getLastState(), "WAIT_1")); //$NON-NLS-1$ // action final ECAction waitAct =
	 * TestEccGenerator.createAction();
	 *
	 * waitAct.setOutput(ECCContentAndLabelProvider.getOutputEvents(destinationFB).
	 * stream() .filter(s -> s.getName().equals("START")).findFirst().orElse(null));
	 * //$NON-NLS-1$ // algorithm
	 * waitAct.setAlgorithm(createTimeOutAlg(destinationFB));
	 * eccGen.getLastState().getECAction().add(waitAct); // transition to
	 * timeoutState eccGen.createTransitionFromTo(eccGen.getNTimesLast(2),
	 * eccGen.getLastState(), getEventInput(name)); eccGen.increaseCaseCount();
	 *
	 * // error state eccGen.createState("ERROR", i + 1); //$NON-NLS-1$
	 * eccGen.getLastState().setName(NameRepository.createUniqueName(eccGen.
	 * getLastState(), "ERROR_1")); //$NON-NLS-1$ // createErrorTransitions(eccGen,
	 * ""); //$NON-NLS-1$ eccGen.createTransitionFromTo(eccGen.getLastState(),
	 * errState, null); eccGen.decreaseCaseCount();
	 *
	 * }
	 */

	private void createMatchState(final TestEccGenerator eccGen, final Event event, final ECState sucState,
			final ECState errState, final int i) {
		eccGen.createState(event.getName() + "_match", i + 2); //$NON-NLS-1$
		eccGen.getLastState()
				.setName(NameRepository.createUniqueName(eccGen.getLastState(), event.getName() + "_MATCH_1")); //$NON-NLS-1$
		final ECAction sucAct = TestEccGenerator.createAction();
		eccGen.getLastState().getECAction().add(sucAct);

		/*
		 * eccGen.createTransitionFromTo(eccGen.getLastState(), suc,
		 * ECCContentAndLabelProvider.getInputEvents(destinationFB)
		 * .get(ECCContentAndLabelProvider.getInputEvents(destinationFB).size() - 1));
		 */

		eccGen.createTransitionFromTo(eccGen.getLastState(), eccGen.getNTimesLast(1),
				ECCContentAndLabelProvider.getInputEvents(destinationFB).stream()
						.filter(s -> ECCContentAndLabelProvider.getEventName(s).equals("_ETimeOut.TimeOut")).findFirst() //$NON-NLS-1$
						.orElse(null));

		// check to see if there are outputs
		if (!destinationFB.getInterfaceList().getInputVars().isEmpty()) {
			final Algorithm alg = createMatchAlgorithm(destinationFB, destinationFB.getInterfaceList().getInputVars(),
					destinationFB.getInterfaceList().getOutputVars().get(0).getName());
			sucAct.setAlgorithm(alg);
			eccGen.getEcc().getECTransition().get(eccGen.getEcc().getECTransition().size() - 2)
					.setConditionExpression("matchData"); //$NON-NLS-1$
			eccGen.getEcc().getECTransition().get(eccGen.getEcc().getECTransition().size() - 1)
					.setConditionExpression("NOT matchData"); //$NON-NLS-1$
		} else {
			eccGen.createTransitionFromTo(eccGen.getLastState(), sucState, null);
		}

	}

	private void createPathTimeOut(final TestEccGenerator eccGen, final ECState err, final String name,
			final Event previous) {
		eccGen.createState("timeout", 0); //$NON-NLS-1$
		eccGen.getLastState().setName(NameRepository.createUniqueName(eccGen.getLastState(), "TIMEOUT_1")); //$NON-NLS-1$
		final ECAction act = TestEccGenerator.createAction();
		if (name.equals("")) { //$NON-NLS-1$
			eccGen.createTransitionFromTo(eccGen.getEcc().getStart(), eccGen.getLastState(), getEventInput("expected")); //$NON-NLS-1$
			act.setAlgorithm(createTimeOutAlg(destinationFB));
		} else {
			eccGen.createTransitionFromTo(eccGen.getNTimesLast(1), eccGen.getLastState(), null);
		}
		act.setOutput(ECCContentAndLabelProvider.getOutputEvents(destinationFB).stream()
				.filter(s -> s.getName().equals("START")).findFirst().orElse(null)); //$NON-NLS-1$
		eccGen.getLastState().getECAction().add(act);

		createErrorTransitions(eccGen, eccGen.getLastState(), err, name); // $NON-NLS-1$
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
			final String ev) {

		for (final Event errEv : destinationFB.getInterfaceList().getEventInputs()) {
			if (!errEv.getName().equals(ev)) {
				eccGen.createTransitionFromTo(from, err, errEv);
			}
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
