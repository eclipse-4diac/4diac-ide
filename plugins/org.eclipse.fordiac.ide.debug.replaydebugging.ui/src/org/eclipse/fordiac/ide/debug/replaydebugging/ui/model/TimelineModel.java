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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.debug.replaydebugging.core.EventChange;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator.EventPosition;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.Timeline;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.CommentsHandler;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.statescomparison.ComparisonColumn;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.statescomparison.ComparisonService;

public class TimelineModel implements Timeline.StructureListener, ComparisonService.Listener, CommentsHandler.Listener {

	private final Timeline timeline;
	private TimelineConnection connectionToParentTimelineModel;

	private final List<TimelineConnection> spawnedConnections = new ArrayList<>();
	private final List<EventMarker> eventMarkers = new ArrayList<>();
	private final BiConsumer<Timeline, Integer> eventSelected;

	private int firstInvalid = -1;

	public static final String PROPERTY_EVENT_ADDED = "eventAdded"; //$NON-NLS-1$
	public static final String PROPERTY_TIMELINE_ADDED = "timelineAdded"; //$NON-NLS-1$
	public static final String PROPERTY_EVENT_DELETED = "eventDeleted"; //$NON-NLS-1$
	public static final String PROPERTY_TIMELINE_DELETED = "timelineDeleted"; //$NON-NLS-1$
	public static final String PROPERTY_STATE_CHANGED = "stateChanged"; //$NON-NLS-1$

	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	public TimelineModel(final Timeline timeline, final BiConsumer<Timeline, Integer> eventSelected) {
		this.timeline = timeline;
		this.eventSelected = eventSelected;

		for (var i = 0; i <= timeline.getMaxEventNumber(); i++) {
			eventMarkers.add(new EventMarker(i, this, this::eventSelected));
		}

		for (final var spawnedTimeline : timeline.getSpawnedTimelines()) {
			addNewSpawnedTimeline(spawnedTimeline);
		}
		updateReadOnlyMarkers();

		timeline.addStructureListener(this);
		ComparisonService.getInstance().addListener(this);
		CommentsHandler.getInstance().addListener(this);
	}

	public List<EventMarker> getEventMarkers() {
		return List.copyOf(eventMarkers);
	}

	public int getFirstInvalid() {
		return firstInvalid;
	}

	public int getGlobalStartPosition() {
		return Timeline.getSpawnedTimelineGlobalEventNumber(timeline);
	}

	public Timeline getTimeline() {
		return timeline;
	}

	public List<TimelineModel> getSpawnedTimelineModels() {
		final List<TimelineModel> timelines = spawnedConnections.stream().map(TimelineConnection::child)
				.collect(Collectors.toList());

		Collections.sort(timelines, getTimelineComparator(timeline, TimelineModel::getTimeline));
		return timelines;
	}

	public static <T> Comparator<T> getTimelineComparator(final Timeline parentTimeline,
			final Function<T, Timeline> timelineExtractor) {
		return (timeline1, timeline2) -> Integer.compare(
				parentTimeline.getSpawnedTimelineEventNumber(timelineExtractor.apply(timeline2)),
				parentTimeline.getSpawnedTimelineEventNumber(timelineExtractor.apply(timeline1)));
	}

	private void addNewSpawnedTimeline(final Timeline spawnedTimeline) {
		final var spawnedModel = new TimelineModel(spawnedTimeline, eventSelected);
		final var connectionToChild = new TimelineConnection(this, spawnedModel,
				timeline.getSpawnedTimelineEventNumber(spawnedTimeline));
		spawnedModel.connectionToParentTimelineModel = connectionToChild;
		spawnedConnections.add(connectionToChild);
	}

