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
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor.handler;

import java.text.MessageFormat;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.bulkeditor.Messages;
import org.eclipse.fordiac.ide.bulkeditor.editors.BulkEditor;
import org.eclipse.fordiac.ide.bulkeditor.editors.BulkEditorInput;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ui.IEditorLauncher;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

/**
 * Opens query files in the bulk editor of their project, e.g., by a double
 * click in the system explorer.
 */
public class QueryFileLauncher implements IEditorLauncher {

	@Override
	public void open(final IPath file) {
		final IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		final IFile queryFile = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(file);
		if (queryFile == null || !queryFile.getProject().isOpen()) {
			showError(window, Status.error(MessageFormat.format(Messages.QueryFileLauncher_NotInProject, file)));
			return;
		}
		try {
			final IWorkbenchPage page = window.getActivePage();
			if (page.openEditor(new BulkEditorInput(queryFile.getProject()),
					BulkEditor.ID) instanceof final BulkEditor bulkEditor) {
				bulkEditor.openQueryFile(URI.createFileURI(file.toOSString()));
			}
		} catch (final PartInitException e) {
			showError(window, e.getStatus());
		}
	}

	private static void showError(final IWorkbenchWindow window, final IStatus status) {
		ErrorDialog.openError(window.getShell(), null, null, status);
	}
}
