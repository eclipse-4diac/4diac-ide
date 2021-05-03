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
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.commands.AddElementsToSubAppCommand;
import org.eclipse.fordiac.ide.application.commands.MoveElementFromSubAppCommand;
import org.eclipse.fordiac.ide.application.commands.MoveElementFromSubAppCommand.MoveOperation;
import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.gef.policies.EmptyXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.gef.policies.ModifiedMoveHandle;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;

/** This policy creates an AddFBToSubAppCommand when user moves selected FBs over a subapp. When this is possible the
 * subapp is marked as selected. */
public class FBAddToSubAppLayoutEditPolicy extends EmptyXYLayoutEditPolicy {

	private Figure moveHandle;

	@SuppressWarnings("rawtypes")
	@Override
	protected Command getAddCommand(Request request) {

		if (isDragAndDropRequestFromSubAppToSubApp(request, getTargetEditPart(request))) {
			final List editParts = ((ChangeBoundsRequest) request).getEditParts();
			final SubAppForFBNetworkEditPart dropEditPart = (SubAppForFBNetworkEditPart) getTargetEditPart(request);
			final CompoundCommand commandos = new CompoundCommand();
			for (final Object editPart : editParts) {
				if (((editPart instanceof EditPart)
						&& (((EditPart) editPart).getModel() instanceof FBNetworkElement))) {
					final FBNetworkElement dragEditPartModel = (FBNetworkElement) ((EditPart) editPart).getModel();

					if (dragEditPartModel.isNestedInSubApp()
							&& isChildFromDropTarget(dragEditPartModel, dropEditPart)) {
						final Rectangle bounds = getOuterSubappEditPart(editPart).getFigure().getBounds();
						commandos.add(new MoveElementFromSubAppCommand(dragEditPartModel, bounds,
								MoveOperation.DRAG_AND_DROP_TO_SUBAPP));
					}
				}
			}

			if (commandos.isEmpty()) {
				return new AddElementsToSubAppCommand(dropEditPart.getModel(), editParts);
			}
			return commandos;
		}
		return super.getAddCommand(request);
	}

	private static SubAppForFBNetworkEditPart getOuterSubappEditPart(final Object editPart) {
		return (SubAppForFBNetworkEditPart) ((AbstractFBNElementEditPart) editPart).getParent().getParent();
	}

	public static boolean isDragAndDropRequestFromSubAppToSubApp(Request generic, EditPart targetEditPart) {
		return (generic instanceof ChangeBoundsRequest) && (targetEditPart instanceof SubAppForFBNetworkEditPart);
	}

	private static boolean isChildFromDropTarget(FBNetworkElement dragEditPartModel,
			SubAppForFBNetworkEditPart dropEditPart) {
		if ((dragEditPartModel.getOuterFBNetworkElement() == null)
				|| (dragEditPartModel.getOuterFBNetworkElement().getOuterFBNetworkElement() == null)) {
			return false;

		}
		return dragEditPartModel.getOuterFBNetworkElement().getOuterFBNetworkElement().equals(dropEditPart.getModel());
	}

	@Override
	protected void showLayoutTargetFeedback(Request request) {
		if (REQ_ADD.equals(request.getType()) && (null == moveHandle)) {

			moveHandle = new ModifiedMoveHandle((GraphicalEditPart) getTargetEditPart(request), new Insets(1),
					DiagramPreferences.CORNER_DIM_HALF);
			addFeedback(moveHandle);
		}
	}

	@Override
	protected void eraseLayoutTargetFeedback(Request request) {
		if (moveHandle != null) {
			removeFeedback(moveHandle);
			moveHandle = null;
		}
	}

}
