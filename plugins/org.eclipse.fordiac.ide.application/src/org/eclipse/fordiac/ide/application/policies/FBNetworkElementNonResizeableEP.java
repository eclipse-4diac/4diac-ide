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

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.gef.policies.ModifiedMoveHandle;
import org.eclipse.fordiac.ide.gef.policies.ModifiedNonResizeableEditPolicy;
import org.eclipse.fordiac.ide.gef.utilities.MarginBoundsHelper;

public class FBNetworkElementNonResizeableEP extends ModifiedNonResizeableEditPolicy {

	private final MarginBoundsHelper boundsHelper = new MarginBoundsHelper();

	@Override
	protected RoundedRectangle createSelectionFeedbackFigure() {
		final RoundedRectangle figure = super.createSelectionFeedbackFigure();
		figure.setFill(false);
		figure.setOutline(true);
		figure.setLineWidth(2 * ModifiedMoveHandle.SELECTION_BORDER_WIDTH);
		return figure;
	}

	@Override
	protected IFigure createDragSourceFeedbackFigure() {
		boundsHelper.updateMargins(getHost().getModel());
		return super.createDragSourceFeedbackFigure();
	}

	@Override
	protected Rectangle getInitialFeedbackBounds() {
		final Rectangle bounds = super.getInitialFeedbackBounds().getCopy();
		boundsHelper.expandRectangle(bounds);
		return bounds;
	}
}