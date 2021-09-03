/*******************************************************************************
 * Copyright (c) 2014 fortiss GmbH
 *               2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr, Melanie Winter - added new constructor
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import org.eclipse.fordiac.ide.model.libraryElement.Primitive;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterface;
import org.eclipse.gef.commands.Command;

public class ChangePrimitiveInterfaceCommand extends Command {
	private final Primitive primitive;
	private final ServiceInterface serviceInterface;
	private ServiceInterface oldServiceInterface;

	public ChangePrimitiveInterfaceCommand(Primitive primitive, ServiceInterface serviceInterface) {
		this.primitive = primitive;
		this.serviceInterface = serviceInterface;
	}

	public ChangePrimitiveInterfaceCommand(Service service, Primitive primitive, String interfaceName) {
		this.primitive = primitive;
		serviceInterface = service.getLeftInterface().getName().equals(interfaceName) ? service.getLeftInterface()
				: service.getRightInterface();
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
