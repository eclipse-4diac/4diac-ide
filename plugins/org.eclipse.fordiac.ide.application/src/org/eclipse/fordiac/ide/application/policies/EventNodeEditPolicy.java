/*******************************************************************************
 * Copyright (c) 2008, 2024 Profactor GmbH, fortiss GmbH, Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import org.eclipse.fordiac.ide.model.commands.change.AbstractReconnectConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.change.ReconnectEventConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;

/**
 * An EditPolicy which allows drawing Connections between EventInterfaces.
 */
public class EventNodeEditPolicy extends InterfaceElementEditPolicy {

	@Override
	protected AbstractConnectionCreateCommand createConnectionCreateCommand() {
		return new EventConnectionCreateCommand(getParentNetwork());
	}

	@Override
	protected AbstractReconnectConnectionCommand createReconnectCommand(final Connection connection,
			final boolean isSourceReconnect, final IInterfaceElement newTarget) {
		return new ReconnectEventConnectionCommand(connection, isSourceReconnect, newTarget, getParentNetwork());
	}
}
