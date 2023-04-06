/*******************************************************************************
 * Copyright (c) 2014, 2021 fortiss GmbH, Primetals Technologies GmbH
 *               2022 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Martin Jobst - fix name validation with proposed changes and child resources
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.text.MessageFormat;
import java.util.Optional;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.mapping.IResourceChangeDescriptionFactory;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;
import org.eclipse.ltk.core.refactoring.participants.ResourceChangeChecker;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.FileEditorInput;

public class RenameType extends RenameParticipant {

	private IFile oldFile;

	private String getOldName() {
		return oldFile.getName();
	}

	@Override
	protected boolean initialize(final Object element) {
		if (element instanceof IFile) {
			oldFile = ((IFile) element);
		}
		return (element instanceof IFile);
	}

	@Override
	public String getName() {
		return Messages.RenameType_Name;
	}

	@Override
	public RefactoringStatus checkConditions(final IProgressMonitor pm, final CheckConditionsContext context)
			throws OperationCanceledException {
		final RefactoringStatus result = new RefactoringStatus();
		checkEditor(result);
		final ResourceChangeChecker resChecker = context.getChecker(ResourceChangeChecker.class);
		final IResourceChangeDescriptionFactory deltaFactory = resChecker.getDeltaFactory();
		final IResourceDelta delta = deltaFactory.getDelta();
		verifyAffectedChildren(delta.getAffectedChildren(), result);
		return result;
	}

	private void verifyAffectedChildren(final IResourceDelta[] affectedChildren, final RefactoringStatus result) {
		for (final IResourceDelta resourceDelta : affectedChildren) {
			if (resourceDelta.getMovedToPath() != null && resourceDelta.getResource() instanceof IFile) {
				final IFile newFile = resourceDelta.getResource().getWorkspace().getRoot()
						.getFile(resourceDelta.getMovedToPath());
				if (nameExistsInTypeLibrary(newFile)) {
					result.addFatalError(MessageFormat.format(Messages.RenameType_TypeExists, newFile.getName()));
				}
				final String name = TypeEntry.getTypeNameFromFile(newFile);
				final Optional<String> error = IdentifierVerifier.verifyIdentifier(name);
				error.ifPresent(result::addFatalError);
			}
			verifyAffectedChildren(resourceDelta.getAffectedChildren(), result);
		}
	}

	protected boolean nameExistsInTypeLibrary(final IFile newFile) {
		return !getOldName().equals(newFile.getName()) && !newFile.getName().equals("a" + getOldName()) //$NON-NLS-1$
				&& TypeLibraryManager.INSTANCE.getTypeEntryForFile(newFile) != null;
	}


	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		return null;
	}

	private void checkEditor(final RefactoringStatus result) {
		// depending if the in-place renaming is active we may not be in the display thread
		Display.getDefault().syncExec(() -> {
			final IEditorPart findEditor = EditorUtils
					.findEditor((final IEditorPart editor) -> editor.getEditorInput() instanceof FileEditorInput
							&& ((FileEditorInput) editor.getEditorInput()).getFile().equals(oldFile));
			if (findEditor != null && findEditor.isDirty()) {
				if (shouldSaveFile(findEditor.getSite().getShell())) {
					findEditor.doSave(new NullProgressMonitor());
				} else {
					result.addFatalError("Abort rename as editor is dirty!");
				}
			}
		});
	}

	private boolean shouldSaveFile(final Shell shell) {
		final int result = MessageDialog.open(MessageDialog.QUESTION, shell, "Rename of Type with unsaved changes!", //$NON-NLS-1$
				MessageFormat.format(
						"There are unsaved changes for type \"{0}\". Do you want to save them before renaming?", //$NON-NLS-1$
						TypeEntry.getTypeNameFromFileName(getOldName())),
				SWT.NONE, "Save", "Cancel"); //$NON-NLS-1$ //$NON-NLS-2$
		return result == 0;
	}

}
