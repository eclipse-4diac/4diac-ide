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

import java.util.stream.Stream;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.fordiac.ide.deployment.debug.preferences.DeploymentDebugPreferences;
import org.eclipse.fordiac.ide.deployment.debug.ui.annotation.WatchValueAnnotation;
import org.eclipse.fordiac.ide.deployment.debug.ui.editparts.WatchValueEditPart;
import org.eclipse.fordiac.ide.deployment.debug.watch.IVarDeclarationWatch;
import org.eclipse.fordiac.ide.model.commands.change.ChangeValueCommand;
import org.eclipse.fordiac.ide.model.data.BoolType;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkElementHelper;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class ToggleBoolValueHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditorChecked(event);
		final CommandStack stack = editor.getAdapter(CommandStack.class);
		getValues(HandlerUtil.getCurrentStructuredSelection(event)).forEachOrdered(value -> toggleValue(value, stack));
		return null;
	}

	private static void toggleValue(final WatchValueAnnotation annotation, final CommandStack stack) {
		if (annotation.getElement() instanceof final VarDeclaration varDeclaration && varDeclaration.isIsInput()
				&& varDeclaration.getType() instanceof BoolType && !varDeclaration.isArray()
				&& annotation.getWatch() instanceof final IVarDeclarationWatch varDeclarationWatch) {
			final String newValue = Boolean.parseBoolean(annotation.getText()) ? "FALSE" : "TRUE"; //$NON-NLS-1$ //$NON-NLS-2$
			try {
				varDeclarationWatch.setValue(newValue);
			} catch (final DebugException e) {
				ErrorDialog.openError(null, null, null, Status.error(e.getLocalizedMessage(), e));
			}
			if (stack != null && DeploymentDebugPreferences.isMonitoringValueWriteThrough()
					&& !FBNetworkElementHelper.isContainedInTypedInstance(varDeclaration.getFBNetworkElement())) {
				stack.execute(new ChangeValueCommand(varDeclaration, newValue));
			}
		}
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		setBaseEnabled(HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME) instanceof final IStructuredSelection selection
				&& getValues(selection).allMatch(ToggleBoolValueHandler::isValidValue));
	}

	private static boolean isValidValue(final WatchValueAnnotation annotation) {
		return annotation.getElement() instanceof final VarDeclaration varDeclaration && varDeclaration.isIsInput()
				&& varDeclaration.getType() instanceof BoolType && !varDeclaration.isArray();
	}

	private static Stream<WatchValueAnnotation> getValues(final IStructuredSelection selection) {
		return selection.stream().filter(WatchValueEditPart.class::isInstance).map(WatchValueEditPart.class::cast)
				.map(WatchValueEditPart::getModel);
	}
}
