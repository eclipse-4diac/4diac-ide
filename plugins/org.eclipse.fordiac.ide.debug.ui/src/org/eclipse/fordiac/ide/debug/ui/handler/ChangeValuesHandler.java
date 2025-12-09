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
package org.eclipse.fordiac.ide.debug.ui.handler;

import java.util.List;
import java.util.stream.Stream;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugVariable;
import org.eclipse.fordiac.ide.gef.dialogs.VariableDialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class ChangeValuesHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		changeValues(HandlerUtil.getActiveShell(event),
				getVariables(HandlerUtil.getCurrentStructuredSelection(event)).toList());
		return null;
	}

	private static void changeValues(final Shell shell, final List<EvaluatorDebugVariable> variables) {
		final List<String> values = VariableDialog.open(shell,
				variables.stream().map(EvaluatorDebugVariable::getInternalVariable));
		if (!values.isEmpty()) {
			for (int i = 0; i < variables.size(); i++) {
				try {
					variables.get(i).setValue(values.get(i));
				} catch (final DebugException e) {
					ErrorDialog.openError(null, null, null, Status.error(e.getLocalizedMessage(), e));
				}
			}
		}
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		setBaseEnabled(HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME) instanceof final IStructuredSelection selection
				&& getVariables(selection).allMatch(IVariable::supportsValueModification));
	}

	private static Stream<EvaluatorDebugVariable> getVariables(final IStructuredSelection selection) {
		return selection.stream().filter(EvaluatorDebugVariable.class::isInstance)
				.map(EvaluatorDebugVariable.class::cast);
	}
}
