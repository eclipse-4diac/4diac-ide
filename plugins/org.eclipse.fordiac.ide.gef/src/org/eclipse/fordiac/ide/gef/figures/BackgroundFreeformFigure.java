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

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

public class BackgroundFreeformFigure extends AbstractFreeformFigure {

	private static final double CANVAS_SCALING_FACTOR = 0.9;

	final ZoomScalableFreeformRootEditPart zoomScalableFreeformRootEditPart;

	public BackgroundFreeformFigure(final ZoomScalableFreeformRootEditPart zoomScalableFreeformRootEditPart) {
		this.zoomScalableFreeformRootEditPart = zoomScalableFreeformRootEditPart;
		setOpaque(true);
		final Display display = Display.getCurrent();
		if (null != display) {
			setBackgroundColor(display.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		}
	}

	@Override
	protected Rectangle calculateFreeformExtent() {
		final Rectangle newExtents = getContents().getFreeformExtent().getCopy();
		final Dimension bckArea = getBackgroundArea();
		newExtents.expand(bckArea.width, bckArea.height);
		return newExtents;
	}

	@Override
	protected void setChildBounds(final Rectangle childBounds) {
		final Dimension bckArea = getBackgroundArea();
		super.setChildBounds(childBounds.getShrinked(bckArea.width, bckArea.height));
	}

	Dimension getBackgroundArea() {
		final org.eclipse.swt.graphics.Rectangle canvasBounds = getCanvasBounds();
		return new Dimension((int) (canvasBounds.width * CANVAS_SCALING_FACTOR),
				(int) (canvasBounds.height * CANVAS_SCALING_FACTOR));
	}

	private org.eclipse.swt.graphics.Rectangle getCanvasBounds() {
		final FigureCanvas figureCanvas = (FigureCanvas) this.zoomScalableFreeformRootEditPart.getViewer().getControl();
		return figureCanvas.getBounds();
	}

}