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
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;

public class CommunicationChannelDestination {

	private final CommunicationChannel communicationChannel;
	private Resource destinationResource;
	private final Set<Connection> connections = new HashSet<>();
	private final Map<Integer, List<IInterfaceElement>> destinationPorts = new HashMap<>();
	private final List<CommunicationMediaInfo> availableMedia = new ArrayList<>();
	private boolean separated;
	private String selectedProtocolId;
	private CommunicationMediaInfo selectedMedia;
	
	public CommunicationChannelDestination(CommunicationChannel communicationChannel) {
		super();
		this.communicationChannel = communicationChannel;
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

	public Map<Integer, List<IInterfaceElement>> getDestinationPorts() {
		return destinationPorts;
	}

	public CommunicationChannel getCommunicationChannel() {
		return communicationChannel;
	}

	public List<CommunicationMediaInfo> getAvailableMedia() {
		return availableMedia;
	}

	public Set<Connection> getConnection() {
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