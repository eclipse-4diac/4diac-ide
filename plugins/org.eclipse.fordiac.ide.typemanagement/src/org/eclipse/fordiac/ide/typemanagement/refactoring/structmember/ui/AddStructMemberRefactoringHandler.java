/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.structmember.ui;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public final class AddStructMemberRefactoringHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = HandlerUtil.getCurrentStructuredSelection(event);
		if (selection.size() == 1) {
			final VarDeclaration structPin = getStructPin(selection.getFirstElement());
			if (structPin != null) {
				AddStructMemberRefactoringUI.openAsync(HandlerUtil.getActiveShell(event), structPin);
			}
		}
		return null;
	}

	static VarDeclaration getStructPin(final Object element) {
		if (element instanceof final EditPart editPart) {
			return getStructPin(editPart.getModel());
		}
		return element instanceof final VarDeclaration variable ? variable : null;
	}
}
