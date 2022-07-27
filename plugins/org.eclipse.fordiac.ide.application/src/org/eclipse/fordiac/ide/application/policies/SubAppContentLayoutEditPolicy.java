/*******************************************************************************
 * Copyright (c) 2013, 2022 AIT, fortiss GmbH, Johannes Kepler University,
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
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.commands.AddElementsToSubAppCommand;
import org.eclipse.fordiac.ide.application.commands.MoveElementsFromSubAppCommand;
import org.eclipse.fordiac.ide.application.editparts.AbstractContainerContentEditPart;
import org.eclipse.fordiac.ide.model.commands.change.AbstractChangeContainerBoundsCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;

/** This policy creates an AddFBToSubAppCommand when user moves selected FBs over a subapp. When this is possible the
 * subapp is marked as selected. */
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
				final List<FBNetworkElement> fbEls = moveFrom.stream().map(ep -> (FBNetworkElement) ep.getModel())
						.collect(Collectors.toList());
				cmd.add(new MoveElementsFromSubAppCommand(fbEls,
						new org.eclipse.swt.graphics.Point(destination.x, destination.y)));
			}

			if (!addTo.isEmpty()) {
				final Point moveDelta = getScaledMoveDelta((ChangeBoundsRequest) request);
				cmd.add(new AddElementsToSubAppCommand(getParentModel(), editParts,
						new org.eclipse.swt.graphics.Point(moveDelta.x - getHostFigure().getBounds().x,
								moveDelta.y - getHostFigure().getBounds().y)));
				final Rectangle newContentBounds = getContentBounds(editParts, moveDelta);
				if (checkGroupNeedsResize(newContentBounds) && createChangeBoundsCommand(newContentBounds) != null) {
					cmd.add(createChangeBoundsCommand(newContentBounds));
				}
			}

			if (!cmd.isEmpty()) {
				return cmd;
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
		return editParts.stream().filter(ep -> ep.getModel() instanceof FBNetworkElement)
				.filter(ep -> isInChild((FBNetworkElement) ep.getModel())).collect(Collectors.toList());
	}

	private boolean isInChild(final FBNetworkElement fbne) {
		final FBNetworkElement firstOuter = fbne.getOuterFBNetworkElement();
		if (firstOuter != null) {
			return getParentModel().equals(firstOuter.getOuterFBNetworkElement());
		}
		return false;
	}

	private List<EditPart> collectAddToElements(final List<EditPart> editParts) {
		final EObject outerFBN = getParentModel().eContainer();
		return editParts.stream().filter(ep -> ep.getModel() instanceof FBNetworkElement)
				.filter(ep -> outerFBN.equals(((FBNetworkElement) ep.getModel()).getFbNetwork()))
				.collect(Collectors.toList());
	}

	private boolean checkGroupNeedsResize(final Rectangle newContentBounds) {
		return getHost() instanceof AbstractContainerContentEditPart
				&& !getHostFigure().getBounds().contains(newContentBounds);
	}

	private AbstractChangeContainerBoundsCommand createChangeBoundsCommand(final Rectangle newContentBounds) {
		if (!getHostFigure().getBounds().contains(newContentBounds)) {
			return ContainerContentLayoutPolicy.createChangeBoundsCommand(getParentModel(), getHostFigure().getBounds(),
					newContentBounds.union(getHostFigure().getBounds()));
		}
		return null;
	}

	public Rectangle getContentBounds(final List<EditPart> list, final Point moveDelta) {
		Rectangle selectionExtend = null;
		for (final EditPart selElem : list) {

			if (selElem instanceof GraphicalEditPart && selElem.getModel() instanceof FBNetworkElement) {
				// only consider the selected FBNetworkElements
				final Rectangle fbBounds = ((GraphicalEditPart) selElem).getFigure().getBounds().getCopy();
				fbBounds.x += moveDelta.x;
				fbBounds.y += moveDelta.y;
				if (selectionExtend == null) {
					selectionExtend = fbBounds;
				} else {
					selectionExtend.union(fbBounds);
				}
				addValueBounds((FBNetworkElement) selElem.getModel(), selectionExtend, moveDelta);
			}
		}
		return (selectionExtend != null) ? selectionExtend : new Rectangle();
	}

	private void addValueBounds(final FBNetworkElement model, final Rectangle selectionExtend, final Point moveDelta) {
		final Map<Object, Object> editPartRegistry = getHost().getViewer().getEditPartRegistry();
		model.getInterface().getInputVars().stream().filter(Objects::nonNull)
		.map(ie -> editPartRegistry.get(ie.getValue())).filter(GraphicalEditPart.class::isInstance)
		.forEach(ep -> {
			final Rectangle pinBounds = ((GraphicalEditPart) ep).getFigure().getBounds().getCopy();
			pinBounds.x += moveDelta.x;
			pinBounds.y += moveDelta.y;
			selectionExtend.union(pinBounds);
		});
	}

}
