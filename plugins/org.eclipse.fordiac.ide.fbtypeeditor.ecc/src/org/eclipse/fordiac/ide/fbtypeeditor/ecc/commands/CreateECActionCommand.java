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

import org.eclipse.fordiac.ide.model.libraryElement.BaseECAction;
import org.eclipse.fordiac.ide.model.libraryElement.BaseECState;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;

public class CreateECActionCommand<T extends BaseECAction> extends CreationCommand {
	private final T action;
	private final BaseECState<T> parent;

	public CreateECActionCommand(final T action, final BaseECState<T> parent) {
		this.action = action;
		this.parent = parent;
	}

	@Override
	public void execute() {
		redo();
	}

	@Override
	public void undo() {
		parent.getECActions().remove(action);
	}

	@Override
	public void redo() {
		parent.getECActions().add(action);
	}

	@Override
	public Object getCreatedElement() {
		return action;
	}
}
