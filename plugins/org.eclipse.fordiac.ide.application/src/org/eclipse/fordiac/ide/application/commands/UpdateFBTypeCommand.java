/*******************************************************************************
 * Copyright (c) 2012 - 2017 AIT, fortiss GmbH, Profactor GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Filip Andren, Alois Zoitl, Gerhard Ebenhofer, Monika Wenger 
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.commands.change.UnmapCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;

/**
 * UpdateFBTypeCommand triggers an update of the type for an FB instance
 */
public class UpdateFBTypeCommand extends Command {
	
	/** The FB view */
	private final FB fb;
	
	/** The copied FB view */
	private FB copiedFB;
		
	/** The FB type if not null this entry should be used */
	private FBTypePaletteEntry entry;
	
	private final FBNetwork network;
	
		
	private List<DeleteConnectionCommand> deleteConnCmds = new ArrayList<>();
	private List<AbstractConnectionCreateCommand> connCreateCmds = new ArrayList<>();
	
	private MapToCommand mapCmd = null;
	private UnmapCommand unmapCmd = null;
	
	
	public UpdateFBTypeCommand(FB fb) {
		this.fb = fb;
		network = (FBNetwork) fb.eContainer();
	}
	
	public UpdateFBTypeCommand(FB fb, PaletteEntry entry) {
		this.fb = fb;
		network = (FBNetwork) fb.eContainer();
		if(entry instanceof FBTypePaletteEntry){
			this.entry = (FBTypePaletteEntry)entry;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		Resource resource = null;
		if (fb.isMapped()) {
			resource = fb.getResource();
			unmapCmd = new UnmapCommand(fb);
			unmapCmd.execute();
		}
		
		// Create new FB		
		copiedFB = copyFB(fb);
		
		// Find connections which should be reconnected
		List<Connection> reConnections = new ArrayList<>();
		for (IInterfaceElement ifEle : fb.getInterface().getAllInterfaceElements()) {
			reConnections.addAll(ifEle.getInputConnections());
			reConnections.addAll(ifEle.getOutputConnections());		
		}
		for (Connection conn : reConnections) {
			doReconnect(conn, findUpdatedInterfaceElement(copiedFB, conn.getSource()),
					findUpdatedInterfaceElement(copiedFB, conn.getDestination()));
		}
		
		// Change name
		String name = fb.getName();
		copiedFB.setName(name);	

				
		// Map FB
		if (resource != null) {
			mapCmd = new MapToCommand(copiedFB, resource);
			if (mapCmd.canExecute()) {
				mapCmd.execute();
			}
		}
	}
	

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		if (unmapCmd != null) {
			unmapCmd.redo();
		}

		for (DeleteConnectionCommand cmd : deleteConnCmds) {
			cmd.redo();
		}

		replaceFBViews(fb, copiedFB);
		
		for (AbstractConnectionCreateCommand cmd : connCreateCmds) {
			cmd.redo();
		}

		if (mapCmd != null) {
			mapCmd.redo();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		if (mapCmd != null) {
			mapCmd.undo();
		}
		
		for (AbstractConnectionCreateCommand cmd : connCreateCmds) {
			cmd.undo();
		}
		
		replaceFBViews(copiedFB, fb);

		for (DeleteConnectionCommand cmd : deleteConnCmds) {
			cmd.undo();
		}
		
		if (unmapCmd != null) {
			unmapCmd.undo();
		}
		
	}

	private IInterfaceElement findUpdatedInterfaceElement(FB copiedFB, IInterfaceElement orig) {
		if (orig == null) {
			// this happens when a connection has already been reconnected,
			// i.e., a connection from an output to an input of the same FB
			return null;
		}
		IInterfaceElement retval = orig;
		if (orig.getFBNetworkElement() == fb) {
			// origView is an interface of the original FB => find same interface on copied FB
			retval = copiedFB.getInterfaceElement(orig.getName());
		}
		return retval;
	}
	
	private void doReconnect(Connection oldConn, IInterfaceElement source, IInterfaceElement dest) {
		//the connection may be allready in our list if source and dest are on our FB
		if(!isInDeleteConnList(oldConn)){
			FBNetwork fbn = oldConn.getFBNetwork();
			//we have to delete the old connection in all cases
			DeleteConnectionCommand cmd = new DeleteConnectionCommand(oldConn);
			cmd.execute();
			deleteConnCmds.add(cmd);
	
			if (source != null && dest != null) {			
				// if source or dest is null it means that an interface element is not available any more
				AbstractConnectionCreateCommand dccc = null;
				
				if(oldConn instanceof EventConnection){
					dccc = new EventConnectionCreateCommand(fbn);
				} else if(oldConn instanceof DataConnection){
					dccc = new DataConnectionCreateCommand(fbn);
				} else if(oldConn instanceof AdapterConnection){
					dccc = new AdapterConnectionCreateCommand(fbn);
				}
				if(null != dccc){
					dccc.setSource(source);
					dccc.setDestination(dest);
					if (dccc.canExecute()) {
						dccc.execute();
						connCreateCmds.add(dccc);
					}
				}
			}
		}
	}

	private boolean isInDeleteConnList(Connection conn) {
		for (DeleteConnectionCommand cmd : deleteConnCmds) {
			if(cmd.getConnectionView().equals(conn)){
				return true;
			}
		}
		return false;
	}

	private void replaceFBViews(FB oldFB, FB newFB) {
		network.getNetworkElements().remove(oldFB);
		network.getNetworkElements().add(newFB);
	}

	private FB copyFB(FB srcFB) {
		FB copiedFB = createCopiedFBEntry(srcFB);
		copiedFB.setInterface(EcoreUtil.copy(copiedFB.getType().getInterfaceList()));
		copiedFB.setName(srcFB.getName());

		// Fix for similar issue as reported in [issue#933] CompositeEditor - setting constant values not possible
		// initialize the value element of all vardeclarations which have no parameters 
		for (VarDeclaration var : copiedFB.getInterface().getInputVars()) {
			if (var.getValue() == null) {
					var.setValue(LibraryElementFactory.eINSTANCE.createValue());
			}
		}
		
		copiedFB.setX(srcFB.getX());
		copiedFB.setY(srcFB.getY());
		createValues(copiedFB);
		
		pasteParams(srcFB, copiedFB);
		replaceFBViews(srcFB, copiedFB);
		return copiedFB;
	}

	protected FB createCopiedFBEntry(FB srcFB) {
		FB fb = null;
		
		if(srcFB instanceof AdapterFB){
			AdapterFB aFB = LibraryElementFactory.eINSTANCE.createAdapterFB();
			aFB.setAdapterDecl(((AdapterFB)srcFB).getAdapterDecl());
			fb = aFB;				
		}
		else{
			fb = LibraryElementFactory.eINSTANCE.createFB();
		}
		
		//Entry handling is here to allow the reuse of this class also for adapter updates
		if(null == entry){
			fb.setPaletteEntry(srcFB.getPaletteEntry());
		}else{
			fb.setPaletteEntry(entry);
		}		
		return fb; 
	}
	
	private void pasteParams(FB src, FB dst) {
		InterfaceList interfaceList = src.getInterface();
		if (interfaceList != null) {
			for (VarDeclaration varDecl : interfaceList .getInputVars()) {
				if (dst.getInterfaceElement(varDecl.getName()) != null) {
					// interface exist on new type
					if (varDecl.getInputConnections().size() == 0) {
						Value value = varDecl.getValue();
						if (value != null && value.getValue() != null) {
							Value newValue = LibraryElementFactory.eINSTANCE.createValue();
							newValue.setValue(value.getValue());
							dst.getInterfaceElement(varDecl.getName()).setValue(newValue);
						}
					}
				}
			}
		}

	}
	
	protected void createValues(FB copiedFB) {
		for (IInterfaceElement element : copiedFB.getInterface().getInputVars()) {
			Value value = LibraryElementFactory.eINSTANCE.createValue();
			element.setValue(value);

		}
	}
}
