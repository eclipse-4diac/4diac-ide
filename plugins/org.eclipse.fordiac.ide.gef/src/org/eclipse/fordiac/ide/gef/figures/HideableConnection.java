/*******************************************************************************
 * Copyright (c) 2014, 2021 Profactor GbmH, fortiss GmbH,
 *               2023       Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Muddasir Shakil - Added double line for Adapter and Struct connection
 *   Prankur Agarwal - Added handling for truncating label according to the
 *   				   max size preference option value
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Geometry;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.model.data.AnyType;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.util.ColorHelper;
import org.eclipse.swt.graphics.Color;

public class HideableConnection extends PolylineConnection {

	private static final int CONNECTION_SELECTION_TOLERANCE = 6;
	public static final int BEND_POINT_BEVEL_SIZE = 5;
	public static final int NORMAL_DOUBLE_LINE_WIDTH = 3;
	private static final int DOUBLE_LINE_AMPLIFICATION = 2;

	private boolean hidden = false;
	private Connection model;
	private Color lighterColor;

	public void setModel(final Connection newModel) {
		model = newModel;
	}

	public Connection getModel() {
		return model;
	}

	public boolean isHidden() {
		return hidden;
	}

	public Color getLighterColor() {
		return lighterColor;
	}

	public void setHidden(final boolean hidden) {
		final boolean oldHidden = this.hidden;
		this.hidden = hidden;
		if (oldHidden != hidden) {
			handleVisibilityChange(hidden);
			invalidate();
			repaint();
		}
	}

	protected void handleVisibilityChange(final boolean hidden) {
		// nothing to do here
	}

	@Override
	public void setForegroundColor(final Color fg) {
		super.setForegroundColor(fg);
		if (isAdapterOrStructConnection()) {
			lighterColor = ColorHelper.lighter(fg);
		}
	}

	@Override
	protected void outlineShape(final Graphics g) {
		if (isAdapterOrStructConnection()) {
			drawDoublePolyline(g);
		} else {
			g.drawPolyline(getPoints());
		}
	}

	public boolean isAdapterOrStructConnection() {
		if (model instanceof DataConnection) {
			final IInterfaceElement refElement = getRefPin();
			return ((null != refElement) && (refElement.getType() instanceof StructuredType));
		}
		return (model instanceof AdapterConnection);
	}

	private IInterfaceElement getRefPin() {
		// if the connections end point fb type could not be loaded it source or
		// destination may be null
		IInterfaceElement refElement = model.getSource();
		if (null == refElement) {
			refElement = model.getDestination();
		}
		if (refElement != null) { // during reconnect or delete both sides could be null see Bug 579040 for
									// details
			final DataType dataType = refElement.getType();
			if ((dataType instanceof AnyType) && (dataType == IecTypes.GenericTypes.ANY)
					&& (refElement == model.getSource())) {
				// if source is of any type change to destination so that source any target
				// struct are shown correctly
				refElement = getModel().getDestination();
			}
		}
		return refElement;
	}

	private void drawDoublePolyline(final Graphics g) {
		g.drawPolyline(getPoints());
		g.setLineWidth(getLineWidth() / NORMAL_DOUBLE_LINE_WIDTH);
		if (g.getAbsoluteScale() >= 1) {
			g.setForegroundColor(lighterColor);
			g.drawPolyline(getPoints());
		}
	}

	@Override
	public Rectangle getBounds() {
		final int CLIPPING_BUFFER = 2;
		return super.getBounds().getExpanded(CLIPPING_BUFFER, CLIPPING_BUFFER);
	}

	@Override
	public void setLineWidth(final int w) {
		int width = w;
		if (isAdapterOrStructConnection()) {
			width = Math.max(w * DOUBLE_LINE_AMPLIFICATION, NORMAL_DOUBLE_LINE_WIDTH);
		}
		super.setLineWidth(width);
	}

	@Override
	protected boolean shapeContainsPoint(final int x, final int y) {
		return Geometry.polylineContainsPoint(getPoints(), x, y, getLineWidth() + CONNECTION_SELECTION_TOLERANCE);
	}

	@Override
	public void setPoints(final PointList points) {
		super.setPoints(getBeveledPoints(points));
	}

	private static PointList getBeveledPoints(final PointList srcPoints) {
		final PointList beveledPoints = new PointList();

		beveledPoints.addPoint(srcPoints.getFirstPoint());

		for (int i = 1; i < (srcPoints.size() - 1); i++) {
			final Point before = srcPoints.getPoint(i - 1);
			final Point after = srcPoints.getPoint(i + 1);

			final int verDistance = Math.abs(before.y - after.y);
			final int horDistance = Math.abs(before.y - after.y);
			int bevelSize = BEND_POINT_BEVEL_SIZE;
			if (verDistance < (2 * BEND_POINT_BEVEL_SIZE)) {
				bevelSize = verDistance / 2;
			}
			if (horDistance < (2 * BEND_POINT_BEVEL_SIZE)) {
				bevelSize = horDistance / 2;
			}

			beveledPoints.addPoint(calculatedBeveledPoint(srcPoints.getPoint(i), before, bevelSize));
			beveledPoints.addPoint(calculatedBeveledPoint(srcPoints.getPoint(i), after, bevelSize));
		}

		beveledPoints.addPoint(srcPoints.getLastPoint());
		return beveledPoints;
	}

	private static Point calculatedBeveledPoint(final Point refPoint, final Point otherPoint, final int bevelSize) {
		if (0 == (refPoint.x - otherPoint.x)) {
			return new Point(refPoint.x, refPoint.y + (((refPoint.y - otherPoint.y) > 0) ? -bevelSize : bevelSize));
		}
		return new Point(refPoint.x + (((refPoint.x - otherPoint.x) > 0) ? -bevelSize : bevelSize), refPoint.y);
	}

}
