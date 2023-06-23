/*******************************************************************************
 * Copyright (c) 2023  Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.figures;

import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.gef.figures.HideableConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;

public class FanOutGroupInterfaceConnectionLabel extends GroupInterfaceConnectionLabel {

	private final EList<Connection> connListRef;

	public FanOutGroupInterfaceConnectionLabel(final boolean srcLabel, final IFigure groupFigure, final FBNetworkConnection connFigure, final EList<Connection> connListRef) {
		super(srcLabel, groupFigure, connFigure);
		connFigure.setBounds(connFigure.getBounds());
		this.connListRef = connListRef;
	}

	private int getIndex() {
		return FBNetworkConnection.getHiddenConnections(connListRef).indexOf(connFigure.getModel());
	}

	private boolean isLast() {
		final List<Connection> hiddenConnections = FBNetworkConnection.getHiddenConnections(connListRef);
		return hiddenConnections.indexOf(connFigure.getModel()) == hiddenConnections.size() - 1;
	}

	@Override
	public void setLocation(final Point p) {
		p.y += getBounds().height() * getIndex();
		super.setLocation(p);
	}

	@Override
	protected Figure createConnectorLine(final HideableConnection connFigure) {
		return new FanOutConnectorLineFigure();
	}

	private class FanOutConnectorLineFigure extends Figure {

		private static enum Direction {
			UP,
			DOWN,
			LEFT,
			RIGHT
		}

		@Override
		protected void paintFigure(final Graphics g) {
			g.setLineWidth(connFigure.getLineWidth());
			final Rectangle bounds = getBounds();

			drawLine(g, Direction.LEFT);
			drawLine(g, Direction.UP);
			if (!isLast()) {
				drawLine(g, Direction.DOWN);
			}

			if (connFigure.isAdapterOrStructConnection()) {
				g.setLineWidth(connFigure.getLineWidth() / HideableConnection.NORMAL_DOUBLE_LINE_WIDTH);
				if (g.getAbsoluteScale() >= 1) {
					g.setForegroundColor(connFigure.getLighterColor());
					g.drawLine(bounds.getLeft(), bounds.getRight());
				}
			}
		}

		private void drawLine(final Graphics g, final Direction direction) {
			final int labelHeight = getLabel().getBounds().height();
			final Point rightBounds = getBounds().getRight();

			int x1, y1, x2, y2;
			switch (direction) {
			case UP:
				x1 = rightBounds.x - labelHeight;
				y1 = rightBounds.y;
				x2 = x1;
				y2 = rightBounds.y - labelHeight;
				break;
			case DOWN:
				x1 = rightBounds.x - labelHeight;
				y1 = rightBounds.y;
				x2 = x1;
				y2 = rightBounds.y + labelHeight;
				break;
			case LEFT:
				x1 = rightBounds.x;
				y1 = rightBounds.y;
				x2 = rightBounds.x - labelHeight;
				y2 = rightBounds.y;
				break;
			case RIGHT:
				// not yet needed
			default:
				return;
			}

			g.drawLine(x1, y1, x2, y2);
		}

	}

}
