/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.text.MessageFormat;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.systemmanagement.changelistener.FordiacResourceChangeListener;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.FileEditorInput;

public class UpdateTypeEntryChange extends Change {

	protected final IFile file;
	protected final TypeEntry typeEntry;
	protected final String newName;
	protected final String oldName;

	public UpdateTypeEntryChange(final IFile file, final TypeEntry typeEntry, final String newName,
			final String oldName) {
		this.file = file;
		this.typeEntry = typeEntry;
		this.newName = newName;
		this.oldName = oldName;
	}

	@Override
	public String getName() {
		return Messages.Refactoring_UpdateTypeEntryChange;
	}

	@Override
	public void initializeValidationData(final IProgressMonitor pm) {
		// nothing to validate here
	}

	@Override
	public RefactoringStatus isValid(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		checkEditor(status, typeEntry, typeEntry.getTypeName());
		return status;
	}

	public void checkEditor(final RefactoringStatus result, final TypeEntry typeEntry, final String oldName) {
		// depending if the in-place renaming is active we may not be in the display
		// thread
		Display.getDefault().syncExec(() -> {
			final IEditorPart findEditor = EditorUtils.findEditor(
					(final IEditorPart editor) -> editor.getEditorInput() instanceof final FileEditorInput fei
							&& fei.getFile().equals(typeEntry.getFile()));
			if (findEditor != null && findEditor.isDirty()) {
				if (shouldSaveFile(findEditor.getSite().getShell(), oldName)) {
					findEditor.doSave(new NullProgressMonitor());
				} else {
					result.addFatalError("Abort rename as editor is dirty!"); //$NON-NLS-1$
				}
			}
		});
	}

	@SuppressWarnings("static-method")
	protected boolean shouldSaveFile(final Shell shell, final String oldName) {
		final int result = MessageDialog.open(MessageDialog.QUESTION, shell, "Rename of Type with unsaved changes!", //$NON-NLS-1$
				MessageFormat.format(
						"There are unsaved changes for type \"{0}\". Do you want to save them before renaming?", //$NON-NLS-1$
						oldName),
				SWT.NONE, "Save", "Cancel"); //$NON-NLS-1$//$NON-NLS-2$
		return result == 0;
	}

	@Override
	public Change perform(final IProgressMonitor pm) throws CoreException {
		final IFile newFile = findNewResource(newName);
		if (newFile != null) {
			FordiacResourceChangeListener.updateTypeEntryByRename(newFile, typeEntry);
			return new UpdateTypeEntryChange(newFile, typeEntry, oldName, newName);
		}
		return null;
	}

	@Override
	public Object getModifiedElement() {
		return file;
	}

	private IFile findNewResource(final String newName) {
		final String fileEnding = file.getFullPath().getFileExtension();
		IPath newPath;
		if (fileEnding != null) {
			newPath = file.getFullPath().removeLastSegments(1).append(newName + "." + fileEnding); //$NON-NLS-1$
		} else {
			newPath = file.getFullPath().removeLastSegments(1).append(newName);

		}
		final IResource findMember = ResourcesPlugin.getWorkspace().getRoot().findMember(newPath);
		if (findMember instanceof final IFile newFile) {
			return newFile;
		}
		return null;
	}

	@Override
	public Object[] getAffectedObjects() {
		return new Object[] { file };
	}
}
