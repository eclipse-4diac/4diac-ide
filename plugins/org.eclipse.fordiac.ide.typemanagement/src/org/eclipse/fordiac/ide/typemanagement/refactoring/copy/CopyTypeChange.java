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

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.NullChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class CopyTypeChange extends Change {

	private final URI destination;
	private String newPackageName;

	protected CopyTypeChange(final URI destination) {
		this.destination = destination;
	}

	@Override
	public void initializeValidationData(final IProgressMonitor pm) {
		newPackageName = PackageNameHelper.getPackageNameFromURI(destination);
	}

	@Override
	public RefactoringStatus isValid(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public String getName() {
		return MessageFormat.format(Messages.MoveTypeToPackage_RenamePackageTo, newPackageName);
	}

	@Override
	public Change perform(final IProgressMonitor pm) throws CoreException {
		final var resource = getResource(destination);
		if (resource.isEmpty()) {
			throw new CoreException(
					Status.error(MessageFormat.format(Messages.CopyTypeChange_CannotLoadResource, destination)));
		}
		final var optElement = getLibraryElement(resource.get());
		if (optElement.isEmpty()) {
			return null;
		}
		final LibraryElement element = optElement.get();

		final String typeName = TypeEntry.getTypeNameFromFileName(destination.lastSegment());
		if (!element.getName().equals(typeName)) {
			element.setName(typeName);
		}
		PackageNameHelper.setPackageName(element, newPackageName);
		try {
			resource.get().save(Map.of());
		} catch (final IOException e) {
			throw new CoreException(
					Status.error(MessageFormat.format(Messages.CopyTypeChange_CannotSaveResource, destination), e));
		}
		return new NullChange(); // no undo change necessary, the element will be deleted
	}

	@Override
	public Resource getModifiedElement() {
		return getResource(destination).orElse(null);
	}

	@Override
	public Object[] getAffectedObjects() {
		final Resource res = getModifiedElement();
		if (res != null) {
			return new Object[] { res };
		}
		return super.getAffectedObjects();
	}

	private static Optional<Resource> getResource(final URI uri) {
		if (!uri.isPlatformResource()) {
			return Optional.empty();
		}
		final ResourceSetImpl resourceSet = new ResourceSetImpl();
		final Resource resource = resourceSet.getResource(uri, true);
		if (resource == null || !resource.getErrors().isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(resource);
	}

	private static Optional<LibraryElement> getLibraryElement(final Resource resource) {
		return resource.getContents().stream().filter(LibraryElement.class::isInstance).map(LibraryElement.class::cast)
				.findFirst();
	}
}
