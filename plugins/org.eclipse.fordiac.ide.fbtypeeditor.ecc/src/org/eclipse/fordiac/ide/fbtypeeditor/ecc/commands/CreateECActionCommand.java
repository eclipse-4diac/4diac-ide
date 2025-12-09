/*******************************************************************************
 * Copyright (c) 2008, 2009 Profactor GmbH
 *               2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr
 *     - command now returns newly created element
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands;

import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;

public class CreateECActionCommand extends CreationCommand {
	private final ECAction action;
	private final ECState parent;

	public CreateECActionCommand(final ECAction action, final ECState parent) {
		super();
		this.action = action;
		this.parent = parent;
	}

	@Override
	public void execute() {
		redo();
	}

	@Override
	public void undo() {
		parent.getECAction().remove(action);
	}

	@Override
	public void redo() {
		parent.getECAction().add(action);
	}

	@Override
	public Object getCreatedElement() {
		return action;
	}
}