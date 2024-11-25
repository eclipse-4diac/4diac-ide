/*******************************************************************************
 * Copyright (c) 2014, 2015 fortiss GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.actions.RenameResourceAction;
import org.eclipse.ui.handlers.HandlerUtil;

public class RenameSystem extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection selection = HandlerUtil.getCurrentSelection(event);

		if ((selection instanceof TreeSelection)
				&& (((TreeSelection) selection).getFirstElement() instanceof AutomationSystem)) {
			final AutomationSystem system = (AutomationSystem) ((TreeSelection) selection).getFirstElement();
			final IProject project = system.getTypeLibrary().getProject();
			runRenameAction(project);
		}
		return null;
	}

	private static void runRenameAction(final IProject project) {
		final RenameResourceAction action = new RenameResourceAction(() -> Display.getDefault().getActiveShell());
		action.selectionChanged(new StructuredSelection(project));
		action.run();
	}
}
