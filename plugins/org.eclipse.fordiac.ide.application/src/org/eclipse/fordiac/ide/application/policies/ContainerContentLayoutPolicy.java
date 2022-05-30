/*******************************************************************************
 * Copyright (c) 2020, 2021 Primetals Technologies Germany GmbH,
 * 							Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr, Alois Zoitl - initial implementation and/or documentation
 *   Alexander Lumplecker, Bianca Wiesmayr, Alois Zoitl - Bug: 568569
 *   Alois Zoitl - extracted most code into common base class for group
 *                 infrastructure
 *               - extracted this policy from the AbstractContainerContentEditPart
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.editparts.AbstractContainerContentEditPart;
import org.eclipse.fordiac.ide.gef.policies.ModifiedMoveHandle;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.fordiac.ide.gef.utilities.RequestUtil;
import org.eclipse.fordiac.ide.model.commands.change.AbstractChangeContainerBoundsCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeGroupBoundsCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeSubAppBoundsCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;

public class ContainerContentLayoutPolicy extends FBNetworkXYLayoutEditPolicy {

	private Figure moveHandle;

	@Override
	protected void showLayoutTargetFeedback(final Request request) {
		if (REQ_ADD.equals(request.getType()) && (moveHandle == null)) {
			// we want to show the handle for the parent which is the whole group
			moveHandle = new ModifiedMoveHandle((GraphicalEditPart) getTargetEditPart(request).getParent(),
					new Insets(1), DiagramPreferences.CORNER_DIM_HALF);
			addFeedback(moveHandle);
		}
	}

	@Override
	protected void eraseLayoutTargetFeedback(final Request request) {
		if (moveHandle != null) {
			removeFeedback(moveHandle);
			moveHandle = null;
		}
	}

	@Override
	public GraphicalEditPart getHost() {
		return (GraphicalEditPart) super.getHost();
	}

	@Override
	protected Command getChangeConstraintCommand(final ChangeBoundsRequest request) {
		final Command cmd = super.getChangeConstraintCommand(request);

		if (RequestUtil.isMoveRequest(request)) {
			final Point moveDelta = getScaledMoveDelta(request);
			final Rectangle newContentBounds = getNewContentBounds(request.getEditParts());
			newContentBounds.translate(moveDelta);
			final Rectangle contentBounds = ContainerContentLayoutPolicy.getContainerAreaBounds(getHost());
			if (!contentBounds.contains(newContentBounds)) {
				newContentBounds.union(contentBounds);
				final AbstractChangeContainerBoundsCommand changeSizeCmd = ContainerContentLayoutPolicy
						.createChangeBoundsCommand(getParentModel(), contentBounds, newContentBounds);
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

	protected FBNetworkElement getParentModel() {
		return (getHost() instanceof AbstractContainerContentEditPart)
				? ((AbstractContainerContentEditPart) getHost()).getContainerElement()
				: null;
	}

	protected Rectangle getNewContentBounds(final List<EditPart> editParts) {
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

	public static Rectangle getContainerAreaBounds(final GraphicalEditPart containerContentEP) {
		final Rectangle contentBounds = containerContentEP.getFigure().getBounds().getCopy();
		final Rectangle containerBounds = ((GraphicalEditPart) containerContentEP.getParent()).getFigure().getBounds();
		final int borderSize = contentBounds.x - containerBounds.x;
		if (containerBounds.width < contentBounds.width) {
			contentBounds.width = containerBounds.width - borderSize;
		}
		final int dy = contentBounds.y - containerBounds.y;
		if ((containerBounds.height - dy) < contentBounds.height) {
			contentBounds.height = containerBounds.height - dy - borderSize;
		}
		return contentBounds;
	}

	public static AbstractChangeContainerBoundsCommand createChangeBoundsCommand(final FBNetworkElement container,
			final Rectangle contentContainerBounds, final Rectangle contentBounds) {
		final int dx = contentBounds.x - contentContainerBounds.x;
		final int dy = contentBounds.y - contentContainerBounds.y;
		final int dw = contentBounds.width - contentContainerBounds.width;
		final int dh = contentBounds.height - contentContainerBounds.height;

		if (container instanceof Group) {
			return new ChangeGroupBoundsCommand((Group) container, dx, dy, dw, dh);
		}
		if (container instanceof SubApp) {
			return new ChangeSubAppBoundsCommand((SubApp) container, dx, dy, dw, dh);
		}
		return null;
	}

}