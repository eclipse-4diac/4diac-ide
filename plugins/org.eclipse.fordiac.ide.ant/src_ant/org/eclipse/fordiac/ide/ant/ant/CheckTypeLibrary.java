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
package org.eclipse.fordiac.ide.ant.ant;

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

public class CheckTypeLibrary extends Task {

	private String projectNameString;

	public void setProjectName(final String value) {
		projectNameString = value;
	}


	@Override
	public void execute() throws BuildException {

		log("=======================================================");//$NON-NLS-1$
		log("                CHECK TYPE LIBRARY TASK                ");//$NON-NLS-1$
		log("=======================================================");//$NON-NLS-1$

		if (projectNameString == null) {
			throw new BuildException("Project name not specified!"); //$NON-NLS-1$
		}

		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IProject project = workspace.getRoot().getProject(projectNameString);
		if (project == null) {
			throw new BuildException("Project named '" + projectNameString + "' not in workspace in Workspace");//$NON-NLS-1$ //$NON-NLS-2$
		}

		ValidateProject.clear(project);
		// before loading all types we need to wait for all builder jobs so that the xtext builders have resolved all
		// dependencies
		waitBuilderJobsComplete();
		ValidateProject.checkTypeLibraryInProjects(project);
		ValidateProject.checkSTInProjects(project);

		waitMarkerJobsComplete();

		// log Markers, only visible in console output
		try {
			final IMarker[] markers = project.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);

			printMarkers(markers, this);

			if (project.findMaxProblemSeverity(IMarker.PROBLEM, true,
					IResource.DEPTH_INFINITE) == IMarker.SEVERITY_ERROR) {
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
		String markerString = ""; //$NON-NLS-1$
		if (marker != null) {
			if (marker.getAttribute(IMarker.SEVERITY) != null) {
				switch ((int) marker.getAttribute(IMarker.SEVERITY)) {
				case IMarker.SEVERITY_INFO:
					markerString += "INFO: "; //$NON-NLS-1$
					break;
				case IMarker.SEVERITY_WARNING:
					markerString += "WARNING: "; //$NON-NLS-1$
					break;
				case IMarker.SEVERITY_ERROR:
					markerString += "ERROR: "; //$NON-NLS-1$
					break;
				default:
					markerString += "PROBLEM :";//$NON-NLS-1$
					break;
				}
			} else {
				markerString += "PROBLEM :";//$NON-NLS-1$
			}

			if (marker.getAttribute(IMarker.MESSAGE) != null) {
				markerString += marker.getAttribute(IMarker.MESSAGE).toString() + " | "; //$NON-NLS-1$
			} else {
				markerString += "NO ERROR MESSAGE | ";//$NON-NLS-1$
			}

			if (marker.getResource().getLocation() != null) {
				markerString += marker.getResource().getLocation().lastSegment() + " : "; //$NON-NLS-1$
			} else {
				markerString += "NO PATH : "; //$NON-NLS-1$
			}

			if (marker.getAttribute(IMarker.LINE_NUMBER) != null) {
				markerString += marker.getAttribute(IMarker.LINE_NUMBER).toString();
			} else {
				markerString += "NO LINE NUMBER"; //$NON-NLS-1$
			}
		}
		return markerString;
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

	public static void waitBuilderJobsComplete() {
		Job[] jobs = Job.getJobManager().find(null); // get all current scheduled jobs

		while (buildJobExists(jobs)) {
			try {
				Thread.sleep(50);
			} catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			jobs = Job.getJobManager().find(null); // update the job list
		}
	}

	private static boolean buildJobExists(final Job[] jobs) {
		final Optional<Job> findAny = Arrays.stream(jobs)
				.filter(j -> (j.getState() != Job.NONE && j.getName().startsWith("Building"))) //$NON-NLS-1$
				.findAny();
		return findAny.isPresent();
	}

}
