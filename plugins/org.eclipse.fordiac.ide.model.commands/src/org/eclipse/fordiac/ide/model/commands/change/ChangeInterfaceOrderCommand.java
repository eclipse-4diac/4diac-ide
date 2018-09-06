/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2018 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger - initial API and implementation and/or initial documentation
 *   Alois Zoitl   - fixed bounds issues and removed redo call from execute to 
 *   				 allow better subclasing 
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.gef.commands.Command;

public class ChangeInterfaceOrderCommand extends Command {
	private boolean isInput;
	private IInterfaceElement selection;
	private EList<? extends IInterfaceElement> interfaces;
	private int oldIndex;
	private int newIndex;
	
	private ChangeInterfaceOrderCommand(IInterfaceElement selection, boolean isInput){
		this.isInput = isInput;
		this.selection = selection;
		if(null != selection && selection.eContainer() instanceof InterfaceList){
			setInterfaces((InterfaceList)selection.eContainer());		
			oldIndex = this.interfaces.indexOf(selection);
		}
	}
	
	public ChangeInterfaceOrderCommand(IInterfaceElement selection, boolean isInput, boolean moveUp){
		this(selection, isInput);
		this.newIndex = moveUp ? oldIndex - 1 : oldIndex + 1;
		
		if(newIndex < 0) {
			newIndex = 0;
		}
		if(newIndex >= interfaces.size()) {
			newIndex = interfaces.size() - 1;
		}
	}
	
	public ChangeInterfaceOrderCommand(IInterfaceElement selection, boolean isInput, int newIndex){
		this(selection, isInput);
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
		return null != selection && interfaces.size() > 1 && interfaces.size() > newIndex;
	}
	
	@Override
	public void execute() {
		moveTo(newIndex);
	}
	
	@Override
	public void redo() {
		 moveTo(newIndex);
	}
	
	@Override
	public void undo() {
		moveTo(oldIndex);
	}
	
	private void moveTo(int index){
		@SuppressWarnings("unchecked")
		EList<IInterfaceElement> temp = (EList<IInterfaceElement>) interfaces;
		temp.move(index, selection);
	}
}
