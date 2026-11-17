/*******************************************************************************
 * Copyright (c) 2026 Osoft e.U.
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
package org.eclipse.fordiac.ide.deployment.debug.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.fordiac.ide.deployment.debug.ui.propertytester.SnapshotLaunchPropertyTester;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Terminates the selected snapshot debug session and starts it again with the
 * values the function block currently has in the runtime.
 */
public class RelaunchSnapshotHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) {
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof final IStructuredSelection structuredSelection) {
			final ILaunch launch = SnapshotLaunchPropertyTester
					.getSnapshotLaunch(structuredSelection.getFirstElement());
			if (launch != null) {
				SnapshotDebugJob.forRelaunch(launch).schedule();
			}
		}
		return null;
	}
}
