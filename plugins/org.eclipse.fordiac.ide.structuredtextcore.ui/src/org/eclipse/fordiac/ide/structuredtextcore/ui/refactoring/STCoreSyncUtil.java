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
package org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.ICoreRunnable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.fordiac.ide.structuredtextcore.ui.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.RefactoringUtil;
import org.eclipse.xtext.ui.refactoring.ui.SyncUtil;

@SuppressWarnings("restriction")
public class STCoreSyncUtil extends SyncUtil {

	@Override
	public void totalSync(final boolean saveAll, final boolean useProgressDialog, final boolean fork)
			throws InvocationTargetException, InterruptedException {
		RefactoringUtil.checkDirtyEditors();
		super.totalSync(true, useProgressDialog, fork);
	}

	@Override
	public void waitForBuild(final IProgressMonitor monitor) {
		super.waitForBuild(monitor);
		// also need to schedule an explicit build job to avoid a missed build after the
		// refactoring completes
		scheduleAfterBuildJob();
	}

	protected static void scheduleAfterBuildJob() {
		final Job afterBuildJob = Job.create(Messages.STCoreSyncUtil_Building,
				(ICoreRunnable) monitor -> ResourcesPlugin.getWorkspace()
						.build(IncrementalProjectBuilder.INCREMENTAL_BUILD, monitor));
		afterBuildJob.setRule(ResourcesPlugin.getWorkspace().getRoot());
		afterBuildJob.setPriority(Job.BUILD);
		afterBuildJob.schedule();
	}
}
