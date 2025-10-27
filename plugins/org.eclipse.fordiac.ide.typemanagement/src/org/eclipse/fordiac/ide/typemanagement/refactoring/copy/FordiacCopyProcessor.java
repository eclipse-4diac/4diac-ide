/*******************************************************************************
 * Copyright (c) 2025 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.typemanagement.refactoring.copy;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.NullChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.CopyArguments;
import org.eclipse.ltk.core.refactoring.participants.CopyParticipant;
import org.eclipse.ltk.core.refactoring.participants.CopyProcessor;
import org.eclipse.ltk.core.refactoring.participants.ParticipantManager;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
import org.eclipse.ltk.core.refactoring.participants.ReorgExecutionLog;
import org.eclipse.ltk.core.refactoring.participants.SharableParticipants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public final class FordiacCopyProcessor extends CopyProcessor {

	private enum ExistsResolve {
		OVERWRITE, DONT_COPY, RENAME, CANCEL_ALL;

		private String newName;

		public ExistsResolve setNewName(final String newName) {
			this.newName = newName;
			return this;
		}

		public String getNewName() {
			return newName;
		}
	}

	private final IResource[] files;
	private final IPath[] copies;
	private final IContainer destination;
	private final ReorgExecutionLog log;
	private final Shell shell;
	private boolean canceled = false;

	public FordiacCopyProcessor(final IResource[] files, final IContainer destination, final Shell shell) {
		this.files = files;
		this.destination = destination;
		this.shell = shell;
		this.copies = new IPath[files.length];
		log = new ReorgExecutionLog();
	}

	@Override
	public RefactoringStatus checkInitialConditions(final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		for (int i = 0; i < files.length; i++) {
			final IResource file = files[i];
			final ExistsResolve resolve = handleAlreadyExists(file);

			if (resolve == ExistsResolve.RENAME) {
				copies[i] = destination.getFullPath().append(resolve.getNewName());
			} else if (resolve == ExistsResolve.OVERWRITE) {
				copies[i] = destination.getFullPath().append(file.getName());
			} else if (resolve == ExistsResolve.CANCEL_ALL) {
				canceled = true;
				break;
			}
		}
		return new RefactoringStatus();
	}

	@Override
	public RefactoringStatus checkFinalConditions(final IProgressMonitor pm, final CheckConditionsContext context)
			throws CoreException, OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		if (canceled) {
			return new NullChange();
		}

		final CompositeChange compChange = new CompositeChange(Messages.FordiacCopyProcessor_CompositeChangeName);

		for (int i = 0; i < files.length; i++) {
			if (copies[i] == null) {
				continue;
			}
			compChange.add(new CopyResourceChange(files[i], copies[i], destination));
		}
		return compChange;
	}

	@Override
	public RefactoringParticipant[] loadParticipants(final RefactoringStatus status,
			final SharableParticipants sharedParticipants) throws CoreException {
		if (canceled) {
			return null;
		}

		final String[] affectedNatures = SystemManager.getNatureIDs();
		final List<CopyParticipant> result = new ArrayList<>();

		for (int i = 0; i < files.length; i++) {
			if (copies[i] == null) {
				continue;
			}
			final CopyParticipant[] participants = ParticipantManager.loadCopyParticipants(status, this, files[i],
					new CopyArguments(copies[i], log), affectedNatures, sharedParticipants);
			result.addAll(Arrays.asList(participants));
		}
		return result.toArray(new RefactoringParticipant[result.size()]);
	}

	@Override
	public Object[] getElements() {
		return files;
	}

	@Override
	public String getIdentifier() {
		return "org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer.copyProcessor"; //$NON-NLS-1$
	}

	@Override
	public String getProcessorName() {
		return Messages.FordiacCopyProcessor_Name;
	}

	@Override
	public boolean isApplicable() throws CoreException {
		return true;
	}

	public static boolean areEqualInWorkspaceOrOnDisk(final IResource r1, final IResource r2) {
		if (r1 == null || r2 == null) {
			return false;
		}
		if (r1.equals(r2)) {
			return true;
		}
		final URI r1Location = r1.getLocationURI();
		final URI r2Location = r2.getLocationURI();
		if (r1Location == null || r2Location == null) {
			return false;
		}
		return r1Location.equals(r2Location);
	}

	private ExistsResolve handleAlreadyExists(final IResource file) {
		final IResource current = destination.findMember(file.getName());
		if (current == null || !current.exists()) {
			return ExistsResolve.OVERWRITE;
		}

		if (areEqualInWorkspaceOrOnDisk(file, current)) {
			return queryUserRename(file);
		}

		if (current instanceof IFolder || !(current instanceof IFile)) {
			return ExistsResolve.OVERWRITE;
		}
		return queryUserOverwrite(file); // overwrite, rename or cancel
	}

	private ExistsResolve queryUserOverwrite(final IResource file) {
		final StringBuilder msg = new StringBuilder(Messages.Copy_OverwriteDialog_Message);
		msg.append(System.lineSeparator()).append(System.lineSeparator());
		msg.append(Messages.Copy_OverwriteDialog_Destination).append(destination.getLocation().append(file.getName()));
		msg.append(System.lineSeparator()).append(System.lineSeparator());
		msg.append(Messages.Copy_OverwriteDialog_Source).append(file.getLocation());

		final int result = MessageDialog.open(MessageDialog.WARNING, shell, Messages.Copy_OverwriteDialog_Title,
				msg.toString(), SWT.NONE, Messages.Copy_OverwriteDialog_Yes, Messages.Copy_OverwriteDialog_No,
				Messages.Copy_OverwriteDialog_Rename, Messages.Copy_OverwriteDialog_Cancel);

		return switch (result) {
		case 0 -> ExistsResolve.OVERWRITE;
		case 1 -> ExistsResolve.DONT_COPY;
		case 2 -> queryUserRename(file);
		default -> ExistsResolve.CANCEL_ALL;
		};
	}

	@SuppressWarnings("unused")
	private ExistsResolve queryUserRename(final IResource file) {
		final StringBuilder msg = new StringBuilder(Messages.Copy_RenameDialog_Message);
		msg.append(file.getName());
		msg.append('\'');

		final int lastIndex = file.getName().lastIndexOf(file.getFileExtension());
		final String nameWithoutExt = file.getName().substring(0, lastIndex - 1);

		final String initial = nameWithoutExt + "_2." + file.getFileExtension(); //$NON-NLS-1$

		final IInputValidator validator = string -> {
			final IWorkspace workspace = file.getWorkspace();
			if (file.getName().equals(string)) {
				return Messages.Copy_RenameDialog_MustBeNewName;
			}
			final IStatus status = workspace.validateName(string, file.getType());
			if (!status.isOK()) {
				return status.getMessage();
			}
			if (workspace.getRoot().exists(destination.getFullPath().append(string))) {
				return Messages.Copy_RenameDialog_NameAlreadyExists;
			}
			return null;
		};

		final InputDialog dialog = new InputDialog(shell, Messages.Copy_RenameDialog_Title, msg.toString(), initial,
				validator) {
			@Override
			protected Control createContents(final Composite parent) {
				final Control contents = super.createContents(parent);
				getText().setSelection(0, lastIndex + 1);
				return contents;
			}
		};
		final int result = dialog.open();
		if (result == 0) {
			return ExistsResolve.RENAME.setNewName(dialog.getValue());
		}
		return ExistsResolve.CANCEL_ALL;
	}
}
