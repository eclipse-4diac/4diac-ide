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

import java.util.Optional;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.deployment.debug.breakpoint.DeploymentWatchpoint;
import org.eclipse.fordiac.ide.deployment.debug.ui.Messages;
import org.eclipse.fordiac.ide.deployment.debug.ui.breakpoint.DeploymentWatchpointUtil;
import org.eclipse.fordiac.ide.gef.dialogs.VariableDialog;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.edit.helper.InitialValueHelper;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

public class ForceHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		if (DeploymentHandlerUtil.getInterfaceElement(event) instanceof final VarDeclaration varDeclaration
				&& EcoreUtil.getRootContainer(varDeclaration) instanceof final AutomationSystem system) {
			final IFile file = system.getTypeEntry().getFile();
			final Optional<String> newValue = openForceDialog(HandlerUtil.getActiveShell(event), varDeclaration);
			if (newValue.isPresent()) {
				final Optional<DeploymentWatchpoint> watchpoint = DeploymentWatchpointUtil.findExistingWatchpoint(file,
						varDeclaration);
				if (watchpoint.isEmpty()) {
					createWatchpoint(file, varDeclaration, newValue.get(), event);
				} else {
					updateWatchpoint(watchpoint.get(), newValue.get(), event);
				}
			}
		}
		return null;
	}

	private static void createWatchpoint(final IResource resource, final VarDeclaration varDeclaration,
			final String newValue, final ExecutionEvent event) {
		if (newValue.isBlank()) {
			return;
		}
		try {
			final DeploymentWatchpoint watchpoint = new DeploymentWatchpoint(resource, varDeclaration);
			watchpoint.setForceValue(newValue);
			watchpoint.setForceEnabled(true);
			DebugPlugin.getDefault().getBreakpointManager().addBreakpoint(watchpoint);
		} catch (final CoreException e) {
			ErrorDialog.openError(HandlerUtil.getActiveShell(event), null, null, e.getStatus());
		}
	}

	private static void updateWatchpoint(final DeploymentWatchpoint watchpoint, final String newValue,
			final ExecutionEvent event) {
		try {
			watchpoint.setForceValue(newValue);
			watchpoint.setForceEnabled(!newValue.isBlank());
			watchpoint.setEnabled(true);
		} catch (final CoreException e) {
			ErrorDialog.openError(HandlerUtil.getActiveShell(event), null, null, e.getStatus());
		}
	}

	private static Optional<String> openForceDialog(final Shell shell, final VarDeclaration varDeclaration) {
		final String initialValue = InitialValueHelper.getInitialOrDefaultValue(varDeclaration);
		if (varDeclaration.getType() instanceof StructuredType || varDeclaration.isArray()) {
			return VariableDialog.open(shell, Messages.ForceHandler_ForceDialogTitle, varDeclaration, initialValue);
		}
		final InputDialog dialog = new InputDialog(shell, Messages.ForceHandler_ForceDialogTitle,
				Messages.ForceHandler_ForceDialogInput, initialValue,
				newValue -> validateValue(varDeclaration, newValue));
		if (dialog.open() == Window.OK) {
			return Optional.of(dialog.getValue());
		}
		return Optional.empty();
	}

	private static String validateValue(final VarDeclaration varDeclaration, final String newValue) {
		final String error = VariableOperations.validateValue(varDeclaration, newValue);
		if (error.isBlank()) {
			return null;
		}
		return error;
	}
}
