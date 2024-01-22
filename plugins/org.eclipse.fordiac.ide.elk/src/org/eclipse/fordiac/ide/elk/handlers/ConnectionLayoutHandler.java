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

import java.text.MessageFormat;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.elk.alg.libavoid.server.LibavoidServerException;
import org.eclipse.fordiac.ide.elk.FordiacLayoutData;
import org.eclipse.fordiac.ide.elk.Messages;
import org.eclipse.fordiac.ide.elk.connection.ConnectionLayoutMapping;
import org.eclipse.fordiac.ide.elk.connection.ConnectionRoutingHelper;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class ConnectionLayoutHandler extends AbstractConnectionLayoutHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IWorkbenchPart part = HandlerUtil.getActiveEditor(event);
		if (null != part) {
			try {
				final ConnectionLayoutMapping mapping = run(part);
				final FordiacLayoutData data = ConnectionRoutingHelper.calculateConnections(mapping);
				runSubapps(mapping, data);
				executeCommand(event, part, data);
			} catch (final LibavoidServerException e) {
				MessageDialog.openWarning(HandlerUtil.getActiveShell(event), Messages.ConnectionLayout_TimeoutTitle,
						MessageFormat.format(Messages.ConnectionLayout_TimeoutMessage, e.getMessage()));
			}
		}
		return Status.OK_STATUS;
	}

}
