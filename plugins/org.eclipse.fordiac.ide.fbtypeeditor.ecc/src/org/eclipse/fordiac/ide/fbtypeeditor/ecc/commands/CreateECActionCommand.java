/*******************************************************************************
 * Copyright (c) 2008, 2009 Profactor GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands;

import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.gef.commands.Command;

public class CreateECActionCommand extends Command {
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
}