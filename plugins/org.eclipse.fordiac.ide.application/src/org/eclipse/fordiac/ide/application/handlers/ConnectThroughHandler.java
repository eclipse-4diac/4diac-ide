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
import java.util.stream.Collectors;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.application.commands.ConnectThroughCommand;
import org.eclipse.fordiac.ide.application.editors.FBNetworkEditor;
import org.eclipse.fordiac.ide.application.editparts.InterfaceEditPartForFBNetwork;
import org.eclipse.fordiac.ide.model.commands.create.LinkConstraints;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class ConnectThroughHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		FBNetworkEditor editor = (FBNetworkEditor) HandlerUtil.getActiveEditor(event);
		ISelection selection = HandlerUtil.getCurrentSelection(event);

		List<IInterfaceElement> ies = checkSelection(selection);

		if (!ies.isEmpty()) {
			ConnectThroughCommand cmd = new ConnectThroughCommand(ies.get(0), ies.get(1));
			if (cmd.canExecute()) {
				editor.getCommandStack().execute(cmd);
			}
		}
		return Status.OK_STATUS;
	}

	@Override
	public void setEnabled(Object evaluationContext) {
		Object selection = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_CURRENT_SELECTION_NAME);
		setBaseEnabled(!checkSelection((ISelection) selection).isEmpty());
	}

	private static List<IInterfaceElement> checkSelection(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structSel = (IStructuredSelection) selection;
			@SuppressWarnings("unchecked")
			List<IInterfaceElement> ieList = (List<IInterfaceElement>) structSel.toList().stream()
					.filter(val -> (val instanceof InterfaceEditPartForFBNetwork))
					.map(val -> ((InterfaceEditPartForFBNetwork) val).getModel()).collect(Collectors.toList());

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
		return Collections.emptyList();
	}

	private static boolean checkSelectedIEs(IInterfaceElement element1, IInterfaceElement element2) {
		if ((!element2.isIsInput()) && (element1.getClass().equals(element2.getClass()))
				&& element1.getFBNetworkElement().equals(element2.getFBNetworkElement())) {
			// the second element is an output, both are of the same type, and both are
			// interface elements from the same
			// fbnetworkelement instance
			if (!element1.getInputConnections().isEmpty() && !element2.getOutputConnections().isEmpty()) {
				if (element1 instanceof VarDeclaration) {
					return LinkConstraints.typeCheck((VarDeclaration) element1, (VarDeclaration) element2);
				}
				return true;
			}
		}
		return false;
	}

}
