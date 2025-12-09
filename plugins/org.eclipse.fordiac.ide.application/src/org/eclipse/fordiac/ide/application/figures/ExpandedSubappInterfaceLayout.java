/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.figures;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.OrderedLayout;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.utilities.ExpandedInterfacePositionMap;

public class ExpandedSubappInterfaceLayout extends ToolbarLayout {

	final ExpandedInterfacePositionMap positions;
	final boolean isInput;

	public ExpandedSubappInterfaceLayout(final ExpandedInterfacePositionMap positions, final boolean isInput) {
		super(false);
		this.positions = positions;
		this.isInput = isInput;
		setStretchMinorAxis(true);
		setSpacing(2);
		setMinorAlignment(OrderedLayout.ALIGN_BOTTOMRIGHT);
	}

	@Override
	public void layout(final IFigure parent) {
		if (positions.inputPositions == null || positions.outputPositions == null) {
			// initial layout
			positions.calculate();
		}

		final List<? extends IFigure> children = parent.getChildren();
		final int numChildren = children.size();
		final Rectangle clientArea = transposer.t(parent.getClientArea());

		// always horizontal
		final int wHint = parent.getClientArea(Rectangle.SINGLETON).width;
		final int hHint = -1;

		final int x = clientArea.x;
		int y = isInput ? positions.getInputUnconnectedStart() : positions.getOutputUnconnectedStart();
		y = (y == Integer.MAX_VALUE) ? clientArea.y : y;

		for (int i = 0; i < numChildren; i++) {
			final IFigure child = children.get(i);
			final int prefHeight = transposer.t(getChildPreferredSize(child, wHint, hHint)).height;

			final var entry = isInput ? positions.inputPositions.get(child) : positions.outputPositions.get(child);
			final Rectangle newBounds;

			if (entry == null) {
				// direct connections
				newBounds = new Rectangle(x, getDirectPosition(child), 0, prefHeight);
			} else if (entry.intValue() == Integer.MAX_VALUE) {
				// unconnected pins
				newBounds = new Rectangle(x, y, 0, prefHeight);
				y += newBounds.height;
			} else {
				// normal connections
				newBounds = new Rectangle(x, entry.intValue(), 0, prefHeight);
			}

			// stretch to fit
			newBounds.width = clientArea.width;
			// bottom right alignment
			newBounds.x += clientArea.width - newBounds.width;

			child.setBounds(transposer.t(newBounds));
		}
	}

	private int getDirectPosition(final IFigure child) {
		return positions.directPositions.getOrDefault(child, Integer.valueOf(0)).intValue();
	}

}
