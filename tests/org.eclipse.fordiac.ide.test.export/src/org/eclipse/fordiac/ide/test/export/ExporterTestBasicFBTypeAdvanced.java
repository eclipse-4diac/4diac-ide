/*******************************************************************************
 * Copyright (c) 2020-2021 fortiss GmbH.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Kirill Dorofeev
 *     - tests for lua exporter
 *******************************************************************************/

package org.eclipse.fordiac.ide.test.export;

import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;

public class ExporterTestBasicFBTypeAdvanced extends ExporterTestBasicFBTypeBase {
	@Override
	void setupFunctionBlock() {
		super.setupFunctionBlock();
		setupAdvancedInterface();
		final ECState startState = LibraryElementFactory.eINSTANCE.createECState();
		startState.setName("START"); //$NON-NLS-1$
		functionBlock.getECC().getECState().add(startState);

		final ECState state = LibraryElementFactory.eINSTANCE.createECState();
		state.setName("State"); //$NON-NLS-1$
		final ECAction action = LibraryElementFactory.eINSTANCE.createECAction();
		action.setOutput(outputEvent);
		final STAlgorithm algorithm = LibraryElementFactory.eINSTANCE.createSTAlgorithm();
		algorithm.setName("ALG1"); //$NON-NLS-1$
		algorithm.setText("DO1 := 42;"); //$NON-NLS-1$
		action.setAlgorithm(algorithm);
		state.getECAction().add(action);
		functionBlock.getECC().getECState().add(state);
		functionBlock.getCallables().add(algorithm);

		final ECTransition fwdTransition = LibraryElementFactory.eINSTANCE.createECTransition();
		fwdTransition.setSource(startState);
		fwdTransition.setDestination(state);
		fwdTransition.setConditionExpression(null);
		functionBlock.getECC().getECTransition().add(fwdTransition);

		final ECTransition bcwdTransition = LibraryElementFactory.eINSTANCE.createECTransition();
		bcwdTransition.setConditionEvent(inputEvent);
		bcwdTransition.setConditionExpression(null);
		bcwdTransition.setSource(state);
		bcwdTransition.setDestination(startState);
		functionBlock.getECC().getECTransition().add(bcwdTransition);
	}
}
