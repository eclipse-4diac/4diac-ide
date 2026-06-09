/*******************************************************************************
 * Copyright (c) 2011 Profactor GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.PrecisionDimension;
import org.eclipse.draw2d.geometry.PrecisionRectangle;

public class WithAnchor extends ChopboxAnchor {

	public static final PrecisionRectangle LOC_HELPER = new PrecisionRectangle();

	public static final double WITH_DISTANCE = 13.0;

	private final int pos;

	public WithAnchor(final IFigure figure, final int pos) {
		super(figure);
		this.pos = pos;
	}

	protected double getAbsoluteWithPos() {
		final PrecisionDimension posBuf = new PrecisionDimension(WITH_DISTANCE * pos, 0.0);
		getOwner().translateToAbsolute(posBuf);
		return posBuf.preciseWidth();
	}

	public int getPos() {
		return pos;
	}
}
