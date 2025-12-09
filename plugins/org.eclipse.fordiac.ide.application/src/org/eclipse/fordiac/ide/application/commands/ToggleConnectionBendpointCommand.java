/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.gef.commands.Command;

public abstract class ToggleConnectionBendpointCommand extends Command {
	private final Connection connection;
	private final ConnectionRoutingData oldRoutingData;
	private ConnectionRoutingData newRoutingData;

	public static ToggleConnectionBendpointCommand createCommand(final Connection connection) {
		if (connection.getRoutingData().is5SegementData()) {
			return new ConvertToThreeSegmentsConnectionCommand(connection);
		}
		return new ConvertToFiveSegmentsConnectionCommand(connection);
	}

	protected ToggleConnectionBendpointCommand(final Connection connection) {
		this.connection = connection;
		this.oldRoutingData = connection.getRoutingData();
	}

	protected abstract void manipulateNewRoutingData(ConnectionRoutingData newRoutingData);

	@Override
	public void execute() {
		createInitalNewRoutingData();
		manipulateNewRoutingData(newRoutingData);
		this.connection.setRoutingData(newRoutingData);
	}

	@Override
	public void redo() {
		this.connection.setRoutingData(newRoutingData);
	}

	@Override
	public void undo() {
		this.connection.setRoutingData(oldRoutingData);
	}

	protected void createInitalNewRoutingData() {
		newRoutingData = LibraryElementFactory.eINSTANCE.createConnectionRoutingData();
		newRoutingData.setDx1(oldRoutingData.getDx1());
		newRoutingData.setDx2(oldRoutingData.getDx2());
		newRoutingData.setDy(oldRoutingData.getDy());
	}
}
