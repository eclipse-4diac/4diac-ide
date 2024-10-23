/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
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
 *******************************************************************************/

package org.eclipse.fordiac.ide.typemanagement.refactoring.move;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.search.types.BlockTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.search.types.DataTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typemanagement.refactoring.UpdateFBInstanceChange;
import org.eclipse.fordiac.ide.typemanagement.wizards.Messages;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.MoveParticipant;

public class MoveTypeRefactoringParticipant extends MoveParticipant {

	private String oldPackageName;
	private String newPackageName;
	private TypeEntry type;
	private IFile destinationFile;
	private IFile currentFile;

	@Override
	protected boolean initialize(final Object element) {
		if (element instanceof final IFile file) {
			this.currentFile = file;
			this.type = TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);
			this.oldPackageName = type.getPackageName();
			if (getArguments().getDestination() instanceof final IResource folder) {
				final URI destinationURI = URI.createPlatformResourceURI(folder.getFullPath().toString(), true)
						.appendSegment(type.getURI().lastSegment());
				this.destinationFile = getFileFromURI(destinationURI);
				this.newPackageName = PackageNameHelper.getPackageNameFromFile(destinationFile);
				return true;
			}
		}
		return false;
	}

	@Override
	public String getName() {
		return MessageFormat.format(Messages.MoveTypeToPackage_RenamePackageTo, newPackageName);
	}

	@Override
	public RefactoringStatus checkConditions(final IProgressMonitor pm, final CheckConditionsContext context)
			throws OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		if (oldPackageName.contentEquals(newPackageName)) {
			status.addWarning(Messages.MoveTypeToPackage_PackageNameIsTheSame);
		}
		if (!(getArguments().getDestination() instanceof IResource)) {
			status.addError(Messages.MoveTypeToPackage_InvalidDestination);
		}
		final Optional<String> errorMessage = IdentifierVerifier.verifyPackageName(newPackageName);
		if (errorMessage.isPresent()) {
			status.addFatalError(errorMessage.get());
		}
		return status;
	}

	@Override
	public Change createPreChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		return new MoveTypeChange(newPackageName, getName(), this.type.getURI());
	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		final CompositeChange parentChange = new CompositeChange(Messages.MoveTypeToPackage);
		parentChange.add(new UpdateTypeEntryFileChange(currentFile, type, destinationFile));
		parentChange.add(getInstanceChanges(type));
		return parentChange;
	}

	static IFile getFileFromURI(final URI uri) {
		if (uri.isPlatformResource()) {
			final Path filePath = new Path(uri.toPlatformString(true));
			return ResourcesPlugin.getWorkspace().getRoot().getFile(filePath);
		}
		return null;
	}

	private static CompositeChange getInstanceChanges(final TypeEntry typeEntry) {
		final CompositeChange change = new CompositeChange(Messages.MoveTypeToPackage_UpdateInstances);
		final List<? extends EObject> result = (typeEntry instanceof final DataTypeEntry dtEntry)
				? new DataTypeInstanceSearch(dtEntry).performSearch()
				: new BlockTypeInstanceSearch(typeEntry).performSearch();

		for (final EObject eObject : result) {
			if (eObject instanceof final FBNetworkElement elem) {
				change.add(new UpdateFBInstanceChange(elem, typeEntry));
			}
		}
		return change;
	}
}
