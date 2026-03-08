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

import org.eclipse.fordiac.ide.debug.replaydebugging.core.DatapointsState;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.Timeline;

/**
 * @brief Model class for a connection between two timelines
 *
 *        This class represents the connection between a parent and a child
 *        timeline. It listens to the replay navigator state changes and updates
 *        its state accordingly. It also provides a property change support for
 *        notifying listeners about changes in the connection state.
 */
public class TimelineConnection implements ReplayNavigator.StateListener {

	public static final String PROPERTY_TIMELINECONNECTION_CHANGED = "timlineConnectionChanged"; //$NON-NLS-1$
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	private final Timeline parent;
	private final Timeline child;
	private final int spawnedIndex;
	private final ReplayNavigator replayNavigator;

	public TimelineConnection(final Timeline parent, final Timeline child, final int spawnedIndex,
			final ReplayNavigator replayNavigator) {
		this.parent = parent;
		this.child = child;
		this.spawnedIndex = spawnedIndex;
		this.replayNavigator = replayNavigator;
		replayNavigator.addStateChangeListener(this);
	}

	public Timeline parent() {
		return parent;
	}

	public Timeline child() {
		return child;
	}

	public int spawnedIndex() {
		return spawnedIndex;
	}

	public boolean isInCurrentPosition() {
		var currentTimeline = replayNavigator.getCurrentEventPosition().timeline();
		while (currentTimeline != null) {
			if (currentTimeline == child) {
				return true;
			}
			currentTimeline = currentTimeline.getParentTimeline();
		}
		return false;
	}

	@Override
	public void stateUpdated(final ReplayNavigator replayNavigator, final DatapointsState changedValues) {
		propertyChangeSupport.firePropertyChange(PROPERTY_TIMELINECONNECTION_CHANGED, null, null);
	}

	// Listener

	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(final PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}
}
