/*******************************************************************************
 * Copyright (c) 2008, 2009, 2013, 2015 Profactor GbmH, fortiss GmbH
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
package org.eclipse.fordiac.ide.gef;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * The Class FixedAnchor.
 *
 * @author gebenh
 */
public class FixedAnchor extends AbstractConnectionAnchor {

	/** The is input. */
	private boolean isInput = false;

	/**
	 * Instantiates a new fixed anchor.
	 *
	 * @param owner   the owner
	 * @param isInput the is input
	 */
	public FixedAnchor(final IFigure owner, final boolean isInput) {
		super(owner);
		this.isInput = isInput;
	}

	@Override
	public Point getLocation(final Point reference) {
		final Rectangle bounds = getOwner().getBounds().getCopy();
		getOwner().translateToAbsolute(bounds);
		return (isInput) ? bounds.getLeft() : bounds.getRight();
	}

	@Override
	public Point getReferencePoint() {
		return getLocation(null);
	}


	public boolean isInput() {
		return isInput;
	}

}
