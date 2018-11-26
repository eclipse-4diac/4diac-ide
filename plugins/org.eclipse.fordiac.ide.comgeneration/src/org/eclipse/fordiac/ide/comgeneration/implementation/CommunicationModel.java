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
import java.util.Map;

import org.eclipse.fordiac.ide.model.libraryElement.Event;

public class CommunicationModel {
	private final Map<Event, CommunicationChannel> channels = new HashMap<>();

	public CommunicationModel() {
		super();
	}

	public Map<Event, CommunicationChannel> getChannels() {
		return channels;
	}
}
