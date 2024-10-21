/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.move;

import java.text.MessageFormat;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.systemmanagement.changelistener.FordiacResourceChangeListener;
import org.eclipse.fordiac.ide.typemanagement.refactoring.UpdateTypeEntryChange;
import org.eclipse.fordiac.ide.typemanagement.wizards.Messages;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

public class UpdateTypeEntryFileChange extends UpdateTypeEntryChange {

	private final IFile destinationFile;

	public UpdateTypeEntryFileChange(final IFile currentFile, final TypeEntry typeEntry, final IFile destinationFile) {
		super(currentFile, typeEntry, typeEntry.getTypeName(), typeEntry.getTypeName());
		this.destinationFile = destinationFile;
	}

	@Override
	public String getName() {
		return Messages.MoveTypeToPackage_UpdateTypeEntryFile;
	}

	@Override
	protected boolean shouldSaveFile(final Shell shell, final String oldName) {
		final int result = MessageDialog.open(MessageDialog.QUESTION, shell, "Moving of Type with unsaved changes!", //$NON-NLS-1$
				MessageFormat.format(
						"There are unsaved changes for type \"{0}\". Do you want to save them before moving it?", //$NON-NLS-1$
						oldName),
				SWT.NONE, "Save", "Cancel"); //$NON-NLS-1$//$NON-NLS-2$
		return result == 0;
	}

	@Override
	public Change perform(final IProgressMonitor pm) throws CoreException {
		if (destinationFile != null) {
			FordiacResourceChangeListener.updateTypeEntry(destinationFile, typeEntry);
			// returns undo change
			return new UpdateTypeEntryFileChange(destinationFile, typeEntry, file);
		}
		return null;
	}
}
