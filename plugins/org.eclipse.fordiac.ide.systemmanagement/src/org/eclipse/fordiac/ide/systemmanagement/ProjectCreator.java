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

import java.util.List;
import java.util.stream.Stream;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.xtext.ui.XtextProjectHelper;

public class ProjectCreator {

	private final String projectName;
	private final IPath projectLocation;

	private ProjectCreator(final String projectName, final IPath projectLocation) {
		this.projectName = projectName;
		this.projectLocation = projectLocation;
	}

	public static ProjectCreator of(final String projectName, final IPath projectLocation) {
		return new ProjectCreator(projectName, projectLocation);
	}

	/**
	 * Creates a new project in the workspace.
	 *
	 * @param monitor the monitor
	 */
	public IProject create(final IProgressMonitor monitor) {
		try {
			return createNew4diacProject(monitor);
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		} finally {
			monitor.done();
		}
		return null;

	}

	private IProject createNew4diacProject(final IProgressMonitor monitor) throws CoreException {
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		final IProject project = root.getProject(projectName);
		final IProjectDescription description = ResourcesPlugin.getWorkspace().newProjectDescription(project.getName());

		if (!Platform.getLocation().equals(projectLocation)) {
			description.setLocation(projectLocation);
		}

		description.setNatureIds(SystemManager.getNatureIDs());

		final List<ICommand> commands = Stream.of(getBuilderIDs()).map(builder -> {
			final ICommand command = description.newCommand();
			command.setBuilderName(builder);
			return command;
		}).toList();
		description.setBuildSpec(commands.toArray(new ICommand[commands.size()]));

		project.create(description, monitor);
		project.open(monitor);

		project.getFolder(TypeLibraryTags.TYPE_LIB_FOLDER_NAME).create(true, true, monitor);
		project.getFolder(TypeLibraryTags.STANDARD_LIB_FOLDER_NAME).create(IResource.VIRTUAL | IResource.FORCE, true,
				monitor);
		project.getFolder(TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME).create(IResource.VIRTUAL | IResource.FORCE, true,
				monitor);

		ManifestHelper.getOrCreateProjectManifest(project);

		project.refreshLocal(IResource.DEPTH_ONE, monitor);
		return project;
	}

	private static String[] getBuilderIDs() {
		return new String[] { SystemManager.FORDIAC_LIBRARY_BUILDER_ID, XtextProjectHelper.BUILDER_ID,
				SystemManager.FORDIAC_OCL_VALIDATION_BUILDER_ID, SystemManager.FORDIAC_EXPORT_BUILDER_ID };
	}
}
