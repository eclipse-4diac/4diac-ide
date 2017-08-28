/*******************************************************************************
 * Copyright (c) 2016, 2017 fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *    - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import org.eclipse.fordiac.ide.application.ApplicationPlugin;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.ui.IEditorPart;

public abstract class AbstractReconnectConnectionCommand extends Command {
	protected final FBNetwork parent;
	protected final ReconnectRequest request;
	private IEditorPart editor;
	protected DeleteConnectionCommand deleteConnectionCmd;
	protected AbstractConnectionCreateCommand connectionCreateCmd;

	public AbstractReconnectConnectionCommand(String label, final ReconnectRequest request, final FBNetwork parent) {
		super(label);
		this.request = request;
		this.parent = parent;
	}

	@Override
	public boolean canUndo() {
		return editor.equals(ApplicationPlugin.getCurrentActiveEditor());	
	}
	
	@Override
	public boolean canRedo() {
		//The default implementation of canRedo calls canExecute. The data need for canExecute is at this time not 
		//valid any more. Therefore we need to have an own implementation. 
		//TODO model refactoring - consider if this can always be true
		return (null != deleteConnectionCmd) && (null != connectionCreateCmd);
	}
	
	@Override
	public boolean canExecute() {
		EditPart source = null;
		EditPart target = null;
		if (request.getType().equals(RequestConstants.REQ_RECONNECT_TARGET)) {
			source = request.getConnectionEditPart().getSource();
			target = request.getTarget();
		} else if (request.getType().equals(RequestConstants.REQ_RECONNECT_SOURCE)) {
			source = request.getTarget();
			target = request.getConnectionEditPart().getTarget();
		}		
		if((source instanceof InterfaceEditPart) && (target instanceof InterfaceEditPart)){
			IInterfaceElement sourceIE = ((InterfaceEditPart)source).getModel();
			IInterfaceElement targetIE = ((InterfaceEditPart)target).getModel();
			return checkSourceAndTarget(sourceIE, targetIE);
		}	
		return false;
	}
	
	@Override
	public void execute() {
		editor = ApplicationPlugin.getCurrentActiveEditor();
		if (request.getType().equals(RequestConstants.REQ_RECONNECT_TARGET)) {
			doReconnectTarget();
		}
		if (request.getType().equals(RequestConstants.REQ_RECONNECT_SOURCE)) {
			doReconnectSource();
		}	
	}

	protected void doReconnectSource() {
		deleteConnectionCmd = new DeleteConnectionCommand((Connection) request.getConnectionEditPart().getModel());
		connectionCreateCmd = createConnectionCreateCommand();
		connectionCreateCmd.setSource((InterfaceEditPart) request.getTarget());
		connectionCreateCmd.setDestination((InterfaceEditPart) request.getConnectionEditPart().getTarget());
		deleteConnectionCmd.execute();
		connectionCreateCmd.execute();
	}
	
	protected void doReconnectTarget() {
		deleteConnectionCmd = new DeleteConnectionCommand((Connection)request.getConnectionEditPart().getModel());
		connectionCreateCmd = createConnectionCreateCommand();
		connectionCreateCmd.setSource((InterfaceEditPart) request.getConnectionEditPart().getSource());
		connectionCreateCmd.setDestination((InterfaceEditPart) request.getTarget());
		deleteConnectionCmd.execute();
		connectionCreateCmd.execute();
	}

	@Override
	public void redo() {
		deleteConnectionCmd.redo();
		connectionCreateCmd.redo();	
	}
	
	@Override
	public void undo() {
		connectionCreateCmd.undo();
		deleteConnectionCmd.undo();
	}
	
	protected abstract AbstractConnectionCreateCommand createConnectionCreateCommand();
	
	protected abstract boolean checkSourceAndTarget(IInterfaceElement sourceIE, IInterfaceElement targetIE);	
}