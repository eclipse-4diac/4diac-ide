/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.commands.util;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.commands.Command;

public abstract class AbstractWorkspaceCommand extends Command {

	protected AbstractWorkspaceCommand() {
	}

	protected AbstractWorkspaceCommand(final String label) {
		super(label);
	}

	@Override
	public final void execute() {
		try {
			getWorkspace().run(this::doExecute, getSchedulingRule(), IWorkspace.AVOID_UPDATE, null);
		} catch (final CoreException e) {
			FordiacLogHelper.logError(getLabel(), e);
		}
	}

	@Override
	public final void redo() {
		try {
			getWorkspace().run(this::doRedo, getSchedulingRule(), IWorkspace.AVOID_UPDATE, null);
		} catch (final CoreException e) {
			FordiacLogHelper.logError(getLabel(), e);
		}
	}

	@Override
	public final void undo() {
		try {
			getWorkspace().run(this::doUndo, getSchedulingRule(), IWorkspace.AVOID_UPDATE, null);
		} catch (final CoreException e) {
			FordiacLogHelper.logError(getLabel(), e);
		}
	}

	protected abstract void doExecute(IProgressMonitor monitor) throws CoreException;

	protected abstract void doRedo(IProgressMonitor monitor) throws CoreException;

	protected abstract void doUndo(IProgressMonitor monitor) throws CoreException;

	protected abstract ISchedulingRule getSchedulingRule();

	protected static IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}
}
