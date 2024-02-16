/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;
import org.eclipse.xtext.ui.refactoring.ui.IRenameElementContext;

@SuppressWarnings("restriction")
public class RenameElementRefactoringParticipant extends RenameParticipant {

	private URI elementURI;

	@Override
	protected boolean initialize(final Object element) {
		if (element instanceof final IRenameElementContext context && isRelevant(context)) {
			elementURI = getCanonicalURI(context.getTargetElementURI());
			return true;
		}
		return false;
	}

	@SuppressWarnings("static-method") // subclasses may override
	protected boolean isRelevant(final IRenameElementContext context) {
		return LibraryElementPackage.Literals.INAMED_ELEMENT.isSuperTypeOf(context.getTargetElementEClass())
				&& (isTypeURI(context.getTargetElementURI()) || isDataTypeURI(context.getTargetElementURI()));
	}

	@Override
	public RefactoringStatus checkConditions(final IProgressMonitor pm, final CheckConditionsContext context)
			throws OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		IdentifierVerifier.verifyIdentifier(getArguments().getNewName()).ifPresent(status::addFatalError);
		return status;
	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		final CompositeChange change = new CompositeChange(getName());
		change.add(new RenameElementChange(elementURI.lastSegment(), elementURI, getArguments().getNewName()));
		return change;
	}

	protected static URI getCanonicalURI(final URI uri) {
		if (isTypeURI(uri)) {
			return uri.trimFragment().appendFragment("/" + uri.fragment().substring(2)); //$NON-NLS-1$
		}
		return uri;
	}

	protected static boolean isTypeURI(final URI uri) {
		return uri != null && uri.hasFragment() && uri.fragment().startsWith("/1"); //$NON-NLS-1$
	}

	protected static boolean isDataTypeURI(final URI uri) {
		return TypeLibraryTags.DATA_TYPE_FILE_ENDING.equalsIgnoreCase(uri.fileExtension());
	}

	@Override
	public String getName() {
		return Messages.RenameElementRefactoringParticipant_Name;
	}
}
