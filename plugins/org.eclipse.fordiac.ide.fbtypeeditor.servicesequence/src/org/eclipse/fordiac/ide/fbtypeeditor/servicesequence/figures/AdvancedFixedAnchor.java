/*******************************************************************************
 * Copyright (c) 2008, 2009 Profactor GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.figures;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.gef.FixedAnchor;

/**
 * The Class AdvancedFixedAnchor.
 */
public class AdvancedFixedAnchor extends FixedAnchor {

	private final int moveX;
	private final int moveY;

	/**
	 * Instantiates a new advanced fixed anchor.
	 * 
	 * @param owner the owner
	 * @param isInput the is input
	 * @param moveX the move x
	 * @param moveY the move y
	 */
	public AdvancedFixedAnchor(final IFigure owner, final boolean isInput,
			final int moveX, final int moveY) {
		super(owner, isInput);
		this.moveX = moveX;
		this.moveY = moveY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.ConnectionAnchor#getLocation(org.eclipse.draw2d.geometry.Point)
	 */
	@Override
	public Point getLocation(final Point reference) {
		Point location = new Point();
		if (isInput()) {
			Point p1 = getBox().getLeft();
			location.x = p1.x + moveX;
			location.y = p1.y + moveY;
		} else {
			Point p1 = getBox().getRight();
			location.x = p1.x + moveX;
			location.y = p1.y + moveY;
		}
		return location;
	}

}
