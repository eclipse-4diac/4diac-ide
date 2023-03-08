/*******************************************************************************
 * Copyright (c) 2008 - 2016 Profactor Gmbh, TU Wien ACIN, fortiss GmbH
 *               2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
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
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		final List<IProject> projectSelection = getSelectedProjects(selection);
		runDeleteAction(projectSelection);
		return null;
	}

	private static void runDeleteAction(final List<IProject> projectSelection) {
		final DeleteResourceAction action = new DeleteResourceAction(() -> Display.getDefault().getActiveShell());
		action.selectionChanged(new StructuredSelection(projectSelection));
		action.run();
	}

	private static List<IProject> getSelectedProjects(final ISelection selection) {
		final List<IProject> projectSelection = new ArrayList<>();
		if (selection instanceof IStructuredSelection) {
			for (final Object element : ((StructuredSelection) selection).toList()) {
				if (element instanceof AutomationSystem) {
					projectSelection.add(((AutomationSystem) element).getTypeLibrary().getProject());
				} else if (element instanceof IProject) {
					projectSelection.add((IProject) element);
				}
			}
		}
		return projectSelection;
	}
}
