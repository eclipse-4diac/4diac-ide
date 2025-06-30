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
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.UpdateTypeEntryChange;
import org.eclipse.fordiac.ide.ui.editors.FordiacEditorMatchingStrategy;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

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
			final TypeLibrary typeLibrary = typeEntry.getTypeLibrary();
			if (typeLibrary != null) {
				typeLibrary.removeTypeEntry(typeEntry);
			}

			if (!destinationFile.getProject().equals(file.getProject())) {
				// different project - discard type entry
				final var entry = TypeLibraryManager.INSTANCE.getTypeLibrary(destinationFile.getProject())
						.createTypeEntry(destinationFile);
				closeEditors(file);
				return new UpdateTypeEntryFileChange(destinationFile, entry, file);
			}
			typeEntry.setFile(destinationFile);
			if (typeLibrary != null) {
				typeLibrary.addTypeEntry(typeEntry);
			}
			// returns undo change
			return new UpdateTypeEntryFileChange(destinationFile, typeEntry, file);
		}
		return null;
	}

	private static FordiacEditorMatchingStrategy editorMatching = new FordiacEditorMatchingStrategy();

	private static void closeEditors(final IFile src) {
		Display.getDefault().asyncExec(() -> {
			final IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			final IEditorReference[] editorReferences = activePage.getEditorReferences();

			for (final IEditorReference editorReference : editorReferences) {
				// close editors that point to the old file
				if (editorMatching.matches(editorReference, new FileEditorInput(src))) {
					activePage.closeEditors(new IEditorReference[] { editorReference }, false);
				}
			}
		});
	}
}
