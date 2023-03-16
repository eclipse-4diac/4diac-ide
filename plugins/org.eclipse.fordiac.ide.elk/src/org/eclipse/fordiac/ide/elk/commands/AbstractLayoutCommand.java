/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.elk.commands;

import java.util.List;

import org.eclipse.fordiac.ide.elk.FordiacLayoutData.ConnectionLayoutData;
import org.eclipse.gef.commands.Command;

public abstract class AbstractLayoutCommand extends Command {

	protected static void setRoutingData(final List<ConnectionLayoutData> routingData) {
		routingData.forEach(rd -> rd.con.setRoutingData(rd.routingData));
	}

	protected static void saveRoutingDataForUndo(final List<ConnectionLayoutData> newRoutingData,
			final List<ConnectionLayoutData> oldRoutingData) {
		newRoutingData.forEach(cd -> oldRoutingData.add(new ConnectionLayoutData(cd.con, cd.con.getRoutingData())));
	}

}