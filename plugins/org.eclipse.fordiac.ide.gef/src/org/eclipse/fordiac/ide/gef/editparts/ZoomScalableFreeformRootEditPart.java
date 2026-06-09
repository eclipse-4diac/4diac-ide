/*******************************************************************************
 * Copyright (c) 2008 Profactor GbmH, fortiss GmbH, Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Changed grid layer so that it shows every 10th and 5th line
 *                 emphasized
 *               - added autoscroll to marquee drag tracker
 *               - moved incremental growing canvas from FBNetworkRootEditPart to
 *                 here so that all graphical editors can have it
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayeredPane;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.FreeformViewport;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayeredPane;
import org.eclipse.draw2d.ScalableFigure;
import org.eclipse.draw2d.ScalableFreeformLayeredPane;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.backgrounds.shadows.RectangleDropShadowBorder;
import org.eclipse.draw2d.zoom.AbstractZoomManager;
import org.eclipse.fordiac.ide.gef.figures.AbstractFreeformFigure;
import org.eclipse.fordiac.ide.gef.figures.BackgroundFreeformFigure;
import org.eclipse.fordiac.ide.gef.figures.ModuloFreeformFigure;
import org.eclipse.fordiac.ide.gef.tools.AdvancedMarqueeDragTracker;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.GridLayer;
import org.eclipse.gef.editparts.GuideLayer;
import org.eclipse.gef.editparts.HierarchicalGridLayer;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.commands.ActionHandler;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;

public class ZoomScalableFreeformRootEditPart extends ScalableFreeformRootEditPart {

	public static final String TOP_LAYER = "TOPLAYER"; //$NON-NLS-1$

	public ZoomScalableFreeformRootEditPart(final IWorkbenchPartSite site, final ActionRegistry actionRegistry) {
		configureZoomManger();
		setupZoomActions(site, actionRegistry);
	}

	@Override
	public DragTracker getDragTracker(final Request req) {
		return new AdvancedMarqueeDragTracker();
	}

	@Override
	protected LayeredPane createPrintableLayers() {
		final FreeformLayeredPane layeredPane = new FreeformLayeredPane();
		layeredPane.add(new FreeformLayer(), PRIMARY_LAYER);
		final ConnectionLayer connectionLayer = new ConnectionLayer();
		layeredPane.add(connectionLayer, CONNECTION_LAYER);

		final FreeformLayer topLayer = new FreeformLayer();
		topLayer.setLayoutManager(new FreeformLayout());
		layeredPane.add(topLayer, TOP_LAYER);
		return layeredPane;
	}

	@Override
	protected GridLayer createGridLayer() {
		return new HierarchicalGridLayer();
	}

	// Duplicated and adjusted this method from base class to allow moving the
	// handle_layer and feedback_layer to scaled layers for correct zooming
	@Override
	protected void createLayers(final LayeredPane layeredPane) {
		layeredPane.add(getScaledLayers(), SCALABLE_LAYERS);
		layeredPane.add(new GuideLayer(), GUIDE_LAYER);
	}

	private static class FeedbackLayer extends FreeformLayer {
		FeedbackLayer() {
			setEnabled(false);
		}
	}

	@Override
	protected ScalableFreeformLayeredPane createScaledLayers() {
		final ScalableFreeformLayeredPane pane = super.createScaledLayers();
		pane.add(new FreeformLayer(), HANDLE_LAYER);
		pane.add(new FeedbackLayer(), FEEDBACK_LAYER);
		return pane;
	}

	private void configureZoomManger() {
		final List<String> zoomLevels = new ArrayList<>(3);
		zoomLevels.add(ZoomManager.FIT_ALL);
		zoomLevels.add(ZoomManager.FIT_WIDTH);
		zoomLevels.add(ZoomManager.FIT_HEIGHT);
		getZoomManager().setZoomLevelContributions(zoomLevels);
		getZoomManager().setZoomLevels(
				new double[] { .25, .45, .5, .55, .6, .65, .75, .80, .85, .90, .95, 1.0, 1.25, 1.5, 1.75, 2.0 });
		getZoomManager().setZoomAnimationStyle(AbstractZoomManager.ANIMATE_ZOOM_IN_OUT);
	}

	private void setupZoomActions(final IWorkbenchPartSite site, final ActionRegistry actionRegistry) {
		final IAction zoomIn = new ZoomInAction(getZoomManager());
		final IAction zoomOut = new ZoomOutAction(getZoomManager());
		actionRegistry.registerAction(zoomIn);
		actionRegistry.registerAction(zoomOut);

		final IHandlerService zoomInService = site.getService(IHandlerService.class);
		zoomInService.activateHandler(zoomIn.getActionDefinitionId(), new ActionHandler(zoomIn));

		final IHandlerService zoomOutService = site.getService(IHandlerService.class);
		zoomOutService.activateHandler(zoomOut.getActionDefinitionId(), new ActionHandler(zoomOut));

	}

	@Override
	protected ZoomManager createZoomManager(final ScalableFigure scalableFigure, final Viewport viewport) {
		return new AdvancedZoomManager(scalableFigure, viewport);
	}

	@Override
	protected IFigure createFigure() {
		final FreeformViewport viewPort = (FreeformViewport) super.createFigure();
		final FreeformLayeredPane drawingArea = (FreeformLayeredPane) viewPort.getContents();

		final AbstractFreeformFigure editorBackground = new BackgroundFreeformFigure(this);
		viewPort.setContents(editorBackground);
		final AbstractFreeformFigure drawingAreaContainer = createDrawingAreaContainer();
		configureDrawingContainer(drawingArea, drawingAreaContainer);
		editorBackground.setContents(drawingAreaContainer);

		return viewPort;
	}

	protected AbstractFreeformFigure createDrawingAreaContainer() {
		return new ModuloFreeformFigure(this);
	}

	private static void configureDrawingContainer(final FreeformLayeredPane drawingArea,
			final AbstractFreeformFigure drawingAreaContainer) {
		drawingAreaContainer.setOpaque(true);
		drawingAreaContainer.setBackgroundColor(getDrawingAreaBGColor());
		drawingAreaContainer.setBorder(createDrawingContainerShadowBorder());
		drawingAreaContainer.setContents(drawingArea);
	}

	private static RectangleDropShadowBorder createDrawingContainerShadowBorder() {
		final RectangleDropShadowBorder border = new RectangleDropShadowBorder();
		border.setHaloSize(8);
		border.setDropShadowSize(12);
		return border;
	}

	private static Color getDrawingAreaBGColor() {
		final String background = Platform.getPreferencesService().getString("org.eclipse.ui.editors", //$NON-NLS-1$
				"AbstractTextEditor.Color.Background", null, null); //$NON-NLS-1$

		if (background != null) {
			// we have a color in the preferences set
			final RGB rgb = StringConverter.asRGB(background, null);
			return new Color(rgb);
		}

		// if not in the preferences try to get it from the current theme
		final ColorRegistry colorRegistry = PlatformUI.getWorkbench().getThemeManager().getCurrentTheme()
				.getColorRegistry();
		return colorRegistry.get("org.eclipse.ui.editors.backgroundColor"); //$NON-NLS-1$
	}

}
