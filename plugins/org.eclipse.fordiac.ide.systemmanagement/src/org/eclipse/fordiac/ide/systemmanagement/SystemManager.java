/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, AIT, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Matthias Plasch, Filip Andren,
 *   Waldemar Eisenmenger, Martin Melik Merkumians
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

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
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.NamedElementComparator;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.dataexport.SystemExporter;
import org.eclipse.fordiac.ide.model.dataimport.SystemImporter;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.systemmanagement.extension.ITagProvider;
import org.eclipse.fordiac.ide.systemmanagement.util.SystemPaletteManagement;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.swt.widgets.Display;

/**
 * The Class SystemManager.
 * 
 * @author gebenh
 */
public enum SystemManager {
	
	INSTANCE;
	
	public static final String FORDIAC_PROJECT_NATURE_ID = "org.eclipse.fordiac.ide.systemmanagement.FordiacNature"; //$NON-NLS-1$
	public static final String OLD_DISTRIBUTED_PROJECT_NATURE_ID = "org.fordiac.systemManagement.DistributedNature"; //$NON-NLS-1$
	
	static final String SYSTEM_FILE_ENDING = ".sys"; //$NON-NLS-1$

	
	/** The model systems. */
	List<AutomationSystem> systems = new ArrayList<>();

	Hashtable<AutomationSystem, Runnable> runningJobs = new Hashtable<AutomationSystem, Runnable>();

	private final HashMap<AutomationSystem, ArrayList<ITagProvider>> tagProviders = new HashMap<AutomationSystem, ArrayList<ITagProvider>>();

	/** The listeners. */
	ArrayList<DistributedSystemListener> listeners = new ArrayList<DistributedSystemListener>();
	
	private final Hashtable<AutomationSystem, CommandStack> systemCommandStacks = new Hashtable<AutomationSystem, CommandStack>();

	/**
	 * Gets the command stack.
	 * 
	 * @param system
	 *          the system
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
		for (Iterator<DistributedSystemListener> iterator = listeners.iterator(); iterator.hasNext();) {
			DistributedSystemListener listener = iterator.next();
			listener.distributedSystemWorkspaceChanged();
		}
	}


	/**
	 * Adds the workspace listener.
	 * 
	 * @param listener
	 *            the listener
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
		TypeLibrary.getInstance(); // this will correctly setup the tool library
									// and initialize important stuff. should be
									// done before loading any systems and
									// adding the resource change listener
		loadSystems();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(new FordiacResourceChangeListener(this));
	}

	/**
	 * Add a system to be managed by the system manager. Additionally, it
	 * initializes the palette of the system.
	 * 
	 * @param system
	 *            the system to be added
	 */
	public void addSystem(final AutomationSystem system) {
		systems.add(system);
		//keep system list sorted so that it is alphabethical shown in all lists
		Collections.sort(systems, NamedElementComparator.INSTANCE);
		notifyListeners();
		Runnable job = createUniqueFBNamesValidity(system);
		runningJobs.put(system, job);
	}

	/**
	 * Remove a system from the set of systems managed by the system manager
	 * 
	 * @param system
	 *            to be added
	 */
	public void removeSystem(final AutomationSystem system) {
		if(systems.remove(system)){
			// TODO cleanup other hash tables the system may be in
			notifyListeners();
		}
	}

	/**
	 * Gets the systems.
	 * 
	 * @return the systems
	 */
	public List<AutomationSystem> getSystems() {
		return systems;
	}

	/**
	 * Load systems.
	 */
	public void loadSystems() {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		List<AutomationSystem> oldSystems = new ArrayList<>(systems);
		systems.clear();
		for (int i = 0; i < projects.length; i++) {
			loadProject(projects[i]);
		}
		// remove all existing models to get the models that no longer exist
		removeOrphanedSystems(oldSystems);
		
	}

	private void removeOrphanedSystems(List<AutomationSystem> oldSystems) {
		for (AutomationSystem automationSystem : oldSystems) {
			if(null == getSystemForName(automationSystem.getName())){
				removeSystem(automationSystem);			
			}
		}		
	}

