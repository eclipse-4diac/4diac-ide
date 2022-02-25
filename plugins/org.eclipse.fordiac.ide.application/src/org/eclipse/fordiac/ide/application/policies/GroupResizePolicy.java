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

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.editparts.GroupContentEditPart;
import org.eclipse.fordiac.ide.gef.policies.ModifiedResizeablePolicy;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.tools.ResizeTracker;

public class GroupResizePolicy extends ModifiedResizeablePolicy {

	private Rectangle minGroupBounds;

	@Override
	protected List createSelectionHandles() {
		updateGroupMinBounds();
		return super.createSelectionHandles();
	}

	@Override
	protected ResizeTracker getResizeTracker(final int direction) {
		return new ResizeTracker(getHost(), direction) {
			@Override
			protected Dimension getMinimumSizeFor(final ChangeBoundsRequest request) {
				final Rectangle bounds = getHost().getFigure().getBounds();
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
		};
	}

	protected int getMinHeight(final Rectangle bounds, final int resizeDirection) {
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

	protected int getMinWidth(final Rectangle bounds, final int resizeDirection) {
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

	@Override
	public GraphicalEditPart getHost() {
		return (GraphicalEditPart) super.getHost();
	}

	private void updateGroupMinBounds() {
		final Rectangle groupBounds = getHost().getFigure().getBounds();
		final GraphicalEditPart groupContent = getGroupContent(getHost());
		if (groupContent != null) { // this should never be the case but just for safety
			final Rectangle groupContentFigureBounds = groupContent.getFigure().getBounds();
			minGroupBounds = getGroupContentBounds(groupContent);

			final int wInc = (groupBounds.width - groupContentFigureBounds.width) / 2;
			minGroupBounds.expand(wInc, 0);
			// we can not use expand for the height increment as we are not symmetrically applying it
			minGroupBounds.height += groupBounds.height - groupContentFigureBounds.height;
			minGroupBounds.y -= (groupContentFigureBounds.y - groupBounds.y);
		} else {
			minGroupBounds = getDefaultGroupContentBounds();
		}
	}

	public static GraphicalEditPart getGroupContent(final EditPart groupEP) {
		return (GraphicalEditPart) groupEP.getChildren().stream()
				.filter(GroupContentEditPart.class::isInstance).findAny().orElse(null);
	}

	public static Rectangle getGroupContentBounds(final EditPart groupContent) {
		Rectangle selectionExtend = null;
		for (final Object child : groupContent.getChildren()) {
			if (child instanceof GraphicalEditPart) {
				final Rectangle fbBounds = getBoundsForEditPart((GraphicalEditPart) child);
				if (selectionExtend == null) {
					selectionExtend = fbBounds.getCopy();
				} else {
					selectionExtend.union(fbBounds);
				}
			}
		}
		return (selectionExtend != null) ? selectionExtend : getDefaultGroupContentBounds();
	}

	private static Rectangle getBoundsForEditPart(final GraphicalEditPart ep) {
		return ep.getFigure().getBounds().getCopy();
	}

	private static Rectangle getDefaultGroupContentBounds() {
		return new Rectangle(new Point(0, 0), IFigure.MIN_DIMENSION);
	}
}