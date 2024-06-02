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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.types.BlockTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.search.types.DataTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.search.types.IEC61499ElementSearch;
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

	@Override
	public Change createChange(final IProgressMonitor monitor) throws CoreException, OperationCanceledException {
		try {
			monitor.beginTask("Creating change...", 1); //$NON-NLS-1$

			final LibraryElement type = typeEntry.getType();
			if (type instanceof StructuredType) {
				return createStructDataChange((DataTypeEntry) typeEntry);
			}
			if (type instanceof FBType) {
				return createFBDataChange();
			}
			return null;

		} finally {
			monitor.done();
		}
	}

	private CompositeChange createStructDataChange(final DataTypeEntry dataTypeEntry) {

		final CompositeChange parentChange = new CompositeChange(
				MessageFormat.format(Messages.Refactoring_RenameFromTo, typeEntry.getTypeName(), newName));
		parentChange.add(new UpdateTypeEntryChange(file, typeEntry, newName, oldName));
		final CompositeChange structUsageChanges = new CompositeChange("Refactoring struct users:");
		parentChange.add(structUsageChanges);

		createStructChanges(dataTypeEntry, structUsageChanges);

		return parentChange;
	}

	private static void createStructChanges(final DataTypeEntry dataTypeEntry,
			final CompositeChange structUsageChanges) {
		final DataTypeInstanceSearch dataTypeInstanceSearch = new DataTypeInstanceSearch(dataTypeEntry);
		final Set<EObject> rootElements = new HashSet<>();
		dataTypeInstanceSearch.performSearch().forEach(obj -> {
			if (obj instanceof final VarDeclaration varDecl) {
				structUsageChanges.add(createSubChange(varDecl, dataTypeEntry, rootElements));
			} else if (obj instanceof final StructManipulator structMan) {
				structUsageChanges.add(new UpdateInstancesChange(structMan, dataTypeEntry));
			}
		});
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

	private static Change createSubChange(final VarDeclaration varDecl, final DataTypeEntry dataTypeEntry,
			final Set<EObject> rootElements) {
		if (varDecl.getFBNetworkElement() != null) {
			if (rootElements.add(varDecl.getFBNetworkElement())) {
				return new UpdateInstancesChange(varDecl.getFBNetworkElement(), dataTypeEntry);
			}
		} else {
			final EObject rootContainer = EcoreUtil.getRootContainer(varDecl);
			if (rootElements.add(rootContainer)) {
				if (rootContainer instanceof final StructuredType stElement) {
					final CompositeChange change = new CompositeChange(MessageFormat.format(
							Messages.Refactoring_AffectedStruct, stElement.getName(), dataTypeEntry.getTypeName()));
					change.add(new StructuredTypeMemberChange(stElement, dataTypeEntry));
					createStructChanges((DataTypeEntry) stElement.getTypeEntry(), change);
					return change;
				}
				if (rootContainer instanceof final FBType fbType) {
					return new InterfaceDataTypeChange(fbType, dataTypeEntry);
				}
			}
		}
		return null;
	}
}
