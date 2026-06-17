/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sophie Öttl - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.systemmanagement;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class ProjectCreator {

	private final String projectName;
	private final IPath projectLocation;
	private IProject projectToCreate;

	public ProjectCreator(final String projectName, final IPath projectLocation) {
		this.projectName = projectName;
		this.projectLocation = projectLocation;
	}

	/**
	 * Creates a new project in the workspace.
	 *
	 * @param monitor the monitor
	 */
	public IProject createProject(final IProgressMonitor monitor) {
		try {
			projectToCreate = SystemManager.INSTANCE.createNew4diacProject(projectName, projectLocation, monitor);
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		} finally {
			monitor.done();
		}
		return projectToCreate;

	}
}
