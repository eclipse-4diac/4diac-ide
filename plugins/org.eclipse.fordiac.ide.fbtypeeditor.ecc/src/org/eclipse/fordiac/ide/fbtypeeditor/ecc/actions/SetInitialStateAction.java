/*******************************************************************************
 * Copyright (c) 2011 - 2015 TU Wien ACIN, fortiss GmbH
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

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeInitialStateCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECStateEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

public class SetInitialStateAction extends SelectionAction {

	/**
	 * Set Initial State Action action id. Value: <code>"org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions.SetInitialStateAction"</code>
	 */
	public static final String SET_INITIAL_STATE_ACTION = "org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions.SetInitialStateAction";//$NON-NLS-1$

	public SetInitialStateAction(IWorkbenchPart part) {
		super(part);
		setId(SET_INITIAL_STATE_ACTION);
		setText(Messages.ECCActions_InitialState);
	}

	@Override
	protected boolean calculateEnabled() {
		if(1 == getSelectedObjects().size()){
			if(getSelectedObjects().get(0) instanceof ECStateEditPart){
				ECState state = ((ECStateEditPart)getSelectedObjects().get(0)).getCastedModel();
				if(null != state){
					//we can only set this state as initial state when we are not the initial sate and have no actions
					return (!state.isStartState()) && state.getECAction().isEmpty();
				}
			}
		}
		return false;
	}
	
	@Override
	public void run() {	
		ECState state = ((ECStateEditPart)getSelectedObjects().get(0)).getCastedModel();		
		execute(new ChangeInitialStateCommand(state));
		refresh();
	}

}
