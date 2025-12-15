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

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.CopyParticipant;

public class CopyTypeParticipant extends CopyParticipant {

	private CompositeChange change;

	@Override
	protected boolean initialize(final Object element) {
		if (element instanceof final IResource resource
				&& getArguments().getDestination() instanceof final IContainer destination) {
			change = new CompositeChange(getName());
			final URI destURI = URI.createPlatformResourceURI(destination.getFullPath().toString(), true);
			try {
				addElement(resource, destURI);
			} catch (final CoreException e) {
				return false;
			}
			return true;
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
		return new RefactoringStatus();
	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		return change;
	}

	private void addElement(final IResource resource, final URI destination) throws CoreException {
		if (resource instanceof final IFile file) {
			if (TypeLibraryManager.INSTANCE.getTypeEntryForFile(file) != null) {
				change.add(new CopyTypeChange(getName(), destination.appendSegment(file.getName())));
			}
		} else if (resource instanceof final IContainer container) {
			for (final IResource member : container.members()) {
				addElement(member, destination.appendSegment(container.getName()));
			}
		}
	}
}