	public void updateCurrentPosition(final EventPosition currentEventPosition, final List<Timeline> presentTimelines) {
		cleanAllEventStates();

		firstInvalid = -1;
		if (currentEventPosition.timeline() == timeline) {
			firstInvalid = currentEventPosition.eventNumber() + 1;
			eventMarkers.get(currentEventPosition.eventNumber()).setIsCurrentEvent(true);
		} else {
			for (var i = 0; i < presentTimelines.size(); i++) {
				if (presentTimelines.get(i) == timeline) {
					firstInvalid = timeline.getSpawnedTimelineEventNumber(presentTimelines.get(i + 1)) + 1;
				}
			}
		}

		for (var i = 0; i < eventMarkers.size(); i++) {
			eventMarkers.get(i).setIsValid(i < firstInvalid);
		}

		for (final var spawnedConnection : spawnedConnections) {
			spawnedConnection.setIsInCurrentPosition(presentTimelines.contains(spawnedConnection.child().timeline));
			spawnedConnection.child().updateCurrentPosition(currentEventPosition, presentTimelines);
		}
		propertyChangeSupport.firePropertyChange(PROPERTY_STATE_CHANGED, null, null);
	}

	private void cleanAllEventStates() {
		for (final var eventMarker : eventMarkers) {
			eventMarker.setIsValid(false);
			eventMarker.setIsCurrentEvent(false);
		}
	}

	public void dispose() {
		timeline.removeStructureListener(this);
		ComparisonService.getInstance().removeListener(this);
		CommentsHandler.getInstance().removeListener(this);
	}

	private void eventSelected(final Integer index) {
		eventSelected.accept(timeline, index);
	}

	public List<TimelineConnection> getSources() {
		return spawnedConnections;
	}

	public List<TimelineConnection> getTargets() {
		return connectionToParentTimelineModel == null ? List.of() : List.of(connectionToParentTimelineModel);
	}

	// Callbacks from the timeline

	@Override
	public void eventAdded(final Timeline timeline) {
		eventMarkers.add(new EventMarker(timeline.getMaxEventNumber(), this, this::eventSelected));
		propertyChangeSupport.firePropertyChange(PROPERTY_EVENT_ADDED, null, null);
	}

	@Override
	public void timelineSpawned(final Timeline spawnedTimeline) {
		addNewSpawnedTimeline(spawnedTimeline);
		propertyChangeSupport.firePropertyChange(PROPERTY_TIMELINE_ADDED, null, null);
	}

	@Override
	public void eventsRemoved(final Timeline timeline, final int removedStartEventIndex,
			final List<EventChange> removedChanges) {
		while (eventMarkers.size() > removedStartEventIndex) {
			eventMarkers.removeLast();
		}
		propertyChangeSupport.firePropertyChange(PROPERTY_EVENT_DELETED, null, null);
	}

	@Override
	public void timelineRemoved(final Timeline parentTimeline, final Timeline removedTimeline,
			final int spawnedAtEventNumber) {
		spawnedConnections.removeIf(timelineConnection -> timelineConnection.child().timeline == removedTimeline);
		propertyChangeSupport.firePropertyChange(PROPERTY_TIMELINE_DELETED, null, null);
	}

	private void updateReadOnlyMarkers() {
		final var firstDeletableEventIndex = timeline.getFirstDeletableEventIndex();
		for (var i = 0; i < eventMarkers.size(); i++) {
			eventMarkers.get(i).setIsReadOnly(i < firstDeletableEventIndex);
		}
	}

	@Override
	public void timelineStateChanged(final Timeline timeline) {
		updateReadOnlyMarkers();
	}

	@Override
	public void columnsChanged(final List<ComparisonColumn> columns) {
		for (final var eventMarker : eventMarkers) {
			eventMarker.setComparisonColor(null);
		}
		for (final var column : columns) {
			if (column.getEventPosition().timeline() == timeline) {
				eventMarkers.get(column.getEventPosition().eventNumber()).setComparisonColor(column.getColor());
			}
		}
	}

	@Override
	public void eventCommentChanged(final EventPosition position, final String comment) {
		if (position.timeline() != timeline) {
			return;
		}
		eventMarkers.get(position.eventNumber()).setComment(comment);
		// update column header if it's present in the comparison table
		for (final var column : ComparisonService.getInstance().getColumns()) {
			if (column.getEventPosition().eventNumber() == position.eventNumber()) {
				column.setLabel(comment);
				ComparisonService.getInstance().replaceColumn(column);
				break;
			}
		}
	}

	// Listener to this

	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(final PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

}
