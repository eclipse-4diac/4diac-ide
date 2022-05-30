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

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.gef.policies.ModifiedMoveHandle;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.fordiac.ide.model.commands.change.AbstractChangeContainerBoundsCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeGroupBoundsCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeSubAppBoundsCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;

public class ContainerContentXYLayoutPolicy extends FBNetworkXYLayoutEditPolicy {

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