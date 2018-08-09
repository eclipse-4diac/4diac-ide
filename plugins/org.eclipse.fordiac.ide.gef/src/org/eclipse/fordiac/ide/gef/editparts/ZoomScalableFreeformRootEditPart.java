/*******************************************************************************
 * Copyright (c) 2008, 2015 - 2018 Profactor GbmH, fortiss GmbH, 
 * 								   Johannes Kepler University 
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
package org.eclipse.fordiac.ide.gef.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayeredPane;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.LayeredPane;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.GridLayer;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.tools.MarqueeDragTracker;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.commands.ActionHandler;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.handlers.IHandlerService;

/**
 * @author Gerhard Ebenhofer, gerhard.ebenhofer@profactor.at
 * 
 */
public class ZoomScalableFreeformRootEditPart extends
		ScalableFreeformRootEditPart {

	/** MarqueeDragTracker which deselects all elements on right click if nothing
	 *  so that the correct conext menu is shown. 
	 *  We are only here if there is no element under the cursor.
	 */
	public class AdvancedMarqueeDragTracker extends MarqueeDragTracker {
		@Override
		protected boolean handleButtonDown(int button) {
			if(3 == button) {
				//on right click deselect everything
				getViewer().setSelection(StructuredSelection.EMPTY);
			}
			return super.handleButtonDown(button);
		}
	}

	public static final String TOPLAYER = "TOPLAYER"; //$NON-NLS-1$
	private static final float[] GRID_DASHES_STYLE = new float[] { 1.0f, 5.0f };
	
	public ZoomScalableFreeformRootEditPart(IWorkbenchPartSite site, ActionRegistry actionRegistry) {
		configureZoomManger();
		setupZoomActions(site, actionRegistry);
	}
	
	@Override	
	public DragTracker getDragTracker(Request req) {
		return new AdvancedMarqueeDragTracker();
	}


	@Override
	protected LayeredPane createPrintableLayers() {
		FreeformLayeredPane layeredPane = new FreeformLayeredPane();
		layeredPane.add(new FreeformLayer(), PRIMARY_LAYER);
		ConnectionLayer connectionLayer = new ConnectionLayer();
		layeredPane.add(connectionLayer, CONNECTION_LAYER);
		
		FreeformLayer topLayer = new FreeformLayer();
		topLayer.setLayoutManager(new FreeformLayout());
		layeredPane.add(topLayer, TOPLAYER);
		return layeredPane;
	}

	@Override
	protected GridLayer createGridLayer() {
		return new GridLayer(){

			@Override
			protected void paintGrid(final Graphics g) {
				int origLineStyle = g.getLineStyle();
				g.setLineStyle(Graphics.LINE_CUSTOM);
				g.setLineDash(GRID_DASHES_STYLE);      //set the line style for all grid lines				
				super.paintGrid(g);
				g.setLineStyle(origLineStyle);
			}
			
		};
	}
	
	
	private void configureZoomManger() {
		List<String> zoomLevels = new ArrayList<>(3);
		zoomLevels.add(ZoomManager.FIT_ALL);
		zoomLevels.add(ZoomManager.FIT_WIDTH);
		zoomLevels.add(ZoomManager.FIT_HEIGHT);
		getZoomManager().setZoomLevelContributions(zoomLevels);
		
		getZoomManager().setZoomLevels(
				new double[] { .25, .5, .75, .80, .85, .90, 1.0, 1.5, 2.0, 2.5,
						3, 4 });

		// TODO __geben -> move ZoomManager.Animate_zooom_in_out to preference page
		getZoomManager().setZoomAnimationStyle(ZoomManager.ANIMATE_ZOOM_IN_OUT);		
	}

	private void setupZoomActions(IWorkbenchPartSite site, ActionRegistry actionRegistry) {
		IAction zoomIn = new ZoomInAction(getZoomManager());
		IAction zoomOut = new ZoomOutAction(getZoomManager());
		actionRegistry.registerAction(zoomIn);
		actionRegistry.registerAction(zoomOut);

		IHandlerService zoomInService = site.getService(IHandlerService.class);
		zoomInService.activateHandler(zoomIn.getActionDefinitionId(),
				new ActionHandler(zoomIn));

		IHandlerService zoomOutService = site.getService(IHandlerService.class);
		zoomOutService.activateHandler(zoomOut.getActionDefinitionId(),
				new ActionHandler(zoomOut));

		
	}
}
