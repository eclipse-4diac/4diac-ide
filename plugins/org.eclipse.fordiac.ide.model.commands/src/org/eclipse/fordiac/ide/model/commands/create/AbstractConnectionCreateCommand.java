/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011 - 2017 Profactor GmbH, TU Wien ACIN, AIT, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Matthias Plasch, Filip Anderen, Monika Wenger
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.ui.controls.Abstract4DIACUIPlugin;
import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IEditorPart;



public abstract class AbstractConnectionCreateCommand extends Command {

	private int connDx1;
	
	private int connDx2;
	
	private int connDy;
	
	/** The editor. */
	private IEditorPart editor;

	protected IEditorPart getEditor() {
		return editor;
	}

	/** The parent. */
	private final FBNetwork parent;

	/** The connection view. */
	private Connection connection;
	
	/** The source view. */
	protected IInterfaceElement source;

	/** The destination view. */
	protected IInterfaceElement destination;
	
	/** flag to indicate if during execution of this command a mirrored connection in the opposite
	 *  element (e.g., resrouce for app) should be created.
	 *  
	 *  This flag is here so that the command can be reused also for creating the mirrored connection.
	 */
	private boolean performMappingCheck;

	private AbstractConnectionCreateCommand mirroredConnection; 

	
	public AbstractConnectionCreateCommand(FBNetwork parent){
		super();
		//initialize values 
		this.connDx1 = 0;
		this.connDx2 = 0;
		this.connDy = 0;
		this.parent = parent;
		this.performMappingCheck = true;
	}
	
	public void setArrangementConstraints(int dx1, int dx2, int dy){		
		this.connDx1 = dx1;
		this.connDx2 = dx2;
		this.connDy = dy;
	}

	@Override
	public boolean canUndo() {
		return getEditor().equals(Abstract4DIACUIPlugin.getCurrentActiveEditor());
	}
	
	@Override
	public abstract boolean canExecute();
	
	
	public void setSource(final IInterfaceElement source) {
		this.source = source;
	}
	
	public IInterfaceElement getSource(){
		return source;
	}

	
	public void setDestination(final IInterfaceElement target) {
		this.destination = target;
	}
	
	@Override
	public void execute() {
		editor = Abstract4DIACUIPlugin.getCurrentActiveEditor();
		
		checkSourceAndTarget();
		
		connection = createConnectionElement();

		connection.setSource(source);
		connection.setDestination(destination);
		connection.setDx1(connDx1);
		connection.setDx2(connDx2);
		connection.setDy(connDy);
		
		parent.addConnection(connection);
		
		if(performMappingCheck){
			mirroredConnection = checkAndCreateMirroredConnection();
			if(null != mirroredConnection){
				mirroredConnection.execute();
			}
		}
	}
	
	@Override
	public void undo() {
		if(null != mirroredConnection){
			mirroredConnection.undo();
		}

		connection.setSource(null);
		connection.setDestination(null);
		parent.removeConnection(connection);
	}

	@Override
	public void redo() {
		connection.setSource(source);
		connection.setDestination(destination);
		parent.addConnection(connection);

		if(null != mirroredConnection){
			mirroredConnection.redo();
		}
	}
	
	protected void checkSourceAndTarget(){
		boolean needsChange;

		if ((source.eContainer().eContainer() instanceof CompositeFBType) || 
			 (source.getFBNetworkElement().getFbNetwork() != parent)){  // the FBNetwork elements are not in the same resource this means one of both is a subapp interface element of the containing subapp
			needsChange = !source.isIsInput();
		} else {
			needsChange = source.isIsInput(); 
		}

		if (needsChange) {
			// our src is an input we have to swap source and target
			IInterfaceElement buf = destination;
			destination = source;
			source = buf;
		}
	}
	
	protected abstract Connection createConnectionElement();

	/** Check if the mapping of source and target require mirrored connection to be created and setup a 
	 *  connection create command accordingly. The execute, undo, and redo will be invoked by AbstractCreateCommand
	 *  when required. 
	 * 
	 * @return a connectioncreatecommand if a mirrord connection should be created, null otherwise
	 */
	private AbstractConnectionCreateCommand checkAndCreateMirroredConnection(){
		if(null != source.getFBNetworkElement() && null != destination.getFBNetworkElement()){
			FBNetworkElement opSource = source.getFBNetworkElement().getOpposite();
			FBNetworkElement opDestination = destination.getFBNetworkElement().getOpposite();
			if(null != opSource && null != opDestination && opSource.getFbNetwork() == opDestination.getFbNetwork()){
				AbstractConnectionCreateCommand cmd = createMirroredConnectionCommand(opSource.getFbNetwork()); 
				cmd.setPerformMappingCheck(false);  //as this is the command for the mirrored connection we don't want again to check
				cmd.setSource(opSource.getInterfaceElement(source.getName()));
				cmd.setDestination(opDestination.getInterfaceElement(destination.getName()));
				return (cmd.canExecute()) ? cmd : null;
			}
		}
		return null;
	}

	/**Create a conneciton command for creating a mirrored connection for the connection type
	 * 
	 * @param fbNetwork  the fbn network for the mirrord connection
	 * @return the command for the connection must not be null
	 */
	protected abstract AbstractConnectionCreateCommand createMirroredConnectionCommand(FBNetwork fbNetwork);

	private void setPerformMappingCheck(boolean performMappingCheck) {
		this.performMappingCheck = performMappingCheck;
	}

}
