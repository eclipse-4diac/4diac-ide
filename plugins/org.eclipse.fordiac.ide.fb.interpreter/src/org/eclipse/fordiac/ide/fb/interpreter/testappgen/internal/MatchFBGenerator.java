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

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;

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
		return sourceType.getName() + "_MATCH";
	}

	@Override
	protected FBType getSourceFB() {
		return sourceType;
	}

	@Override
	protected void generateECC() {
		final TestEccGenerator eccGen = new TestEccGenerator(destinationFB.getECC(), 0);
		for (final Event event : destinationFB.getInterfaceList().getEventInputs()) {
			if (event.getName().contains("expected")) {
				final String[] splitName = event.getName().split("_");
				for (int i = 0; i < splitName.length - 1; i++) {
					eccGen.createState(splitName[i] + "_wait", i);
					if (i == 0) {
						eccGen.createTransitionFromTo(eccGen.getEcc().getStart(), eccGen.getLast(), event);
					} else {
						eccGen.createTransitionFromTo(eccGen.getNTimesLast(2), eccGen.getLast(),
								getEvent(splitName[i - 1]));
					}
					eccGen.increaseCaseCount();
					eccGen.createState("ERROR", i);
					createErrorTransitions(eccGen, splitName[i]);
					eccGen.createTransitionFromTo(eccGen.getLast(), eccGen.getEcc().getStart(), null);
					final ECAction errAct = eccGen.createAction();
					errAct.setOutput(destinationFB.getInterfaceList().getEventOutputs().get(0));
					eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size() - 1).getECAction().add(errAct);
					eccGen.decreaseCaseCount();
					if (i == splitName.length - 2) {
						eccGen.createState(event.getName() + "_match", i + 1);
						final ECAction sucAct = eccGen.createAction();
						sucAct.setOutput(destinationFB.getInterfaceList().getEventOutputs().get(1));
						eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size() - 1).getECAction()
								.add(sucAct);
						sucAct.setAlgorithm(eccGen.createMatchAlgorithm(destinationFB,
								destinationFB.getInterfaceList().getOutputVars(),
								event.getName().substring(0, event.getName().lastIndexOf('_'))));
						eccGen.createTransitionFromTo(eccGen.getNTimesLast(2), eccGen.getLast(),
								getEvent(splitName[i]));
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

	private Event getEvent(final String name) {
		for (final Event ev : destinationFB.getInterfaceList().getEventInputs()) {
			if (ev.getName().equals(name)) {
				return ev;
			}
		}
		return null;
	}

//		for (Event event : sourceType.getInterfaceList().getEventInputs()) {
//			// create State + Transition
//			eccGen.createState(event.getName() + "_wait", 1, event);
//			eccGen.createTransitionFromTo(
//					eccGen.getEcc().getECState().get(0),
//					eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size()-1),
//					event, 0);
//
//			// add Action + Algorithm
//			ECAction action = eccGen.createAction(null);
//			action.setAlgorithm(eccGen.createAlgorithm(event.getName() + "_wait"));
//			eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size()-1).getECAction().add(action);
//
//
//			Event event4 =  LibraryElementFactory.eINSTANCE.createEvent();
//			for(TestCase testCase : testSuite.getTestCases()) {
//				if(testCase.getTestStates().get(0).getTestTrigger().getEvent().equals(event.getName())) {
//					event4 = destinationFB.getInterfaceList().getEvent(testCase.getTestStates().get(0).getTestOutputs().get(0).getEvent());
//				}
//			}
//
//			// match state
//			eccGen.createState(event.getName() + "_match", 2, event);
//
//			eccGen.createTransitionFromTo(eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size()-2),
//					eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size()-1),
//					event4, 0);
//
//			eccGen.increaseCaseCount();
//			ECAction action2 = eccGen.createAction(null);
//			action2.setAlgorithm(eccGen.createAlgorithm(event.getName() + "_match"));
//			action2.setOutput(destinationFB.getInterfaceList().getEventOutputs().get(1));
//			eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size()-1).getECAction().add(action2);
//
//			// ErrorState + Transitions to ErrorState
//			eccGen.createState("ERROR_" + event.getName(), 2, event);
//			for (Event errorEvent : inputEventList) {
//				if(!errorEvent.getName().equals(event4.getName()))
//				eccGen.createTransitionFromTo(
//						eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size()-3),
//						eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size()-1),
//						errorEvent, 0);
//			}
//			eccGen.createTransitionFromTo(
//					eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size()-1),
//					eccGen.getEcc().getECState().get(0),
//					null, 0);
//			ECAction action3 = eccGen.createAction(null);
//			action3.setAlgorithm(eccGen.createAlgorithm(event.getName() + "_error"));
//			action3.setOutput(destinationFB.getInterfaceList().getEventOutputs().get(0));
//			eccGen.getEcc().getECState().get(eccGen.getEcc().getECState().size()-1).getECAction().add(action3);
//
//			eccGen.increaseCaseCount();
//
//		}
//	}

	@Override
	protected List<Event> createInputEventList() {
		final List<Event> list = new ArrayList<>();
		for (final TestCase testCase : testSuite.getTestCases()) {
			final StringBuilder sb = new StringBuilder();
			for (final TestState testState : testCase.getTestStates()) {
				for (final OutputPrimitive outP : testState.getTestOutputs()) {
					sb.append(outP.getEvent() + "_");
				}
			}
			sb.append("expected");
			final Event inEvent = LibraryElementFactory.eINSTANCE.createEvent();
			inEvent.setIsInput(true);
			inEvent.setName(sb.toString());
			inEvent.setType(EventTypeLibrary.getInstance().getType(EventTypeLibrary.EVENT));
			list.add(inEvent);
		}
//		for (final Event event : sourceType.getInterfaceList().getEventInputs()) {
//			final Event outEvent = LibraryElementFactory.eINSTANCE.createEvent();
//			outEvent.setIsInput(true);
//			outEvent.setName(event.getName());
//			outEvent.setType(event.getType());
//			list.add(outEvent);
//		}
		for (final Event event : sourceType.getInterfaceList().getEventOutputs()) {
			final Event inEvent = LibraryElementFactory.eINSTANCE.createEvent();
			inEvent.setIsInput(true);
			inEvent.setName(event.getName());
			inEvent.setType(event.getType());
			list.add(inEvent);
		}
		inputEventList = list;
		return list;
	}

	@Override
	protected List<Event> createOutputEventList() {
		final List<Event> list = new ArrayList<>();
		final Event errorEvent = LibraryElementFactory.eINSTANCE.createEvent();
		errorEvent.setIsInput(false);
		errorEvent.setType(EventTypeLibrary.getInstance().getType(EventTypeLibrary.EVENT));
		errorEvent.setName("ERROR");
		list.add(errorEvent);

		final Event successEvent = LibraryElementFactory.eINSTANCE.createEvent();
		successEvent.setIsInput(false);
		successEvent.setType(EventTypeLibrary.getInstance().getType(EventTypeLibrary.EVENT));
		successEvent.setName("SUCCESS");
		list.add(successEvent);
		outputEventList = list;
		return list;
	}

	@Override
	protected List<VarDeclaration> createInputDataList() {
		final List<VarDeclaration> list = new ArrayList<>();
		for (final VarDeclaration varDeclaration : sourceType.getInterfaceList().getOutputVars()) {

			VarDeclaration varDecl = EcoreUtil.copy(varDeclaration);
			varDecl.setName(varDeclaration.getName() + "_expected");
			varDecl.setIsInput(true);
			list.add(varDecl);

			varDecl = EcoreUtil.copy(varDeclaration);
			varDecl.setName(varDeclaration.getName() + "_received");
			varDecl.setIsInput(true);
			list.add(varDecl);
		}
		return list;
	}

	@Override
	protected List<VarDeclaration> createOutputDataList() {
		final List<VarDeclaration> list = new ArrayList<>();
		for (final VarDeclaration varDeclaration : sourceType.getInterfaceList().getOutputVars()) {
			final VarDeclaration varDecl = EcoreUtil.copy(varDeclaration);
			varDecl.setName(varDecl.getName() + "_matchData");
			varDecl.setIsInput(false);
			varDecl.setType(sourceType.getTypeLibrary().getDataTypeLibrary().getType(FordiacKeywords.BOOL));
			varDecl.setComment("");
			list.add(varDecl);
		}
		return list;
	}
}
