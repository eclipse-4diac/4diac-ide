/*******************************************************************************
 * Copyright (c) 2008, 2026 Profactor GmbH, fortiss GmbH,
 *                          Primetals Technologies Austria GmbH
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
 *   Alois Zoitl - implemented group resizing
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import java.util.List;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.actions.CopyPasteData;
import org.eclipse.fordiac.ide.application.commands.ConnectionReference;
import org.eclipse.fordiac.ide.application.commands.MoveAndReconnectCommand;
import org.eclipse.fordiac.ide.application.commands.PasteCommand;
import org.eclipse.fordiac.ide.application.editparts.EditorWithInterfaceEditPart;
import org.eclipse.fordiac.ide.application.editparts.FBNetworkEditPart;
import org.eclipse.fordiac.ide.application.editparts.GroupContentEditPart;
import org.eclipse.fordiac.ide.application.editparts.UnfoldedSubappContentEditPart;
import org.eclipse.fordiac.ide.gef.policies.ModifiedNonResizeableEditPolicy;
import org.eclipse.fordiac.ide.gef.policies.ModifiedResizeablePolicy;
import org.eclipse.fordiac.ide.gef.utilities.RequestUtil;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.commands.change.AbstractChangeContainerBoundsCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentBoundsCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeGroupBoundsCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeSubAppBoundsCommand;
import org.eclipse.fordiac.ide.model.commands.change.FBNetworkElementSetPositionCommand;
import org.eclipse.fordiac.ide.model.commands.change.RemoveElementsFromGroup;
import org.eclipse.fordiac.ide.model.commands.change.SetPositionCommand;
import org.eclipse.fordiac.ide.model.commands.create.AbstractCreateFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Comment;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

public class FBNetworkXYLayoutEditPolicy extends XYLayoutEditPolicy {

	@Override
	protected EditPolicy createChildEditPolicy(final EditPart child) {
		final Object model = child.getModel();
		if ((model instanceof Group) || (model instanceof final SubApp subApp && subApp.isUnfolded())) {
			return new ContainerResizePolicy();
		}

		if (model instanceof Comment) {
			return new ModifiedResizeablePolicy();
		}

		if (model instanceof FBNetworkElement) {
			return new FBNetworkElementNonResizeableEP();
		}

		return new ModifiedNonResizeableEditPolicy();
	}

	@Override
	protected Command createChangeConstraintCommand(final ChangeBoundsRequest request, final EditPart child,
			final Object constraint) {
		if ((child.getModel() instanceof Group || child.getModel() instanceof SubApp
				|| child.getModel() instanceof Comment) && RequestUtil.isResizeRequest(request)) {
			return createChangeSizeCommand((FBNetworkElement) child.getModel(), request, (Rectangle) constraint);
		}
		if ((child.getModel() instanceof final PositionableElement pe) && (RequestUtil.isMoveRequest(request))) {
			return createMoveCommand(pe, (Rectangle) constraint);
		}
		return null;
	}

	private Command createChangeSizeCommand(final FBNetworkElement container, final ChangeBoundsRequest request,
			final Rectangle constraint) {
		final Dimension sizeDelta = getScaledSizeDelta(request);
		if (sizeDelta.width == 0 && sizeDelta.height == 0) {
			// we hit the min size and we are just moving, return a set position command
			return createMoveCommand(container, constraint);
		}
		final Point moveDelta = getScaledMoveDelta(request);
		return createChangeBoundsCommand(container, sizeDelta, moveDelta);
	}

	public static AbstractChangeContainerBoundsCommand createChangeBoundsCommand(final FBNetworkElement container,
			final Dimension sizeDelta, final Point moveDelta) {
		if (container instanceof final Group group) {
			return new ChangeGroupBoundsCommand(group, moveDelta.x, moveDelta.y, sizeDelta.width, sizeDelta.height);
		}
		if (container instanceof final SubApp subApp) {
			return new ChangeSubAppBoundsCommand(subApp, moveDelta.x, moveDelta.y, sizeDelta.width, sizeDelta.height);
		}
		if (container instanceof final Comment comment) {
			return new ChangeCommentBoundsCommand(comment, moveDelta.x, moveDelta.y, sizeDelta.width, sizeDelta.height);
		}
		return null;
	}

	@Override
	protected Command getCreateCommand(final CreateRequest request) {
		if (null != request) {
			final Object childClass = request.getNewObjectType();
			final Point insertPoint = getInsertPoint(request);
			final FBNetwork fbNetwork = getFBNetwork();
			if ((fbNetwork != null) && (childClass instanceof final TypeEntry typeEntry)) {
				return AbstractCreateFBNetworkElementCommand.createCreateCommand(typeEntry, fbNetwork, insertPoint.x,
						insertPoint.y);
			}
		}
		return null;
	}

	protected Point getInsertPoint(final CreateRequest request) {
		final Point insertPoint = ((Rectangle) getConstraintFor(request)).getTopLeft();
		final SnapToHelper helper = getHost().getAdapter(SnapToHelper.class);
		if (helper != null) {
			getHost().getFigure().translateToAbsolute(insertPoint);
			final PrecisionPoint preciseLocation = new PrecisionPoint(insertPoint);
			final PrecisionPoint result = new PrecisionPoint(insertPoint);
			helper.snapPoint(null, PositionConstants.HORIZONTAL | PositionConstants.VERTICAL, preciseLocation, result);
			getHost().getFigure().translateToRelative(result);
			return result;
		}
		return insertPoint;
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
		final List<? extends EditPart> editParts = request.getEditParts();
		final Point destination = getTranslatedAndZoomedPoint(request);
		final List<FBNetworkElement> fbEls = collectDraggedFBs(editParts, getFBNetwork());
		if (!fbEls.isEmpty()) {
			return new MoveAndReconnectCommand(fbEls, destination, (FBNetwork) getHost().getModel());
		}
		final GroupContentEditPart groupContent = getGroupContentEditPart(editParts);
		if (groupContent != null) {
			return createRemoveFromGroup(groupContent, request);
		}
		return createSplitEditorMoveCommand(request);
	}

	private Command createRemoveFromGroup(final GroupContentEditPart groupContent, final ChangeBoundsRequest request) {
		final List<FBNetworkElement> fbEls = collectFromGroupDraggedFBs(request.getEditParts());
		if (!fbEls.isEmpty()) {
			final Point topLeft = groupContent.getFigure().getBounds().getTopLeft();
			final Point moveDelta = getScaledMoveDelta(request);
			topLeft.translate(moveDelta.x, moveDelta.y);
			return new RemoveElementsFromGroup(fbEls, topLeft);
		}
		return null;
	}

	private Command createSplitEditorMoveCommand(final ChangeBoundsRequest request) {
		final List<FBNetworkElement> movedElements = request.getEditParts().stream().filter(
				ep -> ep.getModel() instanceof final FBNetworkElement fbnel && fbnel.eContainer() == getFBNetwork())
				.map(ep -> (FBNetworkElement) ep.getModel()).toList();

		if (movedElements.isEmpty()) {
			return null;
		}

		final Position topLeftCornerOfFBNetwork = FBNetworkHelper.getTopLeftCornerOfFBNetwork(movedElements);
		final Point targetPos = getTranslatedAndZoomedPoint(request);
		final Position iec61499TargetPos = CoordinateConverter.INSTANCE.createPosFromScreenCoordinates(targetPos.x,
				targetPos.y);

		final CompoundCommand cmd = new CompoundCommand();
		movedElements.forEach(fbnEl -> {
			final Position newPos = LibraryElementFactory.eINSTANCE.createPosition();
			newPos.setX(iec61499TargetPos.getX() + fbnEl.getPosition().getX() - topLeftCornerOfFBNetwork.getX());
			newPos.setY(iec61499TargetPos.getY() + fbnEl.getPosition().getY() - topLeftCornerOfFBNetwork.getY());
			cmd.add(new FBNetworkElementSetPositionCommand(fbnEl, newPos));
		});
		return cmd;
	}

	protected Point getTranslatedAndZoomedPoint(final ChangeBoundsRequest request) {
		final Point location = request.getLocation().getCopy();
		getHost().getFigure().translateToRelative(location);
		return location;
	}

	private static List<FBNetworkElement> collectDraggedFBs(final List<? extends EditPart> editParts,
			final FBNetwork fbNetwork) {
		return editParts.stream().filter(ep -> ep.getModel() instanceof FBNetworkElement)
				.map(ep -> (FBNetworkElement) ep.getModel()).filter(el -> !el.getFbNetwork().equals(fbNetwork))
				// only take fbentworkelements that are not in the same subapp
				.toList();
	}

	private static GroupContentEditPart getGroupContentEditPart(final List<? extends EditPart> editParts) {
		return (GroupContentEditPart) editParts.stream().filter(ep -> ep.getParent() instanceof GroupContentEditPart)
				.map(EditPart::getParent).findFirst().orElse(null);
	}

	private static List<FBNetworkElement> collectFromGroupDraggedFBs(final List<? extends EditPart> editParts) {
		return editParts.stream().filter(ep -> ep.getParent() instanceof GroupContentEditPart)
				.map(ep -> (FBNetworkElement) ep.getModel()).toList();
	}

	@Override
	protected Command getCloneCommand(final ChangeBoundsRequest request) {
		final CopyPasteData copyPasteData = new CopyPasteData(getFBNetwork());
		request.getEditParts().stream().map(n -> (EObject) (n.getModel())).forEach(m -> {
			if (m instanceof final FBNetworkElement el) {
				copyPasteData.elements().add(el);
			}
			if (m instanceof final Connection conn) {
				copyPasteData.conns().add(new ConnectionReference(conn));
			}
		});
		final Point scaledPoint = getDestinationPoint(request);
		return new PasteCommand(copyPasteData, getFBNetwork(), scaledPoint.x, scaledPoint.y);
	}

	private Point getDestinationPoint(final ChangeBoundsRequest request) {
		return getScaledMoveDelta(request);
	}

	public static boolean isDragAndDropRequestToRoot(final Request generic, final EditPart targetEditPart) {
		return (generic instanceof ChangeBoundsRequest)
				&& ((targetEditPart instanceof FBNetworkEditPart)
						|| (targetEditPart instanceof EditorWithInterfaceEditPart))
				&& !(targetEditPart instanceof UnfoldedSubappContentEditPart)
				&& !(targetEditPart instanceof GroupContentEditPart);
	}

	private FBNetwork getFBNetwork() {
		return (getHost().getModel() instanceof final FBNetwork fbNetwork) ? fbNetwork : null;
	}

	private static Command createMoveCommand(final PositionableElement model, final Rectangle constraint) {
		final Position newPos = CoordinateConverter.INSTANCE.createPosFromScreenCoordinates(constraint.x, constraint.y);
		if (model instanceof final FBNetworkElement fbnEl) {
			return new FBNetworkElementSetPositionCommand(fbnEl, newPos);
		}
		return new SetPositionCommand(model, newPos);
	}

	protected Dimension getScaledSizeDelta(final ChangeBoundsRequest request) {
		return request.getSizeDelta().getScaled(1.0 / getZoomManager().getZoom());
	}

	protected Point getScaledMoveDelta(final ChangeBoundsRequest request) {
		final Point moveDelta = request.getMoveDelta().getCopy();
		getHost().getFigure().translateToRelative(moveDelta);
		return moveDelta;
	}

}
