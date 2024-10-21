/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.tools;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.ViewportAutoexposeHelper;

public class FordiacViewportAutoexposeHelper extends ViewportAutoexposeHelper {

	private static final Insets DEFAULT_EXPOSE_THRESHOLD = new Insets(100);

	private long lastStepTime = 0;

	private final Insets threshold;

	public FordiacViewportAutoexposeHelper(final GraphicalEditPart owner) {
		this(owner, DEFAULT_EXPOSE_THRESHOLD);
	}

	public FordiacViewportAutoexposeHelper(final GraphicalEditPart owner, final Insets threshold) {
		super(owner, threshold);
		this.threshold = threshold;
	}

	@Override
	public boolean step(final Point where) {
		final Viewport port = findViewport(owner);

		final Rectangle rect = Rectangle.SINGLETON;
		port.getClientArea(rect);
		port.translateToParent(rect);
		port.translateToAbsolute(rect);
		if (!rect.contains(where)) {
			return false;
		}

		rect.shrink(threshold);

		if (rect.contains(where)) {
			return false;
		}

		if (lastStepTime == 0) {
			lastStepTime = System.currentTimeMillis();
		}

		final long difference = System.currentTimeMillis() - lastStepTime;

		if (difference < 0) {
			return true;
		}

		lastStepTime = System.currentTimeMillis();

		final int region = rect.getPosition(where);
		final Point loc = port.getViewLocation();

		if ((region & PositionConstants.SOUTH) != 0) {
			loc.y += (int) difference * (where.y() - rect.bottom()) / threshold.bottom;
		} else if ((region & PositionConstants.NORTH) != 0) {
			loc.y += (int) difference * (where.y() - rect.top()) / threshold.top;
		}

		if ((region & PositionConstants.EAST) != 0) {
			loc.x += (int) difference * (where.x() - rect.right()) / threshold.right;
		} else if ((region & PositionConstants.WEST) != 0) {
			loc.x += (int) difference * (where.x() - rect.left()) / threshold.left;
		}

		port.setViewLocation(loc);
		return true;
	}
}
