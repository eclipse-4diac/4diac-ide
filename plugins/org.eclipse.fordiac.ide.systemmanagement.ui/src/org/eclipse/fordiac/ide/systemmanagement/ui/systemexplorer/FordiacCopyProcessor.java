/*******************************************************************************
 * Copyright (c) 2025 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.NullChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.CopyArguments;
import org.eclipse.ltk.core.refactoring.participants.CopyProcessor;
import org.eclipse.ltk.core.refactoring.participants.ParticipantManager;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
import org.eclipse.ltk.core.refactoring.participants.ReorgExecutionLog;
import org.eclipse.ltk.core.refactoring.participants.SharableParticipants;

public final class FordiacCopyProcessor extends CopyProcessor {

	private final IResource[] files;
	private final IContainer destination;
	private final ReorgExecutionLog log;

	public FordiacCopyProcessor(final IResource[] files, final IContainer destination) {
		this.files = files;
		this.destination = destination;
		log = new ReorgExecutionLog();
	}

	@Override
	public RefactoringStatus checkInitialConditions(final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public RefactoringStatus checkFinalConditions(final IProgressMonitor pm, final CheckConditionsContext context)
			throws CoreException, OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		// actual copy/paste is handled by FordiacCopyAction & FordiacPasteAction
		// all further modifications are done via CopyParticipants
		return new NullChange();
	}

	@Override
	public RefactoringParticipant[] loadParticipants(final RefactoringStatus status,
			final SharableParticipants sharedParticipants) throws CoreException {
		return ParticipantManager.loadCopyParticipants(status, this, files, new CopyArguments(destination, log),
				new String[] { SystemManager.FORDIAC_PROJECT_NATURE_ID }, sharedParticipants);
	}

	@Override
	public Object[] getElements() {
		return files;
	}

	@Override
	public String getIdentifier() {
		return "org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer.copyProcessor"; //$NON-NLS-1$
	}

	@Override
	public String getProcessorName() {
		return Messages.FordiacCopyProcessor_Name;
	}

	@Override
	public boolean isApplicable() throws CoreException {
		return true;
	}
}
