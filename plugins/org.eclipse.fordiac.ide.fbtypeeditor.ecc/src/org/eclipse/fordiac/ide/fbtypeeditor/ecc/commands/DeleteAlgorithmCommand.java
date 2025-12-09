/*******************************************************************************
 * Copyright (c) 2014 - 2016 fortiss GmbH
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

	private final Algorithm algorithm;

	private final List<ECAction> actions = new ArrayList<>();

	public DeleteAlgorithmCommand(final BasicFBType fbType, final Algorithm algorithm) {
		this.fbType = fbType;
		this.algorithm = algorithm;
	}

	@Override
	public void execute() {
		for (final ECState state : fbType.getECC().getECState()) {
			for (final ECAction ecAction : state.getECAction()) {
				if (ecAction.getAlgorithm() != null && ecAction.getAlgorithm().equals(algorithm)) {
					actions.add(ecAction);
				}
			}
		}

		redo();
	}

	@Override
	public void undo() {
		for (final ECAction action : actions) {
			action.setAlgorithm(algorithm);
		}
		fbType.getCallables().add(algorithm);
	}

	@Override
	public void redo() {
		for (final ECAction action : actions) {
			action.setAlgorithm(null);
		}
		fbType.getCallables().remove(algorithm);
	}

}
