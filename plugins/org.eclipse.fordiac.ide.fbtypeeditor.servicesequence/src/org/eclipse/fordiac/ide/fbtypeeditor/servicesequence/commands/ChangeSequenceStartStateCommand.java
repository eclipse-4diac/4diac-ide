/*******************************************************************************
 * Copyright (c) 2022 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Roithmayr - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.gef.commands.Command;

public class ChangeSequenceStartStateCommand extends Command {
	private final ServiceSequence sequence;
	private final String startState;
	private String oldStartState;

	public ChangeSequenceStartStateCommand(final String startState, final ServiceSequence sequence) {
		this.sequence = sequence;
		this.startState = startState;
	}

	@Override
	public void execute() {
		oldStartState = sequence.getStartState();
		setStartState(startState);
	}

	@Override
	public void undo() {
		setStartState(oldStartState);
	}

	@Override
	public void redo() {
		setStartState(startState);
	}

	private void setStartState(final String startState) {
		sequence.setStartState("".equals(startState) ? null : startState); //$NON-NLS-1$
	}
}
