/*******************************************************************************
 * Copyright (c) 2014 - 2015 Luka Lednicki, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Luka Lednicki, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.comgeneration.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;

public class CommunicationChannelDestination {

	private final CommunicationChannel CommunicationChannel;
	private Resource destinationResource;
	private HashSet<Connection> connections;
	private HashMap<Integer, ArrayList<IInterfaceElement>> destinationPorts;
	private ArrayList<CommunicationMediaInfo> availableMedia;
	private boolean separated;
	private String selectedProtocolId;
	private CommunicationMediaInfo selectedMedia;
	
	public CommunicationChannelDestination(CommunicationChannel communicationChannel) {
		super();
		CommunicationChannel = communicationChannel;
		destinationPorts = new HashMap<Integer, ArrayList<IInterfaceElement>>();
		connections = new HashSet<Connection>();
		availableMedia = new ArrayList<CommunicationMediaInfo>();
		separated = false;
		selectedProtocolId = null;
		selectedMedia = null;
	}

	public Resource getDestinationResource() {
		return destinationResource;
	}

	public void setDestinationResource(Resource destinationResource) {
		this.destinationResource = destinationResource;
	}

	public HashMap<Integer, ArrayList<IInterfaceElement>> getDestinationPorts() {
		return destinationPorts;
	}

	public CommunicationChannel getCommunicationChannel() {
		return CommunicationChannel;
	}

	public ArrayList<CommunicationMediaInfo> getAvailableMedia() {
		return availableMedia;
	}

	public HashSet<Connection> getConnection() {
		return connections;
	}

	public boolean isSeparated() {
		return separated;
	}

	public void setSeparated(boolean separated) {
		this.separated = separated;
	}

	public String getSelectedProtocolId() {
		return selectedProtocolId;
	}

	public void setSelectedProtocolId(String protocolId) {
		this.selectedProtocolId = protocolId;
	}
	
	
	
	public CommunicationMediaInfo getSelectedMedia() {
		return selectedMedia;
	}

	public void setSelectedMedia(CommunicationMediaInfo selectedMedia) {
		this.selectedMedia = selectedMedia;
	}

	public void setSelectedMedia(CommunicationMediaInfo mediaInfo, String protocolId) {
		setSelectedMedia(mediaInfo);
		setSelectedProtocolId(protocolId);
	}
	
	
	
	
	
}