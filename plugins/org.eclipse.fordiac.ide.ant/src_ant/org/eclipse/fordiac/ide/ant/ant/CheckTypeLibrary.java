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

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.ProjectComponent;
import org.apache.tools.ant.Task;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;

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

		// log Markers, only visible in console output
		try {
			final var markers = Arrays.asList(project.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE))
					.stream().filter(m -> !TypeLibraryTags.SYSTEM_TYPE_FILE_ENDING
							.equalsIgnoreCase(m.getResource().getLocation().getFileExtension()))
					.toList();

			printMarkers(markers, this);

			if (project.findMaxProblemSeverity(IMarker.PROBLEM, true,
					IResource.DEPTH_INFINITE) == IMarker.SEVERITY_ERROR) {
				throw new BuildException(
						String.format("%d problems found in loaded project %s", Integer.valueOf(markers.size()), //$NON-NLS-1$
								projectNameString));
			}
		} catch (final CoreException e) {
			throw new BuildException("Cannot get markers", e);//$NON-NLS-1$
		}
	}

	public static void printMarkers(final Iterable<IMarker> markers, final ProjectComponent loggingTask)
			throws CoreException {
		for (final IMarker marker : markers) {
			loggingTask.log(markerToLogString(marker));
		}
	}

	private static String markerToLogString(final IMarker marker) throws CoreException {
		String markerString = ""; //$NON-NLS-1$
		if (marker != null) {
			if (marker.getAttribute(IMarker.SEVERITY) instanceof final Integer i) {
				switch (i.intValue()) {
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
					markerString += "PROBLEM: ";//$NON-NLS-1$
					break;
				}
			} else {
				markerString += "PROBLEM: ";//$NON-NLS-1$
			}

			markerString += marker.getAttribute(IMarker.MESSAGE, "NO ERROR MESSAGE") + " | "; //$NON-NLS-1$//$NON-NLS-2$

			if (marker.getResource().getLocation() != null) {
				markerString += marker.getResource().getLocation().lastSegment() + " : "; //$NON-NLS-1$
			} else {
				markerString += "NO PATH : "; //$NON-NLS-1$
			}

			if (marker.getAttribute(IMarker.LINE_NUMBER) instanceof final Integer lineNumber) {
				markerString += lineNumber.toString();
			} else {
				markerString += "NO LINE NUMBER"; //$NON-NLS-1$
			}

		}
		return markerString;
	}

}
