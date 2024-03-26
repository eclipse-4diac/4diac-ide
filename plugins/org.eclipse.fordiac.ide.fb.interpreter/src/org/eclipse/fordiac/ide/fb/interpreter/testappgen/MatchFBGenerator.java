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

public class MatchFBGenerator extends AbstractBasicFBGenerator {

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
		final AdapterDeclaration plug = createTimeOutPlug();

		final List<Event> evExpected = destinationFB.getInterfaceList().getEventInputs().subList(0,
				destinationFB.getInterfaceList().getEventInputs().size()
						- sourceType.getInterfaceList().getEventOutputs().size());
		for (final Event event : evExpected) {
			if (event.getName().equals("expected")) { //$NON-NLS-1$
				createPathTimeOut(eccGen, plug);
			}
			// each splitName segment is an event name that is expected
			final String[] splitName = event.getName().split("_"); //$NON-NLS-1$

			// flip flop at end
			// error
			eccGen.createState("previous_error", splitName.length + 1); //$NON-NLS-1$
			eccGen.getLastState().setName(NameRepository.createUniqueName(eccGen.getLastState(), "previous_error_1")); //$NON-NLS-1$
			final ECState errState = eccGen.getLastState();
			eccGen.createTransitionFromTo(errState, errState, event);
			final ECAction erAct = TestEccGenerator.createAction();
			erAct.setOutput(destinationFB.getInterfaceList().getEventOutputs().get(0));
			errState.getECAction().add(erAct);
			eccGen.increaseCaseCount();
			// Success
			eccGen.createState("previous_success", splitName.length + 1); //$NON-NLS-1$
			eccGen.getLastState().setName(NameRepository.createUniqueName(eccGen.getLastState(), "previous_success")); //$NON-NLS-1$
			final ECState sucState = eccGen.getLastState();
			eccGen.createTransitionFromTo(sucState, sucState, event);
			final ECAction suAct = TestEccGenerator.createAction();
			suAct.setOutput(destinationFB.getInterfaceList().getEventOutputs().get(1));
			eccGen.getLastState().getECAction().add(suAct);

			for (int i = 0; i < splitName.length - 1; i++) {
				eccGen.createState(splitName[i] + "_wait", i); //$NON-NLS-1$
				eccGen.getLastState()
						.setName(NameRepository.createUniqueName(eccGen.getLastState(), splitName[i] + "_WAIT_1")); //$NON-NLS-1$

				// timeout action for wait state
				final ECAction waitAct = TestEccGenerator.createAction();

				waitAct.setOutput(ECCContentAndLabelProvider.getOutputEvents(destinationFB).stream()
						.filter(s -> s.getName().equals("START")).findFirst().orElse(null));
				waitAct.setAlgorithm(createTimeOutAlg(destinationFB));
				eccGen.getLastState().getECAction().add(waitAct);

				if (i == 0) {
					// first event in a row needs a transition from the start start
					eccGen.createTransitionFromTo(eccGen.getEcc().getStart(), eccGen.getLastState(), event);
				} else {
					// create transition from the last wait state to the new one
					eccGen.createTransitionFromTo(eccGen.getNTimesLast(2), eccGen.getLastState(),
							getEventInput(splitName[i - 1]));
				}
				eccGen.increaseCaseCount();

				// error state
				eccGen.createState("ERROR", i); //$NON-NLS-1$
				eccGen.getLastState().setName(NameRepository.createUniqueName(eccGen.getLastState(), "ERROR_1")); //$NON-NLS-1$
				createErrorTransitions(eccGen, splitName[i]);
				eccGen.createTransitionFromTo(eccGen.getNTimesLast(1), eccGen.getLastState(),
						ECCContentAndLabelProvider.getInputEvents(destinationFB)
								.get(ECCContentAndLabelProvider.getInputEvents(destinationFB).size() - 1));
				eccGen.createTransitionFromTo(eccGen.getLastState(), errState, null);
				eccGen.decreaseCaseCount();

				if (i == splitName.length - 2) {
					// before the success/match state a state with timeout is inserted to check for
					// any additional incoming events
					createTimeOutState(eccGen, splitName[i], errState, i);
					// success/match state, sends success event when reached and checks data
					createMatchState(eccGen, event, sucState, errState, i);
				}
			}

			eccGen.increaseCaseCount();
			eccGen.increaseCaseCount();
		}
	}

	private void createTimeOutState(final TestEccGenerator eccGen, final String name, final ECState errState,
			final int i) {
		// state
		eccGen.createState("WAIT", i + 1); //$NON-NLS-1$
		eccGen.getLastState().setName(NameRepository.createUniqueName(eccGen.getLastState(), "WAIT_1")); //$NON-NLS-1$
		// action
		final ECAction waitAct = TestEccGenerator.createAction();

		waitAct.setOutput(ECCContentAndLabelProvider.getOutputEvents(destinationFB).stream()
				.filter(s -> s.getName().equals("START")).findFirst().orElse(null)); //$NON-NLS-1$
		// algorithm
		waitAct.setAlgorithm(createTimeOutAlg(destinationFB));
		eccGen.getLastState().getECAction().add(waitAct);
		// transition to timeoutState
		eccGen.createTransitionFromTo(eccGen.getNTimesLast(2), eccGen.getLastState(), getEventInput(name));
		eccGen.increaseCaseCount();

		// error state
		eccGen.createState("ERROR", i + 1); //$NON-NLS-1$
		eccGen.getLastState().setName(NameRepository.createUniqueName(eccGen.getLastState(), "ERROR_1")); //$NON-NLS-1$
		createErrorTransitions(eccGen, ""); //$NON-NLS-1$
		eccGen.createTransitionFromTo(eccGen.getLastState(), errState, null);
		eccGen.decreaseCaseCount();

	}

	private void createMatchState(final TestEccGenerator eccGen, final Event event, final ECState sucState,
			final ECState errState, final int i) {
		eccGen.createState(event.getName() + "_match", i + 2); //$NON-NLS-1$
		eccGen.getLastState()
				.setName(NameRepository.createUniqueName(eccGen.getLastState(), event.getName() + "_MATCH_1")); //$NON-NLS-1$
		final ECAction sucAct = TestEccGenerator.createAction();
		eccGen.getLastState().getECAction().add(sucAct);
		// check to see if there are outputs, if so an algorithm needs to be created to
		// check them
		if (!destinationFB.getInterfaceList().getOutputVars().isEmpty()) {

			final Algorithm alg = createMatchAlgorithm(destinationFB, destinationFB.getInterfaceList().getInputVars(),
					destinationFB.getInterfaceList().getOutputVars().get(0).getName());
			sucAct.setAlgorithm(alg);
		}

		eccGen.createTransitionFromTo(eccGen.getNTimesLast(2), eccGen.getLastState(),
				ECCContentAndLabelProvider.getInputEvents(destinationFB)
						.get(ECCContentAndLabelProvider.getInputEvents(destinationFB).size() - 1));

		eccGen.createTransitionFromTo(eccGen.getLastState(), sucState, null);
		eccGen.createTransitionFromTo(eccGen.getLastState(), errState, null);
		if (!destinationFB.getInterfaceList().getInputVars().isEmpty()) {
			eccGen.getEcc().getECTransition().get(eccGen.getEcc().getECTransition().size() - 2)
					.setConditionExpression("matchData"); //$NON-NLS-1$
			eccGen.getEcc().getECTransition().get(eccGen.getEcc().getECTransition().size() - 1)
					.setConditionExpression("NOT matchData"); //$NON-NLS-1$
		}

	}

	private void createPathTimeOut(final TestEccGenerator eccGen, final AdapterDeclaration plug) {
		eccGen.createState("S1", 0); //$NON-NLS-1$
		eccGen.getLastState().setName(NameRepository.createUniqueName(eccGen.getLastState(), "S1")); //$NON-NLS-1$
		eccGen.createTransitionFromTo(eccGen.getEcc().getStart(), eccGen.getLastState(), getEventInput("expected")); //$NON-NLS-1$
		final ECAction act = TestEccGenerator.createAction();
		act.setAlgorithm(createTimeOutAlg(destinationFB));

		act.setOutput(ECCContentAndLabelProvider.getOutputEvents(destinationFB).stream()
				.filter(s -> s.getName().equals("START")).findFirst().orElse(null)); //$NON-NLS-1$
		eccGen.getLastState().getECAction().add(act);

		eccGen.createState("S1", 1); //$NON-NLS-1$
		eccGen.getLastState().setName(NameRepository.createUniqueName(eccGen.getLastState(), "S1")); //$NON-NLS-1$

		eccGen.createTransitionFromTo(eccGen.getNTimesLast(1), eccGen.getLastState(),
				plug.getType().getInterfaceList().getEventOutputs().get(0));
		eccGen.createTransitionFromTo(eccGen.getLastState(), eccGen.getEcc().getStart(), null);
		final ECAction actSuc = TestEccGenerator.createAction();
		actSuc.setOutput(destinationFB.getInterfaceList().getEventOutputs().get(1));
		eccGen.getLastState().getECAction().add(actSuc);

		eccGen.increaseCaseCount();
		eccGen.createState("S1", 1); //$NON-NLS-1$
		eccGen.getLastState().setName(NameRepository.createUniqueName(eccGen.getLastState(), "S1")); //$NON-NLS-1$
		for (final Event ev : destinationFB.getInterfaceList().getEventInputs()) {
			eccGen.createTransitionFromTo(eccGen.getNTimesLast(2), eccGen.getLastState(), ev);
		}
		final ECAction actErr = TestEccGenerator.createAction();
		actErr.setOutput(destinationFB.getInterfaceList().getEventOutputs().get(0));
		eccGen.createTransitionFromTo(eccGen.getLastState(), eccGen.getEcc().getStart(), null);
		eccGen.getLastState().getECAction().add(actErr);
	}

	private AdapterDeclaration createTimeOutPlug() {
		final TypeLibrary typelib = entry.getTypeLibrary();
		final AdapterTypeEntry timeOutEntry = typelib.getAdapterTypeEntry("ATimeOut"); //$NON-NLS-1$
		final CreateInterfaceElementCommand cmd = new CreateInterfaceElementCommand(timeOutEntry.getType(), "timeOut", //$NON-NLS-1$
				sourceType.getInterfaceList(), false, -1);

		try {
			cmd.execute();
		} catch (final Exception e) {
			System.out.print(e.getMessage());
		}

		final AdapterDeclaration timeOutPlug = (AdapterDeclaration) cmd.getCreatedElement();
		destinationFB.getInterfaceList().getPlugs().add(timeOutPlug);
		return timeOutPlug;
	}

	public static Algorithm createTimeOutAlg(final BasicFBType fb) {
		final String algText = "timeOut.DT := TIME#1000ms; \n";//$NON-NLS-1$
		return TestEccGenerator.createAlgorithm(fb, "TimeOutAlg", algText); //$NON-NLS-1$
	}

	private void createErrorTransitions(final TestEccGenerator eccGen, final String ev) {
		for (final Event errEv : destinationFB.getInterfaceList().getEventInputs()) {
			if (!errEv.getName().equals(ev)) {
				eccGen.createTransitionFromTo(eccGen.getNTimesLast(1), eccGen.getLastState(), errEv);
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
		final List<Event> list = new ArrayList<>();
		list.add(createEvent("ERR", false));//$NON-NLS-1$
		list.add(createEvent("SUC", false)); //$NON-NLS-1$
		return list;
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
