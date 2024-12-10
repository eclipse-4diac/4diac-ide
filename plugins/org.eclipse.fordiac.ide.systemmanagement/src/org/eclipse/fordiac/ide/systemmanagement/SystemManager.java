/*******************************************************************************
 * Copyright (c) 2008, 2023 Profactor GmbH, TU Wien ACIN, AIT, fortiss GmbH,
 * 		            Johannes Kepler University Linz
 *                          Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
 *
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
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Refactored class hierarchy of xml exporters
 *               - New Project Explorer layout
 *               - Added support for project renameing
 *   Martin Jobst
 *     - add Xtext nature and builder
 *     - migrate system handling to typelib
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.library.model.library.Required;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.model.dataimport.SystemImporter;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.typelibrary.SystemEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.systemmanagement.changelistener.DistributedSystemListener;
import org.eclipse.fordiac.ide.systemmanagement.changelistener.FordiacResourceChangeListener;
import org.eclipse.fordiac.ide.systemmanagement.util.SystemPaletteManagement;
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
	public static final String ROBOT_PROJECT_NATURE_ID = "org.robotframework.ide.eclipse.main.plugin.robotNature"; //$NON-NLS-1$
	public static final String OLD_DISTRIBUTED_PROJECT_NATURE_ID = "org.fordiac.systemManagement.DistributedNature"; //$NON-NLS-1$

	public static final String SYSTEM_FILE_ENDING = "sys"; //$NON-NLS-1$
	public static final String SYSTEM_FILE_ENDING_WITH_DOT = ".sys"; //$NON-NLS-1$

	public static final String TYPE_LIB_FOLDER_NAME = "Type Library"; //$NON-NLS-1$
	private final IResourceChangeListener fordiacListener = new FordiacResourceChangeListener(this);

	/** The listeners. */
	private final List<DistributedSystemListener> listeners = new ArrayList<>();

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
			final Map<Required, URI> includedLibraries, final IProgressMonitor monitor) throws CoreException {
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		final IProject project = root.getProject(projectName);
		final IProjectDescription description = ResourcesPlugin.getWorkspace().newProjectDescription(project.getName());

		final Map<String, URI> includes = new HashMap<>();
		includedLibraries.forEach((key, value) -> includes.put(key.getSymbolicName(), value));

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

		// configure type lib
		SystemPaletteManagement.linkToolTypeLibsToDestination(includes, project.getFolder(TYPE_LIB_FOLDER_NAME));

		TypeLibraryManager.INSTANCE.getTypeLibrary(project); // insert the project into the project list

		ManifestHelper.createProjectManifest(project, includedLibraries.keySet());

		return project;
	}

	@SuppressWarnings("static-method")
	public synchronized AutomationSystem createNewSystem(final IContainer location, final String name,
			final IProgressMonitor monitor) throws CoreException {
		final IFile systemFile = location.getFile(new Path(name + SystemManager.SYSTEM_FILE_ENDING_WITH_DOT));
		final TypeLibrary typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibrary(systemFile.getProject());
		final AutomationSystem system = SystemImporter.createAutomationSystem();
		system.setName(name);
		typeLibrary.createTypeEntry(systemFile).save(system, monitor);
		return system;
	}

	/**
	 * Load system.
	 *
	 *
	 * systemFile xml file for the system
	 *
	 * @return the system entry
	 */
	private static SystemEntry initSystem(final IFile systemFile) {
		if (systemFile.exists()) {
			return (SystemEntry) TypeLibraryManager.INSTANCE.getTypeLibrary(systemFile.getProject())
					.createTypeEntry(systemFile);
		}
		return null;
	}

	@SuppressWarnings("static-method")
	public synchronized AutomationSystem getSystem(final IFile systemFile) {
		final TypeLibrary typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibrary(systemFile.getProject());
		SystemEntry sysEntry = (SystemEntry) typeLibrary.getTypeEntry(systemFile);
		if (sysEntry == null) {
			sysEntry = initSystem(systemFile);
		}
		return sysEntry != null ? sysEntry.getSystem() : null;
	}

	@SuppressWarnings("static-method")
	public synchronized List<AutomationSystem> getProjectSystems(final IProject project) {
		return TypeLibraryManager.INSTANCE.getTypeLibrary(project).getSystems().stream().map(SystemEntry::getSystem)
				.toList();
	}

	private static String[] getNatureIDs() {
		return new String[] { SystemManager.FORDIAC_PROJECT_NATURE_ID, XtextProjectHelper.NATURE_ID };
	}

	private static String[] getBuilderIDs() {
		return new String[] { XtextProjectHelper.BUILDER_ID, FORDIAC_EXPORT_BUILDER_ID };
	}

	/** Notify listeners. */
	public void notifyListeners() {
		listeners.forEach(DistributedSystemListener::distributedSystemWorkspaceChanged);
	}

	/**
	 * Adds the workspace listener.
	 *
	 * @param listener the listener
	 */
	public void addWorkspaceListener(final DistributedSystemListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeFordiacChangeListener() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(fordiacListener);
	}

	public void addFordiacChangeListener() {
		ResourcesPlugin.getWorkspace().addResourceChangeListener(fordiacListener);
	}

}
