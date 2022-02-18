
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
 *   Lukas Wais - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.projectloader;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;

public class EclipseProjectLoader {
	private final IWorkspace workspace;
	private final IWorkspaceRoot myWorkspaceRoot;
	private final IProjectDescription description;
	private final IProject eclipseProject;
	private final String projectname;

	public EclipseProjectLoader(final String projectname) throws CoreException {
		this.projectname = projectname;
		workspace = ResourcesPlugin.getWorkspace();
		myWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		description = loadProjectDescription(workspace, myWorkspaceRoot.getLocation().append(projectname));
		eclipseProject = myWorkspaceRoot.getProject(description.getName());
		eclipseProject.open(new NullProgressMonitor());
	}

	public IProject getEclipseProject() {
		return eclipseProject;
	}

	public String getProjectname() {
		return projectname;
	}

	private static IProjectDescription loadProjectDescription(final IWorkspace workspace, final IPath projectPath) {
		try {
			final IPath projectFilePath = projectPath.append(File.separator + ".project"); //$NON-NLS-1$
			return workspace.loadProjectDescription(projectFilePath);
		} catch (final CoreException e) {
			System.err.println("CoreException: " + e.getMessage()); //$NON-NLS-1$
		}
		return null;
	}
}
