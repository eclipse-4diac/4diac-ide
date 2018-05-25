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
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.AdapterConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

/**
 * UpdateFBTypeCommand triggers an update of the type for an FB instance
 */
public class UpdateFBTypeCommand extends Command {
	
	//hleper data class for stroing connectiond ata of resoruce connection as the connections are lost during the unmapping process
	class ConnData{
		public IInterfaceElement source;
		public IInterfaceElement dest;
		
		public ConnData(IInterfaceElement source, IInterfaceElement dest) {
			this.source = source;
			this.dest = dest;
		}	
		
	};
	
	/** The FB view */
	private FB fb;
	
	/** The copied FB view */
	private FB copiedFB;
		
	/** The if not null this entry should be used for the type of the updated FB*/
	private PaletteEntry entry;
	
	private FBNetwork network;
		
	private CompoundCommand deleteConnCmds = new CompoundCommand();
	private CompoundCommand connCreateCmds = new CompoundCommand();
	private CompoundCommand resourceConnCreateCmds = new CompoundCommand();
	
	private MapToCommand mapCmd = null;
	private UnmapCommand unmapCmd = null;
	
	
	public UpdateFBTypeCommand(FB fb) {
		this.fb = fb;
		network = (FBNetwork) fb.eContainer();
	}
	
