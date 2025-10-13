/*******************************************************************************
 * Copyright (c) 2006, 2025 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Felix Schmid
 *       - adapt class for custom copy/paste actions
 *       - add "copy package name" action
 ******************************************************************************/

package org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.systemmanagement.ui.Messages;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.DeleteResourceAction;
import org.eclipse.ui.ide.ResourceSelectionUtil;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.navigator.ICommonViewerSite;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;

public class SystemExplorerEditActionProvider extends CommonActionProvider {

	private IAction copyPackageAction;

	private Clipboard clipboard;

	private SystemExplorerCopyAction copyAction;

	private DeleteResourceAction deleteAction;

	private SystemExplorerPasteAction pasteAction;

	private Shell shell;

	@Override
	public void dispose() {
		if (clipboard != null) {
			clipboard.dispose();
			clipboard = null;
		}
		super.dispose();
	}

	@Override
	public void init(final ICommonActionExtensionSite aSite) {
		final ICommonViewerSite viewSite = aSite.getViewSite();
		shell = viewSite.getShell();

		makeEditActions();

		if (viewSite instanceof final ICommonViewerWorkbenchSite workbenchSite) {
			copyPackageAction = new CopyPackageNameAction(workbenchSite.getSelectionProvider());
		}
	}

	@Override
	public void fillContextMenu(final IMenuManager menu) {
		final IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();

		final boolean anyResourceSelected = !selection.isEmpty() && ResourceSelectionUtil
				.allResourcesAreOfType(selection, IResource.PROJECT | IResource.FOLDER | IResource.FILE);

		copyAction.selectionChanged(selection);
		menu.appendToGroup(ICommonMenuConstants.GROUP_EDIT, copyAction);
		if (copyPackageAction.isEnabled()) {
			menu.appendToGroup(ICommonMenuConstants.GROUP_EDIT, copyPackageAction);
		}
		pasteAction.selectionChanged(selection);
		menu.appendToGroup(ICommonMenuConstants.GROUP_EDIT, pasteAction);

		if (anyResourceSelected) {
			deleteAction.selectionChanged(selection);
			menu.appendToGroup(ICommonMenuConstants.GROUP_EDIT, deleteAction);
		}
	}

	@Override
	public void fillActionBars(final IActionBars actionBars) {
		actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(), copyAction);
		actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(), pasteAction);
		actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(), deleteAction);
		updateActionBars();
	}

	public void handleKeyPressed(final KeyEvent event) {
		if (event.character == SWT.DEL && event.stateMask == 0) {
			if (deleteAction.isEnabled()) {
				deleteAction.run();
			}

			// Swallow the event.
			event.doit = false;
		}
	}

	protected void makeEditActions() {
		clipboard = new Clipboard(shell.getDisplay());

		pasteAction = new SystemExplorerPasteAction(shell, clipboard);
		final ISharedImages images = PlatformUI.getWorkbench().getSharedImages();
		pasteAction.setDisabledImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE_DISABLED));
		pasteAction.setImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
		pasteAction.setActionDefinitionId(IWorkbenchCommandConstants.EDIT_PASTE);

		copyAction = new SystemExplorerCopyAction(shell, clipboard, pasteAction);
		copyAction.setDisabledImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_TOOL_COPY_DISABLED));
		copyAction.setImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		copyAction.setActionDefinitionId(IWorkbenchCommandConstants.EDIT_COPY);

		deleteAction = new DeleteResourceAction(() -> shell);
		deleteAction.setDisabledImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE_DISABLED));
		deleteAction.setImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
		deleteAction.setActionDefinitionId(IWorkbenchCommandConstants.EDIT_DELETE);
	}

	@Override
	public void updateActionBars() {
		final IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();

		copyAction.selectionChanged(selection);
		pasteAction.selectionChanged(selection);
		deleteAction.selectionChanged(selection);
	}

	// ------------------------------------------
	// custom action for copying the package name
	private class CopyPackageNameAction extends Action {

		private final ISelectionProvider selectionProvider;

		public CopyPackageNameAction(final ISelectionProvider selectionProvider) {
			setText(Messages.SystemExplorer_CopyPackageNameAction);
			final ISharedImages images = PlatformUI.getWorkbench().getSharedImages();
			setImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
			this.selectionProvider = selectionProvider;
		}

		@Override
		public boolean isEnabled() {
			return getTypeEntry() != null;
		}

		@Override
		public void run() {
			final var typeEntry = getTypeEntry();
			if (typeEntry == null) {
				return;
			}
			String packName = PackageNameHelper.getContainerPackageName(typeEntry.getType());
			if (packName == null || packName.isEmpty()) {
				packName = " "; //$NON-NLS-1$
			}
			clipboard.setContents(new Object[] { packName }, new Transfer[] { TextTransfer.getInstance() });
		}

		private TypeEntry getTypeEntry() {
			final ISelection selection = selectionProvider.getSelection();
			if (!selection.isEmpty() && selection instanceof final IStructuredSelection sel && sel.size() == 1
					&& sel.getFirstElement() instanceof final IFile file) {
				return TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);
			}
			return null;
		}
	}
}
