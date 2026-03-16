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

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.gef.figures.AbstractShadowBorder;

class WithColumnFigure extends Figure {

	public WithColumnFigure(final boolean inputSide, final int columnWidth) {
		setLayoutManager(new WithColumnLayout(inputSide, columnWidth));
	}

	public void setColumnWidth(final int width) {
		getLayoutManager().setColumnWidth(width);
		invalidate();
		revalidate();
	}

	@Override
	public WithColumnLayout getLayoutManager() {
		return (WithColumnLayout) super.getLayoutManager();
	}

	private static class WithColumnLayout extends AbstractPinPropColumnLayout {
		private int columnWidth;

		public WithColumnLayout(final boolean inputSide, final int columnWidth) {
			super(inputSide);
			this.columnWidth = columnWidth;
		}

		public void setColumnWidth(final int width) {
			this.columnWidth = width;
		}

		@Override
		public void layout(final IFigure container) {
			final Rectangle r = container.getClientArea();
			for (final IFigure child : container.getChildren()) {
				final int y = getChildYPos(child);
				final int x = r.x + ((isInputSide()) ? AbstractShadowBorder.SHADOW_INSETS.left
						: -AbstractShadowBorder.SHADOW_INSETS.right);
				child.setBounds(new Rectangle(x, y, columnWidth, child.getPreferredSize().height));
			}
		}

		@Override
		protected Dimension calculatePreferredSize(final IFigure container, final int wHint, final int hHint) {
			int maxHeight = 0;

			for (final IFigure child : container.getChildren()) {
				final int y = getChildYPos(child);
				maxHeight = Math.max(maxHeight, y + child.getPreferredSize().height);
			}

			return new Dimension(columnWidth, maxHeight);
		}

	}
}
