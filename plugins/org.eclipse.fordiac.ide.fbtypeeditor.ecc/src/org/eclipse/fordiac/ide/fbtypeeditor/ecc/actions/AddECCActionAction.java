/*******************************************************************************
 * Copyright (c) 2007 - 2012 4DIAC - consortium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions;

import java.util.List;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.ActionCreationFactory;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.ECCEditor;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.CreateECActionCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionAlgorithmEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionOutputEventEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECStateEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

public class AddECCActionAction extends SelectionAction {

	/**
	 * Add ECC Action action id. Value: <code>"org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions.CreateStateAction"</code>
	 */
	public static final String ADD_ECC_ACTION = "org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions.AddECCActionAction";//$NON-NLS-1$

	ActionCreationFactory actionFactory = new ActionCreationFactory();
	
	public AddECCActionAction(IWorkbenchPart part) {
		super(part);
		setId(ADD_ECC_ACTION);
		setText(Messages.ECCActions_AddAction);
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ADD));
	}

	@Override
	protected boolean calculateEnabled() {
		if(1 == getSelectedObjects().size()){
			ECState state = getState(getSelectedObjects());
			if(null != state){
				//only allow to add actions if we are not the the initial state 
				return !state.isStartState();
			}
			return false;
		}
		return false;
	}


	@Override
	public void run() {		
		ECCEditor editor = (ECCEditor)getWorkbenchPart();
		ECAction action = (ECAction)actionFactory.getNewObject();
		execute(new CreateECActionCommand(action, getState(getSelectedObjects())));		
		editor.outlineSelectionChanged(action);
	}
	
	
	@SuppressWarnings("rawtypes")
	private static ECState getState(List selectedObjects) {
		ECState state = null;
		if(selectedObjects.get(0) instanceof ECStateEditPart){
			state = ((ECStateEditPart)selectedObjects.get(0)).getCastedModel();
		}else if(selectedObjects.get(0) instanceof ECActionAlgorithmEditPart){
			ECActionAlgorithmEditPart part = (ECActionAlgorithmEditPart)selectedObjects.get(0);
			state = (ECState)part.getAction().eContainer();			
		}else if(selectedObjects.get(0) instanceof ECActionOutputEventEditPart){
			ECActionOutputEventEditPart part = (ECActionOutputEventEditPart)selectedObjects.get(0);
			state = (ECState)part.getAction().eContainer();
		}
		return state;
	}
}
