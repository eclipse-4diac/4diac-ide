/*******************************************************************************
 * Copyright (c) 2008 - 2016 Profactor Gmbh, TU Wien ACIN, fortiss GmbH
 *               2019 Johannes Kepler University Linz
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl, Bianca Wiesmayr, Virendra Ashiwal
 *     - delete multiple projects in single dialogue
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.actions.DeleteResourceAction;
import org.eclipse.ui.handlers.HandlerUtil;

public class DeleteSystem extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		List<IProject> projectSelection = getSelectedProjects(selection);
		runDeleteAction(projectSelection);
		return null;
	}

	private static void runDeleteAction(List<IProject> projectSelection) {
		DeleteResourceAction action = new DeleteResourceAction(() -> Display.getDefault().getActiveShell());
		action.selectionChanged(new StructuredSelection(projectSelection));
		action.run();
	}

	private static List<IProject> getSelectedProjects(ISelection selection) {
		List<IProject> projectSelection = new ArrayList<>();
		if (selection instanceof IStructuredSelection) {
			for (Object element : ((StructuredSelection) selection).toList()) {
				if (element instanceof AutomationSystem) {
					projectSelection.add(((AutomationSystem) element).getProject());
				} else if (element instanceof IProject) {
					projectSelection.add((IProject) element);
				}
			}
		}
		return projectSelection;
	}
}
