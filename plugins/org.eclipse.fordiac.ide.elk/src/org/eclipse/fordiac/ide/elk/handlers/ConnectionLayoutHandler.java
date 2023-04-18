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
import org.eclipse.elk.alg.libavoid.server.LibavoidServerException;
import org.eclipse.fordiac.ide.elk.FordiacLayoutData;
import org.eclipse.fordiac.ide.elk.Messages;
import org.eclipse.fordiac.ide.elk.commands.ConnectionLayoutCommand;
import org.eclipse.fordiac.ide.elk.connection.AbstractConnectionRoutingHelper;
import org.eclipse.fordiac.ide.elk.connection.ConnectionLayoutMapping;
import org.eclipse.fordiac.ide.elk.connection.ConnectionLayoutRunner;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class ConnectionLayoutHandler extends AbstractLayoutHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IWorkbenchPart part = HandlerUtil.getActiveEditor(event);

		if (null != part) {
			try {
				final ConnectionLayoutMapping normalMapping = ConnectionLayoutRunner.run(part);
				final FordiacLayoutData data = AbstractConnectionRoutingHelper.calculateConnections(normalMapping);

				ConnectionLayoutRunner.runGroups(part, normalMapping, data);
				ConnectionLayoutRunner.runSubapps(normalMapping, data);

				final ConnectionLayoutCommand cmd = new ConnectionLayoutCommand(data);
				if (event.getTrigger() instanceof Event && ((Event) event.getTrigger()).widget == null) {
					// widget is in case of manual activation the menu item
					// the automatic execution is programmatic and does therefore not have a widget

					// passes the cmd back to the editor to be handled there
					((Event) event.getTrigger()).data = cmd;
				} else {
					part.getAdapter(CommandStack.class).execute(cmd);
				}
			} catch (final LibavoidServerException e) {
				MessageDialog.openWarning(HandlerUtil.getActiveShell(event),
						Messages.ConnectionLayout_TimeoutTitle, Messages.ConnectionLayout_TimeoutMessage);
			}
		}
		return Status.OK_STATUS;
	}

}
