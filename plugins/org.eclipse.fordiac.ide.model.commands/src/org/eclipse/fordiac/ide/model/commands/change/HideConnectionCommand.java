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
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.helpers.ConnectionsHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.gef.commands.Command;

public class HideConnectionCommand extends Command {

	private final Connection connection;
	private final boolean isVisible;
	private Connection resourceConn;
	
	public HideConnectionCommand(final Connection connection, final boolean isVisible) {
		this.connection = connection;
		this.isVisible = isVisible;
	}

	@Override
	public void execute() {
		resourceConn = ConnectionsHelper.getOppositeConnection(connection);
		setVisible(isVisible);
	}

	@Override
	public void undo() {
		setVisible(!isVisible);
	}

	@Override
	public void redo() {
		setVisible(isVisible);
	}

	private void setVisible(boolean visible) {
		connection.setVisible(visible);
		if (null != resourceConn) {
			resourceConn.setVisible(visible);
		}
	}
}
