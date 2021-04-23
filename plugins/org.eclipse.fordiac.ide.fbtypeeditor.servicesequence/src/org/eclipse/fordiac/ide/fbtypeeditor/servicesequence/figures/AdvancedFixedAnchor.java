/*******************************************************************************
 * Copyright (c) 2008, 2009 Profactor GmbH
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
	 * @param owner   the owner
	 * @param isInput the is input
	 * @param moveX   the move x
	 * @param moveY   the move y
	 */
	public AdvancedFixedAnchor(final IFigure owner, final boolean isInput, final int moveX, final int moveY) {
		super(owner, isInput);
		this.moveX = moveX;
		this.moveY = moveY;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.draw2d.ConnectionAnchor#getLocation(org.eclipse.draw2d.geometry.
	 * Point)
	 */
	@Override
	public Point getLocation(final Point reference) {
		final Point location = super.getLocation(reference);
		location.x += moveX;
		location.y += moveY;
		return location;
	}

}
