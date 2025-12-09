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
 *   Patrick Aigner
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.ui.refactoring;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.library.ui.Messages;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.DeleteParticipant;

public class DeleteLibraryRefactoringParticipant extends DeleteParticipant {
	private IProject project;
	private Manifest manifest;
	private boolean block;

	@Override
	protected boolean initialize(final Object element) {
		if (!(element instanceof final IResource resource)) {
			return false;
		}
		project = resource.getProject();
		if (project.getFolder(TypeLibraryTags.STANDARD_LIB_FOLDER_NAME).getFullPath().isPrefixOf(resource.getFullPath())
				|| project.getFolder(TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME).getFullPath()
						.isPrefixOf(resource.getFullPath())) {
			block = true;
			// block deletion of library contents (library itself can be deleted)
			if (resource instanceof final IFolder folder && folder.getFullPath().segmentCount() == 3) {
				manifest = ManifestHelper.getContainerManifest(folder);
				block = manifest == null || !ManifestHelper.isLibrary(manifest); // don't block if it is a valid library
			}

			return true;
		}

		return false;
	}

	@Override
	public String getName() {
		return Messages.DeleteLibraryParticipant_Name;
	}

	@Override
	public RefactoringStatus checkConditions(final IProgressMonitor pm, final CheckConditionsContext context)
			throws OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		if (block) {
			status.addFatalError(Messages.DeleteLibraryParticipant_Block_Delete);
		}
		return status;
	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		if (block) {
			return null;
		}
		return new DeleteLibraryDependencyChange(project, manifest.getProduct().getSymbolicName());
	}
}
