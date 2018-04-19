/*******************************************************************************
 * Copyright (c) 2014 - 2017 Luka Lednicki, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Luka Lednicki, Gerd Kainz, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.comgeneration.implementation;

import java.util.ArrayList;

import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.Link;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
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
		InterfaceList sourceInterface = (InterfaceList) connection.getSource().eContainer();
		FB sourceFB = (FB) sourceInterface.eContainer();
		Resource sourceResource = ((sourceFB.getResource() != null) ? sourceFB.getResource() : null);
		ArrayList<Event> sourceEvents = new ArrayList<Event>();		
		FBNetworkElement mappedElement = sourceFB.getMapping().getTo();
		if (connection instanceof EventConnection) {
			EventConnection eventConnection = (EventConnection) connection;
			sourceEvents.add((Event) mappedElement.getInterfaceElement(eventConnection.getSource().getName()));
		} else if (connection instanceof DataConnection) {
			DataConnection dataConnection = (DataConnection) connection;
			for (With with : dataConnection.getDataSource().getWiths()) {
				if (with.eContainer() instanceof Event) {
					sourceEvents.add((Event) mappedElement.getInterfaceElement(((Event)with.eContainer()).getName()));
				}
			}
		}
		
		InterfaceList destinationInterface = (InterfaceList) connection.getDestination().eContainer();
		FB destinationFB = (FB) destinationInterface.eContainer();
		Resource destinationResource = ((destinationFB.getResource() != null) ? destinationFB.getResource() : null);	
		boolean shouldCreate = sourceResource != null && destinationResource != null && sourceResource != destinationResource;	
		mappedElement = destinationFB.getMapping().getTo();
		if (shouldCreate) {
			boolean local = sourceResource.getDevice() == destinationResource.getDevice();
			for (Event sourceEvent : sourceEvents) {
				CommunicationChannel channel = communicationModel.getChannels().get(sourceEvent);
				if (channel == null) {
					channel = new CommunicationChannel();
					//channel.setSourceInterface(sourceInterface);
					channel.setSourceResource(sourceResource);
					channel.setSourceEvent(sourceEvent);
					channel.setNumberOfDataPorts(sourceEvent.getWith().size());
					channel.setLocal(local);
					communicationModel.getChannels().put(sourceEvent, channel);
				}
				if (!local) {
					channel.setLocal(false);
				}
				CommunicationChannelDestination destination = channel.getDestination(destinationResource);
				destination.getConnection().add(connection);
				int portIndex = -2;			
				if (connection instanceof EventConnection) {
					portIndex = -1;
				} else if (connection instanceof DataConnection) {
					DataConnection dataConnection = (DataConnection) connection;
					portIndex = 0;
					for (With with : sourceEvent.getWith()) {
						if (with.getVariables() == dataConnection.getSource()) {
							break;
						}
						portIndex++;
					}
				}
				ArrayList<IInterfaceElement> destinationPortList = destination.getDestinationPorts().get(portIndex);
				if (destinationPortList == null) {
					destinationPortList = new ArrayList<IInterfaceElement>();
					destination.getDestinationPorts().put(portIndex, destinationPortList);
				}
				destinationPortList.add(mappedElement.getInterfaceElement(connection.getDestination().getName()));
			}
		}
	}
	
	private void collectMediaInformation() {
		for (CommunicationChannel channel : communicationModel.getChannels().values()) {
			collectMediaInformation(channel);
		}
	}
	
	private void collectMediaInformation(CommunicationChannel channel) {
		for (CommunicationChannelDestination destination : channel.getDestinations()) {
			collectMediaInformation(destination);
		}
	}
	
	private void collectMediaInformation(CommunicationChannelDestination destination) {
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
