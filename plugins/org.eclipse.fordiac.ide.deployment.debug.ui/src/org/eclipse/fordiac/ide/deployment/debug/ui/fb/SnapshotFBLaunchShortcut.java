/*******************************************************************************
 * Copyright (c) 2026 Osoft e.U.
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
package org.eclipse.fordiac.ide.deployment.debug.ui.fb;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.ui.ILaunchShortcut2;
import org.eclipse.fordiac.ide.deployment.debug.ui.Messages;
import org.eclipse.fordiac.ide.deployment.debug.ui.handler.SnapshotDebugHandler;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

/**
 * Debugs the selected function block instance with the values it currently has
 * in the runtime.
 */
public class SnapshotFBLaunchShortcut implements ILaunchShortcut2 {

	@Override
	public void launch(final ISelection selection, final String mode) {
		if (selection instanceof final IStructuredSelection structuredSelection) {
			final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			try {
				new SnapshotDebugHandler().execute(structuredSelection, shell);
			} catch (final ExecutionException e) {
				ErrorDialog.openError(shell, Messages.SnapshotDebugHandler_Title, null,
						Status.error(e.getMessage(), e));
			}
		}
	}

	@Override
	public void launch(final IEditorPart editor, final String mode) {
		final ISelectionProvider provider = editor.getSite().getSelectionProvider();
		if (provider != null) {
			launch(provider.getSelection(), mode);
		}
	}

	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(final ISelection selection) {
		// no existing launch configuration, so that every launch reads new values
		return new ILaunchConfiguration[0];
	}

	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(final IEditorPart editorpart) {
		return new ILaunchConfiguration[0];
	}

	@Override
	public IResource getLaunchableResource(final ISelection selection) {
		return null;
	}

	@Override
	public IResource getLaunchableResource(final IEditorPart editorpart) {
		return null;
	}
}
