/*******************************************************************************
 * Copyright (c) 2014 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl 
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.routers;

import org.eclipse.draw2d.AbstractRouter;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.systemconfiguration.editparts.LinkEditPart;
import org.eclipse.fordiac.ide.systemconfiguration.editparts.SegmentEditPart;

public class LinkConnectionRouter extends AbstractRouter {
	
	// the link editpart of the link associated with this connection to route
	LinkEditPart linkEditPart;

	public LinkConnectionRouter(LinkEditPart linkEditPart) {
		super();
		this.linkEditPart = linkEditPart;
	}

	@Override
	public void route(Connection connection) {
		PointList points = connection.getPoints();
		points.removeAllPoints();
		
		Point startPoint = getStartPoint(connection);
		Point endPoint = getEndPoint(connection);
		Point bendPoint = null;
		
		startPoint.x = endPoint.x;
		//This transformation has to be done before working with the bounds from the figures 
		connection.translateToRelative(startPoint);
		connection.translateToRelative(endPoint);
		
		if(null != linkEditPart.getSource()) {
			Rectangle segmentBounds = ((SegmentEditPart)linkEditPart.getSource()).getFigure().getBounds();
			
			if(startPoint.x < segmentBounds.x) { 
				//the devices connection point lays left of the segment's x range we need to insert a bend point
				startPoint.x = segmentBounds.x;
				startPoint.y = segmentBounds.getCenter().y;
				
				bendPoint = new Point(endPoint.x, startPoint.y);
				
			}else if (segmentBounds.right() < startPoint.x){
				//the devices connection point lays right of the segment's x range we need to insert a bend point				
				startPoint.x = segmentBounds.right();
				startPoint.y = segmentBounds.getCenter().y;
				
				bendPoint = new Point(endPoint.x, startPoint.y);
			}
		}
		
		points.addPoint(startPoint);
		if(null != bendPoint){
			//connection.translateToRelative(bendPoint);
			points.addPoint(bendPoint);	
		}
		points.addPoint(endPoint);
		
		connection.setPoints(points);
	}
}
