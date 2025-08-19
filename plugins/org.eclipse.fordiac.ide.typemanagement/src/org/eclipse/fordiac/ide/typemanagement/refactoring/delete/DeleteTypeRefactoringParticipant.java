/*******************************************************************************
 * Copyright (c) 2014, 2024 fortiss GmbH
 *                          Martin Erich Jobst
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
 *   Martin Erich Jobst
 *     - participate only if a type entry exists
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.delete;

import java.text.MessageFormat;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.search.types.BlockTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.ModelEditChange;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.DeleteParticipant;

public class DeleteTypeRefactoringParticipant extends DeleteParticipant {

	private TypeEntry typeEntry;

	@Override
	protected boolean initialize(final Object element) {
		if (element instanceof final IFile file) {
			typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);
			return typeEntry != null;
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

			if (typeEntry instanceof DataTypeEntry && typeEntry.getType() instanceof final StructuredType struct) {
				return new SafeStructDeletionChange(struct);
			}
			if (typeEntry instanceof final FBTypeEntry fbTypeEntry) {
				return createFBTypeDeletionChange(fbTypeEntry.getType());
			}

			return null;
		} finally {
			pm.done();
		}
	}

	private static CompositeChange createFBTypeDeletionChange(final FBType type) {
		final BlockTypeInstanceSearch search = new BlockTypeInstanceSearch(type.getTypeEntry());
		return ModelEditChange.fromModelEdits(
				MessageFormat.format(Messages.DeleteFBTypeParticipant_Change_SafeDeletionChangeTitle, type.getName()),
				search.performSearch().stream().filter(FBNetworkElement.class::isInstance)
						.map(FBNetworkElement.class::cast)
						.filter(fbnEl -> fbnEl instanceof FB && fbnEl.eContainer() instanceof BaseFBType)
						.map(FB.class::cast).map(DeleteInternalFBModelEdit::new).toList());

	}
}
