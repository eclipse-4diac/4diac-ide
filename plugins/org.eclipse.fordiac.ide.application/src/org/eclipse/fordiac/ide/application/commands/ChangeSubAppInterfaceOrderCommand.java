/*******************************************************************************
 * Copyright (c) 2018 Johannes Kepler University
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

import org.eclipse.fordiac.ide.model.commands.change.ChangeInterfaceOrderCommand;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;

public class ChangeSubAppInterfaceOrderCommand extends ChangeInterfaceOrderCommand {

	ChangeInterfaceOrderCommand mirroredElement = null;
	
	public ChangeSubAppInterfaceOrderCommand(IInterfaceElement selection, boolean isInput, boolean moveUp) {
		super(selection, isInput, moveUp);
		
		if(selection.getFBNetworkElement().isMapped()) {
			mirroredElement = new ChangeInterfaceOrderCommand(selection.getFBNetworkElement().getOpposite().getInterfaceElement(selection.getName()),
					isInput, moveUp);
		}
	}
	
	@Override
	public void execute() {
		super.execute();
		if(null != mirroredElement) {
			mirroredElement.execute();
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
