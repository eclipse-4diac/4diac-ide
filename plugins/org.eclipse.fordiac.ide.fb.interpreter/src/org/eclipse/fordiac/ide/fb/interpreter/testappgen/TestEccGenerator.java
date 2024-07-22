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

import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.AbstractBlockGenerator;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm;

public class TestEccGenerator {
	private static final String NAME_ENDING = "_WAIT_1"; //$NON-NLS-1$
	private final ECC ecc;
	// caseCount is for vertical positioning purposes
	private int caseCount;
	// stateCount is for horizontal positioning purposes
	private int stateCount;

	public TestEccGenerator(final ECC ecc, final int caseCount) {
		this.ecc = ecc;
		this.caseCount = caseCount;
	}

	public void createTransitionFromTo(final ECState from, final ECState to, final Event input) {
		final ECTransition transition = LibraryElementFactory.eINSTANCE.createECTransition();
		if (input != null) {
			transition.setConditionEvent(input);
			transition.setConditionExpression(""); //$NON-NLS-1$
		}
		transition.setSource(from);
		transition.setDestination(to);

		ecc.getECTransition().add(transition);

		final double x1 = from.getPosition().getX();
		final double x2 = to.getPosition().getX();
		final double y1 = from.getPosition().getY();
		final double y2 = to.getPosition().getY();

		AbstractBlockGenerator.addPosition(transition, x1 + ((x2 - x1) / 2), y2 + ((y1 - y2) / 2));
	}

	public void createState(String name, final int stateCount) {
		final ECState state = LibraryElementFactory.eINSTANCE.createECState();
		ecc.getECState().add(state);
		if (isInteger(name.substring(0, 1))) {
			name = "_" + name; //$NON-NLS-1$
			name = name.replace('.', '_');
		}
		if (!isInteger(name.substring(name.length() - 1))) {
			name = name + "_1"; //$NON-NLS-1$
		}
		getLastState().setName(NameRepository.createUniqueName(getLastState(), name));
		AbstractBlockGenerator.addPosition(state, 350 + (double) stateCount * 350, (double) caseCount * 75);
		this.stateCount++;
	}

	public void createStateWithIncomingConnection(final ECState from, final Event transitionEvent,
			final String condition, final String name, final int stateCount) {
		createState(name + NAME_ENDING, stateCount);
		final ECState state = getLastState();
		AbstractBlockGenerator.addPosition(state, 350 + (double) stateCount * 350, caseCount * (double) 75);
		createTransitionFromTo(from, state, transitionEvent);
		if (condition != null) {
			ecc.getECTransition().get(ecc.getECTransition().size() - 1).setConditionExpression(""); //$NON-NLS-1$
		}
	}

	public void createState(final String name) {
		createState(name, this.stateCount);
	}

	public void createStateWithIncomingConnection(final ECState from, final Event transitionEvent,
			final String condition, final String name) {
		createStateWithIncomingConnection(from, transitionEvent, condition, name, this.stateCount);
	}

	private static boolean isInteger(final String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (final NumberFormatException e) {
			return false;
		}
	}

	public static ECAction createAction() {
		return LibraryElementFactory.eINSTANCE.createECAction();
	}

	public static ECAction createAction(final Event output) {
		final ECAction act = createAction();
		act.setOutput(output);
		return act;
	}

	public static Algorithm createAlgorithm(final BasicFBType fb, final String name, final String algText) {
		if (fb.getAlgorithmNamed(name) != null) {
			return fb.getAlgorithmNamed(name);
		}

		final TextAlgorithm alg = LibraryElementFactory.eINSTANCE.createSTAlgorithm();
		alg.setName(name);
		final StringBuilder sb = new StringBuilder();
		sb.append("ALGORITHM "); //$NON-NLS-1$
		sb.append(name);
		sb.append("\n"); //$NON-NLS-1$
		sb.append(algText);
		sb.append("\n END_ALGORITHM \n\n\n"); //$NON-NLS-1$
		alg.setText(sb.toString());
		fb.getCallables().add(alg);
		return alg;
	}

	public ECState getLastState() {
		return getEcc().getECState().get(getEcc().getECState().size() - 1);
	}

	public ECState getStartState() {
		return getEcc().getStart();
	}

	// getNtimesLast(0) returns the last and getNtimesLast(1) returns the one before
	// the last state
	public ECState getNTimesLast(final int i) {
		return getEcc().getECState().get(getEcc().getECState().size() - i - 1);
	}

	public ECC getEcc() {
		return ecc;
	}

	public void increaseCaseCount() {
		caseCount++;
	}

	public void decreaseCaseCount() {
		caseCount--;
	}

	public void decreaseCaseCountBy(final int i) {
		caseCount = caseCount - i;
	}

	public int getCaseCount() {
		return caseCount;
	}

	public void resetStateCount() {
		stateCount = 0;
	}

	public int getStateCount() {
		return stateCount;
	}
}
