/*******************************************************************************
 * Copyright (c) 2014, 2015, 2017 Profactor GbmH, fortiss GmbH
 * 				 2018 - 2020 Johannes Kepler University Linz
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
 *
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.figures;

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.util.ColorHelper;
import org.eclipse.swt.graphics.Color;

public class HideableConnection extends PolylineConnection {

	public static final int BEND_POINT_BEVEL_SIZE = 5;
	private static final int NORMAL_DOUBLE_LINE_WIDTH = 3;
	private static final int DOUBLE_LINE_AMPLIFICATION = 2;

	private boolean hidden = false;
	private String label = ""; //$NON-NLS-1$
	private Rectangle moveRect = new Rectangle();
	private org.eclipse.fordiac.ide.model.libraryElement.Connection model;
	private Color lighterColor;

	public void setModel(org.eclipse.fordiac.ide.model.libraryElement.Connection newModel) {
		model = newModel;
	}

	public org.eclipse.fordiac.ide.model.libraryElement.Connection getModel() {
		return model;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	@Override
	public void setForegroundColor(Color fg) {
		super.setForegroundColor(fg);
		if (isAdapterConnectionOrStructConnection()) {
			lighterColor = ColorHelper.lighter(fg);
		}
	}

	@Override
	protected void outlineShape(Graphics g) {
		if (isHidden()) {

			int[] startLine = new int[] { getStart().x, getStart().y, getStart().x + 20, getStart().y };
			int[] endLine = new int[] { getEnd().x, getEnd().y, getEnd().x - 20, getEnd().y };
			g.drawPolyline(startLine);
			g.drawPolyline(endLine);

			Dimension dim = FigureUtilities.getTextExtents(label, g.getFont());
			g.drawText(label, new Point(getEnd().x - dim.width - 25, getEnd().y - (dim.height / 2)));
			moveRect.x = getEnd().x - dim.width - 25;
			moveRect.y = getEnd().y - (dim.height / 2);
			moveRect.width = 5;
			moveRect.height = 5;
		} else {
			if (isAdapterConnectionOrStructConnection()) {
				drawDoublePolyline(g, getBeveledPoints());
			} else {
				g.drawPolyline(getBeveledPoints());
			}
		}
	}

	private boolean isAdapterConnectionOrStructConnection() {
		if (model instanceof DataConnection) {
			// if the connections end point fb type could not be loaded it source or
			// destination may be null
			IInterfaceElement refElement = model.getSource();
			if (null == refElement) {
				refElement = model.getDestination();
			}
			return ((null != refElement) && (refElement.getType() instanceof StructuredType));
		}
		return (model instanceof AdapterConnection);
	}

	private void drawDoublePolyline(Graphics g, PointList beveledPoints) {
		g.drawPolyline(beveledPoints);
		g.setLineWidth(getLineWidth() / NORMAL_DOUBLE_LINE_WIDTH);
		if (g.getAbsoluteScale() >= 1) {
			g.setForegroundColor(lighterColor);
			g.drawPolyline(beveledPoints);
		}
	}


	@Override
	public void setLineWidth(int w) {
		int width = w;
		if (isAdapterConnectionOrStructConnection()) {
			width = Math.max(w * DOUBLE_LINE_AMPLIFICATION, NORMAL_DOUBLE_LINE_WIDTH);
		}
		super.setLineWidth(width);
	}

	private PointList getBeveledPoints() {
		PointList beveledPoints = new PointList();

		beveledPoints.addPoint(getPoints().getFirstPoint());

		for (int i = 1; i < (getPoints().size() - 1); i++) {
			Point before = getPoints().getPoint(i - 1);
			Point after = getPoints().getPoint(i + 1);

			int verDistance = Math.abs(before.y - after.y);
			int horDistance = Math.abs(before.y - after.y);
			int bevelSize = BEND_POINT_BEVEL_SIZE;
			if (verDistance < (2 * BEND_POINT_BEVEL_SIZE)) {
				bevelSize = verDistance / 2;
			}
			if (horDistance < (2 * BEND_POINT_BEVEL_SIZE)) {
				bevelSize = horDistance / 2;
			}

			beveledPoints.addPoint(calculatedBeveledPoint(getPoints().getPoint(i), before, bevelSize));
			beveledPoints.addPoint(calculatedBeveledPoint(getPoints().getPoint(i), after, bevelSize));
		}

		beveledPoints.addPoint(getPoints().getLastPoint());
		return beveledPoints;
	}

	private static Point calculatedBeveledPoint(Point refPoint, Point otherPoint, int bevelSize) {
		if (0 == (refPoint.x - otherPoint.x)) {
			return new Point(refPoint.x, refPoint.y + (((refPoint.y - otherPoint.y) > 0) ? -bevelSize : bevelSize));
		}
		return new Point(refPoint.x + (((refPoint.x - otherPoint.x) > 0) ? -bevelSize : bevelSize), refPoint.y);
	}

}
