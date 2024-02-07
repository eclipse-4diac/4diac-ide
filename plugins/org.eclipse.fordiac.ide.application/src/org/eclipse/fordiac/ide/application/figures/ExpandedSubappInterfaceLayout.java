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
import org.eclipse.draw2d.geometry.Dimension;
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
		positions.calculate(isInput);

		final List<? extends IFigure> children = parent.getChildren();
		final int numChildren = children.size();
		final Rectangle clientArea = transposer.t(parent.getClientArea());
		final int availableHeight = clientArea.height;
		final int x = clientArea.x;
		int y;
		if (isInput) {
			y = positions.getInputUnconnectedStart() == Integer.MAX_VALUE ? clientArea.y
					: positions.getInputUnconnectedStart();
		} else {
			y = positions.getOutputUnconnectedStart() == Integer.MAX_VALUE ? clientArea.y
					: positions.getOutputUnconnectedStart();
		}

		final Dimension[] prefSizes = new Dimension[numChildren];
		final Dimension[] minSizes = new Dimension[numChildren];

		// always horizontal
		final int wHint = parent.getClientArea(Rectangle.SINGLETON).width;
		final int hHint = -1;

		IFigure child;
		int totalHeight = 0;
		int totalMinHeight = 0;
		int prefMinSumHeight = 0;

		for (int i = 0; i < numChildren; i++) {
			child = children.get(i);

			prefSizes[i] = transposer.t(getChildPreferredSize(child, wHint, hHint));
			minSizes[i] = transposer.t(getChildMinimumSize(child, wHint, hHint));

			totalHeight += prefSizes[i].height;
			totalMinHeight += minSizes[i].height;
		}
		totalHeight += (numChildren - 1) * getSpacing();
		totalMinHeight += (numChildren - 1) * getSpacing();
		prefMinSumHeight = totalHeight - totalMinHeight;

		int amntShrinkHeight = totalHeight - Math.max(availableHeight, totalMinHeight);

		if (amntShrinkHeight < 0) {
			amntShrinkHeight = 0;
		}

		for (int i = 0; i < numChildren; i++) {
			int amntShrinkCurrentHeight = 0;
			final int prefHeight = prefSizes[i].height;
			final int minHeight = minSizes[i].height;
			final int prefWidth = prefSizes[i].width;
			final int minWidth = minSizes[i].width;
			child = children.get(i);

			final var entry = positions.positions.get(child);
			final Rectangle newBounds;
			if (entry.intValue() == Integer.MAX_VALUE) {
				newBounds = new Rectangle(x, y, prefWidth, prefHeight);
				y += newBounds.height + getSpacing();
			} else {
				newBounds = new Rectangle(x, entry.intValue(), prefWidth, prefHeight);
			}

			if (prefMinSumHeight != 0) {
				amntShrinkCurrentHeight = (prefHeight - minHeight) * amntShrinkHeight / (prefMinSumHeight);
			}

			int width = Math.min(prefWidth, transposer.t(child.getMaximumSize()).width);
			if (isStretchMinorAxis()) {
				width = transposer.t(child.getMaximumSize()).width;
			}
			width = Math.max(minWidth, Math.min(clientArea.width, width));
			newBounds.width = width;

			newBounds.x += clientArea.width - width; // bottom right alignment

			newBounds.height -= amntShrinkCurrentHeight;

			child.setBounds(transposer.t(newBounds));

			amntShrinkHeight -= amntShrinkCurrentHeight;
			prefMinSumHeight -= (prefHeight - minHeight);
		}

		parent.getBounds().height = parent.getParent().getBounds().height;
	}

}
