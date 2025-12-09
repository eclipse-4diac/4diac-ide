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

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.commands.ResizeGroupOrSubappCommand;
import org.eclipse.fordiac.ide.application.editparts.GroupContentEditPart;
import org.eclipse.fordiac.ide.application.editparts.GroupContentNetwork;
import org.eclipse.fordiac.ide.model.commands.change.AddElementsToGroup;
import org.eclipse.fordiac.ide.model.commands.create.CreateFBElementInGroupCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

public class GroupXYLayoutPolicy extends ContainerContentLayoutPolicy {

	@Override
	protected Command getCreateCommand(final CreateRequest request) {
		if (null != request) {
			final Object childClass = request.getNewObjectType();
			final Point insertPoint = getInsertPoint(request);
			if (childClass instanceof final TypeEntry typeEntry) {
				return new CreateFBElementInGroupCommand(typeEntry, getParentModel(), insertPoint.x, insertPoint.y);
			}
		}
		return null;
	}

	@Override
	protected Command getAddCommand(final Request request) {

		if (isDragAndDropRequestForGroup(request, getTargetEditPart(request))) {
			final ChangeBoundsRequest changeBoundsRequest = (ChangeBoundsRequest) request;
			final List<? extends EditPart> editParts = changeBoundsRequest.getEditParts();
			final Group dropGroup = ((GroupContentNetwork) getTargetEditPart(request).getModel()).getGroup();
			final List<FBNetworkElement> fbEls = collectDraggedFBs(editParts, dropGroup);
			if (containsOnlyFbsAndSubapps(fbEls)) {
				return createAddToGroupCommand(changeBoundsRequest, dropGroup, fbEls);
			}
		}
		// currently we don't want to allow any other add command in the future maybe
		// drop from palette
		return null;
	}

	private static boolean containsOnlyFbsAndSubapps(final List<FBNetworkElement> fbEls) {
		return !fbEls.isEmpty() && !(fbEls.stream().anyMatch(Group.class::isInstance));
	}

	private static List<FBNetworkElement> collectDraggedFBs(final List<? extends EditPart> editParts,
			final Group dropGroup) {
		// only allow to add FBs to the group that are in the same FBNetwork
		return editParts.stream().map(EditPart::getModel).filter(FBNetworkElement.class::isInstance)
				.map(FBNetworkElement.class::cast).filter(el -> dropGroup.getFbNetwork().equals(el.getFbNetwork()))
				.toList();
	}

	public static boolean isDragAndDropRequestForGroup(final Request generic, final EditPart targetEditPart) {
		return (generic instanceof ChangeBoundsRequest) && (targetEditPart instanceof GroupContentEditPart);
	}

	@Override
	protected Group getParentModel() {
		return (Group) super.getParentModel();
	}

	private Command createAddToGroupCommand(final ChangeBoundsRequest request, final Group dropGroup,
			final List<FBNetworkElement> fbEls) {
		final Rectangle groupContentBounds = ContainerContentLayoutPolicy.getContainerAreaBounds(getHost());
		final Point topLeft = groupContentBounds.getTopLeft();
		translateToRelative((GraphicalEditPart) getHost().getParent(), topLeft);
		final Point moveDelta = getScaledMoveDelta(request);
		topLeft.translate(-moveDelta.x, -moveDelta.y);
		final AddElementsToGroup addElementsToGroup = new AddElementsToGroup(dropGroup, fbEls, topLeft);

		return new ResizeGroupOrSubappCommand(getHost(), addElementsToGroup);
	}

}
