/*******************************************************************************
 * Copyright (c) 2014 - 2015 Luka Lednicki, fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
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
