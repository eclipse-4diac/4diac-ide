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

import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;

public class TestEccGenerator {
	private ECC ecc;
	private int stateCount;
	
	public TestEccGenerator(ECC ecc, int stateCount) {
		this.ecc = ecc;
		this.stateCount = stateCount;
	}
	
	public void createTransition(Event input, int cnt) {
		final ECTransition transition = LibraryElementFactory.eINSTANCE.createECTransition();
		transition.setConditionEvent(input);
		if(cnt <= 1 ) {
			transition.setSource(ecc.getStart());
			transition.setDestination(ecc.getECState().get(cnt+stateCount));
			
		} else {
			transition.setSource(ecc.getECState().get(cnt+stateCount-1));
			transition.setDestination(ecc.getECState().get(cnt+stateCount));
		}
		
		ecc.getECTransition().add(transition);
		
		final Position pT1 = LibraryElementFactory.eINSTANCE.createPosition();
		pT1.setX(75*cnt);
		pT1.setY(35*cnt);
		transition.setPosition(pT1);
	}
	
	public void createState(String testCaseName, int cnt) {
		final ECState state = LibraryElementFactory.eINSTANCE.createECState();
		ecc.getECState().add(state);
		state.setName(NameRepository.createUniqueName(state, testCaseName + "_S" + cnt)); //$NON-NLS-1$
		
		final Position pS = LibraryElementFactory.eINSTANCE.createPosition();
		pS.setX(cnt * 150);
		pS.setY(stateCount * 75);
		state.setPosition(pS);
	}
	
	public void createAction() {
		
	}

}
