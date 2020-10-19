/*******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, AIT, fortiss GmbH,
 * 				 2018 - 2020 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Matthias Plasch, Filip Andren,
 *   Monika Wenger
 *       - initial API and implementation and/or initial documentation
 *   Alois Zoitl - fixed copy/paste handling
 *               - added inplace fb instance creation
 *               - extracted FBNetworkRootEditPart from FBNetworkEditor
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import java.util.Collection;
import java.util.stream.Collectors;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.FreeformFigure;
import org.eclipse.draw2d.FreeformLayeredPane;
import org.eclipse.draw2d.FreeformListener;
import org.eclipse.draw2d.FreeformViewport;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.editors.NewInstanceDirectEditManager;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.commands.create.AbstractCreateFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateSubAppInstanceCommand;
import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.gef.tools.MarqueeDragTracker;
import org.eclipse.gef.tools.MarqueeSelectionTool;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PlatformUI;

public class FBNetworkRootEditPart extends ZoomScalableFreeformRootEditPart {

	private class FBNetworkMarqueeDragTracker extends AdvancedMarqueeDragTracker {

		@SuppressWarnings("unchecked")
		@Override
		protected Collection<Object> calculateMarqueeSelectedEditParts() {
			Collection<Object> marqueeSelectedEditParts = super.calculateMarqueeSelectedEditParts();
			// only report connections and fbelements, isMarqueeslectable can not be used
			// for that as it affects connection selection in the wrong way
			return marqueeSelectedEditParts.stream()
					.filter(ep -> (ep instanceof ConnectionEditPart) || (ep instanceof AbstractFBNElementEditPart))
					.collect(Collectors.toSet());
		}

	}

	private final FBNetwork fbNetwork;
	private final Palette palette;
	private NewInstanceDirectEditManager manager;

	public FBNetworkRootEditPart(FBNetwork fbNetwork, Palette palette, IWorkbenchPartSite site,
			ActionRegistry actionRegistry) {
		super(site, actionRegistry);
		this.fbNetwork = fbNetwork;
		this.palette = palette;
	}

	@Override
	public DragTracker getDragTracker(Request req) {
		MarqueeDragTracker dragTracker = new FBNetworkMarqueeDragTracker();
		dragTracker.setMarqueeBehavior(MarqueeSelectionTool.BEHAVIOR_NODES_CONTAINED_AND_RELATED_CONNECTIONS);
		return dragTracker;
	}

	@Override
	public void performRequest(final Request request) {
		// REQ_DIRECT_EDIT -> first select 0.4 sec pause -> click -> edit
		// REQ_OPEN -> doubleclick

		if (((request.getType() == RequestConstants.REQ_DIRECT_EDIT)
				|| (request.getType() == RequestConstants.REQ_OPEN)) && (request instanceof SelectionRequest)) {
			performDirectEdit((SelectionRequest) request);
		} else {
			super.performRequest(request);
		}
	}

	private NewInstanceDirectEditManager getManager() {
		if (null == manager) {
			manager = new NewInstanceDirectEditManager(this, palette, false);
		}
		return manager;
	}

	private void performDirectEdit(SelectionRequest request) {
		NewInstanceDirectEditManager directEditManager = getManager();
		directEditManager.updateRefPosition(new Point(request.getLocation().x, request.getLocation().y));
		if (request.getExtendedData().isEmpty()) {
			directEditManager.show();
		} else {
			Object key = request.getExtendedData().keySet().iterator().next();
			if (key instanceof String) {
				directEditManager.show((String) key);
			}
		}
	}

	@Override
	public Command getCommand(Request request) {
		if (request instanceof DirectEditRequest) {
			AbstractCreateFBNetworkElementCommand cmd = getDirectEditCommand((DirectEditRequest) request);
			if (null != cmd) {
				getViewer().getEditDomain().getCommandStack().execute(cmd);
				EditPart part = (EditPart) getViewer().getEditPartRegistry().get(cmd.getElement());
				getViewer().select(part);
			}
			return null;
		}
		return super.getCommand(request);
	}

	private AbstractCreateFBNetworkElementCommand getDirectEditCommand(DirectEditRequest request) {
		Object value = request.getCellEditor().getValue();
		Point refPoint = getInsertPos();
		if (value instanceof FBTypePaletteEntry) {
			return new FBCreateCommand((FBTypePaletteEntry) value, fbNetwork, refPoint.x, refPoint.y);
		}
		if (value instanceof SubApplicationTypePaletteEntry) {
			return new CreateSubAppInstanceCommand((SubApplicationTypePaletteEntry) value, fbNetwork, refPoint.x,
					refPoint.y);
		}
		return null;
	}

	private Point getInsertPos() {
		Point location = getManager().getLocator().getRefPoint();
		FigureCanvas figureCanvas = (FigureCanvas) getViewer().getControl();
		org.eclipse.draw2d.geometry.Point viewLocation = figureCanvas.getViewport().getViewLocation();
		location.x += viewLocation.x;
		location.y += viewLocation.y;
		org.eclipse.draw2d.geometry.Point insertPos = new org.eclipse.draw2d.geometry.Point(location.x, location.y)
				.scale(1.0 / getZoomManager().getZoom());
		return new Point(insertPos.x, insertPos.y);
	}

	@Override
	protected IFigure createFigure() {
		FreeformViewport viewPort = (FreeformViewport) super.createFigure();
		FreeformLayeredPane drawingArea = (FreeformLayeredPane) viewPort.getContents();

		BackgroundFreeformFigure editorBackground = new BackgroundFreeformFigure();
		viewPort.setContents(editorBackground);
		ModuloFreeformFigure drawingAreaContainer = new ModuloFreeformFigure(); // same size as drawingArea, resizes
		// that
		drawingAreaContainer.setBorder(new EditingAreaBorder());
		editorBackground.setContents(drawingAreaContainer);
		drawingAreaContainer.setContents(drawingArea);

		return viewPort;
	}

	public static class EditingAreaBorder extends MarginBorder {

		private static final Insets DEFAULT_INSETS = new Insets(1, 1, 1, 1);

		public EditingAreaBorder() {
			super(DEFAULT_INSETS);
		}

		@Override
		public boolean isOpaque() {
			return true;
		}

		@Override
		public void paint(IFigure figure, Graphics g, Insets insets) {
			g.setLineStyle(Graphics.LINE_SOLID);
			g.setLineWidth(1);
			Rectangle r = getPaintRectangle(figure, insets);
			r.resize(-1, -1);
			g.setForegroundColor(ColorConstants.buttonDarker);
			g.drawRectangle(r);
		}

	}

	protected class ModuloFreeformFigure extends Figure implements FreeformFigure, FreeformListener {
		private FreeformLayeredPane contents;
		private Rectangle extent;

		private static final int PADDING = 0;
		private static final int BASE_WIDTH = 400;
		private static final int BASE_HEIGHT = 200;

		ModuloFreeformFigure() {
			setOpaque(true);

			ColorRegistry colorRegistry = PlatformUI.getWorkbench().getThemeManager().getCurrentTheme()
					.getColorRegistry();

			setBackgroundColor(colorRegistry.get("org.eclipse.ui.editors.backgroundColor")); //$NON-NLS-1$

		}

		@Override
		public void addFreeformListener(FreeformListener listener) {
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
			Rectangle contentsExtent = contents.getFreeformExtent().getCopy();
			contentsExtent.scale(1.0 / getZoomManager().getZoom());
			int x = calcAxisOrigin(contentsExtent.x, BASE_WIDTH);
			int y = calcAxisOrigin(contentsExtent.y, BASE_HEIGHT);
			int width = calcAxisExtent(contentsExtent.x, x, contentsExtent.width, BASE_WIDTH);
			int height = calcAxisExtent(contentsExtent.y, y, contentsExtent.height, BASE_HEIGHT);
			contentsExtent = new Rectangle(x, y, width, height);
			contentsExtent.scale(getZoomManager().getZoom());
			return contentsExtent;
		}

		private int calcAxisExtent(int baseOrigin, int newOrigin, int sourceExtent, int baseUnit) {
			int startExtent = sourceExtent + PADDING + baseOrigin - newOrigin;

			int newExtend = (startExtent / baseUnit + 1) * baseUnit;
			if (newExtend < (3 * baseUnit)) {
				newExtend = 3 * baseUnit;
			}
			return newExtend;
		}

		private int calcAxisOrigin(int axisPos, int baseUnit) {
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
		public void removeFreeformListener(FreeformListener listener) {
			removeListener(FreeformListener.class, listener);
		}

		@Override
		public void setFreeformBounds(Rectangle bounds) {
			Rectangle r = getFreeformExtent(); // we insist on our own size calculation
			setBounds(r);
			r = r.getCopy();
			translateFromParent(r);
			contents.setFreeformBounds(r);
		}

		public void setContents(FreeformLayeredPane contents) {
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
			Display display = Display.getCurrent();
			if (null != display) {
				setBackgroundColor(display.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
			}
		}

		@Override
		public void addFreeformListener(FreeformListener listener) {
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
				FigureCanvas figureCanvas = (FigureCanvas) getViewer().getControl();
				org.eclipse.swt.graphics.Rectangle canvasBounds = figureCanvas.getBounds();
				extent.expand(canvasBounds.width * 0.9, canvasBounds.height * 0.9);
				translateToParent(extent);
			}
			return extent;
		}

		@Override
		public void removeFreeformListener(FreeformListener listener) {
			removeListener(FreeformListener.class, listener);
		}

		@Override
		public void setFreeformBounds(Rectangle bounds) {
			Rectangle newExtents = calculateBackgroundSize(bounds);
			setBounds(newExtents);
			translateFromParent(newExtents);
			contents.setFreeformBounds(newExtents); // we insist on our own size calculation
		}

		private Rectangle calculateBackgroundSize(Rectangle bounds) {
			return new Rectangle(bounds.x + ((bounds.width - extent.width) / 2),
					bounds.y + ((bounds.height - extent.height) / 2), extent.width, extent.height);
		}

		public void setContents(ModuloFreeformFigure contents) {
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