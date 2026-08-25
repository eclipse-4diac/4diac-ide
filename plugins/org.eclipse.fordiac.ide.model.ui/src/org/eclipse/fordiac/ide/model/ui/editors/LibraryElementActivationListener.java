/*******************************************************************************
 * Copyright (c) 2025 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.editors;

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.fordiac.ide.model.ui.Messages;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.PlainMessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPartService;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class LibraryElementActivationListener implements IPartListener, IWindowListener {

	private final IEditorPart editorPart;
	private final IPartService partService;
	private boolean ignoreUpdates;

	public LibraryElementActivationListener(final IEditorPart editorPart) {
		this.editorPart = editorPart;
		partService = editorPart.getSite().getWorkbenchWindow().getPartService();
		partService.addPartListener(this);
		PlatformUI.getWorkbench().addWindowListener(this);
	}

	public void dispose() {
		partService.removePartListener(this);
		PlatformUI.getWorkbench().removeWindowListener(this);
	}

	@Override
	public void windowActivated(final IWorkbenchWindow window) {
		window.getShell().getDisplay().asyncExec(() -> {
			final IWorkbenchPage activePage = window.getActivePage();
			if (activePage != null) {
				handleActivation(activePage.getActivePart());
			}
		});
	}

	@Override
	public void partActivated(final IWorkbenchPart part) {
		handleActivation(part);
	}

	protected void handleActivation(final IWorkbenchPart part) {
		// some editor activations (e.g., location changes) may result in a recursive
		// call of this method that can be ignored.
		if (part != editorPart || ignoreUpdates) {
			return;
		}
		try {
			ignoreUpdates = true;
			checkEditorActivated();
		} finally {
			ignoreUpdates = false;
		}
	}

	protected void checkEditorActivated() {
		if (!LibraryElementProvider.INSTANCE.isSynchronized(editorPart.getEditorInput())) {
			handleEditorInputChanged(editorPart.getEditorInput());
		}
	}

	protected void handleEditorInputChanged(final IEditorInput editorInput) {
		final PlainMessageDialog replaceContentDialog = PlainMessageDialog
				.getBuilder(getShell(), Messages.LibraryElementActivationListener_FileChangedTitle)
				.message(MessageFormat.format(Messages.LibraryElementActivationListener_FileChangedMessage,
						editorInput.getToolTipText()))
				.buttonLabels(List.of(Messages.LibraryElementActivationListener_ReplaceContentButton,
						Messages.LibraryElementActivationListener_IgnoreChangeButton))
				.build();
		if (replaceContentDialog.open() != 0) {
			return;
		}

		try {
			LibraryElementProvider.INSTANCE.synchronize(editorInput, new NullProgressMonitor());
		} catch (final CoreException e) {
			ErrorDialog.openError(getShell(), Messages.LibraryElementActivationListener_SyncErrorTitle, MessageFormat
					.format(Messages.LibraryElementActivationListener_SyncErrorMessage, editorInput.getToolTipText()),
					e.getStatus());
		}
	}

	protected final Shell getShell() {
		final IWorkbenchPartSite site = editorPart.getSite();
		if (site != null) {
			final IWorkbenchWindow workbenchWindow = site.getWorkbenchWindow();
			if (workbenchWindow != null) {
				return workbenchWindow.getShell();
			}
		}
		return Display.getCurrent().getActiveShell();
	}

	public IEditorPart getEditorPart() {
		return editorPart;
	}

	@Override
	public void windowDeactivated(final IWorkbenchWindow window) {
		// nothing to do
	}

	@Override
	public void windowClosed(final IWorkbenchWindow window) {
		// nothing to do
	}

	@Override
	public void windowOpened(final IWorkbenchWindow window) {
		// nothing to do
	}

	@Override
	public void partBroughtToTop(final IWorkbenchPart part) {
		// nothing to do
	}

	@Override
	public void partClosed(final IWorkbenchPart part) {
		// nothing to do
	}

	@Override
	public void partDeactivated(final IWorkbenchPart part) {
		// nothing to do
	}

	@Override
	public void partOpened(final IWorkbenchPart part) {
		// nothing to do
	}
}
