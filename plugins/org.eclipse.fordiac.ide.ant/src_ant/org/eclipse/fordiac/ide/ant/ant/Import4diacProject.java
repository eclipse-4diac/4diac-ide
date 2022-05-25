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

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.eclipse.ant.core.AntCorePlugin;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
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
			final IPath projectPath = getProjectPath();
			final IProjectDescription description = loadProjectDescription(workspace, projectPath);
			final IProject project = workspace.getRoot().getProject(description.getName());

			if (validateProjectLocation(project, projectPath)) {
				// the project is not already loaded in the workspace
				loadProject(description, project, projectPath);
			}
			check4diacProject(project);
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
		} else if (!Status.OK_STATUS.equals(project.getWorkspace().validateProjectLocation(project, projectPath))) {
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
		final var references = getProject().getReferences();
		if (references != null) {
			monitor = (IProgressMonitor) references.get(AntCorePlugin.ECLIPSE_PROGRESS_MONITOR);
		}
		return monitor;
	}

}
