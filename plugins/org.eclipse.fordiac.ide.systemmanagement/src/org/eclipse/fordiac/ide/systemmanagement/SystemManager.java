/*******************************************************************************
 * Copyright (c) 2008, 2021 Profactor GmbH, TU Wien ACIN, AIT, fortiss GmbH,
 * 		            Johannes Kepler University Linz
 *                          Primetals Technologies Austria GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.dataexport.SystemExporter;
import org.eclipse.fordiac.ide.model.dataimport.SystemImporter;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.typelibrary.SystemEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.systemmanagement.changelistener.DistributedSystemListener;
import org.eclipse.fordiac.ide.systemmanagement.changelistener.FordiacResourceChangeListener;
import org.eclipse.fordiac.ide.systemmanagement.extension.ITagProvider;
import org.eclipse.fordiac.ide.systemmanagement.util.SystemPaletteManagement;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.xtext.ui.XtextProjectHelper;

/** The Class SystemManager.
 *
 * @author gebenh */
public enum SystemManager {

	INSTANCE;

	private static final String PLUGIN_ID = "org.eclipse.fordiac.ide.systemmanagement"; //$NON-NLS-1$

	public static final String FORDIAC_PROJECT_NATURE_ID = "org.eclipse.fordiac.ide.systemmanagement.FordiacNature"; //$NON-NLS-1$
	public static final String ROBOT_PROJECT_NATURE_ID = "org.robotframework.ide.eclipse.main.plugin.robotNature"; //$NON-NLS-1$
	public static final String OLD_DISTRIBUTED_PROJECT_NATURE_ID = "org.fordiac.systemManagement.DistributedNature"; //$NON-NLS-1$

	public static final String SYSTEM_FILE_ENDING = "sys"; //$NON-NLS-1$
	public static final String SYSTEM_FILE_ENDING_WITH_DOT = ".sys"; //$NON-NLS-1$

	public static final String TYPE_LIB_FOLDER_NAME = "Type Library"; //$NON-NLS-1$

	/** The model systems. */
	private final Map<IProject, Map<IFile, SystemEntry>> allSystemsInWS = new HashMap<>();

	private final Map<IProject, ArrayList<ITagProvider>> tagProviders = new HashMap<>();

	/** The listeners. */
	private final List<DistributedSystemListener> listeners = new ArrayList<>();

	/** Instantiates a new system manager. */
	SystemManager() {
		try {
			// ensure dirty workspaces are cleaned before any type library is loaded
			ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
		CoordinateConverter.INSTANCE.name();
		// Correctly setup the tool library needs to be done before loading any systems
		// and adding the resource change listener
		TypeLibraryManager.INSTANCE.loadToolLibrary();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(new FordiacResourceChangeListener(this));
		ValidateProject.checkTypeLibraryInProjectsInWorkspaceJob();
	}

	public static boolean isSystemFile(final Object entry) {
		return ((entry instanceof IFile)
				&& SystemManager.SYSTEM_FILE_ENDING.equalsIgnoreCase(((IFile) entry).getFileExtension()));
	}

	public IProject createNew4diacProject(final String projectName, final IPath location,
			final boolean importDefaultTypeLibrary, final IProgressMonitor monitor) throws CoreException {
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
		}).collect(Collectors.toList());
		description.setBuildSpec(commands.toArray(new ICommand[commands.size()]));

		project.create(description, monitor);
		project.open(monitor);

