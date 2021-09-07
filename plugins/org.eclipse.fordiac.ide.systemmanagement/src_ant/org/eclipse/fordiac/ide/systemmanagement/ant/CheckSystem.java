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
package org.eclipse.fordiac.ide.systemmanagement.ant;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Optional;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;

public class CheckSystem extends Task {

	private String systemPathString;

	public void setSystemPath(final String value) {
		systemPathString = value;
	}

	@Override
	public void execute() throws BuildException {
		if (systemPathString == null) {
			throw new BuildException("System path not specified!"); //$NON-NLS-1$
		}

		final IFile systemFile = getSystemFile();
		if (systemFile == null || !systemFile.exists() || !SystemManager.isSystemFile(systemFile)) {
			throw new BuildException(
					MessageFormat.format("System path {0} does not is not a valid system in workspace!", //$NON-NLS-1$
							systemPathString));
		}

		// load the system to get the error markers is place
		SystemManager.INSTANCE.getSystem(systemFile);
		waitMarkerJobsComplete();

		try {
			IMarker[] markers = systemFile.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_ZERO);
			if (markers.length != 0) {
				throw new BuildException(
						MessageFormat.format("The system {0} has errors or warnings!", systemPathString)); //$NON-NLS-1$
			}
			markers = systemFile.getProject().findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
			if (markers.length != 0) {
				throw new BuildException(MessageFormat
						.format("One of the types used by system {0} has errors or warnings!", systemPathString)); //$NON-NLS-1$
			}
		} catch (final CoreException e) {
			throw new BuildException("Cannot get markers", e); //$NON-NLS-1$
		}

	}

	private IFile getSystemFile() {
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		return workspace.getRoot().getFile(new Path(systemPathString));
	}

	private static void waitMarkerJobsComplete() {
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
