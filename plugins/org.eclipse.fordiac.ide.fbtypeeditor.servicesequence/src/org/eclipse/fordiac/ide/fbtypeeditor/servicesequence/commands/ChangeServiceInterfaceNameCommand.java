/*******************************************************************************
 * Copyright (c) 2014 - 2015 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.gef.commands.Command;

public class ChangeServiceInterfaceNameCommand extends Command {
	
	private FBType fb;
	private boolean isLeftInterface;
	private String name;
	private String oldName;
	
	public ChangeServiceInterfaceNameCommand(String name, FBType fb, boolean isLeftInterface){
		this.fb = fb;
		this.isLeftInterface = isLeftInterface;
		this.name = name;
	}
	
	@Override
	public void execute(){
		if(fb.getService() == null){
			fb.setService(LibraryElementFactory.eINSTANCE.createService());
		}
		if(isLeftInterface){
			if(fb.getService().getLeftInterface() == null){
				fb.getService().setLeftInterface(LibraryElementFactory.eINSTANCE.createServiceInterface());
				oldName = ""; //$NON-NLS-1$
			}else{
				oldName = fb.getService().getLeftInterface().getName();
			}		
		}else{
			if(fb.getService().getRightInterface() == null){
				fb.getService().setRightInterface(LibraryElementFactory.eINSTANCE.createServiceInterface());
				oldName = ""; //$NON-NLS-1$
			}else{
				oldName = fb.getService().getRightInterface().getName();
			}
		}
		redo();
	}
	
	@Override
	public void undo() {
		if(isLeftInterface){
			fb.getService().getLeftInterface().setName(oldName);
		}else{
			fb.getService().getRightInterface().setName(oldName);
		}
	}

	@Override
	public void redo() {
		if(isLeftInterface){
			fb.getService().getLeftInterface().setName(name);
		}else{
			fb.getService().getRightInterface().setName(name);
		}
	}
}
