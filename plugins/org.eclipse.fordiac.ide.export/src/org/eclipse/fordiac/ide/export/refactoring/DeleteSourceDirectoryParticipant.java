/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0/.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.refactoring;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.export.Messages;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.DeleteParticipant;

/**
 * A participant that removes the additional source directories of the type
 * export that refer to a deleted folder.
 */
public class DeleteSourceDirectoryParticipant extends DeleteParticipant {

	private IFolder folder;

	@Override
	protected boolean initialize(final Object element) {
		if (element instanceof final IFolder deletedFolder && deletedFolder.getProject().isAccessible()
				&& UpdateSourceDirectoriesChange.isReferenced(deletedFolder)) {
			folder = deletedFolder;
			return true;
		}
		return false;
	}

	@Override
	public String getName() {
		return Messages.Refactoring_DeleteSourceDirectoryParticipant;
	}

	@Override
	public RefactoringStatus checkConditions(final IProgressMonitor pm, final CheckConditionsContext context) {
		return new RefactoringStatus();
	}

	@Override
	public Change createChange(final IProgressMonitor pm) {
		return UpdateSourceDirectoriesChange.create(folder, directory -> null);
	}
}
