/*******************************************************************************
 * Copyright (c) 2025 Martin Erich Jobst
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
import org.eclipse.fordiac.ide.deployment.debug.breakpoint.DeploymentWatchpoint;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class ClearForceHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		DeploymentHandlerUtil.getWatchpoints(event).filter(DeploymentWatchpoint::isForceEnabled)
				.forEach(watchpoint -> clearForce(watchpoint, event));
		return null;
	}

	private static void clearForce(final DeploymentWatchpoint watchpoint, final ExecutionEvent event) {
		try {
			watchpoint.setForceEnabled(false);
		} catch (final CoreException e) {
			ErrorDialog.openError(HandlerUtil.getActiveShell(event), null, null, e.getStatus());
		}
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		setBaseEnabled(HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME) instanceof final IStructuredSelection selection
				&& DeploymentHandlerUtil.getWatchpoints(selection).anyMatch(DeploymentWatchpoint::isForceEnabled));
	}
}
