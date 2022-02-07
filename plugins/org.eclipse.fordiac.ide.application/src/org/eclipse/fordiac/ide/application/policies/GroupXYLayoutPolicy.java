/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.editparts.GroupContentEditPart;
import org.eclipse.fordiac.ide.application.editparts.GroupContentNetwork;
import org.eclipse.fordiac.ide.application.handlers.TrimGroupHandler;
import org.eclipse.fordiac.ide.gef.policies.ModifiedNonResizeableEditPolicy;
import org.eclipse.fordiac.ide.gef.policies.ModifiedResizeablePolicy;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.commands.change.AddElementsToGroup;
import org.eclipse.fordiac.ide.model.commands.create.CreateFBElementInGroupCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

public class GroupXYLayoutPolicy extends ContainerContentXYLayoutPolicy {

	@Override
	protected Command getCreateCommand(final CreateRequest request) {
		if (null != request) {
			final Object childClass = request.getNewObjectType();
			final Point refPoint = ((Rectangle) getConstraintFor(request)).getTopLeft();
			if (childClass instanceof PaletteEntry) {
				return new CreateFBElementInGroupCommand((PaletteEntry) childClass, getGroup(), refPoint.x,
						refPoint.y);
			}
		}
		return null;
	}

	@Override
	protected Command getAddCommand(final Request request) {

		if (isDragAndDropRequestForGroup(request, getTargetEditPart(request))) {
			final ChangeBoundsRequest changeBoundsRequest = (ChangeBoundsRequest) request;
			final List<EditPart> editParts = changeBoundsRequest.getEditParts();
			final Group dropGroup = ((GroupContentNetwork) getTargetEditPart(request).getModel()).getGroup();
			final List<FBNetworkElement> fbEls = collectDraggedFBs(editParts, dropGroup);
			if (!fbEls.isEmpty()) {
				return createAddToGroupCommand(changeBoundsRequest, dropGroup, fbEls);
			}
		}
		// currently we don't want to allow any other add command in the future maybe drop from pallette
		return null;
	}

	private static List<FBNetworkElement> collectDraggedFBs(final List<EditPart> editParts, final Group dropGroup) {
		// only allow to add FBs to the group that are in the same FBNetwork
		return editParts.stream().filter(ep -> ep.getModel() instanceof FBNetworkElement)
				.map(ep -> (FBNetworkElement) ep.getModel())
				.filter(el -> dropGroup.getFbNetwork().equals(el.getFbNetwork())).collect(Collectors.toList());
	}

	public static boolean isDragAndDropRequestForGroup(final Request generic, final EditPart targetEditPart) {
		return (generic instanceof ChangeBoundsRequest) && (targetEditPart instanceof GroupContentEditPart);
	}

	@Override
	protected EditPolicy createChildEditPolicy(final EditPart child) {
		if (child.getModel() instanceof Group) {
			return new ModifiedResizeablePolicy();
		}
		return new ModifiedNonResizeableEditPolicy();
	}

	@Override
	public GraphicalEditPart getHost() {
		return (GraphicalEditPart) super.getHost();
	}

	private Group getGroup() {
		final Object model = getHost().getModel();
		return (model instanceof GroupContentNetwork) ? ((GroupContentNetwork) model).getGroup() : null;
	}

	private Command createAddToGroupCommand(final ChangeBoundsRequest request, final Group dropGroup,
			final List<FBNetworkElement> fbEls) {
		final Rectangle groupContentBounds = ((GraphicalEditPart) getTargetEditPart(request)).getFigure().getBounds();
		final Point topLeft = groupContentBounds.getTopLeft();
		final Point moveDelta = request.getMoveDelta().getScaled(1.0 / getZoomManager().getZoom());
		topLeft.translate(-moveDelta.x, -moveDelta.y);
		final AddElementsToGroup addElementsToGroup = new AddElementsToGroup(dropGroup, fbEls, topLeft);

		final Rectangle newContentBounds = getNewContentBounds(request.getEditParts());
		newContentBounds.translate(moveDelta);
		if (!groupContentBounds.contains(newContentBounds)) {
			//we need to increase the size of the group
			return createAddGroupAndResizeCommand(dropGroup, addElementsToGroup, groupContentBounds, newContentBounds);
		}

		return addElementsToGroup;
	}

	private static Rectangle getNewContentBounds(final List<EditPart> editParts) {
		Rectangle selectionExtend = null;
		for (final EditPart selElem : editParts) {
			if (selElem instanceof GraphicalEditPart
					&& ((GraphicalEditPart) selElem).getModel() instanceof FBNetworkElement) {
				// only consider the selected FBNetworkElements
				final Rectangle fbBounds = ((GraphicalEditPart) selElem).getFigure().getBounds();
				if (selectionExtend == null) {
					selectionExtend = fbBounds.getCopy();
				} else {
					selectionExtend.union(fbBounds);
				}
			}
		}
		return (selectionExtend != null) ? selectionExtend : new Rectangle();
	}

	private static Command createAddGroupAndResizeCommand(final Group dropGroup,
			final AddElementsToGroup addElementsToGroup, final Rectangle groupContentBounds, final Rectangle newContentBounds) {
		final CompoundCommand cmd = new CompoundCommand();
		newContentBounds.union(groupContentBounds);
		cmd.add(TrimGroupHandler.createChangeGroupBoundsCommand(dropGroup, groupContentBounds, newContentBounds));
		final Point offset = addElementsToGroup.getOffset();
		offset.translate(newContentBounds.x - groupContentBounds.x, newContentBounds.y - groupContentBounds.y);
		addElementsToGroup.setOffset(offset);
		cmd.add(addElementsToGroup);
		return cmd;
	}

}
