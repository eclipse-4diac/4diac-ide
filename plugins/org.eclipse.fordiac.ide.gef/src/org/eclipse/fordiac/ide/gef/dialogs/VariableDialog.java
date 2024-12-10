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
package org.eclipse.fordiac.ide.gef.dialogs;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.fordiac.ide.gef.widgets.VariableWidget;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.FrameworkUtil;

public class VariableDialog extends Dialog {

	private final String title;
	private final List<Variable<?>> variables;
	private final VariableWidget variableWidget;

	protected VariableDialog(final Shell shell, final String title, final List<Variable<?>> variables) {
		super(shell);
		this.title = title;
		this.variables = variables;
		variableWidget = new VariableWidget();
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		final Composite control = variableWidget.createWidget(parent);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(control);
		variableWidget.setInput(variables);
		return control;
	}

	@Override
	protected void configureShell(final Shell shell) {
		super.configureShell(shell);
		if (title != null) {
			shell.setText(title);
		}
	}

	@Override
	protected int getDialogBoundsStrategy() {
		return DIALOG_PERSISTSIZE;
	}

	@Override
	protected IDialogSettings getDialogBoundsSettings() {
		final IDialogSettings settings = PlatformUI
				.getDialogSettingsProvider(FrameworkUtil.getBundle(VariableDialog.class)).getDialogSettings();
		return DialogSettings.getOrCreateSection(settings, getClass().getSimpleName());
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	public List<Variable<?>> getVariables() {
		return variables;
	}

	public static Optional<String> open(final Shell shell, final ITypedElement element, final String initialValue) {
		return open(shell, Messages.VariableDialog_DefaultTitle, element, initialValue);
	}

	public static Optional<String> open(final Shell shell, final String title, final ITypedElement element,
			final String initialValue) {
		final Variable<?> variable;
		try {
			variable = computeInitialValue(element, initialValue);
		} catch (final InvocationTargetException e) {
			ErrorDialog.openError(shell, title, null,
					Status.error(MessageFormat.format(Messages.VariableDialog_ValueError, element.getQualifiedName()),
							e.getTargetException()));
			return Optional.empty();
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			return Optional.empty();
		}
		final VariableDialog dialog = new VariableDialog(shell, title, List.of(variable));
		if (dialog.open() == OK) {
			return Optional.of(variable.toString());
		}
		return Optional.empty();
	}

	public static Variable<?> computeInitialValue(final ITypedElement element, final String initialValue)
			throws InvocationTargetException, InterruptedException {
		final Variable<?>[] result = new Variable[1];
		PlatformUI.getWorkbench().getProgressService()
				.busyCursorWhile(moniotr -> result[0] = VariableOperations.newVariable(element, initialValue));
		return result[0];
	}
}
