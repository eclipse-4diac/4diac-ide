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
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.editparts.GroupContentEditPart;
import org.eclipse.fordiac.ide.application.editparts.GroupContentNetwork;
import org.eclipse.fordiac.ide.gef.policies.ModifiedNonResizeableEditPolicy;
import org.eclipse.fordiac.ide.gef.policies.ModifiedResizeablePolicy;
import org.eclipse.fordiac.ide.gef.utilities.RequestUtil;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.commands.change.AddElementsToGroup;
import org.eclipse.fordiac.ide.model.commands.change.ChangeGroupBoundsCommand;
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

	@Override
	protected Command getChangeConstraintCommand(final ChangeBoundsRequest request) {
		final Command cmd = super.getChangeConstraintCommand(request);

		if (RequestUtil.isMoveRequest(request)) {
			final Point moveDelta = getScaledMoveDelta(request);
			final Rectangle newContentBounds = getNewContentBounds(request.getEditParts());
			newContentBounds.translate(moveDelta);
			final Rectangle groupContentBounds = getGroupAreaBounds(getHost());
			if (!groupContentBounds.contains(newContentBounds)) {
				newContentBounds.union(groupContentBounds);
				final ChangeGroupBoundsCommand changeSizeCmd = createChangeGroupBoundsCommand(getGroup(),
						groupContentBounds, newContentBounds);
				if (cmd instanceof CompoundCommand) {
					((CompoundCommand) cmd).add(changeSizeCmd);
				} else {
					final CompoundCommand compCmd = new CompoundCommand();
					compCmd.add(cmd);
					compCmd.add(changeSizeCmd);
					return compCmd;
				}
			}
		}

		return cmd;
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
		final Rectangle groupContentBounds = getGroupAreaBounds(getHost());
		final Point topLeft = groupContentBounds.getTopLeft();
		final Point moveDelta = getScaledMoveDelta(request);
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

	public static Rectangle getGroupAreaBounds(final GraphicalEditPart groupContentEP) {
		final Rectangle groupContentBounds = groupContentEP.getFigure().getBounds().getCopy();
		final Rectangle groupBounds = ((GraphicalEditPart) groupContentEP.getParent()).getFigure().getBounds();
		final int borderSize = groupContentBounds.x - groupBounds.x;
		if (groupBounds.width < groupContentBounds.width) {
			groupContentBounds.width = groupBounds.width - borderSize;
		}
		final int dy = groupContentBounds.y - groupBounds.y;
		if ((groupBounds.height - dy) < groupContentBounds.height) {
			groupContentBounds.height = groupBounds.height - dy - borderSize;
		}
		return groupContentBounds;
	}

	private Rectangle getNewContentBounds(final List<EditPart> editParts) {
		Rectangle selectionExtend = null;
		for (final EditPart selElem : editParts) {
			if (selElem instanceof GraphicalEditPart && selElem.getModel() instanceof FBNetworkElement) {
				// only consider the selected FBNetworkElements
				final Rectangle fbBounds = ((GraphicalEditPart) selElem).getFigure().getBounds();
				if (selectionExtend == null) {
					selectionExtend = fbBounds.getCopy();
				} else {
					selectionExtend.union(fbBounds);
				}
				addValueBounds((FBNetworkElement) selElem.getModel(), selectionExtend);
			}
		}
		return (selectionExtend != null) ? selectionExtend : new Rectangle();
	}

	private void addValueBounds(final FBNetworkElement model, final Rectangle selectionExtend) {
		final Map<Object, Object> editPartRegistry = getHost().getViewer().getEditPartRegistry();
		model.getInterface().getInputVars().stream().filter(Objects::nonNull)
		.map(ie -> editPartRegistry.get(ie.getValue())).filter(GraphicalEditPart.class::isInstance)
		.forEach(ep -> selectionExtend.union(((GraphicalEditPart) ep).getFigure().getBounds()));
	}

	private static Command createAddGroupAndResizeCommand(final Group dropGroup,
			final AddElementsToGroup addElementsToGroup, final Rectangle groupContentBounds, final Rectangle newContentBounds) {
		final CompoundCommand cmd = new CompoundCommand();
		newContentBounds.union(groupContentBounds);
		cmd.add(createChangeGroupBoundsCommand(dropGroup, groupContentBounds, newContentBounds));
		final Point offset = addElementsToGroup.getOffset();
		offset.translate(newContentBounds.x - groupContentBounds.x, newContentBounds.y - groupContentBounds.y);
		addElementsToGroup.setOffset(offset);
		cmd.add(addElementsToGroup);
		return cmd;
	}

	public static ChangeGroupBoundsCommand createChangeGroupBoundsCommand(final Group group,
			final Rectangle groupContentContainerBounds, final Rectangle groupContentBounds) {
		final int dx = groupContentBounds.x - groupContentContainerBounds.x;
		final int dy = groupContentBounds.y - groupContentContainerBounds.y;
		final int dw = groupContentBounds.width - groupContentContainerBounds.width;
		final int dh = groupContentBounds.height - groupContentContainerBounds.height;
		return new ChangeGroupBoundsCommand(group, dx, dy, dw, dh);
	}

}
