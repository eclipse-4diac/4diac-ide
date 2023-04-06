/*******************************************************************************
 * Copyright (c) 2012, 2020 Original authors and others.
 * 				 2023 Johannes Kepler University, Linz
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Original authors and others - initial API and implementation
 *     Roman Flueckiger <roman.flueckiger@mac.com> - Bug 450334
 *     Dirk Fauth <dirk.fauth@googlemail.com> - Bug 446276, 446275
 *     Prankur Agarwal - handle the case where the whole row is selected
 *     		using the serial number and the width sets to Integer.MAX_VALUE
 ******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.selection.SelectionModel;
import org.eclipse.swt.graphics.Rectangle;

public class FordiacSelectionModel extends SelectionModel {

	public FordiacSelectionModel(final SelectionLayer selectionLayer) {
		super(selectionLayer);
	}

	@Override
	public boolean isRowPositionFullySelected(final int rowPosition, final int rowWidth) {
		final List<Rectangle> selections = super.getSelections();

		// Aggregate all rectangles in the row which are in the selection
		// model
		final List<Rectangle> selectedRectanglesInRow = new ArrayList<>(selections.size());

		// If X is same add up the width of the selected area
		// If width is Integer.MAX_VALUE, it means that the whole row is selected hence treat it as fully selected
		for (final Rectangle r : selections) {
			if (r.width == Integer.MAX_VALUE) {
				return true;
			}
			// Row is within the bounds of the selected rectangle
			if (rowPosition >= r.y && rowPosition < r.y + r.height) {
				selectedRectanglesInRow.add(new Rectangle(r.x, rowPosition, r.width, 1));
			}
		}
		if (selectedRectanglesInRow.isEmpty()) {
			return false;
		}
		sortByX(selectedRectanglesInRow);
		Rectangle finalRectangle = new Rectangle(selectedRectanglesInRow.get(0).x, rowPosition, 0, 0);

		// Ensure that selections in the row are contiguous and cover the
		// entire row
		for (int i = 0; i < selectedRectanglesInRow.size(); i++) {
			final Rectangle rectangle = selectedRectanglesInRow.get(i);
			if (contains(finalRectangle, rectangle)) {
				continue;
			}
			if (i > 0) {
				final Rectangle previousRect = selectedRectanglesInRow.get(i - 1);
				if (rectangle.union(previousRect).width > (rectangle.width + previousRect.width)) {
					// Rectangles not contiguous
					return false;
				}
			}
			// Union will resolve any overlapping area
			finalRectangle = finalRectangle.union(rectangle);
		}
		return finalRectangle.width >= rowWidth;
	}
}
