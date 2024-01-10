/*******************************************************************************
 * Copyright (c) 2021, 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Lukas Wais, Alois Zoitl, Bianca Wiesmayr
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.test.model;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.osgi.framework.Bundle;

public class EclipseProjectLoader {
	private final IProject eclipseProject;

	public EclipseProjectLoader(final Bundle bundle, final Path bundleRelativeProjectPath) throws CoreException, IOException  {
		eclipseProject = openProject(bundle, bundleRelativeProjectPath);
	}

	public IProject getEclipseProject() {
		return eclipseProject;
	}

	public String getProjectname() {
		return eclipseProject.getName();
	}

	private static IProjectDescription loadProjectDescription(final IWorkspace workspace, final IPath projectPath) {
		try {
			final IPath projectFilePath = projectPath.append(File.separator + ".project"); //$NON-NLS-1$
			return workspace.loadProjectDescription(projectFilePath);
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
		return null;
	}

	private static IProject openProject(final Bundle bundle, final Path projectPath) throws IOException, CoreException  {
		final var workspace = ResourcesPlugin.getWorkspace();
		final var myWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		final var url = FileLocator.toFileURL(FileLocator.find(bundle, projectPath));
		final var path = Path.fromOSString(url.getPath());
		final var description = loadProjectDescription(workspace,
				path);
		if (description != null) {
			final var eclipseProject = myWorkspaceRoot.getProject(description.getName());
			if (validateProjectLocation(eclipseProject, path)) {
				// the project is not already loaded in the workspace
				loadProject(description, eclipseProject, path);
			}
			return eclipseProject;
		}
		return null;
	}

	private static void loadProject(final IProjectDescription description, final IProject project, final IPath projectPath)
			throws CoreException {
		final IProgressMonitor monitor = new NullProgressMonitor();
		setProjectLocation(description, projectPath);
		project.create(description, monitor);
		project.open(monitor);
	}

	private static void setProjectLocation(final IProjectDescription description, final IPath projectPath) {
		final IPath projectPathInWorkspace = ResourcesPlugin.getWorkspace().getRoot().getLocation()
				.append(projectPath.lastSegment());
		if (!projectPathInWorkspace.equals(projectPath)) {
			// if projectPath is not in workspace root we need to set the location for correct loading
			description.setLocation(projectPath);
		}
	}

	private static boolean validateProjectLocation(final IProject project, final IPath projectPath) throws IOException, CoreException {
		if (project.exists()) {
			// if the project already exists we can only import it if it has the same location
			if (project.getLocation().equals(projectPath)) {
				project.open(new NullProgressMonitor());
				return false;
			}
			throw new IOException("Project with same name already exists in workspace!"); //$NON-NLS-1$
		} else if (!Status.OK_STATUS.equals(project.getWorkspace().validateProjectLocation(project, projectPath))) {
			throw new IOException(MessageFormat.format("Project location {0} is not valid in workspace!", //$NON-NLS-1$
					projectPath.toPortableString()));
		}
		return true;
	}

}
