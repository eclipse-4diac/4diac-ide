/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, AIT, fortiss GmbH,
 * 				 2018, 2020 Johannes Kepler University Linz
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
 *   			 - New Project Explorer layout
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.dataexport.SystemExporter;
import org.eclipse.fordiac.ide.model.dataimport.SystemImporter;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.systemmanagement.extension.ITagProvider;
import org.eclipse.fordiac.ide.systemmanagement.util.SystemPaletteManagement;
import org.eclipse.gef.commands.CommandStack;

/**
 * The Class SystemManager.
 *
 * @author gebenh
 */
public enum SystemManager {

	INSTANCE;

	public static final String FORDIAC_PROJECT_NATURE_ID = "org.eclipse.fordiac.ide.systemmanagement.FordiacNature"; //$NON-NLS-1$
	public static final String OLD_DISTRIBUTED_PROJECT_NATURE_ID = "org.fordiac.systemManagement.DistributedNature"; //$NON-NLS-1$

	public static final String SYSTEM_FILE_ENDING = "sys"; //$NON-NLS-1$
	public static final String SYSTEM_FILE_ENDING_WITH_DOT = ".sys"; //$NON-NLS-1$

	public static final String TYPE_LIB_FOLDER_NAME = "Type Library"; //$NON-NLS-1$

	/** The model systems. */
	private Map<IProject, Map<IFile, AutomationSystem>> allSystemsInWS = new HashMap<>();

	private final Map<AutomationSystem, ArrayList<ITagProvider>> tagProviders = new HashMap<>();

	/** The listeners. */
	private List<DistributedSystemListener> listeners = new ArrayList<>();

	private final Map<AutomationSystem, CommandStack> systemCommandStacks = new HashMap<>();

	/**
	 * Gets the command stack.
	 *
	 * @param system the system
	 *
	 * @return the command stack
	 */
	public CommandStack getCommandStack(final AutomationSystem system) {
		if (!systemCommandStacks.containsKey(system)) {
			systemCommandStacks.put(system, new CommandStack());
		}
		return systemCommandStacks.get(system);
	}

