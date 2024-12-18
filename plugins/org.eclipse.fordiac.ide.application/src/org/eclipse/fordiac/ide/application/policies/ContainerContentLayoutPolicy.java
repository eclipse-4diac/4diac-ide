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
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.commands.ResizeGroupOrSubappCommand;
import org.eclipse.fordiac.ide.application.editparts.AbstractContainerContentEditPart;
import org.eclipse.fordiac.ide.application.editparts.UnfoldedSubappContentEditPart;
import org.eclipse.fordiac.ide.gef.policies.ModifiedMoveHandle;
import org.eclipse.fordiac.ide.gef.preferences.GefPreferenceConstants;
import org.eclipse.fordiac.ide.model.commands.change.AbstractChangeContainerBoundsCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;

public class ContainerContentLayoutPolicy extends FBNetworkXYLayoutEditPolicy {

	private Figure moveHandle;

	@Override
	protected void showLayoutTargetFeedback(final Request request) {
		if (REQ_ADD.equals(request.getType()) && (moveHandle == null)) {
			// we want to show the handle for the parent which is the whole group
			moveHandle = new ModifiedMoveHandle((GraphicalEditPart) getTargetEditPart(request).getParent(),
					new Insets(1), GefPreferenceConstants.CORNER_DIM_HALF);
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
	protected Command createChangeConstraintCommand(final ChangeBoundsRequest request, final EditPart child,
			final Object constraint) {
		final Command cmd = super.createChangeConstraintCommand(request, child, constraint);
		if (cmd != null) {
			return new ResizeGroupOrSubappCommand(getHost(), cmd);
		}
		return cmd;
	}

	protected FBNetworkElement getParentModel() {
		return (getHost() instanceof final AbstractContainerContentEditPart accep) ? accep.getContainerElement() : null;
	}

	protected Rectangle getNewContentBounds(final List<EditPart> editParts) {
		Rectangle selectionExtend = null;
		for (final EditPart selElem : editParts) {
			if (selElem instanceof final GraphicalEditPart gep
					&& selElem.getModel() instanceof final FBNetworkElement fbnElm) {
				// only consider the selected FBNetworkElements
				final Rectangle fbBounds = gep.getFigure().getBounds();
				if (selectionExtend == null) {
					selectionExtend = fbBounds.getCopy();
				} else {
					selectionExtend.union(fbBounds);
				}
				addValueBounds(fbnElm, selectionExtend);
			}
		}
		return (selectionExtend != null) ? selectionExtend : new Rectangle();
	}

	private void addValueBounds(final FBNetworkElement model, final Rectangle selectionExtend) {
		final Map<Object, EditPart> editPartRegistry = getHost().getViewer().getEditPartRegistry();
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
		final Point moveDelta = new Point(contentBounds.x - contentContainerBounds.x,
				contentBounds.y - contentContainerBounds.y);
		final Dimension sizeDelta = new Dimension(contentBounds.width - contentContainerBounds.width,
				contentBounds.height - contentContainerBounds.height);
		return FBNetworkXYLayoutEditPolicy.createChangeBoundsCommand(container, sizeDelta, moveDelta);
	}

	protected static void translateToRelative(final GraphicalEditPart graphicalEditPart, final Point topLeft) {
		GraphicalEditPart parent = graphicalEditPart;
		while (parent != null) {
			if (parent instanceof UnfoldedSubappContentEditPart) {
				final Rectangle subappContentBounds = ContainerContentLayoutPolicy.getContainerAreaBounds(parent);
				topLeft.translate(-subappContentBounds.x, -subappContentBounds.y);
				return;
			}
			parent = (GraphicalEditPart) parent.getParent();
		}
	}

}