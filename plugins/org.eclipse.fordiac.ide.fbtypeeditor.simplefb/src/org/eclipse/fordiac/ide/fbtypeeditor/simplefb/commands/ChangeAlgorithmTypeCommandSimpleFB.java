/*******************************************************************************
 * Copyright (c) 2018 TU Wien/ACIN, Johannes Kepler University
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Peter Gsellmann
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - moved common parts with basicfb command to common base class  
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.simplefb.commands;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.AbstractChangeAlgorithmTypeCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;

public class ChangeAlgorithmTypeCommandSimpleFB extends AbstractChangeAlgorithmTypeCommand {

	public ChangeAlgorithmTypeCommandSimpleFB(BaseFBType fbType, Algorithm oldAlgorithm, String algorithmType) {
		super(fbType, oldAlgorithm, algorithmType);
	}

	@Override
	protected void changeAlgorithm(Algorithm oldAlg, Algorithm newAlg) {
		getType().setAlgorithm(newAlg);
	}

	@Override
	protected SimpleFBType getType() {
		return (SimpleFBType) super.getType();
	}
}
