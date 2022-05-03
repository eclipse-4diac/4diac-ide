/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ant;

import java.util.Arrays;
import java.util.Optional;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.fordiac.ide.systemmanagement.ValidateProject;

public class CheckProject extends Task {

	private String projectNameString;

	public void setProjectName(final String value) {
		projectNameString = value;
	}


	@Override
	public void execute() throws BuildException {

		log("=======================================================");//$NON-NLS-1$
		log("                   CHECK PROJECT TASK                  ");//$NON-NLS-1$
		log("=======================================================");//$NON-NLS-1$

		if (projectNameString == null) {
			throw new BuildException("Project name not specified!"); //$NON-NLS-1$
		}

		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IProject project = workspace.getRoot().getProject(projectNameString);
		if (project == null) {
			throw new BuildException("Project named '" + projectNameString + "' not in workspace in Workspace");//$NON-NLS-1$ //$NON-NLS-2$
		}


		final IProject[] projectList = new IProject[] { project };
		ValidateProject.clear(projectList);
		ValidateProject.checkTypeLibraryInProjects(projectList);
		ValidateProject.checkSTInProjects(projectList);

		waitMarkerJobsComplete();

		// log Markers, only visible in console output
		try {
			final IMarker[] markers = project.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
			printMarkers(markers, this);
			if (markers.length > 0) {
				throw new BuildException(
						String.format("%d problems found in loaded project %s", markers.length, projectNameString)); //$NON-NLS-1$
			}
		} catch (final CoreException e) {
			throw new BuildException("Could not create error marker: " + e.getMessage());//$NON-NLS-1$
		}
	}


	public static void printMarkers(final IMarker[] markers, final Task task) throws CoreException {
		for (final IMarker marker : markers) {
			task.log(markerToLogString(marker));
		}
	}

	private static String markerToLogString(final IMarker marker) throws CoreException {
		return "PROBLEM: " + marker.getAttribute("message").toString() + " | " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ marker.getResource().getLocation().lastSegment() + " : " //$NON-NLS-1$
				+ marker.getAttribute("lineNumber").toString();
	}

	public static void waitMarkerJobsComplete() {
		Job[] jobs = Job.getJobManager().find(null); // get all current scheduled jobs

		while (markerJobExists(jobs)) {
			try {
				Thread.sleep(50);
			} catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			jobs = Job.getJobManager().find(null); // update the job list
		}
	}

	private static boolean markerJobExists(final Job[] jobs) {
		final Optional<Job> findAny = Arrays.stream(jobs)
				.filter(j -> (j.getState() != Job.NONE && j.getName().startsWith("Add error marker to file"))) //$NON-NLS-1$
				.findAny();
		return findAny.isPresent();
	}

}
