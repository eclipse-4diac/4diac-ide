/*******************************************************************************
 * Copyright (c) 2013, 2023 AIT, 2024, fortiss GmbH, Johannes Kepler University,
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
 *   Sebastian Hollersbacher
 *   	- implemented move and reconnect
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.application.commands.MoveAndReconnectCommand;
import org.eclipse.fordiac.ide.application.commands.ResizeGroupOrSubappCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;

/**
 * This policy creates an MoveAndReconnectCommand when user moves selected FBs
 * over a subapp. When this is possible the subapp is marked as selected.
 */
public class SubAppContentLayoutEditPolicy extends ContainerContentLayoutPolicy {

	@Override
	protected Command getAddCommand(final Request request) {
		if (isDragAndDropRequestForSubapp(request)) {
			final List<? extends EditPart> editParts = ((ChangeBoundsRequest) request).getEditParts();
			final List<? extends EditPart> addTo = collectAddToElements(editParts);

			final CompoundCommand cmd = new CompoundCommand();
			if (!addTo.isEmpty()) {
				final Point destination = getTranslatedAndZoomedPoint((ChangeBoundsRequest) request);
				translateToRelative(getHost(), destination);
				final List<FBNetworkElement> elements = editParts.stream().map(EditPart::getModel)
						.filter(FBNetworkElement.class::isInstance).map(FBNetworkElement.class::cast).toList();
				cmd.add(new MoveAndReconnectCommand(elements, destination, getParentModel().getSubAppNetwork()));
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

	private static List<? extends EditPart> collectAddToElements(final List<? extends EditPart> editParts) {
		return editParts.stream().filter(ep -> ep.getModel() instanceof FBNetworkElement).toList();
	}
}