	public UpdateFBTypeCommand(FB fb, PaletteEntry entry) {
		this.fb = fb;
		network = (FBNetwork) fb.eContainer();
		if(entry instanceof FBTypePaletteEntry || entry instanceof AdapterTypePaletteEntry){
			this.entry = entry;
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
		List<ConnData> resourceConns = null; //buffer for the resource specific connections form the mapped fb that need to be recreated
		if (fb.isMapped()) {
			if(network.equals(fb.getResource().getFBNetwork())) {
				//this is the resource fb we need to use the oposite for a correct update
				fb = (FB)fb.getOpposite();
				network = fb.getFbNetwork();
			}
			resource = fb.getResource();
			resourceConns = getResourceCons();
			unmapCmd = new UnmapCommand(fb);
			unmapCmd.execute();
		}
		
		// Create new FB		
		copyFB(fb);
		
		// Find connections which should be reconnected
		handleApplicationConnections();
		
		// Change name
		copiedFB.setName(fb.getName());	
				
		// Map FB
		if (resource != null) {
			mapCmd = new MapToCommand(copiedFB, resource);
			if (mapCmd.canExecute()) {
				mapCmd.execute();
				recreateResourceConns(resourceConns);
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

		deleteConnCmds.redo();
		replaceFBs(fb, copiedFB);		
		connCreateCmds.redo();

		if (mapCmd != null) {
			mapCmd.redo();
			//after redoing the mapping for the new FB recreate the resoruce cons
			resourceConnCreateCmds.redo();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		if (mapCmd != null) {
			//before removing the copied FB remove the newly created resource connections
			resourceConnCreateCmds.undo(); 
			mapCmd.undo();
		}
		
		connCreateCmds.undo();		
		replaceFBs(copiedFB, fb);
		deleteConnCmds.undo();
		
		if (unmapCmd != null) {
			unmapCmd.undo();
		}		
	}
	
	private void handleApplicationConnections() {
		for (Connection conn : getAllConnectionsForFB(fb)) {
			doReconnect(conn, findUpdatedInterfaceElement(copiedFB, fb, conn.getSource()),
					findUpdatedInterfaceElement(copiedFB, fb, conn.getDestination()));
		}
	}	

	private static List<Connection> getAllConnectionsForFB(FB functionBlock) {
		List<Connection> retVal = new ArrayList<>();
		for (IInterfaceElement ifEle : functionBlock.getInterface().getAllInterfaceElements()) {
			if(ifEle.isIsInput()) {
				retVal.addAll(ifEle.getInputConnections());
			} else {
				retVal.addAll(ifEle.getOutputConnections());
			}
		}		
		return retVal;
	}


	private static IInterfaceElement findUpdatedInterfaceElement(FB copiedFB, FB orgFB, IInterfaceElement orig) {
		if (orig == null) {
			// this happens when a connection has already been reconnected,
			// i.e., a connection from an output to an input of the same FB
			return null;
		}
		IInterfaceElement retval = orig;
		if (orig.getFBNetworkElement() == orgFB) {
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
				AbstractConnectionCreateCommand dccc = createConnCreateCMD(source, fbn);
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

	private static AbstractConnectionCreateCommand createConnCreateCMD(IInterfaceElement refIE, FBNetwork fbn) {
		AbstractConnectionCreateCommand retVal = null;
		if(refIE instanceof Event){
			retVal = new EventConnectionCreateCommand(fbn);
		} else if(refIE instanceof AdapterDeclaration){
			retVal = new AdapterConnectionCreateCommand(fbn);
		} else if(refIE instanceof VarDeclaration){
			retVal = new DataConnectionCreateCommand(fbn);
		}
		return retVal;
	}

	private boolean isInDeleteConnList(Connection conn) {
		for (Object cmd : deleteConnCmds.getCommands()) {
			if(((DeleteConnectionCommand)cmd).getConnectionView().equals(conn)){
				return true;
			}
		}
		return false;
	}

	private void replaceFBs(FB oldFB, FB newFB) {
		network.getNetworkElements().remove(oldFB);
		network.getNetworkElements().add(newFB);
	}

	private void copyFB(FB srcFB) {
		copiedFB = createCopiedFBEntry(srcFB);
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
		replaceFBs(srcFB, copiedFB);
	}

	protected FB createCopiedFBEntry(FB srcFB) {
		FB copy = null;
		
		if(srcFB instanceof AdapterFB){
			AdapterFB aFB = LibraryElementFactory.eINSTANCE.createAdapterFB();
			aFB.setAdapterDecl(((AdapterFB)srcFB).getAdapterDecl());
			copy = aFB;				
		}
		else{
			copy = LibraryElementFactory.eINSTANCE.createFB();
		}
		
		//Entry handling is here to allow the reuse of this class also for adapter updates
		if(null == entry){
			copy.setPaletteEntry(srcFB.getPaletteEntry());
		}else{
			copy.setPaletteEntry(entry);
		}		
		return copy; 
	}
	
	private static void pasteParams(FB src, FB dst) {
		InterfaceList interfaceList = src.getInterface();
		if (interfaceList != null) {
			for (VarDeclaration varDecl : interfaceList .getInputVars()) {
				if (dst.getInterfaceElement(varDecl.getName()) != null) {
					// interface exist on new type
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
	
	protected void createValues(FB copiedFB) {
		for (IInterfaceElement element : copiedFB.getInterface().getInputVars()) {
			Value value = LibraryElementFactory.eINSTANCE.createValue();
			element.setValue(value);
		}
	}
	
	private List<ConnData> getResourceCons() {
		List<ConnData> retVal = new ArrayList<>();
		FB resFB = (FB)fb.getOpposite();
		for (Connection conn : getAllConnectionsForFB(resFB)) {
			IInterfaceElement source = conn.getSource();
			IInterfaceElement dest = conn.getDestination();
			if(!source.getFBNetworkElement().isMapped() || !dest.getFBNetworkElement().isMapped()) {
				//one of both ends is a resourceFB therefore the connection needs to be restored
				retVal.add(new ConnData(conn.getSource(), conn.getDestination()));
			} else if (((source.getFBNetworkElement() == resFB) && (dest.getFBNetworkElement().getOpposite().getFbNetwork() != fb.getFbNetwork())) ||
					((dest.getFBNetworkElement() == resFB) && (source.getFBNetworkElement().getOpposite().getFbNetwork() != fb.getFbNetwork()))){
				//one of both ends is a FB comming from a different fb network and therefore this is also a resource specific connection
				retVal.add(new ConnData(conn.getSource(), conn.getDestination()));
			}
			
		}
		return retVal;
	}
	
	private void recreateResourceConns(List<ConnData> resourceConns) {
		FB orgMappedFB = (FB)unmapCmd.getMappedFBNetworkElement();
		FB copiedMappedFB = (FB)copiedFB.getOpposite();
		for (ConnData connData : resourceConns) {
			IInterfaceElement source =  findUpdatedInterfaceElement(copiedMappedFB, orgMappedFB, connData.source);
			IInterfaceElement dest = findUpdatedInterfaceElement(copiedMappedFB, orgMappedFB, connData.dest);
			if (source != null && dest != null) {			
				// if source or dest is null it means that an interface element is not available any more
				AbstractConnectionCreateCommand dccc = createConnCreateCMD(source, copiedMappedFB.getFbNetwork());
				if(null != dccc){
					dccc.setSource(source);
					dccc.setDestination(dest);
					if (dccc.canExecute()) {
						dccc.execute();
						resourceConnCreateCmds.add(dccc);
					}
				}
			}
		}		
	}

}
