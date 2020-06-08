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
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceGetter;
import org.eclipse.swt.graphics.Color;

public class ConnectorBorder extends AbstractBorder {

	private static final int LR_MARGIN = 5;
	private static final int LR_ADAPTER_MARGIN = 11;

	private final IInterfaceElement editPartModelOject;
	private Color connectorColor;

	public ConnectorBorder(IInterfaceElement editPartModelOject) {
		super();
		this.editPartModelOject = editPartModelOject;
		updateColor();
	}

	public void updateColor() {
		if (isEvent()) {
			connectorColor = PreferenceGetter.getColor(PreferenceConstants.P_EVENT_CONNECTOR_COLOR);
		} else if (isAdapter()) {
			connectorColor = PreferenceGetter.getColor(PreferenceConstants.P_ADAPTER_CONNECTOR_COLOR);
		} else {
			connectorColor = PreferenceGetter.getDataColor(editPartModelOject.getType().getName());
		}
	}

	protected static void createAdapterSymbolMiniFBrotated(final Graphics graphics, Rectangle where, int width,
			boolean filled) {
		graphics.setLineWidth(1);
		graphics.setAntialias(1);
		PointList points = new PointList();
		int offest = 4;
		points.addPoint(width + where.x, where.y + offest);
		if (filled) {
			points.addPoint(width + where.x + 2, where.y + offest);
			points.addPoint(width + where.x + 2, where.y + offest + 2);
			points.addPoint(width + where.x + 4, where.y + offest + 2);
			points.addPoint(width + where.x + 4, where.y + offest);
			points.addPoint(width + where.x + 8, where.y + offest);
			points.addPoint(width + where.x + 8, where.y + offest + 8);
			points.addPoint(width + where.x + 4, where.y + offest + 8);
			points.addPoint(width + where.x + 4, where.y + offest + 6);
			points.addPoint(width + where.x + 2, where.y + offest + 6);
			points.addPoint(width + where.x + 2, where.y + offest + 8);
			points.addPoint(width + where.x, where.y + offest + 8);
			points.addPoint(width + where.x, where.y + offest);
			graphics.fillPolygon(points);
		} else {
			points.addPoint(width + where.x + 4, where.y + offest);
			points.addPoint(width + where.x + 4, where.y + offest + 2);
			points.addPoint(width + where.x + 6, where.y + offest + 2);
			points.addPoint(width + where.x + 6, where.y + offest);
			points.addPoint(width + where.x + 8, where.y + offest);
			points.addPoint(width + where.x + 8, where.y + offest + 7);
			points.addPoint(width + where.x + 6, where.y + offest + 7);
			points.addPoint(width + where.x + 6, where.y + offest + 5);
			points.addPoint(width + where.x + 4, where.y + offest + 5);
			points.addPoint(width + where.x + 4, where.y + offest + 7);
			points.addPoint(width + where.x, where.y + offest + 7);
			points.addPoint(width + where.x, where.y + offest);
			graphics.drawPolygon(points);
		}
	}

	@Override
	public void paint(final IFigure figure, final Graphics graphics, final Insets insets) {
		graphics.setForegroundColor(connectorColor);
		graphics.setBackgroundColor(connectorColor);

		Rectangle where = getPaintRectangle(figure, insets);
		Rectangle r = null;
		if (isInput()) {
			if (isAdapter()) {
				createAdapterSymbolMiniFBrotated(graphics, where, 0, false);
			} else {
				r = new Rectangle(where.x, where.y + (where.height / 2) - 1, 4, 4);
				graphics.fillRectangle(r);
			}
		} else {
			if (isAdapter()) {
				createAdapterSymbolMiniFBrotated(graphics, where, where.width - 9, true);// 16
			} else {
				r = new Rectangle(where.width + where.x - 4, where.y + (where.height / 2) - 1, 4, 4);
				graphics.fillRectangle(r);
			}
		}
	}

	@Override
	public Insets getInsets(final IFigure figure) {
		int lrMargin = (isAdapter()) ? LR_ADAPTER_MARGIN : LR_MARGIN;
		return new Insets(0, lrMargin, 0, lrMargin);
	}

	public boolean isInput() {
		return editPartModelOject.isIsInput();
	}

	public boolean isEvent() {
		return editPartModelOject instanceof Event;
	}

	public boolean isAdapter() {
		return editPartModelOject instanceof AdapterDeclaration;
	}
}
