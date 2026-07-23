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

import org.eclipse.fordiac.ide.model.libraryElement.BaseECAction;
import org.eclipse.fordiac.ide.model.libraryElement.BaseECState;
import org.eclipse.gef.commands.Command;

public class DeleteECActionCommand<T extends BaseECAction> extends Command {
	private final T action;
	private final BaseECState<T> parent;

	public DeleteECActionCommand(final T action, final BaseECState<T> parent) {
		this.action = action;
		this.parent = parent;
	}

	@Override
	public void execute() {
		redo();
	}

	@Override
	public void undo() {
		if (null != parent) {
			parent.getECActions().add(action);
		}
	}

	@Override
	public void redo() {
		if (null != parent) {
			parent.getECActions().remove(action);
		}
	}
}
