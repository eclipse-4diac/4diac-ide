/*******************************************************************************
 * Copyright (c) 2015 fortiss GmbH
 *               2019 Johannes Kepler University Linz
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl, Bianca Wiesmayr, Virendra Ashiwal
 *     - close multiple systems at once fix
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.CloseResourceAction;
import org.eclipse.ui.ide.IDEActionFactory;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;

public class CloseSystemActionProvider extends CommonActionProvider {

	private CloseResourceAction closeProjectAction;

	@Override
	public void init(ICommonActionExtensionSite aSite) {
		super.init(aSite);

		IShellProvider sp = new IShellProvider() {
			@Override
			public Shell getShell() {
				return getActionSite().getViewSite().getShell();
			}
		};
		closeProjectAction = new CloseResourceAction(sp);
	}

	@Override
	public void fillActionBars(IActionBars actionBars) {
		actionBars.setGlobalActionHandler(IDEActionFactory.CLOSE_PROJECT.getId(), closeProjectAction);
		updateActionBars();
	}

	@Override
	public void fillContextMenu(IMenuManager menu) {
		IStructuredSelection selection = getSelectedProjects();
		if (!selection.isEmpty()) {
			closeProjectAction.selectionChanged(selection);
			menu.appendToGroup(ICommonMenuConstants.GROUP_BUILD, closeProjectAction);
		}
	}

	@Override
	public void updateActionBars() {
		closeProjectAction.selectionChanged(getSelectedProjects());
	}

	private IStructuredSelection getSelectedProjects() {
		IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();
		List<IProject> projectSelection = new ArrayList<>();

		for (Object element : selection.toList()) {
			if (element instanceof AutomationSystem) {
				projectSelection.add(((AutomationSystem) element).getProject());
			} else if (element instanceof IProject) {
				projectSelection.add((IProject) element);
			}
		}

		return new StructuredSelection(projectSelection);
	}
}
