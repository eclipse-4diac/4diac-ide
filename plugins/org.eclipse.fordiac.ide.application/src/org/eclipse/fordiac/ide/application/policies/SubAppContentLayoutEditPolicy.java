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
import java.util.stream.Collectors;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.application.commands.AddElementsToSubAppCommand;
import org.eclipse.fordiac.ide.application.commands.MoveElementsFromSubAppCommand;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;

/** This policy creates an AddFBToSubAppCommand when user moves selected FBs over a subapp. When this is possible the
 * subapp is marked as selected. */
public class SubAppContentLayoutEditPolicy extends ContainerContentLayoutPolicy {


	@Override
	protected Command getAddCommand(final Request request) {

		if (isDragAndDropRequestFromSubAppToSubApp(request, getTargetEditPart(request))) {
			final List<EditPart> editParts = ((ChangeBoundsRequest) request).getEditParts();
			final SubApp dropSubApp = (SubApp) getTargetEditPart(request).getModel();
			final List<FBNetworkElement> fbEls = collectDraggedFBs(editParts, dropSubApp);
			final Point destination = getTranslatedAndZoomedPoint((ChangeBoundsRequest) request);

			if (!fbEls.isEmpty()) {
				return new MoveElementsFromSubAppCommand(fbEls,
						new org.eclipse.swt.graphics.Point(destination.x, destination.y));
			}
			return new AddElementsToSubAppCommand(dropSubApp, editParts);
		}
		return super.getAddCommand(request);
	}

	@Override
	protected SubApp getParentModel() {
		return (SubApp) super.getParentModel();
	}

	private static List<FBNetworkElement> collectDraggedFBs(final List<EditPart> editParts,
			final SubApp dropSubApp) {
		return editParts.stream().filter(ep -> ep.getModel() instanceof FBNetworkElement)
				.map(ep -> (FBNetworkElement) ep.getModel())
				.filter(el -> el.isNestedInSubApp() && isChildFromDropTarget(el, dropSubApp))
				.collect(Collectors.toList());
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


}
