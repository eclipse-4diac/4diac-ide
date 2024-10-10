/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.debug.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class ToggleWatchpointHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IWorkbenchPart activePart = HandlerUtil.getActivePart(event);
		final ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
		final IToggleBreakpointsTarget target = DebugUITools.getToggleBreakpointsTargetManager()
				.getToggleBreakpointsTarget(activePart, currentSelection);
		try {
			if (target != null && target.canToggleWatchpoints(activePart, currentSelection)) {
				target.toggleWatchpoints(activePart, currentSelection);
			}
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getLocalizedMessage(), e);
		}
		return null;
	}
}
