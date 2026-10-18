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

import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator.EventPosition;

/** The comment of one event, as shown by its icon above the timeline. */
public class EventCommentMarker {

	public static final String PROPERTY_COMMENT = "comment"; //$NON-NLS-1$

	private final TimelineModel parentTimeline;
	private final int eventIndex;
	private String comment;
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	public EventCommentMarker(final TimelineModel parentTimeline, final int eventIndex, final String comment) {
		this.parentTimeline = parentTimeline;
		this.eventIndex = eventIndex;
		this.comment = comment;
	}

	public TimelineModel getParentTimeline() {
		return parentTimeline;
	}

	public int getEventIndex() {
		return eventIndex;
	}

	public EventPosition getEventPosition() {
		return new EventPosition(parentTimeline.getTimeline(), eventIndex);
	}

	public String getComment() {
		return comment;
	}

	public void setComment(final String comment) {
		final String oldComment = this.comment;
		this.comment = comment;
		propertyChangeSupport.firePropertyChange(PROPERTY_COMMENT, oldComment, comment);
	}

	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(final PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}
}
