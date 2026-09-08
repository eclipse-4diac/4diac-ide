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

import org.eclipse.swt.graphics.Color;

public class EventMarker {
	private final int index;
	private final TimelineModel parentTimeline;
	private boolean isCurrentEvent = false;
	private boolean isValid = false;
	private boolean isReadOnly = false;
	private boolean isHightlighted = false;
	private Color comparisonColor = null;
	private String comment = null;

	public static final String PROPERTY_EVENT_CHANGED = "eventChanged"; //$NON-NLS-1$

	public static final String PROPERTY_IS_CURRENT_CHANGED = "isCurrentChanged"; //$NON-NLS-1$

	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	public EventMarker(final int index, final TimelineModel parentTimeline) {
		this.index = index;
		this.parentTimeline = parentTimeline;
	}

	public int getIndex() {
		return index;
	}

	public Color getComparisonColor() {
		return comparisonColor;
	}

	public void setComparisonColor(final Color color) {
		this.comparisonColor = color;
		propertyChangeSupport.firePropertyChange(PROPERTY_EVENT_CHANGED, null, null);
	}

	public void setIsReadOnly(final boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
		propertyChangeSupport.firePropertyChange(PROPERTY_EVENT_CHANGED, null, null);
	}

	public boolean getIsReadOnly() {
		return isReadOnly;
	}

	public boolean getIsCurrentEvent() {
		return isCurrentEvent;
	}

	public TimelineModel getParentTimeline() {
		return parentTimeline;
	}

	public void setIsCurrentEvent(final boolean isCurrentEvent) {
		this.isCurrentEvent = isCurrentEvent;
		propertyChangeSupport.firePropertyChange(PROPERTY_IS_CURRENT_CHANGED, null, null);
	}

	public boolean getValid() {
		return isValid;
	}

	public void setIsValid(final boolean isValid) {
		this.isValid = isValid;
		propertyChangeSupport.firePropertyChange(PROPERTY_EVENT_CHANGED, null, null);
	}

	public void setComment(final String comment) {
		this.comment = comment;
		propertyChangeSupport.firePropertyChange(PROPERTY_EVENT_CHANGED, null, null);
	}

	public String getComment() {
		return comment;
	}

	public boolean getIsHighlighted() {
		return isHightlighted;
	}

	public void setIsHighlighted(final boolean isHightlighted) {
		this.isHightlighted = isHightlighted;
		propertyChangeSupport.firePropertyChange(PROPERTY_EVENT_CHANGED, null, null);
	}

	// Listener to this

	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(final PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

}
