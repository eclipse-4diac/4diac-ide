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

import org.eclipse.draw2d.ScalableFigure;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.editparts.ZoomManager;

public class AdvancedZoomManager extends ZoomManager {

	Point lastMousePos;

	AdvancedZoomManager(ScalableFigure pane, Viewport viewport) {
		super(pane, viewport);
	}

	public void setLastMousePos(int x, int y) {
		this.lastMousePos = new Point(x, y);
	}

	@Override
	protected void primSetZoom(double zoom) {
		Point newViewLocation = (null == lastMousePos) ? takeMiddPosition(zoom) : calcNewViewLocation(zoom);

		super.primSetZoom(zoom);

		setViewLocation(newViewLocation);
	}

	/*
	 * In order to keep the target under the mouse stable we have to calculate the
	 * new view location such that the following equation holds:
	 *
	 * (mousepos + oldViewLocation) / oldZoom = (mousepos + newViewLocation)/newZoom
	 *
	 */
	private Point calcNewViewLocation(double newZoom) {
		Point oldViewLocation = getViewport().getViewLocation();
		Point newviewLocation = lastMousePos.getCopy();
		newviewLocation.performTranslate(oldViewLocation.x, oldViewLocation.y);
		newviewLocation.scale(1.0 / getZoom());
		newviewLocation.scale(newZoom);
		newviewLocation.performTranslate(-lastMousePos.x, -lastMousePos.y);
		return newviewLocation;
	}

	private Point takeMiddPosition(double newZoom) {
		Point oldViewLocation = getViewport().getClientArea().getCenter();
		Dimension dif = oldViewLocation.getScaled(newZoom / getZoom()).getDifference(oldViewLocation);
		return getViewport().getViewLocation().getTranslated(dif.width, dif.height);
	}

}