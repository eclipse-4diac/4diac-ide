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
 *   Fabio Gandolfi, Michael Oberlehner -
 *   	initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.text.MessageFormat;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.search.types.FBInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.rename.FbInstanceChange;
import org.eclipse.fordiac.ide.typemanagement.refactoring.rename.FbTypeChange;
import org.eclipse.fordiac.ide.typemanagement.refactoring.rename.StructTypeChange;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;

public class RenameTypeRefactoringParticipant extends RenameParticipant {

	IFile file;
	TypeEntry typeEntry;
	String oldName;
	String newName;

	@Override
	protected boolean initialize(final Object element) {
		if (element instanceof final IFile targetFile
				&& TypeLibraryManager.INSTANCE.getTypeEntryForFile(targetFile) != null) {
			this.file = targetFile;
			typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(targetFile);
			oldName = typeEntry.getTypeName();
			newName = TypeEntry.getTypeNameFromFileName(getArguments().getNewName());
			return true;
		}
		return false;
	}

	@Override
	public String getName() {
		return Messages.RenameType_Name;
	}

	@Override
	public RefactoringStatus checkConditions(final IProgressMonitor monitor, final CheckConditionsContext context)
			throws OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		try {
			monitor.beginTask("Checking preconditions...", 1); //$NON-NLS-1$
			checkFileEnding(status, typeEntry);

		} finally {
			monitor.done();
		}
		return status;
	}

	public void checkFileEnding(final RefactoringStatus result, final TypeEntry typeEntry) {
		if (!getArguments().getNewName().endsWith(file.getFileExtension())) {
			result.addFatalError("The file-ending is different to the old one!"); //$NON-NLS-1$
		}
	}

	@Override
	public Change createChange(final IProgressMonitor monitor) throws CoreException, OperationCanceledException {
		try {
			monitor.beginTask("Creating change...", 1); //$NON-NLS-1$

			final LibraryElement type = typeEntry.getType();
			if (type instanceof final StructuredType structType) {
				return createStructDataChange(structType);
			}
			if (type instanceof final FBType fbType) {
				return createFBDataChange(fbType);
			}
			return null;

		} finally {
			monitor.done();
		}
	}

	private CompositeChange createStructDataChange(final StructuredType type) {
		final CompositeChange parentChange = new CompositeChange(
				MessageFormat.format(Messages.Refactoring_RenameFromTo, typeEntry.getTypeName(), newName));

		final Change typeLibraryChange = new UpdateTypeLibraryEntryChange(file, typeEntry, newName, oldName);
		parentChange.add(typeLibraryChange);

		final CompositeChange fbTypeChange = new FbTypeChange(file);
		if (fbTypeChange.getChildren().length != 0) {
			parentChange.add(fbTypeChange);
		}

		final CompositeChange structChange = new StructTypeChange(file, newName);
		if (structChange.getChildren().length != 0) {
			parentChange.add(structChange);
		}

		final CompositeChange fbInstanceChange = new FbInstanceChange(file);
		if (fbInstanceChange.getChildren().length != 0) {
			parentChange.add(fbInstanceChange);
		}

		return parentChange;
	}

	private CompositeChange createFBDataChange(final FBType type) {
		final CompositeChange parentChange = new CompositeChange(
				MessageFormat.format(Messages.Refactoring_RenameFromTo, typeEntry.getTypeName(), newName));
		parentChange.add(new UpdateTypeLibraryEntryChange(file, typeEntry, newName, oldName));
		Set<INamedElement> allFBs;
		final CompositeChange change = new CompositeChange(Messages.Refactoring_AffectedInstancesOfFB);
		final IProject project = type.getTypeEntry().getFile().getProject();

		final var search = new FBInstanceSearch(type);

		allFBs = search.performProjectSearch(project);

		allFBs.addAll(search.performInternalFBSearch(type.getTypeLibrary()));

		allFBs.stream().map(FBNetworkElement.class::cast).map(fbn -> new UpdateInstancesChange(fbn, typeEntry))
				.forEach(change::add);
		if (!allFBs.isEmpty()) {
			parentChange.add(change);
		}
		return parentChange;
	}

}
