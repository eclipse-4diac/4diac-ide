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
package org.eclipse.fordiac.ide.debug.ui.editor;

import java.text.MessageFormat;
import java.util.Optional;

import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.debug.ui.actions.IVariableValueEditor;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugVariable;
import org.eclipse.fordiac.ide.debug.ui.Messages;
import org.eclipse.fordiac.ide.gef.dialogs.VariableDialog;
import org.eclipse.fordiac.ide.model.data.ArrayType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

public class EvaluatorVariableValueEditor implements IVariableValueEditor {

	@Override
	public boolean editVariable(final IVariable variable, final Shell shell) {
		if (variable instanceof final EvaluatorDebugVariable evaluatorDebugVariable) {
			if (isStructuredVariable(evaluatorDebugVariable)) {
				openVariableDialog(evaluatorDebugVariable, shell);
			} else {
				openInputDialog(evaluatorDebugVariable, shell);
			}
			return true;
		}
		return false;
	}

	protected static void openVariableDialog(final EvaluatorDebugVariable variable, final Shell shell) {
		final Optional<String> value = VariableDialog.open(shell, Messages.EvaluatorVariableValueEditor_Title,
				variable.getInternalVariable());
		if (value.isPresent()) {
			try {
				variable.setValue(value.get());
			} catch (final DebugException e) {
				ErrorDialog.openError(null, null, null, Status.error(e.getLocalizedMessage(), e));
			}
		}
	}

	protected static void openInputDialog(final EvaluatorDebugVariable variable, final Shell shell) {
		final InputDialog inputDialog = new InputDialog(shell, Messages.EvaluatorVariableValueEditor_Title,
				MessageFormat.format(Messages.EvaluatorVariableValueEditor_Message, variable.getName()),
				variable.getValue().getValueString(), input -> validateInput(variable, input));
		if (inputDialog.open() == Window.OK) {
			final String newValue = inputDialog.getValue();
			try {
				variable.setValue(newValue);
			} catch (final DebugException e) {
				ErrorDialog.openError(null, null, null, Status.error(e.getLocalizedMessage(), e));
			}
		}
	}

	protected static String validateInput(final IVariable variable, final String input) {
		try {
			if (variable.verifyValue(input)) {
				return null; // null means valid
			}
		} catch (final DebugException exception) {
			return Messages.EvaluatorVariableValueEditor_Exception;
		}
		return Messages.EvaluatorVariableValueEditor_Invalid;
	}

	protected static boolean isStructuredVariable(final EvaluatorDebugVariable evaluatorDebugVariable) {
		final INamedElement type = evaluatorDebugVariable.getInternalVariable().getType();
		return type instanceof StructuredType || type instanceof ArrayType;
	}

	@Override
	public boolean saveVariable(final IVariable variable, final String expression, final Shell shell) {
		return false; // use default
	}
}
