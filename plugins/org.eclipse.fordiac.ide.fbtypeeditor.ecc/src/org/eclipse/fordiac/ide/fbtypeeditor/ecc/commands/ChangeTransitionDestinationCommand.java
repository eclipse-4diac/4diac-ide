/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr
 *       - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands;

import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.gef.commands.Command;

/**
 * A command to change the destination of an EC Transition.
 *
 */
public class ChangeTransitionDestinationCommand extends Command {
	private ECTransition ecTransition;
	private ECState destination;
	private ECState oldDestination;

	public ChangeTransitionDestinationCommand(final ECTransition ecTransition, final ECState destination) {
		this.ecTransition = ecTransition;
		this.destination = destination;
	}

	@Override
	public void execute() {
		oldDestination = ecTransition.getDestination();
		ecTransition.setDestination(destination);
	}

	@Override
	public void undo() {
		ecTransition.setDestination(oldDestination);
	}

	@Override
	public void redo() {
		ecTransition.setDestination(destination);
	}
}