		// configure type lib
		if (importDefaultTypeLibrary) {
			SystemPaletteManagement.copyToolTypeLibToDestination(project.getFolder(TYPE_LIB_FOLDER_NAME));
		}
		getProjectSystemEntries(project); // insert the project into the project list
		return project;
	}

	public synchronized AutomationSystem createNewSystem(final IContainer location, final String name) {
		final IFile systemFile = location.getFile(new Path(name + SystemManager.SYSTEM_FILE_ENDING_WITH_DOT));
		final Map<IFile, SystemEntry> projectSystems = getProjectSystemEntries(location.getProject());
		final SystemEntry entry = projectSystems.computeIfAbsent(systemFile,
				sysFile -> TypeLibraryManager.INSTANCE.getTypeLibrary(sysFile.getProject()).createSystemEntry(sysFile));
		entry.setType(SystemImporter.createAutomationSystem(systemFile));
		saveSystem(entry.getSystem());
		return entry.getSystem();
	}

	public synchronized void removeProject(final IProject project) {
		allSystemsInWS.remove(project);
		notifyListeners();
	}

	public synchronized void renameProject(final IProject oldProject, final IProject newProject) {
		final Map<IFile, SystemEntry> projectSystems = allSystemsInWS.remove(oldProject);
		if (projectSystems != null) {
			allSystemsInWS.put(newProject, projectSystems);
		}
	}

	public synchronized AutomationSystem replaceSystemFromFile(final AutomationSystem system, final IFile file) {
		removeSystem(system);
		return SystemManager.INSTANCE.getSystem(file);
	}

	/** Remove a system from the set of systems managed by the system manager
	 *
	 * @param system to be added */
	public void removeSystem(final AutomationSystem system) {
		removeSystem(system.getSystemFile());
	}

	public synchronized void removeSystem(final IFile systemFile) {
		final Map<IFile, SystemEntry> projectSystems = getProjectSystemEntries(systemFile.getProject());
		final SystemEntry refSystemEntry = projectSystems.remove(systemFile);
		if (null != refSystemEntry) {
			closeAllSystemEditors(refSystemEntry.getSystem());
			notifyListeners();
		}
	}

	public synchronized void moveSystemToNewProject(final IFile oldSystemFile, final IFile newSystemFile) {
		final Map<IFile, SystemEntry> projectSystems = getProjectSystemEntries(oldSystemFile.getProject());
		final SystemEntry systemEntry = projectSystems.remove(oldSystemFile);
		if (null != systemEntry) {
			final AutomationSystem system = systemEntry.getSystem();
			systemEntry.setFile(newSystemFile);
			system.setSystemFile(newSystemFile);
			final Map<IFile, SystemEntry> newProjectSystems = getProjectSystemEntries(oldSystemFile.getProject());
			newProjectSystems.put(newSystemFile, systemEntry);
			notifyListeners();
		}
	}

	/** Search for a system with the old System file and change it to the new file entry
	 *
	 * @param targetProject the project where to search for the system file, this may be different the old system file's
	 *                      project in case of project renames
	 * @param oldSystemFile
	 * @param newSystemFile */
	public synchronized void updateSystemFile(final IProject targetProject, final IFile oldSystemFile,
			final IFile newSystemFile) {
		final Map<IFile, SystemEntry> projectSystems = getProjectSystemEntries(targetProject);
		final SystemEntry systemEntry = projectSystems.remove(oldSystemFile);
		if (null != systemEntry) {
			final AutomationSystem system = systemEntry.getSystem();
			systemEntry.setFile(newSystemFile);
			system.setSystemFile(newSystemFile);
			projectSystems.put(newSystemFile, systemEntry);
		}
	}

	/** Load system.
	 *
	 *
	 * systemFile xml file for the system
	 *
	 * @return the system entry */
	private static SystemEntry initSystem(final IFile systemFile) {
		if (systemFile.exists()) {
			return TypeLibraryManager.INSTANCE.getTypeLibrary(systemFile.getProject()).createSystemEntry(systemFile);
		}
		return null;
	}

	public static void saveTagProvider(final AutomationSystem system, final ITagProvider tagProvider) {
		final IProject project = system.getSystemFile().getProject();
		final IPath projectPath = project.getLocation();
		tagProvider.saveTagConfiguration(projectPath);
	}

	public String getReplacedString(final AutomationSystem system, final String value) {
		final ArrayList<ITagProvider> tagProvider = getTagProviderList(system.getSystemFile().getProject());
		String result = null;
		for (final ITagProvider iTagProvider : tagProvider) {
			result = iTagProvider.getReplacedString(value);
			if (result != null) {
				break;
			}
		}
		return result;
	}

	/** Save system.
	 *
	 * @param system the system
	 * @param all    the all */
	public static void saveSystem(final AutomationSystem system) {
		saveSystem(system, system.getSystemFile());
	}

	public static void saveSystem(final AutomationSystem system, final IFile file) {
		Assert.isNotNull(system.getTypeEntry()); // there should be no system without type entry
		system.getTypeEntry().setLastModificationTimestamp(file.getModificationStamp() + 1);
		final SystemExporter systemExporter = new SystemExporter(system);
		systemExporter.saveSystem(file);
	}

	public synchronized AutomationSystem getSystem(final IFile systemFile) {
		final Map<IFile, SystemEntry> projectSystems = getProjectSystemEntries(systemFile.getProject());
		final SystemEntry sysEntry = projectSystems.computeIfAbsent(systemFile, SystemManager::initSystem);
		return sysEntry.getSystem();
	}

	public synchronized Map<IFile, SystemEntry> getProjectSystemEntries(final IProject project) {
		return allSystemsInWS.computeIfAbsent(project, p -> {
			loadTagProviders(project);
			return new HashMap<>();
		});
	}

	public synchronized List<AutomationSystem> getProjectSystems(final IProject porject) {
		return getProjectSystemEntries(porject).values().stream().map(SystemEntry::getSystem)
				.collect(Collectors.toList());
	}

	private void loadTagProviders(final IProject project) {
		final ArrayList<ITagProvider> providers = getTagProviderList(project);
		if (project.exists()) {
			final IExtensionRegistry registry = Platform.getExtensionRegistry();
			final IConfigurationElement[] elems = registry.getConfigurationElementsFor(PLUGIN_ID, "tagProvider"); //$NON-NLS-1$
			for (final IConfigurationElement element : elems) {
				try {
					final Object object = element.createExecutableExtension("Interface"); //$NON-NLS-1$
					if (object instanceof ITagProvider) {
						final ITagProvider tagProvider = (ITagProvider) object;
						if (tagProvider.loadTagConfiguration(project.getLocation())) {
							providers.add(tagProvider);
						}
					}
				} catch (final CoreException corex) {
					FordiacLogHelper.logError("Error loading TagProviders!", corex); //$NON-NLS-1$
				}
			}
		}
	}

	public ITagProvider getTagProvider(final Class<?> class1, final AutomationSystem system) {
		final ArrayList<ITagProvider> tagProviderList = getTagProviderList(system.getSystemFile().getProject());
		for (final ITagProvider iTagProvider : tagProviderList) {
			if (iTagProvider.getClass().equals(class1)) {
				return iTagProvider;
			}
		}

		ITagProvider provider = null;
		try {
			final Object obj = class1.getDeclaredConstructor().newInstance();
			if (obj instanceof ITagProvider) {
				provider = (ITagProvider) obj;
				provider.initialzeNewTagProvider();
				saveTagProvider(system, provider);
				tagProviderList.add(provider);
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			FordiacLogHelper.logError("Error on creating TagProvider instance!", e); //$NON-NLS-1$
			return null;
		}
		return provider;
	}

	private ArrayList<ITagProvider> getTagProviderList(final IProject project) {
		return tagProviders.computeIfAbsent(project, p -> new ArrayList<>());
	}

	private static String[] getNatureIDs() {
		return new String[] { SystemManager.FORDIAC_PROJECT_NATURE_ID, XtextProjectHelper.NATURE_ID };
	}

	private static String[] getBuilderIDs() {
		return new String[] { XtextProjectHelper.BUILDER_ID };
	}

	private static void closeAllSystemEditors(final AutomationSystem refSystem) {
		// display related stuff needs to run in a display thread
		Display.getDefault().asyncExec(
				() -> EditorUtils.closeEditorsFiltered((final IEditorPart editor) -> (editor instanceof ISystemEditor)
						&& (refSystem.equals(((ISystemEditor) editor).getSystem()))));

	}

	/** Notify listeners. */
	public void notifyListeners() {
		listeners.forEach(DistributedSystemListener::distributedSystemWorkspaceChanged);
	}

	/** Adds the workspace listener.
	 *
	 * @param listener the listener */
	public void addWorkspaceListener(final DistributedSystemListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public TypeEntry getTypeEntry(final IFile file) {
		final Map<IFile, SystemEntry> map = allSystemsInWS.get(file.getProject());

		if (map == null) {
			return null;
		}
		return map.get(file);
	}

}
