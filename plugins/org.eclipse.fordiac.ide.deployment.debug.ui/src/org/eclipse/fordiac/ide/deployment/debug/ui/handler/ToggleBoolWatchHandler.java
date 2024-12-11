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
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.fordiac.ide.deployment.debug.watch.IVarDeclarationWatch;
import org.eclipse.fordiac.ide.model.data.BoolType;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class ToggleBoolWatchHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		HandlerUtil.getCurrentStructuredSelection(event).stream().filter(IVarDeclarationWatch.class::isInstance)
				.map(IVarDeclarationWatch.class::cast).forEachOrdered(ToggleBoolWatchHandler::toggleValue);
		return null;
	}

	private static void toggleValue(final IVarDeclarationWatch watch) {
		if (watch.getInternalVariable().getType() instanceof BoolType) {
			final String newValue = Boolean.parseBoolean(watch.getInternalValue().toString()) ? "FALSE" : "TRUE"; //$NON-NLS-1$ //$NON-NLS-2$
			try {
				watch.setValue(newValue);
			} catch (final DebugException e) {
				ErrorDialog.openError(null, null, null, Status.error(e.getLocalizedMessage(), e));
			}
		}
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		setBaseEnabled(HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME) instanceof final IStructuredSelection selection
				&& selection.stream().filter(IVarDeclarationWatch.class::isInstance)
						.map(IVarDeclarationWatch.class::cast).allMatch(ToggleBoolWatchHandler::isValidValue));
	}

	private static boolean isValidValue(final IVarDeclarationWatch watch) {
		return watch.getInternalVariable().getType() instanceof BoolType;
	}
}
