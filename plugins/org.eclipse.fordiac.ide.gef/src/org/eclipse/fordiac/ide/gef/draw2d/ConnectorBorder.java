/*******************************************************************************
 * Copyright (c) 2014 - 2017 fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - added separate colors for different data types
 *               - added color chache for improved drawing performance
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.draw2d;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.model.data.EventType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceGetter;
import org.eclipse.swt.graphics.Color;

public class ConnectorBorder extends AbstractBorder {

	private static final int CONNECTOR_SIZE = 5;
	private static final int ADAPTER_SIZE = 9;

	private static final int LR_MARGIN = CONNECTOR_SIZE + 1;
	private static final int LR_ADAPTER_MARGIN = 11;

	private final IInterfaceElement editPartModelOject;
	private Color connectorColor;

	public ConnectorBorder(final IInterfaceElement editPartModelOject) {
		super();
		this.editPartModelOject = editPartModelOject;
		updateColor();
	}

	public final void updateColor() {
		if (isEvent()) {
			connectorColor = PreferenceGetter.getColor(PreferenceConstants.P_EVENT_CONNECTOR_COLOR);
		} else if (isAdapter()) {
			connectorColor = PreferenceGetter.getColor(PreferenceConstants.P_ADAPTER_CONNECTOR_COLOR);
		} else {
			connectorColor = PreferenceGetter.getDataColor(editPartModelOject.getType().getName());
		}
	}

	protected static void createAdapterSymbolMiniFBrotated(final Graphics graphics, final Rectangle where, final int width,
			final boolean filled) {
		graphics.setLineWidth(1);
		graphics.setAntialias(1);
		where.x += width;
		where.y += (where.height - ADAPTER_SIZE) / 2;
		final PointList points = new PointList();
		points.addPoint(where.x, where.y);
		if (filled) {
			points.addPoint(where.x + 2, where.y);
			points.addPoint(where.x + 2, where.y + 2);
			points.addPoint(where.x + 4, where.y + 2);
			points.addPoint(where.x + 4, where.y);
			points.addPoint(where.x + 8, where.y);
			points.addPoint(where.x + 8, where.y + 8);
			points.addPoint(where.x + 4, where.y + 8);
			points.addPoint(where.x + 4, where.y + 6);
			points.addPoint(where.x + 2, where.y + 6);
			points.addPoint(where.x + 2, where.y + 8);
			points.addPoint(where.x, where.y + 8);
			points.addPoint(where.x, where.y);
			graphics.fillPolygon(points);
		} else {
			points.addPoint(where.x + 4, where.y);
			points.addPoint(where.x + 4, where.y + 2);
			points.addPoint(where.x + 6, where.y + 2);
			points.addPoint(where.x + 6, where.y);
			points.addPoint(where.x + 8, where.y);
			points.addPoint(where.x + 8, where.y + 8);
			points.addPoint(where.x + 6, where.y + 8);
			points.addPoint(where.x + 6, where.y + 6);
			points.addPoint(where.x + 4, where.y + 6);
			points.addPoint(where.x + 4, where.y + 8);
			points.addPoint(where.x, where.y + 8);
			points.addPoint(where.x, where.y);
			graphics.drawPolygon(points);
		}
	}

	@Override
	public void paint(final IFigure figure, final Graphics graphics, final Insets insets) {
		graphics.setForegroundColor(connectorColor);
		graphics.setBackgroundColor(connectorColor);

		final Rectangle where = getPaintRectangle(figure, insets);
		Rectangle r = null;
		if (isInput()) {
			if (isAdapter()) {
				createAdapterSymbolMiniFBrotated(graphics, where, 0, false);
			} else {
				r = new Rectangle(where.x, where.y + (where.height - CONNECTOR_SIZE) / 2, CONNECTOR_SIZE,
						CONNECTOR_SIZE);
				graphics.fillRectangle(r);
			}
		} else {
			if (isAdapter()) {
				createAdapterSymbolMiniFBrotated(graphics, where, where.width - ADAPTER_SIZE + 1, true);
			} else {
				r = new Rectangle(where.width + where.x - CONNECTOR_SIZE,
						where.y + (where.height - CONNECTOR_SIZE) / 2,
						CONNECTOR_SIZE, CONNECTOR_SIZE);
				graphics.fillRectangle(r);
			}
		}
	}

	@Override
	public Insets getInsets(final IFigure figure) {
		final int lrMargin = (isAdapter()) ? LR_ADAPTER_MARGIN : LR_MARGIN;
		return new Insets(0, lrMargin, 0, lrMargin);
	}

	public boolean isInput() {
		return editPartModelOject.isIsInput();
	}

	public final boolean isEvent() {
		return editPartModelOject.getType() instanceof EventType;
	}

	public final boolean isAdapter() {
		return editPartModelOject.getType() instanceof AdapterType;
	}
}
