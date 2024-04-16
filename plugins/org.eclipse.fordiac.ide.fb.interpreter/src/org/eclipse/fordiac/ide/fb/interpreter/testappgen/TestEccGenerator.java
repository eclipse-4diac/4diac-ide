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
	private final ECC ecc;
	// caseCount is for positioning purposes
	private int caseCount;

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

	public void createState(final String name, final int stateCount) {
		final ECState state = LibraryElementFactory.eINSTANCE.createECState();
		ecc.getECState().add(state);
		state.setName(name);
		AbstractBlockGenerator.addPosition(state, 350 + stateCount * 350, caseCount * 75);
	}

	public static ECAction createAction() {
		return LibraryElementFactory.eINSTANCE.createECAction();
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
}
