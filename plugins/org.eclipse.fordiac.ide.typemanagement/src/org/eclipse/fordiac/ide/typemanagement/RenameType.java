/*******************************************************************************
 * Copyright (c) 2014 -2017 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement;

import java.text.MessageFormat;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.mapping.IResourceChangeDescriptionFactory;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.model.IdentifierVerifyer;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;
import org.eclipse.ltk.core.refactoring.participants.ResourceChangeChecker;

public class RenameType extends RenameParticipant {

	private String oldName;

	@Override
	protected boolean initialize(final Object element) {
		if (element instanceof IFile) {
			oldName = ((IFile) element).getName();
		}
		return (element instanceof IFile);
	}

	@Override
	public String getName() {
		return Messages.RenameType_Name;
	}

	@Override
	public RefactoringStatus checkConditions(final IProgressMonitor pm, final CheckConditionsContext context)
			throws OperationCanceledException {
		final ResourceChangeChecker resChecker = context.getChecker(ResourceChangeChecker.class);
		final IResourceChangeDescriptionFactory deltaFactory = resChecker.getDeltaFactory();
		final IResourceDelta delta = deltaFactory.getDelta();
		return verifyAffectedChildren(delta.getAffectedChildren());
	}

	private RefactoringStatus verifyAffectedChildren(final IResourceDelta[] affectedChildren) {
		for (final IResourceDelta resourceDelta : affectedChildren) {

			if (resourceDelta.getMovedFromPath() != null) {

				if (resourceDelta.getResource() instanceof IFile) {
					final String newName = ((IFile) resourceDelta.getResource()).getName();
					if (nameExistsInTypeLibrary(resourceDelta, newName)) {
						return RefactoringStatus.createFatalErrorStatus(
								MessageFormat.format(Messages.RenameType_TypeExists, newName));
					}
				}

				final String name = getTypeName(resourceDelta);
				if (name != null && !IdentifierVerifyer.isValidIdentifier(name)) {
					return getWrongIdentifierErrorStatus();
				}

			} else if (resourceDelta.getMovedToPath() == null) {
				return verifyAffectedChildren(resourceDelta.getAffectedChildren());
			}
		}

		return new RefactoringStatus();
	}

	protected boolean nameExistsInTypeLibrary(final IResourceDelta resourceDelta, final String newName) {
		return !oldName.equals(newName) && !newName.equals("a" + oldName) //$NON-NLS-1$
				&& TypeLibrary.getPaletteEntryForFile(((IFile) resourceDelta.getResource())) != null;
	}

	protected String getTypeName(final IResourceDelta resourceDelta) {
		if (resourceDelta.getResource() instanceof IFile) {
			return TypeLibrary.getTypeNameFromFile((IFile) resourceDelta.getResource());
		}
		return null;
	}

	protected RefactoringStatus getWrongIdentifierErrorStatus() {
		return RefactoringStatus.createFatalErrorStatus(Messages.RenameType_InvalidIdentifierErrorMessage);
	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		return null;
	}

}
