/*******************************************************************************
 * Copyright (c) 2011 - 2017 TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.ServiceInterfacePaletteFactory;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.gef.commands.Command;

public class CreateInputPrimitiveCommand extends Command {

	private final String type;
	private final ServiceTransaction element;
	private InputPrimitive newPrimitive;

	public CreateInputPrimitiveCommand(String type, ServiceTransaction element) {
		this.type = type;
		this.element = element;
	}

	@Override
	public boolean canExecute() {
		if (type == null || element == null) {
			return false;
		}
		if (element.getInputPrimitive() != null) {
			return false;
		}
		return true;
	}

	@Override
	public void execute() {
		Service service = (Service) element.eContainer().eContainer();
		newPrimitive = LibraryElementFactory.eINSTANCE.createInputPrimitive();
		newPrimitive.setEvent("INIT"); //$NON-NLS-1$
		if (type.equals(ServiceInterfacePaletteFactory.LEFT_INPUT_PRIMITIVE)) {			
			newPrimitive.setInterface(service.getLeftInterface());
		} else{
			newPrimitive.setInterface(service.getRightInterface());
		} 
		element.setInputPrimitive(newPrimitive);
		super.execute();
	}
	
	@Override
	public void undo() {
		element.setInputPrimitive(null);		
	}
	
	@Override
	public void redo() {
		element.setInputPrimitive(newPrimitive);
	}
}
