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
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.gef.commands.Command;

public class CreateOutputPrimitiveCommand extends Command {

	private final String type;
	private final ServiceTransaction parent;
	private final OutputPrimitive refElement;
	private OutputPrimitive newElement;

	public CreateOutputPrimitiveCommand(String type, ServiceTransaction element, OutputPrimitive refElement) {
		this.type = type;
		this.parent = element;
		this.refElement = refElement;
	}

	@Override
	public boolean canExecute() {
		if (type == null || parent == null) {
			return false;
		}
		return true;
	}

	@Override
	public void execute() {
		Service service = (Service) parent.eContainer().eContainer();
		newElement = LibraryElementFactory.eINSTANCE.createOutputPrimitive();
		newElement.setEvent("INITO");	 //$NON-NLS-1$
		if (type.equals(ServiceInterfacePaletteFactory.LEFT_OUTPUT_PRIMITIVE)) {			
			newElement.setInterface(service.getLeftInterface());
		} else if (type.equals(ServiceInterfacePaletteFactory.RIGHT_OUTPUT_PRIMITIVE)) {
			newElement.setInterface(service.getRightInterface());
		}
		if(null == refElement){
			parent.getOutputPrimitive().add(newElement);
		}else{
			int index = parent.getOutputPrimitive().indexOf(refElement);
			parent.getOutputPrimitive().add(index, newElement);
		}
	}
	
	@Override
	public void undo() {
		parent.getOutputPrimitive().remove(newElement);		
	}
	
	@Override
	public void redo() {
		if(null == refElement){
			parent.getOutputPrimitive().add(newElement);
		}
		else{
			int index = parent.getOutputPrimitive().indexOf(refElement);
			parent.getOutputPrimitive().add(index, newElement);
		}
	}
}
