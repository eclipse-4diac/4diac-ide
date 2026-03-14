/*******************************************************************************
 * Copyright (c) 2026 Johannes Kepler University Linz
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
package org.eclipse.fordiac.ide.fbtypeeditor.figures;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;

class PinPropColumnLayout extends AbstractPinPropColumnLayout {

	private final boolean inputSide;
	private final int padding;
	private final int maxWidth;

	public PinPropColumnLayout(final boolean inputSide, final int padding) {
		this(inputSide, padding, Integer.MAX_VALUE);
	}

	public PinPropColumnLayout(final boolean inputSide, final int padding, final int maxWidth) {
		this.inputSide = inputSide;
		this.padding = padding;
		this.maxWidth = maxWidth;
	}

	@Override
	public void layout(final IFigure container) {
		final Rectangle r = container.getClientArea();

		for (final IFigure child : container.getChildren()) {
			final Dimension childSize = child.getPreferredSize();
			final int childWidthToUse = Math.min(maxWidth, childSize.width);

			int x = r.x;
			if (inputSide) {
				// Right-align for inputs (padding on right side towards FB)
				x += r.width - childWidthToUse - padding;
			} else {
				x += padding;
			}

			child.setBounds(new Rectangle(x, getChildYPos(child), childWidthToUse, childSize.height));
		}

	}

	@Override
	protected Dimension calculatePreferredSize(final IFigure container, final int wHint, final int hHint) {
		int maxChildWidth = 0;
		int maxHeight = 0;

		for (final IFigure child : container.getChildren()) {
			final Dimension childSize = child.getPreferredSize();
			maxChildWidth = Math.clamp(childSize.width, maxChildWidth, maxWidth);

			// our column always starts at y 0 there fore we can simple take the y+ height
			// of the most bottom child as maxHeight
			maxHeight = Math.max(maxHeight, getChildYPos(child) + childSize.height);
		}

		maxChildWidth += padding;
		return new Dimension(maxChildWidth, maxHeight);
	}

}
