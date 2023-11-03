/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.application.commands.ConnectThroughCommand;
import org.eclipse.fordiac.ide.application.editparts.InterfaceEditPartForFBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.validation.LinkConstraints;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class ConnectThroughHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final ISelection selection = HandlerUtil.getCurrentSelection(event);

		final List<IInterfaceElement> ies = checkSelection(selection);

		if (!ies.isEmpty()) {
			final ConnectThroughCommand cmd = new ConnectThroughCommand(ies.get(0), ies.get(1));
			if (cmd.canExecute()) {
				editor.getAdapter(CommandStack.class).execute(cmd);
			}
		}
		return Status.OK_STATUS;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final Object selection = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_CURRENT_SELECTION_NAME);
		setBaseEnabled(!checkSelection((ISelection) selection).isEmpty());
	}

	private static List<IInterfaceElement> checkSelection(final ISelection selection) {
		if (selection instanceof final IStructuredSelection structSel) {
			final List<IInterfaceElement> ieList = ((List<?>) structSel.toList()).stream()
					.filter(InterfaceEditPartForFBNetwork.class::isInstance)
					.map(InterfaceEditPartForFBNetwork.class::cast).map(InterfaceEditPartForFBNetwork::getModel)
					.toList();
			if (!ieList.isEmpty()) {
				ieList.sort((arg0, arg1) -> {
					if (arg0.isIsInput() && !arg1.isIsInput()) {
						return -1;
					}
					if (arg1.isIsInput() && !arg0.isIsInput()) {
						return 1;
					}
					return 0;
				});
				if ((ieList.size() == 2) && (checkSelectedIEs(ieList.get(0), ieList.get(1)))) {
					return ieList;
				}
			}
		}
		return Collections.emptyList();
	}

	private static boolean checkSelectedIEs(final IInterfaceElement element1, final IInterfaceElement element2) {
		if ((!element2.isIsInput()) && (element1.getClass().equals(element2.getClass()))
				&& !element1.getInputConnections().isEmpty() && !element2.getOutputConnections().isEmpty()) {
			// the second element is an output, both are of the same type and are not empty
			if (element1 instanceof VarDeclaration) {
				return LinkConstraints.typeCheck(element1, element2);
			}
			return true;

		}
		return false;
	}

}
