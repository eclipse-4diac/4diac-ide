/*******************************************************************************
 * Copyright (c) 2011 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 *                    Johannes Kepler University Linz
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.PrecisionRectangle;

public class OutputWithAnchor extends WithAnchor {

	public OutputWithAnchor(final IFigure figure, final int pos) {
		super(figure, pos);
	}

	@Override
	public Point getLocation(final Point reference) {
		final PrecisionRectangle r = LOC_HELPER;
		r.setBounds(getBox());
		r.translate(-1, -1);
		r.resize(1, 1);
		getOwner().translateToAbsolute(r);
		final double leftX = r.preciseX() + r.preciseWidth() + getAbsoluteWithPos();
		final double centerY = r.preciseY() + r.preciseHeight() / 2;
		return new PrecisionPoint(leftX, centerY);
	}
}
