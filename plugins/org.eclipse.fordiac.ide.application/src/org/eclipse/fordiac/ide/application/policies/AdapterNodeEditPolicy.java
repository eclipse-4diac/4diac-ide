/*******************************************************************************
 * Copyright (c) 2016, 2024 fortiss GmbH, Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Alois Zoitl - moved openEditor helper function to EditorUtils
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import org.eclipse.fordiac.ide.model.commands.change.AbstractReconnectConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.change.ReconnectAdapterConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.AdapterConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;

public class AdapterNodeEditPolicy extends InterfaceElementEditPolicy {

	@Override
	protected AbstractConnectionCreateCommand createConnectionCreateCommand() {
		return new AdapterConnectionCreateCommand(getParentNetwork());
	}

	@Override
	protected AbstractReconnectConnectionCommand createReconnectCommand(final Connection connection,
			final boolean isSourceReconnect, final IInterfaceElement newTarget) {
		return new ReconnectAdapterConnectionCommand(connection, isSourceReconnect, newTarget, getParentNetwork());
	}

}
