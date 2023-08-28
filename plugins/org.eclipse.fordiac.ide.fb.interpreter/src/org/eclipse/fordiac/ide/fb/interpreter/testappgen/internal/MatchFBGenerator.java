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
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

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
		for (final Event event : destinationFB.getInterfaceList().getEventInputs()) {
			if (event.getName().contains("expected")) { //$NON-NLS-1$
				if (event.getName().equals("expected")) { //$NON-NLS-1$
					eccGen.createState("S1", 0); //$NON-NLS-1$
					eccGen.createTransitionFromTo(eccGen.getEcc().getStart(), eccGen.getLast(),
							getEventInput("expected")); //$NON-NLS-1$
					eccGen.createTransitionFromTo(eccGen.getLast(), eccGen.getEcc().getStart(), null);
					final ECAction action = TestEccGenerator.createAction();
					action.setOutput(destinationFB.getInterfaceList().getEventOutputs().get(1));
					eccGen.getLast().getECAction().add(action);
				}
				final String[] splitName = event.getName().split("_"); //$NON-NLS-1$
				for (int i = 0; i < splitName.length - 1; i++) {
					eccGen.createState(splitName[i] + "_wait", i); //$NON-NLS-1$
					eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size() - 1)
							.setName(NameRepository.createUniqueName(
									eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size() - 1),
									splitName[i] + "_WAIT_1")); //$NON-NLS-1$
					if (i == 0) {
						eccGen.createTransitionFromTo(eccGen.getEcc().getStart(), eccGen.getLast(), event);
					} else {
						eccGen.createTransitionFromTo(eccGen.getNTimesLast(2), eccGen.getLast(),
								getEventInput(splitName[i - 1]));
					}
					eccGen.increaseCaseCount();
					eccGen.createState("ERROR", i); //$NON-NLS-1$
					eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size() - 1)
							.setName(NameRepository.createUniqueName(
									eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size() - 1),
									"ERROR_1")); //$NON-NLS-1$
					createErrorTransitions(eccGen, splitName[i]);
					eccGen.createTransitionFromTo(eccGen.getLast(), eccGen.getEcc().getStart(), null);
					final ECAction errAct = TestEccGenerator.createAction();
					errAct.setOutput(destinationFB.getInterfaceList().getEventOutputs().get(0));
					eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size() - 1).getECAction().add(errAct);
					eccGen.decreaseCaseCount();
					if (i == splitName.length - 2) {
						eccGen.createState(event.getName() + "_match", i + 1); //$NON-NLS-1$
						eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size() - 1)
								.setName(NameRepository.createUniqueName(
										eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size() - 1),
										event.getName() + "_MATCH_1")); //$NON-NLS-1$
						final ECAction sucAct = TestEccGenerator.createAction();
						sucAct.setOutput(destinationFB.getInterfaceList().getEventOutputs().get(1));
						eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size() - 1).getECAction()
								.add(sucAct);
						sucAct.setAlgorithm(eccGen.createMatchAlgorithm(destinationFB,
								destinationFB.getInterfaceList().getOutputVars(),
								event.getName().substring(0, event.getName().lastIndexOf('_'))));
						eccGen.createTransitionFromTo(eccGen.getNTimesLast(2), eccGen.getLast(),
								getEventInput(splitName[i]));
						eccGen.createTransitionFromTo(eccGen.getLast(), eccGen.getEcc().getStart(), null);
					}
				}
			}
			eccGen.increaseCaseCount();
			eccGen.increaseCaseCount();
		}
	}

	private void createErrorTransitions(final TestEccGenerator eccGen, final String ev) {
		for (final Event errEv : destinationFB.getInterfaceList().getEventInputs()) {
			if (!errEv.getName().equals(ev)) {
				eccGen.createTransitionFromTo(eccGen.getNTimesLast(1), eccGen.getLast(), errEv);
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
		list.add(createEvent("ERROR", false));//$NON-NLS-1$
		list.add(createEvent("SUCCESS", false)); //$NON-NLS-1$
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
		for (final VarDeclaration varDeclaration : sourceType.getInterfaceList().getOutputVars()) {
			final VarDeclaration varDecl = createVarDeclaration(varDeclaration, varDeclaration.getName() + "_matchData", //$NON-NLS-1$
					false);
			varDecl.setType(sourceType.getTypeLibrary().getDataTypeLibrary().getType(FordiacKeywords.BOOL));
			varDecl.setComment(""); //$NON-NLS-1$

			list.add(varDecl);
		}
		return list;
	}
}
