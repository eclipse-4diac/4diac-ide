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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.Timeline;

/**
 * @brief Represents a resource in the replay debugging UI model
 *
 *        It offers a name and the replay navigator, and manages the models for
 *        connections between timelines.
 */
public class Resource {
	private final ReplayNavigator navigator;
	private final Set<TimelineConnection> connections = new HashSet<>();

	public Resource(final ReplayNavigator navigator) {
		this.navigator = navigator;
		addChildConnections(navigator.getRootTimeline());
	}

	public String getName() {
		return navigator.getIdentifier().resourceName();
	}

	public ReplayNavigator getReplayNavigator() {
		return navigator;
	}

	private void addParentConnection(final Timeline timeline) {
		final var parentTimeline = timeline.getParentTimeline();
		if (parentTimeline == null) {
			return;
		}
		final int spawnedIndex = parentTimeline.getSpawnedTimelineEventNumber(timeline);
		connections.add(new TimelineConnection(parentTimeline, timeline, spawnedIndex, navigator));
	}

	private void addChildConnections(final Timeline timeline) {
		for (final var spawnedTimeline : timeline.getSpawnedTimelines()) {
			final int spawnedIndex = timeline.getSpawnedTimelineEventNumber(spawnedTimeline);
			connections.add(new TimelineConnection(timeline, spawnedTimeline, spawnedIndex, navigator));
			addTimeline(spawnedTimeline);
		}
	}

	public void addTimeline(final Timeline timeline) {
		addParentConnection(timeline);
		addChildConnections(timeline);
	}

	public List<TimelineConnection> getSources(final Timeline timeline) {
		return connections.stream().filter(connection -> connection.parent().equals(timeline)).toList();
	}

	public List<TimelineConnection> getTargets(final Timeline timeline) {
		return connections.stream().filter(connection -> connection.child().equals(timeline)).toList();
	}
}
