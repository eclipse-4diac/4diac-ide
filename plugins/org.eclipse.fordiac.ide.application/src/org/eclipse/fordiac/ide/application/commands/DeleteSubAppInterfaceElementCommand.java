/*******************************************************************************
 * Copyright (c) 2018 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger 
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;

public class DeleteSubAppInterfaceElementCommand extends DeleteInterfaceCommand {
	
	DeleteInterfaceCommand opposite = null;

	public DeleteSubAppInterfaceElementCommand(IInterfaceElement interfaceElement) {
		super(interfaceElement);
		if(interfaceElement.getFBNetworkElement().isMapped()) {
			FBNetworkElement fbE = interfaceElement.getFBNetworkElement().getOpposite();
			IInterfaceElement element = fbE.getInterfaceElement(interfaceElement.getName());
			if(null != element) {
				opposite = new DeleteInterfaceCommand(element);
			}
		}
	}
	
	@Override
	public void execute() {
		super.execute();
		if(null != opposite) {
			opposite.execute();
		}
	}
	
	@Override
	public void undo() {
		super.undo();
		if(null != opposite) {
			opposite.undo();
		}
	}
	
	@Override
	public void redo() {
		super.redo();
		if(null != opposite) {
			opposite.redo();
		}
	}

}
