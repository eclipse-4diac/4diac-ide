/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.commands.create;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.EventType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.gef.commands.Command;

public class CreateInterfaceElementCommand extends Command {
	private boolean isInput;
	private DataType dataType;
	private IInterfaceElement interfaceElement;
	private EList<? extends IInterfaceElement> interfaces;
	private int index;
	private String name;
	private String comment;
	private InterfaceList interfaceList;
	private AdapterCreateCommand cmd;

	public CreateInterfaceElementCommand(DataType dataType, String name, String comment, InterfaceList interfaceList, boolean isInput, int index){
		this.isInput = isInput;
		this.dataType = dataType;
		this.index = index;
		this.name = name;
		this.comment = comment;
		this.interfaceList = interfaceList;
	}
		
	@Override
	public boolean canExecute() {
		return null != dataType && (null != interfaceList);
	}	
	
	private void setInterfaces(InterfaceList interfaceList){
		if(isInput){
			if(dataType instanceof EventType){
				this.interfaces = interfaceList.getEventInputs();				
			}else{
				if(dataType instanceof AdapterType){
					this.interfaces = interfaceList.getSockets();
				}else{
					this.interfaces = interfaceList.getInputVars();
				}
			}
		}else{
			if(dataType instanceof EventType){
				this.interfaces = interfaceList.getEventOutputs();
			}else{
				if(dataType instanceof AdapterType){
					this.interfaces = interfaceList.getPlugs();
				}else{
					this.interfaces = interfaceList.getOutputVars();
				}
			}
		}
	}
	
	@Override
	public void execute() {
		if(dataType instanceof EventType){
			interfaceElement = LibraryElementFactory.eINSTANCE.createEvent();
		}else{
			if(dataType instanceof AdapterType){
				interfaceElement = LibraryElementFactory.eINSTANCE.createAdapterDeclaration();
			}else{
				interfaceElement = LibraryElementFactory.eINSTANCE.createVarDeclaration();
			}
		}
		setInterfaces(interfaceList);
		interfaceElement.setIsInput(isInput);
		interfaceElement.setType(dataType);
		interfaceElement.setTypeName(dataType.getName());
		interfaceElement.setComment(comment);
		if(dataType instanceof AdapterType && interfaceList.eContainer() instanceof CompositeFBType){
			cmd = new AdapterCreateCommand(10, 10, (AdapterDeclaration) interfaceElement, (CompositeFBType)interfaceList.eContainer());
		}
		redo();
		interfaceElement.setName(NameRepository.createUniqueName(interfaceElement, null == name || name.isEmpty() ? dataType.getName() : name));
	}
	
	@Override
	public void redo() {
		@SuppressWarnings("unchecked")
		EList<IInterfaceElement> temp = (EList<IInterfaceElement>) interfaces;
		temp.add(index == -1 ? temp.size() : index, interfaceElement);
		if(null != cmd){
			cmd.execute();
		}
	}
	
	@Override
	public void undo() {
		@SuppressWarnings("unchecked")
		EList<IInterfaceElement> temp = (EList<IInterfaceElement>) interfaces;
		temp.remove(interfaceElement);
		if(null != cmd && cmd.canExecute()){
			cmd.undo();
		}
	}
}
