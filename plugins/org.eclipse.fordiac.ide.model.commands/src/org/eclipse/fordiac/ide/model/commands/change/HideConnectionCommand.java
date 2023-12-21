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

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.helpers.ConnectionsHelper;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkElementHelper;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.commands.Command;

public class HideConnectionCommand extends Command implements ScopedCommand {

	private final Connection connection;
	private final boolean isVisible;
	private final Connection resourceConn;

	public HideConnectionCommand(final Connection connection, final boolean isVisible) {
		this.connection = Objects.requireNonNull(connection);
		this.isVisible = isVisible;
		resourceConn = ConnectionsHelper.getOppositeConnection(connection);
	}

	@Override
	public boolean canExecute() {
		if (connection.getFBNetwork().eContainer() instanceof final FBNetworkElement fbnEl) {
			if (((fbnEl instanceof final SubApp subApp) && (subApp.isTyped())) || (fbnEl instanceof CFBInstance)) {
				return false;
			}
			return !FBNetworkElementHelper.isContainedInTypedInstance(fbnEl);
		}
		return true;
	}

	@Override
	public void execute() {
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

	private void setVisible(final boolean visible) {
		connection.setVisible(visible);
		if (null != resourceConn) {
			resourceConn.setVisible(visible);
		}
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		if (resourceConn != null) {
			return Set.of(connection, resourceConn);
		}
		return Set.of(connection);
	}
}
