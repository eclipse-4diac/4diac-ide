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

public class ChangeSequenceTypeCommand extends Command {
	private final ServiceSequence sequence;
	private final String type;
	private String oldType;

	public ChangeSequenceTypeCommand(final String type, final ServiceSequence sequence) {
		this.sequence = sequence;
		this.type = type;
	}

	@Override
	public void execute() {
		oldType = sequence.getServiceSequenceType();
		setType(type);
	}

	@Override
	public void undo() {
		setType(oldType);
	}

	@Override
	public void redo() {
		setType(type);
	}

	private void setType(final String type) {
		sequence.setServiceSequenceType(type);
	}
}
