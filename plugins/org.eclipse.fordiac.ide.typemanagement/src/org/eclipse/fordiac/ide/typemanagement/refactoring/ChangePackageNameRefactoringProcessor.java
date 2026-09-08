/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
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

public class ChangePackageNameRefactoringProcessor extends RenameProcessor {

	public static final String IDENTIFIER = "org.eclipse.fordiac.ide.typemanagement.changePackageName"; //$NON-NLS-1$

	private final TypeEntry typeEntry;
	private String newPackageName;

	public ChangePackageNameRefactoringProcessor(final TypeEntry typeEntry, final String newPackageName) {
		this.typeEntry = typeEntry;
		this.newPackageName = Objects.requireNonNullElse(newPackageName, ""); //$NON-NLS-1$
	}

	@Override
	public RefactoringStatus checkInitialConditions(final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		final TypeEntry typeEntry = getTypeEntry();
		if (typeEntry == null || typeEntry.getFile() == null) {
			status.addFatalError(Messages.ChangePackageNameRefactoring_NoTypeEntry);
		}
		return status;
	}

	@Override
	public RefactoringStatus checkFinalConditions(final IProgressMonitor pm, final CheckConditionsContext context)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		IdentifierVerifier.verifyPackageName(newPackageName).ifPresent(status::addFatalError);
		return status;
	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		final CompositeChange change = new CompositeChange(getProcessorName());
		addPackageNameChangeForType(change);
		addInstanceChanges(change);
		return change;
	}

	@Override
	public RefactoringParticipant[] loadParticipants(final RefactoringStatus status,
			final SharableParticipants sharedParticipants) throws CoreException {
		return ParticipantManager.loadRenameParticipants(status, this, typeEntry, new RenameArguments(newPackageName, true),
				new String[] { SystemManager.FORDIAC_PROJECT_NATURE_ID }, sharedParticipants);
	}

	@Override
	public Object[] getElements() {
		return new Object[] { typeEntry };
	}

	@Override
	public String getIdentifier() {
		return IDENTIFIER;
	}

	@Override
	public String getProcessorName() {
		return MessageFormat.format(Messages.ChangePackageNameRefactoringProcessor_Name, newPackageName);
	}

	@Override
	public boolean isApplicable() throws CoreException {
		return true;
	}

	private void addPackageNameChangeForType(final CompositeChange change) {
		final List<ModelEdit<?>> modelEdits = new ArrayList<>();
		TypeRefactoringHelper.addPackageNameModelEdit(modelEdits, getTypeEntry(), newPackageName);
		addChange(change, ModelEditChange.fromModelEdits(Messages.MoveTypeToPackage, modelEdits));
	}

	private void addInstanceChanges(final CompositeChange change) {
		final List<ModelEdit<?>> modelEdits = new ArrayList<>();
		TypeRefactoringHelper.addModelEditsForPackageChangedType(modelEdits, getTypeEntry(), newPackageName);
		addChange(change, ModelEditChange.fromModelEdits(Messages.MoveTypeToPackage_UpdateInstances, modelEdits));
	}

	private static void addChange(final CompositeChange compositeChange, final Change change) {
		if (change != null) {
			compositeChange.add(change);
		}
	}

	public TypeEntry getTypeEntry() {
		return typeEntry;
	}

	public TypeLibrary getTypeLibrary() {
		return typeEntry != null ? typeEntry.getTypeLibrary() : null;
	}

	public String getNewPackageName() {
		return newPackageName;
	}

	public void setNewPackageName(final String newPackageName) {
		this.newPackageName = Objects.requireNonNullElse(newPackageName, ""); //$NON-NLS-1$
	}
}
