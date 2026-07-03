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
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.navigator.ICommonViewerSite;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;

public class SystemExplorerEditActionProvider extends CommonActionProvider {

	private IAction copyPackageAction;

	private Clipboard clipboard;

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
		clipboard = new Clipboard(viewSite.getShell().getDisplay());

		if (viewSite instanceof final ICommonViewerWorkbenchSite workbenchSite) {
			copyPackageAction = new CopyPackageNameAction(workbenchSite.getSelectionProvider());
		}
	}

	@Override
	public void fillContextMenu(final IMenuManager menu) {
		if (copyPackageAction.isEnabled()) {
			menu.appendToGroup(ICommonMenuConstants.GROUP_EDIT, copyPackageAction);
		}
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
