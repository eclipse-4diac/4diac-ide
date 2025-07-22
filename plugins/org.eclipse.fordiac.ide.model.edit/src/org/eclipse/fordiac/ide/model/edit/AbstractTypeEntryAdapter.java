/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.model.edit;

import java.text.MessageFormat;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPartService;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public abstract class AbstractTypeEntryAdapter extends AdapterImpl {

	private final IEditorPart editor;
	private ActivationListener activationListener;
	private boolean reloadPending;

	protected abstract void reloadEditorType();

	public void dispose() {
		if (activationListener != null) {
			activationListener.dispose();
			activationListener = null;
		}
	}

	protected IEditorPart getEditor() {
		return editor;
	}

	protected AbstractTypeEntryAdapter(final IEditorPart editor, final IPartService partService) {
		this.editor = editor;
		activationListener = new ActivationListener(partService);
	}

	/**
	 * This method performs any updates / reloads required when the editor of this
	 * adapter is activated.
	 */
	protected void checkEditorActivated() {
		if (reloadPending) {
			performReload();
		}
	}

	protected void handleFileContentChange() {
		if (isActiveEditor()) {
			performReload();
		} else {
			reloadPending = true;
		}
	}

	protected boolean isActiveEditor() {
		return !editorClosed() && getEditor().equals(getEditor().getSite().getPage().getActiveEditor());
	}

	protected boolean editorClosed() {
		return getEditor().getSite().getPage() == null;
	}

	private void performReload() {
		Display.getDefault().asyncExec(() -> {
			if (!editorClosed() && (!getEditor().isDirty() || openFileChangedDialog() == 0)) {
				reloadEditorType();
			}
		});
		reloadPending = false;
	}

	protected int openFileChangedDialog() {
		final String message = MessageFormat.format(Messages.TypeEntryEditor_FileChanged_message,
				getEditor().getEditorInput().getName());
		final MessageDialog dialog = new MessageDialog(getEditor().getSite().getShell(),
				Messages.TypeEntryEditor_FileChangedTitle, null, message, MessageDialog.QUESTION,
				new String[] { Messages.TypeEntryEditor_replace_button_label,
						Messages.TypeEntryEditor_dontreplace_button_label },
				0);

		return dialog.open();
	}

	class ActivationListener implements IPartListener, IWindowListener {

		private IPartService partService;
		private boolean ignoreUpdates;

		public ActivationListener(final IPartService partService) {
			this.partService = partService;
			partService.addPartListener(this);
			PlatformUI.getWorkbench().addWindowListener(this);
		}

		public void dispose() {
			partService.removePartListener(this);
			PlatformUI.getWorkbench().removeWindowListener(this);
			partService = null;
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

		private void handleActivation(final IWorkbenchPart part) {
			if (getEditor() == part && !ignoreUpdates) {
				// some editor activations (e.g., location changes) may result in a recursive
				// call of this method that can be ignored.
				ignoreUpdates = true;
				checkEditorActivated();
				ignoreUpdates = false;
			}
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

	}
}
