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

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.copy.FordiacCopyProcessor.ExistsResolve;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class UserCopyRefactoringQueries implements ICopyRefactoringQueries {

	private final Shell shell;
	private static final Pattern ENDS_WITH_NUMBER = Pattern.compile("[0-9]+$"); //$NON-NLS-1$

	public UserCopyRefactoringQueries(final Shell shell) {
		this.shell = shell;
	}

	@Override
	public ExistsResolve queryOverwrite(final IResource file, final IContainer destination) {
		return queryUserOverwrite(file, destination);
	}

	@Override
	public ExistsResolve queryOverwriteSelf(final IResource file, final IContainer destination) {
		return queryUserOverwriteSelf(file, destination);
	}

	private ExistsResolve queryUserOverwrite(final IResource file, final IContainer destination) {
		final StringBuilder msg = new StringBuilder(Messages.Copy_OverwriteDialog_Message);
		msg.append(System.lineSeparator()).append(System.lineSeparator());
		msg.append(Messages.Copy_OverwriteDialog_Destination).append(destination.getLocation().append(file.getName()));
		msg.append(System.lineSeparator()).append(System.lineSeparator());
		msg.append(Messages.Copy_OverwriteDialog_Source).append(file.getLocation());

		final int[] result = new int[1];
		shell.getDisplay()
				.syncExec(() -> result[0] = MessageDialog.open(MessageDialog.WARNING, shell,
						Messages.Copy_OverwriteDialog_Title, msg.toString(), SWT.NONE, IDialogConstants.YES_LABEL,
						IDialogConstants.NO_LABEL, IDialogConstants.CANCEL_LABEL));
		return switch (result[0]) {
		case 0 -> ExistsResolve.OVERWRITE;
		case 1 -> ExistsResolve.DONT_COPY;
		default -> ExistsResolve.CANCEL_ALL;
		};
	}

	private ExistsResolve queryUserOverwriteSelf(final IResource file, final IContainer destination) {
		final String msg = MessageFormat.format(Messages.Copy_RenameDialog_Message, file.getName());

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

		final InputDialog dialog = new InputDialog(shell, Messages.Copy_RenameDialog_Title, msg,
				computeNewName(file.getName(), destination), validator) {
			@Override
			protected Control createContents(final Composite parent) {
				final Control contents = super.createContents(parent);
				final int lastIdx = getText().getText().lastIndexOf('.');
				if (lastIdx > 0) {
					getText().setSelection(0, lastIdx);
				}
				return contents;
			}
		};
		final int result = dialog.open();
		if (result == 0) {
			return ExistsResolve.RENAME.setNewName(dialog.getValue());
		}
		return ExistsResolve.CANCEL_ALL;
	}

	private static String computeNewName(final String oldName, final IContainer destination) {
		final int lastIdx = oldName.lastIndexOf('.');
		String fileExt = ""; //$NON-NLS-1$
		String fileNameWithoutExt = oldName;
		if (lastIdx > 0) {
			fileExt = oldName.substring(lastIdx);
			fileNameWithoutExt = oldName.substring(0, lastIdx);
		}

		final Matcher m = ENDS_WITH_NUMBER.matcher(fileNameWithoutExt);
		if (m.find()) {
			try {
				BigDecimal newNumber = new BigDecimal(m.group());
				String newName;
				do {
					newNumber = newNumber.add(BigDecimal.ONE);
					newName = m.replaceFirst(newNumber.toPlainString()) + fileExt;
				} while (destination.findMember(newName) != null);
				return newName;
			} catch (final NumberFormatException e) {
				// return default new name below
			}
		}
		return fileNameWithoutExt + "2" + fileExt; //$NON-NLS-1$
	}
}
