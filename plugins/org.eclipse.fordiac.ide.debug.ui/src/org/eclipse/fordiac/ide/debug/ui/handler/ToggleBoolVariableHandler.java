/*******************************************************************************
 * Copyright (c) 2024, 2025 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.debug.ui.handler;

import java.util.stream.Stream;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugVariable;
import org.eclipse.fordiac.ide.model.data.BoolType;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class ToggleBoolVariableHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		getVariables(HandlerUtil.getCurrentStructuredSelection(event))
				.forEachOrdered(ToggleBoolVariableHandler::toggleValue);
		return null;
	}

	private static void toggleValue(final EvaluatorDebugVariable variable) {
		if (variable.getInternalVariable().getType() instanceof BoolType) {
			final String newValue = Boolean.parseBoolean(variable.getInternalVariable().getValue().toString()) ? "FALSE" //$NON-NLS-1$
					: "TRUE"; //$NON-NLS-1$
			try {
				variable.setValue(newValue);
			} catch (final DebugException e) {
				ErrorDialog.openError(null, null, null, Status.error(e.getLocalizedMessage(), e));
			}
		}
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		setBaseEnabled(HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME) instanceof final IStructuredSelection selection
				&& getVariables(selection).allMatch(ToggleBoolVariableHandler::isValidValue));
	}

	private static boolean isValidValue(final EvaluatorDebugVariable variable) {
		return variable.supportsValueModification() && variable.getInternalVariable().getType() instanceof BoolType;
	}

	private static Stream<EvaluatorDebugVariable> getVariables(final IStructuredSelection selection) {
		return selection.stream().filter(EvaluatorDebugVariable.class::isInstance)
				.map(EvaluatorDebugVariable.class::cast);
	}
}
