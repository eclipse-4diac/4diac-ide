/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH and others
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
 *      - changed to use ModelEdits and add support for moving folders
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.rename;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
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
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.ModelEdit;
import org.eclipse.fordiac.ide.typemanagement.refactoring.ModelEditChange;
import org.eclipse.fordiac.ide.typemanagement.refactoring.RefactoringUtil;
import org.eclipse.fordiac.ide.typemanagement.refactoring.UpdateFBInstanceModelEdit;
import org.eclipse.fordiac.ide.typemanagement.refactoring.UpdateTypeEntryChange;
import org.eclipse.fordiac.ide.typemanagement.refactoring.move.MoveTypeModelEdit;
import org.eclipse.fordiac.ide.typemanagement.refactoring.move.UpdateTypeEntryFileChange;
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

	private IResource resource;
	private String newName;

	@Override
	protected boolean initialize(final Object element) {
		if (element instanceof final IResource res) {
			resource = res;
			newName = getArguments().getNewName();
			return RefactoringUtil.containsTypeEntryFile(resource);
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
			checkFileExists(status);
		} finally {
			monitor.done();
		}
		return status;
	}

	protected void checkFileEnding(final RefactoringStatus result) {
		if (resource.getFileExtension() != null && !newName.endsWith(resource.getFileExtension())) {
			result.addFatalError("The file-ending is different to the old one!"); //$NON-NLS-1$
		}
	}

	protected void checkFileExists(final RefactoringStatus result) {
		if (resource.getParent().findMember(newName) != null) {
			result.addFatalError("File already exists!"); //$NON-NLS-1$
		}
	}

	@Override
	public Change createPreChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		if (!(resource instanceof IFolder)) {
			return null; // change package name of types only when folder is renamed
		}

		final List<ModelEdit<?>> modelEdits = new ArrayList<>();
		final List<Change> changes = new ArrayList<>();
		processTypeFiles(resource, resource.getFullPath(), (typeEntry, path) -> {
			final IFile newFile = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
			final String newPackageName = PackageNameHelper.getPackageNameFromFile(newFile);

			modelEdits.add(new MoveTypeModelEdit(newPackageName,
					MessageFormat.format(Messages.MoveTypeToPackage_RenamePackageTo, newPackageName),
					typeEntry.getURI()));
			changes.add(new UpdateTypeEntryFileChange(typeEntry.getFile(), typeEntry, newFile));
		});
		// add model edits before(!) UpdateTypeEntryFileChange
		changes.addFirst(ModelEditChange.fromModelEdits(Messages.CopyTypeChange_RenamePackage, modelEdits));
		return new CompositeChange(Messages.CopyTypeChange_RenamePackage, changes.toArray(Change[]::new));
	}

	@Override
	public Change createChange(final IProgressMonitor monitor) throws CoreException, OperationCanceledException {
		try {
			monitor.beginTask("Creating change...", 1); //$NON-NLS-1$
			final CompositeChange change = new CompositeChange(Messages.Refactoring_RenameChangeName);

			// rename type if only single file was renamed...
			if (resource instanceof final IFile file) {
				final TypeEntry entry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);
				final String newTypeName = TypeEntry.getTypeNameFromFileName(newName);
				change.add(new UpdateTypeEntryChange(entry.getFile(), entry, newTypeName, entry.getTypeName()));
			}

			final List<ModelEdit<?>> modelEdits = new ArrayList<>();
			processTypeFiles(resource, resource.getFullPath(),
					(typeEntry, path) -> addModelEditsForType(modelEdits, typeEntry, path));
			change.add(ModelEditChange.fromModelEdits(Messages.Refactoring_StructUsers, modelEdits));
			return change;
		} finally {
			monitor.done();
		}
	}

	private void processTypeFiles(final IResource resource, IPath newPath, final BiConsumer<TypeEntry, IPath> processor)
			throws CoreException {
		if (resource == this.resource) {
			newPath = newPath.removeLastSegments(1).append(newName);
		} else {
			newPath = newPath.append(resource.getName());
		}

		if (resource instanceof final IFile file) {
			final TypeEntry entry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);
			if (entry != null) {
				processor.accept(entry, newPath);
			}
		} else if (resource instanceof final IContainer container) {
			for (final IResource member : container.members()) {
				processTypeFiles(member, newPath, processor);
			}
		}
	}

	private void addModelEditsForType(final List<ModelEdit<?>> modelEdits, final TypeEntry typeEntry,
			final IPath newPath) {
		final LibraryElement type = typeEntry.getType();
		if (type instanceof StructuredType) {
			createStructChanges((DataTypeEntry) typeEntry, modelEdits, newPath);
		} else if (type instanceof FBType) {
			createFBDataChange(typeEntry, modelEdits);
		}
	}

	private void createStructChanges(final DataTypeEntry dataTypeEntry, final List<ModelEdit<?>> modelEdits,
			final IPath newPath) {
		final DataTypeInstanceSearch dataTypeInstanceSearch = new DataTypeInstanceSearch(dataTypeEntry);
		final Set<EObject> rootElements = new HashSet<>();
		dataTypeInstanceSearch.performSearch().forEach(obj -> {
			if (obj instanceof final VarDeclaration varDecl) {
				createSubChange(varDecl, dataTypeEntry, rootElements, modelEdits, newPath);
			} else if (obj instanceof final StructManipulator structMan) {
				modelEdits.add(new UpdateFBInstanceModelEdit(structMan, dataTypeEntry));
			}
		});
	}

	private void createSubChange(final VarDeclaration varDecl, final DataTypeEntry dataTypeEntry,
			final Set<EObject> rootElements, final List<ModelEdit<?>> modelEdits, final IPath newPath) {
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
				createStructChanges((DataTypeEntry) stElement.getTypeEntry(), modelEdits, newPath);
			}
			if (rootContainer instanceof AttributeDeclaration) {
				modelEdits.add(new RenameUpdateStructDataTypeMemberVariableModelEdit(varDecl, dataTypeEntry));
			}
			if (rootContainer instanceof final FBType fbType && dataTypeEntry.getType() instanceof StructuredType) {
				final IFile newFile = ResourcesPlugin.getWorkspace().getRoot().getFile(newPath);
				modelEdits.add(new RenameUpdateFBTypeInterfaceModelEdit(fbType, dataTypeEntry.getTypeName(),
						PackageNameHelper.getFullTypeNameFromFile(newFile),
						PackageNameHelper.getPackageNameFromFile(dataTypeEntry.getFile())));
			}
		}
	}

	private static void createFBDataChange(final TypeEntry typeEntry, final List<ModelEdit<?>> modelEdits) {
		new BlockTypeInstanceSearch(typeEntry).performSearch().stream().filter(BlockFBNetworkElement.class::isInstance)
				.map(BlockFBNetworkElement.class::cast).map(fbn -> new UpdateFBInstanceModelEdit(fbn, typeEntry))
				.forEach(modelEdits::add);
	}
}
