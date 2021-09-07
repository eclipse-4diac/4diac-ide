/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.gef.figures;

import org.eclipse.draw2d.FreeformFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.gef.LayerConstants;

public class ModuloFreeformFigure extends AbstractFreeformFigure {
	private final ZoomScalableFreeformRootEditPart zoomScalableFreeformRootEditPart;
	private final boolean useFeedbackLayer;

	private static final int PADDING = 0;
	private static final int BASE_WIDTH = 400;
	private static final int BASE_HEIGHT = 200;

	public ModuloFreeformFigure(final ZoomScalableFreeformRootEditPart zoomScalableFreeformRootEditPart) {
		this(zoomScalableFreeformRootEditPart, true);
	}

	public ModuloFreeformFigure(final ZoomScalableFreeformRootEditPart zoomScalableFreeformRootEditPart,
			final boolean useFeedbackLayer) {
		this.zoomScalableFreeformRootEditPart = zoomScalableFreeformRootEditPart;
		this.useFeedbackLayer = useFeedbackLayer;
	}

	@Override
	protected Rectangle calculateFreeformExtent() {
		// adjust size to be a multiple of the base width/height
		Rectangle contentsExtent = getUnscaledContentsExtent();
		contentsExtent.shrink(getInsets());  // take any border into our calculation
		final int x = calcAxisOrigin(contentsExtent.x, BASE_WIDTH);
		final int y = calcAxisOrigin(contentsExtent.y, BASE_HEIGHT);
		final int width = calcAxisExtent(contentsExtent.x, x, contentsExtent.width, BASE_WIDTH);
		final int height = calcAxisExtent(contentsExtent.y, y, contentsExtent.height, BASE_HEIGHT);
		contentsExtent = new Rectangle(x, y, width, height);
		contentsExtent.scale(this.zoomScalableFreeformRootEditPart.getZoomManager().getZoom());
		return contentsExtent;
	}

	protected Rectangle getUnscaledContentsExtent() {
		final Rectangle contentsExtent = ((FreeformFigure) this.zoomScalableFreeformRootEditPart.getContentPane())
				.getFreeformExtent().getCopy();
		if (useFeedbackLayer) {
			// add handle and feedback layer so that dragging elements result in growing the modulo extend
			contentsExtent.union(((FreeformFigure) this.zoomScalableFreeformRootEditPart
					.getLayer(LayerConstants.HANDLE_LAYER)).getFreeformExtent());
			contentsExtent.union(((FreeformFigure) this.zoomScalableFreeformRootEditPart
					.getLayer(LayerConstants.FEEDBACK_LAYER)).getFreeformExtent());
		}
		return contentsExtent;
	}

	private static int calcAxisExtent(final int baseOrigin, final int newOrigin, final int sourceExtent,
			final int baseUnit) {
		final int startExtent = sourceExtent + PADDING + baseOrigin - newOrigin;

		int newExtend = (startExtent / baseUnit + 1) * baseUnit;
		if (newExtend < (3 * baseUnit)) {
			newExtend = 3 * baseUnit;
		}
		return newExtend;
	}

	private static int calcAxisOrigin(final int axisPos, final int baseUnit) {
		if (axisPos < 0) {
			// when negative we need to go one beyond to have the correct origin
			return (axisPos / baseUnit - 1) * baseUnit;
		}
		return (axisPos / baseUnit) * baseUnit;
	}

}