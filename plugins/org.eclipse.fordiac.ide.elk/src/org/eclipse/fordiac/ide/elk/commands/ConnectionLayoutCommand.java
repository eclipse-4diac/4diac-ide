/*******************************************************************************
 * Copyright (c) 2022, 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.elk.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.elk.FordiacLayoutData;
import org.eclipse.fordiac.ide.elk.FordiacLayoutData.ConnectionLayoutData;

// simplified version of the LayoutCommand
public class ConnectionLayoutCommand extends AbstractLayoutCommand {

	private final List<ConnectionLayoutData> newRoutingData;
	private final List<ConnectionLayoutData> oldRoutingData;

	public ConnectionLayoutCommand(final FordiacLayoutData data) {
		this.newRoutingData = data.getConnectionPoints();
		this.oldRoutingData = new ArrayList<>(newRoutingData.size());
	}

	@Override
	public void execute() {
		saveRoutingDataForUndo(newRoutingData, oldRoutingData);
		setRoutingData(newRoutingData);
	}

	@Override
	public void redo() {
		setRoutingData(newRoutingData);
	}

	@Override
	public void undo() {
		setRoutingData(oldRoutingData);
	}

}
