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
 * 	Mario Kastner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.library.ui.wizards.ManageLibraryWizard;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class OpenManageLibraryWizardHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);

		final var shell = (editor != null) ? editor.getSite().getShell() : HandlerUtil.getActiveShell(event);

		final IStructuredSelection selection = HandlerUtil.getCurrentStructuredSelection(event);
		final IProject project = getProject(selection);

		ManageLibraryWizard.openDialog(project, shell);

		return Status.OK_STATUS;
	}

	private static IProject getProject(final IStructuredSelection selection) {
		if (selection != null && !selection.isEmpty()
				&& selection.getFirstElement() instanceof final IResource resource) {
			return resource.getProject();
		}
		return null;
	}

}
