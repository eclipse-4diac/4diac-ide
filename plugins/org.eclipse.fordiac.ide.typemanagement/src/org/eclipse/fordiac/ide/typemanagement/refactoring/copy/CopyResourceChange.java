/*******************************************************************************
 * Copyright (c) 2000, 2025 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Felix Schmid - adapted for custom use case
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.copy;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.participants.ReorgExecutionLog;
import org.eclipse.ltk.core.refactoring.resource.DeleteResourceChange;
import org.eclipse.ltk.core.refactoring.resource.ResourceChange;

public class CopyResourceChange extends ResourceChange {

	private final IResource origin;
	private final ReorgExecutionLog log;
	private final IContainer destination;

	public CopyResourceChange(final IResource origin, final ReorgExecutionLog log, final IContainer destination) {
		Assert.isTrue(origin instanceof IFile || origin instanceof IFolder || origin instanceof IProject);

		this.origin = origin;
		this.log = log;
		this.destination = destination;
	}

	@Override
	public String getName() {
		return Messages.CopyResourceChange_Name.formatted(origin, destination);
	}

	@Override
	public final Change perform(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		try {
			pm.beginTask(getName(), 2);

			String newName = log.getNewName(origin);
			if (newName == null) {
				newName = origin.getName();
			}

			final boolean performReorg = deleteIfAlreadyExists(SubMonitor.convert(pm, 1), newName);
			if (!performReorg) {
				return null;
			}

			final IPath copyPath = destination.getFullPath().append(newName);
			origin.copy(copyPath, getReorgFlags(), SubMonitor.convert(pm, 1));

			markAsExecuted(origin);
			return new DeleteResourceChange(copyPath, false);
		} finally {
			pm.done();
		}
	}

	@Override
	protected IResource getModifiedResource() {
		return origin;
	}

	/**
	 * returns false if source and destination are the same (in workspace or on
	 * disk) in such case, no action should be performed
	 *
	 * @param pm      the progress monitor
	 * @param newName the new name
	 * @return returns <code>true</code> if the resource already exists
	 * @throws CoreException thrown when the resource cannot be accessed
	 */
	private boolean deleteIfAlreadyExists(final IProgressMonitor pm, final String newName) throws CoreException {
		pm.beginTask("", 1); //$NON-NLS-1$
		final IResource current = destination.findMember(newName);
		if (current == null) {
			return true;
		}
		if (!current.exists()) {
			return true;
		}

		if (FordiacCopyProcessor.areEqualInWorkspaceOrOnDisk(origin, current)) {
			return false;
		}

		switch (current) {
		case final IFile file -> file.delete(false, true, SubMonitor.convert(pm, 1));
		case final IFolder folder -> folder.delete(false, true, SubMonitor.convert(pm, 1));
		default -> Assert.isTrue(false);
		}
		return true;
	}

	private static int getReorgFlags() {
		return IResource.KEEP_HISTORY | IResource.SHALLOW;
	}

	private void markAsExecuted(final IResource resource) {
		final ReorgExecutionLog log = getAdapter(ReorgExecutionLog.class);
		if (log != null) {
			log.markAsProcessed(resource);
		}
	}
}
