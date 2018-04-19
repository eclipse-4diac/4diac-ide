/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;

public class CreateSubAppInterfaceElementCommand extends CreateInterfaceElementCommand {

	CreateInterfaceElementCommand mirroredElement = null;

	public CreateSubAppInterfaceElementCommand(DataType dataType, InterfaceList interfaceList, boolean isInput,
			int index) {
		super(dataType, interfaceList, isInput, index);
	}

	@Override
	public void execute() {
		super.execute();
		if(interfaceList.getFBNetworkElement().isMapped()) {
			//the subapp is mapped so we need to created the interface element also in the opposite entry
			mirroredElement = new CreateInterfaceElementCommand(dataType, interfaceList.getFBNetworkElement().getOpposite().getInterface(),
					isInput, index);
			mirroredElement.execute();
			//Set the same name as the one we have also on the mirrored
			mirroredElement.getInterfaceElement().setName(interfaceElement.getName());
		}
	}

	@Override
	public void redo() {
		super.redo();
		if(null != mirroredElement) {
			mirroredElement.redo();
		}
	}

	@Override
	public void undo() {
		super.undo();
		if(null != mirroredElement) {
			mirroredElement.undo();
		}
	}

}
