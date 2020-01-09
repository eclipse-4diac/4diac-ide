/*******************************************************************************
 * Copyright (c) 2014 fortiss GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.gef.commands.Command;

public class ChangeSequenceNameCommand extends Command {
	private ServiceSequence sequence;
	private String name;
	private String oldName;

	public ChangeSequenceNameCommand(String name, ServiceSequence sequence) {
		this.sequence = sequence;
		this.name = name;
	}

	@Override
	public void execute() {
		oldName = sequence.getName();
		redo();
	}

	@Override
	public void undo() {
		sequence.setName(oldName);
	}

	@Override
	public void redo() {
		sequence.setName(name);
	}
}
