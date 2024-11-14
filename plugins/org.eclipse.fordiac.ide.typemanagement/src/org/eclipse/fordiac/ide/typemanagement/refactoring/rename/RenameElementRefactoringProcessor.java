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
package org.eclipse.fordiac.ide.typemanagement.refactoring.rename;

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
import org.eclipse.fordiac.ide.model.search.types.BlockTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.search.types.DataTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.ReconnectPinChange;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.ParticipantManager;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
import org.eclipse.ltk.core.refactoring.participants.RenameArguments;
import org.eclipse.ltk.core.refactoring.participants.RenameProcessor;
import org.eclipse.ltk.core.refactoring.participants.SharableParticipants;

/**
 * A processor for rename refactorings triggered within a model context, such as
 * a property sheet.
 */
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
		change.add(
				new RenameElementChange(MessageFormat.format(Messages.RenameElementRefactoringProcessor_RenamePinInType,
						elementURI.lastSegment()), elementURI, newName));
		createChildChanges(change);
		return change;
	}

	private void createChildChanges(final CompositeChange change) {
		final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForURI(elementURI);
		if (typeEntry == null) {
			return;
		}

		final List<? extends EObject> result = (typeEntry instanceof final DataTypeEntry dtEntry)
				? new DataTypeInstanceSearch(dtEntry).performSearch()
				: new BlockTypeInstanceSearch(typeEntry).performSearch();
		final var eChild = getChildByURI(typeEntry.getType(), elementURI);

		if (eChild instanceof final IInterfaceElement interfaceElement) {
			for (final EObject eObject : result) {
				if (eObject instanceof final FBNetworkElement element) {
					createRenameInterfaceChanges(change, element, interfaceElement);
				}
			}
		}

	}

	private void createRenameInterfaceChanges(final CompositeChange change, final FBNetworkElement element,
			final IInterfaceElement interfaceElement) {
		final String oldName = interfaceElement.getName();
		change.add(new ReconnectPinChange(EcoreUtil.getURI(element), FBNetworkElement.class, newName, oldName));
	}

	public static EObject getChildByURI(final EObject parent, final URI uri) {
		final EObject[] found = { null };
		parent.eAllContents().forEachRemaining(child -> {
			final String uriFragment = child.eResource().getURIFragment(child);
			if (uriFragment.equals(uri.fragment())) {
				found[0] = child;
			}

		});
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
