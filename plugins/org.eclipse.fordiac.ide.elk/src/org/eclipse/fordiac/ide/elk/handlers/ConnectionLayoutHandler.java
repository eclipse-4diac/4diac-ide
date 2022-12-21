/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.elk.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.elk.FordiacLayoutData;
import org.eclipse.fordiac.ide.elk.commands.ConnectionLayoutCommand;
import org.eclipse.fordiac.ide.elk.connection.ConnectionLayoutMapping;
import org.eclipse.fordiac.ide.elk.connection.ConnectionLayoutRunner;
import org.eclipse.fordiac.ide.elk.connection.StandardConnectionRoutingHelper;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

public class ConnectionLayoutHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IWorkbenchPart workbenchPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart();

		final ConnectionLayoutMapping normalMapping = ConnectionLayoutRunner.run(workbenchPart);
		final FordiacLayoutData data = StandardConnectionRoutingHelper.INSTANCE.calculateConnections(normalMapping);

		ConnectionLayoutRunner.runGroups(workbenchPart, normalMapping, data);
		ConnectionLayoutRunner.runSubapps(normalMapping, data);

		workbenchPart.getAdapter(CommandStack.class).execute(new ConnectionLayoutCommand(data));

		return null;
	}

}
