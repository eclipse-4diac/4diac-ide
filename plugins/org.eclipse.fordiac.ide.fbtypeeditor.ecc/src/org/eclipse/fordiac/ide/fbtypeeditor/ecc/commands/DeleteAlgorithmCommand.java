/*******************************************************************************
 * Copyright (c) 2014 - 2016 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.gef.commands.Command;

public class DeleteAlgorithmCommand extends Command {
	
	private final BasicFBType fbType;

	private Algorithm algorithm;
	
	private List<ECAction> actions = new ArrayList<>();
	
	public DeleteAlgorithmCommand(final BasicFBType fbType, Algorithm algorithm){
		this.fbType = fbType;
		this.algorithm = algorithm;
	}

	@Override
	public void execute() {				
		for (ECState state : fbType.getECC().getECState()) {
			for (ECAction ecAction : state.getECAction()) {
				if (ecAction.getAlgorithm() != null
						&& ecAction.getAlgorithm().equals(algorithm)) {
					actions.add(ecAction);
				}
			}
		}
		
		redo();
	}

	@Override
	public void undo() {		
		for (ECAction action : actions) {
			action.setAlgorithm(algorithm);
		}		
		fbType.getAlgorithm().add(algorithm);
	}

	@Override
	public void redo() {
		for (ECAction action : actions) {
			action.setAlgorithm(null);
		}
		fbType.getAlgorithm().remove(algorithm);
	}

}
