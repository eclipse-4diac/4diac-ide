/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner
 *     - initial API and implementation and/or initial documentation
 *   Felix Schmid
 *     - changed to use ModelEdits and add support for moving folders
 *******************************************************************************/

package org.eclipse.fordiac.ide.typemanagement.refactoring.move;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.search.types.BlockTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.ModelEdit;
import org.eclipse.fordiac.ide.typemanagement.refactoring.ModelEditChange;
import org.eclipse.fordiac.ide.typemanagement.refactoring.RefactoringUtil;
import org.eclipse.fordiac.ide.typemanagement.refactoring.UpdateFBTypeModelEdit;
import org.eclipse.fordiac.ide.typemanagement.refactoring.edit.DataTypeEditBuilder;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.MoveParticipant;

public class MoveTypeRefactoringParticipant extends MoveParticipant {

	private IResource resource;
	private IContainer destination;
	private String newPackageName;

	@Override
	protected boolean initialize(final Object element) {
		if (element instanceof final IResource res
				&& getArguments().getDestination() instanceof final IContainer dest) {
			resource = res;
			destination = dest;
			final IFile newFile = ResourcesPlugin.getWorkspace().getRoot().getFile(dest.getFullPath());
			newPackageName = PackageNameHelper.getPackageNameFromFile(newFile);
			return RefactoringUtil.containsTypeEntryFile(res);
		}
		return false;
	}

	@Override
	public String getName() {
		return Messages.MoveTypeToPackage;
	}

	@Override
	public RefactoringStatus checkConditions(final IProgressMonitor pm, final CheckConditionsContext context)
			throws OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		final String packageNameContainer = PackageNameHelper.getPackageNameFromContainer(destination);
		final Optional<String> errorMessage = IdentifierVerifier.verifyPackageName(packageNameContainer);
		if (errorMessage.isPresent()) {
			status.addFatalError(errorMessage.get());
		}
		return status;
	}

	@Override
	public Change createPreChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		final List<ModelEdit<?>> modelEdits = new ArrayList<>();
		final List<Change> changes = new ArrayList<>();
		processTypeFiles(resource, destination.getFullPath(), (typeEntry, path) -> {
			modelEdits.add(new MoveTypeModelEdit(newPackageName,
					MessageFormat.format(Messages.MoveTypeToPackage_RenamePackageTo, newPackageName),
					typeEntry.getURI()));
			changes.add(new UpdateTypeEntryFileChange(typeEntry.getFile(), typeEntry,
					ResourcesPlugin.getWorkspace().getRoot().getFile(path)));
		});
		// add model edits before(!) UpdateTypeEntryFileChange
		changes.addFirst(ModelEditChange.fromModelEdits(Messages.MoveTypeToPackage, modelEdits));
		return new CompositeChange(Messages.MoveTypeToPackage, changes.toArray(Change[]::new));
	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		final List<ModelEdit<?>> modelEdits = new ArrayList<>();
		processTypeFiles(resource, destination.getFullPath(), (typeEntry, path) -> {
			if (typeEntry instanceof final DataTypeEntry dtEntry) {
				DataTypeEditBuilder.createStructuredDataTypeChanges(dtEntry, modelEdits,
						DataTypeEditBuilder.getFullTypeName(path), new HashSet<>());
			} else {
				addInstanceChanges(modelEdits, typeEntry);
			}
		});
		return ModelEditChange.fromModelEdits(Messages.MoveTypeToPackage_UpdateInstances, modelEdits);
	}

	private void processTypeFiles(final IResource resource, final IPath newPath,
			final BiConsumer<TypeEntry, IPath> processor) throws CoreException {
		if (resource instanceof final IFile file) {
			final TypeEntry entry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);
			if (entry != null) {
				processor.accept(entry, newPath.append(file.getName()));
			}
		} else if (resource instanceof final IContainer container) {
			for (final IResource member : container.members()) {
				processTypeFiles(member, newPath.append(container.getName()), processor);
			}
		}
	}

	private static void addInstanceChanges(final List<ModelEdit<?>> modelEdits, final TypeEntry typeEntry) {
		final List<? extends EObject> result = new BlockTypeInstanceSearch(typeEntry).performSearch();

		for (final EObject eObject : result) {
			if (eObject instanceof final BlockFBNetworkElement elem) {
				modelEdits.add(new UpdateFBTypeModelEdit(elem, typeEntry));
			}
		}
	}
}
