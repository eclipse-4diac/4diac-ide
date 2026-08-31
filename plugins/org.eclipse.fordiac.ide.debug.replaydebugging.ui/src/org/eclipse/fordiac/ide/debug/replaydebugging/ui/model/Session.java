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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.Timeline;

/**
 * @brief Represents the current session of the replay debugging, containing all
 *        the devices.
 *
 *        It offers a method to find the resource model corresponding to a
 *        timeline.
 */
public class Session {

	public static final String PROPERTY_SESSION_CHANGED = "sessionChanged"; //$NON-NLS-1$
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	private final Map<String, Device> devices = new ConcurrentHashMap<>();

	public List<Device> getDevices() {
		return new ArrayList<>(devices.values());
	}

	public Resource getResource(final Timeline timeline) {
		for (final var device : devices.values()) {
			for (final var resource : device.getResources()) {
				if (isInHierarchy(resource.getReplayNavigator().getRootTimeline(), timeline)) {
					return resource;
				}
			}
		}
		return null;
	}

	private static boolean isInHierarchy(final Timeline parentTimeline, final Timeline childTimeline) {
		if (parentTimeline == childTimeline) {
			return true;
		}

		for (final var spawnedTimeline : parentTimeline.getSpawnedTimelines()) {
			if (isInHierarchy(spawnedTimeline, childTimeline)) {
				return true;
			}
		}
		return false;
	}

	public void addReplayNavigator(final ReplayNavigator navigator) {
		final String deviceName = navigator.getIdentifier().deviceName();

		// notify only if a new device was added
		final boolean shouldNotify = !devices.containsKey(deviceName);

		final var device = devices.computeIfAbsent(deviceName, _ -> new Device(deviceName));
		device.addReplayNavigator(navigator);
		if (shouldNotify) {
			propertyChangeSupport.firePropertyChange(PROPERTY_SESSION_CHANGED, null, null);
		}
	}

	public void removeReplayNavigator(final ReplayNavigator navigator) {
		final String deviceName = navigator.getIdentifier().deviceName();
		final var device = devices.get(deviceName);
		if (device == null) {
			return;
		}
		device.removeReplayNavigator(navigator);
		if (device.getResources().isEmpty()) {
			devices.remove(deviceName);
			propertyChangeSupport.firePropertyChange(PROPERTY_SESSION_CHANGED, null, null);
		}
	}

	// Listener

	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(final PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

}
