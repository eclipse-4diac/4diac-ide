/*******************************************************************************
 * Copyright (c) 2014, 2021 fortiss GmbH, Primetals Technologies GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement;

import java.text.MessageFormat;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.mapping.IResourceChangeDescriptionFactory;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.model.IdentifierVerifyer;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
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

	private void verifyAffectedChildren(final IResourceDelta[] affectedChildren,
			final RefactoringStatus result) {
		for (final IResourceDelta resourceDelta : affectedChildren) {
			if (resourceDelta.getMovedFromPath() != null) {
				if (resourceDelta.getResource() instanceof IFile) {
					final String newName = ((IFile) resourceDelta.getResource()).getName();
					if (nameExistsInTypeLibrary(resourceDelta, newName)) {
						result.addFatalError(MessageFormat.format(Messages.RenameType_TypeExists, newName));
					}
					final String name = TypeEntry.getTypeNameFromFileName(newName);
					if (name != null && !IdentifierVerifyer.isValidIdentifier(name)) {
						getWrongIdentifierErrorStatus(result);
					}
				} else if (resourceDelta.getMovedToPath() == null) {
					verifyAffectedChildren(resourceDelta.getAffectedChildren(), result);
				}
			}
		}
	}

	protected boolean nameExistsInTypeLibrary(final IResourceDelta resourceDelta, final String newName) {
		return !getOldName().equals(newName) && !newName.equals("a" + getOldName()) //$NON-NLS-1$
				&& TypeLibrary.getPaletteEntryForFile(((IFile) resourceDelta.getResource())) != null;
	}

	@SuppressWarnings("static-method")  // allow child classes to overwrite
	protected void getWrongIdentifierErrorStatus(final RefactoringStatus result) {
		result.addFatalError(Messages.RenameType_InvalidIdentifierErrorMessage);
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
		final int result = MessageDialog.open(
				MessageDialog.QUESTION, shell, "Rename of Type with unsaved changes!",
				MessageFormat.format(
						"There are unsaved changes for type \"{0}\". Do you want to save them before renaming?",
						TypeEntry.getTypeNameFromFileName(getOldName())),
				SWT.NONE, "Save", "Cancel");
		return result == 0;
	}

}
