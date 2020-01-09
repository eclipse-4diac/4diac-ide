/*******************************************************************************
 * Copyright (c) 2008, 2009, 2016 Profactor GmbH, fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands;

import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.gef.commands.Command;

public class DeleteECActionCommand extends Command {
	private final ECAction ecAction;
	private ECState parent;

	public DeleteECActionCommand(final ECAction ecAction) {
		this.ecAction = ecAction;
	}

	@Override
	public void execute() {
		parent = ecAction.getECState();
		redo();
	}

	@Override
	public void undo() {
		if (null != parent) {
			parent.getECAction().add(ecAction);
		}
	}

	@Override
	public void redo() {
		if (null != parent) {
			parent.getECAction().remove(ecAction);
		}
	}
}