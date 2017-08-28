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

import java.util.HashMap;

import org.eclipse.fordiac.ide.model.libraryElement.Event;

public class CommunicationModel {
	private HashMap<Event, CommunicationChannel> channels;

	public CommunicationModel() {
		super();
		channels = new HashMap<Event, CommunicationChannel>();
	}

	public HashMap<Event, CommunicationChannel> getChannels() {
		return channels;
	}
}
