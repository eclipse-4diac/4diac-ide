/*******************************************************************************
 * Copyright (c) 2013, 2016 AIT, fortiss GmbH
 * 				 2018 Johannes Kepler University
 * 				 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Filip Andren, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Reworked this policy to confirm to latest model and to readd
 *                 the AddtoSubapp functionality.
 *   Michael Oberlehner, Lukas Wais
 *   	- implemented drag and drop, added move to parent
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.application.commands.AddElementsToSubAppCommand;
import org.eclipse.fordiac.ide.application.commands.MoveAndReconnectCommand;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.gef.policies.EmptyXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.gef.policies.ModifiedMoveHandle;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.requests.ChangeBoundsRequest;

/**
 * This policy creates an AddFBToSubAppCommand when user moves selected FBs over
 * a subapp. When this is possible the subapp is marked as selected.
 */
public class FBAddToSubAppLayoutEditPolicy extends EmptyXYLayoutEditPolicy {

	private Figure moveHandle;

	@Override
	protected Command getAddCommand(final Request request) {

		if (isDragAndDropRequestFromSubAppToSubApp(request, getTargetEditPart(request))) {
			final List<? extends EditPart> editParts = ((ChangeBoundsRequest) request).getEditParts();
			final SubApp dropSubApp = (SubApp) getTargetEditPart(request).getModel();
			final List<FBNetworkElement> fbEls = collectDraggedFBs(editParts, dropSubApp);
			final Point destination = getTranslatedAndZoomedPoint((ChangeBoundsRequest) request);

			if (!fbEls.isEmpty()) {
				return new MoveAndReconnectCommand(fbEls, new Point(destination.x, destination.y));
			}
			return new AddElementsToSubAppCommand(dropSubApp, editParts);
		}
		return super.getAddCommand(request);
	}

	private static List<FBNetworkElement> collectDraggedFBs(final List<? extends EditPart> editParts,
			final SubApp dropSubApp) {
		return editParts.stream().filter(ep -> ep.getModel() instanceof FBNetworkElement).map(EditPart::getModel)
				.filter(FBNetworkElement.class::isInstance).map(FBNetworkElement.class::cast)
				.filter(el -> el.isNestedInSubApp() && isChildFromDropTarget(el, dropSubApp)).toList();
	}

	public static boolean isDragAndDropRequestFromSubAppToSubApp(final Request generic, final EditPart targetEditPart) {
		return (generic instanceof ChangeBoundsRequest) && (targetEditPart instanceof SubAppForFBNetworkEditPart);
	}

	private static boolean isChildFromDropTarget(final FBNetworkElement draggedFB, final SubApp dropTarget) {
		if ((draggedFB.getOuterFBNetworkElement() == null)
				|| (draggedFB.getOuterFBNetworkElement().getOuterFBNetworkElement() == null)) {
			return false;

		}
		return draggedFB.getOuterFBNetworkElement().getOuterFBNetworkElement().equals(dropTarget);
	}

	private org.eclipse.draw2d.geometry.Point getTranslatedAndZoomedPoint(final ChangeBoundsRequest request) {
		final FigureCanvas viewerControl = (FigureCanvas) getTargetEditPart(request).getViewer().getControl();
		final org.eclipse.draw2d.geometry.Point location = viewerControl.getViewport().getViewLocation();
		return new org.eclipse.draw2d.geometry.Point(request.getLocation().x + location.x,
				request.getLocation().y + location.y).scale(1.0 / getZoomManager().getZoom());
	}

	@Override
	protected void showLayoutTargetFeedback(final Request request) {
		if (REQ_ADD.equals(request.getType()) && (null == moveHandle)) {

			moveHandle = new ModifiedMoveHandle((GraphicalEditPart) getTargetEditPart(request), new Insets(1),
					DiagramPreferences.CORNER_DIM_HALF);
			addFeedback(moveHandle);
		}
	}

	private ZoomManager getZoomManager() {
		return ((ScalableFreeformRootEditPart) (getHost().getRoot())).getZoomManager();
	}

	@Override
	protected void eraseLayoutTargetFeedback(final Request request) {
		if (moveHandle != null) {
			removeFeedback(moveHandle);
			moveHandle = null;
		}
	}

}
