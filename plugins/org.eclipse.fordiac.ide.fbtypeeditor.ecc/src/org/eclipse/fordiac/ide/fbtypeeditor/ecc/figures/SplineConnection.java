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
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.geometry.Vector;
import org.eclipse.swt.graphics.Path;

public class SplineConnection extends PolylineConnection {

	private static final double TRANSITION_CONTROL_POINT_VECTOR_LENGTH = 0.3;

	@Override
	protected void outlineShape(Graphics g) {
		Path p = getPath();
		g.drawPath(p);
		p.dispose();
	}

	@Override
	public Rectangle getBounds() {
		float[] pathBounds = new float[4];
		Path p = getPath();
		p.getBounds(pathBounds);
		p.dispose();
		Rectangle pathRect = new Rectangle((int) pathBounds[0], (int) pathBounds[1], (int) pathBounds[2],
				(int) pathBounds[3]);
		return super.getBounds().getUnion(pathRect);
	}

	private Path getPath() {
		Path p = new Path(null);
		if (3 == getPoints().size()) {
			p.moveTo(getStart().x, getStart().y);
			Vector startEnd = new Vector((float) getEnd().x - getStart().x, (float) getEnd().y - getStart().y);
			Vector startMid = new Vector((float) getPoints().getMidpoint().x - getStart().x,
					(float) getPoints().getMidpoint().y - getStart().y);
			Vector midEnd = new Vector((float) getEnd().x - getPoints().getMidpoint().x,
					(float) getEnd().y - getPoints().getMidpoint().y);

			Vector startEndNorm = startEnd.getDivided((startEnd.getLength() < 0.0001) ? 1.0 : startEnd.getLength());
			Vector startEnd30 = startEndNorm
					.getMultiplied(startMid.getLength() * TRANSITION_CONTROL_POINT_VECTOR_LENGTH);
			Vector startMid30 = startMid.getMultiplied(TRANSITION_CONTROL_POINT_VECTOR_LENGTH);
			Vector midEnd30 = midEnd.getMultiplied(TRANSITION_CONTROL_POINT_VECTOR_LENGTH);

			p.cubicTo(getStart().x + (float) startMid30.x, getStart().y + (float) startMid30.y,
					getPoints().getMidpoint().x - (float) startEnd30.x,
					getPoints().getMidpoint().y - (float) startEnd30.y, getPoints().getMidpoint().x,
					getPoints().getMidpoint().y);

			p.cubicTo(getPoints().getMidpoint().x + (float) startEnd30.x,
					getPoints().getMidpoint().y + (float) startEnd30.y, getEnd().x - (float) midEnd30.x,
					getEnd().y - (float) midEnd30.y, getEnd().x, getEnd().y);
		}
		return p;
	}

}
