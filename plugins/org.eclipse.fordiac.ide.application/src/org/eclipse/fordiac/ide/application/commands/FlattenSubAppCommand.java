/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, AIT, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Matthias Plasch, Filip Andren, Alois Zoitl, Monika Wenger
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.util.ElementSelector;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class FlattenSubAppCommand extends Command {
	final SubApp subapp;
	final FBNetwork parent;
	List<FBNetworkElement> elements = new ArrayList<>();
	List<EventConnection> transferEventConnections = new ArrayList<>();
	List<DataConnection> transferDataConnections = new ArrayList<>();
	List<AdapterConnection> transferAdapterConnections = new ArrayList<>();
	CompoundCommand deleteCommands = new CompoundCommand();
	CompoundCommand createCommands = new CompoundCommand();
	CompoundCommand mappCommands = new CompoundCommand();

	public FlattenSubAppCommand(SubApp subapp) {
		super(Messages.FlattenSubAppCommand_LABEL_FlattenSubAppCommand);
		this.subapp = subapp;
		parent = subapp.getFbNetwork();
	}

	@Override
	public void execute() {
		elements.addAll(subapp.getSubAppNetwork().getNetworkElements());
		adjustFBNElementPosition(subapp.getX(), subapp.getY());
		
		checkConnections();
		createMappCommands();
		
		deleteCommands.add(new DeleteFBNetworkElementCommand(subapp));
		deleteCommands.execute();
		
		subapp.getSubAppNetwork().getNetworkElements().removeAll(elements);
		parent.getNetworkElements().addAll(elements);
		
		subapp.getSubAppNetwork().getEventConnections().removeAll(transferEventConnections);
		parent.getEventConnections().addAll(transferEventConnections);
		
		subapp.getSubAppNetwork().getDataConnections().removeAll(transferDataConnections);
		parent.getDataConnections().addAll(transferDataConnections);
		
		subapp.getSubAppNetwork().getAdapterConnections().removeAll(transferAdapterConnections);
		parent.getAdapterConnections().addAll(transferAdapterConnections);		
		
		createCommands.execute();
		mappCommands.execute();
		
		ElementSelector selector = new ElementSelector();
		selector.selectViewObjects(elements);
	}

	@Override
	public void redo() {
		deleteCommands.redo();
		subapp.getSubAppNetwork().getNetworkElements().removeAll(elements);
		parent.getNetworkElements().addAll(elements);
		adjustFBNElementPosition(subapp.getX(), subapp.getY());
		
		subapp.getSubAppNetwork().getEventConnections().removeAll(transferEventConnections);
		parent.getEventConnections().addAll(transferEventConnections);
		
		subapp.getSubAppNetwork().getDataConnections().removeAll(transferDataConnections);
		parent.getDataConnections().addAll(transferDataConnections);
		
		subapp.getSubAppNetwork().getAdapterConnections().removeAll(transferAdapterConnections);
		parent.getAdapterConnections().addAll(transferAdapterConnections);
		
		createCommands.redo();
		mappCommands.redo();
	}

	@Override
	public void undo() {
		mappCommands.undo();
		createCommands.undo();
		parent.getNetworkElements().removeAll(elements);
		subapp.getSubAppNetwork().getNetworkElements().addAll(elements);
		adjustFBNElementPosition(-subapp.getX(), -subapp.getY());
		
		parent.getEventConnections().removeAll(transferEventConnections);
		subapp.getSubAppNetwork().getEventConnections().addAll(transferEventConnections);
		
		parent.getDataConnections().removeAll(transferDataConnections);
		subapp.getSubAppNetwork().getDataConnections().addAll(transferDataConnections);
		
		parent.getAdapterConnections().removeAll(transferAdapterConnections);
		subapp.getSubAppNetwork().getAdapterConnections().addAll(transferAdapterConnections);
		
		deleteCommands.undo();
	}
		
	private void checkConnections() {
		checkConnectionList(subapp.getSubAppNetwork().getEventConnections(), transferEventConnections);
		checkConnectionList(subapp.getSubAppNetwork().getDataConnections(), transferDataConnections);
		checkConnectionList(subapp.getSubAppNetwork().getAdapterConnections(), transferAdapterConnections);
		
	}
	
	private void createMappCommands() {
		if(subapp.isMapped()) {
			for (FBNetworkElement fbNetworkElement : elements) {
				mappCommands.add(new MapToCommand(fbNetworkElement, subapp.getResource()));
			}
		}
	}

	private <T extends Connection> void checkConnectionList(
			List<T> connectionList, List<T> transferConnectionList) {
		for (T connection : connectionList) {
			if(connection.getSourceElement() != subapp && connection.getDestinationElement() != subapp){
				//it is an internal connection transfer it
				transferConnectionList.add(connection);
			}else{
				deleteCommands.add(new DeleteConnectionCommand(connection));
				if(connection.getSourceElement() == subapp && connection.getDestinationElement() == subapp){
					for (Connection inboundConn : connection.getSource().getInputConnections()) {
						for (Connection outboundConn : connection.getDestination().getOutputConnections()) {
							createCommands.add(createConnCreateCmd(inboundConn.getSource(), outboundConn.getDestination()));
						}
					}
				} else if(connection.getSourceElement() == subapp){
					//for each connection coming into the sub app create one connection in the outer fb network
					for (Connection inboundConn : connection.getSource().getInputConnections()) {
						createCommands.add(createConnCreateCmd(inboundConn.getSource(), connection.getDestination()));
					}
				} else if (connection.getDestinationElement() == subapp){
					//for each connection going from the subapp outputs create one connection in the other fb network
					for (Connection outboundConn : connection.getDestination().getOutputConnections()) {
						createCommands.add(createConnCreateCmd(connection.getSource(), outboundConn.getDestination()));
					}
				}
			}
		}
	}

	private AbstractConnectionCreateCommand createConnCreateCmd(IInterfaceElement source, IInterfaceElement destination) {
		AbstractConnectionCreateCommand cmd = null;
		if(source instanceof Event){
			cmd = new EventConnectionCreateCommand(parent);
		}else if( source instanceof AdapterDeclaration){
			cmd = new AdapterConnectionCreateCommand(parent);
		}else if( source instanceof VarDeclaration){
			cmd = new DataConnectionCreateCommand(parent);
		}
		
		if(null != cmd){
			cmd.setSource(source);
			cmd.setDestination(destination);
		}		
		return cmd;
	}

	private void adjustFBNElementPosition(int x, int y) {
		for (FBNetworkElement fbNetworkElement : elements) {
			fbNetworkElement.setX(fbNetworkElement.getX() + x);
			fbNetworkElement.setY(fbNetworkElement.getY() + y);
		}
		
	}
		
}
