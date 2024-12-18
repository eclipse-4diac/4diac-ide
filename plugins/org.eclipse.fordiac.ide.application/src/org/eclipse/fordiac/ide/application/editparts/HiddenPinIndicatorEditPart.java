/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.ui.preferences.UIPreferenceConstants;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceGetter;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

public class HiddenPinIndicatorEditPart extends AbstractGraphicalEditPart {
	public static final class FordiacTriangle extends Shape {

		private boolean isInput = true;

		public FordiacTriangle(final boolean isInput) {
			this.isInput = isInput;
		}

		/** The points of the triangle. */
		private final PointList triangle = new PointList(3);

		/** @see Shape#fillShape(Graphics) */
		@Override
		protected void fillShape(final Graphics g) {
			g.fillPolygon(triangle);
		}

		/** @see Shape#outlineShape(Graphics) */
		@Override
		protected void outlineShape(final Graphics g) {
			g.drawPolygon(triangle);
		}

		/** @see Figure#primTranslate(int, int) */
		@Override
		public void primTranslate(final int dx, final int dy) {
			super.primTranslate(dx, dy);
			triangle.translate(dx, dy);
		}

		/** @see IFigure#validate() */
		@Override
		public void validate() {
			super.validate();
			final Rectangle r = new Rectangle();
			r.setBounds(getBounds());
			r.shrink(getInsets());
			r.resize(-1, -1);
			int size = Math.min(r.height, r.width);
			size = Math.max(size, 1); // Size cannot be negative
			final Point head, p2, p3;
			if (isInput) {
				head = new Point(r.x, r.y);
				p2 = new Point(head.x, head.y + size);
				p3 = new Point(head.x + size, head.y + size);
			} else {
				head = new Point(r.x + size, r.y);
				p2 = new Point(head.x, head.y + size);
				p3 = new Point(head.x - size, head.y + size);
			}
			triangle.removeAllPoints();
			triangle.addPoint(head);
			triangle.addPoint(p2);
			triangle.addPoint(p3);
		}
	}

	@Override
	protected IFigure createFigure() {
		final FordiacTriangle triangle = new FordiacTriangle(((HiddenPinIndicator) super.getModel()).isInput());
		triangle.setBorder(new MarginBorder(3));
		triangle.setBounds(new Rectangle(0, 0, 15, 15));
		triangle.setBackgroundColor(PreferenceGetter.getColor(UIPreferenceConstants.P_ANY_INT_CONNECTOR_COLOR));
		triangle.setOutline(false);
		return triangle;
	}

	@Override
	protected void createEditPolicies() {
		// nothing to do here
	}

}
