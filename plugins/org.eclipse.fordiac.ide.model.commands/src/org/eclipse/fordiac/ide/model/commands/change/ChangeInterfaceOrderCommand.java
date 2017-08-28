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

package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.gef.commands.Command;

public class ChangeInterfaceOrderCommand extends Command {
	private boolean moveUp;
	private boolean isInput;
	private IInterfaceElement selection;
	private EList<? extends IInterfaceElement> interfaces;
	private int oldIndex;
	private int newIndex;
	
	private ChangeInterfaceOrderCommand(InterfaceList interfaceList, IInterfaceElement selection, boolean isInput){
		this.isInput = isInput;
		this.selection = selection;
		if(!"".equals(interfaceList)){ //$NON-NLS-1$
			setInterfaces(interfaceList);		
			if(null != selection && !"".equals(interfaces)){ 
				oldIndex = this.interfaces.indexOf(selection);
			}
		}
	}
	
	public ChangeInterfaceOrderCommand(InterfaceList interfaceList, IInterfaceElement selection, boolean isInput, boolean moveUp){
		this(interfaceList, selection, isInput);
		this.moveUp = moveUp;
		this.newIndex = -1;
	}
	
	public ChangeInterfaceOrderCommand(InterfaceList interfaceList, IInterfaceElement selection, boolean isInput, int newIndex){
		this(interfaceList, selection, isInput);
		this.newIndex = newIndex;
	}
	
	private void setInterfaces(InterfaceList interfaceList){
		if(isInput){
			if(selection instanceof Event){
				this.interfaces = interfaceList.getEventInputs();				
			}else{
				if(selection instanceof AdapterDeclaration){
					this.interfaces = interfaceList.getSockets();
				}else{
					this.interfaces = interfaceList.getInputVars();
				}
			}
		}else{
			if(selection instanceof Event){
				this.interfaces = interfaceList.getEventOutputs();
			}else{
				if(selection instanceof AdapterDeclaration){
					this.interfaces = interfaceList.getPlugs();
				}else{
					this.interfaces = interfaceList.getOutputVars();
				}
			}
		}
	}
	
	@Override
	public boolean canExecute() {
		return null != selection && !"".equals(interfaces) && interfaces.size() > 1 
				&& ((newIndex == -1 && ((moveUp && oldIndex > 0) || !moveUp && oldIndex < interfaces.size()) || (newIndex != -1 && interfaces.size() >= newIndex)));
	}
	
	@Override
	public void execute() {
		redo();
	}
	
	@Override
	public void redo() {
		 @SuppressWarnings("unchecked")
		EList<IInterfaceElement> temp = (EList<IInterfaceElement>) interfaces;
		temp.move(oldIndex > 0 && moveUp ? oldIndex - 1 : oldIndex + 1, selection);					
	}
	
	@Override
	public void undo() {
		@SuppressWarnings("unchecked")
		EList<IInterfaceElement> temp = (EList<IInterfaceElement>) interfaces;
		temp.move(oldIndex, selection);
	}
}
