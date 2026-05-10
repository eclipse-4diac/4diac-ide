/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - extracted the spline connection from ECTranstionFigure for reuse in highlightning
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Path;

public class SplineConnection extends PolylineConnection {

	@Override
	protected void outlineShape(final Graphics g) {
		final PointList points = getPoints();
		if (points.size() != 7) {
			super.outlineShape(g);
			return;
		}

		final Path p = getPath();
		g.drawPath(p);
		p.dispose();
	}

	@Override
	public Rectangle getBounds() {
		final PointList points = getPoints();
		if (points.size() != 7) {
			return super.getBounds();
		}

		final float[] pathBounds = new float[4];
		final Path p = getPath();
		p.getBounds(pathBounds);
		p.dispose();
		final Rectangle pathRect = new Rectangle((int) pathBounds[0], (int) pathBounds[1], (int) pathBounds[2],
				(int) pathBounds[3]);
		return super.getBounds().getUnion(pathRect);
	}

	private Path getPath() {
		final Path p = new Path(null);
		final PointList points = getPoints();
		p.moveTo(points.getPoint(0).x, points.getPoint(0).y);
		p.cubicTo(points.getPoint(1).x, points.getPoint(1).y, points.getPoint(2).x, points.getPoint(2).y,
				points.getPoint(3).x, points.getPoint(3).y);
		p.cubicTo(points.getPoint(4).x, points.getPoint(4).y, points.getPoint(5).x, points.getPoint(5).y,
				points.getPoint(6).x, points.getPoint(6).y);
		return p;
	}
}