	public AutomationSystem loadProject(IProject project) {
		long time1;
		long time2;
		AutomationSystem system = null;
		if (project.isOpen() && isDistributedSystem(project)) {
			time1 = System.currentTimeMillis();
			system = loadSystem(project);
			time2 = System.currentTimeMillis();
			System.out.println(time2 - time1 + " ms"); //$NON-NLS-1$
			if (system != null) {
				//TODO do we need to check if the system is already in the list?
				addSystem(system);
				// load the applications
				Runnable runnable = runningJobs.get(system);
				if (runnable != null) {
					Display.getDefault().asyncExec(runnable);
				}
			}
		}
		return system;
	}

	private static void initializePalette(AutomationSystem system) {
		long time1;
		long time2;
		time1 = System.currentTimeMillis();
		DataTypeLibrary.getInstance();
		// load palette of the system and initialize the types
		Palette palette = TypeLibrary.loadPalette(system.getProject());
		system.setPalette(palette);

		time2 = System.currentTimeMillis();
		System.out.println(time2 - time1 + " ms for typelib"); //$NON-NLS-1$
	}

	/**
	 * Checks if is distributed system.
	 * 
	 * @param project
	 *            the project
	 * 
	 * @return true, if is distributed system
	 */
	private static boolean isDistributedSystem(final IProject project) {
		boolean retval = false;
		try {
			retval = (project.getNature(FORDIAC_PROJECT_NATURE_ID) != null) ||
					(project.getNature(OLD_DISTRIBUTED_PROJECT_NATURE_ID) != null);  //Allow that also pre eclipse 4diac projects are loaded correctly
		} catch (CoreException e) {
			Activator.getDefault().logWarning(e.getMessage(), e);
		}
		return retval;
	}

	/**
	 * Load system.
	 * 
	 * @param project
	 *            the project
	 * 
	 * @return the automation system
	 */
	private AutomationSystem loadSystem(final IProject project) {
		//TODO model refactoring - check if the marker deletion is a good idea here
		try {
			project.deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
		} catch (CoreException e1) {
			Activator.getDefault().logError(e1.getMessage(), e1);
		}

		IFile systemFile = project.getFile(project.getName() + SYSTEM_FILE_ENDING);
		if(systemFile.exists()){
			AutomationSystem system = createAutomationSystem(project);
			SystemImporter sysImporter = new SystemImporter();
			try {
				InputStream stream = systemFile.getContents();
				sysImporter.importSystem(stream, system);
				stream.close();
			} catch (CoreException e) {
				Activator.getDefault().logError(e.getMessage(), e);
			} catch (IOException e) {
				Activator.getDefault().logError(e.getMessage(), e);
			}
			return system;
		}
		return null;
	}

	public AutomationSystem createAutomationSystem(IProject project) {
		AutomationSystem system = LibraryElementFactory.eINSTANCE.createAutomationSystem();			
		system.setName(project.getName());
		system.setProject(project);
		
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
		IConfigurationElement[] elems = registry.getConfigurationElementsFor(
				Activator.PLUGIN_ID, "tagProvider"); //$NON-NLS-1$
		for (int i = 0; i < elems.length; i++) {
			IConfigurationElement element = elems[i];
			try {
				Object object = element.createExecutableExtension("Interface"); //$NON-NLS-1$
				if (object instanceof ITagProvider) {
					ITagProvider tagProvider = (ITagProvider) object;
					if (tagProvider.loadTagConfiguration(system.getProject().getLocation())) {
						providers.add(tagProvider);
					}
				}
			} catch (CoreException corex) {
				Activator.getDefault().logError("Error loading TagProviders!", corex);				 //$NON-NLS-1$
			}
		}

	}

