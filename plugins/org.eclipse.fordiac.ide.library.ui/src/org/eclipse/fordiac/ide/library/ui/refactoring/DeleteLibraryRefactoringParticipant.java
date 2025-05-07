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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.DeleteParticipant;

public class DeleteLibraryRefactoringParticipant extends DeleteParticipant {
	private IProject project;
	private Manifest manifest;

	@Override
	protected boolean initialize(final Object element) {
		if (element instanceof final IFolder folder
				&& (folder.getParent().getName().equals(TypeLibraryTags.STANDARD_LIB_FOLDER_NAME)
						|| folder.getParent().getName().equals(TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME))) {
			manifest = ManifestHelper.getContainerManifest(folder);
			project = folder.getProject();
			return manifest != null && ManifestHelper.isLibrary(manifest);
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
		return new RefactoringStatus();
	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		return new DeleteLibraryDependencyChange(project, manifest.getProduct().getSymbolicName());
	}
}
