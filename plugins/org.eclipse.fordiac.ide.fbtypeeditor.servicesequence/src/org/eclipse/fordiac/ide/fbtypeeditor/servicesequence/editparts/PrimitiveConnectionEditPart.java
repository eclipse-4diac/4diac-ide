/*******************************************************************************
 * Copyright (c) 2008, 2014, 2015 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
	
	public PrimitiveConnection getCastedModel() {
		return (PrimitiveConnection) getModel();
	}

	@Override
	protected void createEditPolicies() {
//		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new ConnectionEndpointEditPolicy());
	}
	
	@Override
	protected IFigure createFigure() {
		connection = (PolylineConnection) super.createFigure();
		setConnection(getCastedModel().isLeft(), getCastedModel().isInputPrimitive());
		return connection;
	}
	
	public void setConnection(boolean isLeft, boolean isInput){
		PointList pl = new PointList();
		if(isInput) {
			connection.setSourceDecoration(null);
			connection.setTargetDecoration(createArrowRectangle(pl));
		}else{
			connection.setSourceDecoration(createSquare(pl));
			connection.setTargetDecoration(createArrow(pl));		
		}
	}
	
	private PolygonDecoration createArrow(PointList pl){
		PolygonDecoration arrow = new PolygonDecoration();
		pl = new PointList();
		pl.addPoint(0, 0);
		pl.addPoint(-7, -4);
		pl.addPoint(-7, 4);
		pl.addPoint(0, 0);
		arrow.setTemplate(pl);
		arrow.setScale(1, 1);
		return arrow;
	}

	private PolygonDecoration createSquare(PointList pl){
		PolygonDecoration square = new PolygonDecoration();
		pl = new PointList();
		pl.addPoint(-4, -4);
		pl.addPoint(-4, 4);
		pl.addPoint(4, 4);
		pl.addPoint(4, -4);
		pl.addPoint(-4, -4);
		square.setTemplate(pl);
		square.setScale(1, 1);
		return square;
	}
	
	private PolygonDecoration createArrowRectangle(PointList pl){
		PolygonDecoration arrowRectangle = new PolygonDecoration();	
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
