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
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.CloseResourceAction;
import org.eclipse.ui.ide.IDEActionFactory;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;

public class CloseSystemActionProvider extends CommonActionProvider {

	private CloseResourceAction closeProjectAction;

	@Override
	public void init(final ICommonActionExtensionSite aSite) {
		super.init(aSite);
		closeProjectAction = new CloseResourceAction(() -> getActionSite().getViewSite().getShell());
	}

	@Override
	public void fillActionBars(final IActionBars actionBars) {
		actionBars.setGlobalActionHandler(IDEActionFactory.CLOSE_PROJECT.getId(), closeProjectAction);
		updateActionBars();
	}

	@Override
	public void fillContextMenu(final IMenuManager menu) {
		final IStructuredSelection selection = getSelectedProjects();
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
		final IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();
		final List<IProject> projectSelection = new ArrayList<>();

		for (final Object element : selection.toList()) {
			switch (element) {
			case final AutomationSystem sys -> projectSelection.add(sys.getTypeEntry().getFile().getProject());
			case final IProject proj -> projectSelection.add(proj);
			default -> {
				/* no action required in the default case */ }
			}
		}

		return new StructuredSelection(projectSelection);
	}
}
