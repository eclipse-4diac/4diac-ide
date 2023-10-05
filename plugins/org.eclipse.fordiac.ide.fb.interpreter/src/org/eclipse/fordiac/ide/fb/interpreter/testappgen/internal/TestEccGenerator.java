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

import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

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

		final Position pT1 = LibraryElementFactory.eINSTANCE.createPosition();
		final int x1 = from.getPosition().getX();
		final int x2 = to.getPosition().getX();
		final int y1 = from.getPosition().getY();
		final int y2 = to.getPosition().getY();

		pT1.setX(x1 + ((x2 - x1) / 2));
		pT1.setY(y2 + ((y1 - y2) / 2));
		transition.setPosition(pT1);
	}

	public void createState(final String name, final int stateCount) {
		final ECState state = LibraryElementFactory.eINSTANCE.createECState();
		ecc.getECState().add(state);
		state.setName(name);

		final Position pS = LibraryElementFactory.eINSTANCE.createPosition();
		pS.setX(350 + stateCount * 350);
		pS.setY(caseCount * 75);
		state.setPosition(pS);
	}

	public static ECAction createAction() {
		return LibraryElementFactory.eINSTANCE.createECAction();
	}

	public static Algorithm createMatchAlgorithm(final BasicFBType fb, final List<VarDeclaration> inputData,
			final String outputPinName) {
		final String algName = "matchAlgo"; //$NON-NLS-1$
		if (fb.getAlgorithmNamed(algName) != null) {
			return fb.getAlgorithmNamed(algName);
		}
		final TextAlgorithm alg = LibraryElementFactory.eINSTANCE.createSTAlgorithm();
		alg.setName(algName);

		final StringBuilder text = new StringBuilder();

		text.append("ALGORITHM " + alg.getName() + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
		text.append("IF "); //$NON-NLS-1$
		for (int i = 0; i < inputData.size(); i += 2) {
			if (i > 0) {
				text.append(" AND ");//$NON-NLS-1$
			}
			text.append(inputData.get(i).getName() + " = ");//$NON-NLS-1$
			text.append(inputData.get(i + 1).getName());
		}
		text.append("\nTHEN\n");//$NON-NLS-1$
		text.append(outputPinName + ":= BOOL#TRUE;\n");//$NON-NLS-1$
		text.append("ELSE\n"); //$NON-NLS-1$
		text.append(outputPinName + " := BOOL#FALSE;\n");//$NON-NLS-1$
		text.append("END_IF;\n"); //$NON-NLS-1$
		text.append("\nEND_ALGORITHM"); //$NON-NLS-1$
		text.append("\n\n\n"); //$NON-NLS-1$

		alg.setText(text.toString());
		fb.getCallables().add(alg);
		return alg;
	}

	public static Algorithm createTestFbAlgorithm(final BasicFBType fb, final String name) {
		final TextAlgorithm alg = LibraryElementFactory.eINSTANCE.createSTAlgorithm();
		alg.setName(name);
		fb.getCallables().add(alg);
		return alg;
	}

	public static Algorithm createMuxFbAlgorithm(final BasicFBType fb, final String name) {
		final TextAlgorithm alg = LibraryElementFactory.eINSTANCE.createSTAlgorithm();
		final StringBuilder text = new StringBuilder();
		alg.setName("A_" + name); //$NON-NLS-1$
		text.append("ALGORITHM A_" + name + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
		text.append(fb.getInterfaceList().getOutputVars().get(0).getName());
		text.append(":="); //$NON-NLS-1$
		text.append("'" + name + "'"); //$NON-NLS-1$ //$NON-NLS-2$
		text.append(";"); //$NON-NLS-1$
		text.append("\nEND_ALGORITHM"); //$NON-NLS-1$
		text.append("\n\n\n"); //$NON-NLS-1$

		alg.setText(text.toString());
		fb.getCallables().add(alg);

		return alg;
	}

	public static Algorithm createTimeOutAlg(final BasicFBType fb) {
		final String algName = "TimeOutAlg"; //$NON-NLS-1$
		if (fb.getAlgorithmNamed(algName) != null) {
			return fb.getAlgorithmNamed(algName);
		}
		final TextAlgorithm alg = LibraryElementFactory.eINSTANCE.createSTAlgorithm();
		final StringBuilder text = new StringBuilder();
		alg.setName(algName);
		text.append("ALGORITHM " + alg.getName() + "\n");//$NON-NLS-1$ //$NON-NLS-2$
		text.append("timeOut.DT := t#1000ms; \n");//$NON-NLS-1$
		text.append("\nEND_ALGORITHM"); //$NON-NLS-1$
		text.append("\n\n\n"); //$NON-NLS-1$

		alg.setText(text.toString());
		fb.getCallables().add(alg);

		return alg;
	}

	public void createState(final TestCase testCase, final int stateCount) {
		final ECState state = LibraryElementFactory.eINSTANCE.createECState();
		ecc.getECState().add(state);
		state.setName(testCase.getName() + "_S" + stateCount); //$NON-NLS-1$

		final Position pS = LibraryElementFactory.eINSTANCE.createPosition();
		pS.setX(stateCount * 350);
		pS.setY(caseCount * 75);
		state.setPosition(pS);
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
