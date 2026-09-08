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
package org.eclipse.fordiac.ide.debug.replaydebugging.ui.figure;

import java.util.function.IntSupplier;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * @brief Anchor for the connections of the timeline events
 *
 *        This anchor calculates its position based on the index of the event.
 *        It calculates the position taking into account the total width of the
 *        figure where it's contained to determine the size of a marker. It can
 *        be used for both source and target anchors, adjusting its position
 *        accordingly.
 */
public class TimelineAnchor extends AbstractConnectionAnchor {

	private final int eventIndex;
	private final boolean isSource;
	private final IntSupplier numberOfEvents;

	public TimelineAnchor(final IFigure owner, final int eventIndex, final boolean isSource,
			final IntSupplier numberOfEvents) {
		super(owner);
		this.eventIndex = eventIndex;
		this.numberOfEvents = numberOfEvents;
		this.isSource = isSource;
	}

	@Override
	public Point getLocation(final Point reference) {

		final Rectangle bounds = getOwner().getBounds().getCopy();

		final var currentNumberOfEvents = numberOfEvents.getAsInt();
		if (currentNumberOfEvents < 1) {
			final Point p = new Point(bounds.x, bounds.y);
			getOwner().translateToAbsolute(p);
			return p;
		}

		final int availableWidth = bounds.width;
		final int markerSpacing = availableWidth / currentNumberOfEvents;

		final int x = bounds.x + (eventIndex * markerSpacing) + (isSource ? markerSpacing / 2 : 0);
		final int y = bounds.y + (isSource ? bounds.height : bounds.height / 2);

		final Point result = new Point(x, y);
		getOwner().translateToAbsolute(result);
		return result;
	}
}