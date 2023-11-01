/*******************************************************************************
 * Copyright (c) 2013, 2023 AIT, fortiss GmbH, Johannes Kepler University,
 *                               Primetals Technologies Austria GmbH
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
 *   Fabio Gandolfi
 *      - implemented positioning & resizing fro drag & drop
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.commands.AddElementsToSubAppCommand;
import org.eclipse.fordiac.ide.application.commands.MoveElementsFromSubAppCommand;
import org.eclipse.fordiac.ide.application.commands.ResizeGroupOrSubappCommand;
import org.eclipse.fordiac.ide.model.commands.change.RemoveElementsFromGroup;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;

/**
 * This policy creates an AddFBToSubAppCommand when user moves selected FBs over
 * a subapp. When this is possible the subapp is marked as selected.
 */
public class SubAppContentLayoutEditPolicy extends ContainerContentLayoutPolicy {

	@Override
	protected Command getAddCommand(final Request request) {
		if (isDragAndDropRequestForSubapp(request)) {
			final List<EditPart> editParts = ((ChangeBoundsRequest) request).getEditParts();
			final List<EditPart> moveFrom = collectMoveFromElements(editParts);
			final List<EditPart> addTo = collectAddToElements(editParts);

			final CompoundCommand cmd = new CompoundCommand();
			if (!moveFrom.isEmpty()) {
				final Point destination = getTranslatedAndZoomedPoint((ChangeBoundsRequest) request);
				final List<FBNetworkElement> fbWithoutGroup = moveFrom.stream()
						.map(ep -> (FBNetworkElement) ep.getModel()).filter(ep -> !ep.isInGroup()).toList();
				final List<FBNetworkElement> fbWithGroup = moveFrom.stream().map(ep -> (FBNetworkElement) ep.getModel())
						.filter(FBNetworkElement::isInGroup).toList();

				if (!fbWithoutGroup.isEmpty()) {
					cmd.add(new MoveElementsFromSubAppCommand(fbWithoutGroup,
							new org.eclipse.swt.graphics.Point(destination.x, destination.y)));
				}

				if (!fbWithGroup.isEmpty()) {
					final Point subAppCoordinatesPoint = getScaledMoveDelta((ChangeBoundsRequest) request);
					cmd.add(new RemoveElementsFromGroup(fbWithGroup,
							new Point(subAppCoordinatesPoint.x, subAppCoordinatesPoint.y)));
				}
			}

			if (!addTo.isEmpty()) {
				final Point moveDelta = getScaledMoveDelta((ChangeBoundsRequest) request);
				final Rectangle contentBounds = ContainerContentLayoutPolicy.getContainerAreaBounds(getHost());
				final Point topLeft = contentBounds.getTopLeft();
				translateToRelative(getHost(), topLeft);
				topLeft.translate(-moveDelta.x, -moveDelta.y);
				// needs a dummy (swt)point because swt and draw2d points are mixed
				final org.eclipse.swt.graphics.Point dummyPoint = new org.eclipse.swt.graphics.Point(topLeft.x,
						topLeft.y);
				cmd.add(new AddElementsToSubAppCommand(getParentModel(), editParts, dummyPoint));
			}

			if (!cmd.isEmpty()) {
				return new ResizeGroupOrSubappCommand(getHost(), cmd);
			}
		}
		return super.getAddCommand(request);
	}

	@Override
	protected SubApp getParentModel() {
		return (SubApp) super.getParentModel();
	}

	private boolean isDragAndDropRequestForSubapp(final Request request) {
		return (request instanceof ChangeBoundsRequest) && (getHost() == getTargetEditPart(request));
	}

	private List<EditPart> collectMoveFromElements(final List<EditPart> editParts) {
		return editParts.stream().filter(ep -> ep.getModel() instanceof final FBNetworkElement fbel && isInChild(fbel))
				.toList();
	}

	private boolean isInChild(final FBNetworkElement fbne) {
		final FBNetworkElement firstOuter = fbne.getOuterFBNetworkElement();
		if (fbne.isInGroup() && firstOuter != null) {
			return getParentModel().equals(firstOuter);
		}
		if (firstOuter != null) {
			return getParentModel().equals(firstOuter.getOuterFBNetworkElement());
		}
		return false;
	}

	private List<EditPart> collectAddToElements(final List<EditPart> editParts) {
		final EObject outerFBN = getParentModel().eContainer();
		return editParts.stream().filter(ep -> ep.getModel() instanceof FBNetworkElement)
				.filter(ep -> outerFBN.equals(((FBNetworkElement) ep.getModel()).getFbNetwork())).toList();
	}

}
