/*******************************************************************************
 * Copyright (c) 2000, 2025 IBM Corporation and others.
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
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
 *     Andrey Loskutov <loskutov@gmx.de> - generified interface, bug 462760
 *     Felix Schmid - add copy refactoring calls
 *     Michael Oberlehner - added 4diac project specific copy handling
 *******************************************************************************/

package org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.fordiac.ide.typemanagement.refactoring.copy.FordiacCopyProcessor;
import org.eclipse.fordiac.ide.typemanagement.refactoring.copy.UserCopyRefactoringQueries;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CheckConditionsOperation;
import org.eclipse.ltk.core.refactoring.CreateChangeOperation;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CopyFilesAndFoldersOperation;
import org.eclipse.ui.actions.SelectionListenerAction;
import org.eclipse.ui.internal.navigator.resources.plugin.WorkbenchNavigatorMessages;
import org.eclipse.ui.part.ResourceTransfer;

/**
 * Standard action for pasting resources on the clipboard to the selected
 * resource's location.
 * <p>
 * This class may be instantiated; it is not intended to be subclassed.
 * </p>
 *
 * @since 2.0
 */
/* package */class SystemExplorerPasteAction extends SelectionListenerAction {

	/**
	 * The id of this action.
	 */
	public static final String ID = PlatformUI.PLUGIN_ID + ".PasteAction";//$NON-NLS-1$

	/**
	 * The shell in which to show any dialogs.
	 */
	private final Shell shell;

	/**
	 * System clipboard
	 */
	private final Clipboard clipboard;

	/**
	 * Creates a new action.
	 *
	 * @param shell     the shell for any dialogs
	 * @param clipboard the clipboard
	 */
	@SuppressWarnings("restriction")
	public SystemExplorerPasteAction(final Shell shell, final Clipboard clipboard) {
		super(WorkbenchNavigatorMessages.PasteAction_Past_);
		Assert.isNotNull(shell);
		Assert.isNotNull(clipboard);
		this.shell = shell;
		this.clipboard = clipboard;
		setToolTipText(WorkbenchNavigatorMessages.PasteAction_Paste_selected_resource_s_);
		setId(SystemExplorerPasteAction.ID);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this, "HelpId"); //$NON-NLS-1$
	}

	/**
	 * Returns the actual target of the paste action. Returns null if no valid
	 * target is selected.
	 *
	 * @param clipboardContent current content of the clipboard
	 *
	 * @return the actual target of the paste action
	 */
	private IResource getTarget(final IResource[] clipboardContent) {
		final List<? extends IResource> selectedResources = getSelectedResources();

		// selection is copied to itself => copy to parent
		if (clipboardContent != null && areEqualsUnordered(selectedResources, Arrays.asList(clipboardContent))) {
			return selectedResources.get(0).getParent();
		}

		for (IResource resource : selectedResources) {
			if (resource instanceof final IProject proj && !proj.isOpen()) {
				return null;
			}
			if (resource.getType() == IResource.FILE) {
				resource = resource.getParent();
			}
			if (resource != null) {
				return resource;
			}
		}
		return null;
	}

	/**
	 * @param a The first collection
	 * @param b The second collection
	 * @return true if both collections aren't null and they contain the exact same
	 *         items, regardless of their order.
	 */
	private static boolean areEqualsUnordered(final Collection<? extends IResource> a,
			final Collection<? extends IResource> b) {
		return b != null && a != null && !a.isEmpty() // they are not empty...
				&& a.size() == b.size() // ... and they have the same size
				&& a.containsAll(b); // ... and all elements of A are in B
	}

	/**
	 * Returns whether any of the given resources are linked resources.
	 *
	 * @param resources resource to check for linked type. may be null
	 * @return true=one or more resources are linked. false=none of the resources
	 *         are linked
	 */
	private static boolean isLinked(final IResource[] resources) {
		for (final IResource resource : resources) {
			if (resource.isLinked()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Implementation of method defined on <code>IAction</code>.
	 */
	@Override
	public void run() {
		// try a resource transfer
		final ResourceTransfer resTransfer = ResourceTransfer.getInstance();
		final IResource[] resourceData = (IResource[]) clipboard.getContents(resTransfer);

		if (resourceData != null && resourceData.length > 0) {
			final IContainer container = getContainer(resourceData);
			startCopyRefactoring(resourceData, container, shell);
			return;
		}

		// try a file transfer
		final FileTransfer fileTransfer = FileTransfer.getInstance();
		final String[] fileData = (String[]) clipboard.getContents(fileTransfer);

		if (fileData != null) {
			// enablement should ensure that we always have access to a container
			final IContainer container = getContainer(null);
			final CopyFilesAndFoldersOperation operation = new CopyFilesAndFoldersOperation(shell);
			operation.copyFiles(fileData, container);
		}
	}

	private static void startCopyRefactoring(final IResource[] files, final IContainer destination, final Shell shell) {
		final var refactorQueries = new UserCopyRefactoringQueries(shell);
		final var processor = new FordiacCopyProcessor(files, destination, refactorQueries);
		final var refactoring = new ProcessorBasedRefactoring(processor);
		final var checkOp = new CheckConditionsOperation(refactoring, CheckConditionsOperation.ALL_CONDITIONS);
		final var changeOp = new CreateChangeOperation(checkOp, RefactoringStatus.ERROR);

		try {
			final IProgressMonitor pm = new NullProgressMonitor();
			changeOp.run(pm);
			final Change change = changeOp.getChange();
			if (change != null) {
				change.perform(pm);
			} else {
				FordiacLogHelper.logWarning("copy refactoring change could not be created"); //$NON-NLS-1$
			}
		} catch (final CoreException e) {
			ErrorDialog.openError(shell, null, null, e.getStatus());
		}
	}

	/**
	 * Returns the container to hold the pasted resources.
	 *
	 * @param clipboardContent current content of the clipboard
	 */
	private IContainer getContainer(final IResource[] clipboardContent) {
		final List<? extends IResource> selection = getSelectedResources();

		// selection is copied to itself => copy to parent
		if (clipboardContent != null && areEqualsUnordered(selection, Arrays.asList(clipboardContent))) {
			return selection.get(0).getParent();
		}

		if (selection.get(0) instanceof IFile) {
			return selection.get(0).getParent();
		}
		return (IContainer) selection.get(0);
	}

	/**
	 * The <code>PasteAction</code> implementation of this
	 * <code>SelectionListenerAction</code> method enables this action if a resource
	 * compatible with what is on the clipboard is selected.
	 *
	 * -Clipboard must have IResource or java.io.File -Projects can always be pasted
	 * if they are open -Workspace folder may not be copied into itself -Files and
	 * folders may be pasted to a single selected folder in open project or multiple
	 * selected files in the same folder
	 */
	@SuppressWarnings("null")
	@Override
	protected boolean updateSelection(final IStructuredSelection selection) {
		if (!super.updateSelection(selection)) {
			return false;
		}

		final IResource[][] clipboardData = new IResource[1][];
		shell.getDisplay().syncExec(() -> {
			// clipboard must have resources or files
			final ResourceTransfer resTransfer = ResourceTransfer.getInstance();
			clipboardData[0] = (IResource[]) clipboard.getContents(resTransfer);
		});
		final IResource[] resourceData = clipboardData[0];
		final boolean isProjectRes = resourceData != null && resourceData.length > 0
				&& resourceData[0].getType() == IResource.PROJECT;

		if (isProjectRes) {
			for (final IResource resource : resourceData) {
				// make sure all resource data are open projects
				// can paste open projects regardless of selection
				if (resource.getType() != IResource.PROJECT || !((IProject) resource).isOpen()) {
					return false;
				}
			}
			return true;
		}

		if (!getSelectedNonResources().isEmpty()) {
			return false;
		}

		final IResource targetResource = getTarget(resourceData);
		// targetResource is null if no valid target is selected (e.g., open project)
		// or selection is empty
		if (targetResource == null) {
			return false;
		}

		// can paste files and folders to a single selection (file, folder,
		// open project) or multiple file/folder selection with the same parent
		final List<? extends IResource> selectedResources = getSelectedResources();
		if (selectedResources.size() > 1) {
			if (!selectionIsOfType(IResource.FILE | IResource.FOLDER)) {
				return false;
			}
			for (final IResource resource : selectedResources) {
				if (!targetResource.equals(resource.getParent())) {
					return false;
				}
			}
		}
		if (resourceData != null) {
			// linked resources can only be pasted into projects
			return !(isLinked(resourceData) && targetResource.getType() != IResource.PROJECT
					&& targetResource.getType() != IResource.FOLDER);
		}
		final TransferData[] transfers = clipboard.getAvailableTypes();
		final FileTransfer fileTransfer = FileTransfer.getInstance();
		for (final TransferData transfer : transfers) {
			if (fileTransfer.isSupportedType(transfer)) {
				return true;
			}
		}
		return false;
	}
}
