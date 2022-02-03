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

package org.eclipse.fordiac.ide.systemmanagement.util;

import java.io.File;
import java.text.MessageFormat;

import org.apache.tools.ant.BuildException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;

public class ProjectImport {

	public static void check4diacProject(final IProject project) throws BuildException {
		try {
			if (!project.hasNature(SystemManager.FORDIAC_PROJECT_NATURE_ID)) {
				throw new BuildException(
						MessageFormat.format("Project {0} is not a 4diac IDE project!", project.getName())); //$NON-NLS-1$
			}
		} catch (final CoreException e) {
			throw new BuildException(e);
		}
	}
	
	public static void setProjectLocation(final IProjectDescription description, final IPath projectPath) {
		final IPath projectPathInWorkspace = ResourcesPlugin.getWorkspace().getRoot().getLocation()
				.append(projectPath.lastSegment());
		if (!projectPathInWorkspace.equals(projectPath)) {
			// if projectPath is not in workspace root we need to set the location for correct loading
			description.setLocation(projectPath);
		}
	}

	public static void loadProject(final IProjectDescription description, final IProject project, final IPath projectPath, final IProgressMonitor monitor)
			throws CoreException {
		setProjectLocation(description, projectPath);
		project.create(description, monitor);
		project.open(monitor);
	}
	

	public static IProjectDescription loadProjectDescription(final IWorkspace workspace, final IPath projectPath)
			throws CoreException {
		final IPath projectFilePath = projectPath.append(File.separator + ".project"); //$NON-NLS-1$
		return workspace.loadProjectDescription(projectFilePath);
	}
	
	private ProjectImport() {
		throw new UnsupportedOperationException("This helper class should not be instantiated");
	}
}
