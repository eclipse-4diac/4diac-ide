/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University
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
package org.eclipse.fordiac.ide.gef.editparts;

import org.eclipse.draw2d.FreeformFigure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RangeModel;
import org.eclipse.draw2d.ScalableFigure;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.editparts.ZoomManager;

public class AdvancedZoomManager extends ZoomManager {

	private Point lastMousePos;

	AdvancedZoomManager(final ScalableFigure pane, final Viewport viewport) {
		super(pane, viewport);
	}

	public void setLastMousePos(final int x, final int y) {
		this.lastMousePos = new Point(x, y);
	}

	@Override
	public void setZoomAsText(final String zoomString) {
		super.setZoomAsText(zoomString);

		if (FIT_HEIGHT.equalsIgnoreCase(FIT_HEIGHT) || FIT_ALL.equalsIgnoreCase(zoomString)
				|| FIT_WIDTH.equalsIgnoreCase(zoomString)) {
			// scroll to the center so that our drawing canvas is fully shown
			final Point scrollPoint = new Point(calculateCenterScrollPos(getViewport().getHorizontalRangeModel()),
					calculateCenterScrollPos(getViewport().getVerticalRangeModel()));
			setViewLocation(scrollPoint);
		}

	}

	private static int calculateCenterScrollPos(final RangeModel rangeModel) {
		final int center = (rangeModel.getMaximum() + rangeModel.getMinimum()) / 2;
		return center - rangeModel.getExtent() / 2;
	}

	@Override
	protected void primSetZoom(final double zoom) {
		final Point newViewLocation = (null == lastMousePos) ? takeMiddPosition(zoom) : calcNewViewLocation(zoom);
		super.primSetZoom(zoom);
		setViewLocation(newViewLocation);
	}

	@Override
	protected double getFitHeightZoomLevel() {
		return getFitXZoomLevelBounds(1);
	}

	@Override
	protected double getFitPageZoomLevel() {
		return getFitXZoomLevelBounds(2);
	}

	@Override
	protected double getFitWidthZoomLevel() {
		return getFitXZoomLevelBounds(0);
	}

	/** This method is copied from {@link ZoomManager} and adjusted such that it uses the bounds of the figure to
	 * correctly take the modulo figure into account.
	 *
	 * See {@link ZoomManager#getFitXZoomLevel(final int which)} for the original implementation. */
	private double getFitXZoomLevelBounds(final int which) {
		IFigure fig = getScalableFigure();

		final Dimension available = getViewport().getClientArea().getSize();
		Dimension desired;
		if (fig instanceof FreeformFigure) {
			desired = ((FreeformFigure) fig).getBounds().getSize();
		} else {
			desired = fig.getPreferredSize().getCopy();
		}

		desired.width -= fig.getInsets().getWidth();
		desired.height -= fig.getInsets().getHeight();

		while (fig != getViewport()) {
			available.width -= fig.getInsets().getWidth();
			available.height -= fig.getInsets().getHeight();
			fig = fig.getParent();
		}

		final double scaleX = Math.min(available.width * getZoom() / desired.width, getMaxZoom());
		final double scaleY = Math.min(available.height * getZoom() / desired.height, getMaxZoom());
		if (which == 0) {
			return scaleX;
		}
		if (which == 1) {
			return scaleY;
		}
		return Math.min(scaleX, scaleY);
	}


	/*
	 * In order to keep the target under the mouse stable we have to calculate the
	 * new view location such that the following equation holds:
	 *
	 * (mousepos + oldViewLocation) / oldZoom = (mousepos + newViewLocation)/newZoom
	 *
	 */
	private Point calcNewViewLocation(final double newZoom) {
		final Point oldViewLocation = getViewport().getViewLocation();
		final Point newviewLocation = lastMousePos.getCopy();
		newviewLocation.performTranslate(oldViewLocation.x, oldViewLocation.y);
		newviewLocation.scale(1.0 / getZoom());
		newviewLocation.scale(newZoom);
		newviewLocation.performTranslate(-lastMousePos.x, -lastMousePos.y);
		return newviewLocation;
	}

	private Point takeMiddPosition(final double newZoom) {
		final Point oldViewLocation = getViewport().getClientArea().getCenter();
		final Dimension dif = oldViewLocation.getScaled(newZoom / getZoom()).getDifference(oldViewLocation);
		return getViewport().getViewLocation().getTranslated(dif.width, dif.height);
	}

}