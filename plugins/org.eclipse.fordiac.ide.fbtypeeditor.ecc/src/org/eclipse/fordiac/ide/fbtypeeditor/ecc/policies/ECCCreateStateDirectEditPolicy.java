/*******************************************************************************
 * Copyright (c) 2026 Vikash Kumar Sinha
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vikash Kumar Sinha - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.policies;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.CreateECStateCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editors.ECCStateCreationDirectEditManager;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.requests.LocationRequest;

public class ECCCreateStateDirectEditPolicy extends DirectEditPolicy {

	public void performDirectEdit(final LocationRequest request) {
		new ECCStateCreationDirectEditManager(getHost(), request.getLocation().getCopy()).show();
	}

	@Override
	protected Command getDirectEditCommand(final DirectEditRequest request) {
		if (request.getCellEditor() != null && request.getCellEditor().getValue() instanceof final String name
				&& !name.isBlank()) {
			final Point location = request.getLocation().getCopy();
			getHostFigure().translateToRelative(location);
			final Position pos = CoordinateConverter.INSTANCE.createPosFromScreenCoordinates(location.x, location.y);
			final ECState newState = LibraryElementFactory.eINSTANCE.createECState();
			newState.setName(name);
			return new CreateECStateCommand(newState, pos, (ECC) getHost().getModel());
		}
		return null;
	}

	@Override
	protected void showCurrentEditValue(final DirectEditRequest request) {
		// the state does not exist yet, nothing to show
	}
}
