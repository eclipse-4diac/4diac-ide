/*******************************************************************************
 * Copyright (c) 2014 - 2017 fortiss GmbH 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.draw2d;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceGetter;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.jface.preference.IPreferenceStore;

public class ConnectorBorder extends AbstractBorder {

	private boolean highlight = false;
	private IInterfaceElement editPartModelOject;
	private int leftMargin;
	private int rightMargin;
	private int topMargin;
	private int bottomMargin;
	
	public ConnectorBorder(IInterfaceElement editPartModelOject) {
		this(editPartModelOject, false, 0, 0);
	}
	
	public ConnectorBorder(IInterfaceElement editPartModelOject, boolean highlight) {
		this(editPartModelOject, highlight, 0, 0);
	}

	public ConnectorBorder(IInterfaceElement editPartModelOject, boolean highlight, int topMargin, int bottomMargin) {
		super();
		this.highlight = highlight;
		this.editPartModelOject = editPartModelOject;
		if(isAdapter()){
			leftMargin = 11;
			rightMargin = 11;
		}else{
			leftMargin = 5;
			rightMargin = 5;
		}
		this.topMargin = topMargin;
		this.bottomMargin = bottomMargin;
	}

	protected void createAdapterSymbol_MiniFBrotated(final Graphics graphics, Rectangle where, int width, boolean filled){
		graphics.setBackgroundColor(PreferenceGetter
				.getColor(PreferenceConstants.P_ADAPTER_CONNECTOR_COLOR));
		graphics.setForegroundColor(PreferenceGetter
				.getColor(PreferenceConstants.P_ADAPTER_CONNECTOR_COLOR));
		graphics.setLineWidth(1);
		graphics.setAntialias(1);
		PointList points = new PointList();		
		int offest = 4;
		points.addPoint(width + where.x, where.y + offest);
		if(filled){						
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
        }else{
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
	public void paint(final IFigure figure, final Graphics graphics,
			final Insets insets) {
		if (isEvent()) {
			graphics.setForegroundColor(PreferenceGetter
					.getColor(PreferenceConstants.P_EVENT_CONNECTOR_COLOR));
			graphics.setBackgroundColor(PreferenceGetter
					.getColor(PreferenceConstants.P_EVENT_CONNECTOR_COLOR));
		} else if(isAdapter()) {
			graphics.setForegroundColor(PreferenceGetter
					.getColor(PreferenceConstants.P_ADAPTER_CONNECTOR_COLOR));
			graphics.setBackgroundColor(PreferenceGetter
					.getColor(PreferenceConstants.P_ADAPTER_CONNECTOR_COLOR));
		} else {
			graphics.setForegroundColor(PreferenceGetter
					.getColor(PreferenceConstants.P_DATA_CONNECTOR_COLOR));
			graphics.setBackgroundColor(PreferenceGetter
					.getColor(PreferenceConstants.P_DATA_CONNECTOR_COLOR));
		}
		Rectangle where = getPaintRectangle(figure, insets);
		Rectangle r = null;
		if(isInput()){
			if(isAdapter()){
				createAdapterSymbol_MiniFBrotated(graphics, where, 0, false);
			}else{
				r = new Rectangle(where.x, where.y + (where.height / 2) - 1, 4, 4);
				graphics.fillRectangle(r);				
			}
		} else {
			if(isAdapter()){
				createAdapterSymbol_MiniFBrotated(graphics, where, where.width - 9, true);//16
			}else{
				r = new Rectangle(where.width + where.x - 4, where.y + (where.height / 2) - 1, 4, 4);
				graphics.fillRectangle(r);
			}
		}
		if (highlight) {
			graphics.setForegroundColor(ColorConstants.gray);
			graphics.setBackgroundColor(ColorConstants.gray);
			IPreferenceStore pf = org.eclipse.fordiac.ide.gef.Activator
					.getDefault().getPreferenceStore();
			int cornerDim = pf.getInt(DiagramPreferences.CORNER_DIM);
			if (cornerDim > 1) {
				cornerDim = cornerDim / 2;
			}
			Rectangle rect = getPaintRectangle(figure, new Insets(0, 0, 1, 1));
			graphics.drawRoundRectangle(new Rectangle(rect.x, rect.y,
					rect.width, rect.height), cornerDim, cornerDim);
		}		
	}

	@Override
	public Insets getInsets(final IFigure figure) {
		return new Insets(topMargin, leftMargin, bottomMargin, rightMargin);
	}

	public int getTopMargin() {
		return topMargin;
	}

	public void setTopMargin(int topMargin) {
		this.topMargin = topMargin;
	}

	public int getBottomMargin() {
		return bottomMargin;
	}

	public void setBottomMargin(int bottomMargin) {
		this.bottomMargin = bottomMargin;
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