	/**
	 * Notify listeners.
	 */
	public void notifyListeners() {
		for (DistributedSystemListener listener : listeners) {
			listener.distributedSystemWorkspaceChanged();
		}
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

	/**
	 * Instantiates a new system manager.
	 */
	private SystemManager() {
		// Correctly setup the tool library needs to be done before loading any systems
		// and adding the resource change listener
		TypeLibrary.loadToolLibrary();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(new FordiacResourceChangeListener(this));
	}

	public IProject createNew4diacProject(String projectName, IPath location, boolean importDefaultPalette,
			IProgressMonitor monitor) throws CoreException {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		IProject project = root.getProject(projectName);
		IProjectDescription description = ResourcesPlugin.getWorkspace().newProjectDescription(project.getName());

		if (!Platform.getLocation().equals(location)) {
			description.setLocation(location);
		}

		description.setNatureIds(getNatureIDs());

		project.create(description, monitor);
		project.open(monitor);

		// configure palette
		if (importDefaultPalette) {
			SystemPaletteManagement.copyToolTypeLibToDestination(project.getFolder(TYPE_LIB_FOLDER_NAME));
		}
		getProjectSystems(project); // insert the project into the project list
		return project;
	}

	public AutomationSystem createNewSystem(IContainer location, String name) {
		IFile systemFile = location.getFile(new Path(name + SystemManager.SYSTEM_FILE_ENDING_WITH_DOT));
		Map<IFile, AutomationSystem> projectSystems = getProjectSystems(location.getProject());
		AutomationSystem system = projectSystems.computeIfAbsent(systemFile, this::createAutomationSystem);
		saveSystem(system);
		return system;
	}

	void removeProject(IProject project) {
		allSystemsInWS.remove(project);
		notifyListeners();
	}

	/**
	 * Remove a system from the set of systems managed by the system manager
	 *
	 * @param system to be added
	 */
	public void removeSystem(final AutomationSystem system) {
		Map<IFile, AutomationSystem> projectSystems = getProjectSystems(system.getSystemFile().getProject());
		if (null != projectSystems.remove(system.getSystemFile())) {
			notifyListeners();
		}
	}

	private static void initializePalette(AutomationSystem system) {
		// load palette of the system and initialize the types
		Palette palette = TypeLibrary.getTypeLibrary(system.getSystemFile().getProject()).getBlockTypeLib();
		system.setPalette(palette);
	}

	/**
	 * Load system.
	 *
	 *
	 * systemFile xml file for the system
	 *
	 * @return the automation system
	 */
	private AutomationSystem loadSystem(final IFile systemFile) {
		// TODO model refactoring - check if the marker deletion is a good idea here
		try {
			systemFile.deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
		} catch (CoreException e1) {
			Activator.getDefault().logError(e1.getMessage(), e1);
		}

		if (systemFile.exists()) {
			AutomationSystem system = createAutomationSystem(systemFile);
			SystemImporter sysImporter = new SystemImporter(system);
			try {
				sysImporter.importSystem(systemFile.getContents());
			} catch (CoreException | TypeImportException e) {
				Activator.getDefault().logError(e.getMessage(), e);
			}
			return system;
		}
		return null;
	}

	private AutomationSystem createAutomationSystem(IFile systemFile) {
		AutomationSystem system = LibraryElementFactory.eINSTANCE.createAutomationSystem();
		system.setName(TypeLibrary.getTypeNameFromFile(systemFile));
		system.setSystemFile(systemFile);

		// create PhysicalConfiguration
		SystemConfiguration sysConf = LibraryElementFactory.eINSTANCE.createSystemConfiguration();
		system.setSystemConfiguration(sysConf);

		initializePalette(system);
		loadTagProvider(system);
		return system;
	}

	private void loadTagProvider(AutomationSystem system) {
		if (!tagProviders.containsKey(system)) {
			tagProviders.put(system, new ArrayList<ITagProvider>());
		}
		ArrayList<ITagProvider> providers = tagProviders.get(system);

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elems = registry.getConfigurationElementsFor(Activator.PLUGIN_ID, "tagProvider"); //$NON-NLS-1$
		for (IConfigurationElement element : elems) {
			try {
				Object object = element.createExecutableExtension("Interface"); //$NON-NLS-1$
				if (object instanceof ITagProvider) {
					ITagProvider tagProvider = (ITagProvider) object;
					if (tagProvider.loadTagConfiguration(system.getSystemFile().getProject().getLocation())) {
						providers.add(tagProvider);
					}
				}
			} catch (CoreException corex) {
				Activator.getDefault().logError("Error loading TagProviders!", corex); //$NON-NLS-1$
			}
		}

	}

	public void saveTagProvider(AutomationSystem system, ITagProvider tagProvider) {
		IProject project = system.getSystemFile().getProject();
		IPath projectPath = project.getLocation();
		tagProvider.saveTagConfiguration(projectPath);
	}

	public String getReplacedString(AutomationSystem system, String value) {
		ArrayList<ITagProvider> tagProvider = tagProviders.get(system);
		String result = null;
		for (ITagProvider iTagProvider : tagProvider) {
			result = iTagProvider.getReplacedString(value);
			if (result != null) {
				break;
			}
		}
		return result;
	}

	/**
	 * Save system.
	 *
	 * @param system the system
	 * @param all    the all
	 */
	public static void saveSystem(final AutomationSystem system) {
		SystemExporter systemExporter = new SystemExporter(system);
		systemExporter.saveSystem(system.getSystemFile());
	}

	/**
	 * Gets the system for name.
	 *
	 * @param string the string
	 *
	 * @return the system for name
	 */
	public AutomationSystem getSystemForName(final String string) {
//		for (AutomationSystem system : systems) {
//			if (system.getName().equals(string)) {
//				return system;
//			}
//		}
		return null;
	}

	public List<AutomationSystem> getSystems() {
		return Collections.emptyList();
	}

	public AutomationSystem getSystem(IFile systemFile) {
		Map<IFile, AutomationSystem> projectSystems = getProjectSystems(systemFile.getProject());
		return projectSystems.computeIfAbsent(systemFile, sysFile -> {
			long startTime = System.currentTimeMillis();
			AutomationSystem system = loadSystem(systemFile);
			long endTime = System.currentTimeMillis();
			System.out.println(
					"Loading time for System (" + systemFile.getName() + "): " + (endTime - startTime) + " ms"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			return system;
		});
	}

	public Map<IFile, AutomationSystem> getProjectSystems(IProject project) {
		return allSystemsInWS.computeIfAbsent(project, p -> new HashMap<>());
	}

	public ITagProvider getTagProvider(Class<?> class1, AutomationSystem system) {
		if (!tagProviders.containsKey(system)) {
			tagProviders.put(system, new ArrayList<ITagProvider>());
		}
		ITagProvider provider = null;
		ArrayList<ITagProvider> tagProviderList = tagProviders.get(system);
		if (tagProviderList != null) {
			for (ITagProvider iTagProvider : tagProviderList) {
				if (iTagProvider.getClass().equals(class1)) {
					provider = iTagProvider;
					break;
				}
			}
		}
		if (provider == null) {
			try {
				Object obj = class1.newInstance();
				if (obj instanceof ITagProvider) {
					provider = (ITagProvider) obj;
					provider.initialzeNewTagProvider();
					saveTagProvider(system, provider);
					tagProviderList.add(provider);
				}
			} catch (InstantiationException e) {
				return null;
			} catch (IllegalAccessException e) {
				return null;
			}
		}
		return provider;
	}

	private static String[] getNatureIDs() {
		return new String[] { SystemManager.FORDIAC_PROJECT_NATURE_ID };
	}

}
