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
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.gef.figures.HideableConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;

public class FanOutGroupInterfaceConnectionLabel extends GroupInterfaceConnectionLabel {

	private final EList<Connection> connListRef;

	public FanOutGroupInterfaceConnectionLabel(final boolean srcLabel, final IFigure groupFigure,
			final FBNetworkConnection connFigure, final EList<Connection> connListRef) {
		super(srcLabel, groupFigure, connFigure);
		connFigure.setBounds(connFigure.getBounds());
		this.connListRef = connListRef;
	}

	private int getIndex() {
		return getHiddenGroupBorderCrossingConnections(connListRef).indexOf(connFigure.getModel());
	}

	private boolean isLast() {
		final List<Connection> hiddenConnections = getHiddenGroupBorderCrossingConnections(connListRef);
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

	private class FanOutConnectorLineFigure extends GroupInterfaceConnectorFigure {

		@Override
		protected void paintFigure(final Graphics g) {
			if (getIndex() == 0) {
				// handles the case where the first interface element (which is a GroupInterfaceConnectionLabel)
				// gets deleted and a FanOutGroupInterfaceConnectionLabel has to "take its place"
				super.paintFigure(g);
			} else {
				g.setLineWidth(connFigure.getLineWidth());

				final PointList points = createPointList(g);
				g.drawPolyline(points);

				drawAdapterOrStructLine(g, points);
			}
		}

		private PointList createPointList(final Graphics g) {
			final Point rightBounds = getBounds().getRight();
			final int labelHeight = getLabel().getBounds().height;
			final int labelWidth = getLabel().getBounds().width;
			final int maxLabelWidth = connFigure.getMaxFanOutLabelWidth();
			final int diff = maxLabelWidth - labelWidth;

			final PointList points = new PointList();

			final int rightX = rightBounds.x;
			final int leftX = rightBounds.x - labelHeight - diff;

			// left
			points.addPoint(rightX, rightBounds.y);
			points.addPoint(leftX, rightBounds.y);

			// up
			points.addPoint(leftX, rightBounds.y - labelHeight);

			// down
			if (!isLast()) {
				points.addPoint(leftX, rightBounds.y + labelHeight);
			}

			return points;
		}

	}

}
