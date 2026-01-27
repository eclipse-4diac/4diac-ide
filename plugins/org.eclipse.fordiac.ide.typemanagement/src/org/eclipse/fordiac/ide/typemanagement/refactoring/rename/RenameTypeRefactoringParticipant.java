/*******************************************************************************
 * Copyright (c) 2023, 2026 Primetals Technologies Austria GmbH and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi, Michael Oberlehner
 *      - initial API and implementation and/or initial documentation
 *   Felix Schmid
 *      - changed to use ModelEdits
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.rename;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
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
import org.eclipse.fordiac.ide.typemanagement.refactoring.ModelEdit;
import org.eclipse.fordiac.ide.typemanagement.refactoring.ModelEditChange;
import org.eclipse.fordiac.ide.typemanagement.refactoring.UpdateFBInstanceModelEdit;
import org.eclipse.fordiac.ide.typemanagement.refactoring.UpdateTypeEntryChange;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;

/**
 * A participant for type rename refactorings triggered within a model context,
 * such as file rename.
 */
public class RenameTypeRefactoringParticipant extends RenameParticipant {

	private IFile file;
	private TypeEntry typeEntry;
	private String oldName;
	private String newName;
	private String packageName;

	@Override
	protected boolean initialize(final Object element) {
		if (element instanceof final IFile targetFile
				&& TypeLibraryManager.INSTANCE.getTypeEntryForFile(targetFile) != null) {
			this.file = targetFile;
			this.typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(targetFile);
			this.oldName = typeEntry.getTypeName();
			this.newName = TypeEntry.getTypeNameFromFileName(getArguments().getNewName());
			this.packageName = PackageNameHelper.getContainerPackageName(typeEntry.getType());
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
				return createStructDataChange((DataTypeEntry) typeEntry);
			}
			if (type instanceof FBType) {
				return createFBDataChange();
			}
			return new UpdateTypeEntryChange(file, typeEntry, newName, oldName);

		} finally {
			monitor.done();
		}
	}

	private CompositeChange createStructDataChange(final DataTypeEntry dataTypeEntry) {
		final CompositeChange parentChange = new CompositeChange(
				MessageFormat.format(Messages.Refactoring_RenameFromTo, typeEntry.getTypeName(), newName));
		parentChange.add(new UpdateTypeEntryChange(file, typeEntry, newName, oldName));

		final List<ModelEdit<?>> modelEdits = new ArrayList<>();
		createStructChanges(dataTypeEntry, modelEdits);
		parentChange.add(ModelEditChange.fromModelEdits(Messages.Refactoring_StructUsers, modelEdits));

		return parentChange;
	}

	private void createStructChanges(final DataTypeEntry dataTypeEntry, final List<ModelEdit<?>> modelEdits) {
		final DataTypeInstanceSearch dataTypeInstanceSearch = new DataTypeInstanceSearch(dataTypeEntry);
		final Set<EObject> rootElements = new HashSet<>();
		dataTypeInstanceSearch.performSearch().forEach(obj -> {
			if (obj instanceof final VarDeclaration varDecl) {
				createSubChange(varDecl, dataTypeEntry, rootElements, modelEdits);
			} else if (obj instanceof final StructManipulator structMan) {
				modelEdits.add(new UpdateFBInstanceModelEdit(structMan, dataTypeEntry));
			}
		});
	}

	private CompositeChange createFBDataChange() {
		final CompositeChange parentChange = new CompositeChange(
				MessageFormat.format(Messages.Refactoring_RenameFromTo, typeEntry.getTypeName(), newName));
		parentChange.add(new UpdateTypeEntryChange(file, typeEntry, newName, oldName));

		final List<ModelEdit<?>> modelEdits = new ArrayList<>();
		final IEC61499ElementSearch search = new BlockTypeInstanceSearch(typeEntry);
		final List<? extends EObject> searchResults = search.performSearch();
		searchResults.stream().filter(BlockFBNetworkElement.class::isInstance).map(BlockFBNetworkElement.class::cast)
				.map(fbn -> new UpdateFBInstanceModelEdit(fbn, typeEntry)).forEach(modelEdits::add);

		if (!modelEdits.isEmpty()) {
			parentChange.add(ModelEditChange.fromModelEdits(Messages.Refactoring_AffectedInstancesOfFB, modelEdits));
		}
		return parentChange;
	}

	private void createSubChange(final VarDeclaration varDecl, final DataTypeEntry dataTypeEntry,
			final Set<EObject> rootElements, final List<ModelEdit<?>> modelEdits) {
		if (varDecl.getBlockFBNetworkElement() != null) {
			if (varDecl.getBlockFBNetworkElement() instanceof StructManipulator) {
				return; // StructManipulators handle varDecls differently...
			}
			if (rootElements.add(varDecl.getBlockFBNetworkElement())) {
				modelEdits.add(new UpdateFBInstanceModelEdit(varDecl.getBlockFBNetworkElement(), dataTypeEntry));
			}
		} else {
			final EObject rootContainer = EcoreUtil.getRootContainer(varDecl);
			if (!rootElements.add(rootContainer)) {
				return;
			}
			if (rootContainer instanceof final StructuredType stElement) {
				modelEdits.add(new RenameUpdateStructDataTypeMemberVariableModelEdit(varDecl, dataTypeEntry));
				createStructChanges((DataTypeEntry) stElement.getTypeEntry(), modelEdits);
			}
			if (rootContainer instanceof AttributeDeclaration) {
				modelEdits.add(new RenameUpdateStructDataTypeMemberVariableModelEdit(varDecl, dataTypeEntry));
			}
			if (rootContainer instanceof final FBType fbType && dataTypeEntry.getType() instanceof StructuredType) {
				modelEdits.add(new RenameUpdateFBTypeInterfaceModelEdit(fbType, oldName, newName, packageName));
			}
		}
	}
}
