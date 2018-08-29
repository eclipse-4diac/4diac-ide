/*******************************************************************************
 * Copyright (c) 2014 - 2017 Luka Lednicki, fortiss GmbH
 * 				 2018 Johannes Kepler University	
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Luka Lednicki, Gerd Kainz, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - cleaned up some code fix for multiple data connections   
 *******************************************************************************/
package org.eclipse.fordiac.ide.comgeneration.implementation;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Link;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;


public class Analyzer {
	
	private CommunicationModel communicationModel;
	
	public CommunicationModel analyze(Application application) {	
		communicationModel = new CommunicationModel();
		for (EventConnection connection : application.getFBNetwork().getEventConnections()) {
			collectChannels(connection);
		}
		for (DataConnection connection : application.getFBNetwork().getDataConnections()) {
			collectChannels(connection);
		}
		collectMediaInformation();
		return communicationModel;
	}
	
	
	public void collectChannels(Connection connection) {
		if(connection.getSourceElement().isMapped() && connection.getDestinationElement().isMapped() &&
				connection.getSourceElement().getResource() != connection.getDestinationElement().getResource()) {
			//we only not to add this connection if both ends are mapped to two different resources		
			
			List<Event> sourceEvents = getSourceEvents(connection); 
			Resource sourceResource = connection.getSourceElement().getResource();
			Resource destinationResource = connection.getDestinationElement().getResource();	
			
			FBNetworkElement mappedElement = connection.getDestinationElement().getOpposite();
			boolean local = sourceResource.getDevice() == destinationResource.getDevice();
			
			for (Event sourceEvent : sourceEvents) {
				CommunicationChannel channel = getComChannel(local, sourceEvent, connection);
				if(null != channel) {
					CommunicationChannelDestination destination = channel.getDestination(destinationResource);
					
					destination.getConnection().add(connection);
					int portIndex = getPortIndex(connection, sourceEvent);
					
					List<IInterfaceElement> destinationPortList = destination.getDestinationPorts().get(portIndex);
					if (null == destinationPortList) {
						destinationPortList = new ArrayList<>();
						destination.getDestinationPorts().put(portIndex, destinationPortList);
					}
					destinationPortList.add(mappedElement.getInterfaceElement(connection.getDestination().getName()));
				}
			}
		}
	}


	private static int getPortIndex(Connection connection, Event sourceEvent) {
		int portIndex = -2;			
		if (connection instanceof EventConnection) {
			portIndex = -1;
		} else if (connection instanceof DataConnection) {
			portIndex = 0;
			for (With with : sourceEvent.getWith()) {
				if (with.getVariables().getName().equals(connection.getSource().getName())) {
					break;
				}
				portIndex++;
			}
		}
		return portIndex;
	}


	private static List<Event> getSourceEvents(Connection connection) {
		List<Event> sourceEvents =  new ArrayList<>();		
		FBNetworkElement mappedElement = connection.getSourceElement().getOpposite();
		if (connection instanceof EventConnection) {
			sourceEvents.add((Event) mappedElement.getInterfaceElement(connection.getSource().getName()));
		} else if (connection instanceof DataConnection) {
			for (With with : ((VarDeclaration)connection.getSource()).getWiths()) {
				if (with.eContainer() instanceof Event) {
					sourceEvents.add((Event) mappedElement.getInterfaceElement(((Event)with.eContainer()).getName()));
				}
			}
		}
		return sourceEvents;
	}


	private CommunicationChannel getComChannel(boolean local, Event sourceEvent, Connection connection) {
		CommunicationChannel channel = communicationModel.getChannels().get(sourceEvent);
		if (null == channel) {
			if(connection instanceof EventConnection) {
				//currently only add a channel if an event, data connection should attach to existing channels
				channel = new CommunicationChannel();
				channel.setSourceEvent(sourceEvent);
				channel.setLocal(local);
				communicationModel.getChannels().put(sourceEvent, channel);
			}
		} else if (!local) {
			//force non local if needed
			channel.setLocal(false);
		}
		return channel;
	}
	
	private void collectMediaInformation() {
		for (CommunicationChannel channel : communicationModel.getChannels().values()) {
			collectMediaInformation(channel);
		}
	}
	
	private static void collectMediaInformation(CommunicationChannel channel) {
		for (CommunicationChannelDestination destination : channel.getDestinations()) {
			collectMediaInformation(destination);
		}
	}
	
	private static void collectMediaInformation(CommunicationChannelDestination destination) {
		Device sourceDevice = (Device) destination.getCommunicationChannel().getSourceResource().eContainer();	
		Device destinationDevice = (Device) destination.getDestinationResource().eContainer();
		for (Link sourceLink : sourceDevice.getInConnections()) {
			for (Link destinationLink : destinationDevice.getInConnections()) {
				if (sourceLink.getSegment() == destinationLink.getSegment()) {
					destination.getAvailableMedia().add(new CommunicationMediaInfo(sourceLink, destinationLink, sourceLink.getSegment()));
				}
			}
		}	
	}
}
