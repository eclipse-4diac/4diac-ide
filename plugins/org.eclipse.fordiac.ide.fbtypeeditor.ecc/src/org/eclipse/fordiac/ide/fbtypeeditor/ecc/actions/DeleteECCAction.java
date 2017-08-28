/*******************************************************************************
 * Copyright (c) 2013 fortiss GmbH
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
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionAlgorithmEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionOutputEventEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECStateEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECTransitionEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.DeleteAction;
import org.eclipse.ui.IWorkbenchPart;

public class DeleteECCAction extends DeleteAction {

	public DeleteECCAction(IWorkbenchPart part) {
		super(part);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Command createDeleteCommand(List objects) {
		if (objects.isEmpty())
			return null;
		if (!(objects.get(0) instanceof EditPart))
			return null;

		ArrayList<EditPart> list = new ArrayList<EditPart>();

		//before anything else add the transitions 
		for (Object object : objects) {
			if(object instanceof ECTransitionEditPart){
				list.add((ECTransitionEditPart)object);
			}
		}
				
		
		
		for (int i = 0; i < objects.size(); i++) {
			EditPart object = (EditPart) objects.get(i);
			if(object instanceof ECActionAlgorithmEditPart){
				if(!stateContainedInDeleteList(objects, (ECState)((ECActionAlgorithmEditPart)object).getCastedModel().getAction().eContainer())){
					list.add(object);
				}
			}else if (object instanceof ECActionOutputEventEditPart){
				if(!stateContainedInDeleteList(objects, (ECState)((ECActionOutputEventEditPart)object).getCastedModel().getAction().eContainer())){
					list.add(object);
				}
			}
			else{
				if(!(object instanceof ECTransitionEditPart)){
					list.add(object);
				}
			}
		}
		
		
		return super.createDeleteCommand(list);
	}

	@SuppressWarnings("rawtypes")
	private boolean stateContainedInDeleteList(List objects, ECState eState) {
		for (int i = 0; i < objects.size(); i++) {
			EditPart object = (EditPart) objects.get(i);
			if(object instanceof ECStateEditPart){
				if(((ECStateEditPart)object).getCastedModel().equals(eState)){
					return true;
				}				
			}
		}
		return false;
	}
	
	

}
