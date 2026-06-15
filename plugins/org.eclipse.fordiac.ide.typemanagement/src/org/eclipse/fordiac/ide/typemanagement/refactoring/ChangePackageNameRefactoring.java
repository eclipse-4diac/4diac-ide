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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CheckConditionsOperation;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.CreateChangeOperation;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.ui.refactoring.RefactoringUI;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.swt.widgets.Shell;

public class ChangePackageNameRefactoring extends Refactoring {

	private final TypeEntry typeEntry;
	private String newPackageName;

	public ChangePackageNameRefactoring(final TypeEntry typeEntry, final String newPackageName) {
		this.typeEntry = typeEntry;
		this.newPackageName = newPackageName;
	}

	public static void openWizard(final TypeEntry typeEntry, final Shell shell) {
		try {
			RefactoringUtil.saveAllAndBuild();
			final ChangePackageNameRefactoring refactoring = new ChangePackageNameRefactoring(typeEntry,
					typeEntry.getPackageName());
			final ChangePackageNameRefactoringWizard wizard = new ChangePackageNameRefactoringWizard(refactoring);
			final RefactoringWizardOpenOperation openOperation = new RefactoringWizardOpenOperation(wizard);
			openOperation.run(shell, refactoring.getName());
		} catch (final OperationCanceledException e) {
			// ignore
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (final Exception e) {
			FordiacLogHelper.logError("Error during package name refactoring", e); //$NON-NLS-1$
		}
	}

	public static void run(final TypeEntry typeEntry, final String newPackageName, final Shell shell) {
		final ChangePackageNameRefactoring refactoring = new ChangePackageNameRefactoring(typeEntry, newPackageName);
		final CheckConditionsOperation checkOp = new CheckConditionsOperation(refactoring,
				CheckConditionsOperation.ALL_CONDITIONS);
		final CreateChangeOperation changeOp = new CreateChangeOperation(checkOp, RefactoringStatus.ERROR);

		try {
			final IProgressMonitor pm = new NullProgressMonitor();
			changeOp.run(pm);
			final Change change = changeOp.getChange();
			if (change != null) {
				change.perform(pm);
			} else if (changeOp.getConditionCheckingFailedSeverity() >= RefactoringStatus.ERROR) {
				RefactoringUI.createLightWeightStatusDialog(changeOp.getConditionCheckingStatus(), shell,
						Messages.ChangePackageNameRefactoring_ProblemOccurred).open();
			} else {
				FordiacLogHelper.logWarning("change package name refactoring change could not be created for " //$NON-NLS-1$
						+ getRefactoringInfo(typeEntry, newPackageName));
			}
		} catch (final CoreException e) {
			final String refactoringInfo = getRefactoringInfo(typeEntry, newPackageName);
			ErrorDialog.openError(shell, Messages.ChangePackageNameRefactoring_ProblemOccurred, refactoringInfo,
					e.getStatus());
			FordiacLogHelper.logError("Error changing package name for " + refactoringInfo, e); //$NON-NLS-1$
		}
	}

	@Override
	public String getName() {
		return Messages.ChangePackageNameRefactoring_Name;
	}

	@Override
	public RefactoringStatus checkInitialConditions(final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		if (typeEntry == null || typeEntry.getFile() == null) {
			status.addFatalError(Messages.ChangePackageNameRefactoring_NoTypeEntry);
		}
		return status;
	}

	@Override
	public RefactoringStatus checkFinalConditions(final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		IdentifierVerifier.verifyPackageName(newPackageName).ifPresent(status::addFatalError);
		return status;
	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		final CompositeChange change = new CompositeChange(getName());
		addPackageNameChangeForType(change);
		addInstanceChanges(change);
		return change;
	}

	private void addPackageNameChangeForType(final CompositeChange change) {
		final List<ModelEdit<?>> modelEdits = new ArrayList<>();
		TypeRefactoringHelper.addPackageNameModelEdit(modelEdits, typeEntry, newPackageName);
		addChange(change, ModelEditChange.fromModelEdits(Messages.MoveTypeToPackage, modelEdits));
	}

	private void addInstanceChanges(final CompositeChange change) {
		final List<ModelEdit<?>> modelEdits = new ArrayList<>();
		TypeRefactoringHelper.addModelEditsForPackageChangedType(modelEdits, typeEntry, newPackageName);
		addChange(change, ModelEditChange.fromModelEdits(Messages.MoveTypeToPackage_UpdateInstances, modelEdits));
	}

	private static void addChange(final CompositeChange compositeChange, final Change change) {
		if (change != null) {
			compositeChange.add(change);
		}
	}

	private static String getRefactoringInfo(final TypeEntry typeEntry, final String newPackageName) {
		final String typeEntryInfo = typeEntry != null ? typeEntry.getFullTypeName() : "<null>"; //$NON-NLS-1$
		return "typeEntry=" + typeEntryInfo + ", newPackageName=" + newPackageName; //$NON-NLS-1$ //$NON-NLS-2$
	}

	public String getNewPackageName() {
		return newPackageName;
	}

	public void setNewPackageName(final String newPackageName) {
		this.newPackageName = newPackageName;
	}

	TypeLibrary getTypeLibrary() {
		return typeEntry != null ? typeEntry.getTypeLibrary() : null;
	}
}
