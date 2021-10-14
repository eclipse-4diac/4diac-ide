/*******************************************************************************
 * Copyright (c) 2014 fortiss GmbH
 *               2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr, Melanie Winter - clean up
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.gef.commands.Command;

public class ChangeSequenceNameCommand extends Command {
	private final ServiceSequence sequence;
	private final String name;
	private String oldName;

	public ChangeSequenceNameCommand(final String name, final ServiceSequence sequence) {
		this.sequence = sequence;
		this.name = name;
	}

	@Override
	public void execute() {
		oldName = sequence.getName();
		setName(name);
	}

	@Override
	public void undo() {
		setName(oldName);
	}

	@Override
	public void redo() {
		setName(name);
	}

	private void setName(final String name) {
		sequence.setName(name);
	}
}
