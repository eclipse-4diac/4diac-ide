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

import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm;

public class TestEccGenerator {
	private ECC ecc;
	private int caseCount;
	
	public TestEccGenerator(ECC ecc, int caseCount) {
		this.ecc = ecc;
		this.caseCount = caseCount;
	}
	
	
	public void createTransitionFromTo(ECState from, ECState to, Event input, int stateCount) {
		final ECTransition transition = LibraryElementFactory.eINSTANCE.createECTransition();
		if(input != null) {
			transition.setConditionEvent(input);
			transition.setConditionExpression("");			
		}
		transition.setSource(from);
		transition.setDestination(to);
			

		
		ecc.getECTransition().add(transition);
		
		final Position pT1 = LibraryElementFactory.eINSTANCE.createPosition();
		int x1 = from.getPosition().getX();
		int x2 = to.getPosition().getX();
		int y1 = from.getPosition().getY();
		int y2 = to.getPosition().getY();
		pT1.setX(x1+((x2-x1)/2));
		pT1.setY(y1);
		
//		if(stateCount > 1) {
//			pT1.setX(300*stateCount);
//		} else {
//			pT1.setX(100*stateCount);
//		}
//		pT1.setY(90*caseCount);
		transition.setPosition(pT1);
	}
	
	public void createState(String name, int stateCount, Event output) {
		final ECState state = LibraryElementFactory.eINSTANCE.createECState();
		ecc.getECState().add(state);
		state.setName(name); //$NON-NLS-1$
		
		final Position pS = LibraryElementFactory.eINSTANCE.createPosition();
		pS.setX(stateCount * 350);
		pS.setY(caseCount * 75);
		state.setPosition(pS);
		//state.getECAction().add(createAction(testCase, output, stateCount));
	}
	
	public ECAction createAction(Event event) {
		final ECAction action = LibraryElementFactory.eINSTANCE.createECAction();
		//action.setAlgorithm(createAlgorithm(testCase, testCase.getName()));
		if(event != null) {
			action.setOutput(event);
		}
		return action;
	}
	
	public void createState(TestCase testCase, int stateCount, Event output) {
		final ECState state = LibraryElementFactory.eINSTANCE.createECState();
		ecc.getECState().add(state);
		state.setName(testCase.getName() + "_S" + stateCount); //$NON-NLS-1$
		
		final Position pS = LibraryElementFactory.eINSTANCE.createPosition();
		pS.setX(stateCount * 350);
		pS.setY(caseCount * 75);
		state.setPosition(pS);
		state.getECAction().add(createAction(testCase, output, stateCount));
	}
	
	public ECAction createAction(TestCase testCase, Event event, int stateCnt) {
		final ECAction action = LibraryElementFactory.eINSTANCE.createECAction();
		action.setAlgorithm(createAlgorithm(testCase, testCase.getName()));
		action.setOutput(event);
		return action;
	}
	
	public Algorithm createAlgorithm(String name) { //String expected, String received
		final TextAlgorithm alg = LibraryElementFactory.eINSTANCE.createSTAlgorithm();
		alg.setName(name);
	
		final StringBuilder text = new StringBuilder();
		text.append("ALGORITHM " + name + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
//		for (final TestState t : testCase.getTestStates()) {
//			// add input primitive parameters as statements
//			if (t.getTestTrigger().getParameters() != null) {
//				text.append(t.getTestTrigger().getParameters());
//				text.append(";"); //$NON-NLS-1$
//			}
	
			// add output primitive parameters as statements
//			for (final OutputPrimitive o : t.getOutputPrimitive()) {
//				if (o.getParameters() != null) {
//					text.append(o.getParameters());
//					text.append(";"); //$NON-NLS-1$
//				}
//			}
		// }
		//text.append("if" + expected + " = " + received + "then\n");
//		text.append("else");
//		text.append("end_if;");
		
		text.append("\nEND_ALGORITHM"); //$NON-NLS-1$
		text.append('\n');
	
		alg.setText(text.toString());
		return alg;
	}
	
	public Algorithm createAlgorithm(TestCase testCase, String string) {
		final TextAlgorithm alg = LibraryElementFactory.eINSTANCE.createSTAlgorithm();
		alg.setName(testCase.getName());
	
		final StringBuilder text = new StringBuilder();
		text.append("ALGORITHM " +  testCase.getName() + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
		for (final TestState t : testCase.getTestStates()) {
			// add input primitive parameters as statements
			if (t.getTestTrigger().getParameters() != null) {
				text.append(t.getTestTrigger().getParameters());
				text.append(";"); //$NON-NLS-1$
			}
	
			// add output primitive parameters as statements
//			for (final OutputPrimitive o : t.getOutputPrimitive()) {
//				if (o.getParameters() != null) {
//					text.append(o.getParameters());
//					text.append(";"); //$NON-NLS-1$
//				}
//			}
		}
		text.append("\nEND_ALGORITHM"); //$NON-NLS-1$
		text.append('\n');
	
		alg.setText(text.toString());
		return alg;
	}
	
	public ECC getEcc() {
		return ecc;
	}
	
	public void increaseCaseCount() {
		caseCount++;
	}
	public int getCaseCount() {
		return caseCount;
	}
}
