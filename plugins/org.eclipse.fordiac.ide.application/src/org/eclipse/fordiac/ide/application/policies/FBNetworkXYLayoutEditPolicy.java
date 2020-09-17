/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Michael Hofmann, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.commands.ListFBCreateCommand;
import org.eclipse.fordiac.ide.application.commands.PasteCommand;
import org.eclipse.fordiac.ide.gef.policies.ModifiedNonResizeableEditPolicy;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.commands.change.SetPositionCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateSubAppInstanceCommand;
import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.fordiac.ide.util.dnd.TransferDataSelectionOfFb;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

public class FBNetworkXYLayoutEditPolicy extends XYLayoutEditPolicy {

	private ZoomManager zoomManager;

	@Override
	public void setHost(EditPart host) {
		super.setHost(host);
		zoomManager = ((ScalableFreeformRootEditPart) (getHost().getRoot())).getZoomManager();
	}

	@Override
	protected EditPolicy createChildEditPolicy(EditPart child) {
		return new ModifiedNonResizeableEditPolicy();

	}

	@Override
	protected Command createChangeConstraintCommand(final ChangeBoundsRequest request, final EditPart child,
			final Object constraint) {
		// return a command that can move a "ViewEditPart"
		if ((child.getModel() instanceof PositionableElement) && (constraint instanceof Rectangle)) {
			return new SetPositionCommand((PositionableElement) child.getModel(), request, (Rectangle) constraint);
		}
		return null;
	}

	@Override
	protected Command getCreateCommand(final CreateRequest request) {
		if (null != request) {
			Object childClass = request.getNewObjectType();
			Rectangle constraint = (Rectangle) getConstraintFor(request);
			if (getHost().getModel() instanceof FBNetwork) {
				FBNetwork fbNetwork = (FBNetwork) getHost().getModel();
				if (childClass instanceof FBTypePaletteEntry) {
					FBTypePaletteEntry type = (FBTypePaletteEntry) childClass;
					return new FBCreateCommand(type, fbNetwork, constraint.getLocation().x, constraint.getLocation().y);
				}
				if (childClass instanceof FBTypePaletteEntry[]) {
					FBTypePaletteEntry[] type = (FBTypePaletteEntry[]) childClass;
					return new ListFBCreateCommand(type, fbNetwork, constraint.getLocation().x,
							constraint.getLocation().y);
				}
				if (childClass instanceof SubApplicationTypePaletteEntry) {
					SubApplicationTypePaletteEntry type = (SubApplicationTypePaletteEntry) request.getNewObjectType();
					return new CreateSubAppInstanceCommand(type, fbNetwork, constraint.getLocation().x,
							constraint.getLocation().y);
				}
				if (childClass instanceof TransferDataSelectionOfFb[]) {
					TransferDataSelectionOfFb[] type = (TransferDataSelectionOfFb[]) childClass;
					return new ListFBCreateCommand(type, fbNetwork, constraint.getLocation().x,
							constraint.getLocation().y);
				}
			}
		}
		return null;
	}

	@Override
	protected Command getAddCommand(final Request generic) {
		return null;
	}

	@Override
	protected Command getCloneCommand(ChangeBoundsRequest request) {
		List<EObject> elements = ((Stream<?>) (request.getEditParts()).stream())
				.map(n -> (EObject) (((EditPart) n).getModel())).collect(Collectors.toList());
		Point scaledPoint = request.getMoveDelta().getScaled(1.0 / zoomManager.getZoom());
		return new PasteCommand(elements, (FBNetwork) getHost().getModel(), scaledPoint.x, scaledPoint.y);
	}

}
