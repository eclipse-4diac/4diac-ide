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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;


class CommunicationChannel {
	
	private Event sourceEvent;	
	private final Map<Resource, CommunicationChannelDestination> destinations = new HashMap<>();	
	private boolean local;
		

	public CommunicationChannel() {
		local = true;
	}
	
	
	public CommunicationChannelDestination getDestination(Resource resource) {
		if (destinations.containsKey(resource)) {
			return destinations.get(resource);
		}
		CommunicationChannelDestination newDestination = new CommunicationChannelDestination(this);
		newDestination.setDestinationResource(resource);
		destinations.put(resource, newDestination);
		return newDestination;
	}
	
	public Collection<CommunicationChannelDestination> getDestinations() {
		return destinations.values();
	}

	public Resource getSourceResource() {
		return sourceEvent.getFBNetworkElement().getResource();
	}

	public Event getSourceEvent() {
		return sourceEvent;
	}


	public void setSourceEvent(Event sourceEvent) {
		this.sourceEvent = sourceEvent;
	}


	public boolean isLocal() {
		return local;
	}


	public void setLocal(boolean local) {
		this.local = local;
	}


	public int getNumberOfDataPorts() {
		return sourceEvent.getWith().size();
	}

	
}