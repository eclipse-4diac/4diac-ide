/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Michael Hofmann, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.commands.MoveElementsFromSubAppCommand;
import org.eclipse.fordiac.ide.application.commands.PasteCommand;
import org.eclipse.fordiac.ide.application.editparts.FBNetworkEditPart;
import org.eclipse.fordiac.ide.application.editparts.GroupContentEditPart;
import org.eclipse.fordiac.ide.application.editparts.UISubAppNetworkEditPart;
import org.eclipse.fordiac.ide.application.editparts.UnfoldedSubappContentEditPart;
import org.eclipse.fordiac.ide.gef.policies.ModifiedNonResizeableEditPolicy;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.commands.change.FBNetworkElementSetPositionCommand;
import org.eclipse.fordiac.ide.model.commands.change.RemoveElementsFromGroup;
import org.eclipse.fordiac.ide.model.commands.change.SetPositionCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateSubAppInstanceCommand;
import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

public class FBNetworkXYLayoutEditPolicy extends XYLayoutEditPolicy {

	@Override
	protected EditPolicy createChildEditPolicy(final EditPart child) {
		return new ModifiedNonResizeableEditPolicy();
	}

	@Override
	protected Command createChangeConstraintCommand(final ChangeBoundsRequest request, final EditPart child,
			final Object constraint) {
		// return a command that can move a "ViewEditPart"
		if ((child.getModel() instanceof PositionableElement) && (constraint instanceof Rectangle)) {
			if (child.getModel() instanceof FBNetworkElement) {
				return new FBNetworkElementSetPositionCommand((FBNetworkElement) child.getModel(), request,
						(Rectangle) constraint);
			}
			return new SetPositionCommand((PositionableElement) child.getModel(), request, (Rectangle) constraint);
		}
		return null;
	}

	@Override
	protected Command getCreateCommand(final CreateRequest request) {
		if (null != request) {
			final Object childClass = request.getNewObjectType();
			final Rectangle constraint = (Rectangle) getConstraintFor(request);
			if (getHost().getModel() instanceof FBNetwork) {
				final FBNetwork fbNetwork = (FBNetwork) getHost().getModel();
				if (childClass instanceof FBTypePaletteEntry) {
					final FBTypePaletteEntry type = (FBTypePaletteEntry) childClass;
					return new FBCreateCommand(type, fbNetwork, constraint.getLocation().x, constraint.getLocation().y);
				}
				if (childClass instanceof SubApplicationTypePaletteEntry) {
					final SubApplicationTypePaletteEntry type = (SubApplicationTypePaletteEntry) request
							.getNewObjectType();
					return new CreateSubAppInstanceCommand(type, fbNetwork, constraint.getLocation().x,
							constraint.getLocation().y);
				}
			}
		}
		return null;
	}

	@Override
	protected Command getAddCommand(final Request request) {
		if (isDragAndDropRequestToRoot(request, getTargetEditPart(request))) {
			return handleDragToRootRequest((ChangeBoundsRequest) request);
		}
		return null;
	}

	protected ZoomManager getZoomManager() {
		return ((ScalableFreeformRootEditPart) (getHost().getRoot())).getZoomManager();
	}

	private Command handleDragToRootRequest(final ChangeBoundsRequest request) {
		final List<EditPart> editParts = request.getEditParts();
		final Point destination = getTranslatedAndZoomedPoint(request);
		List<FBNetworkElement> fbEls = collectFromSubappDraggedFBs(editParts, getFBNetwork());
		if (!fbEls.isEmpty()) {
			return new MoveElementsFromSubAppCommand(fbEls,
					new org.eclipse.swt.graphics.Point(destination.x, destination.y));
		}
		fbEls = collectFromGroupDraggedFBs(editParts);
		if (!fbEls.isEmpty()) {
			return new RemoveElementsFromGroup(fbEls, new org.eclipse.swt.graphics.Point(destination.x, destination.y));
		}
		return null;
	}

	protected org.eclipse.draw2d.geometry.Point getTranslatedAndZoomedPoint(final ChangeBoundsRequest request) {
		final FigureCanvas viewerControl = (FigureCanvas) getTargetEditPart(request).getViewer().getControl();
		final org.eclipse.draw2d.geometry.Point location = viewerControl.getViewport().getViewLocation();
		return new org.eclipse.draw2d.geometry.Point(request.getLocation().x + location.x,
				request.getLocation().y + location.y).scale(1.0 / getZoomManager().getZoom());
	}

	private static List<FBNetworkElement> collectFromSubappDraggedFBs(final List<EditPart> editParts,
			final FBNetwork fbNetwork) {
		return editParts.stream().filter(ep -> ep.getModel() instanceof FBNetworkElement)
				.map(ep -> (FBNetworkElement) ep.getModel()).filter(FBNetworkElement::isNestedInSubApp)
				.filter(el -> !el.getFbNetwork().equals(fbNetwork))   // only take fbentworkelements that are not in the
																	   // same subapp
				.collect(Collectors.toList());
	}

	private static List<FBNetworkElement> collectFromGroupDraggedFBs(final List<EditPart> editParts) {
		return editParts.stream().filter(ep -> ep.getParent() instanceof GroupContentEditPart)
				.map(ep -> (FBNetworkElement) ep.getModel()).collect(Collectors.toList());
	}

	@Override
	protected Command getCloneCommand(final ChangeBoundsRequest request) {
		final List<EObject> elements = ((Stream<?>) (request.getEditParts()).stream())
				.map(n -> (EObject) (((EditPart) n).getModel())).collect(Collectors.toList());
		final Point scaledPoint = getDestinationPoint(request);
		return new PasteCommand(elements, (FBNetwork) getHost().getModel(), scaledPoint.x, scaledPoint.y);
	}

	private Point getDestinationPoint(final ChangeBoundsRequest request) {
		return request.getMoveDelta().getScaled(1.0 / getZoomManager().getZoom());
	}

	public static boolean isDragAndDropRequestToRoot(final Request generic, final EditPart targetEditPart) {
		return (generic instanceof ChangeBoundsRequest)
				&& ((targetEditPart instanceof FBNetworkEditPart)
						|| (targetEditPart instanceof UISubAppNetworkEditPart))
				&& !(targetEditPart instanceof UnfoldedSubappContentEditPart)
				&& !(targetEditPart instanceof GroupContentEditPart);
	}

	private FBNetwork getFBNetwork() {
		final Object model = getHost().getModel();
		return (model instanceof FBNetwork) ? (FBNetwork) model : null;
	}

}
