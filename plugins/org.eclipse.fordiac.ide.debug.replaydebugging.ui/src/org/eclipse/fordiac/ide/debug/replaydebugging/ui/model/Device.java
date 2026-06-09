/*******************************************************************************
 * Copyright (c) 2026 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.replaydebugging.ui.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator;

/**
 * @brief Model class representing a device in the replay debugging UI.
 *
 *        It has a name and a list of resource models
 */
public class Device {

	public static final String PROPERTY_DEVICE_CHANGED = "deviceChanged"; //$NON-NLS-1$
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	private final String name;
	private final Map<String, Resource> resources = new HashMap<>();

	public Device(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<Resource> getResources() {
		return new ArrayList<>(resources.values());
	}

	public void addReplayNavigator(final ReplayNavigator navigator) {
		resources.put(navigator.getIdentifier().resourceName(), new Resource(navigator));
		propertyChangeSupport.firePropertyChange(PROPERTY_DEVICE_CHANGED, null, null);
	}

	public void removeReplayNavigator(final ReplayNavigator navigator) {
		resources.remove(navigator.getIdentifier().resourceName());
		propertyChangeSupport.firePropertyChange(PROPERTY_DEVICE_CHANGED, null, null);
	}

	// Listener

	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(final PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

}
