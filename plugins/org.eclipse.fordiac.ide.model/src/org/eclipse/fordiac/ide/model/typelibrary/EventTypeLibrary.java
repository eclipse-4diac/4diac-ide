/********************************************************************************
 * Copyright (c) 2008, 2009, 2017 Profactor GmbH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gerhard Ebenhofer, Monika Wenger
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.EventType;

public final class EventTypeLibrary {
	public static final String EVENT = "Event"; //$NON-NLS-1$
	public static final String EINIT = "EInit"; //$NON-NLS-1$

	private static final EventTypeLibrary INSTANCE = new EventTypeLibrary();

	private final Map<String, EventType> typeMap = new ConcurrentHashMap<>();

	private EventTypeLibrary() {
		initElementaryTypes();
	}

	public static EventTypeLibrary getInstance() {
		return INSTANCE;
	}

	private void initElementaryTypes() {
		getType(EVENT);
		getType(EINIT);
	}

	public Collection<EventType> getEventTypes() {
		return Collections.unmodifiableCollection(typeMap.values());
	}

	public EventType getType(final String name) {
		if (name == null) {
			return getType(EVENT);
		}
		return typeMap.computeIfAbsent(name, this::createEventType);
	}

	private EventType createEventType(final String name) {
		final EventType type = DataFactory.eINSTANCE.createEventType();
		type.setName(name);
		encloseInResource(type);
		return type;
	}

	private static void encloseInResource(final EventType type) {
		new ResourceImpl(URI.createFileURI(type.getName() + ".eventlib.dtp")).getContents().add(type); //$NON-NLS-1$
	}
}
