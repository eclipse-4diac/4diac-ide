/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ant.ant;

import java.io.File;
import java.text.MessageFormat;
import java.util.function.Predicate;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.eclipse.ant.core.AntCorePlugin;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;

public class Import4diacProject extends Task {

	private String projectPathString;

	public void setProjectPath(final String value) {
		projectPathString = value;
	}

	@Override
	public void execute() throws BuildException {
		if (projectPathString == null) {
			throw new BuildException("Project path not specified!"); //$NON-NLS-1$
		}
		try {
			final IWorkspace workspace = ResourcesPlugin.getWorkspace();

			final var desc = workspace.getDescription();
			desc.setAutoBuilding(false);
			workspace.setDescription(desc);
			Job.getJobManager().cancel(null);

			// ensure SystemManager is loaded
			@SuppressWarnings("unused")
			final SystemManager mgr = SystemManager.INSTANCE;

			final IPath projectPath = getProjectPath();
			final IProjectDescription description = loadProjectDescription(workspace, projectPath);
			final IProject project = workspace.getRoot().getProject(description.getName());

			if (validateProjectLocation(project, projectPath)) {
				// the project is not already loaded in the workspace
				loadProject(description, project, projectPath);
			}
			check4diacProject(project);
			TypeLibraryManager.INSTANCE.getTypeLibrary(project).reload();
			waitBuilderJobsComplete();
			runFullBuild(project);
			waitBuilderJobsComplete();

		} catch (final CoreException e) {
			throw new BuildException(e);
		}
	}

	private IPath getProjectPath() {
		return new Path(projectPathString);
	}

	private static IProjectDescription loadProjectDescription(final IWorkspace workspace, final IPath projectPath)
			throws CoreException {
		final IPath projectFilePath = projectPath.append(File.separator + ".project"); //$NON-NLS-1$
		return workspace.loadProjectDescription(projectFilePath);
	}

	private boolean validateProjectLocation(final IProject project, final IPath projectPath) throws CoreException {
		if (project.exists()) {
			// if the project already exists we can only import it if it has the same
			// location
			if (project.getLocation().equals(projectPath)) {
				project.open(getMonitor());
				return false;
			}
			throw new BuildException("Project with same name already exists in workspace!"); //$NON-NLS-1$
		}
		if (!Status.OK_STATUS.equals(project.getWorkspace().validateProjectLocation(project, projectPath))) {
			throw new BuildException(MessageFormat.format("Project location {0} is not valid in workspace!", //$NON-NLS-1$
					projectPath.toPortableString()));
		}
		return true;
	}

	private static void setProjectLocation(final IProjectDescription description, final IPath projectPath) {
		final IPath projectPathInWorkspace = ResourcesPlugin.getWorkspace().getRoot().getLocation()
				.append(projectPath.lastSegment());
		if (!projectPathInWorkspace.equals(projectPath)) {
			// if projectPath is not in workspace root we need to set the location for
			// correct loading
			description.setLocation(projectPath);
		}
	}

	private void loadProject(final IProjectDescription description, final IProject project, final IPath projectPath)
			throws CoreException {
		final IProgressMonitor monitor = getMonitor();
		setProjectLocation(description, projectPath);
		project.create(description, monitor);
		project.open(monitor);
	}

	private static void check4diacProject(final IProject project) throws BuildException {
		try {
			if (!project.hasNature(SystemManager.FORDIAC_PROJECT_NATURE_ID)) {
				throw new BuildException(
						MessageFormat.format("Project {0} is not a 4diac IDE project!", project.getName())); //$NON-NLS-1$
			}
		} catch (final CoreException e) {
			throw new BuildException(e);
		}
	}

	private IProgressMonitor getMonitor() {
		IProgressMonitor monitor = null;
		final var project = getProject();
		if (project != null) {
			final var references = project.getReferences();
			if (references != null) {
				monitor = (IProgressMonitor) references.get(AntCorePlugin.ECLIPSE_PROGRESS_MONITOR);
			}
		}
		return monitor;
	}

	public static void waitBuilderJobsComplete() {
		waitJobsComplete(job -> !(job.getName().equals(org.eclipse.core.internal.utils.Messages.utils_stringJobName)
				|| job.getName().equals(org.eclipse.core.internal.utils.Messages.resources_snapshot)
				|| job.getName().startsWith(org.eclipse.fordiac.ide.library.Messages.LibraryManager_DownloadJobName
						.split("\\{\\d+\\}")[0]))); //$NON-NLS-1$
	}

	public static void runFullBuild(final IProject project) {
		try {
			project.build(IncrementalProjectBuilder.FULL_BUILD, null);
		} catch (final CoreException e) {
			throw new BuildException(e);
		}
	}

	public static void waitJobsComplete(final Predicate<Job> isValidJob) {

		// Minimum wait time is needed to ensure that jobs are started by the
		// ResourceChangeListener

		final int MINIUM_WAIT_TIME_MS = 1000;
		final int SLEEP_TIME_MS = 50;

		final int ADDITIONAL_CYCLES = (MINIUM_WAIT_TIME_MS + 1 + (SLEEP_TIME_MS / 2)) / SLEEP_TIME_MS;

		int allJobsDone = ADDITIONAL_CYCLES;
		do {
			// wait for all jobs to be done
			final Job[] jobs = Job.getJobManager().find(null); // get all current scheduled jobs
			boolean unfinishedJobs = false;
			for (final var job : jobs) {
				if (isValidJob.test(job)) {
					if (job.getResult() == null) {
						unfinishedJobs = true;
						allJobsDone = ADDITIONAL_CYCLES; // reset allJobsDone if a Job has been added during waiting
					}
					join(job, SLEEP_TIME_MS);
				}
			}
			// if there are no jobs in the list we wait some additional cycles
			// if new jobs show up we wait for them to finish as well
			if (!unfinishedJobs) {
				allJobsDone--;
				sleep(SLEEP_TIME_MS);
			}
		} while (allJobsDone > 0);

	}

	private static void sleep(final int SLEEP_TIME_NS) {
		try {
			Thread.sleep(SLEEP_TIME_NS);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private static void join(final Job job, final int SLEEP_TIME_NS) {
		try {
			job.join(SLEEP_TIME_NS, null);
		} catch (final OperationCanceledException e) {
			throw new BuildException(e);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

}
