/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.tools.ResizeTracker;

class GroupResizeTracker extends ResizeTracker {
	private Rectangle minGroupBounds;

	public GroupResizeTracker(final GraphicalEditPart owner, final int direction) {
		super(owner, direction);
	}

	@Override
	protected Dimension getMinimumSizeFor(final ChangeBoundsRequest request) {
		final Rectangle bounds = getOwner().getFigure().getBounds();
		// also check the top left corner as content is not moved during resize
		final int minWidth = getMinWidth(bounds, getResizeDirection());
		final int minHeight = getMinHeight(bounds, getResizeDirection());
		return new Dimension(minWidth, minHeight);
	}

	@Override
	protected boolean handleDragStarted() {
		updateGroupMinBounds();
		return super.handleDragStarted();
	}

	@Override
	protected void enforceConstraintsForResize(final ChangeBoundsRequest changeBoundsRequest) {
		super.enforceConstraintsForResize(changeBoundsRequest);
		if (getOwner() != null && isTopOrLeft()) {
			// ensure that on minimumsize the bottom right corner is not moving, this is has potential to be upstreamed
			// to GEF
			final PrecisionRectangle originalConstraint = new PrecisionRectangle(getOwner().getFigure().getBounds());
			getOwner().getFigure().translateToAbsolute(originalConstraint);
			final PrecisionRectangle manipulatedConstraint = new PrecisionRectangle(
					changeBoundsRequest.getTransformedRectangle(originalConstraint));
			final Point origBR = originalConstraint.getBottomRight();
			final Point newBR = manipulatedConstraint.getBottomRight();
			if (origBR.x < newBR.x || origBR.y < newBR.y) {
				final Point moveDelta = changeBoundsRequest.getMoveDelta();
				moveDelta.x -= (newBR.x - origBR.x);
				moveDelta.y -= (newBR.y - origBR.y);
				changeBoundsRequest.setMoveDelta(moveDelta);
			}
		}
	}

	private boolean isTopOrLeft() {
		return getResizeDirection() == PositionConstants.NORTH
				|| getResizeDirection() == PositionConstants.NORTH_WEST
				|| getResizeDirection() == PositionConstants.WEST;
	}

	private void updateGroupMinBounds() {
		final Rectangle groupBounds = getOwner().getFigure().getBounds();
		final GraphicalEditPart groupContent = GroupResizePolicy.getGroupContent(getOwner());
		if (groupContent != null) { // this should never be the case but just for safety
			final Rectangle groupContentFigureBounds = groupContent.getFigure().getBounds();
			minGroupBounds = GroupResizePolicy.getGroupContentBounds(groupContent);

			final int wInc = (groupBounds.width - groupContentFigureBounds.width) / 2;
			minGroupBounds.expand(wInc, 0);
			// we can not use expand for the height increment as we are not symmetrically applying it
			minGroupBounds.height += groupBounds.height - groupContentFigureBounds.height;
			minGroupBounds.y -= (groupContentFigureBounds.y - groupBounds.y);
		} else {
			minGroupBounds = GroupResizePolicy.getDefaultGroupContentBounds();
		}
	}

	private int getMinHeight(final Rectangle bounds, final int resizeDirection) {
		int height = minGroupBounds.height;
		switch (resizeDirection) {
		case PositionConstants.NORTH:
		case PositionConstants.NORTH_WEST:
		case PositionConstants.NORTH_EAST:
			// ensure that the bottom border is not lost
			height += bounds.height - (minGroupBounds.y - bounds.y) - minGroupBounds.height;
			break;
		case PositionConstants.SOUTH:
		case PositionConstants.SOUTH_EAST:
		case PositionConstants.SOUTH_WEST:
			// ensure that the top border is not lost
			height += (minGroupBounds.y - bounds.y);
			break;
		default:
			// we don't care about WEST and EAST as they are not define the height
			break;
		}

		return height;
	}

	private int getMinWidth(final Rectangle bounds, final int resizeDirection) {
		int width = minGroupBounds.width;
		switch (resizeDirection) {
		case PositionConstants.WEST:
		case PositionConstants.NORTH_WEST:
		case PositionConstants.SOUTH_WEST:
			// ensure that the right border is not lost
			width += bounds.width - (minGroupBounds.x - bounds.x) - minGroupBounds.width;
			break;
		case PositionConstants.EAST:
		case PositionConstants.NORTH_EAST:
		case PositionConstants.SOUTH_EAST:
			// ensure that the top border is not lost
			width += (minGroupBounds.x - bounds.x);
			break;
		default:
			// we don't care about NORTH and SOUTH as they are not define the height
			break;
		}
		return width;
	}
}