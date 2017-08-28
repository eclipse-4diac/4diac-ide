/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.handles;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Locator;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.handles.SquareHandle;
import org.eclipse.swt.graphics.Color;


/** A selection handle that can be utilized to draw a square with a plus on the 
 * selection border to indicate creation options.
 * 
 */
public class PlusHandle extends SquareHandle {
	
	private static final int DEFAULT_PLUS_HANDLE_SIZE = 9;
	
	Color handleColor = ColorConstants.gray;
	
	public PlusHandle(GraphicalEditPart owner, Locator loc) {
		super(owner, loc);
	}

	void setHandleColor(Color col){
		handleColor = col;
	}

	@Override
	protected void init() {
		setPreferredSize(new Dimension(DEFAULT_PLUS_HANDLE_SIZE, DEFAULT_PLUS_HANDLE_SIZE));
	}

	
	@Override
	protected DragTracker createDragTracker() {
		//We don't want to be draged arround
		return null;
	}
	
	@Override
	protected Color getBorderColor() {
		return handleColor;
	}

	@Override
	protected Color getFillColor() {
		return handleColor;
	}
	
	@Override
	public void paintFigure(Graphics g) {
		super.paintFigure(g);
		Rectangle r = getBounds();
		
		g.setBackgroundColor(getFillColor());
		g.fillRectangle(r.x, r.y, r.width, r.height);
		
		g.setForegroundColor(ColorConstants.white);
		
		int yMiddle = r.y + r.height/2;
		int xMiddle = r.x + r.width/2;
		
		g.drawLine(r.x , yMiddle, r.x + r.width , yMiddle);
		g.drawLine(xMiddle, r.y, xMiddle, r.y + r.height);
	}

}
