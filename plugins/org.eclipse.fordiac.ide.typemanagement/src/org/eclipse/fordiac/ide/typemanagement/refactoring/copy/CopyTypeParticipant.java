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

import java.util.Optional;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.RefactoringUtil;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.CopyParticipant;

public class CopyTypeParticipant extends CopyParticipant {

	private IResource resource;
	private IContainer destination;

	@Override
	protected boolean initialize(final Object element) {
		if ((element instanceof IFile || element instanceof IFolder)
				&& getArguments().getDestination() instanceof final IContainer dest) {
			resource = (IResource) element;
			destination = dest;
			return RefactoringUtil.containsTypeEntryFile(resource);
		}
		return false;
	}

	@Override
	public String getName() {
		return Messages.CopyTypeChange_RenamePackage;
	}

	@Override
	public RefactoringStatus checkConditions(final IProgressMonitor pm, final CheckConditionsContext context)
			throws OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();

		String newName = getArguments().getExecutionLog().getNewName(resource);
		if (newName != null) {
			final int lastIdx = newName.lastIndexOf('.');
			if (lastIdx != -1) {
				newName = newName.substring(0, lastIdx); // remove file extension
			}
			final Optional<String> identErrorMsg = IdentifierVerifier.verifyIdentifier(newName);
			if (identErrorMsg.isPresent()) {
				status.addFatalError(identErrorMsg.get());
			}
		}

		final String packageNameContainer = PackageNameHelper.getPackageNameFromContainer(destination);
		final Optional<String> packageErrorMsg = IdentifierVerifier.verifyPackageName(packageNameContainer);
		if (packageErrorMsg.isPresent()) {
			status.addFatalError(packageErrorMsg.get());
		}
		return status;
	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		final CompositeChange change = new CompositeChange(getName());
		final URI destURI = URI.createPlatformResourceURI(destination.getFullPath().toString(), true);
		try {
			addElement(change, resource, destURI);
		} catch (final CoreException e) {
			return null;
		}
		return change;
	}

	private void addElement(final CompositeChange change, final IResource resource, final URI destination)
			throws CoreException {
		if (resource instanceof final IFile file) {
			if (TypeLibraryManager.INSTANCE.getTypeEntryForFile(file) != null) {
				change.add(new CopyTypeChange(destination.appendSegment(getNewName(file))));
			}
		} else if (resource instanceof final IContainer container) {
			for (final IResource member : container.members()) {
				addElement(change, member, destination.appendSegment(getNewName(container)));
			}
		}
	}

	private String getNewName(final IResource res) {
		final String name = getArguments().getExecutionLog().getNewName(res);
		return name == null ? res.getName() : name;
	}
}
