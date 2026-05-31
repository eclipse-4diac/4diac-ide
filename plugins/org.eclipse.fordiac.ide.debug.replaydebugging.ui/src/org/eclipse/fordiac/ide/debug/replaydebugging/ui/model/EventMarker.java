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
import java.util.function.Consumer;

import org.eclipse.swt.graphics.Color;

public class EventMarker {
	private final int index;
	private final TimelineModel parentTimeline;
	private final Consumer<Integer> eventSelected;
	private boolean isCurrentEvent = false;
	private boolean isValid = false;
	private boolean isReadOnly = false;
	private Color comparisonColor = null;

	public static final String PROPERTY_EVENT_CHANGED = "eventChanged"; //$NON-NLS-1$

	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	public EventMarker(final int index, final TimelineModel parentTimeline, final Consumer<Integer> eventSelected) {
		this.index = index;
		this.parentTimeline = parentTimeline;
		this.eventSelected = eventSelected;
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
		propertyChangeSupport.firePropertyChange(PROPERTY_EVENT_CHANGED, null, null);
	}

	public boolean getValid() {
		return isValid;
	}

	public void setIsValid(final boolean isValid) {
		this.isValid = isValid;
		propertyChangeSupport.firePropertyChange(PROPERTY_EVENT_CHANGED, null, null);
	}

	public void eventSelected() {
		eventSelected.accept(Integer.valueOf(index));
	}

	// Listener to this

	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(final PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

}
