/*******************************************************************************
 * Copyright (c) 2008, 2021 Profactor GbmH, fortiss GmbH, Johannes Kepler University
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

import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.FreeformFigure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayeredPane;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.FreeformListener;
import org.eclipse.draw2d.FreeformViewport;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayeredPane;
import org.eclipse.draw2d.ScalableFigure;
import org.eclipse.draw2d.ScalableFreeformLayeredPane;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.gef.draw2d.SingleLineBorder;
import org.eclipse.fordiac.ide.model.ui.editors.AdvancedScrollingGraphicalViewer;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.FreeformGraphicalRootEditPart;
import org.eclipse.gef.editparts.GridLayer;
import org.eclipse.gef.editparts.GuideLayer;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.gef.tools.MarqueeDragTracker;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.commands.ActionHandler;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;

public class ZoomScalableFreeformRootEditPart extends ScalableFreeformRootEditPart {

	/**
	 * Grid layer that draws the grid in dashed lines and every X line solid to give
	 * the grid more structure
	 */
	private static class MajorMinorGridLayer extends GridLayer {
		private static final double MIN_ABSOLUTE_INTERLEAVE = 5.0;

		private static final int MAJOR_INTERLEAVE = 10; // draw each 10th line thicker dashed to give the grid more
		// structure
		private static final int MEDIUM_INTERLEAVE = 5; // draw each 5th line medium dashed to give the grid more
		// structure

		private static final float[] GRID_MINOR_DASHES_STYLE = new float[] { 1.0f, 5.0f };
		private static final float[] GRID_MEDIUM_DASHES_STYLE = new float[] { 2.0f, 4.0f };
		private static final float[] GRID_MAJOR_DASHES_STYLE = new float[] { 4.0f, 2.0f };

		@Override
		protected void paintGrid(final Graphics g) {
			final int origLineStyle = g.getLineStyle();
			g.setLineStyle(Graphics.LINE_CUSTOM);

			final Rectangle clip = g.getClip(Rectangle.SINGLETON);

			if (gridX > 0) {
				drawVerLines(g, clip);
			}

			if (gridY > 0) {
				drawHorLines(g, clip);
			}
			g.setLineStyle(origLineStyle);
		}

		private void drawVerLines(final Graphics g, final Rectangle clip) {
			final int majorInterleaveX = gridX * MAJOR_INTERLEAVE;
			final int medInterleaveX = gridX * MEDIUM_INTERLEAVE;
			final int realInterleaveX = determineInterleave(gridX, medInterleaveX, majorInterleaveX, g.getAbsoluteScale());

			if (realInterleaveX > 0) {
				for (int i = getLineStart(origin.x, clip.x, realInterleaveX); i < clip.x
						+ clip.width; i += realInterleaveX) {
					setLineStyle(g, i, origin.x, majorInterleaveX, medInterleaveX);
					g.drawLine(i, clip.y, i, clip.y + clip.height);
				}
			}
		}

		private void drawHorLines(final Graphics g, final Rectangle clip) {
			final int majorInterleaveY = gridY * MAJOR_INTERLEAVE;
			final int medInterleaveY = gridY * MEDIUM_INTERLEAVE;
			final int realInterleaveY = determineInterleave(gridY, medInterleaveY, majorInterleaveY, g.getAbsoluteScale());
			if (realInterleaveY > 0) {
				for (int i = getLineStart(origin.y, clip.y, realInterleaveY); i < clip.y
						+ clip.height; i += realInterleaveY) {
					setLineStyle(g, i, origin.y, majorInterleaveY, medInterleaveY);
					g.drawLine(clip.x, i, clip.x + clip.width, i);
				}
			}
		}

		private static int determineInterleave(final int interleave, final int medInterleave, final int majorInterleave,
				final double absoluteScale) {
			if (interleave * absoluteScale > MIN_ABSOLUTE_INTERLEAVE) {
				return interleave;
			}

			if (medInterleave * absoluteScale > MIN_ABSOLUTE_INTERLEAVE) {
				return medInterleave;
			}

			if (majorInterleave * absoluteScale > MIN_ABSOLUTE_INTERLEAVE) {
				return majorInterleave;
			}
			return -1;
		}

		private static int getLineStart(final int origin, final int clip, final int distance) {
			int newOrigin = origin;
			if (origin >= clip) {
				while (newOrigin - distance >= clip) {
					newOrigin -= distance;
				}
			} else {
				while (newOrigin < clip) {
					newOrigin += distance;
				}
			}
			return newOrigin;
		}

		private static void setLineStyle(final Graphics g, final int currLinePos, final int origin,
				final int majorInterleave, final int mediumInterleave) {
			final int delta = origin - currLinePos;
			if (0 == (delta % majorInterleave)) {
				g.setLineDash(GRID_MAJOR_DASHES_STYLE);
			} else if (0 == (delta % mediumInterleave)) {
				g.setLineDash(GRID_MEDIUM_DASHES_STYLE);
			} else {
				g.setLineDash(GRID_MINOR_DASHES_STYLE);
			}
		}

	}

	private static final Request MARQUEE_REQUEST = new Request(RequestConstants.REQ_SELECTION);

	/**
	 * MarqueeDragTracker which deselects all elements on right click if nothing so
	 * that the correct conext menu is shown. We are only here if there is no
	 * element under the cursor.
	 *
	 * Furthermore it performs autoscrolling if the user went beyond the viewport
	 * boundaries.
	 */
	public class AdvancedMarqueeDragTracker extends MarqueeDragTracker {

		@Override
		protected boolean handleButtonDown(final int button) {
			if (3 == button) {
				// on right click deselect everything
				getViewer().setSelection(StructuredSelection.EMPTY);
			}
			return super.handleButtonDown(button);
		}

		@Override
		public void mouseDown(final MouseEvent me, final EditPartViewer viewer) {
			if (viewer instanceof AdvancedScrollingGraphicalViewer) {
				bindToContentPane(me, (AdvancedScrollingGraphicalViewer) viewer);
			}
			super.mouseDown(me, viewer);
		}

		@Override
		public void mouseDrag(final MouseEvent me, final EditPartViewer viewer) {
			if (isActive() && viewer instanceof AdvancedScrollingGraphicalViewer) {
				final Point oldViewPort = ((AdvancedScrollingGraphicalViewer) viewer).getViewLocation();
				((AdvancedScrollingGraphicalViewer) viewer).checkScrollPositionDuringDrag(me);
				final Dimension delta = oldViewPort
						.getDifference(((AdvancedScrollingGraphicalViewer) viewer).getViewLocation());
				// Compensate the moved scrolling in the start position for correct dropping of
				// moved parts
				setStartLocation(getStartLocation().getTranslated(delta));
				bindToContentPane(me, (AdvancedScrollingGraphicalViewer) viewer);
			}
			super.mouseDrag(me, viewer);
		}

		protected void bindToContentPane(final MouseEvent me, final AdvancedScrollingGraphicalViewer viewer) {
			final Rectangle contentPaneBounds = viewer.translateBoundsToRoute(
					((FreeformGraphicalRootEditPart) viewer.getRootEditPart()).getContentPane());

			final org.eclipse.draw2d.geometry.Point viewLocation = viewer.getViewLocation();
			me.x += viewLocation.x;
			me.y += viewLocation.y;

			me.x = Math.max(me.x, contentPaneBounds.getTopLeft().x);
			me.y = Math.max(me.y, contentPaneBounds.getTopLeft().y);

			me.x = Math.min(me.x, contentPaneBounds.getBottomRight().x - 1);
			me.y = Math.min(me.y, contentPaneBounds.getBottomRight().y - 1);
			me.x -= viewLocation.x;
			me.y -= viewLocation.y;
		}

		@Override
		protected boolean handleDoubleClick(final int button) {
			if (1 == button) {
				performOpen();
			}
			return true;
		}

		protected void performOpen() {
			final EditPart editPart = getCurrentViewer().findObjectAt(getLocation());
			if (null != editPart) {
				final SelectionRequest request = new SelectionRequest();
				request.setLocation(getLocation());
				request.setType(RequestConstants.REQ_OPEN);
				editPart.performRequest(request);
			}
		}

		// In the base class version not shown elements can not be selected, as we have now auto-scrolling this is not a
		// good behavior therefore this overridden version. For details see base class.
		@Override
		protected boolean isMarqueeSelectable(final GraphicalEditPart editPart) {
			return editPart.getTargetEditPart(MARQUEE_REQUEST) == editPart && editPart.isSelectable();
		}
	}

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
		return new MajorMinorGridLayer();
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
		getZoomManager().setZoomLevels(new double[] { .25, .5, .75, .80, .85, .90, 1.0, 1.5, 2.0, 2.5, 3, 4 });
		getZoomManager().setZoomAnimationStyle(ZoomManager.ANIMATE_ZOOM_IN_OUT);
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

		final BackgroundFreeformFigure editorBackground = new BackgroundFreeformFigure();
		viewPort.setContents(editorBackground);
		final ModuloFreeformFigure drawingAreaContainer = new ModuloFreeformFigure(); // same size as drawingArea,
		// resizes that
		drawingAreaContainer.setBorder(new SingleLineBorder());
		editorBackground.setContents(drawingAreaContainer);
		drawingAreaContainer.setContents(drawingArea);

		return viewPort;
	}

	protected class ModuloFreeformFigure extends Figure implements FreeformFigure, FreeformListener {
		private FreeformLayeredPane contents;
		private Rectangle extent;

		private static final int PADDING = 0;
		private static final int BASE_WIDTH = 400;
		private static final int BASE_HEIGHT = 200;

		ModuloFreeformFigure() {
			setOpaque(true);

			final ColorRegistry colorRegistry = PlatformUI.getWorkbench().getThemeManager().getCurrentTheme()
					.getColorRegistry();

			setBackgroundColor(colorRegistry.get("org.eclipse.ui.editors.backgroundColor")); //$NON-NLS-1$

		}

		@Override
		public void addFreeformListener(final FreeformListener listener) {
			addListener(FreeformListener.class, listener);
		}

		@Override
		public void fireExtentChanged() {
			performRevalidation();
		}

		@Override
		public void notifyFreeformExtentChanged() {
			performRevalidation();
		}

		@Override
		public Rectangle getFreeformExtent() {
			if (extent == null) {
				extent = calculateModuloExtent();
				translateToParent(extent);
			}
			return extent;
		}

		private Rectangle calculateModuloExtent() { // adjust size to be a multiple of the base width/height
			Rectangle contentsExtent = getUnscaledContentsExtent();
			contentsExtent.shrink(getInsets());  // take any border into our calculation
			final int x = calcAxisOrigin(contentsExtent.x, BASE_WIDTH);
			final int y = calcAxisOrigin(contentsExtent.y, BASE_HEIGHT);
			final int width = calcAxisExtent(contentsExtent.x, x, contentsExtent.width, BASE_WIDTH);
			final int height = calcAxisExtent(contentsExtent.y, y, contentsExtent.height, BASE_HEIGHT);
			contentsExtent = new Rectangle(x, y, width, height);
			contentsExtent.scale(getZoomManager().getZoom());
			return contentsExtent;
		}

		private Rectangle getUnscaledContentsExtent() {
			final Rectangle contentsExtent = ((FreeformFigure) getContentPane()).getFreeformExtent().getCopy();
			// add handle and feedback layer so that dragging elements result in growing the modulo extend
			contentsExtent.union(((FreeformFigure) getLayer(HANDLE_LAYER)).getFreeformExtent());
			contentsExtent.union(((FreeformFigure) getLayer(FEEDBACK_LAYER)).getFreeformExtent());
			return contentsExtent;
		}

		private int calcAxisExtent(final int baseOrigin, final int newOrigin, final int sourceExtent,
				final int baseUnit) {
			final int startExtent = sourceExtent + PADDING + baseOrigin - newOrigin;

			int newExtend = (startExtent / baseUnit + 1) * baseUnit;
			if (newExtend < (3 * baseUnit)) {
				newExtend = 3 * baseUnit;
			}
			return newExtend;
		}

		private int calcAxisOrigin(final int axisPos, final int baseUnit) {
			if (axisPos < 0) {
				// when negative we need to go one beyond to have the correct origin
				return (axisPos / baseUnit - 1) * baseUnit;
			}
			return (axisPos / baseUnit) * baseUnit;
		}

		private void performRevalidation() { // see invalidate() from FreeformHelper
			extent = null;
			if (getParent() != null) {
				((BackgroundFreeformFigure) getParent()).performRevalidation();
			} else {
				revalidate();
			}
		}

		@Override
		public void removeFreeformListener(final FreeformListener listener) {
			removeListener(FreeformListener.class, listener);
		}

		@Override
		public void setFreeformBounds(final Rectangle bounds) {
			Rectangle r = getFreeformExtent(); // we insist on our own size calculation
			setBounds(r);
			r = r.getCopy();
			translateFromParent(r);
			contents.setFreeformBounds(r);
		}

		public void setContents(final FreeformLayeredPane contents) {
			this.contents = contents;
			add(contents);
			contents.addFreeformListener(this);
		}
	}

	protected class BackgroundFreeformFigure extends Figure implements FreeformFigure, FreeformListener {
		private ModuloFreeformFigure contents;
		private Rectangle extent;

		public BackgroundFreeformFigure() {
			setOpaque(true);
			final Display display = Display.getCurrent();
			if (null != display) {
				setBackgroundColor(display.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
			}
		}

		@Override
		public void addFreeformListener(final FreeformListener listener) {
			addListener(FreeformListener.class, listener);
		}

		@Override
		public void fireExtentChanged() {
			getListeners(FreeformListener.class)
			.forEachRemaining(listener -> ((FreeformListener) listener).notifyFreeformExtentChanged());
			performRevalidation();
		}

		@Override
		public void notifyFreeformExtentChanged() {
			performRevalidation();
		}

		@Override
		public Rectangle getFreeformExtent() {
			if (extent == null) {
				extent = contents.getFreeformExtent().getCopy();
				final FigureCanvas figureCanvas = (FigureCanvas) getViewer().getControl();
				final org.eclipse.swt.graphics.Rectangle canvasBounds = figureCanvas.getBounds();
				extent.expand(canvasBounds.width * 0.9, canvasBounds.height * 0.9);
				translateToParent(extent);
			}
			return extent;
		}

		@Override
		public void removeFreeformListener(final FreeformListener listener) {
			removeListener(FreeformListener.class, listener);
		}

		@Override
		public void setFreeformBounds(final Rectangle bounds) {
			final Rectangle newExtents = calculateBackgroundSize(bounds);
			setBounds(newExtents);
			translateFromParent(newExtents);
			contents.setFreeformBounds(newExtents); // we insist on our own size calculation
		}

		private Rectangle calculateBackgroundSize(final Rectangle bounds) {
			return new Rectangle(bounds.x + ((bounds.width - extent.width) / 2),
					bounds.y + ((bounds.height - extent.height) / 2), extent.width, extent.height);
		}

		public void setContents(final ModuloFreeformFigure contents) {
			this.contents = contents;
			add(contents);
			contents.addFreeformListener(this);
		}

		private void performRevalidation() { // see invalidate() from FreeformHelper
			extent = null;
			if (getParent() != null) {
				getParent().revalidate();
			} else {
				revalidate();
			}
		}
	}
}
