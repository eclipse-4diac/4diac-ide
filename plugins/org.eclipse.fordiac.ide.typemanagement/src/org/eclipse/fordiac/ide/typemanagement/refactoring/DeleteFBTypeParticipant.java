/*******************************************************************************
 * Copyright (c) 2014 - 2017 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Daniel Lindhuber - safe type deletion
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.DeleteParticipant;

public class DeleteFBTypeParticipant extends DeleteParticipant {

	IFile file = null;

	@Override
	protected boolean initialize(final Object element) {
		if (element instanceof final IFile targetFile) {
			this.file = targetFile;
			return true;
		}
		return false;
	}

	@Override
	public String getName() {
		return Messages.DeleteFBTypeParticipant_Name;
	}

	@Override
	public RefactoringStatus checkConditions(final IProgressMonitor pm, final CheckConditionsContext context)
			throws OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		try {
			pm.beginTask("Creating change...", 1); //$NON-NLS-1$
			final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);

			if (typeEntry.getType() instanceof final StructuredType struct) {
				return new SafeStructDeletionChange(struct);
			}
			if (typeEntry.getType() instanceof final FBType type) {
				return new SafeFBTypeDeletionChange(type);
			}

			return null;
		} finally {
			pm.done();
		}
	}

}
