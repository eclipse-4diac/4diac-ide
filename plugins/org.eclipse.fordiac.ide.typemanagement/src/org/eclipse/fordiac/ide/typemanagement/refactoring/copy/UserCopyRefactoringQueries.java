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
 *   Felix Schmid - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.typemanagement.refactoring.copy;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.copy.FordiacCopyProcessor.ExistsResolve;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class UserCopyRefactoringQueries implements ICopyRefactoringQueries {

	private final Shell shell;

	public UserCopyRefactoringQueries(final Shell shell) {
		this.shell = shell;
	}

	@Override
	public ExistsResolve queryOverwrite(final IResource file, final IContainer destination) {
		final ExistsResolve[] result = new ExistsResolve[1];
		shell.getDisplay().syncExec(() -> {
			result[0] = queryUserOverwrite(file, destination);
		});
		return result[0];
	}

	private ExistsResolve queryUserOverwrite(final IResource file, final IContainer destination) {
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
		case 2 -> queryUserOverwriteSelf(file, destination);
		default -> ExistsResolve.CANCEL_ALL;
		};
	}

	@Override
	public ExistsResolve queryOverwriteSelf(final IResource file, final IContainer destination) {
		final ExistsResolve[] result = new ExistsResolve[1];
		shell.getDisplay().syncExec(() -> {
			result[0] = queryUserOverwriteSelf(file, destination);
		});
		return result[0];
	}

	@SuppressWarnings("unused")
	private ExistsResolve queryUserOverwriteSelf(final IResource file, final IContainer destination) {
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
