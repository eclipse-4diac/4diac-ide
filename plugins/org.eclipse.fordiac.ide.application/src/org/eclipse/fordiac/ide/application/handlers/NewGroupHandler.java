/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.model.commands.create.AbstractCreateFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateGroupCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;

public class NewGroupHandler extends AbstractContainerElementHandler {

	@Override
	protected AbstractCreateFBNetworkElementCommand createContainerCreationCommand(final List<?> selection,
			final FBNetwork network, final Rectangle posSizeRef) {
		return new CreateGroupCommand(network, selection, posSizeRef);
	}

}
