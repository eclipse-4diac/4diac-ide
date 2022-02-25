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

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.fordiac.ide.application.editors.NewInstanceDirectEditManager;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.commands.create.AbstractCreateFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateSubAppInstanceCommand;
import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.requests.LocationRequest;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.gef.tools.MarqueeSelectionTool;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IWorkbenchPartSite;

public class FBNetworkRootEditPart extends ZoomScalableFreeformRootEditPart {

	public static class FBNetworkMarqueeDragTracker extends AdvancedMarqueeDragTracker {

		@Override
		protected Collection<EditPart> calculateMarqueeSelectedEditParts() {
			final Collection<EditPart> marqueeSelectedEditParts = super.calculateMarqueeSelectedEditParts();
			// only report connections and fbelements, isMarqueeslectable can not be used
			// for that as it affects connection selection in the wrong way
			return marqueeSelectedEditParts.stream()
					.filter(ep -> (ep instanceof ConnectionEditPart && ep.isSelectable())
							|| (ep.getModel() instanceof FBNetworkElement))
					.collect(Collectors.toSet());
		}

	}

	private final FBNetwork fbNetwork;
	private final Palette palette;

	public FBNetworkRootEditPart(final FBNetwork fbNetwork, final Palette palette, final IWorkbenchPartSite site,
			final ActionRegistry actionRegistry) {
		super(site, actionRegistry);
		this.fbNetwork = fbNetwork;
		this.palette = palette;
	}

	@Override
	public DragTracker getDragTracker(final Request req) {
		final FBNetworkMarqueeDragTracker dragTracker = new FBNetworkMarqueeDragTracker();
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

	private NewInstanceDirectEditManager createDirectEditManager() {
		return new NewInstanceDirectEditManager(this, palette, false);
	}

	void performDirectEdit(final SelectionRequest request) {
		final NewInstanceDirectEditManager directEditManager = createDirectEditManager();
		directEditManager.updateRefPosition(new Point(request.getLocation().x, request.getLocation().y));
		if (request.getExtendedData().isEmpty()) {
			directEditManager.show();
		} else {
			final Object key = request.getExtendedData().keySet().iterator().next();
			if (key instanceof String) {
				directEditManager.show((String) key);
			}
		}
	}

	@Override
	public Command getCommand(final Request request) {
		if (request instanceof DirectEditRequest) {
			final AbstractCreateFBNetworkElementCommand cmd = getDirectEditCommand((DirectEditRequest) request);
			if (null != cmd && cmd.canExecute()) {
				getViewer().getEditDomain().getCommandStack().execute(cmd);
				final EditPart part = (EditPart) getViewer().getEditPartRegistry().get(cmd.getElement());
				getViewer().select(part);
			}
			return null;
		}
		return super.getCommand(request);
	}

	private AbstractCreateFBNetworkElementCommand getDirectEditCommand(final DirectEditRequest request) {
		final Object value = request.getCellEditor().getValue();
		final Point refPoint = getInsertPos(request, getViewer(), getZoomManager().getZoom());
		if (value instanceof FBTypePaletteEntry) {
			return new FBCreateCommand((FBTypePaletteEntry) value, fbNetwork, refPoint.x, refPoint.y);
		}
		if (value instanceof SubApplicationTypePaletteEntry) {
			return new CreateSubAppInstanceCommand((SubApplicationTypePaletteEntry) value, fbNetwork, refPoint.x,
					refPoint.y);
		}
		return null;
	}

	public static Point getInsertPos(final LocationRequest request, final EditPartViewer viewer,
			final double zoom) {
		final org.eclipse.draw2d.geometry.Point location = request.getLocation();
		final FigureCanvas figureCanvas = (FigureCanvas) viewer.getControl();
		final org.eclipse.draw2d.geometry.Point viewLocation = figureCanvas.getViewport().getViewLocation();
		location.x += viewLocation.x;
		location.y += viewLocation.y;
		final org.eclipse.draw2d.geometry.Point insertPos = new org.eclipse.draw2d.geometry.Point(location.x, location.y)
				.scale(1.0 / zoom);
		return new Point(insertPos.x, insertPos.y);
	}


}