/********************************************************************************
 * Copyright (c) 2008, 2009, 2017 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
	private Map<String, DataType> typeMap;
	private static EventTypeLibrary instance;

	private EventTypeLibrary() {
		typeMap = new HashMap<String, DataType>();
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
			typeMap = new HashMap<String, DataType>();
		}
		EventType type = DataFactory.eINSTANCE.createEventType();
		type.setName("Event"); //$NON-NLS-1$
		typeMap.put("Event", type); //$NON-NLS-1$
	}

	public Collection<DataType> getEventTypes() {
		return typeMap.values();
	}

	/**
	 * FIXME only return type if it really exists! --> after parsing/importing
	 * of types is implemented --> planned for V0.3
	 * 
	 * @param name the name
	 * 
	 * @return the type
	 */
	public DataType getType(final String name) {
		if (name == null) {
			return typeMap.get("Event"); //$NON-NLS-1$
		}
		Object value = typeMap.get(name.toUpperCase());
		if (value != null) {
			return (DataType) value;
		}
		EventType type = DataFactory.eINSTANCE.createEventType();
		type.setName(name);
		typeMap.put(name, type);
		return type;
	}

}
