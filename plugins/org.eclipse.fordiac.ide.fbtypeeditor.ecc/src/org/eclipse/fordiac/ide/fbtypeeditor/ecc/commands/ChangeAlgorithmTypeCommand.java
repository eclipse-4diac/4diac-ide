/*******************************************************************************
 * Copyright (c) 2014, 2016 fortiss GmbH, 2018 TU Wien/ACIN, 
 * 					Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   
 *   Peter Gsellmann
 *     - incorporation of simple fb
 *   Alois Zoitl - moved common parts with simple command to common base class  
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands;

import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;

public class ChangeAlgorithmTypeCommand extends AbstractChangeAlgorithmTypeCommand {


	public ChangeAlgorithmTypeCommand(BaseFBType fbType, Algorithm oldAlgorithm, String algorithmType) {
		super(fbType, oldAlgorithm, algorithmType);
	}

	@Override
	protected void changeAlgorithm(Algorithm oldAlg, Algorithm newAlg) {
		getType().getAlgorithm().add(getType().getAlgorithm().indexOf(oldAlg), newAlg);
		updateECActions(oldAlg, newAlg);
		getType().getAlgorithm().remove(oldAlg);
	}

	private void updateECActions(Algorithm oldAlg, Algorithm newAlg) {
		for (ECState state : getType().getECC().getECState()) {
			for (ECAction action : state.getECAction()) {
				if (action.getAlgorithm() == oldAlg) {
					action.setAlgorithm(newAlg);
				}
			}
		}
	}
	
	@Override
	protected BasicFBType getType() {
		return (BasicFBType) super.getType();
	}

}
