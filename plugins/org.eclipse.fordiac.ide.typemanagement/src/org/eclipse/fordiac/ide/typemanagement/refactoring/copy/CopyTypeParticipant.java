/*******************************************************************************
 * Copyright (c) 2024, 2025 Primetals Technologies Austria GmbH
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
 *     - adapted for copy refactoring
 *******************************************************************************/

package org.eclipse.fordiac.ide.typemanagement.refactoring.copy;

import java.text.MessageFormat;
import java.util.Optional;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.CopyParticipant;

public class CopyTypeParticipant extends CopyParticipant {

	private IFile origin;
	private URI destinationURI;
	private String newPackageName;

	@Override
	protected boolean initialize(final Object element) {
		if (element instanceof final IFile file
				&& getArguments().getDestination() instanceof final IContainer destination) {
			if (TypeLibraryManager.INSTANCE.getTypeEntryForFile(file) == null) {
				return false;
			}

			origin = file;
			destinationURI = URI.createPlatformResourceURI(destination.getFullPath().append(file.getName()).toString(),
					true);
			newPackageName = PackageNameHelper.getPackageNameFromURI(destinationURI);
			return true;
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
		final Optional<String> errorMessage = IdentifierVerifier.verifyPackageName(newPackageName);
		if (errorMessage.isPresent()) {
			status.addFatalError(errorMessage.get());
		}
		return status;
	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		return new CopyTypeChange(newPackageName, getName(), origin, destinationURI);
	}

	public static Optional<LibraryElement> getLibraryElement(final Resource resource) {
		return resource.getContents().stream().filter(LibraryElement.class::isInstance).map(LibraryElement.class::cast)
				.findFirst();
	}
}
