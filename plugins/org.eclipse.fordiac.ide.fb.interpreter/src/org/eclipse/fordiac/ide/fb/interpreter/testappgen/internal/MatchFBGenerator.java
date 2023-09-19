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
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public class MatchFBGenerator extends AbstractFBGenerator {

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

			//
			//
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
				waitAct.setOutput(plug.getAdapterFB().getInterface().getEventInputs().get(0));
				waitAct.setAlgorithm(TestEccGenerator.createTimeOutAlg(destinationFB));
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
						plug.getAdapterFB().getInterface().getEventOutputs().get(0));
				eccGen.createTransitionFromTo(eccGen.getLastState(), errState, null);
				eccGen.decreaseCaseCount();

				if (i == splitName.length - 2) {
					// before the success/match state a state with timeout is inserted to check for
					// any additional incoming events
					createTimeOutState(eccGen, plug, splitName[i], errState, i);
					// success/match state, sends success event when reached and checks data
					createMatchState(eccGen, plug, event, sucState, errState, i);
				}
			}

			eccGen.increaseCaseCount();
			eccGen.increaseCaseCount();
		}
	}

	private void createTimeOutState(final TestEccGenerator eccGen, final AdapterDeclaration plug, final String name,
			final ECState errState, final int i) {
		// state
		eccGen.createState("WAIT", i + 1); //$NON-NLS-1$
		eccGen.getLastState().setName(NameRepository.createUniqueName(eccGen.getLastState(), "WAIT_1")); //$NON-NLS-1$
		// action
		final ECAction waitAct = TestEccGenerator.createAction();
		waitAct.setOutput(plug.getAdapterFB().getInterface().getEventInputs().get(0));
		// algorithm
		waitAct.setAlgorithm(TestEccGenerator.createTimeOutAlg(destinationFB));
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

	private void createMatchState(final TestEccGenerator eccGen, final AdapterDeclaration plug, final Event event,
			final ECState sucState, final ECState errState, final int i) {
		eccGen.createState(event.getName() + "_match", i + 2); //$NON-NLS-1$
		eccGen.getLastState()
				.setName(NameRepository.createUniqueName(eccGen.getLastState(), event.getName() + "_MATCH_1")); //$NON-NLS-1$
		final ECAction sucAct = TestEccGenerator.createAction();
		eccGen.getLastState().getECAction().add(sucAct);
		// check to see if there are outputs, if so an algorithm needs to be created to
		// check them
		if (!destinationFB.getInterfaceList().getOutputVars().isEmpty()) {

			final Algorithm alg = TestEccGenerator.createMatchAlgorithm(destinationFB,
					destinationFB.getInterfaceList().getInputVars(),
					destinationFB.getInterfaceList().getOutputVars().get(0).getName());
			sucAct.setAlgorithm(alg);
		}
		eccGen.createTransitionFromTo(eccGen.getNTimesLast(2), eccGen.getLastState(),
				plug.getAdapterFB().getInterface().getEventOutputs().get(0));

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
		act.setAlgorithm(TestEccGenerator.createTimeOutAlg(destinationFB));
		act.setOutput(plug.getAdapterFB().getInterface().getEventInputs().get(0));
		eccGen.getLastState().getECAction().add(act);

		eccGen.createState("S1", 1); //$NON-NLS-1$
		eccGen.getLastState().setName(NameRepository.createUniqueName(eccGen.getLastState(), "S1")); //$NON-NLS-1$
		eccGen.createTransitionFromTo(eccGen.getNTimesLast(1), eccGen.getLastState(),
				plug.getAdapterFB().getInterface().getEventOutputs().get(0));
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
		cmd.execute();
		final AdapterDeclaration timeOutPlug = (AdapterDeclaration) cmd.getCreatedElement();
		timeOutPlug.setType(timeOutEntry.getType());
		destinationFB.getInterfaceList().getPlugs().add(timeOutPlug);
		return timeOutPlug;
	}

	private void createErrorTransitions(final TestEccGenerator eccGen, final String ev) {
		for (final Event errEv : destinationFB.getInterfaceList().getEventInputs()) {
			if (!errEv.getName().equals(ev)) {
				eccGen.createTransitionFromTo(eccGen.getNTimesLast(1), eccGen.getLastState(), errEv);
			}
		}
	}

	private Event getEventInput(final String name) {
		for (final Event ev : destinationFB.getInterfaceList().getEventInputs()) {
			if (ev.getName().equals(name)) {
				return ev;
			}
		}
		return null;
	}

	@Override
	protected List<Event> createInputEventList() {
		final List<Event> list = new ArrayList<>();
		for (final TestCase testCase : testSuite.getTestCases()) {
			final StringBuilder sb = new StringBuilder();
			for (final TestState testState : testCase.getTestStates()) {
				for (final OutputPrimitive outP : testState.getTestOutputs()) {
					sb.append(outP.getEvent() + "_"); //$NON-NLS-1$
				}
			}
			sb.append("expected"); //$NON-NLS-1$
			final String name = sb.toString();

			if (!containsEvent(list, name)) {
				list.add(createEvent(sb.toString(), true));
			}
		}
		for (final Event event : sourceType.getInterfaceList().getEventOutputs()) {
			list.add(createEvent(event.getName(), event.getType(), true));
		}
		inputEventList = list;
		return list;
	}

	@Override
	protected List<Event> createOutputEventList() {
		final List<Event> list = new ArrayList<>();
		list.add(createEvent("ERR", false));//$NON-NLS-1$
		list.add(createEvent("SUC", false)); //$NON-NLS-1$
		outputEventList = list;
		return list;
	}

	@Override
	protected List<VarDeclaration> createInputDataList() {
		final List<VarDeclaration> list = new ArrayList<>();
		for (final VarDeclaration varDeclaration : sourceType.getInterfaceList().getOutputVars()) {
			list.add(createVarDeclaration(varDeclaration, varDeclaration.getName() + "_expected", true)); //$NON-NLS-1$
			list.add(createVarDeclaration(varDeclaration, varDeclaration.getName() + "_received", true)); //$NON-NLS-1$
		}
		return list;
	}

	@Override
	protected List<VarDeclaration> createOutputDataList() {
		final List<VarDeclaration> list = new ArrayList<>();
		if (!sourceType.getInterfaceList().getOutputVars().isEmpty()) {
			final VarDeclaration varDecl = createVarDeclaration("matchData", false); //$NON-NLS-1$
			varDecl.setType(sourceType.getTypeLibrary().getDataTypeLibrary().getType(FordiacKeywords.BOOL));
			varDecl.setComment(""); //$NON-NLS-1$
			list.add(varDecl);
		}
		return list;
	}
}
