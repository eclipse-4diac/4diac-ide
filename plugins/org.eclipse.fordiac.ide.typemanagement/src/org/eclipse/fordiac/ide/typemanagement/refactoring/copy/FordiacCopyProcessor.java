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

package org.eclipse.fordiac.ide.typemanagement.refactoring.copy;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.NullChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.CopyArguments;
import org.eclipse.ltk.core.refactoring.participants.CopyParticipant;
import org.eclipse.ltk.core.refactoring.participants.CopyProcessor;
import org.eclipse.ltk.core.refactoring.participants.ParticipantManager;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
import org.eclipse.ltk.core.refactoring.participants.ReorgExecutionLog;
import org.eclipse.ltk.core.refactoring.participants.SharableParticipants;

public final class FordiacCopyProcessor extends CopyProcessor {

	public enum ExistsResolve {
		OVERWRITE, DONT_COPY, RENAME, CANCEL_ALL;

		private String newName;

		public ExistsResolve setNewName(final String newName) {
			this.newName = newName;
			return this;
		}

		public String getNewName() {
			return newName;
		}
	}

	private final IResource[] files;
	private final boolean[] doCopy;
	private final IContainer destination;
	private final ReorgExecutionLog log;
	private final ICopyRefactoringQueries queries;
	private boolean canceled = false;

	public FordiacCopyProcessor(final IResource[] files, final IContainer destination,
			final ICopyRefactoringQueries queries) {
		this.files = files;
		this.destination = destination;
		this.queries = queries;
		this.doCopy = new boolean[files.length];
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
		for (int i = 0; i < files.length; i++) {
			final IResource file = files[i];
			final ExistsResolve resolve = handleAlreadyExists(file);

			if (resolve == ExistsResolve.RENAME) {
				log.setNewName(file, resolve.getNewName());
				doCopy[i] = true;
			} else if (resolve == ExistsResolve.OVERWRITE) {
				doCopy[i] = true;
			} else if (resolve == ExistsResolve.CANCEL_ALL) {
				canceled = true;
				break;
			}
		}
		return new RefactoringStatus();
	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		if (canceled) {
			return new NullChange();
		}

		final CompositeChange compChange = new CompositeChange(Messages.FordiacCopyProcessor_CompositeChangeName);

		for (int i = 0; i < files.length; i++) {
			if (!doCopy[i]) {
				continue;
			}
			compChange.add(new CopyResourceChange(files[i], log, destination));
		}
		return compChange;
	}

	@Override
	public RefactoringParticipant[] loadParticipants(final RefactoringStatus status,
			final SharableParticipants sharedParticipants) throws CoreException {
		if (canceled) {
			return null;
		}

		final String[] affectedNatures = SystemManager.getNatureIDs();
		final List<CopyParticipant> result = new ArrayList<>();

		for (int i = 0; i < files.length; i++) {
			if (!doCopy[i]) {
				continue;
			}
			final CopyParticipant[] participants = ParticipantManager.loadCopyParticipants(status, this, files[i],
					new CopyArguments(destination, log), affectedNatures, sharedParticipants);
			result.addAll(Arrays.asList(participants));
		}
		return result.toArray(new RefactoringParticipant[result.size()]);
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

	public static boolean areEqualInWorkspaceOrOnDisk(final IResource r1, final IResource r2) {
		if (r1 == null || r2 == null) {
			return false;
		}
		if (r1.equals(r2)) {
			return true;
		}
		final URI r1Location = r1.getLocationURI();
		final URI r2Location = r2.getLocationURI();
		if (r1Location == null || r2Location == null) {
			return false;
		}
		return r1Location.equals(r2Location);
	}

	private ExistsResolve handleAlreadyExists(final IResource file) {
		final IResource current = destination.findMember(file.getName());
		if (current == null || !current.exists()) {
			return ExistsResolve.OVERWRITE;
		}

		if (areEqualInWorkspaceOrOnDisk(file, current)) {
			return queries.queryOverwriteSelf(file, destination);
		}

		if (current instanceof IFolder || !(current instanceof IFile)) {
			return ExistsResolve.OVERWRITE;
		}
		return queries.queryOverwrite(file, destination);
	}
}
