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
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.copy.FordiacCopyProcessor.ExistsResolve;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

public class UserCopyRefactoringQueries implements ICopyRefactoringQueries {

	private final Shell shell;

	public UserCopyRefactoringQueries(final Shell shell) {
		this.shell = shell;
	}

	@Override
	public ExistsResolve queryOverwrite(final IResource file, final IContainer destination) {
		return queryUserOverwrite(file, destination);
	}

	@Override
	public ExistsResolve queryOverwriteSelf(final IResource file, final IContainer destination) {
		return queryUserOverwrite(file, destination);
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
}
