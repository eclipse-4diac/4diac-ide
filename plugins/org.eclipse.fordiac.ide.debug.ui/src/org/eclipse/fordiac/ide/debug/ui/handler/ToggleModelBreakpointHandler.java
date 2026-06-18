/***************************
 * Copyright (c) 2026 Vikash Kumar sinha
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vikash Kumar sinha- initial API and implementation and/or initial documentation
 ***************************/
package org.eclipse.fordiac.ide.debug.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTargetExtension;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class ToggleModelBreakpointHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IWorkbenchPart activePart = HandlerUtil.getActivePart(event);
		final ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
		try {
			final IToggleBreakpointsTarget target = DebugUITools.getToggleBreakpointsTargetManager()
					.getToggleBreakpointsTarget(activePart, currentSelection);
			if (target instanceof final IToggleBreakpointsTargetExtension extension) {
				extension.toggleBreakpoints(activePart, currentSelection);
			} else if (target != null) {
				target.toggleLineBreakpoints(activePart, currentSelection);
			}
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getLocalizedMessage(), e);
		}
		return null;
	}
}