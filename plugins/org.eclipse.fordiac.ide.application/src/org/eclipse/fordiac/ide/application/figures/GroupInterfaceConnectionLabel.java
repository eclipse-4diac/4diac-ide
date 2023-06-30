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
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.gef.figures.HideableConnection;

public class GroupInterfaceConnectionLabel extends FBNetworkConnectionLabel {

	protected final FBNetworkConnection connFigure;
	private final Figure connectorLine;
	private IFigure groupFigure;

	public GroupInterfaceConnectionLabel(final boolean srcLabel, final IFigure groupFigure,
			final FBNetworkConnection connFigure) {
		super(srcLabel);
		this.groupFigure = groupFigure;
		this.connFigure = connFigure;
		connectorLine = createConnectorLine(connFigure);
		add(connectorLine, (srcLabel) ? 0 : -1);

	}

	public IFigure getGroupFigure() {
		return groupFigure;
	}

	public void setGroupFigure(final IFigure groupFigure) {
		this.groupFigure = groupFigure;
	}

	@Override
	public void setLocation(final Point p) {
		if (groupFigure == null) {
			// there was a problem with the group figure, go back to the default behavior
			super.setLocation(p);
			return;
		}

		final Dimension preferredSize = getPreferredLabelSize();
		final Rectangle groupBounds = groupFigure.getBounds();
		final Point refPoint = p.getCopy();
		int width = 0;
		if (!isSrcLabel()) {
			refPoint.x = Math.min(groupBounds.x, p.x - preferredSize.width);
			width = Math.max(p.x - refPoint.x, preferredSize.width);
		} else {
			width = Math.max(groupBounds.right() - refPoint.x, preferredSize.width);
		}
		refPoint.y -= preferredSize.height / 2;

		connectorLine.setPreferredSize(Math.max(0, width - preferredSize.width), preferredSize.height);
		preferredSize.width = width;
		super.setBounds(new Rectangle(refPoint, preferredSize));
	}

	private Dimension getPreferredLabelSize() {
		// we need to manually calculate the preferred size otherwise any invalidations and layout manager interactions
		// result in infinite update loops on resize operations.
		final Label label = getLabel();
		final Dimension preferredSize = label.getTextBounds().getSize();
		final Insets insets = getInsets();
		preferredSize.expand(insets.getWidth() + 4, insets.getHeight());  // the +4 are for the margin border
		return preferredSize;
	}

	@SuppressWarnings("static-method") // allow subclasses to provide their own connector line (e.g., fan-out and in)
	protected Figure createConnectorLine(final HideableConnection connFigure) {
		return new Figure() {
			@Override
			protected void paintFigure(final Graphics g) {
				g.setLineWidth(connFigure.getLineWidth());
				final Rectangle bounds = getBounds();
				g.drawLine(bounds.getLeft(), bounds.getRight());
				drawFanOutConnector(connFigure, g, bounds);
				if (connFigure.isAdapterOrStructConnection()) {
					g.setLineWidth(connFigure.getLineWidth() / HideableConnection.NORMAL_DOUBLE_LINE_WIDTH);
					if (g.getAbsoluteScale() >= 1) {
						g.setForegroundColor(connFigure.getLighterColor());
						g.drawLine(bounds.getLeft(), bounds.getRight());
					}
				}
			}

			/*
			 * Draws the small part of the connection that is needed to connect the
			 * first group interface label with the other fan out labels.
			 *
			 * ----------------------------------->
			 *                       |   <- draws this
			 *                       |
			 *                       ------------->
			 *                       |
			 *                       |
			 *                       ------------->
			 *                       |
			 */
			private void drawFanOutConnector(final HideableConnection connFigure, final Graphics g,
					final Rectangle bounds) {
				if (isSrcLabel() && FBNetworkConnection.getHiddenConnections(connFigure.getModel().getSource().getOutputConnections()).size() > 1) {
					final Point right = bounds.getRight();
					final int labelHeight = getLabel().getBounds().height();
					final int labelWidth = getLabel().getBounds().width;
					final int maxLabelWidth = ((FBNetworkConnection) connFigure).getMaxFanOutLabelWidth();
					final int diff = maxLabelWidth - labelWidth;
					g.drawLine(right.x - labelHeight - diff, right.y, right.x - labelHeight - diff, right.y + labelHeight);
				}
			}
		};
	}

}
