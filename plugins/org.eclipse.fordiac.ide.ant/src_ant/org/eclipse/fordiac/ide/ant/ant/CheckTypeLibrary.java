/*******************************************************************************
 * Copyright (c) 2022, 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi - initial implementation and/or documentation
 *   Ernst Blecha - refactoring
 *******************************************************************************/
package org.eclipse.fordiac.ide.ant.ant;

import java.util.Arrays;
import java.util.Optional;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.ProjectComponent;
import org.apache.tools.ant.Task;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;

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
			throw new BuildException("Project named '" + projectNameString + "' not in workspace");//$NON-NLS-1$ //$NON-NLS-2$
		}

		// log Markers, only visible in console output
		try {
			final var markers = Arrays.asList(project.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE))
					.stream().filter(m -> !SystemManager.isSystemFile(m.getResource().getLocation())).toList();

			printMarkers(markers, this);

			if (markers.stream().anyMatch(m -> getIntegerAttribute(m, IMarker.SEVERITY)
					.filter(s -> s.intValue() == IMarker.SEVERITY_ERROR).isPresent())) {
				throw new BuildException(String.format("The project %s has %d errors or warnings!", projectNameString, //$NON-NLS-1$
						Integer.valueOf(markers.size())));
			}
		} catch (final CoreException e) {
			throw new BuildException("Cannot get markers", e); //$NON-NLS-1$
		}

	}

	public static void printMarkers(final Iterable<IMarker> markers, final ProjectComponent loggingTask) {
		markers.forEach(marker -> Optional.ofNullable(marker).ifPresent(m -> loggingTask.log(markerToLogString(m))));
	}

	private static String markerToLogString(final IMarker marker) { //@formatter:off
		return getIntegerAttribute(marker, IMarker.SEVERITY).map(CheckTypeLibrary::convertToSeverity)
						.orElse("PROBLEM: ") + //$NON-NLS-1$
				marker.getAttribute(IMarker.MESSAGE,
						"NO ERROR MESSAGE") + //$NON-NLS-1$
				" | " + //$NON-NLS-1$
				Optional.ofNullable(marker.getResource().getLocation()).map(IPath::lastSegment)
						.orElse("NO PATH") + //$NON-NLS-1$
				" : " + //$NON-NLS-1$
				getIntegerAttribute(marker, IMarker.LINE_NUMBER).map(i -> i.toString())
						.orElse("NO LINE NUMBER"); //$NON-NLS-1$
	} //@formatter:on

	private static String convertToSeverity(final Integer severity) {
		return switch (severity.intValue()) {
		case IMarker.SEVERITY_INFO -> "INFO: "; //$NON-NLS-1$
		case IMarker.SEVERITY_WARNING -> "WARNING: "; //$NON-NLS-1$
		case IMarker.SEVERITY_ERROR -> "ERROR: "; //$NON-NLS-1$
		default -> null;
		};
	}

	private static Optional<Integer> getIntegerAttribute(final IMarker marker, final String attributeName) {
		try {
			if (marker.getAttribute(attributeName) instanceof final Integer i) {
				return Optional.of(i);
			}
		} catch (final CoreException e) {
			// The exception tells us that this attribute does not exist, return empty
		}
		return Optional.empty();
	}

}