	public void saveTagProvider(AutomationSystem system, ITagProvider tagProvider) {
		IProject project = system.getProject();
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

	private Runnable createUniqueFBNamesValidity(final AutomationSystem system) {

		return new Runnable() {
			public void run() {
				for (Application app : system.getApplication()) {
					checkAndCreateAnnotation(system, app.getFBNetwork().getNetworkElements());
				}
				checkDevices(system, system.getSystemConfiguration().getDevices());
			}

			private void checkDevices(AutomationSystem system, EList<Device> devices) {
				for (Device device : devices) {
					for (org.eclipse.fordiac.ide.model.libraryElement.Resource res : device.getResource()) {
						checkAndCreateAnnotation(system, res.getFBNetwork().getNetworkElements());
					}
				}
			}

			private void checkAndCreateAnnotation(AutomationSystem system, List<FBNetworkElement> element) {
				for (FBNetworkElement fb : element) {
					fb.getAnnotations().clear();
					// TODO model refactoring - check if this is necessary in the new
					// version if yes:
					// check how we can use the same functionality as in set name
					// if
					// (!NameRepository.getInstance().addSystemUniqueFBInstanceName(system,
					// fb.getName(), fb.getId())) {
					// Annotation anno = fb.createAnnotation("FB name not unique");
					// anno.setServity(IMarker.SEVERITY_ERROR);
					// }
					if(element instanceof SubApp){
						checkAndCreateAnnotation(system, ((SubApp) element).getFbNetwork().getNetworkElements());
					}
				}
			}
		};
	}



	/**
	 * Save system.
	 * 
	 * @param system
	 *            the system
	 * @param all
	 *            the all
	 */
	public void saveSystem(final org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem system) {
		IProject project = system.getProject();
		SystemExporter systemExporter = new SystemExporter();
		StringWriter stringWriter = new StringWriter();
		Result result = new StreamResult(stringWriter);
		try {
			systemExporter.generateSYSFileContent(system, result);

			IFile iec61499SystemFile = project.getFile(system.getName() + SYSTEM_FILE_ENDING);
			ByteArrayInputStream stream = new ByteArrayInputStream(stringWriter.toString().getBytes("UTF-8")); //$NON-NLS-1$
			if (iec61499SystemFile.exists()) {
				iec61499SystemFile.setContents(stream, IFile.KEEP_HISTORY | IFile.FORCE, null);
			} else {
				iec61499SystemFile.create(stream, IFile.KEEP_HISTORY | IFile.FORCE, null);
			}
		} catch (Exception e) {
			// TODO Perform correct error handling
			e.printStackTrace();
		}
	}

	/**
	 * Gets the system for name.
	 * 
	 * @param string
	 *            the string
	 * 
	 * @return the system for name
	 */
	public AutomationSystem getSystemForName(final String string) {
		for (AutomationSystem system : systems) {
			if (system.getName().equals(string)) {
				return system;
			}			
		}
		return null;
	}

	/**
	 * Gets the project handle.
	 * 
	 * @param name the name
	 * 
	 * @return the project handle
	 */
	private static IProject getProjectHandle(String name) {
		return ResourcesPlugin.getWorkspace().getRoot().getProject(name);
	}

	/**
	 * returns a unique/valid name for a system.
	 * 
	 * @param name the name
	 * 
	 * @return a unique/valid system name
	 */
	private static String getValidSystemName(final String name) {

		if (isUniqueSystemName(name)) {
			return name;
		} else {
			int i = 1;
			String temp = name + "_" + i; //$NON-NLS-1$
			while (INSTANCE.getSystemForName(temp) != null || getProjectHandle(temp).exists()) {
				i++;
				temp = name + "_" + i; //$NON-NLS-1$
			}
			return temp;
		}
	}
	
	public static boolean isUniqueSystemName(final String name) {
		return (INSTANCE.getSystemForName(name) == null && !getProjectHandle(name).exists());
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

	public Palette getPalette(IProject project) {
		AutomationSystem srcSystem = getSystemForName(project.getName());
		if (srcSystem != null) {
			return srcSystem.getPalette();
		}
		return TypeLibrary.getInstance().getPalette();
	}

	public AutomationSystem createLocalProject(String projectName) {
		NullProgressMonitor monitor = new NullProgressMonitor();
		try {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			String systemName = SystemManager.getValidSystemName(projectName);

			IProject project = root.getProject(systemName);
			IProjectDescription description = ResourcesPlugin.getWorkspace().newProjectDescription(project.getName());

			description.setNatureIds(new String[] { SystemManager.FORDIAC_PROJECT_NATURE_ID });

			project.create(description, monitor);
			project.open(monitor);

			
			SystemPaletteManagement.copyToolTypeLibToProject(project);
		

			AutomationSystem system = createAutomationSystem(project); 
			INSTANCE.addSystem(system);

			AutomationSystem sys2 = INSTANCE.getSystemForName(system.getName());
			if (sys2 != null && !sys2.equals(system)) {
				system = sys2;
			}

			return system;
		} catch (CoreException x) {
			// Activator.getDefault().logError(x.getMessage(), x);
		} finally {
			monitor.done();
		}
		return null;
	}

}
