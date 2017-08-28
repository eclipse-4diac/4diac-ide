/*******************************************************************************
 * Copyright (c) 2014 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import org.eclipse.fordiac.ide.model.libraryElement.Primitive;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterface;
import org.eclipse.gef.commands.Command;

public class ChangePrimitiveInterfaceCommand extends Command {
	private Primitive primitive;
	private ServiceInterface serviceInterface;
	private ServiceInterface oldServiceInterface;

	public ChangePrimitiveInterfaceCommand(Service service, Primitive primitive, String interfaceName) {
		super();
		this.primitive = primitive;
		serviceInterface = service.getLeftInterface().getName().equals(interfaceName) ? service.getLeftInterface() : service.getRightInterface();
	}

	@Override
	public void execute() {
		oldServiceInterface = primitive.getInterface();
		primitive.setInterface(serviceInterface);
	}

	@Override
	public void undo() {
		primitive.setInterface(oldServiceInterface);
	}
	@Override
	public void redo() {
		primitive.setInterface(serviceInterface);
	}

}
