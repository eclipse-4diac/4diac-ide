/*******************************************************************************
 * Copyright (c) 2008, 2009, 2016 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
		parent = (ECState) ecAction.eContainer();
		redo();
	}

	@Override
	public void undo() {
		if(null != parent){
			parent.getECAction().add(ecAction);
		}
	}

	@Override
	public void redo() {
		if(null != parent){
			parent.getECAction().remove(ecAction);
		}
	}
}