/*******************************************************************************
 * Copyright (c) 2014, 2015, 2017 Profactor GbmH, fortiss GmbH 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.figures;

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

public class HideableConnection extends PolylineConnection {
	
	public static final int BEND_POINT_BEVEL_SIZE = 5;
	
	boolean hidden = false;
	String label = ""; //$NON-NLS-1$
	Rectangle moveRect = new Rectangle();
	org.eclipse.fordiac.ide.model.libraryElement.Connection model;
	
	public void setModel(org.eclipse.fordiac.ide.model.libraryElement.Connection newModel){
		model = newModel;
	}
	
	public org.eclipse.fordiac.ide.model.libraryElement.Connection getModel() {
		return model;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	
	@Override
	protected void outlineShape(Graphics g) {
		if (isHidden()) {

			int[] startLine = new int[] { getStart().x, getStart().y,
					getStart().x + 20, getStart().y };
			int[] endLine = new int[] { getEnd().x, getEnd().y,
					getEnd().x - 20, getEnd().y };
			g.drawPolyline(startLine);
			g.drawPolyline(endLine);
			
			Dimension dim = FigureUtilities.getTextExtents(label, g.getFont());
			g.drawText(label, new Point(getEnd().x - dim.width - 25, getEnd().y-dim.height/2));
			moveRect.x = getEnd().x - dim.width - 25; 
			moveRect.y = getEnd().y-dim.height/2; 
			moveRect.width =5; 
			moveRect.height =5; 
			//g.drawOval(moveRect);
			
		} else {
			drawBeveledPolyline(g);
		}
	}

	private void drawBeveledPolyline(Graphics g) {
		PointList beveledPoints = new PointList();
		
		beveledPoints.addPoint(getPoints().getFirstPoint());
		
		for(int i = 1; i < getPoints().size() -1; i++){
			Point bevore = getPoints().getPoint(i - 1);
			Point after = getPoints().getPoint(i + 1);
			
			int verDistance = Math.abs(bevore.y - after.y);
			int horDistance = Math.abs(bevore.y - after.y);
			int bevelSize = BEND_POINT_BEVEL_SIZE;
			if(verDistance < 2 * BEND_POINT_BEVEL_SIZE){
				bevelSize = verDistance / 2;
			}
			if(horDistance < 2 * BEND_POINT_BEVEL_SIZE){
				bevelSize = horDistance / 2;
			}
			
			beveledPoints.addPoint(calcualtedBeveledPoint(getPoints().getPoint(i), bevore, bevelSize));
			beveledPoints.addPoint(calcualtedBeveledPoint(getPoints().getPoint(i), after, bevelSize));
		}
		
		beveledPoints.addPoint(getPoints().getLastPoint());
		
		g.drawPolyline(beveledPoints);
	}
	
	private static Point calcualtedBeveledPoint(Point refPoint, Point otherPoint, int bevelSize){
		if(0 == (refPoint.x - otherPoint.x)){
			return new Point(refPoint.x, 
					refPoint.y + (((refPoint.y - otherPoint.y) > 0) ? -bevelSize : bevelSize));				
		} 
		return new Point(refPoint.x + (((refPoint.x - otherPoint.x) > 0) ? -bevelSize : bevelSize), refPoint.y);
	}
	
}
