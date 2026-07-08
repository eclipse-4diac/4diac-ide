/*******************************************************************************
 * Copyright (c) 2008, 2026 Profactor GmbH, TU Wien ACIN, AIT, fortiss GmbH,
 *                          Johannes Kepler University Linz,
 *                          Primetals Technologies Austria GmbH,
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Matthias Plasch, Filip Andren,
 *   Waldemar Eisenmenger, Martin Melik Merkumians
 *                - initial API and implementation and/or initial documentation
 *   Alois Zoitl  - Refactored class hierarchy of xml exporters
 *                - New Project Explorer layout
 *                - Added support for project renameing
 *   Martin Jobst - add Xtext nature and builder
 *                - migrate system handling to typelib
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.systemmanagement.changelistener.FordiacResourceChangeListener;
import org.eclipse.fordiac.ide.systemmanagement.nature.FordiacNature;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.xtext.ui.XtextProjectHelper;

/**
 * The Class SystemManager.
 *
 * @author gebenh
 */
public enum SystemManager {

	INSTANCE;

	public static final String FORDIAC_PROJECT_NATURE_ID = "org.eclipse.fordiac.ide.systemmanagement.FordiacNature"; //$NON-NLS-1$
	public static final String FORDIAC_EXPORT_BUILDER_ID = "org.eclipse.fordiac.ide.export.builder"; //$NON-NLS-1$
	public static final String FORDIAC_LIBRARY_BUILDER_ID = "org.eclipse.fordiac.ide.library.builder"; //$NON-NLS-1$
	public static final String FORDIAC_OCL_VALIDATION_BUILDER_ID = "org.eclipse.fordiac.ide.validation.oclbuilder"; //$NON-NLS-1$
	public static final String ROBOT_PROJECT_NATURE_ID = "org.robotframework.ide.eclipse.main.plugin.robotNature"; //$NON-NLS-1$
	public static final String OLD_DISTRIBUTED_PROJECT_NATURE_ID = "org.fordiac.systemManagement.DistributedNature"; //$NON-NLS-1$

	public static final String SYSTEM_FILE_ENDING = "sys"; //$NON-NLS-1$
	public static final String SYSTEM_FILE_ENDING_WITH_DOT = ".sys"; //$NON-NLS-1$

	private final IResourceChangeListener fordiacListener = new FordiacResourceChangeListener();

	/** The listeners. */

	/** Instantiates a new system manager. */
	SystemManager() {
		addFordiacChangeListener();
	}

	public static boolean isSystemFile(final Object entry) {
		return (entry instanceof final IPath path
				&& SystemManager.SYSTEM_FILE_ENDING.equalsIgnoreCase((path).getFileExtension()))
				|| (entry instanceof final IFile file
						&& SystemManager.SYSTEM_FILE_ENDING.equalsIgnoreCase((file).getFileExtension()));
	}

	@SuppressWarnings("static-method")
	public IProject createNew4diacProject(final String projectName, final IPath location,
			final IProgressMonitor monitor) throws CoreException {
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		final IProject project = root.getProject(projectName);
		final IProjectDescription description = ResourcesPlugin.getWorkspace().newProjectDescription(project.getName());

		if (!Platform.getLocation().equals(location)) {
			description.setLocation(location);
		}

		description.setNatureIds(getNatureIDs());

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

	public static String[] getNatureIDs() {
		return new String[] { SystemManager.FORDIAC_PROJECT_NATURE_ID, XtextProjectHelper.NATURE_ID };
	}

	private static String[] getBuilderIDs() {
		return new String[] { FORDIAC_LIBRARY_BUILDER_ID, XtextProjectHelper.BUILDER_ID,
				FORDIAC_OCL_VALIDATION_BUILDER_ID, FORDIAC_EXPORT_BUILDER_ID };
	}

	public void removeFordiacChangeListener() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(fordiacListener);
	}

	public void addFordiacChangeListener() {
		ResourcesPlugin.getWorkspace().addResourceChangeListener(fordiacListener);
	}

	public static boolean hasFordiacProjectNature(final IProject project) {
		try {
			return project != null && project.isAccessible()
					&& project.hasNature(SystemManager.FORDIAC_PROJECT_NATURE_ID);
		} catch (final CoreException e) {
			FordiacLogHelper.logError(
					MessageFormat.format(Messages.FordiacSystemManagement_ErrorLoadingProjectNature, e.getMessage()),
					e);
		}
		return false;
	}

	public static void validateProjectNature(final IProject project) {
		try {
			if (project != null && project.isAccessible()) {
				final var nature = project.getNature(SystemManager.FORDIAC_PROJECT_NATURE_ID);
				if (nature instanceof final FordiacNature fordiacNature) {
					fordiacNature.validate();
				}
			}
		} catch (final CoreException e) {
			FordiacLogHelper.logError(
					MessageFormat.format(Messages.FordiacSystemManagement_ErrorLoadingProjectNature, e.getMessage()),
					e);
		}
	}

}
