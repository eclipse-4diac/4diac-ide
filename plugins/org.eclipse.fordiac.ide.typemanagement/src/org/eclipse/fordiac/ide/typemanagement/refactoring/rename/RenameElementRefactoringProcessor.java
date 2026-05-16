/*******************************************************************************
 * Copyright (c) 2024, 2026 Martin Erich Jobst and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Felix Schmid - changed to use ModelEdits
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.rename;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.search.types.BlockTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.search.types.DataTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.ModelEdit;
import org.eclipse.fordiac.ide.typemanagement.refactoring.ModelEditChange;
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
		final List<ModelEdit<?>> instanceModelEdits = new ArrayList<>();

		createChildChanges(instanceModelEdits);
		final RenameElementModelEdit typeModelEdit = new RenameElementModelEdit(MessageFormat
				.format(Messages.RenameElementRefactoringProcessor_RenamePinInType, elementURI.lastSegment()),
				elementURI, newName);

		if (instanceModelEdits.isEmpty()) {
			return ModelEditChange.fromModelEdits(getProcessorName(), List.of(typeModelEdit));
		}

		final CompositeChange result = new CompositeChange(getProcessorName());
		result.add(ModelEditChange.fromModelEdits(getProcessorName(), instanceModelEdits));
		result.add(ModelEditChange.fromModelEdits(getProcessorName(), List.of(typeModelEdit)));
		return result;
	}

	private void createChildChanges(final List<ModelEdit<?>> modelEdits) {
		final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForURI(elementURI);
		if (typeEntry == null) {
			return;
		}

		final List<? extends EObject> result = (typeEntry instanceof final DataTypeEntry dtEntry)
				? DataTypeInstanceSearch.createSearchIncludingDerivedDataTypes(dtEntry).performSearch()
				: new BlockTypeInstanceSearch(typeEntry).performSearch();
		final var eChild = getChildByURI(typeEntry.getType(), elementURI);

		if (eChild instanceof final IInterfaceElement interfaceElement) {
			result.stream().filter(BlockFBNetworkElement.class::isInstance).map(BlockFBNetworkElement.class::cast)
					.forEach(element -> createRenameInterfaceChanges(modelEdits, element, interfaceElement));
		}
	}

	private void createRenameInterfaceChanges(final List<ModelEdit<?>> modelEdits, final BlockFBNetworkElement element,
			final IInterfaceElement interfaceElement) {
		final IInterfaceElement instancePin = element.getInterface()
				.getInterfaceElement(List.of(interfaceElement.getName()));
		if (instancePin != null) {
			modelEdits.add(new RenameElementModelEdit(MessageFormat
					.format(Messages.RenameElementRefactoringProcessor_RenamePinInInstance,
							interfaceElement.getName()),
					EcoreUtil.getURI(instancePin), newName));
		}
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
