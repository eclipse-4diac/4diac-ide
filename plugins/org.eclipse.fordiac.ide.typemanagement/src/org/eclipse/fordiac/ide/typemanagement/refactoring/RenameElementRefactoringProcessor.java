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

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.types.BlockTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.ParticipantManager;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
import org.eclipse.ltk.core.refactoring.participants.RenameArguments;
import org.eclipse.ltk.core.refactoring.participants.RenameProcessor;
import org.eclipse.ltk.core.refactoring.participants.SharableParticipants;

public class RenameElementRefactoringProcessor extends RenameProcessor {

	private URI elementURI;
	private String newName;

	public RenameElementRefactoringProcessor(final URI elementURI, final String newName) {
		this.elementURI = elementURI;
		this.newName = newName;
	}

	@Override
	public RefactoringStatus checkInitialConditions(final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		IdentifierVerifier.verifyIdentifier(newName).ifPresent(status::addFatalError);
		return status;
	}

	@Override
	public RefactoringStatus checkFinalConditions(final IProgressMonitor pm, final CheckConditionsContext context)
			throws CoreException, OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		final CompositeChange change = new CompositeChange(getProcessorName());
		final RenameElementChange change2 = new RenameElementChange(elementURI.lastSegment(), elementURI, newName);
		change.add(change2);
		createChildChanges(change);
		return change;
	}

	private void createChildChanges(final CompositeChange change) {
		final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForURI(elementURI);
		final BlockTypeInstanceSearch search = new BlockTypeInstanceSearch(typeEntry);
		final List<? extends EObject> result = search.performSearch();

		for (final EObject eObject : result) {

		}

		final var obj = getChildByURI(typeEntry.getType(), elementURI);
		String oldName = "";
		if (obj instanceof final VarDeclaration varDecl) {
			oldName = varDecl.getName();
		}

		for (final EObject eObject : result) {

			if (eObject instanceof final FBNetworkElement fbNeworkElement) {
				// System.out.println(fbNeworkElement);
				final IInterfaceElement interfaceElement = fbNeworkElement.getInterfaceElement(newName);
				// System.out.println(interfaceElement);

				final ReconnectPinChange c = new ReconnectPinChange(EcoreUtil.getURI(eObject), FBNetworkElement.class,
						newName, oldName, fbNeworkElement);
				change.add(c);
			}

		}

	}

	public EObject getChildByURI(final EObject parent, final URI uri) {

		final EObject[] found = { null };

		parent.eAllContents().forEachRemaining(child -> {
			final String uriFragment = child.eResource().getURIFragment(child);
			if (uriFragment.equals(uri.fragment())) {
				found[0] = child;
			}

		});

		// Return null if the child EObject is not found
		return found[0];
	}

	@Override
	public RefactoringParticipant[] loadParticipants(final RefactoringStatus status,
			final SharableParticipants sharedParticipants) throws CoreException {
		return ParticipantManager.loadRenameParticipants(status, this, elementURI, new RenameArguments(newName, true),
				new String[] { SystemManager.FORDIAC_PROJECT_NATURE_ID }, sharedParticipants);
	}

	@Override
	public Object[] getElements() {
		return new Object[] { elementURI };
	}

	@Override
	public String getIdentifier() {
		return "org.eclipse.fordiac.ide.typemanagement.renameElement"; //$NON-NLS-1$
	}

	@Override
	public String getProcessorName() {
		return MessageFormat.format(Messages.RenameElementRefactoringProcessor_Name, newName);
	}

	@Override
	public boolean isApplicable() throws CoreException {
		return true;
	}

	public URI getElementURI() {
		return elementURI;
	}

	public void setElementURI(final URI elementURI) {
		this.elementURI = elementURI;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(final String newName) {
		this.newName = newName;
	}
}
