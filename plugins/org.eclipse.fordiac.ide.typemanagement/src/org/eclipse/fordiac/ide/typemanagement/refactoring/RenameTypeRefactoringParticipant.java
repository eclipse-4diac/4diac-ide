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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

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

		final CompositeChange fbTypeChange = getRequiredFbTypeChange();
		if (Objects.nonNull(fbTypeChange)) {
			parentChange.add(fbTypeChange);
		}

		final CompositeChange structChange = getRequiredStructChange();
		if (Objects.nonNull(structChange)) {
			parentChange.add(structChange);
		}

		return parentChange;
	}

	private CompositeChange getRequiredFbTypeChange() {
		final InstanceSearch search = StructDataTypeSearch
				.createStructInterfaceSearch((StructuredType) typeEntry.getTypeEditable());
		final Set<INamedElement> fbTypes = search.performTypeLibBlockSearch(typeEntry.getTypeLibrary());

		if (fbTypes.isEmpty()) {
			return null;
		}

		final CompositeChange fbTypeChanges = new CompositeChange(Messages.Refactoring_AffectedFuctionBlock);

		final Map<String, Set<InterfaceDataTypeChange>> affectedFunctionBlockChangeInterfaces = new HashMap<>();

		fbTypes.stream().filter(FBType.class::isInstance).map(fb -> (FBType) fb).forEach(fb -> {

			final String label = fb.getTypeEntry().getFile().getName() + " ["
					+ fb.getTypeEntry().getFile().getProject().getName() + "]";

			if (!affectedFunctionBlockChangeInterfaces.containsKey(label)) {
				affectedFunctionBlockChangeInterfaces.put(label, new HashSet<>());
			}

			affectedFunctionBlockChangeInterfaces.get(label).add(new InterfaceDataTypeChange(fb, typeEntry, oldName));

		});

		final Stream<CompositeChange> changesStream = affectedFunctionBlockChangeInterfaces.entrySet().stream()
				.map(entry -> {
					final CompositeChange fbChange = new CompositeChange(entry.getKey());

					entry.getValue().stream().forEach(fbChange::add);

					return fbChange;
				});

		changesStream.forEach(fbTypeChanges::add);

		return fbTypeChanges;
	}

	private CompositeChange getRequiredStructChange() {
		final IProject project = typeEntry.getFile().getProject();

		final CompositeChange structChange = new CompositeChange(Messages.Refactoring_AffectedStruct);

		final InstanceSearch structMemberSearch = StructDataTypeSearch
				.createStructMemberSearch((StructuredType) typeEntry.getTypeEditable());

		final Set<INamedElement> allFBWithStruct = InstanceSearch.performProjectSearch(project, structMemberSearch,
				StructDataTypeSearch.createStructInterfaceSearch((StructuredType) typeEntry.getTypeEditable()),
				new FBInstanceSearch((DataTypeEntry) typeEntry));

		allFBWithStruct.addAll(structMemberSearch.searchStructuredTypes(typeEntry.getTypeLibrary()));

		if (allFBWithStruct.isEmpty()) {
			return null;
		}

		allFBWithStruct.stream().map(this::createSubChange).forEach(structChange::add);

		return structChange;
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
