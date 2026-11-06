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
 *   Michael Oberlehner - call the participants of the rename element refactoring
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.rename;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;
import org.eclipse.ltk.core.refactoring.participants.SharableParticipants;
import org.eclipse.xtext.ui.refactoring.impl.AbstractProcessorBasedRenameParticipant;
import org.eclipse.xtext.ui.refactoring.ui.IRenameElementContext;

/**
 * A participant for rename refactorings triggered within an Xtext context. It
 * renames the model element like {@link RenameElementRefactoringProcessor} and
 * also calls the participants of that processor.
 */
@SuppressWarnings("restriction")
public class RenameElementRefactoringParticipant extends RenameParticipant {

	private URI elementURI;
	private List<RefactoringParticipant> participants = List.of();
	private final RefactoringStatus loadStatus = new RefactoringStatus();

	@Override
	protected boolean initialize(final Object element) {
		if (element instanceof final IRenameElementContext context && isRelevant(context)) {
			elementURI = getCanonicalURI(context.getTargetElementURI());
			participants = loadParticipants();
			return true;
		}
		return false;
	}

	private List<RefactoringParticipant> loadParticipants() {
		final RenameElementRefactoringProcessor processor = new RenameElementRefactoringProcessor(elementURI,
				getArguments().getNewName());
		try {
			return Stream.of(processor.loadParticipants(loadStatus, new SharableParticipants()))
					.filter(participant -> !(participant instanceof AbstractProcessorBasedRenameParticipant))
					.toList();
		} catch (final CoreException e) {
			loadStatus.merge(RefactoringStatus.create(e.getStatus()));
			return List.of();
		}
	}

	@SuppressWarnings("static-method") // subclasses may override
	protected boolean isRelevant(final IRenameElementContext context) {
		return LibraryElementPackage.Literals.INAMED_ELEMENT.isSuperTypeOf(context.getTargetElementEClass())
				&& (isTypeURI(context.getTargetElementURI()) || isDataTypeURI(context.getTargetElementURI())
						|| isAttributeTypeURI(context.getTargetElementURI()));
	}

	@Override
	public RefactoringStatus checkConditions(final IProgressMonitor pm, final CheckConditionsContext context)
			throws OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		IdentifierVerifier.verifyIdentifier(getArguments().getNewName()).ifPresent(status::addFatalError);
		status.merge(loadStatus);
		final SubMonitor monitor = SubMonitor.convert(pm, participants.size());
		for (final RefactoringParticipant participant : participants) {
			status.merge(participant.checkConditions(monitor.split(1), context));
		}
		return status;
	}

	@Override
	public Change createPreChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		final SubMonitor monitor = SubMonitor.convert(pm, participants.size());
		final List<Change> changes = new ArrayList<>();
		for (final RefactoringParticipant participant : participants) {
			addChange(changes, participant.createPreChange(monitor.split(1)));
		}
		return changes.isEmpty() ? null : new CompositeChange(getName(), changes.toArray(Change[]::new));
	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		final SubMonitor monitor = SubMonitor.convert(pm, participants.size() + 1);
		final List<Change> changes = new ArrayList<>();
		addChange(changes,
				RenameElementRefactoringHelper.createRenameChange(getName(), elementURI, getArguments().getNewName()));
		monitor.worked(1);
		for (final RefactoringParticipant participant : participants) {
			addChange(changes, participant.createChange(monitor.split(1)));
		}
		return changes.size() == 1 ? changes.getFirst()
				: new CompositeChange(getName(), changes.toArray(Change[]::new));
	}

	private static void addChange(final List<Change> changes, final Change change) {
		if (change != null) {
			changes.add(change);
		}
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

	protected static boolean isAttributeTypeURI(final URI uri) {
		return TypeLibraryTags.ATTRIBUTE_TYPE_FILE_ENDING.equalsIgnoreCase(uri.fileExtension());
	}

	@Override
	public String getName() {
		return Messages.RenameElementRefactoringParticipant_Name;
	}
}
