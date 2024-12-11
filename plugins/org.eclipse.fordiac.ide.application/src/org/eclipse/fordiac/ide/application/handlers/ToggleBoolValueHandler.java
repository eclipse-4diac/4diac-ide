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
package org.eclipse.fordiac.ide.application.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.gef.editparts.ValueEditPart;
import org.eclipse.fordiac.ide.model.commands.change.ChangeValueCommand;
import org.eclipse.fordiac.ide.model.data.BoolType;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class ToggleBoolValueHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditorChecked(event);
		final CommandStack stack = editor.getAdapter(CommandStack.class);
		if (stack != null) {
			HandlerUtil.getCurrentStructuredSelection(event).stream().filter(ValueEditPart.class::isInstance)
					.map(ValueEditPart.class::cast).map(ValueEditPart::getModel)
					.forEachOrdered(value -> toggleValue(value, stack));
		}
		return null;
	}

	private static void toggleValue(final Value value, final CommandStack stack) {
		if (value.getParentIE() instanceof final VarDeclaration varDeclaration && varDeclaration.isIsInput()
				&& varDeclaration.getType() instanceof BoolType && !varDeclaration.isArray()) {
			final String newValue = Boolean.parseBoolean(value.getValue()) ? "FALSE" : "TRUE"; //$NON-NLS-1$ //$NON-NLS-2$
			stack.execute(new ChangeValueCommand(varDeclaration, newValue));
		}
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		setBaseEnabled(HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME) instanceof final IStructuredSelection selection
				&& selection.stream().filter(ValueEditPart.class::isInstance).map(ValueEditPart.class::cast)
						.map(ValueEditPart::getModel).allMatch(ToggleBoolValueHandler::isValidValue));
	}

	private static boolean isValidValue(final Value value) {
		return value.getParentIE() instanceof final VarDeclaration varDeclaration && varDeclaration.isIsInput()
				&& varDeclaration.getType() instanceof BoolType && !varDeclaration.isArray();
	}
}
