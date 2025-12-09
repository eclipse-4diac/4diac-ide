/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.model.commands.create.AbstractCreateFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.gef.commands.Command;

public class FBNetworkCreateInstanceDirectEditPolicy extends AbstractCreateInstanceDirectEditPolicy {
	@Override
	protected Command getElementCreateCommand(final TypeEntry value, final Point refPoint) {
		return AbstractCreateFBNetworkElementCommand.createCreateCommand(value, (FBNetwork) getHost().getModel(),
				refPoint.x, refPoint.y);
	}
}