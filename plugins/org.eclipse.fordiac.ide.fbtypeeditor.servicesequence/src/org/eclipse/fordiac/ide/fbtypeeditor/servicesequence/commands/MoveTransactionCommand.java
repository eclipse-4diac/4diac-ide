/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2015 Profactor GmbH, fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.gef.commands.Command;

public class MoveTransactionCommand extends Command {

	private final ServiceSequence parent;
	private final ServiceTransaction moveElement;
	private final int oldIndex;
	private int newIndex;

	public MoveTransactionCommand(ServiceTransaction model, int oldIndex, int newIndex) {
		this.moveElement = model;
		this.oldIndex = oldIndex;
		this.newIndex = newIndex;
		this.parent = (ServiceSequence) moveElement.eContainer();
	}

	@Override
	public boolean canExecute() {
		return ((parent != null) && (moveElement != null));
	}
	
	@Override
	public void execute() {
		parent.getServiceTransaction().move(newIndex, moveElement);
	}
	
	@Override
	public void undo() {
		parent.getServiceTransaction().move(oldIndex, moveElement);
	}
	
	@Override
	public void redo() {
		parent.getServiceTransaction().move(newIndex, moveElement);
	}
}
