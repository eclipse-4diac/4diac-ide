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
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.search.types.BlockTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.search.types.FBInstanceSearch;
import org.eclipse.fordiac.ide.model.search.types.IEC61499ElementSearch;
import org.eclipse.fordiac.ide.model.search.types.InstanceSearch;
import org.eclipse.fordiac.ide.model.search.types.StructDataTypeSearch;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
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
			checkFileEnding(status);
			checkFileName(status);
		} finally {
			monitor.done();
		}
		return status;
	}

	protected void checkFileEnding(final RefactoringStatus result) {
		if (!getArguments().getNewName().endsWith(file.getFileExtension())) {
			result.addFatalError("The file-ending is different to the old one!"); //$NON-NLS-1$
		}
	}

	protected void checkFileName(final RefactoringStatus result) {
		if (!oldName.equalsIgnoreCase(newName)) {
			try {
				final String name = getArguments().getNewName();
				for (final IResource resource : file.getParent().members()) {
					if (name.equalsIgnoreCase(resource.getName())) {
						result.addFatalError("File already exists!"); //$NON-NLS-1$
					}
				}
			} catch (final CoreException e) {
				// do nothing
			}
		}
	}

	@Override
	public Change createChange(final IProgressMonitor monitor) throws CoreException, OperationCanceledException {
		try {
			monitor.beginTask("Creating change...", 1); //$NON-NLS-1$

			final LibraryElement type = typeEntry.getType();
			if (type instanceof StructuredType) {
				return createStructDataChange();
			}
			if (type instanceof FBType) {
				return createFBDataChange();
			}
			return null;

		} finally {
			monitor.done();
		}
	}

	private CompositeChange createStructDataChange() {
		InstanceSearch search = StructDataTypeSearch
				.createStructMemberSearch((StructuredType) typeEntry.getTypeEditable());
		final IProject project = typeEntry.getFile().getProject();

		final Set<INamedElement> allFBWithStruct = InstanceSearch.performProjectSearch(project,
				StructDataTypeSearch.createStructMemberSearch((StructuredType) typeEntry.getTypeEditable()),
				StructDataTypeSearch.createStructInterfaceSearch((StructuredType) typeEntry.getTypeEditable()),
				new FBInstanceSearch((DataTypeEntry) typeEntry));
		allFBWithStruct.addAll(search.searchStructuredTypes(typeEntry.getTypeLibrary()));
		final CompositeChange parentChange = new CompositeChange(
				MessageFormat.format(Messages.Refactoring_RenameFromTo, typeEntry.getTypeName(), newName));
		parentChange.add(new UpdateTypeEntryChange(file, typeEntry, newName, oldName));

		final CompositeChange change = new CompositeChange(Messages.Refactoring_AffectedStruct);
		final CompositeChange fbTypeChanges = new CompositeChange("Fb Types:"); //$NON-NLS-1$
		search = StructDataTypeSearch.createStructInterfaceSearch((StructuredType) typeEntry.getTypeEditable());
		final Set<INamedElement> fbTypes = search.performTypeLibBlockSearch(typeEntry.getTypeLibrary());
		fbTypes.forEach(fb -> fbTypeChanges.add(new InterfaceDataTypeChange((FBType) fb, typeEntry, oldName)));
		parentChange.add(fbTypeChanges);
		allFBWithStruct.stream().map(this::createSubChange).forEach(change::add);

		if (!allFBWithStruct.isEmpty()) {
			parentChange.add(change);
		}

		return parentChange;
	}

	private CompositeChange createFBDataChange() {
		final CompositeChange parentChange = new CompositeChange(
				MessageFormat.format(Messages.Refactoring_RenameFromTo, typeEntry.getTypeName(), newName));
		parentChange.add(new UpdateTypeEntryChange(file, typeEntry, newName, oldName));
		final CompositeChange change = new CompositeChange(Messages.Refactoring_AffectedInstancesOfFB);

		final IEC61499ElementSearch search = new BlockTypeInstanceSearch(typeEntry);
		final List<? extends EObject> searchResults = search.performSearch();
		searchResults.stream().filter(FBNetworkElement.class::isInstance).map(FBNetworkElement.class::cast)
				.map(fbn -> new UpdateInstancesChange(fbn, typeEntry)).forEach(change::add);

		if (!searchResults.isEmpty()) {
			parentChange.add(change);
		}
		return parentChange;
	}

	private Change createSubChange(final INamedElement element) {
		if (element instanceof final StructuredType stElement) {
			return new StructuredTypeMemberChange(stElement, typeEntry, typeEntry.getTypeName(), newName);
		}
		if (element instanceof final FBType fbType) {
			return new InterfaceDataTypeChange(fbType, typeEntry, oldName);
		}
		if (element instanceof final FBNetworkElement elem) {
			return new UpdateInstancesChange(elem, typeEntry);
		}
		return null;
	}
}
