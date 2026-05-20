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

/**
 * @brief Model class for a connection between two timelines
 *
 *        This class represents the connection between a parent and a child
 *        timeline. It listens to the replay navigator state changes and updates
 *        its state accordingly. It also provides a property change support for
 *        notifying listeners about changes in the connection state.
 */
public class TimelineConnection {

	public static final String PROPERTY_TIMELINECONNECTION_CHANGED = "timelineConnectionChanged"; //$NON-NLS-1$
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	private final TimelineModel parent;
	private final TimelineModel child;
	private final int spawnedIndex;
	private boolean isInCurrentPosition = false;

	public TimelineConnection(final TimelineModel parent, final TimelineModel child, final int spawnedIndex) {
		this.parent = parent;
		this.child = child;
		this.spawnedIndex = spawnedIndex;
	}

	public TimelineModel parent() {
		return parent;
	}

	public TimelineModel child() {
		return child;
	}

	public int spawnedIndex() {
		return spawnedIndex;
	}

	public boolean isInCurrentPosition() {
		return isInCurrentPosition;
	}

	public void setIsInCurrentPosition(final boolean isInCurrentPosition) {
		this.isInCurrentPosition = isInCurrentPosition;
		propertyChangeSupport.firePropertyChange(PROPERTY_TIMELINECONNECTION_CHANGED, null, null);
	}

	// Listener to this

	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(final PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}
}
