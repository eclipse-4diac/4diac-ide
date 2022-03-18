/*******************************************************************************
 * Copyright (c) 2014, 2016 fortiss GmbH, 2018 TU Wien/ACIN,
 * 					Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
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

	public ChangeAlgorithmTypeCommand(final BaseFBType fbType, final Algorithm oldAlgorithm, final String algorithmType) {
		super(fbType, oldAlgorithm, algorithmType);
	}

	@Override
	protected void changeAlgorithm(final Algorithm oldAlg, final Algorithm newAlg) {
		getType().getCallables().add(getType().getCallables().indexOf(oldAlg), newAlg);
		updateECActions(oldAlg, newAlg);
		getType().getCallables().remove(oldAlg);
	}

	private void updateECActions(final Algorithm oldAlg, final Algorithm newAlg) {
		for (final ECState state : getType().getECC().getECState()) {
			for (final ECAction action : state.getECAction()) {
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
