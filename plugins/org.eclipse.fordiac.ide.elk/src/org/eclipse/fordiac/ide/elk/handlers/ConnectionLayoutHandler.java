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
package org.eclipse.fordiac.ide.elk.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.elk.FordiacLayoutData;
import org.eclipse.fordiac.ide.elk.commands.ConnectionLayoutCommand;
import org.eclipse.fordiac.ide.elk.connection.ConnectionLayoutMapping;
import org.eclipse.fordiac.ide.elk.connection.ConnectionLayoutRunner;
import org.eclipse.fordiac.ide.elk.connection.StandardConnectionRoutingHelper;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class ConnectionLayoutHandler extends AbstractLayoutHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IWorkbenchPart part = HandlerUtil.getActiveEditor(event);

		if (null != part) {
			final ConnectionLayoutMapping normalMapping = ConnectionLayoutRunner.run(part);
			final FordiacLayoutData data = StandardConnectionRoutingHelper.INSTANCE.calculateConnections(normalMapping);

			ConnectionLayoutRunner.runGroups(part, normalMapping, data);
			ConnectionLayoutRunner.runSubapps(normalMapping, data);

			part.getAdapter(CommandStack.class).execute(new ConnectionLayoutCommand(data));
		}
		return Status.OK_STATUS;
	}
}
