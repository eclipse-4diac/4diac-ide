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
import java.util.HashMap;
import java.util.Map;

import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.EventType;

public final class EventTypeLibrary {
	public static final String EVENT = "Event"; //$NON-NLS-1$
	private Map<String, DataType> typeMap;
	private static EventTypeLibrary instance;

	private EventTypeLibrary() {
		typeMap = new HashMap<>();
		initElementaryTypes();
	}

	public static EventTypeLibrary getInstance() {
		if (instance == null) {
			instance = new EventTypeLibrary();
		}
		return instance;
	}

	private void initElementaryTypes() {
		if (typeMap == null) {
			typeMap = new HashMap<>();
		}
		final EventType type = DataFactory.eINSTANCE.createEventType();
		type.setName(EVENT);
		typeMap.put(EVENT, type);
	}

	public Collection<DataType> getEventTypes() {
		return typeMap.values();
	}

	/** FIXME only return type if it really exists! --> after parsing/importing of types is implemented --> planned for
	 * V0.3
	 *
	 * @param name the name
	 *
	 * @return the type */
	public DataType getType(final String name) {
		if (name == null) {
			return typeMap.get(EVENT);
		}
		final Object value = typeMap.get(name.toUpperCase());
		if (value != null) {
			return (DataType) value;
		}
		final EventType type = DataFactory.eINSTANCE.createEventType();
		type.setName(name);
		typeMap.put(name, type);
		return type;
	}

}
