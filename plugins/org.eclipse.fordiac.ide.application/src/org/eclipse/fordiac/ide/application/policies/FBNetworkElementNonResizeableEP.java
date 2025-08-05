/*******************************************************************************
 * Copyright (c) 2022, 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Sebastian Hollersbacher - Added Margin to Drag Figure
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import org.eclipse.draw2d.GhostImageFigure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.gef.policies.ModifiedMoveHandle;
import org.eclipse.fordiac.ide.gef.policies.ModifiedNonResizeableEditPolicy;
import org.eclipse.fordiac.ide.gef.utilities.CollisionChangeBoundsRequest;
import org.eclipse.fordiac.ide.gef.utilities.MarginBoundsHelper;
import org.eclipse.gef.requests.ChangeBoundsRequest;

public class FBNetworkElementNonResizeableEP extends ModifiedNonResizeableEditPolicy {

	private final MarginBoundsHelper boundsHelper = new MarginBoundsHelper();
	private GhostImageFigure ghostFigure;

	@Override
	protected RoundedRectangle createSelectionFeedbackFigure() {
		final RoundedRectangle figure = super.createSelectionFeedbackFigure();
		figure.setFill(false);
		figure.setOutline(true);
		figure.setLineWidth(2 * ModifiedMoveHandle.SELECTION_BORDER_WIDTH);
		return figure;
	}

	@Override
	protected void showChangeBoundsFeedback(final ChangeBoundsRequest request) {
		super.showChangeBoundsFeedback(request);

		final PrecisionRectangle rect = new PrecisionRectangle(super.getInitialFeedbackBounds().getCopy());
		getHostFigure().translateToAbsolute(rect);
		rect.translate(request.getMoveDelta());
		rect.resize(request.getSizeDelta());
		ghostFigure.translateToRelative(rect);
		ghostFigure.setBounds(rect);
		ghostFigure.validate();

		if (request instanceof final CollisionChangeBoundsRequest collisionBoundsRequest) {
			final IFigure dragFigure = getDragSourceFeedbackFigure();
			if (dragFigure.getBorder() instanceof final ModifiedMoveHandle.SelectionBorder border) {
				final boolean collision = collisionBoundsRequest.checkCollision(dragFigure.getBounds());
				border.setColor(
						collision ? ModifiedMoveHandle.getCollisionColor() : ModifiedMoveHandle.getSelectionColor());
			}
			dragFigure.validate();
		}
	}

	@Override
	protected IFigure createDragSourceFeedbackFigure() {
		boundsHelper.updateMargins(getHost().getModel());
		ghostFigure = new GhostImageFigure(getHostFigure(), 2 * ModifiedMoveHandle.SELECTION_FILL_ALPHA, null);
		addFeedback(ghostFigure);
		return super.createDragSourceFeedbackFigure();
	}

	@Override
	protected Rectangle getInitialFeedbackBounds() {
		final Rectangle bounds = super.getInitialFeedbackBounds().getCopy();
		boundsHelper.expandRectangle(bounds);
		return bounds;
	}

	@Override
	public void deactivate() {
		super.deactivate();
		removeGhostFigure();
	}

	@Override
	public void eraseChangeBoundsFeedback(final ChangeBoundsRequest request) {
		super.eraseChangeBoundsFeedback(request);
		removeGhostFigure();
	}

	private void removeGhostFigure() {
		if (ghostFigure != null) {
			removeFeedback(ghostFigure);
			ghostFigure = null;
		}
	}
}