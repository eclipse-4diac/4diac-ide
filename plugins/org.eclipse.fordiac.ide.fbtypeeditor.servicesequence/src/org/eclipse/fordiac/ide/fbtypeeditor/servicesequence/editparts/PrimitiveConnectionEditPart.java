/*******************************************************************************
 * Copyright (c) 2008, 2014, 2015 Profactor GmbH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;

public class PrimitiveConnectionEditPart extends AbstractConnectionEditPart {

	private PolylineConnection connection;

	@Override
	public PrimitiveConnection getModel() {
		return (PrimitiveConnection) super.getModel();
	}

	@Override
	protected void createEditPolicies() {
		// currently we don't have special edit polices
	}

	@Override
	protected IFigure createFigure() {
		connection = (PolylineConnection) super.createFigure();
		setConnection(getModel().isLeft(), getModel().isInputPrimitive());
		return connection;
	}

	public void setConnection(boolean isLeft, boolean isInput) {
		// TODO consider isLeft here
		if (isInput) {
			connection.setSourceDecoration(null);
			connection.setTargetDecoration(createArrowRectangle());
		} else {
			connection.setSourceDecoration(createSquare());
			connection.setTargetDecoration(createArrow());
		}
	}

	private static PolygonDecoration createArrow() {
		final PolygonDecoration arrow = new PolygonDecoration();
		final PointList pl = new PointList();
		pl.addPoint(0, 0);
		pl.addPoint(-7, -4);
		pl.addPoint(-7, 4);
		pl.addPoint(0, 0);
		arrow.setTemplate(pl);
		arrow.setScale(1, 1);
		return arrow;
	}

	private static PolygonDecoration createSquare() {
		final PolygonDecoration square = new PolygonDecoration();
		final PointList pl = new PointList();
		pl.addPoint(-4, -4);
		pl.addPoint(-4, 4);
		pl.addPoint(4, 4);
		pl.addPoint(4, -4);
		pl.addPoint(-4, -4);
		square.setTemplate(pl);
		square.setScale(1, 1);
		return square;
	}

	private static PolygonDecoration createArrowRectangle() {
		final PolygonDecoration arrowRectangle = new PolygonDecoration();
		final PointList pl = new PointList();
		pl.addPoint(-4, -4);
		pl.addPoint(-4, 4);
		pl.addPoint(4, 4);
		pl.addPoint(4, -4);
		pl.addPoint(-4, -4);
		pl.addPoint(-4, 0);
		pl.addPoint(-11, -4);
		pl.addPoint(-11, 4);
		pl.addPoint(-4, 0);
		arrowRectangle.setTemplate(pl);
		arrowRectangle.setScale(1, 1);
		return arrowRectangle;
	}
}
